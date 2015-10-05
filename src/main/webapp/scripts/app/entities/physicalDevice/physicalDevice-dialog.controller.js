'use strict';

angular.module('transpilerApp').controller('PhysicalDeviceDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PhysicalDevice',
        function($scope, $stateParams, $modalInstance, entity, PhysicalDevice) {

        $scope.physicalDevice = entity;
        $scope.load = function(id) {
            PhysicalDevice.get({id : id}, function(result) {
                $scope.physicalDevice = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('transpilerApp:physicalDeviceUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.physicalDevice.id != null) {
                PhysicalDevice.update($scope.physicalDevice, onSaveFinished);
            } else {
                PhysicalDevice.save($scope.physicalDevice, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
