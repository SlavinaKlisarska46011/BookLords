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
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
</head>

<body>
	<c:choose>
		<c:when test="${sessionScope.loggedUser != null}">
			<div><jsp:include page="logedHeader.jsp" /></div>
		</c:when>
		<c:otherwise>
			<div><jsp:include page="notLogedHeader.jsp" /></div>
		</c:otherwise>
	</c:choose>
	<div class="wrapper row2">
		<div id="container" class="clear">
			<hr>
			<br>
			<div id="book">
				<c:choose>
					<c:when test="${ not empty author }">
						<table>
							<tr>
								<th></th>
								<th></th>
								<th></th>
							</tr>
							<tr>
								<td><img alt="Book Poster"
									src='<c:url value="${author.imageURL}"></c:url>' width="270"
									height="280"></td>
								<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
								<td>
									<h1 style="color: black">
										<c:if test="${ not empty author.name }">
											<c:out value="${author.name}"></c:out>
										</c:if>
										<br> <br> <br />
									</h1>
									<p>
										<strong>Description:</strong> <b><c:out
												value="${author.biography}"></c:out></b>
									</p>
									<p>
										<strong>born:</strong>
										<c:out value="${author.born}"></c:out>
									</p> <c:if test="${ not empty author.died}">
										<p>
											<strong>Died:</strong>
											<c:out value="${author.died}"></c:out>
										</p>
									</c:if> <!-- 	<c:if test="${ not empty book.genres }"> <p>
									<strong>Genres:</strong>
									<c:forEach items="${book.genres}" var="genre">
										<c:out value="${genre}"></c:out>
									</c:forEach>	</c:if>
								</p>-->
								</td>
							</tr>
						</table>
					</c:when>
					<c:otherwise>
						<h1>No information about this author.</h1>
						<h3>
							You can go to <a href="https://www.google.bg">Google</a>
						</h3>
					</c:otherwise>
				</c:choose>
			</div>
			<br />
		</div>
	</div>
	<div class="wrapper row3">
		<footer id="footer" class="clear">
			<p class="fl_left">
				Booklords.com
			</p>
		</footer>
	</div>
</body>
</html>
