 'use strict';

angular.module('expensereportApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-expensereportApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-expensereportApp-params')});
                }
                return response;
            }
        };
    });
