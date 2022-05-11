let logoutBtn = document.querySelector('#logout-btn');
let allBtn = document.querySelector('#All-1');
let pendingBtn = document.querySelector('#Pending');
let approvedBtn = document.querySelector('#Approved');
let deniedBtn= document.querySelector('#Denied');
let populateFilter = 'All'

logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('jwt');

    window.location = 'index.html';
});

allBtn.addEventListener('click', () => {
    populateFilter = 'All';
    populateTicketsTable();
})

pendingBtn.addEventListener('click', () => {
    console.log("pending button click")
    populateFilter = 'Pending';
    populateTicketsTable();
})

approvedBtn.addEventListener('click', () => {
    populateFilter = 'Approved';
    populateTicketsTable();
})

deniedBtn.addEventListener('click', () => {
    populateFilter = 'Denied';
    populateTicketsTable();
})

window.addEventListener('load', (event) => {
    populateTicketsTable();
});

async function populateTicketsTable() {
    const URL = `http://34.145.117.3:8080/user/${localStorage.getItem('user_id')}/tickets`;

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
        let statusHeader = document.querySelector('#status-header');
        statusHeader.innerText = populateFilter;
        tbody.innerHTML = '';
        console.log("status 200");
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
            if(populateFilter == "Pending" && ticket.status == "Pending"){
                tbody.appendChild(tr);
            } else if (populateFilter == "Approved" && ticket.status == "Approved"){
                tbody.appendChild(tr);
            } else if (populateFilter == "Denied" && ticket.status == "Denied"){
                tbody.appendChild(tr);
            } else if (populateFilter == "All"){
                tbody.appendChild(tr);
            }      
            
        }
    }
    
}