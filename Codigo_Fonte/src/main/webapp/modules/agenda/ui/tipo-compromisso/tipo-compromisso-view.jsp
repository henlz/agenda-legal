<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" lang="pt" ng-init="initialize()">

<div ng-switch on="currentState">
	<div ng-switch-when="tipo-compromisso.listar">
		<div ng-include="'./modules/agenda/ui/tipo-compromisso/tipo-compromisso-list.jsp'"></div>
	</div>
	<div ng-switch-when="tipo-compromisso.inserir">
		<div ng-include="'./modules/agenda/ui/tipo-compromisso/tipo-compromisso-insert.jsp'"></div>
	</div>
</div>
        
	
</html>