var todoApp = angular.module('todoApp', ['todoApp.resources']);

todoApp.controller('TodoCtrl', function TodoCtrl($scope,Todos,Todo) {
    $scope.todos = Todos.query(null, angular.noop, function () {
            throw new Error("query failed")
        });

    $scope.addTodo = function() {
         Todo.save(null,angular.noop,angular.noop);
    };
});