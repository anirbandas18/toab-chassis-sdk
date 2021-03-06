package com.teenthofabud.core.common.converter;

import com.teenthofabud.core.common.constant.TOABCascadeLevel;
import com.teenthofabud.core.common.data.document.TOABBaseDocument;
import com.teenthofabud.core.common.data.dto.TOABRequestContextHolder;
import com.teenthofabud.core.common.data.vo.TOABBaseVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class TOABBaseDocument2VoConverter<S extends TOABBaseDocument,T extends TOABBaseVo> {

    public void expandAuditFields(S entity, T vo) {
        TOABCascadeLevel cascadeLevel = TOABRequestContextHolder.getCascadeLevelContext();
        switch (cascadeLevel) {
            case ONE:
            case TWO:
                vo.setActive(entity.getActive());
                vo.setModifiedOn(entity.getModifiedOn());
                vo.setCreatedOn(entity.getCreatedOn());
                vo.setModifiedBy(entity.getModifiedBy());
                vo.setCreatedBy(entity.getCreatedBy());
                break;
            default:
                log.debug("No eligible fields available in {} to perform two level cascading in target type");
                break;
        }
    }

}
