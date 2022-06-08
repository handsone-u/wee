package com.whatweeat.wwe.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class MenuCreateDTO extends MenuDTO{
    private String menuName;
    private String menuImage;
}
