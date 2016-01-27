'use strict';

angular.module('expensereportApp')
    .controller('ProjectController', function ($scope, $state, Project) {

        $scope.projects = [];
        $scope.loadAll = function() {
            Project.query(function(result) {
               $scope.projects = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.project = {
                projectname: null,
                id: null
            };
        };
    });
