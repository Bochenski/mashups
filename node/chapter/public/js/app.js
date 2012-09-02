// Declare app level module which depends on filters, and services
angular.module('myApp', ['myApp.filters', 'myApp.services', 'myApp.directives']).
  config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
    $routeProvider
    	.when('/', {
    		templateUrl: 'partials/index', 
    		controller: IndexCtrl
    	}).
   		when('/addBook', {
   			templateUrl: 'partials/addBook', 
   			controller: AddBookCtrl
   		}).
   		when('/readBook/:id', {
   			templateUrl: 'partials/readBook',
   			controller: ReadBookCtrl
   		}).
   		when('/editBook/:id', {
   			templateUrl: 'partials/editBook',
   			controller: EditBookCtrl
   		}).
   		when('/deleteBook/:id', {
   			templateUrl: 'partials/deleteBook',
   			controller: DeleteBookCtrl
   		}).
    	otherwise({
    		redirectTo: '/'
    	});
    $locationProvider.html5Mode(true);
  }]);