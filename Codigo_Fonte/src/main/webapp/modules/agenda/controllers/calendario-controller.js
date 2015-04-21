(function (angular) {
    'use strict';


    angular.module('agenda').controller('CalendarioController', function( $scope, $compile, $importService, $log, $state, $mdToast, $mdDialog) {
    	
    	/**
    	 * 
    	 */
    	$importService("compromissoService");
    	
    	/**
    	 * 
    	 */
    	$scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
            $scope.initialize(toState, toParams, fromState, fromParams);
        });
    	
    	$scope.LIST_STATE = "agenda.listar";
    	
    	$scope.INSERT_STATE = "agenda.inserir";
    	
    	$scope.DETAIL_STATE = "agenda.detalhe";
    	
    	$scope.UPDATE_STATE = "agenda.alterar";
    	
    	$scope.currentEntity = {};
    	
    	/**
    	 * 
    	 */
    	$scope.initialize = function (toState, toParams, fromState, fromParams) {
    		var state = $state.current.name;
     
            switch (state) {
                case $scope.LIST_STATE:
                {
                    $scope.changeToList();
                }
                    break;
                case $scope.DETAIL_STATE:
                {
                    $scope.changeToDetail($state.params.id);
                }
                    break;
                case $scope.INSERT_STATE:
                {
                    $scope.changeToInsert();
                }
                    break;
                case $scope.UPDATE_STATE:
                {
                    $scope.changeToUpdate($state.params.id);
                }
                    break;
                default :
                {
                    $state.go($scope.LIST_STATE);
                }
            }
    	}
    	
    	$scope.changeToList= function() {
    		$scope.currentState = $scope.LIST_STATE;
    		
    		var pageRequest = new PageRequest();
        	pageRequest.size = 10;
        	$scope.pageRequest = pageRequest;
        	
        	$scope.listCompromissosByFilters(null, pageRequest);
    	}
    	
    	$scope.changeToInsert = function() {
            $scope.currentState = $scope.INSERT_STATE;
            
            $scope.currentEntity.dataInicio = new Date();
            $scope.currentEntity.dataFim = new Date();
            
            $scope.listCategoriasCompromissos();
            $scope.listTiposCompromissos();
    	}
    	
    	$scope.changeToDetail = function (id) {
            $log.info("changeToDetail", id);
     
            if (id == null || id == "" || id == 0) {
                $scope.msg = {type: "error", text: $scope.INVALID_ID_MESSAGE, dismiss: true};
                $scope.currentState = $scope.LIST_STATE;
                $state.go($scope.LIST_STATE);
                return;
            }
     
            compromissoService.findCompromissoById(id, {
                callback: function (result) {
                    $scope.currentEntity = result;
                    $scope.currentState = $scope.DETAIL_STATE;
                    $state.go($scope.DETAIL_STATE, {id: id});
                    $scope.$apply();
     
                    $scope.listCategoriasCompromissos();
                    $scope.listTiposCompromissos();
                },
                errorHandler: function (message, exception) {
                    $scope.msg = {type: "danger", text: message, dismiss: true};
                    $scope.$apply();
                }
            });
        };
        
        $scope.changeToUpdate = function (id) {
            $log.info("changeToUpdate", id);
     
            compromissoService.findCompromissoById(id, {
                callback: function (result) {
                    $scope.currentEntity = result;
                    $scope.currentState = $scope.UPDATE_STATE;
                    $state.go($scope.UPDATE_STATE, {id: id});
                    $scope.$apply();
     
                    $scope.listCategoriasCompromissos();
                    $scope.listTiposCompromissos();
                },
                errorHandler: function (message, exception) {
                    $scope.msg = {type: "danger", text: message, dismiss: true};
                    $scope.$apply();
                }
            });
        };
    	
    	$scope.listCategoriasCompromissos = function(){
    		compromissoService.listCategoriasCompromissos( {
        		callback: function(result){
        			$scope.listaCategorias = result;
        			$scope.$apply();
        		},
        		errorHandler: function(message, error){
        			$log.error(message);
        		}
        	})
    	}
    	
    	$scope.listTiposCompromissos = function(){
    		compromissoService.listTiposCompromissos( {
    			callback: function(result){
    				$scope.listaTipos = result;
    				$scope.$apply();
    			},
    			errorHandler: function(message, error){
    				$log.error(message);
    			}
    		})
    	}
    	
    	$scope.listCompromissosByFilters = function(filters, pageRequest){
    		compromissoService.listCompromissosByFilters(filters, pageRequest, {
        		callback: function(result){
        			$scope.content = result;
        			
        			var eventsArray = [];
        			
        			for (var i = 0; i < result.length; i++) {
						for (var x = 0; x < result[i].agendas.length; x++) {
							eventsArray.push({
								id: result[i].id,
								title: result[i].titulo,
								start: result[i].agendas[x].dataInicio,
								end: result[i].agendas[x].dataFim,
								description: result[i].descricao
							});
						}
					}
        			
        			$scope.renderCalendar(eventsArray);
					
        			$scope.$apply();
        		},
        		errorHandler: function(message, error){
        			$log.error(message);
        		}
        	})
    	}
    	
    	/**
    	 * 
    	 */
    	$scope.gridOptions = {
    		data: 'content',
    		columnDefs: [
    		    { displayName: "Titulo", field: "titulo", width: "50%" },
    		    { displayName: "Data de início", field: "dataInicio", cellFilter: "date : 'dd/MM/yyyy - hh:mm'" },
    		    { displayName: "Data de fim", field: "dataFim", cellFilter: "date : 'dd/MM/yyyy - hh:mm'" }
    		],
    		jqueryUITheme: true
    	};
    	
    	$scope.validaForm = function() {
    		
    		if (($scope.currentEntity.titulo == null || $scope.currentEntity.titulo == undefined) 
    				|| ($scope.currentEntity.dataInicio == null || $scope.currentEntity.dataInicio == undefined) 
    				|| ($scope.currentEntity.dataFim == null || $scope.currentEntity.dataFim == undefined) ) {
    			var toast = $mdToast.simple()
                .content("Preencha todos os campos obrigatórios.")
                .action('Fechar')
                .highlightAction(false)
                .position('bottom');

    			$mdToast.show(toast);
    			
    			return false;
    		}
			return true;
    	}
    	
    	$scope.insertCompromisso = function(){
    		
    		if ($scope.validaForm) {
	    		compromissoService.insertCompromisso($scope.currentEntity, {
	    			callback: function(result){
	    				$state.go($scope.LIST_STATE, {});
	    			},
	    			errorHandler: function(message, error){
	    				$log.error("Erro ao salvar: ",message);
	    			}
	    		})
    		}
    	};
    	
    	$scope.updateCompromisso = function(){
    		
    		if ($scope.validaForm) {
    			
    			compromissoService.updateCompromisso($scope.currentEntity, {
    				callback: function(result){
    					$state.go($scope.LIST_STATE, {});
    				},
    				errorHandler: function(message, error){
    					$log.error("Erro ao salvar: ",message);
    				}
    			})
    		}
    	};
    	
    	$scope.removeCompromisso = function() {
    		
    		var confirm = $mdDialog.confirm()
            .title('Exclusão de Compromisso')
            .content('Tem certeza que deseja excluir o registro? Esta operação não poderá ser desfeita.')
            .ariaLabel('Exclusão de Compromisso')
            .ok('Sim!')
            .cancel('Cancelar')
            .targetEvent();

            $mdDialog.show(confirm).then(function () {
    		
	    		if ($scope.currentEntity.id != null) {
	    			compromissoService.removeCompromisso($scope.currentEntity.id, {
	    				callback: function(result){
	    					$state.go($scope.LIST_STATE, {});
	    					
	    					$mdToast.show($mdToast.simple()
		    		                .content('Compromisso excluído com sucesso!')
		    		                .action('Fechar')
		    		                .highlightAction(false)
		    		                .position('top')).then(function() {
		    		            });
	    				},
	    				errorHandler: function(message, error){
	    					$log.error("Erro ao excluir: ",message);
	    				}
	    			})
	    		}
            });
    	}
    	
    	$scope.renderCalendar = function(events) {
    		$(calendarElement).fullCalendar({
				events: events,
				local: 'America/Brasil',
				aspectRatio: 3,
				displayEventEnd: true,
				lang: 'pt-br',
				eventRender: function(event, element) { 
//				    element.find('.fc-content').append("<br/><md-tooltip>"+ event.description +"</md-tooltip>");
//				    $compile(element)($scope)
				},
				eventClick: function(calEvent, jsEvent, view) {
					$state.go($scope.DETAIL_STATE, {id: calEvent.id});
			    }
			});
    	}
    	
    	$scope.dateOptions = {
	        changeYear: true,
	        changeMonth: true,
	        yearRange: '-0:2050'
	    };

    }
);

}(window.angular));