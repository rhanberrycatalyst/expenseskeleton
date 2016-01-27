'use strict';

angular.module('expensereportApp')
    .controller('LineitemController', function ($scope, $state, Lineitem) {

        $scope.lineitems = [];
        $scope.loadAll = function() {
            Lineitem.query(function(result) {
               $scope.lineitems = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.lineitem = {
                amount: null,
                type: null,
                id: null
            };
        };
    });
