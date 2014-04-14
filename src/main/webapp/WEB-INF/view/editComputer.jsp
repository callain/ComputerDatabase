<jsp:include page="include/header.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<script>
	$(function() {
		$(".datepicker").datepicker({
			dateFormat : "<spring:message code="date.pattern.js" />"
		});
	});
</script>

<section id="main">

	<h1>Edit Computer</h1>

	<form:form action="editComputer" id="computer-form" method="POST" class="form-horizontal" commandName="computer">

		<form:hidden path="id" />

		<tags:formdiv test="${validation.charAt(0) == '0'.charAt(0)}">
			<label for="name" class="col-sm-3 control-label"><spring:message code="computer.name" /></label>
			<div class="col-xs-3">
				<form:input path="name" class="form-control" />
				<span class="glyphicon form-control-feedback"></span>
			</div>
		</tags:formdiv>

		<tags:formdiv test="${validation.charAt(1) == '0'.charAt(0)}">
			<label for="introduced" class="col-sm-3 control-label"><spring:message code="computer.introduced" /></label>
			<div class="col-xs-3">
				<form:input path="introduced" class="form-control datepicker" />
				<span class="glyphicon form-control-feedback"></span>
			</div>
			<div class="col-xs-3">
				<span class="help-block"><spring:message code="date.pattern.joda"/></span>
			</div>
		</tags:formdiv>

		<tags:formdiv test="${validation.charAt(2) == '0'.charAt(0)}">
			<label for="discontinued" class="col-sm-3 control-label"><spring:message code="computer.discontinued" /></label>
			<div class="col-xs-3">
				<form:input path="discontinued" class="form-control datepicker" />
				<span class="glyphicon form-control-feedback"></span>
			</div>
			<div class="col-xs-3">
				<span class="help-block"><spring:message code="date.pattern.joda"/></span>
			</div>
		</tags:formdiv>

		<tags:formdiv test="${validation.charAt(3) == '0'.charAt(0)}">
			<label for="company" class="col-sm-3 control-label"><spring:message code="computer.company" /></label>
			<div class="col-xs-3">
				<form:select path="companyId" class="form-control">
					<form:option value="0">--</form:option>
					<form:options items="${companies}" itemValue="id" itemLabel="name"/>
				</form:select>
			</div>
		</tags:formdiv>

		<div class="actions">
			<button type="submit" class="btn btn-primary"><spring:message code="edit" /></button>
			<a href="computers" class="btn btn-default"><spring:message code="cancel" /></a>
		</div>
	</form:form>
</section>

<jsp:include page="include/footer.jsp" />