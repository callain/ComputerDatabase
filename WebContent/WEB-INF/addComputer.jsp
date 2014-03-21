<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section id="main">

	<h1>Add Computer</h1>

	<form action="/ComputerDatabase/addComputer" method="POST" class="form-horizontal">
		<fieldset>
			<div class="form-group">
				<label for="name" class="col-sm-3 control-label">Computer name:</label>
				<div class="col-xs-3">
					<input type="text" class="form-control" name="name" />
				</div>
				<div class="col-xs-3">
					<span class="help-block">Required</span>
				</div>
			</div>

			<div class="form-group">
				<label for="introduced" class="col-sm-3 control-label">Introduced date:</label>
				<div class="col-xs-3">
					<input type="text" class="form-control" name="introduced"/>
				</div>
				<div class="col-xs-3">
					<span class="help-block">YYYY-MM-DD hh:mm:ss</span>
				</div>
			</div>
			<div class="form-group">
				<label for="discontinued" class="col-sm-3 control-label">Discontinued date:</label>
				<div class="col-xs-3">
					<input type="text" class="form-control" name="discontinued"/>
				</div>
				<div class="col-xs-3">
					<span class="help-block">YYYY-MM-DD hh:mm:ss</span>
				</div>
			</div>
			<div class="form-group">
				<label for="company" class="col-sm-3 control-label">Company Name:</label>
				<div class="col-xs-3">
					<select name="company" class="form-control">
						<c:forEach var="company" items="${companies}" >
							<option value="${company.id}">${company.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<button type="submit" class="btn btn-primary">Add</button>
			or
			<a href="computers" class="btn btn-default">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />