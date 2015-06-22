<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" lang="pt" ng-init="initialize()">

<md-content layout-padding>
	<md-subheader>Log de eventos</md-subheader>
	<md-divider></md-divider>
	<md-list>
	<md-list-item ng-repeat="log in logs track by $index">
		<p>{{::log.created | date: 'HH:mm'}} - {{::log.log}}</p>
		<md-divider ng-if="!$last"></md-divider>
	</md-list-item>

	</md-list>
	</md-content>


</html>