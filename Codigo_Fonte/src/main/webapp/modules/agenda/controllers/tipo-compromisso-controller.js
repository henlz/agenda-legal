(function (angular) {
    'use strict';


    angular.module('agenda').controller('TipoCompromissoController', function( $scope, $compile, $importService, $log, $state, $mdDialog, $mdToast) {
    	
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
    	$scope.LIST_STATE = "tipo-compromisso.listar";
    	
    	$scope.INSERT_STATE = "tipo-compromisso.inserir";
    	
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
        	
        	$scope.listTiposCompromissos();
    	}
    	
    	$scope.changeToInsert = function() {
            $scope.currentState = $scope.INSERT_STATE;
    	}
    	
    	$scope.showInsertPopup = function(ev) {
    	    $mdDialog.show({
    	      controller: 'TipoCompromissoDialogController',
    	      templateUrl: './modules/agenda/ui/tipo-compromisso/popup/popup-tipo-compromisso.html',
    	      targetEvent: ev,
    	      locals: {
    	    	  entidade: null
    	      }
    	    })
    	    .then(function(answer) {
    	    	$scope.saveTipoCompromisso(answer);
    	    }, function() {
    	      
    	    });
    	  };
    	  
    	  $scope.showUpdatePopup = function(ev, entity) {
    		  $mdDialog.show({
    			  controller: 'TipoCompromissoDialogController',
    			  templateUrl: './modules/agenda/ui/tipo-compromisso/popup/popup-tipo-compromisso.html',
    			  targetEvent: ev,
    			  locals: {
    				  entidade: angular.copy(entity)
    			  }
    		  })
    		  .then(function(answer) {
    			  $scope.updateTipoCompromisso(answer);
    		  }, function() {
    			  
    		  });
    	  };
    	
    	$scope.listTiposCompromissos = function(){
    		compromissoService.listTiposCompromissos( {
        		callback: function(result){
        			$scope.data.content = result;
        			$scope.$apply();
        		},
        		errorHandler: function(message, error){
        			$log.error(message);
        		}
        	})
    	}
    	
    	
    	var DELETE_COLUMN_TEMPLATE = "<md-button ng-click=\"showUpdatePopup($event, row.entity)\">Alterar</md-button> <md-button ng-click=\"removerRegistro(row.entity)\">Remover</md-button>";
    	
    	$scope.removerRegistro = function(entity) {
    		
    		var confirm = $mdDialog.confirm()
            .title('Exclusão de Tipo de compromisso')
            .content('Tem certeza que deseja excluir o registro? Esta operação não poderá ser desfeita.')
            .ariaLabel('Exclusão de Tipo de Compromisso')
            .ok('Sim!')
            .cancel('Cancelar')
            .targetEvent();

	        $mdDialog.show(confirm).then(function () {
	        	var id = entity.id;
	    		compromissoService.removeTipoCompromisso(entity.id, {
	    			callback: function(result){
	    				$mdToast.show($mdToast.simple()
	    		                .content('Registro excluído com sucesso!')
	    		                .action('Fechar')
	    		                .highlightAction(false)
	    		                .position('top')).then(function() {
	    		            });
	    				var y = -1;
	    				for (var int = 0; int < $scope.data.content.length; int++) {
							if ($scope.data.content[int].id == id) {
								y = int;
							}
						}
	    				
	    				$scope.data.content.splice(y, 1);
	    				$scope.$apply();
	    			},
	    			errorHandler: function(message, execption) {
	    				$log.error("Erro ao excluir: ",message);
	    			}
	    		})
	        });
    		
    	}
    	
    	/**
    	 * 
    	 */
    	$scope.gridOptions = {
    		data: 'data.content',
    		columnDefs: [
    		    { displayName: "Descrição", field: "descricao" },
    		    { displayName: "Ações", cellTemplate: DELETE_COLUMN_TEMPLATE }
    		],
    		multiSelect: false
    	};
    	
    	/**
    	 * 
    	 */
    	$scope.saveTipoCompromisso = function(entity){
    		compromissoService.insertTipoCompromisso(entity.descricao, {
    			callback: function(result){
    				$scope.data.content.push(result);
    				
    				$mdToast.show($mdToast.simple()
    		                .content('Registro salvo com sucesso!')
    		                .action('Fechar')
    		                .highlightAction(false)
    		                .position('top')).then(function() {
    		            });    				
    				
    				$scope.$apply();
    			},
    			errorHandler: function(message, error){
    				$log.error("Erro ao salvar: ", message);
    			}
    		})
    	};
    	
    	/**
    	 * 
    	 */
    	$scope.updateTipoCompromisso = function(entity){
    		compromissoService.updateTipoCompromisso(entity, {
    			callback: function(result){
    				$mdToast.show($mdToast.simple()
    		                .content('Registro alterado com sucesso!')
    		                .action('Fechar')
    		                .highlightAction(false)
    		                .position('top')).then();
    				
    				var y = -1;
    				for (var int = 0; int < $scope.data.content.length; int++) {
						if ($scope.data.content[int].id == result.id) {
							y = int;
						}
					}
    				
    				$scope.data.content.splice(y, 1)
    				$scope.data.content.push(result);
    				$scope.$apply();
    			},
    			errorHandler: function(message, error){
    				$log.error("Erro ao salvar: ", message);
    			}
    		})
    	};

    }
).controller('TipoCompromissoDialogController', function( $scope, $mdDialog, $mdToast, entidade) {
	$scope.entidade = entidade == null ? {} : entidade;
	$scope.modoAlteracao = entidade != null ? true : false;
	
	$scope.salvar = function() {
		if ($scope.validaForm()){
			$mdDialog.hide($scope.entidade);
		}
	}
	
	$scope.cancelar = function() {
		$mdDialog.cancel();
	}
	
	$scope.validaForm = function() {
        if (!$scope.tipoForm.$valid) {
            $mdToast.show($mdToast.simple()
                .content('Preencha todos os campos obrigatórios!')
                .action('Fechar')
                .highlightAction(false)
                .position('top')).then(function() {
            });

            return false;
        } else {
            return true;
        }
    }
});

}(window.angular));