'use strict';

angular.module('expensereportApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


