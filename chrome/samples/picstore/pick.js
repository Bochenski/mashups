var showThumbs = function(entries) {
  $('#files').html('');
  entries.forEach(function(entry, i) {
    console.log("Got image " + entry.name);
    var img = document.createElement('img');
    img.setAttribute('src', entry.toURL());
    img.addEventListener("click", function() { pick(entry); });
    $('#files').append(img);
  });
};

var pick = function(imageFileEntry) {
  if (!window.webkitIntent) {
    console.log("No intent. No pick. Picked " + imageFileEntry.name);
    return;
  }

  imageFileEntry.file(function(f) {
    var dataUrl = getFileAsDataUrl(imageFileEntry, f.type, onDataUrl);
  }, onError);
};

var onDataUrl = function(url) {
  window.webkitIntent.postResult(url);
}

var loaded = function() {
  initFilesystem(function() { readPictureDirectory(showThumbs); });
}

window.addEventListener("load", loaded);
