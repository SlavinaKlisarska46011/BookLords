<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<title>Booklords</title>
<meta charset="iso-8859-1">
<link rel="stylesheet" href="../../../static/css/styles/layout.css" type="text/css">
<link rel="stylesheet" href="../../../static/css/publicButton.css" type="text/css">
<link rel="stylesheet" type="text/css" href="../../../static/css/styles.css" />
<link rel="stylesheet" type="text/css" href="../../../static/css/stars.css" />


<style type="text/css">
.div2 {
	background-color: #F5FFFA;
	width: 400px;
	height: 30px;
	padding: 40px;
	border: 1px solid #FF6347;
}

.not-active {
	pointer-events: none;
	cursor: default;
}

.p {
	width: 350px;
}

#hr {
	width: 100%;
}

#commentIdDiv {
	background-color: #F5FFFA;
	border-radius: 25px;
	width: 50%;
	height: 60%;
	left: 40px;
}

#showComment {
	font-size: 100%;
	margin: 100%;
}

#nameP {
	margin: auto;
	background-color: #F5FFFA;
	border: 1px solid #FF6347;
	border-radius: 25px;
	position: absolute;
	width: 200px;
	height: auto;
	text-align: center;
	padding: 10px;
	word-wrap: break-word;
}

#divButton {
	position: relative;
	left: 400px;
	width: 10%;
	height: 1%;
}

#p2 {
	width: 90%;
}

#commentImg {
	cursor: pointer;
}

#commentImg:hover {
	filter: alpha(opacity=70);
	opacity: 0.7;
}
body {
	margin: 40px;
}
</style>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/comments.js">
	
