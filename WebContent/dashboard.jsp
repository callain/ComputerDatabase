<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<section id="main">
	<h1 id="homeTitle">${fn:length(computers)} computers found</h1>
	<div id="actions">
		<form class="form-inline" action="" method="GET">
			<div class="form-group">
				<input type="search" id="searchbox" class="form-control" name="search" value="" placeholder="Search name">
				<button type="button" id="searchsubmit" class="btn btn-primary">Filter by name</button>
			</div>
		</form>
		<a class="btn btn-success" id="add" href="addComputer.jsp">Add Computer</a>
	</div>

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
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${computers}">
				<tr>
					<td><a href="#" onclick="">${computer.name}</a></td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<c:if test="${computer.companyId != null }">
						<td>${companies.get(computer.companyId).name}</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>

<jsp:include page="include/footer.jsp" />
