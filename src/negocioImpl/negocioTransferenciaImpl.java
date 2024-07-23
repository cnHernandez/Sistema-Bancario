package negocioImpl;

import datosImpl.transferenciaDaoImpl;
import negocio.negocioTransferencia;

public class negocioTransferenciaImpl implements negocioTransferencia{

	private transferenciaDaoImpl trans;
	
	public negocioTransferenciaImpl(){
		trans = new transferenciaDaoImpl();
	}
	
	@Override
	public boolean insertarTransferencia(int Origen, int Destino) {
		return trans.insertarTransferencia(Origen, Destino);
	}

}
