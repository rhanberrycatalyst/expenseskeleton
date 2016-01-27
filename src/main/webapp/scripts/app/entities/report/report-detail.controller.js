'use strict';

angular.module('expensereportApp')
    .controller('ReportDetailController', function ($scope, $rootScope, $stateParams, entity, Report, Project, Lineitem) {
        $scope.report = entity;
        $scope.load = function (id) {
            Report.get({id: id}, function(result) {
                $scope.report = result;
            });
        };
        var unsubscribe = $rootScope.$on('expensereportApp:reportUpdate', function(event, result) {
            $scope.report = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
