package com.whatweeat.wwe.dto;

import lombok.Data;

@Data
public class MenuHomeResponse {
    String name;
    String imageURL;

    public MenuHomeResponse(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
    }
}
