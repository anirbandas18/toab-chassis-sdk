package com.teenthofabud.core.common.data.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@ToString
public abstract class TOABBaseEntity {

    @Column(name = "created_on")
    @ToString.Include
    @CreatedDate
    private LocalDateTime createdOn;
    @Column(name = "modified_on")
    @ToString.Include
    @LastModifiedDate
    private LocalDateTime modifiedOn;
    @Column(name = "created_by")
    @ToString.Include
    @CreatedBy
    private Long createdBy;
    @Column(name = "modified_by")
    @ToString.Include
    @LastModifiedBy
    private Long modifiedBy;
    @Column(name = "active_sw")
    @ToString.Include
    private Boolean active;
    @ToString.Include
    @Version
    private Integer version;

}
