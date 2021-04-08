package com.example.Springdesafio.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    private int productId;
    private String name;
    private String category;
    private String brand;
    private double price;
    private int quantity;
    private String freeShipping;
    private String prestige;

    public String getAttribute(String value){
        String attribute=null;
        switch (value){
            case "name": attribute = getName();
                    break;
            case "category": attribute = getCategory();
                break;
            case "brand": attribute = getBrand();
                break;
            case "freeShipping": attribute = getFreeShipping();
                break;
            case "prestige": attribute = getPrestige();
                break;
        }
        return attribute;
    }
}
