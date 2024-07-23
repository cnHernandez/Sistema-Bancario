package negocio;

import java.util.ArrayList;

import entidad.pago;

public interface negocioPago {
	ArrayList<pago> obtenerPagosPorIdCliente(int idCliente);
}
