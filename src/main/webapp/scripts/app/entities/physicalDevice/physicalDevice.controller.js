'use strict';

angular.module('transpilerApp')
    .controller('PhysicalDeviceController', function ($scope, PhysicalDevice) {
        $scope.physicalDevices = [];
        $scope.loadAll = function() {
            PhysicalDevice.query(function(result) {
               $scope.physicalDevices = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PhysicalDevice.get({id: id}, function(result) {
                $scope.physicalDevice = result;
                $('#deletePhysicalDeviceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PhysicalDevice.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePhysicalDeviceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.physicalDevice = {deviceModelIdentifier: null, deviceModelName: null, serialNumber: null, paymentCaptureDeviceIdentifier: null, gateWayIdentifier: null, partyLevel: null, appliedToPartyIdentifier: null, partyRoleIdentifier: null, partyRoleName: null, deviceStatusIdentifier: null, deviceFirmwareVersion: null, deviceManufacturerIdentifier: null, deviceManufacturerName: null, P2PEComplianceFlag: null, deviceOwnershipFlag: null, id: null};
        };
    });
