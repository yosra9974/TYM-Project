package tn.tym.esprit.authentication;

public class UserNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String user_not_found) {
        super(user_not_found);
    }
}
