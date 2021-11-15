package com.teenthofabud.core.common.data.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TOABValidationContextHolder {

    private static final ThreadLocal<Map<String,Object>> SUPPORTING_VALIDATION_PARAMETER_CONTEXT = ThreadLocal.withInitial( () -> {
        return new HashMap<>();
    });

    public static void setSupportingValidationParameterContext(String key, String value) {
        Map<String,Object> parameters = SUPPORTING_VALIDATION_PARAMETER_CONTEXT.get();
        parameters.put(key, value);
        SUPPORTING_VALIDATION_PARAMETER_CONTEXT.set(parameters);
    }

    public static Optional<Object> getSupportingValidationParameterContext(String key) {
        Map<String,Object> parameters = SUPPORTING_VALIDATION_PARAMETER_CONTEXT.get();
        if(parameters.isEmpty()) {
            return Optional.empty();
        } else {
            Optional<Object> value = Optional.of(parameters.get(key));
            return value;
        }
    }

    public static void clearSupportingValidationParameterContext() {
        SUPPORTING_VALIDATION_PARAMETER_CONTEXT.remove();
    }
    public static void clearSupportingValidationParameterContext(String key) {
        Map<String,Object> parameters = SUPPORTING_VALIDATION_PARAMETER_CONTEXT.get();
        if(!parameters.isEmpty()) {
            parameters.remove(key);
            SUPPORTING_VALIDATION_PARAMETER_CONTEXT.set(parameters);
        }
    }

}
