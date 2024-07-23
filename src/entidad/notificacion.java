package entidad;

import java.util.Date;

public class notificacion {
    private int idNotificacion;
    private cliente cliente; // Composición con Cliente
    private Date fecha;
    private String mensaje;
    private boolean leida;

    // Constructor vacío
    public notificacion() {
    }

    // Constructor con parámetros
    public notificacion(int idNotificacion, cliente cliente, Date fecha, String mensaje, boolean leida) {
        this.idNotificacion = idNotificacion;
        this.cliente = cliente;
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.leida = leida;
    }

    // Getters y Setters
    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }
}

