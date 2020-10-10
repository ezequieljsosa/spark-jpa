package dds;

public class AutoNotFoundException extends Exception {

	private String patente;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutoNotFoundException() {
		super();
		
	}
	public AutoNotFoundException(String patente) {
		super();
		this.patente = patente;
	}



	public AutoNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	

	public AutoNotFoundException(Throwable arg0) {
		super(arg0);
	
	}

	
	
}
