<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<script>
$(function() {
	$(".datepicker").datepicker({dateFormat: "yy-mm-dd" });
});
</script>

<section id="main">

	<h1>Edit Computer</h1>

	<form action="editComputer" id="computer-form" method="POST" class="form-horizontal">
		
		<input type="hidden" name="id" value="${computer.id}" />
		
		<tags:formdiv test="${validation.charAt(0) == '0'.charAt(0)}">
			<label for="name" class="col-sm-3 control-label">Computer name:</label>
			<div class="col-xs-3">
				<input type="text" class="form-control" name="name" value="${computer.name}" />
				<span class="glyphicon form-control-feedback"></span>
			</div>
		</tags:formdiv>

		<tags:formdiv test="${validation.charAt(1) == '0'.charAt(0)}">
			<label for="introduced" class="col-sm-3 control-label">Introduced date:</label>
			<div class="col-xs-3">
				<input type="text" class="form-control datepicker" name="introduced" value="${computer.introduced}" />
				<span class="glyphicon form-control-feedback"></span>
			</div>
			<div class="col-xs-3">
				<span class="help-block">YYYY-MM-DD</span>
			</div>
		</tags:formdiv>
		
		<tags:formdiv test="${validation.charAt(2) == '0'.charAt(0)}">
			<label for="discontinued" class="col-sm-3 control-label">Discontinued date:</label>
			<div class="col-xs-3">
				<input type="text" class="form-control datepicker" name="discontinued" value="${computer.discontinued}" />
				<span class="glyphicon form-control-feedback"></span>
			</div>
			<div class="col-xs-3">
				<span class="help-block">YYYY-MM-DD</span>
			</div>
		</tags:formdiv>
		
		<tags:formdiv test="${validation.charAt(3) == '0'.charAt(0)}">
			<label for="company" class="col-sm-3 control-label">Company Name:</label>
			<div class="col-xs-3">
				<select name="company" class="form-control">
					<option value="0">--</option>
					<c:forEach var="company" items="${companies}">
						<option value="${company.id}" <c:if test="${computer.companyId == company.id}">selected</c:if>>${company.name}</option>
					</c:forEach>
				</select>
			</div>
		</tags:formdiv>
		
		<div class="actions">
			<button type="submit" class="btn btn-primary">Save</button>
			<a href="computers" class="btn btn-default">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />