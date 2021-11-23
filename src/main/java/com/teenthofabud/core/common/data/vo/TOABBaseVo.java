package com.teenthofabud.core.common.data.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class TOABBaseVo {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ToString.Include
    private LocalDateTime createdOn;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ToString.Include
    private LocalDateTime modifiedOn;
    @ToString.Include
    private Long createdBy;
    @ToString.Include
    private Long modifiedBy;
    @ToString.Include
    private Boolean active;

}
