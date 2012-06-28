var writeToFile = function() {
  var filename = document.getElementById('filename').value;
  console.log('Saving ' + filename);
  pictureDirectory.getFile(filename, {create: true}, function(file) {
    console.log('Writing to file ' + file);
    file.createWriter(function(writer) {
      writer.onwriteend = wroteFile;
      if (window.webkitIntent.data instanceof Blob) {
        console.log('Writing blob');
        writer.write(window.webkitIntent.data);
      } else {
        // do something fancy to make a blob through
        // url -> Image -> canvas -> bytes -> BlobBuilder
        console.log('Writing something else...');
        var url = window.webkitIntent.getExtra('url');
        if (!url) {
          url = window.webkitIntent.data;
        }
        getDataUrlAsBlob(url, window.webkitIntent.type, function(blob) {
          writer.write(blob);
        });
      }
    }, onError);
  }, onError);
};

var wroteFile = function() {
  console.log('Saved!');
  window.webkitIntent.postResult("ok");
};

var save = function() {
  console.log('Saving file...');
  initFilesystem(writeToFile);
};

var loaded = function() {
  $('#save').click(save);

  if (window.webkitIntent) {
    var url = window.webkitIntent.getExtra('url');
    var filename = window.webkitIntent.getExtra('filename');
    if (url) {
      document.getElementById('preview').setAttribute('src', url);
    } else if (window.webkitIntent.data instanceof Blob) {
      var blobUrl = window.URL.createObjectURL(window.webkitIntent.data);
      document.getElementById('preview').setAttribute('src', blobUrl);
    } else {
      document.getElementById('preview').setAttribute('src', window.webkitIntent.data);
    }

    if (filename) {
      document.getElementById('filename').value = filename;
    } else {
      document.getElementById('filename').value = 'picture.jpg';
    }
  }
};

window.addEventListener("load", loaded);
