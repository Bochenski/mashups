document.writeln('Hello, world!');

//Here we are defining a "method method" on all functions.  
//What this means is that we can define methods on Objects without having
//to refer specifically to the prototype
Function.prototype.method = function(name,func) {
  if(!this.prototype[name]) //don't add methods that are already defined - help prevent clashes
   this.prototype[name] = func;
   return this;
  }
}

//Object.create was implmented in 1.8.5 so this is to patch up 
//older versions of Javascript
if(typeof Object.create !== 'function') {
  Object.create = function(o) {
    var F = function() {};
    F.prototype = o;
    return new F();
  };
}

var stooge = { 
  "first-name": "Jerome",
  "last-name": "Howard"
};

var x = stooge;
x.nickname = 'Curly';
var nick = stooge.nickname;

//so another_stooge here has stooge as it's prototype.
//if we define e.g. another_stooge['first-name'] = 'bob' then that would be looked up first
//so stooge['first-name'] would remain unchanged (you can't change the prototype, only look up it's values)
var another_stooge = Object.create(stooge);


//This is an example where we're using the previously defined
//method method to define a method called integer on all Number Objects
//the ['ceil'] thing is like calling Math.ceil, but when we wern't quite sure what to type - cleaver
Number.method('integer', function() {
  return Math[this < 0 ? 'ceil' : 'floor'](this);
});

document.writeln((-10/3).integer());

var myObject = { value: 2};


myObject.double = function() {
  var that = this;

  var helper = function() {
    that.value = that.value + that.value
  };

  helper(); //Invoke helper as a function (so 'this' inside the function is the global object. 
    //'that' however is in scope giving access to this which is my object)
}

myObject.double(); //this is myObject inside the double function;

document.writeln(myObject.value);

var Quo = function(string) {
  this.status = string;
};

Quo.prototype.get_status = function() {
  return this.status;
};

//when we invoke a function with the new prefix, then it changes the
//behaviour of return, rather than return undefined, it returns 'this' (the new object)
//if however you specify a return object, it will return that instead
var myQuo = new Quo('hi');
document.writeln('step1');
document.writeln(this.status);
document.writeln('step2');
document.writeln(myQuo.get_status());
document.writeln(myQuo.flibble);