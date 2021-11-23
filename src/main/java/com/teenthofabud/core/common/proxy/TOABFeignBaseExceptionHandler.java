package com.teenthofabud.core.common.proxy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.core.common.data.vo.ErrorVo;
import com.teenthofabud.core.common.error.TOABErrorCode;
import com.teenthofabud.core.common.error.TOABFeignException;
import com.teenthofabud.core.common.error.TOABSystemException;
import feign.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class TOABFeignBaseExceptionHandler {

    public abstract HttpStatus[] getClientErrorResponseStatuses();
    public abstract HttpStatus[] getServerErrorResponseStatuses();
    public abstract Optional<ErrorVo> getClientErrorMappedNativeErrorModel(byte[] rawResponse);

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Getter
    private ObjectMapper objectMapper;

    private boolean isStatusCodeResemblesClientError(HttpStatus httpStatusCode) {
        for(HttpStatus httpStatus : getClientErrorResponseStatuses()) {
            if(httpStatus.equals(httpStatusCode)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStatusCodeResemblesServerError(HttpStatus httpStatusCode) {
        for(HttpStatus httpStatus : getServerErrorResponseStatuses()) {
            if(httpStatus.equals(httpStatusCode)) {
                return true;
            }
        }
        return false;
    }

    private Optional<? extends TOABFeignException> parseErrorResponse(ErrorVo errorDetails) {
        Optional<? extends TOABFeignException> optEx = Optional.of(new TOABFeignException(errorDetails.getCode(), errorDetails.getMessage(), errorDetails.getDomain()));
        return optEx;
    }

    public Optional<? extends TOABFeignException> parseResponseToException(Response response) {
        Optional<? extends TOABFeignException> optEx = Optional.empty();
        try {
            HttpStatus httpStatusCode = HttpStatus.resolve(response.status());
            InputStream rawErrorResponseBody = response.body().asInputStream();
            byte[] rawResponse = rawErrorResponseBody.readAllBytes();
            String formattedResponse = new String(rawResponse, StandardCharsets.UTF_8);
            log.debug("Response from service: {}", formattedResponse);
            if (isStatusCodeResemblesClientError(httpStatusCode)) {
                log.debug("Client calling error");
                Optional<ErrorVo> optErrorDetails = getClientErrorMappedNativeErrorModel(rawResponse);
                if(optErrorDetails.isPresent()) {
                    optEx = parseErrorResponse(optErrorDetails.get());
                }
            }  else if (isStatusCodeResemblesServerError(httpStatusCode)) {
                log.debug("Service stability error");
                TypeReference<HashMap<String,Object>> mapTypeReference = new TypeReference<HashMap<String,Object>>() {};
                Map<String, Object> errorDetails = objectMapper.readValue(rawResponse, mapTypeReference);
                String message = (String) errorDetails.get("error");
                String domain = (String) errorDetails.get("path");
                optEx = Optional.of(new TOABFeignException(TOABErrorCode.SYSTEM_IO_FAILURE.getErrorCode(), message, domain));
            }
            else {
                optEx = Optional.of(new TOABFeignException(TOABErrorCode.UNEXPECTED_CLIENT_RESPONSE_STATUS.getErrorCode(), formattedResponse));
            }

            return optEx;
        } catch (IOException e) {
            log.error("Unable to parse response body", e);
            throw new TOABSystemException(TOABErrorCode.SYSTEM_IO_FAILURE, "Unable to parse response body", e);
        }
    }

}
