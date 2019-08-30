package org.openpredict.exchange.rest.controllers;

import org.openpredict.exchange.beans.cmd.CommandResultCode;
import org.openpredict.exchange.beans.cmd.OrderCommand;
import org.openpredict.exchange.rest.commands.ApiErrorCodes;
import org.openpredict.exchange.rest.events.RestGenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class RestControllerHelper {

    public static ResponseEntity<RestGenericResponse> errorResponse(ApiErrorCodes errMessage, String... args) {
        return ResponseEntity
                .status(errMessage.httpStatus)
                .body(RestGenericResponse.builder()
                        .ticket(0)
                        .gatewayResultCode(errMessage.gatewayErrorCode)
                        .coreResultCode(0)
                        .description(String.format(errMessage.errorDescription, (Object[]) args))
                        .build());
    }

    public static ResponseEntity<RestGenericResponse> coreResponse(OrderCommand cmd, Supplier<Object> successMapper, HttpStatus successCode) {
        CommandResultCode resultCode = cmd.resultCode;
        return ResponseEntity
                .status(resultCode == CommandResultCode.SUCCESS ? successCode : HttpStatus.BAD_REQUEST)
                .body(RestGenericResponse.builder()
                        .ticket(0)
                        .gatewayResultCode(0)
                        .coreResultCode(resultCode.getCode())
                        .data(successMapper.get())
                        .description(resultCode.toString())
                        .build());

    }


    public static ResponseEntity<RestGenericResponse> successResponse(Object data, HttpStatus code) {
        return ResponseEntity
                .status(code)
                .body(RestGenericResponse.builder()
                        .ticket(0)
                        .gatewayResultCode(0)
                        .coreResultCode(0)
                        .data(data)
                        //.description(null)
                        .build());

    }

}
