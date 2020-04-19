$(function() {
	$('#file-upload').change(function() {
		if ($('#file-upload')[0].files) {
			let label = $(this).next('label');
			let files = $('#file-upload')[0].files;
			let text = "";
			
			for (var i = 0; i < files.length; i++) {
				text += files[i].name + ", ";
			}
			
			if (text.length > 30) {
				text = text.substring(0, 30);
				text += "...";
			} else {
				text = text.substring(0, text.length - 2);
			}
			label.text(text);
		}
	});
});