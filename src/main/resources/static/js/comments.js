	function showComment(text, event) {
		var tempX = event.pageX;
		var a = tempX + 40;
		var tempY = event.pageY;
		var msgbox = $("#showComment");
		msgbox.html('<p id="nameP" style="left: '+a+'px;top: '+tempY+'px;">'
				+ text + '</p>');
	}

	function clearComment() {
		var msgbox = $("#showComment");
		msgbox.empty();
	}

	function clearContents(element) {
		element.value = '';
	}