function bloodDonorFormValidator() {
    let contact = document.getElementById("contact").value;
    let password = document.getElementById("bloodDonorPassword").value;
    let confirmPassword = document.getElementById("bloodDonorConfirmPassword").value;

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