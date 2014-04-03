<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="href" required="true"%>
<%@ attribute name="content" required="true"%>
<%@ attribute name="search" required="false"%>
<%@ attribute name="page" required="false"%>
<%@ attribute name="field" required="false"%>
<%@ attribute name="isDesc" required="false"%>

<c:set var="url" value="${href}?" />

<c:if test="${!empty search}" >
	<c:set var="url" value="${url}&search=${search}" />
</c:if>

<c:if test="${!empty page}" >
	<c:set var="url" value="${url}&page=${page}" />
</c:if>

<c:if test="${!empty field}" >
	<c:set var="url" value="${url}&field=${field}" />
</c:if>

<c:if test="${!empty isDesc}" >
	<c:set var="url" value="${url}&isDesc=${isDesc}" />
</c:if>

<a href="${url}" >${content}</a>