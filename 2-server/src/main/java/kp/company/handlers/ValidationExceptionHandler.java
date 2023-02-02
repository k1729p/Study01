package kp.company.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

/**
 * The exception handler for web requests validation.<br>
 * The validation constraints are defined in the 'openapi.yaml' file.
 * 
 */
@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * The constructor.
	 * 
	 */
	public ValidationExceptionHandler() {
		super();
	}

	/**
	 * Customize the response for the {@link ConstraintViolationException}.
	 * <p>
	 * This method delegates to the {@link #handleExceptionInternal}.
	 * 
	 * @param exception the {@link ConstraintViolationException}
	 * @param request   the current web request
	 * @return the {@link ResponseEntity}
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception,
			WebRequest request) {

		logger.warn(
				String.format("handleConstraintViolation(): ConstraintViolationException[%s]", exception.getMessage()));
		return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatusCode statusCode, WebRequest webRequest) {

		logger.warn(String.format("handleMethodArgumentNotValid(): MethodArgumentNotValidException[%s]",
				exception.getMessage()));
		return handleExceptionInternal(exception, exception.getMessage(), headers, statusCode, webRequest);
	}
}