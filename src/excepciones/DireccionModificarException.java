package excepciones;

public class DireccionModificarException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DireccionModificarException(String message) {
        super(message);
    }

    public DireccionModificarException(String message, Throwable cause) {
        super(message, cause);
    }

}
