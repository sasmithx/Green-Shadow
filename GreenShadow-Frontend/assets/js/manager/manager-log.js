let baseUrlStaff='http://localhost:8080/api/v1/staff';


loadlogCode('LOG-'); // Replace 'IIM' with your desired prefix
loadAllCropCodesLogs();
loadAllfiledCodesLogs();
loadAllStaffCodeslog();
fetchLogDetails();



function loadlogCode(prefix) {
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: `http://localhost:8080/api/v1/log/generateCode?prefix=${prefix}`,
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (newCode) {
            console.log('Generated Inventory Code:', newCode);
            $('#logCode').val(newCode); // Populate the input field with the new code
        },
        error: function (xhr, status, error) {
            console.error('Error generating inventory code:', error);
        }
    });
}

function loadAllCropCodesLogs() {
    $('#cropCode').empty();
    const token = localStorage.getItem('jwtToken');
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: "http://localhost:8080/api/v1/crop",
        method: "GET",
        dataType: "json",//please convert the response into jason
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (resp) {

            for (const customer of resp) {
                $("#cropCode").empty();
                Cus += '<option value="' + customer.cropCode + '">' + customer.cropCode+ '</option>';

                console.log(typeof resp);
                $("#cropCode").append(Cus);
            }
            //  btnRowClick();
            //rowBack();
        }
    });

}

function loadAllfiledCodesLogs() {
    $("#fieldCode2").empty();
    const token = localStorage.getItem('jwtToken');
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: "http://localhost:8080/api/v1/field",
        method: "GET",
        dataType: "json",//please convert the response into jason
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },

        success: function (resp) {

            for (const customer of resp.data) {
                $("#fieldCode").empty();
                Cus += '<option value="' + customer.fieldCode + '">' + customer.fieldCode+ '</option>';

                console.log(typeof resp);
                $("#fieldCode").append(Cus);
            }
            //  btnRowClick();
            //rowBack();
        }
    });

}


function loadAllStaffCodeslog() {
    $('#staffCode').empty();
    const token = localStorage.getItem('jwtToken');
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: baseUrlStaff,
        method: "GET",
        dataType: "json",//please convert the response into jason
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (resp) {

            for (const customer of resp.data) {
                $("#staffCode").empty();
                Cus += '<option value="' + customer.staffId + '">' + customer.staffId+ '</option>';

                console.log(typeof resp);
                $("#staffCode").append(Cus);
            }
            //  btnRowClick();
            //rowBack();
        }
    });

}


$('#addCrop').click(function () {

    const cropCode = $('#cropCode').val();
    const staffId = $('#staffCode').val();
    const quantity = $('#cropQty').val();
    const members = $('#staffCount').val();

    // Validate the input fields
    if (!cropCode || !staffId || !quantity || !members) {
        alert("Please fill in all fields before adding crop details.");
        return;
    }

    // Push the new crop details into the cropDetails array
    cropDetails.push({
        cropCode: cropCode,
        staffId: staffId,
        quantity: quantity,
        membersInStaff: members
    });

    // Clear the input fields
    $('#cropCode').val('');
    $('#staffCode').val('');
    $('#cropQty').val('');
    $('#staffCount').val('');

    // Add crop detail row to the table
    const row = `
      <tr>
        <td>${cropCode}</td>
        <td>${staffId}</td>
        <td>${quantity}</td>
        <td>${members}</td>
      </tr>
    `;
    $('#tableDetails').append(row);
});
let cropDetails = [];
// Handle the Submit button click
$('#saveCrop').click(function () {
    const token = localStorage.getItem('jwtToken');
    // Collect the form data
    const logCode = $('#logCode').val();
    const logDate = $('#logDate').val();
    const logDetails = $('#logDetail').val();
    const role = $('#role').val();
    const fieldCode = $('#fieldCode').val();

    // Validate the form fields
    if (!logCode || !logDate || !logDetails || !role || !fieldCode || cropDetails.length === 0) {
        alert("Please fill in all the fields and add at least one crop detail.");
        return;
    }

    // Prepare the request data
    const monitorlogDTO = {
        logCode: logCode,
        logDate: logDate,
        logDetails: logDetails,
        role: role,
        fieldCode: fieldCode,
        cropDetails: cropDetails
    };

    // AJAX request to submit data to the backend
    $.ajax({
        url: 'http://localhost:8080/api/v1/log/update',  // Make sure this matches your backend endpoint
        type: 'POST',
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        data: JSON.stringify(monitorlogDTO),
        success: function (response) {
            alert(response);  // Success message
            fetchLogDetails();  // Refresh the monitoring log and crop details table
            clearForm();  // Clear the form after successful submission
        },
        error: function (xhr, status, error) {
            alert('Error: ' + xhr.responseText);
        }
    });
});

function fetchLogDetails() {
    const token = localStorage.getItem('jwtToken');

    $.ajax({
        url: 'http://localhost:8080/api/v1/log',  // Assuming you have an endpoint to fetch all logs
        type: 'GET',
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (data) {
            for (var f of data) {

                let row = `<tr>

                        <th>${f.logCode}</th>
                        <td>${f.logDate}</td>
                        <td>${f.logDetails}</td>
                        <td>${f.role}</td>
                        <td>${f.fieldCode}</td>
                      

                    </tr>`;
                $("#tblLog").append(row);

            }  // Populate the Monitor Logs table
        }
    });

    $.ajax({
        url: 'http://localhost:8080/api/v1/log/detail',  // Assuming you have an endpoint to fetch crop details
        type: 'GET',
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function (data) {
            for (var f of data) {

                let row = `<tr>

                     <th>${f.logCode}</th>
                        <td>${f.cropCode}</td>
                        <td>${f.staffId}</td>
                        <td>${f.membersInStaff}</td>
                        <td>${f.quantity}</td>
                      

                    </tr>`;
                $("#tblCropDetails").append(row);

            }
        }
    });
}


$('#btngetall').click(function() {
    const token = localStorage.getItem('jwtToken');

    $.ajax({
        url: 'http://localhost:8080/api/v2/log/most-used', // The URL of your controller endpoint
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${token}`  // Add the JWT token to the Authorization header
        },
        success: function(response) {
            // If the response is successful, populate the input field with the crop code
            $('#inputcropname').val(response); // Assuming the response is the cropCode

        },
        error: function(xhr, status, error) {
            // Handle error, for example show a message in case of failure
            alert('Error fetching most used crop: ' + error);
        }
    });
});
// Function to clear the form fields
function clearForm() {
    $('#logcode').val('');
    $('#logdate').val('');
    $('#logdetail').val('');
    $('#roleSelector').val('');
    $('#fieldCode2').val('');
    $('#cropCodeDesc').val('');
    $('#staffCode2').val('');
    $('#qunity').val('');
    $('#memebers').val('');
    $('#tableDetails').empty();
    cropDetails = [];  // Reset crop details
}



