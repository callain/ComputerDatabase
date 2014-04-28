<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<section id="main">
	<div class="container">
		<form method="post" action="j_spring_security_check" class="form-horizontal">

			<div class="form-group has-feedback">
				<label for="j_username" class="col-sm-3 control-label"><spring:message code="login" /></label>
				<div class="col-xs-3">
					<input name="j_username" value="" type="text" class="form-control" />
				</div>
			</div>

			<div class="form-group has-feedback">
				<label for="j_password" class="col-sm-3 control-label"><spring:message code="password" /></label>
				<div class="col-xs-3">
					<input name="j_password" type="password" class="form-control" />
				</div>
			</div>

			<div class="form-actions">
				<button type="submit" class="btn btn-primary">
					<spring:message code="validate" />
				</button>
			</div>

		</form>
	</div>
</section>
<jsp:include page="include/footer.jsp" />