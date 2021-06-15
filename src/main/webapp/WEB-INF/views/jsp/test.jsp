<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File upload</title>

<script type="text/javascript">
function Validatebodypanelbumper(theForm)
{
   var regexp;
   var extension = new FormData(theForm).get("file").value.lastIndexOf('.');
   if ((extension.toLowerCase() != ".png") &&
       (extension.toLowerCase() != ".jpg") &&
       (extension != ""))
   {
      alert("The \"FileUpload\" field contains an unapproved filename.");
      theForm.file.focus();
      return false;
   }
   return true;
}
</script>
</head>

<body>
	<div>
		<form method="POST" enctype="multipart/form-data" onsubmit="Validatebodypanelbumper()">
			<table>
				<tr><td>File to upload:</td><td>
				<input type="file" id="file" name="file" accept="image/*" /></td></tr>
				<tr><td></td><td><input type="submit" value="Upload" /></td></tr>
			</table>
		</form>
	</div>
</body>
</html>