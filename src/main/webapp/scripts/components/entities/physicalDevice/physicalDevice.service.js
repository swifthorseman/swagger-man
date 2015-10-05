'use strict';

angular.module('transpilerApp')
    .factory('PhysicalDevice', function ($resource, DateUtils) {
        return $resource('api/physicalDevices/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
