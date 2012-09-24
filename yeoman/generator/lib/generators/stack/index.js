var util = require('util'),
	yeoman = require('yeoman');

module.exports = Generator;

function Generator() {
	yeoman.generators.Base.apply(this, arguments);
}

util.inherits(Generator, yeoman.generators.Base);

Generator.prototype.createStackFile = function() {
	this.write('app/js/stack.js', "// I made this\n");
};