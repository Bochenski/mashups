describe('Book controllers', function() {
	describe ('IndexCtrl', function() {

		var scope, ctrl, $httpBackend;

		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
			$httpBackend = _$httpBackend_;
			$httpBackend.expectGET('/api/books').
				respond({"books": [ {title: "Book 1", chapters: "4"}, {title: "Book 2", chapters: "5"}]});
			
			scope = $rootScope.$new();
			ctrl = $controller(IndexCtrl, {$scope: scope});
		}));

		it ('shoud display "books" model with 2 books', function() {

			expect(scope.books).toBeUndefined();
			
			$httpBackend.flush();

			expect(scope.books.length).toBe(2);
		});
	});
});