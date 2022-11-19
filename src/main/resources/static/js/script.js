function bloodDonorFormValidator() {
    let age = document.getElementById("age").value;
    let contact = document.getElementById("contact").value;
    let password = document.getElementById("bloodDonorPassword").value;
    let confirmPassword = document.getElementById("bloodDonorConfirmPassword").value;

    // age validation
    if (age < 18) {
        document.getElementById("ageMessage").innerHTML = "blood donors age must be minimum 18 years";
        return false;
    }

    if (age > 50) {
        document.getElementById("ageMessage").innerHTML = "blood donors age must be maximum 50 years";

        return false;
    }

    // contact validation
    let reg = /^(\+)?(88)?01[0-9]{9}$/;
    if (!reg.exec(contact)) {
        document.getElementById("contactMessage").innerHTML = "**invalid contact format...";
        console.log("Tanver")
        return false;
    }

    // password validation
    if (password === "") {
        document.getElementById("passwordMessage").innerHTML = "**please fill password...";
        return false;
    }
    if (password.length < 5) {
        document.getElementById("passwordMessage").innerHTML = "**password length must be " +
            "greater then 5 character...";
        return false;
    }

    if (password.length > 20) {
        document.getElementById("passwordMessage").innerHTML = "**password length must be " +
            "smaller then 20 character...";
        return false;
    }

    if (password !== confirmPassword) {
        document.getElementById("passwordMessage").innerHTML = "**Do not match your password...";
        return false;
    }
}

function bloodRecipientFormValidator() {
    let age = document.getElementById("age").value;
    let contact = document.getElementById("contact").value;
    let password = document.getElementById("bloodRecipientPassword").value;
    let confirmPassword = document.getElementById("bloodRecipientConfirmPassword").value;

    // age validation
    if (age <= 0) {
        document.getElementById("ageMessage").innerHTML = "blood reci's age must be minimum 18 years";
        return false;
    }

    if (age >= 130) {
        document.getElementById("ageMessage").innerHTML = "blood donors age must be maximum 50 years";
        return false;
    }

    // contact validation
    let reg = /^(\+)?(88)?01[0-9]{9}$/;
    if (!reg.exec(contact)) {
        document.getElementById("contactMessage").innerHTML = "**invalid contact format...";
        console.log("Tanver")
        return false;
    }

    // password validation
    if (password === "") {
        document.getElementById("passwordMessage").innerHTML = "**please fill password...";
        return false;
    }
    if (password.length < 5) {
        document.getElementById("passwordMessage").innerHTML = "**password length must be " +
            "greater then 5 character...";
        return false;
    }

    if (password.length > 20) {
        document.getElementById("passwordMessage").innerHTML = "**password length must be " +
            "smaller then 20 character...";
        return false;
    }

    if (password !== confirmPassword) {
        document.getElementById("passwordMessage").innerHTML = "**Do not match your password...";
        return false;
    }
}