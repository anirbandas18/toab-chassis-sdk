package com.teenthofabud.core.common.handler;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class TOABBaseAuditPropertyHandler implements AuditorAware<Long> {

    private static final Long DEFAULT_USER_ID = 1L;

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(DEFAULT_USER_ID);
    }
}
