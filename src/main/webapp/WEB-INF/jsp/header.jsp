<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<html>
<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
	<script src="<spring:url value="/resources/javascripts/rest.js"/>"></script>
</head>
<body>
	<img src="<spring:url value="/resources/images/yoan-logo.png"/>"
		width=10% />
	<h4 id="welcomeMessage"></h4>
	<script>getLoginInfo()</script>
</body>
</html>