var data = {
	"books" : [
	{
		"title": "Book 1",
		"chapters": "3"
	},
	{
		"title": "Book 2",
		"chapters": "4"
	}
	]
};

// GET

exports.books = function (req, res) {
	var books = [];
	data.books.forEach(function (book, i) {
		books.push( {
			id: i,
			title: book.title,
			chapters: book.chapters
		});
	});
	res.json({
		books: books
	});
};

exports.book = function (req, res) {
	var id = req.params.id;
	if (id >= 0 && id < data.books.length) {
		res.json({
			book: data.books[id]
		});
	} else {
		res.json(false);
	}
};

//POST
exports.addBook = function (req, res) {
	data.books.push(req.body);
	res.json(req.body);
};

//PUT
exports.editBook = function (req, res) {
	var id = req.params.id;

	if (id >= 0 && id < data.books.length) {
		data.books[id] = req.body;
		res.json(true);
	} else {
		res.json(false);
	}
};

//DELETE
exports.deleteBook = function(req, res) {
	var id = req.params.id;

	if (id >= 0 && id < data.books.length) {
		data.books.splice(id, 1);
		res.json(true);
	} else {
		res.json(false);
	}
};