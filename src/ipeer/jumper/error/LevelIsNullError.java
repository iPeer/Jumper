package ipeer.jumper.error;

public class LevelIsNullError extends Exception {

	private static final long serialVersionUID = -5515048988360857789L;

	public LevelIsNullError() {
	}

	public LevelIsNullError(String message) {
		super(message);
	}

	public LevelIsNullError(Throwable cause) {
		super(cause);
	}

	public LevelIsNullError(String message, Throwable cause) {
		super(message, cause);
	}

}
