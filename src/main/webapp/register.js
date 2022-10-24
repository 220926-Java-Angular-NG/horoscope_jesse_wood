let submitButton = document.getElementById("submit-button");
let backButton = document.getElementById("back-button");

submitButton.addEventListener('click', async(event) => {
    event.preventDefault();

    let firstName = document.getElementById("first-name").value;
    let lastName = document.getElementById("last-name").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let confirmPW = document.getElementById("cpassword").value;
    let zodiac = document.getElementById("zodiac").value;
    
    if (firstName && lastName && email && password && zodiac && password === confirmPW) {
        try {
            let newUser = {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                zodiac: zodiac
            }
            json = JSON.stringify(newUser);
            
            response = await fetch('http://localhost:8080/users/signup', {
                method:"POST",
                body:json
            })

            if (!response.ok) {
                throw new Error(response.status)
            }

            response.json().then((data) => {
                let currentUser = JSON.stringify(data);
                window.localStorage.setItem("currentUser", currentUser);
            });

            setTimeout(() => {
                window.location.replace("home.html");
            }, 3000);
        } catch(error) {
            console.log(error);
            let invalidText = document.getElementById("invalid");
            invalidText.innerHTML = "Invalid Credentials. Please try again."
        }
    }
});
backButton.addEventListener('click', async(event) => {
    event.preventDefault();

    console.log("back button");

    setTimeout(() => {
        window.location.replace('index.html');
    }, 3000);
});