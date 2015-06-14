(function (angular) {
    'use strict';


    angular.module('agenda').controller('UsuarioController', function ($scope, $compile, $importService, $log, $state, $mdToast, $mdDialog) {

            /**
             *
             */
            $importService("accountService");

            /**
             *
             */
            $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
                $scope.initialize(toState, toParams, fromState, fromParams);
            });

            /**
             *
             * @type {string}
             */
            $scope.LIST_STATE = "usuario.listar";

            $scope.INSERT_STATE = "usuario.inserir";

            $scope.DETAIL_STATE = "usuario.detalhe";

            $scope.UPDATE_STATE = "usuario.alterar";

            /**
             *
             * @type {{}}
             */
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

            /**
             *
             */
            $scope.changeToList = function () {
                $scope.currentState = $scope.LIST_STATE;

                var pageRequest = new PageRequest();
                $scope.pageRequest = pageRequest;

                $scope.listUsuarios();
            }

            /**
             *
             */
            $scope.changeToInsert = function () {
                $scope.currentState = $scope.INSERT_STATE;
            }

            /**
             *
             * @param id
             */
            $scope.changeToDetail = function (id) {
                $log.info("changeToDetail", id);

                if (id == null || id == "" || id == 0) {
                    $scope.msg = {type: "error", text: $scope.INVALID_ID_MESSAGE, dismiss: true};
                    $scope.currentState = $scope.LIST_STATE;
                    $state.go($scope.LIST_STATE);
                    return;
                }

                //compromissoService.findCompromissoById(id, {
                //    callback: function (result) {
                //        $scope.currentEntity = result;
                //        $scope.currentState = $scope.DETAIL_STATE;
                //        $state.go($scope.DETAIL_STATE, {id: id});
                //        $scope.$apply();
                //
                //        $scope.listCategoriasCompromissos();
                //        $scope.listTiposCompromissos();
                //    },
                //    errorHandler: function (message, exception) {
                //        $scope.msg = {type: "danger", text: message, dismiss: true};
                //        $scope.$apply();
                //    }
                //});
            };

            /**
             *
             * @param id
             */
            $scope.changeToUpdate = function (id) {
                $log.info("changeToUpdate", id);

                accountService.findUserById(id, {
                    callback: function (result) {
                        $scope.currentEntity = result;
                        $scope.currentState = $scope.UPDATE_STATE;
                        $state.go($scope.UPDATE_STATE, {id: id});
                        $scope.$apply();
                    },
                    errorHandler: function (message, exception) {
                        $log.error(message);
                    }
                });
            };

            /**
             *
             */
            $scope.listUsuarios = function () {
                accountService.listUsuarios({
                    callback: function (result) {
                        $scope.listaUsuarios = result;
                        $scope.$apply();
                    },
                    errorHandler: function (message, error) {
                        $log.error(message);
                    }
                })
            }

            /**
             *
             * @type {string}
             */
            $scope.USUARIOS_COLUNA_ACOES =
                "<div>" +
                "<md-button class=\"md-icon-button\" ui-sref=\"usuario.alterar({id: row.entity.id})\"><i class=\"md-icon md-icon-edit\"></i></md-button>" +
                    //"<md-button class=\"md-icon-button\" ng-click=\"excluirUsuario(row.entity)\"><i class=\"md-icon md-icon-delete\"></i></md-button>" +
                "<md-button ng-if=\"row.entity.enabled == false\" class=\"md-icon-button\" ng-click=\"changeUserStatus(row.entity, true)\"><i class=\"md-icon md-icon-check\"></i></md-button>" +
                "<md-button ng-if=\"row.entity.enabled == true\" class=\"md-icon-button\" ng-click=\"excluirUsuario(row.entity, false)\"><i class=\"md-icon md-icon-clear\"></i></md-button>" +
                "</div>";

            /**
             *
             * @param entity
             */
            $scope.changeUserStatus = function (entity, status) {
                accountService.changeUserStatus(entity.id, status, {
                    callback: function (result) {
                        entity = result;
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
            $scope.gridOptions = {
                data: 'listaUsuarios',
                columnDefs: [
                    {displayName: "Nome", field: "name", width: "40%"},
                    {displayName: "E-mail", field: "email", width: "35%"},
                    {displayName: "Status", field: "enabled", width: "10%"},
                    {displayName: "Ações", width: "20%", cellTemplate: $scope.USUARIOS_COLUNA_ACOES}
                ],
                jqueryUITheme: true
            };

            $scope.validaForm = function () {

                //if (($scope.currentEntity.titulo == null || $scope.currentEntity.titulo == undefined)
                //    || ($scope.currentEntity.dataInicio == null || $scope.currentEntity.dataInicio == undefined)
                //    || ($scope.currentEntity.dataFim == null || $scope.currentEntity.dataFim == undefined)) {
                //    var toast = $mdToast.simple()
                //        .content("Preencha todos os campos obrigatórios.")
                //        .action('Fechar')
                //        .highlightAction(false)
                //        .position('bottom');
                //
                //    $mdToast.show(toast);
                //
                //    return false;
                //}
                return true;
            }

            $scope.insertUsuario = function () {

                if ($scope.validaForm()) {
                    $scope.currentEntity.enabled = false;
                    accountService.insertUser($scope.currentEntity, {
                        callback: function (result) {
                            $state.go($scope.LIST_STATE);
                        },
                        errorHandler: function (message, error) {
                            $log.error("Erro ao salvar: ", message);
                        }
                    })
                }
            };

            $scope.updateUsuario = function () {

                if ($scope.validaForm) {
                    accountService.updateUser($scope.currentEntity, {
                        callback: function (result) {
                            $state.go($scope.LIST_STATE, {});
                        },
                        errorHandler: function (message, error) {
                            $log.error("Erro ao salvar: ", message);
                        }
                    })
                }
            };

            $scope.removeUsuario = function () {

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
                            callback: function (result) {
                                $state.go($scope.LIST_STATE, {});

                                $mdToast.show($mdToast.simple()
                                    .content('Compromisso excluído com sucesso!')
                                    .action('Fechar')
                                    .highlightAction(false)
                                    .position('top')).then(function () {
                                });
                            },
                            errorHandler: function (message, error) {
                                $log.error("Erro ao excluir: ", message);
                            }
                        })
                    }
                });
            };

        }
    ).controller('ShareDialogController', function ($scope, $mdDialog, $importService, $mdToast, compromisso) {

            $importService("compromissoService");
            $importService("accountService");

            $scope.campo = null;

            $scope.friendsGridOptions = {}

            accountService.getFriendsList({
                callback: function (result) {
                    $scope.friends = result;
                },
                errorHandler: function (message, error) {
                    console.log(message);
                }
            });

            $scope.enviar = function (friend) {

                compromissoService.shareCompromissoWithUser(compromisso, friend, {
                    callback: function (result) {
                        $mdDialog.hide(true);
                        $mdToast.show($mdToast.simple()
                            .content("Compromisso compartilhado!")
                            .action('Fechar')
                            .highlightAction(false)
                            .position('top')).then(function () {
                        });
                    },
                    errorHandler: function (message, error) {
                        $mdToast.show($mdToast.simple()
                            .content(message)
                            .action('Fechar')
                            .highlightAction(false)
                            .position('top')).then(function () {
                        });
                    }
                });


            };

            $scope.cancelar = function () {
                $mdDialog.cancel();
            };
        });

}(window.angular));