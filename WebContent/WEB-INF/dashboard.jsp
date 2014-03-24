<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<section id="main">
	<h1 id="homeTitle">${nbComputers} computers found</h1>
<!-- 	<div class="alert alert-success alert-dismissable"> -->
<!-- 		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button> -->
<!-- 		<strong>Well done!</strong>You sucessfully add a computer. -->
<!-- 	</div> -->
	<div id="actions">
		<form class="form-inline" action="" method="POST">
			<div class="form-group">
				<input type="search" id="searchbox" class="form-control" name="search" value="" placeholder="Search name">
				<button type="submit" id="searchsubmit" class="btn btn-primary">Filter by name</button>
			</div>
		</form>
		<a class="btn btn-success" id="add" href="addComputer">Add Computer</a>
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
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${computers}">
				<tr>
					<td><a href="editComputer?id=${computer.id}">${computer.name}</a></td>
					<td><fmt:formatDate pattern="dd/MM/yyyy" value="${computer.introduced}" /></td>
					<td><fmt:formatDate pattern="dd/MM/yyyy" value="${computer.discontinued}" /></td>
					<td>${computer.company.name}</td>
					<td><a href="deleteComputer?id=${computer.id}"><span class="glyphicon glyphicon-trash" style="color: #d9534f"></span></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<%--For displaying Previous link except for the 1st page --%>
	 <ul class="pagination">
	 <c:choose>
		 <c:when test="${currentPage == 1}">
	          <li class="disabled"><a href="#">&laquo;</a></li>
	     </c:when>
	     <c:otherwise>
	     	<li><a href="computers?page=${currentPage - 1}">&laquo;</a></li>
	     </c:otherwise>
     </c:choose>
     
     <c:forEach begin="1" end="${nbPages}" var="i">
         <c:choose>
             <c:when test="${currentPage eq i}">
                 <li class="active"><a href="#">${i}</a></li>
             </c:when>
             <c:otherwise>
                 <li><a href="computers?page=${i}">${i}</a></li>
             </c:otherwise>
         </c:choose>
     </c:forEach>
        
     <%--For displaying Next link --%>
		<c:if test="${currentPage lt nbPages}">
		   <li><a href="computers?page=${currentPage + 1}">&raquo;</a></li>
		</c:if>
    </ul>
</section>

<jsp:include page="include/footer.jsp" />
