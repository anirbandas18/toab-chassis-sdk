package com.teenthofabud.core.common.converter;

import com.teenthofabud.core.common.constant.TOABCascadeLevel;
import com.teenthofabud.core.common.data.document.TOABBaseDocument;
import com.teenthofabud.core.common.data.dto.TOABRequestContextHolder;
import com.teenthofabud.core.common.data.vo.TOABBaseVo;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Slf4j
public abstract class TOABBaseDocument2VoConverter<S extends TOABBaseDocument,T extends TOABBaseVo> {

    public void expandAuditFields(S entity, T vo) {
        TOABCascadeLevel cascadeLevel = TOABRequestContextHolder.getCascadeLevelContext();
        switch (cascadeLevel) {
            case ONE:
                vo.setActive(entity.getActive());
                break;
            case TWO:
                vo.setActive(entity.getActive());
                vo.setModifiedOn(entity.getModifiedOn().truncatedTo(ChronoUnit.MILLIS));
                vo.setCreatedOn(entity.getCreatedOn().truncatedTo(ChronoUnit.MILLIS));
                vo.setModifiedBy(entity.getModifiedBy());
                vo.setCreatedBy(entity.getCreatedBy());
                break;
            default:
                log.debug("No eligible fields available in {} to perform two level cascading in target type");
                break;
        }
    }

}
