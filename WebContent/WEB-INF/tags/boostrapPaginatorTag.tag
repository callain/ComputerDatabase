<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="totalPages" required="true"%>
<%@ attribute name="search" required="false"%>

<!-- BEGIN LIST -->
<ul class="pagination">

	<!-- FIRST PAGE -->
	<c:choose>
		<c:when test="${currentPage != 1}">
			<c:choose>
				<c:when test="${!empty search}">
					<li><a href="computers?page=1&search=${search}">&laquo;</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="computers?page=1">&laquo;</a></li>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<li class="disabled"><a href="computers?page=1">&laquo;</a></li>
		</c:otherwise>
	</c:choose>

	<!-- PREVIOUS PAGE -->
	<c:choose>
		<c:when test="${currentPage == 1}">
			<li class="disabled"><a href="#">&lt;</a></li>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${!empty search}">
					<li><a href="computers?page=${currentPage - 1}&search=${search}">&lt;</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="computers?page=${currentPage - 1}">&lt;</a></li>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

	<!-- CURRENT PAGE - 2 -->
	<c:if test="${currentPage - 2 >= 1 }">
		<c:choose>
				<c:when test="${!empty search}">
					<li><a href="computers?page=${currentPage - 2}&search=${search}">${currentPage - 1}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="computers?page=${currentPage - 2}">${currentPage - 2}</a></li>
				</c:otherwise>
			</c:choose>
	</c:if>
	<c:if test="${currentPage - 1 >= 1 }">
		<c:choose>
				<c:when test="${!empty search}">
					<li><a href="computers?page=${currentPage - 1}&search=${search}">${currentPage - 1}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="computers?page=${currentPage - 1}">${currentPage - 1}</a></li>
				</c:otherwise>
			</c:choose>
	</c:if>

	<!-- CURRENT PAGE -->
	<li class="active"><a href="#">${currentPage}</a></li>

	<!-- CURRENT PAGE + 2 -->
	<c:if test="${currentPage + 1 < totalPages }">
		<c:choose>
				<c:when test="${!empty search}">
					<li><a href="computers?page=${currentPage + 1}&search=${search}">${currentPage + 1}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="computers?page=${currentPage + 1}">${currentPage + 1}</a></li>
				</c:otherwise>
			</c:choose>
	</c:if>
	<c:if test="${currentPage + 2 < totalPages }">
		<c:choose>
				<c:when test="${!empty search}">
					<li><a href="computers?page=${currentPage + 2}&search=${search}">${currentPage + 2}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="computers?page=${currentPage + 2}">${currentPage + 2}</a></li>
				</c:otherwise>
			</c:choose>
	</c:if>

	<!-- NEXT PAGE -->
	<c:choose>
		<c:when test="${currentPage == totalPages}">
			<li class="disabled"><a href="#">&gt;</a></li>
	</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${!empty search}">
					<li><a href="computers?page=${currentPage + 1}&search=${search}">&gt;</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="computers?page=${currentPage + 1}">&gt;</a></li>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>

	<!-- LAST PAGE -->
	<c:choose>
		<c:when test="${currentPage + 2 != totalPages && currentPage != totalPages}">
			<c:choose>
				<c:when test="${!empty search}">
					<li><a href="computers?page=${totalPages}&search=${search}">&raquo;</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="computers?page=${totalPages}">&raquo;</a></li>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<li class="disabled"><a href="computers?page=${totalPages}">&raquo;</a></li>
		</c:otherwise>
	</c:choose>

	<!-- END LIST -->
</ul>