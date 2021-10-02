package com.teenthofabud.core.common.data.dto;

import com.teenthofabud.core.common.constant.TOABPersistenceOperatorType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TOABQueryParameterKey {

    private TOABPersistenceOperatorType operatorType;
    private String name;

}
