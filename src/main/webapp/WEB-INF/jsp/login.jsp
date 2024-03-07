<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Logowanie</title>
<jsp:directive.include file="elements/head.jsp" />
<link rel="stylesheet" href='css/login.css' >
</head>
<body>
  <form:form method="POST" action="${pageContext.request.contextPath}/login" modelAttribute="query">
            <div class="container">
              <h1>Morfeo</h1>
      	      <h4>Logowanie do systemu Morfeo</h4>
              <p>Login:</p>
              <form:input path="login" class="input-text" placeholder="Login" id="login"  type="text"/>
              <p>Hasło:</p>
              <form:input path="password" class="input-text" placeholder="Password" id="password"  type="password"/>
              
              <input class="button" type="submit" value="Zaloguj" />
            </div>
          </form:form>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous">
    </script>
</body>
</html>