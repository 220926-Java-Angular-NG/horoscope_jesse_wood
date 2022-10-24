let submitButton = document.getElementById("submit-button");
let signupButton = document.getElementById("signup-button");

submitButton.addEventListener('click', async(event) => {
    event.preventDefault();

    let emailInput = document.getElementById("email-input").value;
    let passwordInput = document.getElementById("password-input").value;

    let user = {
        email:emailInput,
        password:passwordInput
    }
    console.log(user);

    json = JSON.stringify(user);
    console.log(json);

    try {
        fetch("http://localhost:8080/users/login", {
            method:"POST",
            body:json
        }).then((response) => {
            if (!response.ok) {
                throw Error(response.status);
            }
    
            response.json().then((data) => {
                let currentUser = JSON.stringify(data);
                window.localStorage.setItem("currentUser", currentUser);
            });
    
            setTimeout(() => {
                window.location.replace("home.html");
            }, 3000);
        });
    } catch (error) {
        console.log(error);
    }
});

signupButton.addEventListener('click', async(event) => {
    event.preventDefault();

    setTimeout(() => {
        window.location.replace("register.html");
    });
});