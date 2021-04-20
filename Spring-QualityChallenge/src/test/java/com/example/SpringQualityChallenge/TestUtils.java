package com.example.SpringQualityChallenge;

import com.example.SpringQualityChallenge.dtos.HotelDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;

public class TestUtils {

    /*public List<ArticleDTO> findArticleByName(String name) {
        List<ArticleDTO> articleDTOS = null;
        articleDTOS = loadDataBase();
        ArticleDTO result = null;
        List<ArticleDTO> marketDTOList=new ArrayList<>();
        if (articleDTOS != null){
            marketDTOList = articleDTOS.stream()
                    .filter(articleDTO -> articleDTO.getName().contains(name))
                    .collect(Collectors.toList());
        }

        if(marketDTOList.size()==0){
            throw new ArticleDTO(name);
        }
        return marketDTOList;
    }*/

    public List<HotelDTO> loadDataBase() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:dbHotels.json");
        }catch (Exception e){
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<HotelDTO>> typeRef = new TypeReference<List<HotelDTO>>() {};
        List<HotelDTO> articleDTOS = null;

        try {
            articleDTOS = objectMapper.readValue(file, typeRef);

        }catch (Exception e){
            e.printStackTrace();
        }

        return articleDTOS;

    }
}
