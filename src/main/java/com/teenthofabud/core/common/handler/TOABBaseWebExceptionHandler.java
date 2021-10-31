package com.teenthofabud.core.common.handler;

import brave.Tracer;
import com.teenthofabud.core.common.data.vo.ErrorVo;
import com.teenthofabud.core.common.error.TOABBaseException;
import com.teenthofabud.core.common.error.TOABFeignException;
import com.teenthofabud.core.common.error.TOABSystemException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Locale;

public interface TOABBaseWebExceptionHandler {

    @Deprecated
    default ResponseEntity<ErrorVo> parseExceptionToResponse(TOABBaseException e, MessageSource messageSource) {
        ErrorVo vo = new ErrorVo();
        String msg = messageSource.getMessage(e.getError().getErrorCode(), null, Locale.US);
        if(e.getParameters() != null) {
            Deque<Object> parameters = new ArrayDeque<>(Arrays.asList(e.getParameters()));
            parameters.addFirst(e.getSubDomain());
            msg = String.format(msg, parameters.toArray(new Object[parameters.size()]));
        }
        vo.setCode(e.getError().getErrorCode());
        vo.setMessage(msg);
        vo.setDomain(e.getError().getDomain());
        return ResponseEntity.status(e.getError().getHttpStatusCode()).body(vo);
    }

    default ResponseEntity<ErrorVo> parseExceptionToResponse(TOABBaseException e, TOABMessageSource messageSource, Tracer tracer) {
        ErrorVo vo = new ErrorVo();
        String msg = messageSource.getMessage(e.getError().getErrorCode(), null, Locale.getDefault());
        if(e.getParameters() != null) {
            Deque<Object> parameters = new ArrayDeque<>(Arrays.asList(e.getParameters()));
            msg = String.format(msg, parameters.toArray(new Object[parameters.size()]));
        }
        String domainAndSubDomain = String.join(" - ", Arrays.asList(e.getError().getDomain(), e.getSubDomain()));
        vo.setCode(e.getError().getErrorCode());
        vo.setMessage(msg);
        vo.setDomain(domainAndSubDomain);
        vo.setTrace(tracer.currentSpan().context().traceIdString());
        return ResponseEntity.status(e.getError().getHttpStatusCode()).body(vo);
    }

    default ResponseEntity<ErrorVo> parseExceptionToResponse(TOABFeignException e, Tracer tracer) {
        ErrorVo vo = new ErrorVo();
        String msg = e.getErrorMessage();
        String domainAndSubDomain = e.getErrorDomain();
        vo.setCode(e.getErrorCode());
        vo.setMessage(msg);
        vo.setDomain(domainAndSubDomain);
        vo.setTrace(tracer.currentSpan().context().traceIdString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(vo);
    }

    default ResponseEntity<ErrorVo> parseExceptionToResponse(TOABSystemException e, TOABMessageSource messageSource, Tracer tracer) {
        ErrorVo vo = new ErrorVo();
        String msg = messageSource.getMessage(e.getError().getErrorCode(), null, Locale.getDefault());
        if(e.getParameters() != null) {
            Deque<Object> parameters = new ArrayDeque<>(Arrays.asList(e.getParameters()));
            msg = String.format(msg, parameters.toArray(new Object[parameters.size()]));
        }
        String domainAndSubDomain = e.getError().getDomain();
        vo.setCode(e.getError().getErrorCode());
        vo.setMessage(msg);
        vo.setDomain(domainAndSubDomain);
        vo.setTrace(tracer.currentSpan().context().traceIdString());
        return ResponseEntity.status(e.getError().getHttpStatusCode()).body(vo);
    }


}
