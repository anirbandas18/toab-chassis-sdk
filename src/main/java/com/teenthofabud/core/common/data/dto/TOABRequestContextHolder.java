package com.teenthofabud.core.common.data.dto;

import com.teenthofabud.core.common.constant.TOABCascadeLevel;

public class TOABRequestContextHolder {

    private static final ThreadLocal<TOABCascadeLevel> CASCADE_LEVEL_CONTEXT = ThreadLocal.withInitial( () -> {
        return TOABCascadeLevel.ZERO;
    });

    public static void setCascadeLevelContext(TOABCascadeLevel cascadeLevel) {
        CASCADE_LEVEL_CONTEXT.set(cascadeLevel);
    }

    public static TOABCascadeLevel getCascadeLevelContext() {
        return CASCADE_LEVEL_CONTEXT.get();
    }

    public static void clearCascadeLevelContext() {
        CASCADE_LEVEL_CONTEXT.remove();
    }

}
