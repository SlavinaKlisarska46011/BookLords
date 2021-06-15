<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<title>Booklords</title>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="css/styles/layout.css" type="text/css">
<link rel="stylesheet" href="css/publicButton.css" type="text/css">
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
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
							<td><img alt="Profile picture"
								src='<c:url value="${user.profilePicture}"></c:url>' width="270"
								height="280"></td>
							<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
							<td>
								<h1 style="color: black">
									<strong><b>Name:</b></strong>
									<c:if test="${ not empty user.name }">
										<c:out value="${user.name}"></c:out>
									</c:if><a href="http://localhost:8080/Edit">[edit]</a>
									<br> <br> <br />
								</h1>
								<p>
									<strong>Joined: </strong> <b><c:out value="${user.activity}"></c:out></b>
								</p> 
							</td>
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