package org.csu.nailong.order.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.csu.nailong.order.common.CommonResponse;
import org.csu.nailong.order.common.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少参数: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.BAD_REQUEST, "缺少参数: " + e.getParameterName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        log.error("参数校验失败: {}", errorMessage);
        return CommonResponse.createForError(ResponseCode.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数类型错误: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT, "参数类型错误: " + e.getName());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResponse<Object> handleAuthenticationException(AuthenticationException e) {
        log.error("身份验证失败: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.UNAUTHORIZED, "身份验证失败，请重新登录");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResponse<Object> handleAccessDeniedException(AccessDeniedException e) {
        log.error("权限不足: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.FORBIDDEN, "权限不足，无法访问");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResponse<Object> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("请求的资源不存在: {}", e.getRequestURL());
        return CommonResponse.createForError(ResponseCode.NOT_FOUND, "嗯？奶龙没有这种东西的哦");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public CommonResponse<Object> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        log.error("错误的方法: {}", e.getMethod());
        return CommonResponse.createForError(ResponseCode.METHOD_NOT_ALLOWED, "请求方式错误，请使用正确的 HTTP 方法");
    }

    @ExceptionHandler(SocketTimeoutException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public CommonResponse<Object> handleSocketTimeoutException(SocketTimeoutException e) {
        log.error("请求超时: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.REQUEST_TIMEOUT, "世界上最遥远的距离，莫过于你与奶龙之间少了互联网的桥梁");
    }

    @ExceptionHandler(UnknownHostException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public CommonResponse<Object> handleUnknownHostException(UnknownHostException e) {
        log.error("无法连接服务器: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.SERVICE_UNAVAILABLE, "世界上最遥远的距离，莫过于你与奶龙之间少了互联网的桥梁");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<Object> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("请求参数违反约束: {}", e.getMessage());
        return CommonResponse.createForError(ResponseCode.BAD_REQUEST, "请求参数不符合要求，请检查后重新提交");
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<Object> handleHttpClientErrorException(HttpClientErrorException e) {
        log.error("HTTP 客户端错误: {}", e.getMessage());
        ResponseCode code = ResponseCode.fromCode(e.getStatusCode().value());
        return CommonResponse.createForError(code, "客户端请求错误");
    }

    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<Object> handleLoginException(LoginException e) {
        log.error("登录异常: {} - {}", e.getErrorCode(), e.getErrorMessage());
        return CommonResponse.createForError(ResponseCode.BAD_REQUEST, e.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<Object> handleException(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return CommonResponse.createForError(ResponseCode.INTERNAL_SERVER_ERROR, "奶龙家里着火啦，请不要揭穿他，偷偷告诉小七就好啦");
    }
}
