<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" lang="pt">

	<head>
		<title>Agenda Legal</title>


		<meta charset="UTF-8">
		<!-- Styles -->
		<jsp:include page="/modules/default-styles.jsp"/>

		<!-- EITS Bottom Sheet -->
		<link rel="stylesheet" type="text/css" href="./static/js/eits-bottomsheet/eits-bottomsheet.css">

		<!-- Scripts -->
		<jsp:include page="/modules/default-scripts.jsp"/>

		<!-- Main -->
		<script type="text/javascript" src="./modules/agenda/agenda-main.js?v=${version}"/></script>

		<!-- Custom Directive -->
		<script type="text/javascript" src="./static/js/eits-bottomsheet/eits-bottomsheet.js?v=${version}"></script>

		<!-- Controllers -->
		<script type="text/javascript" src="./modules/abstract-crud-controller.js?v=${version}"></script>
	  	<script type="text/javascript" src="./modules/agenda/controllers/calendario-controller.js?v=${version}"></script>
	  	<script type="text/javascript" src="./modules/agenda/controllers/categoria-compromisso-controller.js?v=${version}"></script>
	  	<script type="text/javascript" src="./modules/agenda/controllers/tipo-compromisso-controller.js?v=${version}"></script>
	  	<script type="text/javascript" src="./modules/agenda/controllers/contatos-controller.js?v=${version}"></script>

	</head>

	<body>

		<md-toolbar class="md-tall">
			<h2 class="md-toolbar-tools">
				<span style="color:white;">Agenda Legal</span>
			</h2>

			<section layout="row" layout-sm="column">
		        <md-button ui-sref="agenda.listar">Calendário</md-button>
		        <md-button ui-sref="categoria-compromisso.listar">Categoria de compromisso</md-button>
                <md-button ui-sref="tipo-compromisso.listar">Tipo de compromisso</md-button>
                <md-button ui-sref="contatos.listar">Contatos</md-button>
				<md-button><a href="logout" > Logout</a></md-button>
		    </section>
		</md-toolbar>

		<ui-view/>

	</body>
</html>
