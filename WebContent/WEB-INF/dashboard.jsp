<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/boostrapPaginator.tld" prefix="bootstrap" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<section id="main">
	<h1 id="homeTitle">${nbComputers} computers found</h1>
	<c:if test="${add}">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<strong>Well done!</strong>You sucessfully add a computer.
		</div>
	</c:if>
	<div id="actions">
		<form class="form-inline" action="" method="GET">
			<div class="form-group">
				<input type="search" id="searchbox" class="form-control" name="search" value="" placeholder="Search name">
				<button type="submit" id="searchsubmit" class="btn btn-primary">Filter by name</button>
			</div>
		</form>
		<a class="btn btn-success" id="add" href="addComputer">Add Computer</a>
	</div>
	<tags:boostrapPaginatorTag currentPage="${currentPage}" totalPages="${nbPages}" search="${search}"/>
<%-- 	<bootstrap:pagination currentPage="${currentPage}" totalPages="${nbPages}" search="${search}"/> --%>
	<table class="table table-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<!-- Table header for Computer Name -->
				<th>Computer Name</th>
				<th>Introduced Date</th>
				<!-- Table header for Discontinued Date -->
				<th>Discontinued Date</th>
				<!-- Table header for Company -->
				<th>Company</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${computers}">
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
	<tags:boostrapPaginatorTag currentPage="${currentPage}" totalPages="${nbPages}" search="${search}"/>
</section>

<jsp:include page="include/footer.jsp" />
