var loaded = function() {

  var imageURL = window.webkitIntent.data;
  console.log("Saving " + imageURL);
  $('#image').attr("src", imageURL);

  $('#close').click(closeSave);
};

var closeSave = function() {
  // TODO: add code here to call postResult on incoming intent.
};

window.addEventListener("load", loaded);
