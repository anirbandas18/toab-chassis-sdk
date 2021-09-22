package com.teenthofabud.core.common.handler;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TOABBaseAuditPropertyHandler implements AuditorAware<Long> {

    private static final Long DEFAULT_USER_ID = 1L;

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(DEFAULT_USER_ID);
    }
}
