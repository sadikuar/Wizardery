$(function() {
	// initial disabled state set to true	
	$('#confirmDelete').prop('disabled', true);

	// using === also checks if the types are identical (better than using ==)
	
	// until email is not equal to confirm email, button will remain disabled
	$('#confirmEmail').keyup(function() {
		if ($('#email').val() === $('#confirmEmail').val()) {
			$('#confirmDelete').prop('disabled', false);
		} else {
			$('#confirmDelete').prop('disabled', true);
		}
	})

	// only send if email equals to confirm email
	$('#confirmDelete').click(function() {
		if ($('#email').val() === $('#confirmEmail').val()) {
			$('#confirmModal').modal('hide');
			$('#formDeleteProfile').submit();
		}
	});
});