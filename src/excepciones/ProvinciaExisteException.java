package excepciones;

public class ProvinciaExisteException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProvinciaExisteException(String message) {
        super(message);
    }

    public ProvinciaExisteException(String message, Throwable cause) {
        super(message, cause);
    }

}
