$(document).ready(function()
{
	$.validator.addMethod("dateControl",function(value, element)
	{
		try
		{
			$.datepicker.parseDate("yy-mm-dd", value);
			return true;
		}
		catch(e)
		{
			return false;
		}
	}, "Please enter a valid date in the format yyyy-mm-dd.");
	
	$('#computer-form').validate(
	{
		rules:
		{
			name:
			{
		        minlength: 2,
		        maxlength: 50,
		        required: true
			},
			
			introduced:
			{
				dateControl: true
			},
			
			discontinued:
			{
				dateControl: true
			},
			
			company:
			{
				required: true
			}
	    },

	    highlight: function(element)
	    {
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
				$(element).next('.form-control-feedback').removeClass('glyphicon-ok').addClass('glyphicon-remove');
		},
		
		success: function(element)
		{
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
				$(element).next('.form-control-feedback').removeClass('glyphicon-remove').addClass('glyphicon-ok');
		},
		
		errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element) {
            if(element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
	});
});