<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<section id="main">
	<c:if test="${computerAdded}">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<strong>Well done!</strong> You sucessfully add a computer.
		</div>
	</c:if>
	
	<c:if test="${computerEdited}">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<strong>Well done!</strong> You sucessfully edited a computer.
		</div>
	</c:if>
	
	<c:if test="${computerDeleted}">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<strong>Well done!</strong> You sucessfully delete a computer.
		</div>
	</c:if>
	
	<div class="container">
		<h1 id="homeTitle">${cw.results} computers found</h1>
		<div>
			<form action="computers" class="form-inline" method="GET">
				<div style="width: 100%;">
					<tags:boostrapPaginatorTag currentPage="${cw.currentPage}" totalPages="${cw.pages}" search="${cw.search}"  field="${cw.field}" isDesc="${cv.isDesc}"/>
					<input type="search" id="searchbox" class="form-control" name="search" value="" placeholder="Search name">
					<button type="submit" id="searchsubmit" class="btn btn-primary">Filter by name</button>
					<a class="btn btn-success" id="add" href="addComputer"  style="float: right;">Add Computer</a>
				</div>
			</form>
		</div>
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th><tags:link href="computers" content="Computer Name" page="${cw.currentPage}" search="${cw.search}" field="${cw.computerFields[1]}" isDesc="${cw.computerFields[1].name == cw.field.name? !cw.isDesc : cw.isDesc}" /></th>
					<th><tags:link href="computers" content="Introduced Date" page="${cw.currentPage}" search="${cw.search}" field="${cw.computerFields[2]}" isDesc="${cw.computerFields[2].name == cw.field.name? !cw.isDesc : cw.isDesc}" /></th>
					<th><tags:link href="computers" content="Discontinued Date" page="${cw.currentPage}" search="${cw.search}" field="${cw.computerFields[3]}" isDesc="${cw.computerFields[3].name == cw.field.name? !cw.isDesc : cw.isDesc}" /></th>
					<th><tags:link href="computers" content="Company" page="${cw.currentPage}" search="${cw.search}" field="${cw.computerFields[4]}" isDesc="${cw.computerFields[4].name == cw.field.name? !cw.isDesc : cw.isDesc}" /></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="computer" items="${cw.computers}">
					<tr>
						<td><a href="editComputer?id=${computer.id}">${computer.name}</a></td>
						<td><fmt:formatDate pattern="YYYY-MM-dd" value="${computer.introduced}" /></td>
						<td><fmt:formatDate pattern="YYYY-MM-dd" value="${computer.discontinued}" /></td>
						<td>${computer.company.name}</td>
						<td><a type="button" class="btn btn-danger" href="deleteComputer?id=${computer.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<tags:boostrapPaginatorTag currentPage="${cw.currentPage}" totalPages="${cw.pages}" search="${cw.search}" field="${cw.field}" isDesc="${cv.isDesc}"/>
	</div>
</section>

<jsp:include page="include/footer.jsp" />
