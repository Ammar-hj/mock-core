package com.platform.mockcore.handler;

import com.platform.mockcore.enums.RespCodeEnum;
import com.platform.mockcore.exception.MockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MockException.class)
    @ResponseBody
    public ResponseEntity<String> handleBizException(MockException e) {
        logger.warn("mock exception: ", e);
        if (e.getCode() == RespCodeEnum.GROOVY_COMPILE_EXCEPTION.getCode() || e.getCode() == RespCodeEnum.GROOVY_RUNTIME_EXCEPTION.getCode()) {
            String body = String.format("[%s-%s]", e.getCode(), e.getMessage()) + "\n"
                    + "********************************************************************************\n"
                    + e.getCause().getMessage() + "\n"
                    + "********************************************************************************\n";
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(String.format("[%s-%s]", e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        logger.warn("sys exception: ", e);
        return new ResponseEntity<>(RespCodeEnum.UNKNOWN_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
