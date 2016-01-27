'use strict';

angular.module('expensereportApp')
    .controller('LineitemDetailController', function ($scope, $rootScope, $stateParams, entity, Lineitem, Report) {
        $scope.lineitem = entity;
        $scope.load = function (id) {
            Lineitem.get({id: id}, function(result) {
                $scope.lineitem = result;
            });
        };
        var unsubscribe = $rootScope.$on('expensereportApp:lineitemUpdate', function(event, result) {
            $scope.lineitem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
