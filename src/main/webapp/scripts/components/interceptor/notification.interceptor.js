 'use strict';

angular.module('transpilerApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-transpilerApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-transpilerApp-params')});
                }
                return response;
            },
        };
    });