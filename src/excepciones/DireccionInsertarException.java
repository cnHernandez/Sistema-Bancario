package excepciones;

public class DireccionInsertarException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DireccionInsertarException(String message) {
        super(message);
    }

    public DireccionInsertarException(String message, Throwable cause) {
        super(message, cause);
    }
}