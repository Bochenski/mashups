var file = urlParams['file'];
var mimeType;
var fileBlob;
var fileBlobUrl;
var viewFileEntry;

var onFilesystemReady = function() {
  pictureDirectory.getFile(file, {}, function(fileEntry) {
    viewFileEntry = fileEntry;
    fileEntry.file(function(f) {
      mimeType = f.type;
      fileBlob = f;
      fileBlobUrl = webkitURL.createObjectURL(f);
      document.getElementById('show').setAttribute('src', fileBlobUrl);
    }, onError);
  }, onError);
};

var editImage = function() {
  var intent = new WebKitIntent(
      { "action":"http://webintents.org/edit",
        "type":mimeType,
      "data":fileBlob,
      "extras":{"url":fileBlobUrl}});
  navigator.webkitStartActivity(intent, onEditSuccess, onEditFailure);
};

var onEditSuccess = function(data) {
  console.log("Got edited image " + data);
  if (data instanceof Blob) {
    viewFileEntry.createWriter(function (writer) {
      writer.onwriteend = onWriteEnd;
      writer.write(data);
    }, onError);
  } else {
    getDataUrlAsBlob(data, mimeType, function(b) {
      viewFileEntry.createWriter(function (writer) {
        writer.onwriteend = onWriteEnd;
        writer.write(b);
      }, onError);
    });
  }
};

var onWriteEnd = function() {
  console.log('Wrote edited file ');

  // Re-read the file from disk and display it.
  initFilesystem(onFilesystemReady);
};

var onEditFailure = function(data) {
  console.log('Could not edit image');
};

var loaded = function() {
  initFilesystem(onFilesystemReady);

  var h1 = document.getElementsByTagName('h1')[0].innerText = file;

  $('#edit').click(editImage);
};

window.addEventListener("load", function() {
  loaded();
});
