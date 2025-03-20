package com.tpi_pais.mega_store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
/*
* El BadRequestException es una excepcion personalizada que se utiliza para manejar
* errores de validaciones de datos.
* Por ejemplo: Cuando un argumento tiene que tener un tipo de dato correcto.
* */