<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" lang="pt" ng-init="initialize()">

<div ng-switch on="currentState">
	<div ng-switch-when="usuario.listar">
		<div ng-include="'./modules/agenda/ui/usuarios/usuario-list.html'"></div>
	</div>
	<div ng-switch-when="usuario.inserir">
		<div ng-include="'./modules/agenda/ui/usuarios/usuario-insert.html'"></div>
	</div>
	<div ng-switch-when="usuario.alterar">
		<div ng-include="'./modules/agenda/ui/usuarios/usuario-insert.html'"></div>
	</div>
	<div ng-switch-when="usuario.detalhe">
        <div ng-include="'./modules/agenda/ui/usuarios/usuario-detail.html'"></div>
	</div>
</div>


</html>