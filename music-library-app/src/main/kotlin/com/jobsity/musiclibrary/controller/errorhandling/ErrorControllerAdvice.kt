package com.jobsity.musiclibrary.controller.errorhandling

import com.jobsity.musiclibrary.error.InvalidInputDataException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ErrorControllerAdvice {
    private val logger = LoggerFactory.getLogger(ErrorControllerAdvice::class.java)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<ErrorMessageModel> {
        val errorCode = generateErrorCode()
        logger.error("Unexpected error. Error code: {}. Cause: {}", errorCode, ex.message, ex)
        val errorMessage = ErrorMessageModel(
            System.currentTimeMillis(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Sorry, an unexpected error has occurred. $errorCode",
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> {
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.fieldErrors.forEach { fieldError: FieldError ->
            errors[fieldError.field] = fieldError.defaultMessage ?: "Validation failed"
        }
        val validationErrorResponse = ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            errors
        )
        return ResponseEntity(validationErrorResponse, HttpStatus.BAD_REQUEST)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    fun handleInvalidInputDataException(ex: InvalidInputDataException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            System.currentTimeMillis(),
            HttpStatus.NOT_FOUND.value(),
            ex.extendedMessage()
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    private fun generateErrorCode(): String {
        return "ERR-${System.currentTimeMillis()}"
    }
}
