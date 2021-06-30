<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<title>Booklords</title>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="css/styles/layout.css" type="text/css">
<link rel="stylesheet" href="css/publicButton.css" type="text/css">

<style>
.nameP {
	background-color: #FFD9D9;
	border: 1px solid red;
	position: absolute;
	left: 370px;
	top: 77px;
	width: 190px;
	text-align: center;
}

.myPassword {
	font-size: 11px;
	margin: 10px;
	left: 100px;
}

#password {
	position: absolute;
	left: 300px;
	top: 541px;
}
#password1 {
	position: absolute;
	left: 300px;
	top: 564px;
}
.passwordImg{
    position: absolute;
	left: 445px;
	top: 541px;
}

.passwordP {
	background-color: #FFD9D9;
	border: 1px solid red;
	position: absolute;
	left: 465px;
	top: 560px;
	width:220px;
    text-align:center;
}

.passwordImg1{
    position: absolute;
	left: 445px;
	top: 566px;
}
</style>

<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	var ifTruePassword = false;
	var ifTruePassword1 = false;

	function check() {
		if (ifTruePassword && ifTruePassword1) {
			document.getElementById("submitPassword").disabled = false;
		} else {
			document.getElementById("submitPassword").disabled = true;
		}
	}

	function checkPassword() {
		var msgbox = $("#myPassword1");
		msgbox.empty();
		var password = $("#password1").val();
		var regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$/;
		if (regex.test(password)) {
			ifTruePassword = true;
			msgbox
					.html('<img src="images/ok.png" class="passwordImg1" height="20" width="20">');
			check();
		} else {
			ifTruePassword = false;
			msgbox
					.html('<img src="images/not.png" class="passwordImg1" height="20" width="20"><p class="passwordP">At least one upper case english letter, at least one lower case english letter, at least one digit, at least one special character, minimum 6 in length</p>');
			check();
		}
	}
	
	function sendRequestPassword() {
		var msgbox = $("#myPassword");
		msgbox.empty();
		var password = $("#password").val();
		var regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$/;
		var url = 'http://localhost:8080/Validate?password=' + password;
		if (regex.test(password)) {
			$
					.ajax({
						url : url,
						type : "POST",
						complete : function(jqXHR) {
							if (jqXHR.status == 200) {
								ifTruePassword1 = true;
								msgbox
										.html('<img src="images/ok.png" class="passwordImg" height="20" width="20">');
								check();
							} else {
								ifTruePassword1 = false;
								msgbox
										.html('<img src="images/not.png" class="passwordImg" height="20" width="20">');
								check();
							}
						}

					});
		} else {
			ifTruePassword1 = false;
			msgbox.html('<img src="images/not.png" class="passwordImg" height="20" width="20">');
			check();
		}
	}

	function Validatebodypanelbumper(theForm) {
		var regexp;
		var extension = new FormData(theForm).get("file").value
				.lastIndexOf('.');
		if ((extension.toLowerCase() != ".png")
				&& (extension.toLowerCase() != ".jpg") && (extension != "")) {
			alert("The \"FileUpload\" field contains an unapproved filename.");
			theForm.file.focus();
			return false;
		}
		return true;
	}
	
	function release(theForm){
		var vidFile = document.getElementById("file").files[0].name;
		if(vidFile.length > 0){
			document.getElementById("submitPicture").disabled = false;
			}
	}
</script>
</head>
<body>

	<div><jsp:include page="logedHeader.jsp" /></div>

	<div class="wrapper row2">
		<div id="container" class="clear">
			<hr>
			<br>
			<div id="user">
				<c:if test="${ not empty user }">
					<table>
						<tr>
							<th></th>
							<th></th>
						</tr>
						<tr>
							<td><img alt="Profile picture" id="image"
								src='images/<c:url value="${user.profilePicture}"></c:url>' width="200"
								height="210"></td>
							<div>
								<form:form method="POST" enctype="multipart/form-data"
									onsubmit="Validatebodypanelbumper()">
									<table>
										<tr>
											<td>File to upload:</td>
											<td><input type="file" id="file" name="file" 
												accept="image/*" onchange="release()"/></td>
										</tr>
										<tr>
											<td></td>
											<td><input type="submit" id="submitPicture" disabled value="Upload" /></td>
										</tr>
									</table>
								</form:form>
							</div>
							<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
							<td><form method="POST" action="./EditPassword">
							<hr><br>
							<p style="color:black;">Change password</p>
									<div>
										<label for="password">Current password</label> <input
											type="password" id="password" 
											name="password" onchange="sendRequestPassword()" />
										<div class="myPassword" id="myPassword" align="right"></div>
									</div>
									<div>
										<label for="password1">New password</label> <input
											type="password" id="password1" 
											name="password1" onchange="checkPassword()" />
										<div class="myPassword1" id="myPassword1" align="right"></div>
									</div>
									<br>
									<td><input type="submit" id="submitPassword" disabled
										value="Upload" /></td>
								</form></td>
						</tr>
					</table>
				</c:if>
			</div>
			<br />
		</div>
		<!-- / content body -->
	</div>
</body>
</html>