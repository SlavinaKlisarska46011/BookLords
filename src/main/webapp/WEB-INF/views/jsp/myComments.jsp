<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BookLords</title>
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
			<div class="beauty">
				<br> <br>
				
			</div>
			
			<div id="booksWithComments">
				<c:choose>
					<c:when test="${not empty booksWithComments}">

						<c:forEach items="${booksWithComments}" var="entry">
							<c:set var="book" value="${entry.key}" />
							<c:set var="comment" value="${entry.value}" />
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
											src='<c:url value="${book.posterURL}"></c:url>' width="130"
											height="200"></td>
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
													<br>
												</p>
											</c:if>
										</td>

									</tr>
								</table>
							</a>
							<br>
							<br>
							<h1 style="color: #919191">
								<strong>&nbsp&nbsp&nbsp&nbspComment:</strong>
								<c:if test="${ not empty comment }">
													&nbsp&nbsp&nbsp&nbsp<c:out value="${comment}"></c:out>
								</c:if>
							</h1>
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
							<b>No comments yet.</b>
						</h3>
					</c:otherwise>
				</c:choose>
			</div>

			<div id="intro"></div>
		</div>
	</div>
</body>
</html>