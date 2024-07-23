package excepciones;

public class ListarProvinciasException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListarProvinciasException(String message) {
        super(message);
    }

    public ListarProvinciasException(String message, Throwable cause) {
        super(message, cause);
    }

}
