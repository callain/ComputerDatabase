<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
	$(function() {
		$(".datepicker").datepicker({
			dateFormat : "yy-mm-dd"
		});
	});
</script>

<c:if test="${name}">
	<script>
		$('#name').valid();
	</script>
</c:if>

<section id="main">
	<div class="container">
		<h1>Add Computer</h1>
		<form action="addComputer" method="POST" id="computer-form" class="form-horizontal">
			
			<div class="form-group has-feedback">
				<label for="name" class="col-sm-3 control-label">Computer name:</label>
				<div class="col-xs-3">
					<input type="text" class="form-control" name="name" id="name"/>
					<span class="glyphicon form-control-feedback"></span>
				</div>
			</div>

			<div class="form-group has-feedback">
				<label for="introduced" class="col-sm-3 control-label">Introduced date:</label>
				<div class="col-xs-3">
					<input type="text" class="form-control datepicker" name="introduced" id="introduced" /> <span class="glyphicon form-control-feedback"></span>
				</div>
				<div class="col-xs-3">
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>

			<div class="form-group has-feedback">
				<label for="discontinued" class="col-sm-3 control-label">Discontinued date:</label>
				<div class="col-xs-3">
					<input type="text" class="form-control datepicker" name="discontinued" id="discontinued" /> <span class="glyphicon form-control-feedback"></span>
				</div>
				<div class="col-xs-3">
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group has-feedback">
				<label for="company" class="col-sm-3 control-label">Company Name:</label>
				<div class="col-xs-3">
					<select name="company" class="form-control">
						<option value="0">--</option>
						<c:forEach var="company" items="${companies}">
							<option value="${company.id}">${company.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="actions">
				<button type="submit" class="btn btn-primary">Add</button>
				<a href="computers" class="btn btn-default">Cancel</a>
			</div>
		</form>
	</div>
</section>

<jsp:include page="include/footer.jsp" />