console.log('hello');

var buf = new Buffer(100);

for (i = 0; i < 100; i ++) { 
  buf[i] = i
}

var newBuf = buf.slice(40,60)

console.log(buf);

console.log(newBuf);