'use strict';

angular.module('expensereportApp').controller('ProjectDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Project', 'User', 'Report',
        function($scope, $stateParams, $uibModalInstance, entity, Project, User, Report) {

        $scope.project = entity;
        $scope.users = User.query();
        $scope.reports = Report.query();
        $scope.load = function(id) {
            Project.get({id : id}, function(result) {
                $scope.project = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('expensereportApp:projectUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.project.id != null) {
                Project.update($scope.project, onSaveSuccess, onSaveError);
            } else {
                Project.save($scope.project, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
