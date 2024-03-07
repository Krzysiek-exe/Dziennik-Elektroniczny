<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Zalogowano jako rodzic</title>
<jsp:directive.include file="elements/head.jsp" />
<link rel="stylesheet" href='css/student.css' >
</head>
<body>
		<h2>Panel rodzica</h2>
	    <div class="underline1"></div>
	    <h4 class="logged">Zalogowano: ${parent.firstName} ${parent.lastName} </h4>
        <h4 class="d-flex justify-content-center">Dzieci</h4>
        <div class="underline2"></div>

	    <ul class="nav nav-tabs justify-content-center" id="myTab" role="tablist">
	      <c:forEach items="${students}" var="list">
		      <li class="nav-item" role="presentation">
		        <button class="nav-link" id="${list.key.firstName}-tab" data-bs-toggle="tab" data-bs-target="#page${list.key.firstName}" type="button" role="tab" 
		        aria-controls="page${list.key.firstName}" aria-selected="false">${list.key.firstName}</button>
		      </li>
		  </c:forEach>
	    </ul>
	    <div class="tab-content" id="myTabContent">
	      <c:forEach items="${students}" var="list">
	      <div class="tab-pane fade" id="page${list.key.firstName}" role="tabpanel" aria-labelledby="${list.key.firstName}-tab">
				<div class="container">
				<table class="table" style="width:100%">
				<thead>
				<tr class="navcolor">
					<th style="width:30%" scope="col">Przedmiot</th>
					<th scope="col">Oceny</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${list.value}" var="prop">
						<tr>
							<td>${prop.key}</td>	
							<td>
							<c:forEach items="${prop.value}" var="listItem">
								<button type="button" class="btn tt grade-${listItem.grade}" data-toggle="tooltip" 
								data-placement="top" title="${listItem.description}">
 									 ${listItem.grade}
								</button>
							</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
				</div>
	      </div>
	      </c:forEach>
	    </div>
	    <div class="position-absolute top-0 end-0 mx-20">
	    <button type="button" class="btn color text-end" data-bs-toggle="modal" data-bs-target="#staticBackdrop"
	    onClick="newPasswordFunction(${parent.id})">
        	Zmiana hasła
       </button>
	    </div>
	    
  	  <!-- Modal -->
<form action="parent/change" method="post" modelAttribute="parent">
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
	<form action="login" method="get">
		<input type="submit" name="submit" value="Wyloguj" class="btn color logout" />
	</form>
	
	<script>
  		function newPasswordFunction(idParent){
  			document.getElementById("idParent").setAttribute('value', idParent);
  		}
  	</script>

	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
	integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
	crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
	integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF"
	crossorigin="anonymous"></script>
</body>
</html>