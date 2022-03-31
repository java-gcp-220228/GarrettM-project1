let logoutBtn = document.querySelector('#logout-btn');

logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('jwt');

    window.location = 'index.html';
});



let submitButton = document.getElementById('submit-btn');

submitButton.addEventListener('click', async () => {
    let ticketName = document.getElementById('ticketName');
    let ticketDescription = document.getElementById('description');
    let ticketAmount = document.getElementById('amount');
    let ticketTypeInput = document.getElementsByName('type');
    let ticketImageInput = document.querySelector('#a-file-input')

    let ticketType;
    for(let radio of ticketTypeInput){
        if(radio.checked){
            ticketType = radio.value;
        }
    }
    if(ticketName.value == "" || ticketDescription.value == "" || ticketAmount <=0){
        return;
    }
    console.log(ticketName.value);
    console.log(ticketDescription.value);
    console.log(ticketType);
    


    let formData = new FormData();
    formData.append('ticketName', ticketName.value);
    formData.append('description', ticketDescription.value);
    formData.append('amount', ticketAmount.value);
    formData.append('type', ticketType);
    formData.append('image', ticketImageInput.files[0]);

    try {
        let res = await fetch(`http://localhost:8080/users/${localStorage.getItem('user_id')}/tickets`,{
            method: 'POST',
            body: formData,
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwt')}`
            }

        });
        
        window.location ='employee.html'
    } catch (e){
        console.log(e);
    }
})