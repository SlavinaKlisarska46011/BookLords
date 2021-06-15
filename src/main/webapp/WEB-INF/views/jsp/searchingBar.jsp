<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<style type="text/css">
@import url(https://fonts.googleapis.com/css?family=Open+Sans);

.searchA {
	width: 75%;
	right: 50px;
	top: 105px;
}

.searchTerm:focus {
	color: #FF4500;
}

.searchButton {
	position: absolute;
	left: 1162px;
	top: 95px;
	width: 40px;
	height: 42px;
	border: 1px solid #FF4500;
	background: #FF4500;
	text-align: center;
	color: #fff;
	border-radius: 5px;
	cursor: pointer;
	font-size: 20px;
}

#search {
	position: absolute;
	width: 18.3%;
	left: 900px;
	top: 95px;
	border: 3px solid #FF4500;
	padding: 8px;
	height: 20px;
	border-radius: 5px;
	outline: none;
	color: #9DBFAF;
}

#searchLabel {
	position: absolute;
	left: 850px;
	top: 170px;
}

#products {
	position: absolute;
	left: 901px;
	top: 137px;
	a
	{
	color
	:
	black;
}

img {
	float: left;
}
}
</style>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>

<script type="text/javascript">
	function reloadSuggestions() {
		var text = $("#search").val();

		var url = "http://localhost:8080/Search?prefix=" + text;

		$
				.ajax({
					url : url,
					dataType : "json",
					success : function(data) {
						$("#products").empty();
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
									container.href = "http://localhost:8080/ShowBook?id="
											+ object.bookId;

									var containerDiv = document
											.createElement("div");
									containerDiv.setAttribute("id", "Div1");
									containerDiv.style.cssText = 'color:black;background-color: #F8F8FF	;display: table;width:260px;border: 1px solid #808080;';
									container.appendChild(containerDiv);

									var title = document.createElement("p");
									title.style.cssText = 'float:right;text-align: center;vertical-align: middle;display: table-cell;';
									if (object.title.length > 30) {
										title.innerHTML = object.title
												.substring(0, 30)
												+ "...";
									} else {
										title.innerHTML = object.title;
									}

									containerDiv.appendChild(title);

									var photo = document.createElement("img");
									photo.src = object.posterURL;
									photo.style.cssText = 'float:left';
									photo.width = 40;
									photo.height = 40;

									containerDiv.appendChild(photo);

									$("#products").append(container);
								}
							}
						}
					}

				});
	}
</script>
</head>
<body>

<div class="wrap">
   <div class="searchA">
   <form action="http://localhost:8080/search?searchBy=title" method="GET">
	<input type="text" autocomplete="off" placeholder="Search books" name="search" id="search" class="searchTerm"
		onkeyup="reloadSuggestions()" />	<button type="submit" class="searchButton">
		<img src="images/search.png" height="20" width="20">
        <i class="fa fa-search"></i>
     </button></form>
       </div>
</div>

	<div id="products"></div>
</body>
</html>