// See http://www.html5rocks.com/en/tutorials/canvas/imagefilters/ for
// details...

var Filters = {};

Filters.context = null;

Filters.apply = function(imageData, filterName) {
  var filter = this[filterName];
  return filter.call(this, imageData);
}

Filters.grayscale = function(pixels) {
  var d = pixels.data;
  for (var i=0; i<d.length; i+=4) {
    var r = d[i];
    var g = d[i+1];
    var b = d[i+2];
    // CIE luminance for the RGB
    var v = 0.2126*r + 0.7152*g + 0.0722*b;
    d[i] = d[i+1] = d[i+2] = v;
  }
  return pixels;
}

Filters.noise = function(pixels) {
  return this.AddNoise(pixels, 50);
}

Filters.AddNoise = function(pixels, level) {
  var d = pixels.data;
  for (var i = 0; i < d.length; i+=4) {
    var r = d[i];
    var g = d[i+1];
    var b = d[i+2];
    var newr = r + ((Math.random() - 0.5) * level);
    var newg = g + ((Math.random() - 0.5) * level);
    var newb = b + ((Math.random() - 0.5) * level);

    if (newr < 0) newr = 0;
    if (newr > 255) newr = 255;
    if (newg < 0) newg = 0;
    if (newg > 255) newg = 255;
    if (newb < 0) newb = 0;
    if (newb > 255) newb = 255;

    d[i] = newr;
    d[i+1] = newg;
    d[i+2] = newb;
  }
  return pixels;
}

Filters.colorScale = function(pixels, k) {
  var d = pixels.data;
  for (var i = 0; i < d.length; i+=4) {
    var r = d[i];
    var g = d[i+1];
    var b = d[i+2];
    var a = d[i+3];

    var newr = r * k[0] + g * k[1] + b*k[2]  + a*k[3];
    var newg = r * k[4] + g * k[5] + b*k[6]  + a*k[7];
    var newb = r * k[8] + g * k[9] + b*k[10]  + a*k[11];
    var newa = r * k[12] + g * k[13] + b*k[14]  + a*k[15];

    if (newr < 0) newr = 0;
    if (newr > 255) newr = 255;
    if (newg < 0) newg = 0;
    if (newg > 255) newg = 255;
    if (newb < 0) newb = 0;
    if (newb > 255) newb = 255;
    if (newa < 0) newa = 0;
    if (newa > 255) newa = 255;

    d[i] = newr;
    d[i+1] = newg;
    d[i+2] = newb;
    d[i+3] = newa;
  }
  return pixels;
}

// Credit to Pixastic for this math. (MPL License)
Filters.sepia = function(pixels) {
  return this.colorScale(pixels,
                         [0.393, 0.769, 0.189, 0,
                          0.349, 0.686, 0.168, 0,
                          0.272, 0.534, 0.131, 0,
                          0, 0, 0, 1]);
}

Filters.brighten = function(pixels) {
  return this.brightnessFilter(pixels, 40);
}

Filters.threshold = function(pixels) {
  return this.thresholdFilter(pixels, 128);
}

Filters.sharpen = function(pixels) {
  return this.convolute(pixels,
          [ 0, -1,  0,
           -1,  5, -1,
            0, -1,  0]);
}

Filters.blur = function(pixels) {
  return this.convolute(pixels,
          [ 1/9, 1/9, 1/9,
            1/9, 1/9, 1/9,
            1/9, 1/9, 1/9 ]);
}

Filters.sobel = function(pixels) {
  pixels = this.grayscale(pixels);

  var vertical = this.convoluteFloat32(pixels,
           [-1,-2,-1,
              0, 0, 0,
              1, 2, 1]);
  var horizontal = this.convoluteFloat32(pixels,
            [-1,0,1,
             -2,0,2,
             -1,0,1]);

  var id = this.context.createImageData(vertical.width, vertical.height);
  for (var i=0; i<id.data.length; i+=4) {
    var v = Math.abs(vertical.data[i]);
    id.data[i] = v;
    var h = Math.abs(horizontal.data[i]);
    id.data[i+1] = h
    id.data[i+2] = (v+h)/4;
    id.data[i+3] = 255;
  }
  return id;
};

