
// Function to show the corresponding section and hide others
const sidebar =document.querySelector('.sidebar');


$(document).ready(function () {
  $("#btnDash").click(function () {
    window.location.href = "../../pages/manager/manager-dash.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  $("#btnVehicle").click(function () {
    window.location.href = "../../pages/manager/manager-vehicle.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  $("#btnStaff").click(function () {
    window.location.href = "../../pages/manager/manager-staff.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  $("#btnEquipment").click(function () {
    window.location.href = "../../pages/manager/manager-equipment.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  $("#btnCrop").click(function () {
    window.location.href = "../../pages/manager/manager-crop.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  $("#btnField").click(function () {
    window.location.href = "../../pages/manager/manager-field.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  $("#btnUser").click(function () {
    window.location.href = "../../pages/manager/manager-user.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  $("#btnLog").click(function () {
    window.location.href = "../../pages/manager/manager-log.html"; // Navigate to Page 2
  });
});

$(document).ready(function () {
  $("#btnEmail").click(function () {
    window.location.href = "../../pages/manager/manager-email.html"; // Navigate to Page 2
  });
});


$(document).ready(function () {
  $("#btnLogout").click(function () {
    window.location.href = "../../Log.html"; // Navigate to Page 2
  });
});


$(document).ready(function () {
  // Initially show the log page and hide the crop page
  $('#crop-page').hide();
  $('#log-page').show();

  // Event handler for navigating to the log page
  $('#navLog').click(function () {
    $('#crop-page').hide();
    $('#log-page').show();
  });

  // Event handler for navigating to the crop page
  $('#navCrop').click(function () {
    $('#log-page').hide();
    $('#crop-page').show();
  });

 /* $('#viewCrop').click(function () {
    $('#crop-page').hide();
    $('#cart').show();
  });*/

});

