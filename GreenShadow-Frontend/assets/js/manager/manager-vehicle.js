let baseUrlVehicles='http://localhost:8080/api/v1/vehicles'


getAllV();
getNextVcodes();
btnRowClickV();
loadAllSCodes();

function getNextVcodes(){
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url:baseUrlVehicles+'/nextVd',
        method:'GET',
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function(resp){
            console.log(resp);
            $('#vehicleCode').val(resp)
        }
    });
}



function getAllV() {

    $("#vTbody").empty();
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: baseUrlVehicles,
        method: "GET",
        contentType: 'application/json',
        headers: {
            "Authorization": `Bearer ${token}`
        },
        success: function (res) {
            console.log(res);
            for (var f of res.data) {

                let row = `<tr>

                     <th>${f.vehicleCode}</th>
                        <td>${f.licensePlateNumber}</td>
                        <td>${f.vehicleCategory}</td>
                        <td>${f.fuelType}</td>
                        <td>${f.status}</td>
                        <td>${f.allocatedStaffId}</td>
                        <td>${f.remarks}</td>

                    </tr>`;
                $("#vTbody").append(row);

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
                $("#allocatedStaffId").empty();
                Cus += '<option value="' + customer.staffId + '">' + customer.staffId+ '</option>';

                console.log(typeof resp);
                $("#allocatedStaffId").append(Cus);
            }
        }
    });

}


$('#saveVbtn').click(function() {
    const token = localStorage.getItem('jwtToken');

    var formData = $("#vehicleForm").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log(data);
    console.log('Token:', token);

    $.ajax({
        url: baseUrlVehicles,
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
            getAllV();
            clearFieldsV();
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


$('#deleteVbtn').click(function (){
    let vCode = $("#vehicleCode").val();
    const token = localStorage.getItem('jwtToken');

    if (!vCode) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Vehicle code code is required to delete a field.'
        });
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/vehicles?vCode="+vCode,
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
            clearFieldsV();
            getAllV();



        },
        error: function (ob, status, t) {
            console.error("Error deleting field:", status, t);

            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Cannot delete the field. Please try again.'
            });
        }
    });
});

$('#updateVbtn').click(function() {

    const token = localStorage.getItem('jwtToken');
    var formData = $("#vehicleForm").serializeArray();
    var data = {};
    $(formData).each(function(index, obj) {
        data[obj.name] = obj.value;
    });

    console.log('Data to update:', data);
    console.log('Token:', token);

    $.ajax({
        url: baseUrlVehicles+'/update',
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
                text: 'Vehicle details updated successfully'
            });
            getAllV();
            clearFieldsV();
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


function btnRowClickV() {
    $('#vTable').on('click', 'tr', function() {
        var headers = $(this).children('th');
        var cells = $(this).children('td');
        $('#vehicleCode').val(headers.eq(0).text());
        $('#licensePlateNumber').val(cells.eq(0).text());
        $('#vehicleCategory').val(cells.eq(1).text());
        $('#fuelType').val(cells.eq(2).text());
        $('#status').val(cells.eq(3).text());
        $('#allocatedStaffId').val(cells.eq(4).text());
        $('#remarks').val(cells.eq(5).text());
    });
}





function clearFieldsV() {
    $('#vehicleCode').val('');
    $('#licensePlateNumber').val('');
    $('#vehicleCategory').val('');
    $('#fuelType').val('');
    $('#status').val('');
    $('#allocatedStaffId').val('');
    $('#remarks').val('');

    getNextVcodes();
    $('#vehicleCode').focus();
}
