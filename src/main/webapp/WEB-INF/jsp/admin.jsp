<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Zalogowano jako admin</title>
<jsp:directive.include file="elements/head.jsp" />
<link rel="stylesheet" href='css/admin.css' >
</head>
<body>
    <h2 class="my-3">Panel administratora</h2>
    <div class="underline"></div>
    
    <div class="container d-inline row center-block">
        <div class="card-group d-flex justify-content-center">
            <div class="card text-white bg-primary mb-3 col mx-3" style="max-width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Dodaj</h5>
                    <p class="card-text">Dodaje użytkownika do bazy danych</p>
                    <a href="/admin/add" class="btn btn-secondary stretched-link">Dodaj użytkownika</a>
                </div>
            </div>
            <div class="card text-white bg-secondary mb-3 col" style="max-width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Usuń</h5>
                    <p class="card-text">Usuwa użytkownika z bazy danych</p>
                    <a href="/admin/delete" class="btn btn-success stretched-link">Usuń użytkownika</a>
                </div>
            </div>
            <div class="card text-white bg-success mb-3 col mx-3" style="max-width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">Zaktualizuj</h5>
                    <p class="card-text">Aktualizuje dane o istniejącym użytkowniku w bazie danych</p>
                    <a href="/admin/update" class="btn btn-primary stretched-link">Zaktualizuj użytkownika</a>
                </div>
            </div>
        </div>
    </div>
    <form action="login" method="get">
		<input type="submit" name="submit" value="Wyloguj" class="btn color logout" />
	</form>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous">
        </script>
</body>
</html>