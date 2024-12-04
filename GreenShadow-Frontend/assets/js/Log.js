const wrapper = document.querySelector('.wrapper')
const registerLink = document.querySelector('.register-link')
const loginLink = document.querySelector('.login-link')

registerLink.onclick = () => {
    wrapper.classList.add('active')
}

loginLink.onclick = () => {
    wrapper.classList.remove('active')
}

$("#loginForm").on("submit", function(event) {
    event.preventDefault();

    const signinData = {
        email: $("#email").val(),
        password: $("#password").val()
    };

    // Make AJAX request to backend
    $.ajax({
        url: 'http://localhost:8080/api/v1/auth/signin',  // Adjust URL to match your server
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(signinData),
        success: function(response) {
            // Assuming response contains the JWT token
            if (response.token) {
                // Store token in localStorage (or sessionStorage)
                localStorage.setItem('jwtToken', response.token);

                // Show success alert with SweetAlert
                Swal.fire({
                    icon: 'success',
                    title: 'Login Successful!',
                    text: 'You are now logged in.',
                    //confirmButtonText: 'Proceed',
                    //timer: 2000 // Optionally, set a timer to auto close the alert
                }).then(() => {
                    // Redirect to protected page after the alert closes
                    window.location.href = 'pages/manager/manager-dash.html';
                });

                // Clear the form
                $("#loginForm")[0].reset();
                // getAllCrops();

                // Optionally call LoadAll or other functions to load protected data
                //

            } else {
                // Show error alert if token is not returned
                Swal.fire({
                    icon: 'error',
                    title: 'Error!',
                    text: 'Failed to retrieve token.',
                    confirmButtonText: 'Try Again'
                });
            }
        },
        error: function(xhr, status, error) {
            // Show error alert if AJAX request fails
            Swal.fire({
                icon: 'error',
                title: 'Sign In Failed!',
                text: 'Please check your credentials.',
                confirmButtonText: 'Retry'
            });
        }
    });
});


$("#registerForms").on("submit", function(event) {
    event.preventDefault();

    const signupData = {
        name: $("#registerName").val(),
        email: $("#registerEmail").val(),
        password: $("#registerPassword").val(),
        role: $("#userRole").val()
    };

    $.ajax({
        url: 'http://localhost:8080/api/v1/auth/signup',
        type: 'POST',
        contentType: 'application/json',

        data: JSON.stringify(signupData),
        success: function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Registration Successful',
                text: response.role
            });
            $("#registerForms")[0].reset();
            $('#login').show();
            $('#register').hide();
        },
        error: function() {
            Swal.fire({
                icon: 'error',
                title: 'Registration Failed',
                text: 'Error while registering user.'
            });
        }
    });
});