package excepciones;

public class InsertarLocalidadException extends LocalidadException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsertarLocalidadException(String message) {
        super(message);
    }

    public InsertarLocalidadException(String message, Throwable cause) {
        super(message, cause);
    }

}
