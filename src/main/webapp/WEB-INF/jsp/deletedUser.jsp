<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Usunięto uzytkownika</title>
<jsp:directive.include file="elements/head.jsp" />
<link rel="stylesheet" href='css/admin.css' >
</head>
<body>
	<h2>${message}</h2>

	<a href="/admin/delete" class="btn btn-primary stretched-link my-3"> << Wstecz</a>
	
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous">
        </script>
</body>
</html>