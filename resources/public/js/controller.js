var todoApp = angular.module('todoApp', ['todoApp.resources']);

todoApp.controller('TodoCtrl', function TodoCtrl($scope,Todos) {
    $scope.todos = Todos.query(null, angular.noop, function () {
            throw new Error("query failed")
        });
});