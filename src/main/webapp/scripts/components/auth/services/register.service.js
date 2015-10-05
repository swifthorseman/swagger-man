'use strict';

angular.module('transpilerApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


