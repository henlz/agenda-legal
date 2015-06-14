<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" lang="pt" ng-init="initialize()">

<div ng-switch on="currentState">
	<div ng-switch-when="agenda.listar">
		<div ng-include="'./modules/agenda/ui/calendario/calendario-list.jsp'"></div>
	</div>
	<div ng-switch-when="agenda.pesquisar">
		<div ng-include="'./modules/agenda/ui/calendario/calendario-search.html'"></div>
	</div>
	<div ng-switch-when="agenda.inserir">
		<div ng-include="'./modules/agenda/ui/calendario/calendario-insert.html'"></div>
	</div>
	<div ng-switch-when="agenda.alterar">
		<div ng-include="'./modules/agenda/ui/calendario/calendario-insert.html'"></div>
	</div>
	<div ng-switch-when="agenda.detalhe">
		<div ng-include="'./modules/agenda/ui/calendario/calendario-detail.jsp'"></div>
	</div>
</div>
        
	
</html>