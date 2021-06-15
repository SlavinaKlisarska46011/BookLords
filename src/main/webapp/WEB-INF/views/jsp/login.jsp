<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>
<jsp:include page="homeHeader.jsp" />
	<div class="container">
		<section id="content">
			<h1>Login Form</h1>
			<c:if test="${ not empty loginAgain}">
				<h3 style="color: red">Invalid username or password!</h3>
			</c:if>
			<form:form commandName="user">
				<form:errors path="*" cssClass="errorblock" element="div" />
				<br>
				<div>
					<label for="email"></label>
					<form:input type="text" required="required" id="email"
						placeholder="Email" path="email" cssErrorClass="error" />
					<form:errors path="email" cssClass="error" />
				</div>
				<div>
					<label for="password"></label>
					<form:input type="password" required="required"
						placeholder="Password" path="password" cssErrorClass="error" />
					<form:errors path="password" cssClass="error" />
				</div>
				<div>
					<input type="submit" value="Log in" />
					<!--  <a href="#">Lost your password?</a> -->
					<a href="http://localhost:8080/BookLords/Register">Register</a>
				</div>
			</form:form>
		</section>
		<!-- content -->
	</div>
	<!-- container -->
</body>





</body>
</html>
