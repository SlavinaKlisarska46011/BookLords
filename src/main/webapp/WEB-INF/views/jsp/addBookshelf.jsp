<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyBooks</title>
<style type="text/css">
div.absolute {
	position: absolute;
	top: 180px;
	left: 200px;
	width: 600px;
	height: 100px;
	border: none;
	z-index: -1;
}

input[type=text].beautiful {
	width: 70%;
	box-sizing: border-box;
	border: none;
	border-radius: 4px;
	font-size: 16px;
	background-color: white;
	background-position: 10px 10px;
	background-repeat: no-repeat;
	padding: 12px 20px 12px 40px;
}

input[type=submit].shiny {
	background-color: #4CAF50;
	border: none;
	color: white;
	padding: 16px 32px;
	text-decoration: none;
	margin: 4px 2px;
	cursor: pointer;
}

input.new {
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 10px 25px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
}
</style>
</head>
<body>
	<div><jsp:include page="logedHeader.jsp" /></div>
	<div class="absolute">
		<c:if test="${not empty emptyBookshelf}">
			<h3 align="right" style="color: red">Please enter valid
				bookshelf name!</h3><br/><br/>
		</c:if>
		<c:if test="${not empty existingBookshelf}">
			<h3 align="right" style="color: red">This bookshelf already
				exists!</h3><br/><br/>
		</c:if>
		<form:form commandName="bookshelf">
			<form:errors path="*" cssClass="errorblock" element="div" />
			<form:input class="beautiful" id="shelf" type="text" path="name"
				placeholder="Shelf name" />
			<form:errors path="name" cssClass="error" />
			<input class="new" type="submit" value="Add shelf" />
		</form:form>
	</div>
</body>
</html>