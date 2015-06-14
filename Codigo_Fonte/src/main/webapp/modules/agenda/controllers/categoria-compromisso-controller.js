(function (angular) {
    'use strict';


    angular.module('agenda').controller('CategoriaCompromissoController', function( $scope, $compile, $importService, $log, $state, $mdDialog, $mdToast) {
    	
    	/**
    	 * 
    	 */
    	$importService("compromissoService");
    	
    	/**
    	 * 
    	 */
    	$scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
//            $log.info("$stateChangeSuccess");
     
            $scope.initialize(toState, toParams, fromState, fromParams);
        });
    	
    	/**
    	 * 
    	 */
    	$scope.LIST_STATE = "categoria-compromisso.listar";
    	
    	$scope.INSERT_STATE = "categoria-compromisso.inserir";
    	
    	$scope.currentEntity = {};
    	
    	$scope.data = {};
    	
    	/**
    	 * 
    	 */
    	$scope.initialize = function (toState, toParams, fromState, fromParams) {
    		var state = $state.current.name;
     
            switch (state) {
                case $scope.LIST_STATE:
                {
                	$scope.currentEntity = {};
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
        	
        	$scope.listCategoriasCompromissos();
    	}
    	
    	$scope.changeToInsert = function() {
            $scope.currentState = $scope.INSERT_STATE;
    	}
    	
    	$scope.showInsertPopup = function(ev) {
    	    $mdDialog.show({
    	      controller: 'CategoriaCompromissoDialogController',
    	      templateUrl: './modules/agenda/ui/categoria-compromisso/popup/popup-categoria-compromisso.html',
    	      targetEvent: ev,
    	      locals: {
    	    	  entidade: null
    	      }
    	    })
    	    .then(function(result) {
//    	    	$scope.saveCategoriaCompromisso(answer);
    	    	$scope.data.content.push(result);
    	    }, function() {
    	      
    	    });
    	  };
    	  
    	  $scope.showUpdatePopup = function(ev, entity) {
    		  $mdDialog.show({
    			  controller: 'CategoriaCompromissoDialogController',
    			  templateUrl: './modules/agenda/ui/categoria-compromisso/popup/popup-categoria-compromisso.html',
    			  targetEvent: ev,
    			  locals: {
    				  entidade: angular.copy(entity)
    			  }
    		  })
    		  .then(function(result) {
//    			  $scope.updateCategoriaCompromisso(answer);
    			  
    			  for (var int = 0; int < $scope.data.content.length; int++) {
						if ($scope.data.content[int].id == result.id) {
							$scope.data.content.splice(int, 1);
							$scope.data.content.push(result);
						}
					}
    			  
    			  $mdToast.show($mdToast.simple()
			                .content('Registro alterado com sucesso!')
			                .action('Fechar')
			                .highlightAction(false)
			                .position('top')).then();
    		  }, function() {
    			  
    		  });
    	  };
    	
    	$scope.listCategoriasCompromissos = function(){
    		compromissoService.listCategoriasCompromissos( {
        		callback: function(result){
        			$scope.data.content = result;
        			$scope.$apply();
        		},
        		errorHandler: function(message, error){
        			$log.error(message);
        		}
        	})
    	}
    	
    	
    	var DELETE_COLUMN_TEMPLATE = "<md-button ng-if=\"!row.entity.doSistema\" ng-click=\"showUpdatePopup($event, row.entity)\">Alterar</md-button> <md-button ng-if=\"!row.entity.doSistema\" ng-click=\"removerRegistro(row.entity)\">Excluír</md-button>";
    	
    	/**
    	 * 
    	 */
    	$scope.gridOptions = {
    		data: 'data.content',
    		columnDefs: [
    		    { displayName: "Descrição", field: "descricao" },
    		    { displayName: "Ações", cellTemplate: DELETE_COLUMN_TEMPLATE }
    		]
//    		enableColumnResize: false,
    	};
    	
    	$scope.saveCategoriaCompromisso = function(entity){
    		compromissoService.insertCategoriaCompromisso(entity.descricao, {
    			callback: function(result){
    				$scope.data.content.push(result);
    				$scope.$apply();
    			},
    			errorHandler: function(message, error){
    				$log.error("Erro ao salvar: ",message);
    			}
    		})
    	};
    	
    	/**
    	 * 
    	 */
    	$scope.updateCategoriaCompromisso = function(entity){
    		compromissoService.updateCategoriaCompromisso(entity, {
    			callback: function(result){
    				
    				for (var int = 0; int < $scope.data.content.length; int++) {
						if ($scope.data.content[int].id == result.id) {
							$scope.data.content[int] = result;
							$scope.$apply();
						}
					}
    				
    				$mdToast.show($mdToast.simple()
    		                .content('Registro alterado com sucesso!')
    		                .action('Fechar')
    		                .highlightAction(false)
    		                .position('top')).then();  
    				
    				
    			},
    			errorHandler: function(message, error){
    				$log.error("Erro ao salvar: ", message);
    			}
    		})
    	};
    	
    	$scope.removerRegistro = function(entity) {
    		
    		var confirm = $mdDialog.confirm()
            .title('Exclusão de Categoria de compromisso')
            .content('Tem certeza que deseja excluir o registro? Esta operação não poderá ser desfeita.')
            .ariaLabel('Exclusão de Categoria de Compromisso')
            .ok('Sim!')
            .cancel('Cancelar')
            .targetEvent();

            $mdDialog.show(confirm).then(function () {
    		
	    		var id = entity.id;
	    		compromissoService.removeCategoriaCompromisso(entity, {
	    			callback: function(result){
	    				for (var int = 0; int < $scope.data.content.length; int++) {
							if ($scope.data.content[int].id == id) {
								$scope.data.content.splice(int, 1);
							}
						}
	    				
	    				$mdToast.show($mdToast.simple()
	    		                .content('Registro excluído com sucesso!')
	    		                .action('Fechar')
	    		                .highlightAction(false)
	    		                .position('top')).then(function() {
	    		            });
	    				
	    				$scope.$apply();
	    				
	    				$state.go($scope.LIST_STATE, {});
	    			},
	    			errorHandler: function(message, execption) {
	    				$log.error("Erro ao excluir: ",message);
	    			}
	    		})
            });
    		
        	}

    }
).controller('CategoriaCompromissoDialogController', function( $scope, $mdDialog, entidade) {
	$scope.entidade = entidade == null ? {} : entidade;
	$scope.modoAlteracao = entidade != null ? true : false;
	
	$scope.salvar = function() {
		if ($scope.entidade) {
			
			if ($scope.modoAlteracao) {
				
				compromissoService.updateCategoriaCompromisso($scope.entidade, {
					callback: function(result){
						$mdDialog.hide(result);
					},
					errorHandler: function(message, error){
						$log.error("Erro ao salvar: ", message);
					}
				})
				
			} else {
				compromissoService.insertCategoriaCompromisso($scope.entidade.descricao, {
					callback: function(result){
						$mdDialog.hide(result);
						$scope.$apply();
					},
					errorHandler: function(message, error){
						$log.error("Erro ao salvar: ",message);
					}
				})
			}
			
			
		}
	}
	
	
	$scope.cancelar = function() {
		$mdDialog.cancel();
	}
});

}(window.angular));