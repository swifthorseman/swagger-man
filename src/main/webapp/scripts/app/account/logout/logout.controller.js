'use strict';

angular.module('transpilerApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
