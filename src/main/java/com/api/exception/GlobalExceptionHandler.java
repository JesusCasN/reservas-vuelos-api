package com.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Manejo generalizado para excepciones (404 Not Found)
    @ExceptionHandler({
            UsuarioNoEncontradoException.class,
            RolNoEncontradoException.class,
            VueloNoEncontradoException.class,
            VuelosNoEncontradosException.class,
            ReservaNoEncontradaException.class,
            BoletoNoEncontradoException.class,
            EmailNoEncontradoException.class
    })
    public ProblemDetail handleNotFoundExceptions(RuntimeException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Recurso no encontrado");
        problem.setType(URI.create("about:blank"));
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }

    // Manejo específico para usuario existente (409 Conflict)
    @ExceptionHandler(UsuarioYaExisteException.class)
    public ProblemDetail handleUsuarioYaExiste(UsuarioYaExisteException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Usuario ya existe");
        problem.setType(URI.create("about:blank"));
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }

    // Manejo específico para contraseña incorrecta (400 Bad Request)
    @ExceptionHandler(ContrasenaIncorrectaException.class)
    public ProblemDetail handleContrasenaIncorrecta(ContrasenaIncorrectaException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Contraseña incorrecta");
        problem.setType(URI.create("about:blank"));
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }

    // Manejo de errores de validación en DTOs (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Error de validación");
        problem.setType(URI.create("about:blank"));
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage())
        );
        problem.setProperty("errors", errors);
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }

    //401 error de email o password al momento de ingresar
    @ExceptionHandler({ BadCredentialsException.class, InternalAuthenticationServiceException.class })
    public ProblemDetail handleLoginFailures(RuntimeException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problem.setTitle("Credenciales inválidas");
        problem.setDetail("El email o la contraseña son incorrectos.");
        problem.setType(URI.create("about:blank"));
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }

    // Manejo de errores de autorización (403 Forbidden)
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ProblemDetail handleAccessDenied(AuthorizationDeniedException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problem.setTitle("Acceso denegado");
        problem.setDetail("No tienes permisos suficientes para acceder a este recurso.");
        problem.setType(URI.create("about:blank"));
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }

    // Manejo genérico para excepciones no previstas (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        log.error("Excepción inesperada capturada: ", ex);
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Error interno del servidor");
        problem.setType(URI.create("about:blank"));
        problem.setDetail("Ocurrió un error inesperado. Contacte con soporte.");
        problem.setProperty("timestamp", LocalDateTime.now());
        return problem;
    }

}
