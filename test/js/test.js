/**
 * Created with IntelliJ IDEA.
 * User: carsten
 * Date: 11/2/13
 * Time: 11:32 PM
 * To change this template use File | Settings | File Templates.
 */
describe("A suite", function() {

    var $httpBackend;

    beforeEach(module('todoApp'));

    beforeEach(inject(function($injector) {
        $httpBackend = $injector.get('$httpBackend');
    }));

    it('should call allRequests', inject(function($rootScope, $controller) {
        var $scope = $rootScope.$new();

        $httpBackend.expectGET('/rest/todos',
            {"Accept":"application/json, text/plain, */*","X-Requested-With":"XMLHttpRequest"}).respond(201,
                [{"title":"newTitle","date":"2020-01-01"},{"title":"newTitle","date":"2020-01-01"}]
            );
        $controller('TodoCtrl', {$scope: $scope});

        $httpBackend.flush();

        var todos = $scope.todos;
        expect(todos[0].date).toBe("2020-01-01");
        expect(todos[0].title).toBe("newTitle");

    }));

    it('should call put on addTodo ', inject(function($rootScope, $controller) {
        var $scope = $rootScope.$new();

        $httpBackend.expectGET('/rest/todos',
            {"Accept":"application/json, text/plain, */*","X-Requested-With":"XMLHttpRequest"}).respond(201,
                []
            );

        $httpBackend.expectPOST('/rest/todo',
            null,
            {"Accept":"application/json, text/plain, */*","X-Requested-With":"XMLHttpRequest","Content-Type":"application/json;charset=utf-8"}).respond(201,
                null
            );
        $controller('TodoCtrl', {$scope: $scope});

        $scope.addTodo();
        $httpBackend.flush();

        //var todos = $scope.todos;
        //expect(todos.length).toBe(3);

    }));


    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

});


