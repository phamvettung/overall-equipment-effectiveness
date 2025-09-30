package vn.intech.oee2025.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {

	SUCCESS(0, "Success.", HttpStatus.OK),
	USER_EXISTED(1, "Username is already.", HttpStatus.BAD_REQUEST),
	EMAIL_EXISTED(2, "Email is already.", HttpStatus.BAD_REQUEST),
	USER_NOT_EXISTED(3, "User not existed.", HttpStatus.NOT_FOUND),
	ROLE_NOT_EXISTED(4, "Role is not found.", HttpStatus.NOT_FOUND),
	UNEXPECTED_VALUE(5, "Unexpected value.", HttpStatus.BAD_REQUEST),
	INVALID_PASSWORD(6, "Password must be at least 8 charactors.", HttpStatus.BAD_REQUEST),
	USERNAME_PASSWORD_INCORRECT(7, "Username or password incorrect.", HttpStatus.UNAUTHORIZED),
	INVALID_CREDENTIALS(8, "Invalid credentials.", HttpStatus.BAD_REQUEST),
	INVALID_KEY(98, "Invalid message key.", HttpStatus.BAD_REQUEST),
	UNCATEGORIZED_EXCEPION(99, "Uncategorized exception.", HttpStatus.INTERNAL_SERVER_ERROR)
	;
	
	private ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}
	private int code;
	private String message;
	private HttpStatusCode statusCode;
	
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public HttpStatusCode getStatusCode() {
		return statusCode;
	}

}
