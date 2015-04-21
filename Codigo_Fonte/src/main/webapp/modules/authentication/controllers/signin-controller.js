(function ( angular ) {
    'use strict';

/**
 *
 * @param $scope
 * @param $state
 */
angular.module('authentication')
	   .controller('SigninController', function( $scope, $state, $http, $mdToast, $window ) {

    /*-------------------------------------------------------------------
     * 		 				 	ATTRIBUTES
     *-------------------------------------------------------------------*/

    /*-------------------------------------------------------------------
     * 		 				 	  BEHAVIORS
     *-------------------------------------------------------------------*/

	$scope.signin = function( user ){
		
    	if( $scope.authenticationForm.$invalid ) {
    		$mdToast.showSimple( "Fomulário inválido" );
    	} else {

			var config = {
				headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
			};
			
			$http.post( "./authenticate", $.param(user), config)
			.success( function( data, status, headers, config ) {
				$window.location.href = "./";
			})
			.error( function( data, status, headers, config ){
				$mdToast.showSimple( data );
			});
    	}
    }
});

}(window.angular));