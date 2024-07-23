package entidad;

import java.util.Date;

public class cuotas {
	private int idCuota;
    private prestamo idPrestamo;
    private int numeroCuota;
    private Date fechaVencimiento;
    private double monto;
    private boolean pagada;
    private Date fechaPago;

    // Constructor vacío
    public cuotas() {
    	
    	idPrestamo = new prestamo();
    }

    // Constructor con parámetros
    public cuotas(int idCuota, int idPrestamos, int numeroCuota, Date fechaVencimiento, double monto, boolean pagada, Date fechaPago) {
        this.idCuota = idCuota;
        idPrestamo = new prestamo();
        idPrestamo.setIdPrestamo(idPrestamos);
        this.numeroCuota = numeroCuota;
        this.fechaVencimiento = fechaVencimiento;
        this.monto = monto;
        this.pagada = pagada;
        this.fechaPago = fechaPago;
    }

    // Getters y Setters
    
    public int getIdPrestamo() {
    	return idPrestamo.getIdPrestamo();
    }; 
    
    public void setIdPrestamo(int id_prestamo) {
    	idPrestamo.setIdPrestamo(id_prestamo);    	
    }
    
    
    
    
    public int getIdCuota() {
        return idCuota;
    }

    public void setIdCuota(int idCuota) {
        this.idCuota = idCuota;
    }

    public int getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(int numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    @Override
    public String toString() {
        return "Cuota [idCuota=" + idCuota + ", idPrestamo=" + idPrestamo.getIdPrestamo() + ", numeroCuota=" + numeroCuota 
                + ", fechaVencimiento=" + fechaVencimiento + ", monto=" + monto + ", pagada=" + pagada 
                + ", fechaPago=" + fechaPago + "]";
    }
}
