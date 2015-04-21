(function (angular) {
"use strict";
/**
 *
 */

angular.module("eits-bottomsheet", [])
    .directive('bottomsheet', function () {
        return {
            restrict: 'EA',
            scope: {
                delay: '='
            },
            link: function postLink(scope, element, attrs){
                $(element).addClass('eits-bottomsheet');

                var delay = scope.delay != undefined ? scope.delay : 450;
                scope.$on('showEitsBottomSheetEvent', function(){

                    if( $(element).is(':animated') ) {
                        $(element).stop(false, true);
                    }

                    $(element).animate({
                        height : $(element).css('height') == '0px' ? '150px' : '0px'
                    },{
                        duration: delay
                    });
                });
            }
        };
    });
}(window.angular));