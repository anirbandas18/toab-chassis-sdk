package com.teenthofabud.core.common.data.dto;

import com.teenthofabud.core.common.constant.TOABPersistenceDataType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TOABQueryParameterValue<T> {

    private TOABPersistenceDataType dataType;
    private T data;

}
