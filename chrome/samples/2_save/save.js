var loaded = function() {
  var imageURL = window.webkitIntent.data;
  console.log("Saving " + imageURL);
  $('#image').attr("src", imageURL);

  $('#close').click(closeSave);
};

var closeSave = function() {
  console.log("Closing");
  window.webkitIntent.postResult("ok");
};

window.addEventListener("load", loaded);
