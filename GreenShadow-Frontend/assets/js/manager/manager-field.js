
let baseUrlFiled='http://localhost:8080/api/v1/field';
let baseUrlStaff='http://localhost:8080/api/v1/staff';

$(document).ready(function() {
    getAllFileds();
    getNextFiledCode();
    loadAllCropCodes();
    loadAllStaffCodes();
    btnRowClick();

});

function getNextFiledCode(){
    const token = localStorage.getItem('jwtToken');

    $.ajax({
        url:baseUrlFiled+'/generateFieldCode',
        method:'GET',
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function(resp){
            console.log(resp);
            $('#fieldCode').val(resp)
        }
    });
}



function getAllFileds() {

    $("#fieldTableBody").empty();
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: baseUrlFiled,
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function (res) {
            console.log(res);
            for (var field of res.data) {
                let empPic = field.fieldImageFile ? `data:image/jpeg;base64,${field.fieldImageFile}` : 'path/to/default/image.jpg'; // Use a default image if empPic is empty

                let row = `<tr>

                     <td>${field.fieldCode}</td>
                        <td>${field.fieldName}</td>
                        <td>${field.fieldLocation}</td>
                        <td>${field.size}</td>
                        <td>${field.cropCode}</td>
                        <td>${field.nameOfCrop}</td>
                        <td>${field.staffId}</td>
                        <td><img src="${empPic}" alt="${field.nameOfCrop}'s Picture" style="width: 50px; height: 50px;"/></td>

                    </tr>`;
                $("#fieldTableBody").append(row);

            }
        }

    });

}


function loadAllCropCodes() {
    $('#cropCode').empty();
    const token = localStorage.getItem('jwtToken');

    var Cus = '';
    $.ajax({
        url: "http://localhost:8080/api/v1/crop",
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function (resp) {

            for (const customer of resp) {
                $("#cropCode").empty();
                Cus += '<option value="' + customer.cropCode + '">' + customer.cropCode+ '</option>';

                console.log(typeof resp);
                $("#cropCode").append(Cus);
            }
        }
    });

}




function loadAllStaffCodes() {
    $('#staffId').empty();
    const token = localStorage.getItem('jwtToken');

    var Cus = '';
    $.ajax({
        url: baseUrlStaff,
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function (resp) {

            for (const customer of resp.data) {
                $("#staffId").empty();
                Cus += '<option value="' + customer.staffId + '">' + customer.staffId+ '</option>';

                console.log(typeof resp);
                $("#staffId").append(Cus);
            }
        }
    });

}

$('#fieldForm').on('submit', function(e) {
    e.preventDefault();
    const token = localStorage.getItem('jwtToken');

    var formData = new FormData(this);

    $.ajax({
        type: 'POST',
        url: baseUrlFiled+'/save',
        data: formData,
        contentType: false,
        headers: {
            "Authorization": `Bearer ${token}`
        },
        processData: false,
        success: function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Saved Successfully',
                text: response.text
            });

            getAllFileds();
            clearFields();
            btnRowClick();
        },
        error: function(xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: 'EROOR',
                text: status,xhr,error
            });
        }
    });
});


$('#deleteFiled').click(function (){
    let fieldID = $("#fieldCode").val();
    const token = localStorage.getItem('jwtToken');

    if (!fieldID) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Field code is required to delete a field.'
        });
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/field?fCode="+fieldID,
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function (res) {
            console.log(res);

            Swal.fire({
                icon: 'success',
                title: 'Deleted Successfully',
                text: res.text
            });
            clearFields();
            getAllFileds();

            getNextFiledCode();
        },
        error: function (ob, status, t) {
            console.error("Error deleting field:", status, t);

            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Failed to delete the field. Please try again.'
            });
        }
    });
});


function btnRowClick() {
    $('#filedTable').on('click', 'tr', function() {
        // Your existing code here
        let id=$(this).children(":eq(0)").text();
        let name=$(this).children(":eq(1)").text();
        let gender=$(this).children(":eq(2)").text();
        let joindate=$(this).children(":eq(3)").text();
        let level=$(this).children(":eq(4)").text();
        let totp=$(this).children(":eq(5)").text();
        let dob=$(this).children(":eq(6)").text();
        let pic=$(this).children(":eq(7)").text();


        $('#fieldCode').val(id);
        $('#fieldName').val(name);
        $('#fieldLocation').val(gender);
        $('#size').val(joindate);
        $('#cropCode').val(level);
        $('#nameOfCrop').val(totp);
        $('#staffId').val(dob);
        $('#fieldImage1').val('')

    });
}

function clearFields() {
    $('#fieldCode').val('');
    $('#fieldName').val('');
    $('#fieldLocation').val('');
    $('#size').val('');
    $('#cropCode').val('');
    $('#nameOfCrop').val('');
    $('#staffId').val('');
    $('#fieldImage1').val('');
    getNextFiledCode();
    $('#fieldCode').focus();
}



$('#updatefields').click(function (e) {
    e.preventDefault();
    const token = localStorage.getItem('jwtToken');


    let formData = new FormData();


    formData.append("fieldCode", $("#fieldCode").val());
    formData.append("fieldName", $("#fieldName").val());
    formData.append("fieldLocation", $("#fieldLocation").val());
    formData.append("size", $("#size").val());
    formData.append("cropCode", $("#cropCode").val() || null);
    formData.append("nameOfCrop", $("#nameOfCrop").val() || null);
    formData.append("staffId", $("#staffId").val() || null);
    formData.append("fieldImageFile", $("#fieldImage1")[0].files[0]);

    $.ajax({
        url: baseUrlFiled+'/update',
        type: "PUT",
        data: formData,
        processData: false,
        contentType: false,
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function (response) {
            console.log("Field updated successfully:", response);

            getAllFileds();
            clearFields();

            Swal.fire({
                icon: 'success',
                title: 'Update Successful',
                text: 'The field data has been updated successfully.'
            });
        },
        error: function (xhr, status, error) {
            console.error("Failed to update field:", error);

            Swal.fire({
                icon: 'error',
                title: 'Update Failed',
                text: 'An error occurred while updating the field data. Status: ' + xhr.status
            });
        }
    });
});
