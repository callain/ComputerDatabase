<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
	<title>EPF Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<link href="/ComputerDatabase/resources/css/main.css" rel="stylesheet" media="screen">

	<script type="text/javascript" src="/ComputerDatabase/resources/js/jquery-2.1.0.min.js"></script>
	
	<!--  JQuery Validation Plugin -->
	<script type="text/javascript" src="/ComputerDatabase/resources/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="/ComputerDatabase/resources/js/validation.js"></script>
	<script type="text/javascript" src="/ComputerDatabase/resources/js/messages_fr.js"></script>

	<script type="text/javascript" src="/ComputerDatabase/resources/js/jquery-ui-1.10.4.custom.min.js"></script>
	<link href="/ComputerDatabase/resources/css/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" media="screen">
	<script src="/ComputerDatabase/resources/js/jquery.ui.datepicker-fr.js"></script>
	
	<!-- Bootstrap -->
	<link href="/ComputerDatabase/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<script type="text/javascript" src="/ComputerDatabase/resources/js/bootstrap.min.js"></script>
	
</head>
<body>
	<header class="navbar">
		<h1 class="fill">
			<a href="/ComputerDatabase/computers"> Application - Computer Database </a>
			<spring:message code="welcome" />${pageContext.request.userPrincipal.name}
			<sec:authorize access="isAuthenticated()">
				<a href="j_spring_security_logout" style="color: red;"><span class="glyphicon glyphicon-off"></span></a>
			</sec:authorize>
			<span style="float: right;">
				<a href="?language=fr_FR" ><img src="/ComputerDatabase/resources/img/FR.gif" /></a>
				<a href="?language=en_US" ><img src="/ComputerDatabase/resources/img/USA.png" /></a>
			</span>
		</h1>
	</header>