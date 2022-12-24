<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Details</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body class="container mt-4">
	<div class="d-flex align-items-center justify-content-between">
		<h1>Project Details</h1>
		<a href="/dashboard" class="btn btn-secondary">Dashboard</a>
	</div>
	<div class="mt-5">
		<p><span class="bold">Project: </span><c:out value="${project.title}" /></p>
		<p><span class="bold">Description: </span><c:out value="${project.description}" /></p>
		<p><span class="bold">Due Date: </span><fmt:formatDate value="${project.dueDate}" pattern="MMMM dd"/></p>
	</div>
	<h5><a href="/projects/${project.id}/tasks">See Tasks</a></h5>
	<c:if test="${project.lead.id == userId}">
		<form method="POST" action="/projects/delete/${project.id}">
			<input type="hidden" name="_method" value="delete">
	    	<input type="submit" value="Delete" class="btn btn-danger mt-3">
		</form>
	</c:if>
</body>
</html>