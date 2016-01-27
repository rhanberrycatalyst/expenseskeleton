'use strict';

angular.module('expensereportApp')
	.controller('LineitemDeleteController', function($scope, $uibModalInstance, entity, Lineitem) {

        $scope.lineitem = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Lineitem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
