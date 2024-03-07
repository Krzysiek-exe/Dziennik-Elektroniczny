<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Zalogowano jako uczeń</title>
<jsp:directive.include file="elements/head.jsp" />
<link rel="stylesheet" href='css/student.css' >
</head>
<body>
		<h2>Panel ucznia</h2>
	    <div class="underline1"></div>
	    <h4 class="logged">Zalogowano: ${person.firstName} ${person.lastName} </h4>
	    <h4 class="logged">Numer albumu: ${person.id} </h4>
	    <h4 class="logged">Klasa: ${className} </h4>
        <h4 class="d-flex justify-content-center">Oceny</h4>
        <div class="underline2"></div>

        <div class="container">
			<table class="table" style="width:100%">
			<thead>
			<tr class="navcolor">
				<th style="width:30%" scope="col">Przedmiot</th>
				<th scope="col">Oceny</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${przedmioty}" var="list">
				<tr>
					<td class="align-middle">${list.key}</td>	
					<td>
						<c:forEach items="${list.value}" var="listItem">
							<button type="button" class="btn tt grade-${listItem.grade}" data-toggle="tooltip" data-placement="top" title="${listItem.description}">
		 						${listItem.grade}
							</button>
						</c:forEach>
					</td>
				</tr>
				</c:forEach>
			</tbody>
			</table>
		</div>
	<form action="login" method="get">
		<input type="submit" name="submit" value="Wyloguj" class="btn color logout" />
	</form>
	<div class="position-absolute top-0 end-0 mx-20">
	    <button type="button" class="btn color text-end" data-bs-toggle="modal" data-bs-target="#staticBackdrop"
	    onClick="newPasswordFunction(${person.id})">
        	Zmiana hasła
       </button>
	</div>
	  	  <!-- Modal -->
<form action="student/change" method="post" modelAttribute="student">
  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
    aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="staticBackdropLabel">Zmiana hasła</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form>
            <div class="form-group">
              <label for="grade">Podaj stare hasło</label>
              <input type="password" class="form-control" id="oldPassword" name="oldPassword" placeholder="Stare hasło">
            </div>
            <br>
            <div class="form-group">
              <label for="description">Podaj nowe hasło</label>
              <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="Nowe hasło">
            </div>
            <br>
            <div class="form-group">
              <label for="description">Powtórz nowe hasło</label>
              <input type="password" class="form-control" id="repeatNewPassword" name="repeatNewPassword" placeholder="Nowe hasło">
            </div>
            <div class="form-group">
              <input type="hidden" class="form-control" id="idParent" name="idParent">
            </div>
            <br>
            <input type="submit" name="submit" value="Zmień" class="btn color" />
          </form>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Zamknij</button>
        </div>
      </div>
    </div>
  </div>
</form>
	
	<script>
  		function newPasswordFunction(idParent){
  			document.getElementById("idParent").setAttribute('value', idParent);
  		}
  	</script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous">
        </script>
</body>
</html>