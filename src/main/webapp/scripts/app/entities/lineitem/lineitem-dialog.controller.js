'use strict';

angular.module('expensereportApp').controller('LineitemDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lineitem', 'Report',
        function($scope, $stateParams, $uibModalInstance, entity, Lineitem, Report) {

        $scope.lineitem = entity;
        $scope.reports = Report.query();
        $scope.load = function(id) {
            Lineitem.get({id : id}, function(result) {
                $scope.lineitem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('expensereportApp:lineitemUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lineitem.id != null) {
                Lineitem.update($scope.lineitem, onSaveSuccess, onSaveError);
            } else {
                Lineitem.save($scope.lineitem, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
