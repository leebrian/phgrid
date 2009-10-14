package gov.cdc.ncphi.phgrid.gipse.dbimporter;
/**
 * Exception class used to convey DBImporter specific exceptions.
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.13 1059-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class DBImporterException extends Exception {

	private static final long serialVersionUID = 1L;

	public DBImporterException() {
		super();
	}

	public DBImporterException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBImporterException(String message) {
		super(message);
	}

	public DBImporterException(Throwable cause) {
		super(cause);
	}

}
