let baseUrlEq='http://localhost:8080/api/v1/equipment'
let baseUrlStaff='http://localhost:8080/api/v1/staff';
let baseUrlFiled='http://localhost:8080/api/v1/field';

getNextEcodes();
getAllEQ();
loadAllfiledCodes();
loadAllSCodes();
btnRowClickE();


function getNextEcodes(){
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url:baseUrlEq+'/nextEId',
        method:'GET',
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function(resp){
            console.log(resp);
            $('#equipmentId').val(resp)
        }
    });
}


function getAllEQ() {
    const token = localStorage.getItem('jwtToken');


    $("#equipmentTableBody").empty();
    $.ajax({
        url: baseUrlEq,
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function (res) {
            console.log(res);
            for (var f of res.data) {

                let row = `<tr>

                     <th>${f.equipmentId}</th>
                        <td>${f.name}</td>
                        <td>${f.type}</td>
                        <td>${f.status}</td>
                        <td>${f.equantity}</td>
                        <td>${f.assignedStaffId}</td>
                        <td>${f.assignedFieldCode}</td>

                    </tr>`;
                $("#equipmentTableBody").append(row);

            }
        }

    });

}


function loadAllfiledCodes() {
    $("#fieldCode1").empty();
    const token = localStorage.getItem('jwtToken');
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
                $("#assignedFieldCode").empty();
                Cus += '<option value="' + customer.fieldCode + '">' + customer.fieldCode+ '</option>';

                console.log(typeof resp);
                $("#assignedFieldCode").append(Cus);
            }
        }
    });

}

function loadAllSCodes() {
    $('#staffId').empty();
    const token = localStorage.getItem('jwtToken');

    var Cus = '';
    $.ajax({
        url: "http://localhost:8080/api/v1/staff",
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function (resp) {

            for (const customer of resp.data) {
                $("#assignedStaffId").empty();
                Cus += '<option value="' + customer.staffId + '">' + customer.staffId+ '</option>';

                console.log(typeof resp);
                $("#assignedStaffId").append(Cus);
            }
        }
    });

}


$('#saveEq').click(function() {
    const token = localStorage.getItem('jwtToken');

    var formData = $("#equipmentForm").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log(data);

    $.ajax({
        url: baseUrlEq,
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(data),
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function(res) {


            Swal.fire({
                icon: 'success',
                title: 'Saved Successfully',
                text: res.text
            });
            getAllEQ();
            clearFieldsEq();

        },
        error: function(ob, txtStatus, error) {
            alert(txtStatus);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: ob.responseText
            });
        }
    });
});

function btnRowClickE() {
    $('#eqTable').on('click', 'tr', function() {
        var headers = $(this).children('th');
        var cells = $(this).children('td');
        $('#equipmentId').val(headers.eq(0).text());
        $('#name1').val(cells.eq(0).text());
        $('#type').val(cells.eq(1).text());
        $('#status1').val(cells.eq(2).text());
        $('#quantity').val(cells.eq(3).text());
        $('#assignedStaffId').val(cells.eq(4).text());
        $('#assignedFieldCode').val(cells.eq(5).text());
    });
}

$('#updateEq').click(function() {
    const token = localStorage.getItem('jwtToken');

    var formData = $("#equipmentForm").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log('Data to update:', data);
    console.log('Token:', token);

    $.ajax({
        url: baseUrlEq+'/update',
        method: "PUT",
        contentType: 'application/json',
        data: JSON.stringify(data),
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function(res) {
            Swal.fire({
                icon: 'success',
                title: 'Updated Successfully',
                text: 'Equpment details updated successfully'
            });
            getAllEQ();
            clearFieldsEq();
        },
        error: function(ob, txtStatus, error) {
            alert(txtStatus);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: ob.responseText
            });
        }
    });
});

$('#deleteEq').click(function (){
    const token = localStorage.getItem('jwtToken');
    let eCode = $("#equipmentId").val();

    if (!eCode) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Ecode code is required to delete a field.'
        });
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/equipment?eCode="+eCode,
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
            clearFieldsEq();
            getAllEQ();



        },
        error: function (ob, status, t) {
            console.error("Error deleting field:", status, t);

            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Eqipment to delete the field. Please try again.'
            });
        }
    });
});


function clearFieldsEq() {
    $('#equipmentId').val('');
    $('#name1').val('');
    $('#type').val('');
    $('#status1').val('');
    $('#quantity').val('');
    $('#assignedStaffId').val('');
    $('#assignedFieldCode').val('');


    getNextEcodes();
    $('#equipmentId').focus();
}




