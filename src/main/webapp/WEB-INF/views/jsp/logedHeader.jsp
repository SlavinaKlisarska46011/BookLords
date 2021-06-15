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
<link rel="stylesheet" href="css/styles/dropdownButton.css"
	type="text/css">
<!--[if lt IE 9]><script src="scripts/html5shiv.js"></script><![endif]-->
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
</head>

<body>

	<div class="wrapper row1">
		<header id="header" class="clear">
			<div id="hgroup">
				<h1>
					<a href="http://localhost:8080/BookLords/">BookLords</a>
				</h1>
				<h2>Once you learn to read, you will be forever free</h2>
			</div>
			<nav>
				<ul>
					<li><a href="http://localhost:8080/BookLords/">Home</a></li>
					<li class="dropdown"><a style="cursor: pointer;">My Books</a>
					<c:choose>
					<c:when test="${sessionScope.loggedUser != null}">
					<c:set var="user" scope="session" value="${sessionScope.loggedUser}"/>
					<div class="dropdown-content">
												<c:forEach items="${user.bookshelves}" var="bookshelf">
													<a href="http://localhost:8080/BookLords/MyBooks?bookshelfId=${bookshelf.id}"><c:out
															value="${bookshelf.name}"/></a>
												</c:forEach>
												<a href="http://localhost:8080/BookLords/MyBooks">Add Bookshelf</a>
											</div>
											</c:when></c:choose>
					</li>
					
					<li class="dropdown"><a class="dropbtn" style="cursor: pointer;">Community</a>
						<div class="dropdown-content">
							<a href="http://localhost:8080/SearchPeople">People</a>
						</div></li>
							<li class="dropdown"><a class="dropbtn" style="cursor: pointer;">My Profile</a>
						<div class="dropdown-content">
							<a href="http://localhost:8080/BookLords/ViewProfile">View Profile</a>
							<a href="http://localhost:8080/BookLords/MyComments">My Comments</a> <a href="http://localhost:8080/BookLords/SignOut">Sign Out</a>
						</div></li>
				</ul>
			</nav>
		</header>
	</div>
	<div><jsp:include page="searchingBar.jsp" /></div>
</body>
</html>