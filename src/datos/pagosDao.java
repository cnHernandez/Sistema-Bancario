package datos;

import java.util.ArrayList;

import entidad.pago;

public interface pagosDao {
	public ArrayList<pago> obtenerPagosPorIdCliente(int idCliente);
}
