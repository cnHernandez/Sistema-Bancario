package excepciones;

public class ObtenerLocalidadException extends LocalidadException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObtenerLocalidadException(String message) {
        super(message);
    }

    public ObtenerLocalidadException(String message, Throwable cause) {
        super(message, cause);
    }

}
