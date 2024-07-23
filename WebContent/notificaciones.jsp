<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="entidad.notificacion" %>
<%@ page import="java.util.ArrayList" %>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Notificaciones de Préstamos</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="CSS///styles.css" rel="stylesheet">
</head>
<body class="body-noti">
  <div class="container d-flex justify-content-center align-items-start min-vh-100" style="margin-top: 50px;">


    <div class="card shadow-lg p-4" style="width: 100%; max-width: 800px;">
      <h2 class="card-title">Notificaciones</h2>
      <div class="card-body">
        <div class="list-group">
          <% 
            ArrayList<notificacion> notificaciones = (ArrayList<notificacion>) session.getAttribute("notificaciones");
            if (notificaciones != null && !notificaciones.isEmpty()) {
              for (notificacion notif : notificaciones) {
                String claseNotif = notif.isLeida() ? "notificacion list-group-item leida" : "notificacion list-group-item";
          %>
                <div class="<%= claseNotif %> chat-message received">
                  <p class="mb-1"><%= notif.getMensaje() %></p>
                  <small><%= notif.getFecha() %></small>
                  <form action="ServletNotificacion" method="POST" style="display:inline;">
                    <input type="hidden" name="idNotificacion" value="<%= notif.getIdNotificacion() %>">
                    <button type="submit" class="btn btn-sm btn-primary float-right">
                      <i class="fa fa-check"></i>
                    </button>
                  </form>
                </div>
          <% 
              }
            } else {
          %>
              <p>No hay notificaciones disponibles.</p>
          <% } %>
        </div>
      </div>
    </div>
  </div>
</body>
</html>

