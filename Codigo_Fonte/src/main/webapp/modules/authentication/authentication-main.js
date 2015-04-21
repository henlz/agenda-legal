(function(window, angular, undefined) {
	'use strict';

	//Start the AngularJS
	var module = angular.module('authentication', ['ngMaterial', 'ui.router', 'eits-dwr-broker']);

	module.config( function( $stateProvider, $urlRouterProvider, $importServiceProvider ) {
		//-------
		//Broker configuration
		//-------
		$importServiceProvider.setBrokerURL("./broker/interface");
		
		//-------
		//URL Router
		//-------

		//HOME
        $urlRouterProvider.otherwise("/");

        $stateProvider.state('signin',{
        	url : "/",
        	controller : 'SigninController',
        	templateUrl : "./modules/authentication/ui/signin/signin-view.jsp"
        });
	});

	module.run( function( $rootScope, $window ) {
		
	});

	angular.element(document).ready( function() {
		angular.bootstrap( document, ['authentication']);
	});

})(window, window.angular);