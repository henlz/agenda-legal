<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="./webjars/angular-material/<spring:eval expression="@environment.getProperty('angular-material.version')"/>/angular-material.min.css">
<link rel="stylesheet" href="./webjars/ng-grid/<spring:eval expression="@environment.getProperty('ng-grid.version')"/>/ng-grid.min.css">
<link rel="stylesheet" href="./webjars/jquery-ui/<spring:eval expression="@environment.getProperty('jquery-ui.version')"/>/jquery-ui.min.css">
<link rel="stylesheet" href="./static/css/theme.css">
<link rel="stylesheet" href="./static/js/fullcalendar/fullcalendar.min.css">
<link rel="stylesheet" href="./static/font-icons/css/material-design-iconic-font.css">