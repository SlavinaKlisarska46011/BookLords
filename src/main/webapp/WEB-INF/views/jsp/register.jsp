<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"
	charset="UTF-8">
<title>Register</title>

<style type="text/css">
body {
	font-family: verdana, Helvetica, sans-serif
}

.myImg {
	font-size: 11px;
	margin: 10px;
}

.myName {
	font-size: 11px;
	margin: 10px;
}

.availablecss {
	background-color: #CEFFCE;
	border: 1px solid green;
	position: absolute;
	left: 370px;
	top: 140px;
}

.notavailablecss {
	background-color: #FFD9D9;
	border: 1px solid red;
	position: absolute;
	left: 370px;
	top: 143px;
	width:190px;
    text-align:center;
}

.emailImg {
	position: absolute;
	left: 340px;
	top: 150px;
}

.nameImg {
	position: absolute;
	left: 340px;
	top: 85px;
}

.nameP {
	background-color: #FFD9D9;
	border: 1px solid red;
	position: absolute;
	left: 370px;
	top: 77px;
	width:190px;
    text-align:center;
}

.passwordImg {
	position: absolute;
	left: 340px;
	top: 220px;
}

.passwordP {
	background-color: #FFD9D9;
	border: 1px solid red;
	position: absolute;
	left: 370px;
	top: 210px;
	width:220px;
    text-align:center;
}

</style>

<link rel="stylesheet" href="css/style.css">

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">

	var ifTrueName = false;
	var ifTrueEmail = false;
	var ifTruePassword = false;

	function check() {
		if(ifTrueName && ifTrueEmail && ifTruePassword){
	    	document.getElementById("submit").disabled = false;
		}else{
			document.getElementById("submit").disabled = true;
		}
	}

	function sendRequestName() {
		var msgbox = $("#myName");
		msgbox.empty();
		var name = $("#name").val();
		var regex = /^[a-zA-Z ]{3,20}$/;
		if (regex.test(name)) {
			ifTrueName = true;
			msgbox
					.html('<img src="images/ok.png" class="nameImg" height="20" width="20">');
			check();
			
		} else {
			ifTrueName = false;
			msgbox
					.html('<img src="images/not.png" class="nameImg" height="20" width="20"><p class="nameP">Name must contains only letters</p>');
			check();
		}
	}

	function sendRequestPassword() {
		var msgbox = $("#myPassword");
		msgbox.empty();
		var password = $("#password").val();
		var regex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$/;
		if (regex.test(password)) {
			ifTruePassword = true;
			msgbox
					.html('<img src="images/ok.png" class="passwordImg" height="20" width="20">');
			check();
		} else {
			ifTruePassword = false;
			msgbox
					.html('<img src="images/not.png" class="passwordImg" height="20" width="20"><p class="passwordP">At least one upper case english letter, at least one lower case english letter, at least one digit, at least one special character, minimum 6 in length</p>');
			check();
		}
	}

	function sendRequestEmail() {
		var msgbox = $("#myImg");
		msgbox.empty();
		var email = $("#email").val();
		var regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
		var url = 'http://localhost:8080/BookLords/Validate?email=' + email;
		if (regex.test(email)) {
			$
					.ajax({
						url : url,
						type : "GET",
						complete : function(jqXHR) {
							if (jqXHR.status == 200) {
								ifTrueEmail = true;
								msgbox
										.html('<img src="images/ok.png" class="emailImg" height="20" width="20"> <p class="availablecss"> Available </p>');
								check();
							} else {
								ifTrueEmail = false;
								msgbox
										.html('<img src="images/not.png" class="emailImg" height="20" width="20"> <p class="notavailablecss">'
												+ email
												+ ' is already in use. </p>');
								check();
							}
						}

					});
		} else {
			ifTrueEmail = false;
			msgbox.html('<p class="notavailablecss">Invalid email</p>');
			check();
		}
	}
</script>

</head>

<body>
<jsp:include page="homeHeader.jsp" />
	<div class="container">
		<section id="content">
			<form:form commandName="user">
				<form:errors path="*" cssClass="errorblock" element="div" />
				<br>
				<h1>Sign up</h1>
				<div>
					<label for="name"></label>
					<form:input type="text" required="required" id="name"
						placeholder="Name" path="name" cssErrorClass="error"
						onchange="sendRequestName()" />
					<form:errors path="name" cssClass="error" />
					<div class="myName" id="myName" align="right"></div>
				</div>
				<div>
					<label for="email"></label>
					<form:input type="text" required="required" id="email"
						placeholder="Email" path="email" cssErrorClass="error"
						onchange="sendRequestEmail()" />
					<form:errors path="email" cssClass="error" />
					<div class="myImg" id="myImg" align="right"></div>
				</div>
				<div>
					<label for="password"></label>
					<form:input type="password" required="required"
						placeholder="Password" path="password" cssErrorClass="error"
						onchange="sendRequestPassword()" />
					<form:errors path="password" cssClass="error" />
					<div class="myPassword" id="myPassword" align="right"></div>
				</div>
				<div>
					<input type="submit" id="submit" value="Sign up" disabled /> <a
						href="http://localhost:8080/Login">Sign in</a>
				</div>
			</form:form>
			<!-- form -->
		</section>
		<!-- content -->
	</div>
	<!-- container -->
</body>






</body>
</html>
