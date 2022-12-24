<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Manager Dashboard</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body class="container mt-4">
	<div class="d-flex align-items-center justify-content-between">
		<h1>Welcome, <c:out value="${user.firstName}" />!</h1>
		<a href="/logout" class="btn btn-danger">Logout</a>
	</div>
	<div class="d-flex align-items-center justify-content-between mt-5">
		<h4>All Projects: </h4>
		<a href="/projects/new" class="btn btn-warning">+ new project</a>
	</div>
	<table class="table table-striped table-bordered mt-3">
		<thead>
			<tr>
				<th>Project</th>
				<th>Team Lead</th>
				<th>Due Date</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="project" items="${unassignedProjects}">
				<tr>
					<c:if test="${project.lead.id != user.id}">
						<td><a href="/projects/${project.id}">${project.title}</a></td>
						<td><c:out value="${project.lead.firstName}"></c:out></td>
						<td><fmt:formatDate value="${project.dueDate}" pattern="MMMM dd"/></td>
					    <td>
							<form method="POST" action="/dashboard/join/${project.id}">
					    		<input type="hidden" name="_method" value="put" />
					    		<input type="submit" value="Join Team" class="btn btn-dark"/>
					    	</form>
					    </td>
				    </c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h4 class="mt-5">Your Projects</h4>
	<table class="table table-striped table-bordered mt-3">
		<thead>
			<tr>
				<th>Project</th>
				<th>Lead</th>
				<th>Due Date</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="project" items="${assignedProjects}">
				<tr>
					<td><a href="/projects/${project.id}">${project.title}</a></td>
					<td><c:out value="${project.lead.firstName}"></c:out></td>
					<td><fmt:formatDate value="${project.dueDate}" pattern="MMMM dd"/></td>
					<c:if test="${project.lead.id==user.id}">
				       <td><a href="/projects/edit/${project.id}">Edit Project</a></td>
				    </c:if>
				    <c:if test="${project.lead.id!=user.id}">
				       <td>
				       		<form method="POST" action="/dashboard/leave/${project.id}">
					    		<input type="hidden" name="_method" value="put" />
					    		<input type="submit" value="Leave Team" class="btn btn-danger"/>
					    	</form>
				       </td>
		    		</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>