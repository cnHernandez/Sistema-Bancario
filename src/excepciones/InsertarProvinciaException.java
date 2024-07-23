package excepciones;

public class InsertarProvinciaException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsertarProvinciaException(String message) {
        super(message);
    }

    public InsertarProvinciaException(String message, Throwable cause) {
        super(message, cause);
    }

}
