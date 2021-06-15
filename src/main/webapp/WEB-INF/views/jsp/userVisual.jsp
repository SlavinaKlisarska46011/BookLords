<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<title>Booklords</title>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="css/styles/layout.css" type="text/css">
<link rel="stylesheet" href="css/publicButton.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<style type="text/css">
.div2 {
	width: 300px;
	height: 100px;
	padding: 50px;
	right: 50px;
}

.position {
	z-index: -1;
}
</style>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
</head>

<body>
	<c:choose>
		<c:when test="${sessionScope.loggedUser != null}">
			<div><jsp:include page="logedHeader.jsp" /></div>
		</c:when>
		<c:otherwise>
			<div><jsp:include page="login.jsp" /></div>
		</c:otherwise>
	</c:choose>
	<div id="container" style="color: #919191">
		<div style="background-color: #232323">

			<div class="position"><jsp:include page="peopleSearchBar.jsp" /></div>
			<div class="beauty">
				<br>
				<br>
				<br>
			</div>
			<br> <br>
			<div id="books">
				<c:choose>
					<c:when test="${ not empty users}">

						<c:forEach items="${users}" var="user">
							<a href="http://localhost:8080/ShowUser?id=${user.id}">
								<table>
									<tr>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
									</tr>
									<tr>
										<td><img alt="Profile picture"
											src='profile_pics/<c:url value="${user.profilePicture}"></c:url>'
											width="70" height="80"></td>
										<td>
											<p style="color: #919191">
												<strong>&nbsp&nbsp&nbsp&nbspName:</strong>
												<c:if test="${ not empty user.name }">
													&nbsp&nbsp&nbsp&nbsp<c:out value="${user.name}"></c:out>
												</c:if>
											</p>
										</td>
										<td>
											<p style="color: #919191">
												<strong>&nbsp&nbsp&nbsp&nbspActivity:</strong>
												<c:if test="${ not empty user.activity }">
													&nbsp&nbsp&nbsp&nbsp<c:out value="${user.activity}"></c:out>
												</c:if>
											</p>
										</td>
									</tr>
								</table>
							</a>
							<hr />
							<br>
							<br>
							<br>
							<br>
							<br>
						</c:forEach>
					</c:when>

					<c:otherwise>
						<h3>
							<!--<b>No users found.</b> -->
						</h3>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

</body>
</html>
