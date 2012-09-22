var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/chapter');

var Schema   = mongoose.Schema;
var ObjectId = Schema.ObjectId;

var BookSchema = new Schema({
  title: String,
  chapters: String
});

var Book = mongoose.model('Book', BookSchema);

module.exports = Book;