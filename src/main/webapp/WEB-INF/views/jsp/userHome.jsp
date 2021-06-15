<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BookLords</title>
<style>
.center {
	margin: top 20%;
	width: 15%;
	height: 50px;
	border: 3px solid #919191;
	padding: 1px;
	font-size: 20px;
	color: black;
	background-color: #919191;
	text-align: center;
}

#profiles {
	position: absolute;
	top: 360px;
	right: 0;
}

#profilesH1 {
	position: absolute;
	top: 360px;
	right: 0;
}


</style>
</head>
<body>
	<div><jsp:include page="logedHeader.jsp" /></div>
	<div id="container" style="color: #919191">
		<div style="background-color: #232323">
			<table>
				<tr>
					<th>
					<th>
				<tr>
				<tr>
					<td>
						<div id="books">
							<c:choose>
								<c:when test="${not empty books}">
									<h1 style="color: #919191">People you follow have rated these books:</h1>
									<c:forEach items="${books}" var="entry">
										<c:set var="book" value="${entry.key}" />
										<c:set var="rating" value="${entry.value}" />
										<a
											href="http://localhost:8080/ShowBook?id=${book.bookId}">
											<table>
												<tr>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
												</tr>
												<tr>
													<td><img alt="Book Poster"
														src='<c:url value="${book.posterURL}"></c:url>'
														width="130" height="200"></td>
													<td>
														<h1 style="color: #919191">
															<strong>&nbsp&nbsp&nbsp&nbspTitle:</strong>
															<c:if test="${ not empty book.title }">
													&nbsp&nbsp&nbsp&nbsp<c:out value="${book.title}"></c:out>
															</c:if>
														</h1> <c:if test="${ not empty book.authors }">
															<p style="color: #919191">
																<br /> <strong>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspby:</strong>
																<c:forEach items="${book.authors}" var="author">
																	<c:out value="${author.name}" />

																</c:forEach>
															<p>
															<h1 style="color: #919191">
																&nbsp&nbsp&nbsp&nbsp Current rating:
																<c:out value="${rating}" />
															</h1>
															<br>
														</c:if>
													</td>
												</tr>
											</table>
										</a>
										<br>
										<br>
										<br>
										<br>
										<br>
									</c:forEach>
								</c:when>
							</c:choose>
					</td>
					<td><c:choose>
							<c:when test="${sessionScope.loggedUser != null}">
								<h1 id="profilesH1">People you are following:</h1>

								<div align="right">
									<table align="right">
										<tr>
											<th></th>
											<th></th>

										</tr>
										<tr id="profiles">
										
											<td align="center"><c:forEach
													items="${user.followedPeople}" var="followedPerson">
													<a
														href="http://localhost:8080/ShowUser?id=${followedPerson.id}"><h1
															style="color: #919191">
															<strong>&nbsp&nbsp&nbsp&nbsp</strong><br>
															<c:out value="${followedPerson.name}"></c:out>
														</h1> <br> <img alt="Book Poster"
														src='<c:url value="${followedPerson.profilePicture}"></c:url>'
														width="60" height="100"></a>
													<br>
												</c:forEach></td>


										</tr>
									</table>
									<br> <br>
								</div>

							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose></td>
				</tr>
			</table>
			<br> <br>
			<hr />
			<br> <br> <br> <br> <br>


		</div>
	</div>
</body>
</html>