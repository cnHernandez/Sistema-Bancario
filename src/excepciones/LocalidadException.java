package excepciones;

public class LocalidadException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LocalidadException(String message) {
        super(message);
    }

    public LocalidadException(String message, Throwable cause) {
        super(message, cause);
    }

}
