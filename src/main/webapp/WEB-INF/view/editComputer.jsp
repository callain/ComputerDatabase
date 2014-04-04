<jsp:include page="include/header.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<script>
	$(function() {
		$(".datepicker").datepicker({
			dateFormat : "yy-mm-dd"
		});
	});
</script>

<section id="main">

	<h1>Edit Computer</h1>

	<form:form action="editComputer" id="computer-form" method="POST" class="form-horizontal" commandName="computer">

		<form:hidden path="id" />

		<tags:formdiv test="${validation.charAt(0) == '0'.charAt(0)}">
			<label for="name" class="col-sm-3 control-label">Computer name:</label>
			<div class="col-xs-3">
				<form:input path="name" class="form-control" />
				<span class="glyphicon form-control-feedback"></span>
			</div>
		</tags:formdiv>

		<tags:formdiv test="${validation.charAt(1) == '0'.charAt(0)}">
			<label for="introduced" class="col-sm-3 control-label">Introduced date:</label>
			<div class="col-xs-3">
				<form:input path="introduced" class="form-control datepicker" />
				<span class="glyphicon form-control-feedback"></span>
			</div>
			<div class="col-xs-3">
				<span class="help-block">YYYY-MM-DD</span>
			</div>
		</tags:formdiv>

		<tags:formdiv test="${validation.charAt(2) == '0'.charAt(0)}">
			<label for="discontinued" class="col-sm-3 control-label">Discontinued date:</label>
			<div class="col-xs-3">
				<form:input path="discontinued" class="form-control datepicker" />
				<span class="glyphicon form-control-feedback"></span>
			</div>
			<div class="col-xs-3">
				<span class="help-block">YYYY-MM-DD</span>
			</div>
		</tags:formdiv>

		<tags:formdiv test="${validation.charAt(3) == '0'.charAt(0)}">
			<label for="company" class="col-sm-3 control-label">Company Name:</label>
			<div class="col-xs-3">
				<form:select path="companyId" class="form-control">
					<form:option value="0">--</form:option>
					<form:options items="${companies}" itemValue="id" itemLabel="name"/>
				</form:select>
			</div>
		</tags:formdiv>

		<div class="actions">
			<button type="submit" class="btn btn-primary">Save</button>
			<a href="computers" class="btn btn-default">Cancel</a>
		</div>
	</form:form>
</section>

<jsp:include page="include/footer.jsp" />