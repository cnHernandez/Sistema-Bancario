<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="navbar.jsp" %>
<% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 1) { %>  
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="CSS/styles.css" rel="stylesheet">
    <style>
        .custom-container {
    display: flex;
    flex-direction: column; 
    justify-content: center;
    align-items: center;
    padding: 40px; 
    background-color: #B0C4DE; 
    border: 1px solid #ced4da; 
    border-radius: 8px; 
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    margin-top: 80px;
    max-height: 80vh; 
    overflow-y: auto;
}

.custom-container h1 {
    margin-bottom: 20px; 
}

        .custom-button:hover {
            background-color: #0056b3;
        }
        .custom-container img {
    max-width: 100%; 
    margin-bottom: 20px; 
}
        
    </style>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container custom-container">
    <h1>¡Bienvenido Administrador!</h1>
    <p>Desliza a la izquierda para abrir el menú</p>
</div>

<div class="container custom-container">
    <img src="https://cdn-icons-png.flaticon.com/128/11689/11689300.png" alt="Descripción de la imagen">
    <h1>¡ABML!</h1>
    <p>Esta interfaz es para gestionar los ABML de los clientes.</p>
</div>

</body>
</html>
<% } else { %>
    <%@ include file="Error.jsp" %>
<% } %>
