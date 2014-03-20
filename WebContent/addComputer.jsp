<jsp:include page="include/header.jsp" />
<section id="main">

	<h1>Add Computer</h1>

	<form action="/computer" method="POST" class="form-horizontal">
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
					<input type="date" class="form-control" name="introducedDate" pattern="YY-MM-dd" />
				</div>
				<div class="col-xs-3">
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group">
				<label for="discontinued" class="col-sm-3 control-label">Discontinued date:</label>
				<div class="col-xs-3">
					<input type="date" class="form-control" name="introducedDate" pattern="YY-MM-dd" />
				</div>
				<div class="col-xs-3">
					<span class="help-block">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="form-group">
				<label for="company" class="col-sm-3 control-label">Company Name:</label>
				<div class="col-xs-3">
					<select name="company" class="form-control">
						<option value="0">--</option>
						<option value="1">Apple</option>
						<option value="2">Dell</option>
						<option value="3">Lenovo</option>
					</select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<button type="button" class="btn btn-primary">Add</button>
			or
			<a href="/computer" class="btn btn-default">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="include/footer.jsp" />