var fileSystem;
var pictureDirectory;

var urlParams = (function() {
  var pieces = window.location.search.split(/\?|\&/);
  var params = {};
  for (var i = 0; i <= pieces.length - 1; ++i) {
    var kv = pieces[i].split(/\=/);
    if (kv.length > 1) {
      params[decodeURIComponent(kv[0])] = decodeURIComponent(kv[1]);
    } else {
      params[decodeURIComponent(kv[0])] = null;
    }
  };
  return params;
})();

var initFilesystem = function(callback) {
  window.webkitRequestFileSystem(window.PERMANENT, 50*1024*1024, function(fs) {
    console.log('Opened filesystem');
    fileSystem = fs;
    fs.root.getDirectory('Pictures', {create: true}, function(dir) {
      pictureDirectory = dir;
      callback();
    }, onError);
  }, onError);
};

var toArray = function(list) {
  return Array.prototype.slice.call(list || [], 0);
};

var onError = function(e) {
  console.log(e);

  $('#error').html("Filesystem error! " + e.code);
  $('#error').show();
  setTimeout(function() { $('#error').hide(); }, 3000);
};

var readPictureDirectory = function(onComplete) {
  var reader = pictureDirectory.createReader();
  var entries = [];
  var readDir = function() {
    reader.readEntries(function(results) {
      if (!results.length) {
        onComplete(entries);
      } else {
        entries = entries.concat(toArray(results));
        readDir();
      }
    });
  };
  readDir();
};

var listDirResults = function(entries) {
  $('#entries').html('');
  entries.forEach(function(entry, i) {
    console.log('appending ' + entry.name);
    var link = document.createElement('a');
    link.setAttribute('href', 'show.html?file=' + encodeURIComponent(entry.name));
    link.innerHTML = entry.name;
    var li = document.createElement('li');
    li.appendChild(link);
    $('#entries').append(li);
  });
};

var getFileAsDataUrl = function(fileEntry, mimeType, callback) {
  var img = new Image();
  img.src = fileEntry.toURL();
  img.onload = function() {
    var canvas = document.createElement('canvas');
    canvas.width = img.width;
    canvas.height = img.height;
    var ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0);
    var dataURL = canvas.toDataURL(mimeType);
    callback(dataURL);    
  };
};

var getDataUrlAsBlob = function(url, mimeType, callback) {
  var img = new Image();
  img.src = url;
  img.onload = function() {
    var canvas = document.createElement('canvas');
    canvas.width = img.width;
    canvas.height = img.height;
    var ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0);
    if (canvas.toBlob) {
      canvas.toBlob(function(blob) { callback(blob); }, mimeType);
    } else {
      // Convert data url pieces to a blob.
      var dataURL = canvas.toDataURL(mimeType);
      var base64 = dataURL.split(',')[1];
      var binary = window.atob(base64);

      var arraybuf = new ArrayBuffer(binary.length);
      var arraybuf8 = new Uint8Array(arraybuf);
      for (var i = 0; i < binary.length; ++i) {
        arraybuf8[i] = binary.charCodeAt(i) & 0xff;
      }

      var bb = new WebKitBlobBuilder();
      bb.append(arraybuf);
      callback(bb.getBlob());
    }
  }
};

var upload = function(e) {
  console.log('uploading...');
  var fileInput = $('<input>', {'type':'file', 'id':'fileselect'});
  fileInput.hide();
  $('#upload').parent().append(fileInput);
  fileInput.change(pickFile);
  fileInput.click();
};

var pickFile = function(e) {
  var files = e.currentTarget.files;
  $('#fileselect').detach();
  if (files.length != 1) {
    return;
  }

  var f = files[0];
  console.log("Uploaded file named " + f.name);

  pictureDirectory.getFile(f.name, {create: true}, function(file) {
    file.createWriter(function (writer) {
      writer.onwriteend = function(e) {
        console.log('Wrote file ' +f.name);
        readPictureDirectory(listDirResults);
      };
      writer.write(f);
    }, onError);
  }, onError);
};
