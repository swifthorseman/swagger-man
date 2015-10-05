'use strict';

angular.module('transpilerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('physicalDevice', {
                parent: 'entity',
                url: '/physicalDevices',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PhysicalDevices'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/physicalDevice/physicalDevices.html',
                        controller: 'PhysicalDeviceController'
                    }
                },
                resolve: {
                }
            })
            .state('physicalDevice.detail', {
                parent: 'entity',
                url: '/physicalDevice/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PhysicalDevice'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/physicalDevice/physicalDevice-detail.html',
                        controller: 'PhysicalDeviceDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PhysicalDevice', function($stateParams, PhysicalDevice) {
                        return PhysicalDevice.get({id : $stateParams.id});
                    }]
                }
            })
            .state('physicalDevice.new', {
                parent: 'physicalDevice',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/physicalDevice/physicalDevice-dialog.html',
                        controller: 'PhysicalDeviceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {deviceModelIdentifier: null, deviceModelName: null, serialNumber: null, paymentCaptureDeviceIdentifier: null, gateWayIdentifier: null, partyLevel: null, appliedToPartyIdentifier: null, partyRoleIdentifier: null, partyRoleName: null, deviceStatusIdentifier: null, deviceFirmwareVersion: null, deviceManufacturerIdentifier: null, deviceManufacturerName: null, P2PEComplianceFlag: null, deviceOwnershipFlag: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('physicalDevice', null, { reload: true });
                    }, function() {
                        $state.go('physicalDevice');
                    })
                }]
            })
            .state('physicalDevice.edit', {
                parent: 'physicalDevice',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/physicalDevice/physicalDevice-dialog.html',
                        controller: 'PhysicalDeviceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PhysicalDevice', function(PhysicalDevice) {
                                return PhysicalDevice.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('physicalDevice', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
