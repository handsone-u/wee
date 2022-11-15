package com.whatweeat.wwe.dto;

import com.whatweeat.wwe.minigame.domain.form.ExpenseName;
import com.whatweeat.wwe.minigame.domain.form.FlavorName;
import com.whatweeat.wwe.minigame.domain.form.NationName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class MenuCreateDTO extends MenuDTO {
    private String menuName;
    private String menuImage;

    public static MenuCreateDTO getDto(String menuName, String menuImage, Boolean rice, Boolean noodle, Boolean soup, Boolean healthy,
                                       Boolean instant, Boolean alcohol, ExpenseName expenseName,
                                       Set<FlavorName> flavorNames, Set<NationName> nationNames) {
        MenuCreateDTO dto = MenuCreateDTO.builder()
                .menuName(menuName)
                .menuImage(menuImage)
                .rice(rice)
                .noodle(noodle)
                .soup(soup)
                .healthy(healthy)
                .instant(instant)
                .alcohol(alcohol)
                .expenseName(expenseName)
                .build();
        dto.getFlavorNames().addAll(flavorNames);
        dto.getNationNames().addAll(nationNames);
        return dto;
    }
}
