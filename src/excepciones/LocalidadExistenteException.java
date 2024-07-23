package excepciones;

public class LocalidadExistenteException extends LocalidadException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LocalidadExistenteException(String message) {
        super(message);

}
	
	  public LocalidadExistenteException(String message, Throwable cause) {
	        super(message, cause);
	    }
	  
}
