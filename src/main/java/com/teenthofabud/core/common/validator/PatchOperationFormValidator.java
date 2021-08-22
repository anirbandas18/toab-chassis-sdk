package com.teenthofabud.core.common.validator;

import com.teenthofabud.core.common.data.form.PatchOperationForm;
import com.teenthofabud.core.common.error.TOABErrorCode;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.json.JsonPatch;

public class PatchOperationFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(PatchOperationForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PatchOperationForm form = (PatchOperationForm) target;
        if(StringUtils.isEmpty(StringUtils.trimWhitespace(form.getOp()))) {
            errors.rejectValue("op", TOABErrorCode.PATCH_ATTRIBUTE_INVALID.name());
            return;
        } else {
            try {
                JsonPatch.Operation.valueOf(form.getOp().toUpperCase());
            } catch (IllegalArgumentException e) {
                errors.rejectValue("op", TOABErrorCode.PATCH_ATTRIBUTE_INVALID.name());
                return;
            }
        }
        if(StringUtils.isEmpty(StringUtils.trimWhitespace(form.getPath()))) {
            errors.rejectValue("path", TOABErrorCode.PATCH_ATTRIBUTE_INVALID.name());
            return;
        } else if (!form.getPath().matches("\\/(\\S+)*(\\/\\S*)*")) {
            errors.rejectValue("path", TOABErrorCode.PATCH_ATTRIBUTE_INVALID.name());
            return;
        }
        if(StringUtils.isEmpty(StringUtils.trimWhitespace(form.getValue()))) {
            errors.rejectValue("value", TOABErrorCode.PATCH_ATTRIBUTE_INVALID.name());
            return;
        }
    }
}
