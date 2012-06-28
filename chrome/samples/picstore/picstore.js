window.addEventListener("load", function() {
  initFilesystem(function() { readPictureDirectory(listDirResults); });

  $('#upload').click(upload);
});
