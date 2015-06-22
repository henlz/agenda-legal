(function (angular) {
    'use strict';


    angular.module('agenda').controller('LogsController', function ($scope, $compile, $importService, $log, $state, $mdToast, $mdDialog) {

        /**
         *
         */
        $importService("accountService");


        accountService.listEventosLog({
            callback: function(result){
                $scope.logs = result;
                $scope.$apply();
            },
            errorHandler: function(message) {
                $log.error(message);
            }
        })

    });

}(window.angular));