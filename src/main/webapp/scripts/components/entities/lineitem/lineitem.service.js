'use strict';

angular.module('expensereportApp')
    .factory('Lineitem', function ($resource, DateUtils) {
        return $resource('api/lineitems/:id', {}, {
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
