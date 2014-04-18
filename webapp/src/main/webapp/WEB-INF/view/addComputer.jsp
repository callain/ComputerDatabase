<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<input type="hidden" id="datePattern" value="<spring:message code="date.pattern.js" />" />
<input type="hidden" id="dateError" value="<spring:message code="date.error" />" />
<input type="hidden" id="lang" value="<spring:message code="lang" />" />
<script>
	$(function() {
		$.datepicker.setDefaults($.datepicker.regional[$('#lang').val()]);
		$(".datepicker").datepicker({
			dateFormat : $('#datePattern').val(),
			minDate : new Date('1970/1/1'),
			maxDate : new Date('2038/12/31'),
			changeMonth: true,
			changeYear: true
		});
	});
</script>

<section id="main">
	<div class="container">
		<h1><spring:message code="add.computer" /></h1>
		<form:form action="addComputer" method="POST" id="computer-form" class="form-horizontal" commandName="computer">
			<tags:formdiv test="${validation.charAt(0) == '0'.charAt(0)}">
				<label for="name" class="col-sm-3 control-label"><spring:message code="computer.name" /></label>
				<div class="col-xs-3">
					<form:input path="name" id="name" class="form-control"/>
					<span class="glyphicon form-control-feedback"></span>
					<form:errors path="name" class="has-error" />
				</div>
			</tags:formdiv>

			<tags:formdiv test="${validation.charAt(1) == '0'.charAt(0)}">
				<label for="introduced" class="col-sm-3 control-label"><spring:message code="computer.introduced" /></label>
				<div class="col-xs-3">
					<form:input path="introduced" id="introduced" class="form-control datepicker"/>
					<span class="glyphicon form-control-feedback"></span>
					<form:errors path="introduced" class="has-error" />
				</div>
				<div class="col-xs-3">
					<span class="help-block"><spring:message code="date.pattern"/></span>
				</div>
			</tags:formdiv>

			<tags:formdiv test="${validation.charAt(2) == '0'.charAt(0)}">
				<label for="discontinued" class="col-sm-3 control-label"><spring:message code="computer.discontinued" /></label>
				<div class="col-xs-3">
					<form:input path="discontinued" id="discontinued" class="form-control datepicker" />
					<span class="glyphicon form-control-feedback"></span>
					<form:errors path="discontinued" class="has-error" />
				</div>
				<div class="col-xs-3">
					<span class="help-block"><spring:message code="date.pattern"/></span>
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
				<button type="submit" class="btn btn-primary"><spring:message code="add" /></button>
				<a href="computers" class="btn btn-default"><spring:message code="cancel" /></a>
			</div>
		</form:form>
	</div>
</section>

<jsp:include page="include/footer.jsp" />