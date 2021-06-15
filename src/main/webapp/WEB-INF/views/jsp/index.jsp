<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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

	<div><jsp:include page="notLogedHeader.jsp" /></div>

	<div class="wrapper row2">
		<div id="container" class="clear">
			<h3 style="color: black">Search and browse books</h3>
			<hr>
			<br> <br>
			<table>
				<tr>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
				<tr>
					<td><div>
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=Art">Art</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=Biography & Autobiography">Biography</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=Automobiles">Automobiles</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=Body, Mind">Comedy
								Lit</a><br /> <a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=Business & Economics">Business & Economics</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=christian">Christian</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=classics">Classics</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=comics">Comics</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=contemporary">Contemporary</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=cookbooks">Cookbooks</a><br />
						</div></td>
					<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
					<td><div>
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=crime">Crime</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=Computers">Computers</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=fantasy">Fantasy</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=fiction">Fiction</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=Games">Games</a><br /> <a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=graphic-novels">Graphic
								Novels</a><br /> <a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=historical-fiction">Historical
								Fiction</a><br /> <a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=history">History</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=horror">Horror</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=humor-and-comedy">Humor
								and Comedy</a><br />
						</div></td>
					<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
					<td>
						<div>
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=Mathematics">Mathematics</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=memoir">Memoir</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=medical">Medical</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=Music">Music</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=non-fiction">Nonfiction</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=pets">Pets</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=philosophy">Philosophy</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=poetry">Poetry</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=psychology">Psychology</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=religion">Religion</a><br />
						</div>
					</td>
					<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
					<td><div>
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=romance">Romance</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=science">Science</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=science-fiction">Science
								Fiction</a><br /> <a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=self-help">Self
								Help</a><br /> <a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=Social Science">Social Science</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=spirituality">Spirituality</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=sports">Sports</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=thriller">Thriller</a><br />
							<a class="actionLinkLite"
								href="http://localhost:8080/search?searchBy=genre&search=travel">Travel</a><br />
							<a class="actionLink"
								href="http://localhost:8080/search?searchBy=genre&search=Books">More genres..</a>
						</div></td>
				</tr>
			</table>
			<br> <br><br>
			<h3 style="color: black">Quote Of The Day</h3>
			<hr>
			<br>

			<div id="intro">
				<section class="clear">

					<article class="two_quarter">
						<h2>
							<c:if test="${ not empty quote }">

								<c:out value="${quote}" />


							</c:if>
						</h2>
					</article>
				</section>
			</div>

		</div>
	</div>
	<div class="wrapper row3">
		<footer id="footer" class="clear">
			
		</footer>
	</div>
</body>
</html>
