package org.serratec.backend.redesocial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler  extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(EmailException.class)
	protected ResponseEntity<Object> handleEmailException(EmailException ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}

	@ExceptionHandler(SenhaException.class)
	protected ResponseEntity<Object> handleSenhaException(SenhaException ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
	
	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<Void> handleNotFoundException(NotFoundException ex) {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(UnauthorizedException.class)
	protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	}
	
}
