package excepciones;

public class ObtenerProvinciaPorNombreException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObtenerProvinciaPorNombreException(String message) {
        super(message);
    }

    public ObtenerProvinciaPorNombreException(String message, Throwable cause) {
        super(message, cause);
    }

}
