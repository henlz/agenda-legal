(function(window, angular, undefined) {
	'use strict';

	//Start the AngularJS
	var module = angular.module('agenda', ['ngMaterial', 'ui.router', 'eits-dwr-broker', 'ngGrid', 'ui.date']);

	module.config( function( $stateProvider, $urlRouterProvider, $importServiceProvider ) {
		//-------
		//Broker configuration
		//-------
		$importServiceProvider.setBrokerURL("./broker/interface");
		
		//-------
		//URL Router
		//-------

		//HOME
        $urlRouterProvider.otherwise("/agenda/listar");

        // Compromisso
        $stateProvider.state('agenda',{
			url : "/agenda",
			controller : 'CalendarioController',
			templateUrl : "./modules/agenda/ui/calendario/calendario-view.jsp"
		}).state('agenda.listar', {
			url: '/listar'
		}).state('agenda.inserir', {
			url: '/inserir'
		}).state('agenda.pesquisar', {
			url: '/pesquisar'
		}).state('agenda.alterar', {
			url: '/alterar/:id'
		}).state('agenda.detalhe', {
			url: '/detalhe/:id'
		});

        // Compromisso
        $stateProvider.state('usuario',{
			url : "/usuario",
			controller : 'UsuarioController',
			templateUrl : "./modules/agenda/ui/usuarios/usuario-view.jsp"
		}).state('usuario.listar', {
			url: '/listar'
		}).state('usuario.inserir', {
			url: '/inserir'
		}).state('usuario.alterar', {
			url: '/alterar/:id'
		}).state('usuario.detalhe', {
			url: '/detalhe/:id'
		});

        // Categoria de compromisso
		$stateProvider.state('categoria-compromisso',{
			url : "/categoria-compromisso",
			controller : 'CategoriaCompromissoController',
			templateUrl : "./modules/agenda/ui/categoria-compromisso/categoria-compromisso-view.jsp"
		}).state('categoria-compromisso.listar', {
			url: "/listar"
		}).state('categoria-compromisso.inserir', {
			url: "/inserir"
		});
		
		// Tipo de compromisso
		$stateProvider.state('tipo-compromisso',{
			url : "/tipo-compromisso",
			controller : 'TipoCompromissoController',
			templateUrl : "./modules/agenda/ui/tipo-compromisso/tipo-compromisso-view.jsp"
		}).state('tipo-compromisso.listar', {
			url: "/listar"
		}).state('tipo-compromisso.inserir', {
			url: "/inserir"
		});
		
		// Contatos
		$stateProvider.state('contatos',{
			url : "/contatos",
			controller : 'ContatosController',
			templateUrl : "./modules/agenda/ui/contatos/contatos-view.jsp"
		}).state('contatos.listar', {
			url: "/listar"
		}).state('contatos.inserir', {
			url: "/inserir"
		});

		// Contatos
		$stateProvider.state('logs',{
			url : "/logs",
			controller : 'LogsController',
			templateUrl : "./modules/agenda/ui/log/log-view.jsp"
		});

	});

	module.run( function( $rootScope, $window ) {
		
	});

	angular.element(document).ready( function() {
		angular.bootstrap( document, ['agenda']);
	});

})(window, window.angular);