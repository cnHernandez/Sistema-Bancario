<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidad.cuenta" %>
<%@ include file="navbar.jsp" %> 
<%
    ArrayList<cuenta> cuentas = null;
    String cbu = (String) session.getAttribute("cbu");
    if (session.getAttribute("tipoUsuario") != null && (int) session.getAttribute("tipoUsuario") == 2) {
        cuentas = (ArrayList<cuenta>) session.getAttribute("cuentas");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Transferencia</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script>
        function validarFormulario() {
            let cbu = document.forms["formTransferencia"]["txtCbu"].value;
            let monto = document.forms["formTransferencia"]["txtMonto"].value;
            let cuenta = document.forms["formTransferencia"]["idCuenta"].value;
            let mensajeError = "";

            if (cbu == "") {
                mensajeError += "El CBU es obligatorio.\n";
            } else if (!/^\d{22}$/.test(cbu)) {
                mensajeError += "El CBU debe tener 22 dígitos.\n";
            }

            if (monto == "") {
                mensajeError += "El monto es obligatorio.\n";
            } else if (isNaN(monto) || parseFloat(monto) <= 0) {
                mensajeError += "El monto debe ser un número positivo.\n";
            }

            if (cuenta == "") {
                mensajeError += "Debe seleccionar una cuenta.\n";
            }

            if (mensajeError != "") {
                alert(mensajeError);
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <div class="container custom-container">
        <h1 class="display-4 text-center mb-4 h2-reportes">Transferencia</h1>
        <form name="formBuscarCbu" action="ServletTranferencia" method="get" class="mb-3">
            <div class="form-group">
                <label for="txtCbu">Ingrese el CBU:</label>
                <input type="text" name="txtCbu" class="form-control" maxlength="22">
            </div>
            <button type="submit" name="btnBuscarCbu" class="btn btn-primary">Buscar</button>
            <%
                String msj = (String) request.getAttribute("msj");
                if (msj != null) {
            %>
                <p class="text-danger mt-2"><%= msj %></p>
            <%
                }
            %>
        </form>

        <form name="formTransferencia" action="ServletTranferencia" method="post" onsubmit="return validarFormulario();">
            <input type="hidden" name="cbu" value="<%= cbu %>">

            <div class="form-group">
                <label for="txtMonto">Ingrese monto:</label>
                <input type="number" name="txtMonto" class="form-control" step="0.01" min="0">
            </div>

            <div class="form-group">
                <label for="idCuenta">Seleccionar cuenta:</label>
                <select id="idCuenta" name="idCuenta" class="form-control" required>
                    <% 
                        if (cuentas != null && !cuentas.isEmpty()) {
                            for (cuenta c : cuentas) {
                    %>
                        <option value="<%= c.getIdCuenta() %>">
                            Número cta.: <%= c.getNumeroCuenta() %>, Saldo Disponible:$ <%= c.getSaldo() %>, Tipo de cuenta: 
                            <%
                                if (c.getId_tipo_cuenta() == 1) {
                            %>
                                Caja de ahorro
                            <%
                                } else if (c.getId_tipo_cuenta() == 2) {
                            %>
                                Cuenta corriente
                            <%
                                }
                            %>
                        </option>
                    <%
                            }
                        } else {
                    %>
                        <option value="">No hay cuentas disponibles</option>
                    <%
                        }
                    %>
                </select>
            </div>
            <button type="submit" name="btnConfirmarTransferencia" class="btn btn-success">Transferir</button>

        </form>
         <div class="text-center mt-3">
            <a href="PrincipalCliente.jsp" class="btn btn-outline-primary">Volver</a>
        </div>
        
    </div>
    <div class="container mt-4">
    <div class="row">
        <div class="col-md-6 offset-md-3"> 
            <% String error = (String) request.getAttribute("error"); %>
            <% Boolean band = (Boolean) request.getAttribute("bandera"); %>
            <% if (band != null) { %>
                <div class="alert <%= band ? "alert-success" : "alert-danger" %> alert-dismissible fade show" role="alert">
                    <%= band ? "Operación realizada correctamente!" : error %>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            <% } %>
        </div>
    </div>
</div>

</body>
</html>