Filters.brightnessFilter = function(pixels, adjustment) {
  var d = pixels.data;
  for (var i=0; i<d.length; i+=4) {
    d[i] += adjustment;
    d[i+1] += adjustment;
    d[i+2] += adjustment;
  }
  return pixels;
}

Filters.thresholdFilter = function(pixels, threshold) {
  var d = pixels.data;
  for (var i=0; i<d.length; i+=4) {
    var r = d[i];
    var g = d[i+1];
    var b = d[i+2];
    var v = (0.2126*r + 0.7152*g + 0.0722*b >= threshold) ? 255 : 0;
    d[i] = d[i+1] = d[i+2] = v
  }
  return pixels;
}

Filters.applyConvolution = function(pixels, weights) {
  return this.convolute(pixels, weights, true);
}

Filters.convolute = function(pixels, weights, opaque) {
  var side = Math.round(Math.sqrt(weights.length));
  var halfSide = Math.floor(side/2);

  var src = pixels.data;
  var sw = pixels.width;
  var sh = pixels.height;

  var w = sw;
  var h = sh;
  var output = this.context.createImageData(w, h);
  var dst = output.data;

  var alphaFac = opaque ? 1 : 0;

  for (var y=0; y<h; y++) {
    for (var x=0; x<w; x++) {
      var sy = y;
      var sx = x;
      var dstOff = (y*w+x)*4;
      var r=0, g=0, b=0, a=0;
      for (var cy=0; cy<side; cy++) {
        for (var cx=0; cx<side; cx++) {
          var scy = Math.min(sh-1, Math.max(0, sy + cy - halfSide));
          var scx = Math.min(sw-1, Math.max(0, sx + cx - halfSide));
          var srcOff = (scy*sw+scx)*4;
          var wt = weights[cy*side+cx];
          r += src[srcOff] * wt;
          g += src[srcOff+1] * wt;
          b += src[srcOff+2] * wt;
          a += src[srcOff+3] * wt;
        }
      }
      dst[dstOff] = r;
      dst[dstOff+1] = g;
      dst[dstOff+2] = b;
      dst[dstOff+3] = a + alphaFac*(255-a);
    }
  }
  return output;
}

if (!window.Float32Array)
  Float32Array = Array;

Filters.convoluteFloat32 = function(pixels, weights, opaque) {
  var side = Math.round(Math.sqrt(weights.length));
  var halfSide = Math.floor(side/2);

  var src = pixels.data;
  var sw = pixels.width;
  var sh = pixels.height;

  var w = sw;
  var h = sh;
  var output = {
    width: w, height: h, data: new Float32Array(w*h*4)
  };
  var dst = output.data;

  var alphaFac = opaque ? 1 : 0;

  for (var y=0; y<h; y++) {
    for (var x=0; x<w; x++) {
      var sy = y;
      var sx = x;
      var dstOff = (y*w+x)*4;
      var r=0, g=0, b=0, a=0;
      for (var cy=0; cy<side; cy++) {
        for (var cx=0; cx<side; cx++) {
          var scy = Math.min(sh-1, Math.max(0, sy + cy - halfSide));
          var scx = Math.min(sw-1, Math.max(0, sx + cx - halfSide));
          var srcOff = (scy*sw+scx)*4;
          var wt = weights[cy*side+cx];
          r += src[srcOff] * wt;
          g += src[srcOff+1] * wt;
          b += src[srcOff+2] * wt;
          a += src[srcOff+3] * wt;
        }
      }
      dst[dstOff] = r;
      dst[dstOff+1] = g;
      dst[dstOff+2] = b;
      dst[dstOff+3] = a + alphaFac*(255-a);
    }
  }
  return output;
}

