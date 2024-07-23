package negocioImpl;

import java.util.ArrayList;

import datosImpl.pagosDaoImpl;
import entidad.pago;
import negocio.negocioPago;

public class negocioPagosImpl implements negocioPago {

	private pagosDaoImpl negocioPago;
	
	public negocioPagosImpl() {
        this.negocioPago = new pagosDaoImpl();
    }
	
	@Override
	public ArrayList<pago> obtenerPagosPorIdCliente(int idCliente) {
		// TODO Auto-generated method stub
		return negocioPago.obtenerPagosPorIdCliente(idCliente);
	}

}
