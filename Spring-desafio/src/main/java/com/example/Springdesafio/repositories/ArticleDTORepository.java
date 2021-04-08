package com.example.Springdesafio.repositories;

import com.example.Springdesafio.dtos.ArticleDTO;
import com.example.Springdesafio.exceptions.ArticleDTONotFoudException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ArticleDTORepository {

    public List<ArticleDTO> findProductsByCategory(List<ArticleDTO> articleDTOS, Map<String,String> params) throws IOException, ArticleDTONotFoudException;
    public List<ArticleDTO> findProductsByCategories(List<ArticleDTO> articleDTOS, Map<String,String> params) throws IOException;
    public List<ArticleDTO> findProductsByCategoriesAndOrder(List<ArticleDTO> articleDTOS, Map<String,String> params) throws IOException, ArticleDTONotFoudException;
    public ArticleDTO findArticleByName(String name, String brand) throws ArticleDTONotFoudException;

}
