<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" lang="pt" ng-init="initialize()">

	<md-content md-theme="docs-dark" class="md-padding" layout="column" layout-sm="column">
	    
	    <md-input-container>
	      <label>Descrição</label>
	      <input ng-model="currentEntity.descricao">
	    </md-input-container>
    
	</md-content>
	
	<md-button ng-click="saveCategoriaCompromisso()" class="md-raised md-primary">SALVAR</md-button>
	
</html>