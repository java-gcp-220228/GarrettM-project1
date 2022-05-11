let logoutBtn = document.querySelector('#logout-btn');

logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('jwt');

    window.location = 'index.html';
});

window.addEventListener('load', (event) => {
    populateTicketsTable();
});

async function populateTicketsTable() {
    const URL = `http://34.145.117.3:8080/tickets/pending`;

    let res = await fetch(URL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}` // Include our JWT into the request
        }
        // credentials: 'include' // This is if you're using HttpSession w/ JSESSIONID cookies
    })

    if (res.status === 200) {
        let tickets = await res.json();
        
        let tbody = document.querySelector('#Tickets-tbl > tbody');
        tbody.innerHTML = '';

        for (let ticket of tickets) {
            let tr = document.createElement('tr');

            let td1 = document.createElement('td');
            td1.innerText = ticket.id;

            let td2 = document.createElement('td');
            td2.innerText = ticket.ticketName;

            let td3 = document.createElement('td');
            td3.innerText = ticket.description;

            let td4 = document.createElement('td');
            td4.innerText = ticket.type;

            let td5 = document.createElement('td');
            td5.innerText = '$' + ticket.amount;

            let td6 = document.createElement('td');
            td6.innerText = ticket.createTime;

            let td7 = document.createElement('td');
            td7.innerText=ticket.resolutionTime;

            let td8 = document.createElement('td');
            td8.innerText = ticket.employeeUserName; 

            let td9 = document.createElement('td');
            td9.innerText = ticket.financeManagerUserName;

            let td10 = document.createElement('td');
            td10.innerText = ticket.status;
            
            let td11 = document.createElement('td');
            let imgElement = document.createElement('img');
            imgElement.setAttribute('src', `http://34.145.117.3:8080/tickets/${ticket.id}/image`);
            imgElement.style.height = '100px';
            td11.appendChild(imgElement);           

            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tr.appendChild(td6);
            tr.appendChild(td7);
            tr.appendChild(td8);
            tr.appendChild(td9);
            tr.appendChild(td10);
            tr.appendChild(td11);
            //console.log("reached querySelector")
            
            //console.log("reached appendchild")
            //console.log(tr);
            tbody.appendChild(tr);

            if(ticket.status == "Pending"){
                //create radio options
                console.log('getting to status check');
                let denyStatus = document.createElement('button');
                denyStatus.innerText = 'Deny Ticket';
                let approveStatus = document.createElement('button');
                approveStatus.innerText = 'Approve Ticket';
                tr.appendChild(denyStatus);
                tr.appendChild(approveStatus);

                denyStatus.addEventListener('click', async () => {
                    console.log('deny button pressed');
                    let response = 'Denied';
                    let res = await fetch(`http://34.145.117.3:8080/tickets/${ticket.id}?status=${response}`, {
                        method: 'PATCH',
                        headers: {
                            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
                        }
                    });

                    if (res.status === 200) {
                        populateTicketsTable(); // Have this function call itself
                    }

                });

                approveStatus.addEventListener('click', async () => {
                    console.log('approve button clicked');
                    let response = 'Approved';
                    let res = await fetch(`http://34.145.117.3:8080/tickets/${ticket.id}?status=${response}`, {
                        method: 'PATCH',
                        headers: {
                            'Authorization': `Bearer ${localStorage.getItem('jwt')}`
                        }
                    });
                    
                    if (res.status === 200) {
                        console.log("returned 200")
                        populateTicketsTable();
                    }
                }); 
            }
            
        }
    }
    
}