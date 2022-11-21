package com.teenthofabud.core.common.data.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class TOABBaseEntity {

    @Column(name = "created_on")
    @ToString.Include
    @CreatedDate
    @Access(AccessType.FIELD)
    protected LocalDateTime createdOn;
    @Column(name = "modified_on")
    @ToString.Include
    @LastModifiedDate
    @Access(AccessType.FIELD)
    protected LocalDateTime modifiedOn;
    @Column(name = "created_by")
    @ToString.Include
    @CreatedBy
    @Access(AccessType.FIELD)
    protected Long createdBy;
    @Column(name = "modified_by")
    @ToString.Include
    @LastModifiedBy
    @Access(AccessType.FIELD)
    protected Long modifiedBy;
    @Column(name = "active_sw")
    @ToString.Include
    @Access(AccessType.FIELD)
    protected Boolean active;
    @ToString.Include
    @Version
    @Access(AccessType.FIELD)
    protected Integer version;

}
