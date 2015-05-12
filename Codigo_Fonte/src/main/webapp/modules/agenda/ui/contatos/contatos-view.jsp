<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" lang="pt" ng-init="initialize()">


<md-content>
<h2>Amigos</h2>
<md-button style="margin: 10px 10px" class="md-raised md-primary" ng-click="showAddFriendPopup($event)">Adicionar amigo</md-button>
<div style="width:70%; height: 100px; margin: 0 auto;" ng-grid="friendsGridOptions"></div>
	
</md-content>

<md-divider/>

<md-content>

<h2>Contatos externos</h2>

<md-button style="margin: 10px 10px" class="md-raised md-primary" ng-click="showInsertPopup($event)">Novo contato</md-button>

<div style="width:70%; height: 100px; margin: 0 auto;" ng-grid="gridOptions"></div>

</md-content>
	
</div>
	
</html>