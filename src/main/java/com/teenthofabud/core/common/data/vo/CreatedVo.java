package com.teenthofabud.core.common.data.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class CreatedVo {

    @ToString.Include
    private String id;

}
