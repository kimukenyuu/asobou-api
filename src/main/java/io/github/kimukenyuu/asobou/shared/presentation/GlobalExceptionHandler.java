package io.github.kimukenyuu.asobou.shared.presentation;

import io.github.kimukenyuu.asobou.user.domain.UserAlreadyExistsException;
import io.github.kimukenyuu.asobou.user.domain.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail handleUserNotFound(
            UserNotFoundException exception,
            HttpServletRequest request
    ) {
        return problem(
                HttpStatus.NOT_FOUND,
                "User not found",
                exception.getMessage(),
                "USER_NOT_FOUND",
                request
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    ProblemDetail handleUserAlreadyExists(
            UserAlreadyExistsException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problem.setTitle("User already exists");
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("code", "USER_ALREADY_EXISTS");

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleValidationFailure(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Request validation failed"
        );

        problem.setTitle("Invalid request");
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty(
                "code",
                "VALIDATION_FAILED"
        );
        problem.setProperty(
                "errors",
                exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> Map.of(
                                "field", error.getField(),
                                "message", error.getDefaultMessage() == null
                                        ? "Invalid value"
                                        : error.getDefaultMessage()
                        ))
                        .toList()
        );

        return problem;
    }

    private ProblemDetail problem(
            HttpStatus status,
            String title,
            String detail,
            String code,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(title);
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("code", code);
        return problem;
    }
}
