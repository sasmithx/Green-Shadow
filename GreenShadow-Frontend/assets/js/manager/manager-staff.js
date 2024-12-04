let baseUrlStaff='http://localhost:8080/api/v1/staff';
let baseUrlVehicles='http://localhost:8080/api/v1/vehicles'

$(document).ready(function() {
    getAllStaff();
    loadAllfiledCodes();
    loadAllVCodes();
    getNextStaffCode();
    btnRowClickStaff();
});

function getNextStaffCode(){
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url:baseUrlStaff+'/nextId',
        method:'GET',
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`
        },

        success: function(resp){
            console.log(resp);
            $('#staffId1').val(resp)
        }
    });
}


function getAllStaff() {
    const token = localStorage.getItem('jwtToken');

    $("#staffTboady").empty();
    $.ajax({
        url: baseUrlStaff,
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        },

        success: function (res) {
            console.log(res);
            for (var r of res.data) {
                let row = `<tr>
                    <th>${r.staffId}</th>
                    <td>${r.firstName}</td>
                    <td>${r.lastName}</td>
                    <td>${r.designation}</td>
                    <td>${r.gender}</td>
                    <td>${r.joinedDate}</td>
                    <td>${r.dob}</td>
                    <td>${r.addressLine1}</td>
                    <td>${r.addressLine2}</td>
                    <td>${r.addressLine3}</td>
                    <td>${r.addressLine4}</td>
                    <td>${r.addressLine5}</td>
                    <td>${r.contactNo}</td>
                    <td>${r.email}</td>
                    <td>${r.members}</td>
                     <td>${r.fieldCode}</td>
                     <td>${r.vcode}</td>
                    
                    </tr>`;
                $("#staffTboady").append(row);

            }
        }

    });

}


$('#savestaffbtn').click(function() {

    const token = localStorage.getItem('jwtToken');
    var formData = $("#staffForm").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log(data);

    $.ajax({
        url: baseUrlStaff,
        method: "POST",
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`
        },

        data: JSON.stringify(data),
        success: function(res) {


            Swal.fire({
                icon: 'success',
                title: 'Saved Successfully',
                text: res.text
            });
            getAllStaff();
            clearFieldsStaff();
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

function loadAllfiledCodes() {
    $("#fieldCode1").empty();
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
                $("#fieldCode1").empty();
                Cus += '<option value="' + customer.fieldCode + '">' + customer.fieldCode+ '</option>';

                console.log(typeof resp);
                $("#fieldCode1").append(Cus);
            }
        }
    });

}


$('#deletestaffbtn').click(function (){

    let staffId = $("#staffId1").val();
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url:"http://localhost:8080/api/v1/staff?sCode="+staffId,
        method:"DELETE",
        headers: {
            "Authorization": `Bearer ${token}`
        },

        success:function (res) {
            console.log(res)
            getAllStaff();
            clearFieldsStaff();


            Swal.fire({
                icon: 'success',
                title: 'Delete Successfully',
                text: res.text
            });


        },
        error:function (ob,status,t){
            console.log(ob);
            console.log(status);
            console.log(t);

        }
    })
});

$('#updatestaffbtn').click(function() {

    var formData = $("#staffForm").serializeArray();
    const token = localStorage.getItem('jwtToken');
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log('Data to update:', data);

    $.ajax({
        url: baseUrlStaff+'/update',
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
                text: 'Staff details updated successfully'
            });
            getAllStaff();
            clearFieldsStaff();
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


function loadAllVCodes() {
    $("#vCode").empty();
    const token = localStorage.getItem('jwtToken');

    var Cus = '';
    $.ajax({
        url: baseUrlVehicles,
        method: "GET",
        dataType: "json",
        headers: {
            "Authorization": `Bearer ${token}`
        },

        success: function (resp) {

            for (const customer of resp.data) {
                $("#vCode").empty();
                Cus += '<option value="' + customer.vehicleCode + '">' + customer.vehicleCode+ '</option>';

                console.log(typeof resp);
                $("#vCode").append(Cus);
            }
        }
    });

}

function btnRowClickStaff() {
    $('#staffTable').on('click', 'tr', function() {
        var headers = $(this).children('th');
        var cells = $(this).children('td');
        $('#staffId1').val(headers.eq(0).text());
        $('#firstName').val(cells.eq(0).text());
        $('#lastName').val(cells.eq(1).text());
        $('#designation').val(cells.eq(2).text());
        $('#gender').val(cells.eq(3).text());
        $('#joinedDate').val(cells.eq(4).text());
        $('#dob').val(cells.eq(5).text());
        $('#addressLine1').val(cells.eq(6).text());
        $('#addressLine2').val(cells.eq(7).text());
        $('#addressLine3').val(cells.eq(8).text());
        $('#addressLine4').val(cells.eq(9).text());
        $('#addressLine5').val(cells.eq(10).text());
        $('#contactNo').val(cells.eq(11).text());
        $('#email1').val(cells.eq(12).text());
        $('#members').val(cells.eq(13).text());
        $('#fieldCode1').val(cells.eq(14).text());
        $('#vCode').val(cells.eq(15).text());

    });
}




function clearFieldsStaff() {
    $('#staffId1').val('');
    $('#firstName').val('');
    $('#lastName').val('');
    $('#designation').val('');
    $('#gender').val('');
    $('#joinedDate').val('');
    $('#dob').val('');
    $('#addressLine1').val('');
    $('#addressLine2').val('');
    $('#addressLine3').val('');
    $('#addressLine4').val('');
    $('#addressLine5').val('');
    $('#contactNo').val('');
    $('#email1').val('');
    $('#members').val('');
    $('#fieldCode1').val('');
    $('#vCode').val('');
    getNextStaffCode();
    $("#staffId1").focus();
}

