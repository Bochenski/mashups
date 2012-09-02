describe('Book controllers', function() {
	describe ('IndexCtrl', function() {
		it ('shoud display "books" model with 2 books', function() {
			var scope = {},
			ctrl = new IndexCtrl(scope);
			expect(scope.books.length).toBe(2);
		});
	});
});