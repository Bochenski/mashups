var saveIntent = function() {
  // TODO: add code to postResult to the intent here
};

var EditServiceLoaded = function() {
  $('#save').click(saveIntent);
  $('#apply').click(applyFilter);
  $('#undo').click(undo);
  $('#filter').change(filterChange);

  $('#save').attr("disabled", true);
  $('#filter').attr("disabled", true);
  $('#apply').attr("disabled", true);

  var ctx = document.getElementById('canvas').getContext('2d');
  Filters.context = ctx;

  // TODO: add code to load image from window.webkitIntent here.
};

window.addEventListener("load", EditServiceLoaded);
