<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet"> 
    <link href="CSS/styles.css" rel="stylesheet">
</head>
<body>


    <nav class="navbar navbar-expand-lg navbar-light navbar-custom">
        <div class="container">
            <img src="https://cdn-icons-png.flaticon.com/128/1086/1086741.png" alt="Logo Banco Pacheco" style="max-height: 40px; margin-right: 10px;">
            <a class="navbar-brand" href="<%
                Integer tipoUsuario = (Integer) session.getAttribute("tipoUsuario");
                if (tipoUsuario != null) {
                    if (tipoUsuario == 1) {
                        out.print("Principal.jsp");
                    } else {
                        out.print("PrincipalCliente.jsp");
                    }
                } else {
                    out.print("login.jsp"); 
                }
            %>">Banco Pacheco avanza</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                    <% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 1) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="Principal.jsp">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <p class="nav-link">¡Hola <%= session.getAttribute("nombre") %>!</p>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="ServletLogin?action=logout">Cerrar sesión</a>
                    </li>
                    <% } else if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 2) { %>
                    <li class="nav-item">
					    <a class="nav-link" href="notificaciones.jsp">
					        <div class="notification-icon">
					            <i class="fa fa-envelope fa-2x"></i>
					            <span class="notification-badge" id="notificationCount">0</span>
					        </div>
					    </a>
					</li>
                    <li class="nav-item">
                        <a class="nav-link" href="ServletClientes?action=completarMisDatos">Mis Datos</a>
                    </li>
                    <li class="nav-item">
                        <p class="nav-link" >¡Hola <%= session.getAttribute("nombre") %>!</p>
                    </li>					
                    <li class="nav-item">
                        <a class="nav-link" href="ServletLogin?action=logout">Cerrar sesión</a>
                    </li>
                    <% } else if (session.getAttribute("tipoUsuario") == null){ %>
                    <li class="nav-item">
                        <p class="nav-link" >¡Hola es un gusto volverte a ver!</p>
                    </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </nav>
    <% if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 1) { %>
    <nav id="menuLateral" class="menu-lateral">
        <h5 class="mb-3 text-black">Menú</h5>
        <ul>
            <li><a href="ServletClientes?action=listar">Clientes</a></li>
            <li><a href="ServletUsuario?action=listarTodos">Usuarios</a></li>
            <li><a href="ServletSolicitudes">Solicitudes de Prestamos</a></li>
            <li><a href="Reportes.jsp">Reportes</a></li>
        </ul>
    </nav>
    <% } else if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 2) { %>
    <nav id="menuLateral" class="menu-lateral">
        <h5 class="mb-3 text-black">Menú</h5>
        <ul>
            <%
			    Integer idCliente = (Integer) session.getAttribute("idCliente");
			    String link = "ServletCuentas?action=listar";
			    if (idCliente != null) {
			        link += "&idCliente=" + idCliente;
			    }
			%>
			<li><a href="<%= link %>">Ver mis cuentas</a></li>
            <li><a href="solicitarPrestamo.jsp">Solicitar Préstamo</a></li>
            <li><a href="listarPrestamos?">Ver mis préstamos</a></li>
            <li><a href="ServletPagos?">Ver mis pagos</a></li>
            <li><a href="Transferencia.jsp">Transferencia</a></li>
            <li><a href="notificaciones.jsp">Notificaciones</a></li>
        </ul>
    </nav>
    <% } %>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    $(document).ready(function() {
        var menuVisible = false;
        var menuWidth = $('#menuLateral').outerWidth();

        $('#menuLateral').css('left', -menuWidth + 'px'); // Ocultar el menú al principio

        $(document).mousemove(function(e) {
            if (e.pageX < 50 && !menuVisible) { // Mostrar el menú si el mouse está cerca del borde izquierdo
                $('#menuLateral').css('left', '0');
                menuVisible = true;
            } else if (e.pageX > menuWidth + 50 && menuVisible) { // Ocultar el menú si el mouse está fuera del menú
                $('#menuLateral').css('left', -menuWidth + 'px');
                menuVisible = false;
            }
        });
    });
    </script>
    <script>
    $(document).ready(function() {
        // Función para actualizar el contador de notificaciones
        function actualizarContadorNotificaciones() {
            $.ajax({
                type: "GET",
                url: "ServletNotificacion", // Ajusta la URL según tu configuración
                success: function(response) {
                    console.log("Respuesta del servidor: ", response); // Añade esta línea
                    $("#notificationCount").text(response);
                },
                error: function() {
                    console.error('Error al obtener el contador de notificaciones.');
                }
            });
        }

        // Llamar a la función inicialmente al cargar la página
        actualizarContadorNotificaciones();

        // Actualizar cada 30 segundos (30000 milisegundos)
        setInterval(actualizarContadorNotificaciones, 30000);
    });
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>