<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" lang="pt">

	<form name="authenticationForm" 
		layout="column" layout-align="center center">
			
		<span>Autenticação do Usuário</span>
      		
		<md-input-container style="width: 300px">
          	<label>Email:</label>
          	<input type="email" ng-model="user.email" placeholder="Entre com seu endereço de email" required>
       	</md-input-container>
	  	<md-input-container style="width: 300px">
          	<label>Senha:</label>
          	<input type="password" ng-model="user.password" placeholder="Entre com sua senha" required>
       	</md-input-container>
       	
       	<md-button class="md-raised md-primary" ng-click="signin(user)">Entrar</md-button>
	</form>
	
</html>