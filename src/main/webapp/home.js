let currentUser = window.localStorage.getItem("currentUser");
currentUser = JSON.parse(currentUser);
console.log(currentUser);

let welcome = document.getElementById("welcome");
let currentName = document.getElementById("name");
let email = document.getElementById("email");
let sign = document.getElementById("sign");
let horoscopeText = document.getElementById("horoscope");
let moodButton = document.getElementById("mood-button");
let moodText = document.getElementById("mood-text");
let logoutButton = document.getElementById("logout-button");

let horoscope;

try {
    getHoroscope(currentUser.zodiac).then((hs) => {
        horoscope = hs;
        console.log(horoscope);
        welcome.innerHTML = `Welcome to your future, ${currentUser.firstName}`;
        currentName.innerHTML = `Name: ${currentUser.firstName} ${currentUser.lastName}`;
        email.innerHTML = `Email: ${currentUser.email}`;
        sign.innerHTML = `Sign: ${horoscope.sunsign}`;
    
        horoscopeText.innerHTML = `Today's horoscope: ${horoscope.horoscope}`;
    });
    
    moodButton.addEventListener('click', async(event) => {
        event.preventDefault();

        try {
            horoscope = await getHoroscope(currentUser.zodiac);

            moodText.innerHTML = `You are feeling...${horoscope.meta.mood}`;
            currentUser.mood = horoscope.meta.mood;
            window.localStorage.setItem("currentUser", currentUser);
            console.log(currentUser);

            json = JSON.stringify(currentUser);

            response = await fetch('http://localhost:8080/mood/update', {
                method:"PUT",
                body: json
            });

            if (!response.ok) {
                throw new Error(response.status);
            }

        } catch(error) {
            console.log(error);
            moodText.innerHTML = "The fates are clouded...";
        }
    });
} catch (error) {
    console.log(error);
    welcome.innerHTML = "Something has gone wrong. Please refresh the page."
}

logoutButton.addEventListener('click', async(event) => {
    event.preventDefault();

    try {
        response = await fetch('http://localhost:8080/users/logout', {
            method:"POST"
        });
        if (!response.ok) {
            throw new Error(response.status);
        }

        setTimeout(() => {
            window.location.replace('index.html');
        }, 3000);
    } catch (error) {
        console.log(error);
    }
});

async function getHoroscope(zodiac) {
    response = await fetch(`http://sandipbgt.com/theastrologer/api/horoscope/${zodiac}/today`);
    if (!response.ok) {
        throw new Error(response.status);
    }
    return response.json();
}
