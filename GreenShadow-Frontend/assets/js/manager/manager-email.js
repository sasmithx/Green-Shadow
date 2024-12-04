document.getElementById("contactForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent form submission for demonstration

    const fullName = document.getElementById("fullName").value;
    const email = document.getElementById("email").value;
    const subject = document.getElementById("subject").value;
    const message = document.getElementById("message").value;

    if (fullName && email && subject && message) {
        alert(`Thank you, ${fullName}! Your message has been sent.`);
        this.reset();
    } else {
        alert("Please fill in all the fields.");
    }
});