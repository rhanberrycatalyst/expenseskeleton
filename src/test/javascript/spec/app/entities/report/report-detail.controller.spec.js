'use strict';

describe('Controller Tests', function() {

    describe('Report Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockReport, MockProject, MockLineitem;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockReport = jasmine.createSpy('MockReport');
            MockProject = jasmine.createSpy('MockProject');
            MockLineitem = jasmine.createSpy('MockLineitem');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Report': MockReport,
                'Project': MockProject,
                'Lineitem': MockLineitem
            };
            createController = function() {
                $injector.get('$controller')("ReportDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'expensereportApp:reportUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
