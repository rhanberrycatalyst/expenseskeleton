'use strict';

angular.module('expensereportApp')
    .controller('ProjectDetailController', function ($scope, $rootScope, $stateParams, entity, Project, User, Report) {
        $scope.project = entity;
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
            });
        };
        var unsubscribe = $rootScope.$on('expensereportApp:projectUpdate', function(event, result) {
            $scope.project = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
