package excepciones;

public class ObtenerProvinciaPorIdException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObtenerProvinciaPorIdException(String message) {
        super(message);
    }

    public ObtenerProvinciaPorIdException(String message, Throwable cause) {
        super(message, cause);
    }

}
