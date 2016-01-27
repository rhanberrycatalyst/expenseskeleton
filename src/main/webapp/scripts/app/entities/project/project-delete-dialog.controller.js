'use strict';

angular.module('expensereportApp')
	.controller('ProjectDeleteController', function($scope, $uibModalInstance, entity, Project) {

        $scope.project = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Project.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
