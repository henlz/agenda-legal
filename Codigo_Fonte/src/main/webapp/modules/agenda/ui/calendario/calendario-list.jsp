<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" lang="pt" ng-init="initialize()">
	<md-content>
		<div>
			<md-button style="margin: 10px 10px" class="md-fab md-primary" ui-sref="agenda.inserir"><i class="md-icon md-icon-add"></i></md-button>
			<md-button style="margin: 10px 10px" class="md-fab" ui-sref="agenda.pesquisar"><i class="md-icon md-icon-search"></i></md-button>
		</div>

		<br>

		<div id="calendarElement" class="span8 calendar"></div>
	</md-content>
	
</html>