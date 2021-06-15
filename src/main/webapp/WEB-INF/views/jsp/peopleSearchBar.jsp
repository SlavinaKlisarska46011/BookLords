<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<style type="text/css">
@import url(https://fonts.googleapis.com/css?family=Open+Sans);

.searchA1 {
	width: 75%;
	right: 50px;
	top: 105px;
}

.searchTerm1:focus {
	color: #1E90FF;
}

.searchButton1 {
	position: absolute;
	left: 295px;
	top: 180px;
	width: 40px;
	height: 42px;
	border: 1px solid #1E90FF;
	background: #1E90FF;
	text-align: center;
	color: #fff;
	border-radius: 5px;
	cursor: pointer;
	font-size: 20px;
}

#search1 {
	position: absolute;
	width: 260px;
	left: 20px;
	top: 180px;
	border: 3px solid #1E90FF;
	padding: 8px;
	height: 20px;
	border-radius: 5px;
	outline: none;
	color: #9DBFAF;
}

#searchLabel1 {
	position: absolute;
	left: 850px;
	top: 170px;
}

#products1 {
	position: absolute;
	left: 30px;
	top: 222px;
}
}
</style>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>

<script type="text/javascript">
	function reloadSuggestionsName() {
		var text = $("#search1").val();

		var url = "http://localhost:8080/SearchPeopleByName?prefix="
				+ text;

		$
				.ajax({
					url : url,
					dataType : "json",
					success : function(data) {
						$("#products1").empty();
						if (data != null) {
							var json = jQuery.parseJSON(JSON.stringify(data));
							var iterations = 0;
							if (json.length > 5) {
								iterations = 5;
							} else {
								iterations = json.length;
							}
							for (index = 0; index < iterations; index++) {
								var object = json[index];
								if (object != null) {

									var container = document.createElement("a");
									container.href = "http://localhost:8080/BookLords/ShowUser?id="
											+ object.id;

									var containerDiv = document
											.createElement("div");
									containerDiv.setAttribute("id", "Div1");
									containerDiv.style.cssText = 'color:black;background-color: #F8F8FF	;display: table;width:260px;border: 1px solid #808080;';
									container.appendChild(containerDiv);
									
									var photo = document.createElement("img");
									photo.src = 'profile_pics/'
											+ object.profilePicture;
									photo.style.cssText = 'float:left;border:1px solid #021a40;;';
									photo.width = 40;
									photo.height = 40;

									containerDiv.appendChild(photo);

									var name = document.createElement("h1");
									name.style.cssText = 'float:middle;text-align: center;vertical-align: middle;';
									if (object.name.length > 18) {
										name.innerHTML = object.name.substring(
												0, 18)
												+ "...";
									} else {
										name.innerHTML = object.name;
									}

									containerDiv.appendChild(name);

									$("#products1").append(container);
								}
							}
						}
					}

				});
	}
</script>
</head>
<body>

<form:form action="./searchByName" id="searchForm1">
	<div class="wrap">
		<div class="searchA1">
			<input type="text" name="searchName" autocomplete="off" placeholder="Search people"
				id="search1" class="searchTerm1" onkeyup="reloadSuggestionsName()" />
			<a href="http://localhost:8080/searchByGenre">
				<button type="submit" class="searchButton1">
					<img src="images/search.png" height="20" width="20"> <i
						class="fa fa-search1"></i>
				</button>
			</a>
		</div>
	</div>
	
	</form:form>

	<div id="products1"></div>
</body>
</html>