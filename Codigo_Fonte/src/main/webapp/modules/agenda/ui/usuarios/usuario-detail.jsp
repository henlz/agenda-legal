<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:ng="http://angularjs.org" lang="pt" ng-init="initialize()">
	<form id="formCompromisso" novalidate="true">
		<md-content md-theme="docs-dark" class="md-padding" layout="column" layout-sm="column">
	    	<md-content layout="row">
			    <md-input-container>
			      <label>Título</label>
			      <input name="titulo" ng-model="currentEntity.titulo" disabled>
			    </md-input-container>
			    
			    <md-input-container>
			      <label>Descrição</label>
			      <input ng-model="currentEntity.descricao" disabled>
			    </md-input-container>
			    
			    <md-input-container>
			      <label>Observações</label>
			      <input ng-model="currentEntity.observacoes" disabled>
			    </md-input-container>
		    </md-content>
		    
		    <md-content layout="row">
		    	<md-input-container>
			      <label>Data Início</label>
			      <input name="dataInicio" ng-model="currentEntity.dataInicio" ui-date="dateOptions" disabled>
			    </md-input-container>
			    <md-input-container>
			    	<label>Hora</label>
			    	<input ng-model="currentEntity.dataInicio" type="time" disabled>
			    </md-input-container>
		    </md-content>
		    
		    <md-content layout="row">
		    	<md-input-container>
			      <label>Data Fim</label>
			      <input name="dataFim" ng-model="currentEntity.dataFim" ui-date="dateOptions" disabled>
			    </md-input-container>
			    <md-input-container>
			    	<label>Hora</label>
			    	<input ng-model="currentEntity.dataFim" type="time" disabled>
			    </md-input-container>
		    </md-content>
		    
		    <md-content layout-align="center center">
			    <md-select placeholder="Frequência" ng-model="currentEntity.frequencia" disabled>
				    <md-option value="UMA_VEZ">Uma vez</md-option>
				    <md-option value="DIARIAMENTE">Diariamente</md-option>
				    <md-option value="SEMANALMENTE">Semanalmente</md-option>
				    <md-option value="MENSALMENTE">Mensalmente</md-option>
				    <md-option value="ANUALMENTE">Anualmente</md-option>
			  	</md-select>
			  	
			    <md-select placeholder="Status" ng-model="currentEntity.status" disabled>
				    <md-option value="DISPONIVEL">Disponível</md-option>
				    <md-option value="OCUPADO">Ocupado</md-option>
			  	</md-select>
			  	
			    <md-select placeholder="Categoria de compromisso" ng-model="currentEntity.categoriaCompromisso" disabled>
				    <md-option ng-repeat="categoria in listaCategorias" ng-value="categoria" ng-selected="categoria.id == currentEntity.categoriaCompromisso.id">{{::categoria.descricao}}</md-option>
			  	</md-select>
				  			  	
			    <md-select placeholder="Tipo de compromisso" ng-model="currentEntity.tipoCompromisso" disabled>
				    <md-option ng-repeat="tipo in listaTipos" ng-value="tipo" ng-selected="tipo.id == currentEntity.tipoCompromisso.id">{{::tipo.descricao}}</md-option>
			  	</md-select>
			  	
		  	</md-content>
		</md-content>
		
		<md-button ng-if="currentEntity.shared == true" ng-click="removeSharedCompromisso($event)" class="md-accent md-raised">cancelar compartilhamento</md-button>
		<md-button ng-if="currentEntity.shared != true" ui-sref="agenda.alterar({id:currentEntity.id})" class="md-primary md-raised">editar</md-button>
		<md-button ng-if="currentEntity.shared != true" ng-click="showSharePopup($event)" class="md-accent md-raised">compartilhar compromisso</md-button>
		<md-button ui-sref="agenda.listar" class="md-raised">VOLTAR</md-button>
		
	</form>
	
</html>