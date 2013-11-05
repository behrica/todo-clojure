var todoApp = angular.module('todoApp', ['todoApp.resources']);

todoApp.controller('TodoCtrl', function TodoCtrl($scope,Todos,Todo) {
    $scope.todoTitle= null;
    $scope.todos = retrieveTodos();

    function retrieveTodos() {
        return Todos.query(null, angular.noop, function () {
            throw new Error("query failed")
        });
    }


    $scope.addTodo = function() {
         Todo.save({title:$scope.todoTitle},function() {
             $scope.todos = retrieveTodos();
         },angular.noop);

    };
});