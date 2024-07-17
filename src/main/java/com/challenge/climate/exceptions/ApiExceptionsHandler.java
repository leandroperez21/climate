package com.challenge.climate.exceptions;

import com.challenge.climate.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionsHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDTO> excepcionGenericaHandler(Exception ex) {
        ResponseDTO responseDTO = new ResponseDTO(500, ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
    }

    @ExceptionHandler({TipoClimaException.class})
    public ResponseEntity<ResponseDTO> climaInexistenteHandler(Exception ex) {
        ResponseDTO responseDTO = new ResponseDTO(404, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler({DiaIncorrectoException.class})
    public ResponseEntity<ResponseDTO> diaIncorrectoHandler(Exception ex) {
        ResponseDTO responseDTO = new ResponseDTO(404, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }
}