</script>
<script>
	function showComment(text, event) {
		var tempX = event.pageX;
		var a = tempX + 40;
		var tempY = event.pageY;
		var msgbox = $("#showComment");
		msgbox.html('<p id="nameP" style="left: '+a+'px;top: '+tempY+'px;">'
				+ text + '</p>');
	}

	function clearComment() {
		var msgbox = $("#showComment");
		msgbox.empty();
	}

	function clearContents(element) {
		element.value = '';
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
	<div class="wrapper row2">
		<div id="container" class="clear">
			<hr>
			<br>
			<div id="book">
				<c:if test="${ not empty book }">
					<table>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
						<tr>
							<td><img alt="Book Poster"
								src='<c:url value="${book.posterURL}"/>' width="270"
								height="280"><br> <br>
								<hr> <br> <c:choose>
									<c:when test="${ not empty rated }">
										<h1>
											You have rated this book with rating:
											<c:out value="${rated}" />
										</h1>
									</c:when>
									<c:otherwise>
										<p>
										<h1 style="color: black">Rate this book:</h1>
										<c:choose>
											<c:when test="${sessionScope.loggedUser != null}">
												<span class="rating"> <input type="radio"
													class="rating-input" id="rating-input-1-5" name="rating"
													value="1"
													onclick="window.location='http://localhost:8080/ShowBook?id=${book.bookId}&rating=1';">
													<label for="rating-input-1-5" class="rating-star"></label>
													<input type="radio" class="rating-input"
													id="rating-input-1-4" name="rating" value="2"
													onclick="window.location='http://localhost:8080/ShowBook?id=${book.bookId}&rating=2';">
													<label for="rating-input-1-4" class="rating-star"></label>
													<input type="radio" class="rating-input"
													id="rating-input-1-3" name="rating" value="3"
													onclick="window.location='http://localhost:8080/ShowBook?id=${book.bookId}&rating=3';">
													<label for="rating-input-1-3" class="rating-star"></label>
													<input type="radio" class="rating-input"
													id="rating-input-1-2" name="rating" value="4"
													onclick="window.location='http://localhost:8080/ShowBook?id=${book.bookId}&rating=4';">
													<label for="rating-input-1-2" class="rating-star"></label>
													<input type="radio" class="rating-input"
													id="rating-input-1-1" name="rating" value="5"
													onclick="window.location='http://localhost:8080/ShowBook?id=${book.bookId}&rating=5';">
													<label for="rating-input-1-1" class="rating-star"></label>
												</span>
											</c:when>
										</c:choose>
									</c:otherwise>
								</c:choose></td>
							<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
							<td>
								<h1 style="color: black">
									<font size="5"> <strong><b>Title:</b></strong> <c:if
											test="${ not empty book.title }">
											<c:out value="${book.title}" />
										</c:if></font>

								</h1> <br>
								<p>
									<font size="3">Rating: <c:out value="${book.rating}"></c:out>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
										People rated:<c:out value="${book.numberOfRatings}" /></font>
								</p> <br> <br> <br />
								<p>
									<strong>ISBN:</strong> <b><c:out value="${book.ISBN}"></c:out></b>
								</p> <c:if test="${ not empty book.authors }">
									<p>
										<br> <strong>Authors:</strong>
										<c:forEach items="${book.authors}" var="author">
											<a
												href="http://localhost:8080/ShowAuthor?id=${author.id}"><c:out
													value="${author.name}" /></a>
										</c:forEach>
									</p>
								</c:if>
								<p>
									<br> <strong>Description:</strong>
									<c:out value="${book.description}" />
								</p>
								<p>
									<br> <strong>Pages:</strong>
									<c:out value="${book.pages}" />
								</p>
								<p>
									<br> <strong>Edition Language:</strong>
									<c:out value="${book.editionLanguage}" />
								</p>
								<p>
									<c:if test="${ not empty book.genres }">
										<br>
										<strong>Genres:</strong>
										<c:forEach items="${book.genres}" var="genre">
											<a
												href="http://localhost:8080/search?searchBy=genre&search=${genre}"><c:out
													value="${genre}" /></a>
										</c:forEach>
									</c:if>
								</p>
								<p>
									<br>
									<c:if test="${not empty book.readOnlineURL}">
										<c:if test="${book.readOnlineURL != 'none'}">
											<a href="${book.readOnlineURL}"/>
								<h3>
													<span class="label label-default">Read online</span>
												</h3>
								</a>
										</c:if>
										<c:if test="${book.readOnlineURL == 'none'}">
											<a href="${book.readOnlineURL}" class="not-active"><h3>
													<span class="label label-default">Read online</span>
												</h3></a>
										</c:if>
									</c:if>
								</p>

								<p>
									<br>
									<c:if test="${not empty book.buyOnlineURL}">
										<c:if test="${book.buyOnlineURL != 'none'}">
											<a href="${book.buyOnlineURL}"/><h3>
													<span class="label label-default">Buy online</span>
												</h3></a>
										</c:if>
										<c:if test="${book.buyOnlineURL == 'none'}">
											<a href="${book.buyOnlineURL}" class="not-active"><h3>
													<span class="label label-default">Buy online</span>
												</h3></a>
										</c:if>
									</c:if>
								</p>
							</td>
						</tr>
					</table>
				</c:if>
			</div>
		</div>
	</div>
	<br />
	<br>
	<br>
	<br>
	<div class="div1" align="center">
		<c:choose>
			<c:when test="${sessionScope.loggedUser != null}">
				<%--@elvariable id="comment" type="java"--%>
				<form:form modelAttribute="comment">
					<form:errors path="*" cssClass="errorblock" element="div" />
					<form:textarea type="text" onfocus="clearContents(this);"
						path="text" name="commentTextArea"
						placeholder="Ask anything about the book" maxlength="150"
						cols="63" rows="5" />
					<form:errors path="text" cssClass="error" />
					<form:input type="hidden" path="bookId" value="${book.bookId}" />
					<input type="submit" value="Post" />
				</form:form>
			</c:when>
			<c:otherwise>
				<textarea name="text" placeholder="Ask anything about the book"
					maxlength="150" cols="63" rows="5"></textarea>
				<input type="submit" value="Submit"
					onclick="window.location='http://localhost:8080//Login';" />
			</c:otherwise>
		</c:choose>
		<br>

		<c:if test="${not empty book.comments}">

			<c:forEach items="${book.comments}" var="comment">
				<div class="div2" align="left">
					<div id="divButton" align="right"
						onmousemove="showComment('${comment.text}',event)"
						onmouseout="clearComment()">
						<img src="images/comment.png" id="commentImg" alt="Mountain View"
							style="width: 40px; height: 40px;">
					</div>
					<c:if test="${loggedUser.id != comment.user.id}">
						<h2 align='left'>
							By: <a
								href="http://localhost:8080/ShowUser?id=${comment.user.id}"><c:out
									value="${comment.user.name}"></c:out></a>
						</h2>
					</c:if>
					<c:if test="${loggedUser.id == comment.user.id}">
						<h2 align='left'>
							By: <a href="http://localhost:8080/ViewProfile"><c:out
									value="${comment.user.name}"></c:out></a>
						</h2>
					</c:if>
					<h3 align='left'>
						Posted:
						<c:out value="${comment.date}"></c:out>
					</h3>

					<div id="showComment"></div>

				</div>
			</c:forEach>
		</c:if>
	</div>
	<div class="wrapper row3">
		<hr id="hr">
		<footer id="footer" class="clear"> </footer>
	</div>
</body>
</html>
