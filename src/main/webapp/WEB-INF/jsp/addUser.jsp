<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="elements/head.jsp" />
<link rel="stylesheet" href='css/adminFunctions.css' >
</head>
<body>
  	<form:form method="POST" action="${pageContext.request.contextPath}/admin/add" modelAttribute="query">
        <div class="container py-4 my-10">
            <div class="card-group">
                <div class="card mx-3 mb-3">
                    <div class="card-header"><i class="fa-solid fa-plus"></i> Dodaj użytkownika</div>
                    <div class="card-body">
                        <div class="card-text">
                            <p>Nazwisko: <input type="text" name="lastName" /></p>
                            <p>Imie: <input type="text" name="firstName" /></p>
                            <p>Zawód: <input type="text" name="zawod_user" /></p>
                            <p>Login: <input type="text" name="nick_user" /></p>
                            <p>Hasło: <input type="text" name="password" /></p>
                            <input class="button" type="submit" value="Dodaj" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <button type="button" class="btn mx-4 btn-primary" onclick="history.go(-1)">Wstecz</button> 
      </form:form>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous">
        </script>
</body>
</html>