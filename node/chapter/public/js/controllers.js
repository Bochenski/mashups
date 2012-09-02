function IndexCtrl($scope, $http) {
	$http.get('/api/books').
		success(function(data, status, headers, config) {
			$scope.books = data.books;
		});
}

function AddBookCtrl($scope,$http, $location) {
	$scope.form = {};
	$scope.submitBook = function() {
		$http.post('/api/book', $scope.form).
			success(function(data) {
				$location.path('/');
			});
	};
}

function ReadBookCtrl($scope, $http, $routeParams) {
	$http.get('/api/book/' + $routeParams.id).
		success(function(data) {
			$scope.book = data.book;
		});
}

function EditBookCtrl($scope, $http, $location, $routeParams) {
	$scope.form = {};
	$http.get('/api/book/' + $routeParams.id).
		success(function(data) {
			$scope.form = data.book;
		});

	$scope.editBook = function() {
		$http.put('/api/book/' + $routeParams.id, $scope.form).
			success(function(data) {
				$location.url('/readBook/' + $routeParams.id);
			});
	};
}

function DeleteBookCtrl($scope, $http, $location, $routeParams) {
	$http.get('/api/book/' + $routeParams.id).
		success(function(data) {
			$scope.book = data.book;
		});

	$scope.deleteBook = function() {
		$http.delete('/api/book/' + $routeParams.id).
			success(function(data) {
				$location.url('/');
			});
	};

	$scope.home = function() {
		$location.url('/');
	};
}