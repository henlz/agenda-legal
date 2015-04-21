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
		<title>Autenticação do Usuário</title>
		
		<!-- Styles -->
		<jsp:include page="/modules/default-styles.jsp"/>
		
		<!-- Scripts -->
		<jsp:include page="/modules/default-scripts.jsp"/>
		
		<!-- Main -->
		<script type="text/javascript" src="./modules/authentication/authentication-main.js?v=${version}"/></script>
		
		<!-- Controllers -->
		<script type="text/javascript" src="./modules/authentication/controllers/signin-controller.js?v=${version}"></script>
	</head>
	
	<body layout-align="center" layout="row">
	
		<!-- Main Panel -->
	    <div layout-align="center center" layout="column">
	    	
<!-- 	    	<img src="https://s3.amazonaws.com/www.eits.com.br/logo-email.png" alt="eits it solutions"/> -->
			
		    <ui-view/>
		    
		</div>
		
	</body>
</html>