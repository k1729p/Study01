package kp.company.handlers;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.invoke.MethodHandles;

/**
 * The exception handler for web requests validation.
 * <p>
 * The validation constraints are defined in the 'openapi.yaml' file.
 * </p>
 */
@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Customize the response for the {@link ConstraintViolationException}.
     * <p>
     * This method delegates to the {@link #handleExceptionInternal}.
     * </p>
     *
     * @param exception the {@link ConstraintViolationException}
     * @param request   the current web request
     * @return the {@link ResponseEntity}
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception,
                                                               WebRequest request) {

        logger.warn("handleConstraintViolation(): ConstraintViolationException[{}]", exception.getMessage());
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
                request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode statusCode,
                                                                  @NonNull WebRequest webRequest) {

        logger.warn("handleMethodArgumentNotValid(): MethodArgumentNotValidException[{}]", exception.getMessage());
        return handleExceptionInternal(exception, exception.getMessage(), headers, statusCode, webRequest);
    }
}