
angular.module('todoApp.resources', ['ngResource']).
    factory('Todos', function ($resource) {
        return $resource('/rest/todos');
    });