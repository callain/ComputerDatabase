<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="totalPages" required="true"%>
<%@ attribute name="search" required="false"%>
<%@ attribute name="field" required="false"%>
<%@ attribute name="isDesc" required="false"%>

<!-- BEGIN LIST -->
<ul class="pagination">

	<!-- FIRST PAGE -->
	<li <c:if test="${currentPage == 1}">class="disabled"</c:if>><tags:link href="computers" page="1" search="${search}" content="&laquo;" field="${field}" isDesc="${isDesc}"/></li>

	<!-- PREVIOUS PAGE -->
	<c:choose>
		<c:when test="${currentPage == 1}" >
			<li class="disabled"><tags:link href="#" content="&lt;" search="${search}" field="${field}" isDesc="${isDesc}"/></li>
		</c:when>
		<c:otherwise>
			<li><tags:link href="computers" content="&lt;" page="${currentPage - 1}" search="${search}" field="${field}" isDesc="${isDesc}"/></li>
		</c:otherwise>		
	</c:choose>

	<!-- CURRENT PAGE - 2 -->
	<c:if test="${currentPage - 2 >= 1 }">
		<li><tags:link href="computers" page="${currentPage - 2}" search="${search}" content="${currentPage - 2}" field="${field}" isDesc="${isDesc}"/></li>
	</c:if>
	<c:if test="${currentPage - 1 >= 1 }">
		<li><tags:link href="computers" page="${currentPage - 1}" search="${search}" content="${currentPage - 1}" field="${field}" isDesc="${isDesc}"/></li>
	</c:if>
	
	<!-- CURRENT PAGE -->
	<li class="active"><a href="#">${currentPage}</a></li>

	<!-- CURRENT PAGE + 2 -->
	<c:if test="${currentPage + 1 <= totalPages }">
		<li><tags:link href="computers" page="${currentPage + 1}" search="${search}" content="${currentPage + 1}" field="${field}" isDesc="${isDesc}"/></li>
	</c:if>
	<c:if test="${currentPage + 2 <= totalPages }">
		<li><tags:link href="computers" page="${currentPage + 2}" search="${search}" content="${currentPage + 2}" field="${field}" isDesc="${isDesc}"/></li>
	</c:if>

	<!-- NEXT PAGE -->
	<c:choose>
		<c:when test="${currentPage == totalPages}" >
			<li class="disabled"><tags:link href="#" content="&gt;" search="${search}" field="${field}" isDesc="${isDesc}"/></li>
		</c:when>
		<c:otherwise>
			<li><tags:link href="computers" content="&gt;" page="${currentPage + 1}" search="${search}" field="${field}" isDesc="${isDesc}"/></li>
		</c:otherwise>		
	</c:choose>

	<!-- LAST PAGE -->
	<li <c:if test="${currentPage == totalPages}">class="disabled"</c:if>><tags:link href="computers" page="${totalPages}" search="${search}" content="&raquo;" field="${field}" isDesc="${isDesc}"/></li>

	<!-- END LIST -->
</ul>