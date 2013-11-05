
angular.module('todoApp.resources', ['ngResource']).
    factory('Todos', function ($resource) {
        return $resource('/rest/todos');
    }).
    factory('Todo', function ($resource) {
        return $resource('/rest/todo');
    });
