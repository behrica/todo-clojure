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

    it('should call allRequests', inject(function($rootScope, $controller,$injector) {
        var $scope = $rootScope.$new();

//        $httpBackend.expectGET('/vlib/rest/requests',
//            {"Accept":"application/json, text/plain, */*"}).respond(201,
//                [{"id":"1","title":"my title"}]
//            );
        $controller('TodoCtrl', {$scope: $scope});

        //$httpBackend.flush();

        var requests = $scope.todos;
        //expect(requests[0].id).toBe("1")
        //expect(requests[0].title).toBe("my title")

    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

});


