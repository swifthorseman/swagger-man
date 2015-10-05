'use strict';

angular.module('transpilerApp')
    .controller('PhysicalDeviceDetailController', function ($scope, $rootScope, $stateParams, entity, PhysicalDevice) {
        $scope.physicalDevice = entity;
        $scope.load = function (id) {
            PhysicalDevice.get({id: id}, function(result) {
                $scope.physicalDevice = result;
            });
        };
        $rootScope.$on('transpilerApp:physicalDeviceUpdate', function(event, result) {
            $scope.physicalDevice = result;
        });
    });
