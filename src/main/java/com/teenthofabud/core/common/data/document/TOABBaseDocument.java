package com.teenthofabud.core.common.data.document;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public abstract class TOABBaseDocument {

    @CreatedDate
    @ToString.Include
    private LocalDateTime createdOn;
    @LastModifiedDate
    @ToString.Include
    private LocalDateTime modifiedOn;
    @CreatedBy
    @ToString.Include
    private Long createdBy;
    @LastModifiedBy
    @ToString.Include
    private Long modifiedBy;
    @ToString.Include
    private Boolean active;

}
