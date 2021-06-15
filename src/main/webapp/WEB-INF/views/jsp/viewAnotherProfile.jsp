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
									<th></th>
								</tr>
								<tr>
									<td><img alt="Profile picture"
										src='<c:url value="${user.profilePicture}"></c:url>'
										width="270" height="280"></td>
									<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
									<td>
										<h1 style="color: black">
											<strong><b>Name:</b></strong>
											<c:if test="${ not empty user.name }">
												<c:out value="${user.name}"></c:out>
											</c:if>
											<br> <br> <br />
										</h1>
										<p>
											<strong>Joined: </strong> <b><c:out
													value="${user.activity}"></c:out></b>
										</p>
										<p>
											<br> <br> <br> <br> <br> <br> <br>
											<br>
											<c:choose>
												<c:when test="${not empty followedUser }">
													<form action="./ShowUser" method="POST">
														<input type="hidden" name="userId" value="${user.id}" />
														<input type="hidden" name="followOrUnfollow"
															value="unfollow" /> <input type="submit" id="submit"
															value="Unfollow" />
													</form>
												</c:when>
												<c:otherwise>
													<form action="./ShowUser" method="POST">
														<input type="hidden" name="userId" value="${user.id}" />
														<input type="hidden" name="followOrUnfollow"
															value="follow" /> <input type="submit" id="submit"
															value="Follow" />
													</form>
												</c:otherwise>
											</c:choose>
										</p>
									</td>
									<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>
									<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</td>

									<td style="text-align: right">

										<p>
											<c:if test="${ not empty user.followers }">
												<strong><c:out value="${user.name}"></c:out> is
													followed by:</strong>
												<c:forEach items="${user.followers}" var="follower">
													<p align="center">
														<img alt="Profile picture"
															src='<c:url value="${follower.profilePicture}"></c:url>'
															width="40" height="50">
														<c:out value="${follower.name}"></c:out>
													</p>
												</c:forEach>
											</c:if>
										</p>
										<br>
									<br>
										<p>
											<c:if test="${ not empty user.followedPeople }">
												<strong><c:out value="${user.name}"></c:out> is
													following:</strong>
												<br>
												<br>
												<br>
												<c:forEach items="${user.followedPeople}"
													var="followedPerson">
													<img alt="Profile picture"
														src='<c:url value="${followedPerson.profilePicture}"></c:url>'
														width="40" height="50">
													<c:out value="${followedPerson.name}"></c:out>
													<br>
												</c:forEach>
											</c:if>
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
		</c:when>
		<c:otherwise>
			<div><jsp:include page="login.jsp" /></div>
		</c:otherwise>
	</c:choose>
</body>
</html>