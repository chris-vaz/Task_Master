<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit My Project</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body class="container mt-4">
	<div class="d-flex align-items-center justify-content-between">
		<h1>Edit Project</h1>
		<a href="/dashboard" class="btn btn-secondary">Dashboard</a>
	</div>
	<form:form method="POST" action="/projects/edit/${editProject.id}" modelAttribute="editProject" class="mt-5">
		<input type="hidden" name="_method" value="put" />
		
		<div>
			<form:label class="form-label" path="title">Project Title: </form:label>
			<form:input type="text" class="form-control" path="title"/>
			<form:errors path="title" class="text-danger"/>
		</div>
		<div>
			<form:label class="form-label" path="description">Project Description: </form:label>
			<form:textarea class="form-control" path="description"></form:textarea>
			<form:errors path="description" class="text-danger"/>
		</div>
		<div>
			<form:label class="form-label" path="dueDate">Project Date: </form:label>
			<form:input type="date" class="form-control" path="dueDate"/>
			<form:errors path="dueDate" class="text-danger"/>
		</div>
		<form:hidden path="lead"/>
		<input type="submit" class="btn btn-primary mt-3" value="Submit" />
	</form:form>
</body>
</html>