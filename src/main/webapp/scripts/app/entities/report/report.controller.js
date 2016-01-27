'use strict';

angular.module('expensereportApp')
    .controller('ReportController', function ($scope, $state, Report) {

        $scope.reports = [];
        $scope.loadAll = function() {
            Report.query(function(result) {
               $scope.reports = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.report = {
                reportname: null,
                id: null
            };
        };
    });
