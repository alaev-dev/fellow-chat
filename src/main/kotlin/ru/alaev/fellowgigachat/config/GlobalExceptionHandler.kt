package ru.alaev.fellowgigachat.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(ex: DomainException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message ?: "Invalid domain data",
            path = request.getDescription(false)
        )
        return ResponseEntity(errorResponse, ex.errorType.httpStatus)
    }

    // Обработка других исключений
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message ?: "An unexpected error occurred",
            path = request.getDescription(false)
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

class DomainException(message: String, val errorType: ErrorType) : RuntimeException(message)

data class ErrorResponse(
    val message: String,
    val path: String
)

enum class ErrorType(val httpStatus: HttpStatus) {
    NOT_FOUND(HttpStatus.NOT_FOUND),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST)
}
