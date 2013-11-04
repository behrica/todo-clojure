var todoApp = angular.module('todoApp', []);

todoApp.controller('TodoCtrl', function TodoCtrl($scope) {
    $scope.todos = [1,2,3]

});