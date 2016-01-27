'use strict';

describe('Controller Tests', function() {

    describe('Lineitem Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLineitem, MockReport;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLineitem = jasmine.createSpy('MockLineitem');
            MockReport = jasmine.createSpy('MockReport');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Lineitem': MockLineitem,
                'Report': MockReport
            };
            createController = function() {
                $injector.get('$controller')("LineitemDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expensereportApp:lineitemUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
