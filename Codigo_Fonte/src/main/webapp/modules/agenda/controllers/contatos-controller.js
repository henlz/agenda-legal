(function (angular) {
    'use strict';


    angular.module('agenda').controller('ContatosController', function ($scope, $compile, $importService, $log, $state, $mdDialog, $mdToast) {

            /**
             *
             */
            $importService("compromissoService");
            $importService("accountService");

            /**
             *
             */
            $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
//            $log.info("$stateChangeSuccess");

                $scope.initialize(toState, toParams, fromState, fromParams);
            });

            $scope.currentEntity = {};

            $scope.data = {};

            $scope.usuarioAtual = {};

            /**
             *
             */
            $scope.initialize = function (toState, toParams, fromState, fromParams) {

                $scope.listContatos();
                $scope.listFriends();

            }

            $scope.changeToList = function () {
                $scope.currentState = $scope.LIST_STATE;

                var pageRequest = new PageRequest();
                pageRequest.size = 10;
                $scope.pageRequest = pageRequest;

                $scope.listCategoriasCompromissos();
            }

            $scope.changeToInsert = function () {
                $scope.currentState = $scope.INSERT_STATE;
            }
            
            /**
             * 
             */
            $scope.showAddFriendPopup = function (ev) {
                $mdDialog.show({
                    controller: 'AddFriendDialogController',
                    templateUrl: './modules/agenda/ui/contatos/popup/popup-add-friend.html',
                    targetEvent: ev
                })
                    .then(function (result) {
//                        $scope.saveContato($scope.usuarioAtual.id, result);
                    }, function () {
                    });
            };

            /**
             * 
             */
            $scope.showInsertPopup = function (ev) {
                $mdDialog.show({
                    controller: 'ContatosDialogController',
                    templateUrl: './modules/agenda/ui/contatos/popup/popup-contatos.html',
                    targetEvent: ev,
                    locals: {
                        entidade: null
                    }
                })
                    .then(function (result) {
                        $scope.saveContato($scope.usuarioAtual.id, result);
                    }, function () {
                    });
            };

            $scope.showUpdatePopup = function (ev, entity) {
                $mdDialog.show({
                    controller: 'ContatosDialogController',
                    templateUrl: './modules/agenda/ui/contatos/popup/popup-contatos.html',
                    targetEvent: ev,
                    locals: {
                        entidade: angular.copy(entity)
                    }
                })
                    .then(function (result) {
                        $scope.updateContato($scope.usuarioAtual.id, result);
                    }, function () {

                    });
            };

            $scope.listContatos = function () {
                accountService.getCurrentUser({
                    callback: function (result) {
                        $scope.usuarioAtual = result;
                        $scope.data.content = result.contatos;
                        $scope.$apply();
                    },
                    errorHandler: function (message, error) {
                        $log.error(message);
                    }
                })
            }

            $scope.listFriends = function() {
                accountService.getFriends( {
                    callback: function (result) {
                        
                        $scope.data.friends = [];
                        
                        for (var i = 0; i < result.length; i++) {
                            console.log(result[i]);
                            
                            var status = null;
                            
                            if (result[i].status == "PENDENTE") status = 'Pendente';
                            if (result[i].status == "ACEITA") status = 'Amigo';
                            if (result[i].status == "RECUSADO") status = 'Recusado';
                            
                            $scope.data.friends.push({
                                id: result[i].id,
                                name: $scope.usuarioAtual.id == result[i].usuarioOrigem.id ? result[i].usuarioDestino.name : result[i].usuarioOrigem.name,
                                status: status
                            });
                        }
                        
                        $scope.$apply();
                    },
                    errorHandler: function (message, error) {
                        $log.error(message);
                    }
                })
            }
            
            /**
             * 
             */
            $scope.aceitarSolicitacaoAmigo = function(id) {
                accountService.acceptFriendRequest(id, {
                    callback: function (result) {
                        for (var i = 0; i < $scope.data.friends.length; i++) {
                            var element = $scope.data.friends[i];
                            if (element.id == result.id) element = result;
                        }
                        
                        $mdToast.show($mdToast.simple()
                            .content('Solicitação aceita!')
                            .action('Fechar')
                            .highlightAction(false)
                            .position('top')).then();
                        
                        $scope.$apply();
                    }, errorHandler: function ( message, exception ) {
                        $log.error(message);
                    }
                })
            }
            
            $scope.removerAmigo = function(id) {
                accountService.removeFriend(id, {
                    callback: function() {
                        for (var i = 0; i < $scope.data.friends.length; i++) {
                            var element = $scope.data.friends[i];
                            if (element.id == id) {
                                $scope.data.friends.splice(i, 1);
                            }
                        }
                        
                        $scope.$apply();
                    }
                })
            }

            var DELETE_COLUMN_TEMPLATE = "<md-button ng-click=\"showUpdatePopup($event, row.entity)\">Alterar</md-button> <md-button ng-click=\"removerRegistro(row.entity)\">Excluír</md-button>";

            /**
             *
             */
            $scope.gridOptions = {
                data: 'data.content',
                columnDefs: [
                    {displayName: "Descrição", field: "descricao"},
                    {displayName: "Telefone", field: "telefone"},
                    {displayName: "Email", field: "email"},
                    {displayName: "Ações", width: '20%', cellTemplate: DELETE_COLUMN_TEMPLATE}
                ]
            };
            
            var MANAGE_FRIEND_COLUMN_TEMPLATE = "<md-button ng-if=\"row.entity.status == 'Pendente'\" ng-click=\"aceitarSolicitacaoAmigo(row.entity.id)\">Aceitar</md-button> <md-button ng-if=\"row.entity.status == 'Pendente'\" ng-click=\"recusarSolicitacao(row.entity)\">Recusar</md-button> <md-button ng-if=\"row.entity.status == 'Amigo'\" ng-click=\"removerAmigo(row.entity.id)\">Excluir</md-button>";
            
            /**
             *
             */
            $scope.friendsGridOptions = {
                data: 'data.friends',
                columnDefs: [
                    {displayName: "Amigo", field: "name"},
                    {displayName: "Status", field: "status"},
                    {displayName: "Ações", width: '20%', cellTemplate: MANAGE_FRIEND_COLUMN_TEMPLATE}
                ]
            };

            $scope.saveContato = function (id, entity) {
                accountService.insertContactToUser(id, entity, {
                    callback: function (result) {
                        $scope.usuarioAtual = result;
                        $scope.data.content = result.contatos;
                        
                        $mdToast.show($mdToast.simple()
                            .content('Registro alterado com sucesso!')
                            .action('Fechar')
                            .highlightAction(false)
                            .position('top')).then();
                        
                        $scope.$apply();
                    },
                    errorHandler: function (message, error) {
                        $log.error("Erro ao salvar: ", message);
                    }
                })
            };

            /**
             *
             */
            $scope.updateContato = function (id, entity) {
                accountService.updateContactToUser(id, entity, {
                    callback: function (result) {

                        $scope.usuarioAtual = result;
                        $scope.data.content = result.contatos;

                        $mdToast.show($mdToast.simple()
                            .content('Registro alterado com sucesso!')
                            .action('Fechar')
                            .highlightAction(false)
                            .position('top')).then();
                    },
                    errorHandler: function (message, error) {
                        $log.error("Erro ao salvar: ", message);
                    }
                })
            };

            /**
             * 
             */
            $scope.removerRegistro = function (entity) {

                var confirm = $mdDialog.confirm()
                    .title('Exclusão de Categoria de compromisso')
                    .content('Tem certeza que deseja excluir o registro? Esta operação não poderá ser desfeita.')
                    .ariaLabel('Exclusão de Categoria de Compromisso')
                    .ok('Sim!')
                    .cancel('Cancelar')
                    .targetEvent();

                $mdDialog.show(confirm).then(function () {

                    var id = entity.id;
                    accountService.removeContactFromUser($scope.usuarioAtual.id, entity, {
                        callback: function () {
                            for (var int = 0; int < $scope.data.content.length; int++) {
                                if ($scope.data.content[int].id == id) {
                                    $scope.data.content.splice(int, 1);
                                }
                            }

                            $mdToast.show($mdToast.simple()
                                .content('Registro excluído com sucesso!')
                                .action('Fechar')
                                .highlightAction(false)
                                .position('top')).then(function () {
                            });

                            $scope.$apply();

                            $state.go($scope.LIST_STATE, {});
                        },
                        errorHandler: function (message, execption) {
                            $log.error("Erro ao excluir: ", message);
                        }
                    })
                });

            }

        }
    ).controller('ContatosDialogController', function ($scope, $mdDialog, entidade) {
            $scope.entidade = entidade == null ? new Contato() : entidade;
            $scope.modoAlteracao = entidade != null ? true : false;

            $scope.salvar = function () {
                if ($scope.entidade) {
                    $mdDialog.hide($scope.entidade);
                }
            }


            $scope.cancelar = function () {
                $mdDialog.cancel();
            }
            
        }).controller('AddFriendDialogController', function ($scope, $mdDialog, $importService, $mdToast) {
            
            $importService("accountService");
            
            $scope.campo = null;

            $scope.enviar = function () {
                if ($scope.campo) {
                    accountService.sendFriendRequest($scope.campo, {
                        callback: function(result) {
                            $mdDialog.hide(true);
                            $mdToast.show($mdToast.simple()
                                .content("Solicitação enviada!")
                                .action('Fechar')
                                .highlightAction(false)
                                .position('top')).then(function () {
                            });
                        },
                        errorHandler: function(message, error){
                            $mdToast.show($mdToast.simple()
                                .content(message)
                                .action('Fechar')
                                .highlightAction(false)
                                .position('top')).then(function () {
                            });
                        }
                    })
                    
                    
                }
            }
            
            $scope.cancelar = function () {
                $mdDialog.cancel();
            }
        });

}(window.angular));