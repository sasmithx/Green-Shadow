

$(document).ready(function() {
    getNextCropCode();
    getAllCrops();
    loadAllfiledCodes1();
    btnRowClickCrop();

});


function loadAllfiledCodes1() {
    $("#fieldCodes").empty();
    const token = localStorage.getItem('jwtToken');
    var Cus = '';
    $.ajax({
        url: "http://localhost:8080/api/v1/field",
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": `Bearer ${token}`
        },

        success: function (resp) {

            for (const customer of resp.data) {
                $("#fieldCodes").empty();
                Cus += '<option value="' + customer.fieldCode + '">' + customer.fieldCode+ '</option>';

                console.log(typeof resp);
                $("#fieldCodes").append(Cus);
            }
        }
    });

}

function getNextCropCode() {
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: 'http://localhost:8080/api/v1/crop/cropCode',
        method: 'GET',
        dataType: 'json',
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function (resp) {
            console.log(resp);
            $('#cropCode1').val(resp.cropCode);
        },
        error: function (err) {
            console.error('Error fetching crop code:', err);
        }
    });
}


function getAllCrops() {
    $("#cropTableBody").empty();
    const token = localStorage.getItem('jwtToken');

    $.ajax({
        url: 'http://localhost:8080/api/v1/crop',
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function (res) {
            console.log(res);
            for (var crop of res) {

                let cropImageUrl = crop.cropImage ? `/img/${crop.cropImage}` : 'assets/img/Background.jpg';

                console.log(`Image URL: ${cropImageUrl}`);

                let row = `<tr>
                    <td>${crop.cropCode}</td>
                    <td>${crop.cropCommonName}</td>
                    <td>${crop.cropScientificName}</td>
                    <td><img src="${cropImageUrl}"></td>
                    <td>${crop.category}</td>
                    <td>${crop.qty}</td>
                    <td>${crop.cropSeason}</td>
                    <td>${crop.fieldCodes}</td>
                    <td>${crop.filedNames}</td>
                </tr>`;
                $("#cropTableBody").append(row);
            }
        }
    });
}


$('#cropForm').on('submit', function(e) {
    e.preventDefault();
    const token = localStorage.getItem('jwtToken');

    var formData = new FormData(this);

    for (let pair of formData.entries()) {
        console.log(pair[0]+ ': '+ pair[1]);
    }

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/api/v1/crop',
        data: formData,
        contentType: false,
        processData: false,
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Saved Successfully',
                text: response.text
            });
            clearFieldsCrops();
            getAllCrops();
        },
        error: function(xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: "NOT SAVED"
            });
            console.error(error);
        }
    });
});

function btnRowClickCrop() {
    $('#cropTable').on('click', 'tr', function() {
        // Your existing code here
        let id=$(this).children(":eq(0)").text();
        let name=$(this).children(":eq(1)").text();
        let gender=$(this).children(":eq(2)").text();
        let joindate=$(this).children(":eq(3)").text();
        let level=$(this).children(":eq(4)").text();
        let totp=$(this).children(":eq(5)").text();
        let dob=$(this).children(":eq(6)").text();
        let pic=$(this).children(":eq(7)").text();
        let nam=$(this).children(":eq(8)").text();


        $('#cropCode1').val(id);
        $('#cropCommonName').val(name);
        $('#cropScientificName').val(gender);
        $('#cropImage').val('');
        $('#category').val(level);
        $('#qty').val(totp);
        $('#cropSeason').val(dob);
        $('#fieldCodes').val(pic)
        $('#filedNames').val(nam)


    });
}


$('#deleteCropBtn').click(function (){
    let crop = $("#cropCode1").val();
    const token = localStorage.getItem('jwtToken');

    if (!crop) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Field code is required to delete a field.'
        });
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/crop?cCode="+crop,
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
            clearFieldsCrops();
            getAllCrops();
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

function clearFieldsCrops() {
    $('#cropCode1').val('');
    $('#cropCommonName').val('');
    $('#cropScientificName').val('');
    $('#cropImage').val('');
    $('#category').val('');
    $('#qty').val('');
    $('#cropSeason').val('');
    $('#fieldCodes').val('');
    $('#filedNames').val('');


    getNextCropCode();
    $('#cropCode1').focus();
}

$('#updaeCropbutton').on('click', function(e) {
    e.preventDefault();

    const token = localStorage.getItem('jwtToken');
    const cropCode = $('#cropCode1').val();

    if (!cropCode) {
        Swal.fire({
            icon: 'error',
            title: 'Crop Code Required',
            text: 'Please enter the crop code to update.'
        });
        return;
    }

    var formData = new FormData($('#cropForm')[0]);

    for (let pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
    }

    $.ajax({
        type: 'PUT',
        url: `http://localhost:8080/api/v1/crop/${cropCode}`,
        data: formData,
        contentType: false,
        processData: false,
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Updated Successfully',
                text: response.text || 'Crop updated successfully.'
            });
            clearFieldsCrops();
            getAllCrops();
        },
        error: function(xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: 'ERROR',
                text: xhr.responseText || "Failed to update crop."
            });
            console.error(error);
        }
    });
});

