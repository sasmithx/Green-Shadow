$(document).ready(function() {
    getUserCount();
    getStaffCount();
    getCropCount();
    getVehicleCount();
    getEquipmentCount();
    updateBarChart();
});

function getUserCount(){
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: "http://localhost:8080/api/v1/dash/user_count",
        headers: {"Authorization": `Bearer ${token}`},
        method: "GET",
        success: function (response) {
            console.log(response);
            $("#userCount").text(response);
            updateBarChart('userItem', response);
        },
        error: function (jqxhr, textStatus, error) {
            console.log("getUserCount failed.");
            console.log(jqxhr);
        }
    })
}



function getStaffCount(){
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: "http://localhost:8080/api/v1/dash/staff_count",
        headers: {"Authorization": `Bearer ${token}`},
        method: "GET",
        success: function (response) {
            console.log(response);
            $("#staffCount").text(response);
            updateBarChart('staffItem', response);
        },
        error: function (jqxhr, textStatus, error) {
            console.log("getStaffCount failed.");
            console.log(jqxhr);
        }
    })
}

function getCropCount(){
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: "http://localhost:8080/api/v1/dash/crop_count",
        headers: {"Authorization": `Bearer ${token}`},
        method: "GET",
        success: function (response) {
            console.log(response);
            $("#cropCount").text(response);
            updateBarChart('cropItem', response);
        },
        error: function (jqxhr, textStatus, error) {
            console.log("getCropCount failed.");
            console.log(jqxhr);
        }
    })
}

function getVehicleCount(){
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: "http://localhost:8080/api/v1/dash/vehicle_count",
        headers: {"Authorization": `Bearer ${token}`},
        method: "GET",
        success: function (response) {
            console.log(response);
            $("#vehicleCount").text(response);
            updateBarChart('vehicleItem', response);
        },
        error: function (jqxhr, textStatus, error) {
            console.log("getVehicleCount failed.");
            console.log(jqxhr);
        }
    })
}

function getEquipmentCount(){
    const token = localStorage.getItem('jwtToken');
    $.ajax({
        url: "http://localhost:8080/api/v1/dash/equipment_count",
        headers: {"Authorization": `Bearer ${token}`},
        method: "GET",
        success: function (response) {
            console.log(response);
            $("#equipmentCount").text(response);
            updateBarChart('equipmentItem', response);
        },
        error: function (jqxhr, textStatus, error) {
            console.log("getEquipmentCount failed.");
            console.log(jqxhr);
        }
    })
}



function updateBarChart(itemId, value) {
    $(`#${itemId}`).css('--val', value);
}


// MAP
