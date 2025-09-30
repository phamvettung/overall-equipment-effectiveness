package vn.intech.oee2025.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.intech.oee2025.dto.response.MessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	ResponseEntity<MessageResponse> handlingException(Exception exception){		
		MessageResponse response = new MessageResponse(ErrorCode.UNCATEGORIZED_EXCEPION.getCode(), ErrorCode.UNCATEGORIZED_EXCEPION.getMessage(), null);		
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(value = CustomException.class)
	ResponseEntity<MessageResponse> handlingCustomException(CustomException exception){		
		MessageResponse response = new MessageResponse(exception.getErrorCode().getCode(), exception.getErrorCode().getMessage(), null);		
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<MessageResponse> handlingValidation(MethodArgumentNotValidException exception){	
		String enumKey = exception.getFieldError().getDefaultMessage();
		ErrorCode errorCode = ErrorCode.INVALID_KEY;
		try {
			errorCode = ErrorCode.valueOf(enumKey);
		}catch (Exception e) {
			
		}
		MessageResponse response = new MessageResponse(errorCode.getCode(), errorCode.getMessage(), null);	
		return ResponseEntity.badRequest().body(response);
	}
}
