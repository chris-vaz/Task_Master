<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Task Page</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body class="container mt-4">
	<div class="d-flex align-items-center justify-content-between">
		<h1>Project: <c:out value="${project.title}" /></h1>
		<a href="/dashboard" class="btn btn-secondary">Dashboard</a>
	</div>
	<p class="mt-5">Project Lead: <c:out value="${project.lead.firstName}" /></p>
	<form:form action="/projects/${project.id}/tasks" method="post" modelAttribute="task">
		<div>
			<form:label class="form-label" path="name">Project Description: </form:label>
			<form:textarea class="form-control" path="name"></form:textarea>
			<form:errors path="name" class="text-danger"/>
		</div>
		<input type="submit" class="btn btn-primary mt-3" value="Submit" />
	</form:form>
	
	<c:forEach var="task" items="${tasks}">
		<h4 class="mt-5">Added by <c:out value="${task.creator.firstName}"></c:out> at <fmt:formatDate value="${task.createdAt}" pattern="h:mm a MMMM dd"/>:</h4>
		<p><c:out value="${task.name}"></c:out></p>
	</c:forEach>
</body>
</html>