package excepciones;

public class ListarLocalidadesException extends LocalidadException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListarLocalidadesException(String message) {
        super(message);
    }

    public ListarLocalidadesException(String message, Throwable cause) {
        super(message, cause);
    }

}
