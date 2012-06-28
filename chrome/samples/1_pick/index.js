// Start the upload chain.
var upload = function() {
  console.log("uploading");

  // Bring up the Open file dialog. On file selection, call pickFile().
  var fileInput = $('<input>', {'type':'file', 'id':'fileselect'});
  fileInput.hide();
  $('#upload').parent().append(fileInput);
  fileInput.change(pickFile);
  fileInput.click();
};

// Step two in the upload chain. Loads the file upon selection,
// using FileReader.
var pickFile = function(e) {
  var files = e.currentTarget.files;
  if (files.length != 1) {
    $('#fileselect').detach();
    return;
  }

  var f = files[0];
  console.log("File named " + f.name);
  var reader = new FileReader();
  reader.onloadend = fileLoaded;
  reader.onerror = fileError;
  reader.readAsDataURL(f);
};

// Step three in the upload chain: load the image.
var fileLoaded = function(e) {
  console.log("File loaded ");
  var dataURL = e.currentTarget.result;
  loadImageFromUrl(dataURL);
};

// Step four in the upload chain. Load the image into the page
// from a url. This function is a good integration point for any
// code that loads the editor canvas from a url.
var loadImageFromUrl = function(url) {
  var img = new Image();
  img.onload = imageLoaded;
  img.src = url;
};

// Step five in the upload chain: draw the image to the canvas.
var imageLoaded = function() {
  var img = this;
  console.log("width=" + img.width + " height=" + img.height);

  $('#canvas').removeClass("empty");
  $('#canvas').attr('width',img.width);
  $('#canvas').attr('height',img.height);
  var ctx = document.getElementById('canvas').getContext('2d');
  ctx.drawImage(img, 0, 0);

  $('#fileselect').detach();

  $('#save').attr("disabled", false);
  $('#filter').attr("disabled", false);
  $('#apply').attr("disabled", false);
};

var pick = function() {
  console.log('Picking an image with web intents');

  var intent = new WebKitIntent(
      { "action":"http://webintents.org/pick",
        "type":"image/*"
      });

  navigator.webkitStartActivity(intent, onSuccess, onError);
};

// Step two in picking a file with web intents.
var onSuccess = function(data) {
  var imageURL;
  if (data instanceof Blob) {
    imageURL = webkitURL.createObjectURL(data);
  } else {
    imageURL = data;
  }

  loadImageFromUrl(imageURL);
};

var onError = function() {
  console.log("Error picking file with intents");
};

var fileError = function(e) {
  console.log("Error reading file");
  $('#fileselect').detach();
};

// Let the user save the image. Should bring up an iframe with the
// contents of the canvas and tell the user to right-click and save-image-as.
var save = function() {
  var dataURL = document.getElementById('canvas').toDataURL('image/jpeg');
  console.log("saving... " + dataURL);

  $('#saveframe').contents().find('#close').click(closeSave);
  $('#saveframe').contents().find('img').attr('src', dataURL);
  $('#saveframe').show();
};

var closeSave = function() {
  $('#saveframe').hide();
};

// Storage for the "undo" command.
var gSaveImageData;

// Manage applying a filter to the image.
var applyFilter = function() {
  var filter = $('#filter option:selected').attr("id");
  console.log("Apply filter: " + filter);

  // Ignore if no option selected.
  if (!filter) return;

  if (filter == "custom") {
    var arr = [];
    var inputs = $('#matrix :input');
    for (var i=0; i<inputs.length; i++) {
      arr.push(parseFloat(inputs[i].value));
    }
  }

  var ctx = document.getElementById('canvas').getContext('2d');
  gSaveImageData = ctx.getImageData(0, 0, ctx.canvas.width, ctx.canvas.height);
  var imageData = ctx.getImageData(0, 0, ctx.canvas.width, ctx.canvas.height);
  if (filter == 'custom') {
    imageData = Filters.applyConvolution(imageData, arr);
  } else {
    imageData = Filters.apply(imageData, filter);
  }
  ctx.putImageData(imageData, 0, 0);

  $('#undo').show();
  $('#undo').attr("disabled", false);
};

var undo = function() {
  console.log("undo apply filter");

  var ctx = document.getElementById('canvas').getContext('2d');
  ctx.clearRect(0, 0, gSaveImageData.width, gSaveImageData.height);
  ctx.putImageData(gSaveImageData, 0, 0);
  $('#undo').hide();
  $('#undo').attr("disabled", true);
  gSaveImageData = null;
};

var filterChange = function() {
  var filter = $('#filter option:selected').attr("id");
  if (filter == "custom") {
    $('#customdiv').show();
  } else {
    $('#customdiv').hide();
  }
};

var loaded = function() {
  $('#upload').click(upload);
  $('#pick').click(pick);
  $('#save').click(save);
  $('#apply').click(applyFilter);
  $('#undo').click(undo);
  $('#filter').change(filterChange);

  $('#save').attr("disabled", true);
  $('#filter').attr("disabled", true);
  $('#apply').attr("disabled", true);

  var ctx = document.getElementById('canvas').getContext('2d');
  Filters.context = ctx;

  checkVersion();
};

// Make sure we're on Chrome v2x
// Useful to distinguish the API used in v19 from v >= 20.
var checkVersion = function() {
  if (!navigator.userAgent.match(/Chrome\/2/)) {
    var warning = document.createElement('div');
    warning.setAttribute('style', 'text-align:center; background-color:red;');
    warning.innerText = 'Warning! You need to be using Chrome version >= 20!';
    $('header')[0].appendChild(warning);
  }
};

window.addEventListener("load", loaded);
