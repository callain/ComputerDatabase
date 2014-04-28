<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<section id="main">
	<c:if test="${computerAdded}">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<spring:message code="add.computer.successful" />
		</div>
	</c:if>
	
	<c:if test="${computerEdited}">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<spring:message code="edit.computer.successful" />
		</div>
	</c:if>
	
	<c:if test="${computerDeleted}">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<spring:message code="delete.computer.successful" />
		</div>
	</c:if>
	
	<spring:message code="date.pattern.joda" var="datePattern" />
	
	<div class="container">
		<h1 id="homeTitle">${cw.results} <spring:message code="computer.found" /></h1>
		<div>
			<form action="computers" class="form-inline" method="GET">
				<div style="width: 100%;">
					<tags:boostrapPaginatorTag currentPage="${cw.currentPage}" totalPages="${cw.pages}" search="${cw.search}"  field="${cw.field}" isDesc="${cw.isDesc}"/>
					<input type="search" id="searchbox" class="form-control" name="search" value="" placeholder="<spring:message code="search.name" />">
					<button type="submit" id="searchsubmit" class="btn btn-primary"><spring:message code="filter.name" /></button>
					<sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
						<a class="btn btn-success" id="add" href="addComputer"  style="float: right;"><spring:message code="add.computer" /></a>
					</sec:authorize>
				</div>
			</form>
		</div>
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th><tags:link href="computers" page="${cw.currentPage}" search="${cw.search}" field="${cw.computerFields[1]}" isDesc="${cw.computerFields[1].name == cw.field.name? !cw.isDesc : cw.isDesc}"><spring:message code="computer.name" /></tags:link></th>
					<th><tags:link href="computers" page="${cw.currentPage}" search="${cw.search}" field="${cw.computerFields[2]}" isDesc="${cw.computerFields[2].name == cw.field.name? !cw.isDesc : cw.isDesc}"><spring:message code="computer.introduced" /></tags:link></th>
					<th><tags:link href="computers" page="${cw.currentPage}" search="${cw.search}" field="${cw.computerFields[3]}" isDesc="${cw.computerFields[3].name == cw.field.name? !cw.isDesc : cw.isDesc}"><spring:message code="computer.discontinued" /></tags:link></th>
					<th><tags:link href="computers" page="${cw.currentPage}" search="${cw.search}" field="${cw.computerFields[4]}" isDesc="${cw.computerFields[4].name == cw.field.name? !cw.isDesc : cw.isDesc}"><spring:message code="computer.company" /></tags:link></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="computer" items="${cw.computers}">
					<tr>
						<td><a href="editComputer?id=${computer.id}">${computer.name}</a></td>
						<td><joda:format value="${computer.introduced}" pattern="${datePattern}"/></td>
						<td><joda:format value="${computer.discontinued}" pattern="${datePattern}"/></td>
						<td>${computer.company.name}</td>
						<sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
							<td><a type="button" class="btn btn-danger" href="deleteComputer?id=${computer.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
						</sec:authorize>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<tags:boostrapPaginatorTag currentPage="${cw.currentPage}" totalPages="${cw.pages}" search="${cw.search}" field="${cw.field}" isDesc="${cw.isDesc}"/>
	</div>
</section>

<jsp:include page="include/footer.jsp" />
