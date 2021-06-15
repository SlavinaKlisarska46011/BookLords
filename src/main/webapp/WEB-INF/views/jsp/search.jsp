<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<title>Booklords</title>
<meta charset="iso-8859-1">
<!--[if lt IE 9]><script src="scripts/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>


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

.locked {
   pointer-events: none;
   cursor: default;
}
</style>
<script type="text/javascript">
	
	
function sendRequestBookShelf(bookId, bookShelfId) {
	console.log(bookId);
	console.log(bookShelfId);
	var url = "http://localhost:8080/BookLords/AddBookToBookshelf?bookId=" + bookId + "&bookshelfId=" + bookShelfId;

		$
				.ajax({
					url : url,
					type : "GET",
					complete : function(jqXHR) {
						if (jqXHR.status == 200) {
							console.log(jqXHR.status);
						}
					}

				});
}
	
</script>
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
	<div id="container" style="color: #919191">
		<div style="background-color: #232323">
			<div class="beauty">
				<br> <br>
				<h1 style="font-size: 130%">Search and browse books</h1>
			</div>
			<form:form action="./search" id="searchForm">
				<input name="search" id="s" type="text" />
				<input type="submit" value="Submit" id="submitButton" />
				<div class="beautiful">
					<br> <input type="radio" name="searchBy" value="title"
						checked="checked">&nbsp&nbsptitle&nbsp&nbsp&nbsp&nbsp <input
						type="radio" name="searchBy" value="author" />&nbsp&nbspauthor&nbsp&nbsp&nbsp&nbsp
					<input type="radio" name="searchBy" value="genre" />&nbsp&nbspgenre&nbsp&nbsp&nbsp&nbsp
				</div>
			</form:form>
			<div id="books">
				<c:choose>
					<c:when test="${not empty books}">

						<c:forEach items="${books}" var="book">
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
														<c:out value="${author.name}"/>

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
							<c:choose>
								<c:when test="${ not empty user}">
									<div class="center">
										<li id="${book.bookId}" class="dropdown"><a style="cursor: pointer;" class="dropbtn"
											style="color: black">Add To My Books</a>
											<div class="dropdown-content">
												<c:forEach items="${user.bookshelves}" var="bookshelf">
													<a style="cursor: pointer;" onclick="sendRequestBookShelf('${book.bookId}','${bookshelf.id}')"><c:out
															value="${bookshelf.name}"></c:out></a>
												</c:forEach>
											</div></li>
									</div>

								</c:when>
								<c:otherwise>
									<div class="center">
										<h3 class="dropdown">
											<a href="http://localhost:8080/Login"
												class="dropbtn" style="color: black">Add To My Books</a>
										</h3>
									</div>
								</c:otherwise>
							</c:choose>
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
							<b>No books found.</b>
						</h3>
					</c:otherwise>
				</c:choose>
			</div>

			<div id="intro"></div>
		</div>
	</div>
	<!-- Footer -->
	<div class="wrapper row3">
		<footer id="footer" class="clear">
			<p class="fl_left"></p>
			<p class="fl_right">
				Template by <a href="http://www.os-templates.com/"
					title="Free Website Templates">OS Templates</a>
			</p>
		</footer>
	</div>

</body>
</html>
