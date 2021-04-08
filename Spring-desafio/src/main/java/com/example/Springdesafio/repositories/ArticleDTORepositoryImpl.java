package com.example.Springdesafio.repositories;

import com.example.Springdesafio.dtos.ArticleDTO;
import com.example.Springdesafio.exceptions.ArticleDTONotFoudException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Repository
@AllArgsConstructor
public class ArticleDTORepositoryImpl implements ArticleDTORepository{

    private List<ArticleDTO> articleDTOS;

    public ArticleDTORepositoryImpl(){
        this.articleDTOS=loadDataBase();
    }

    /**
     * Filtra solo por categoría o por orden si se envío el GET de toda la lista con un orden determinado
     * @param articleDTOS
     * @param params
     * @return
     * @throws IOException
     * @throws ArticleDTONotFoudException
     */
    @Override
    public List<ArticleDTO> findProductsByCategory(List<ArticleDTO> articleDTOS, Map<String, String> params) throws IOException, ArticleDTONotFoudException {
        List<ArticleDTO> articleDTOList=new ArrayList<>();
        if(params.get("order")!=null){
            articleDTOList=loadDataBase();
            articleDTOList=getOrder(params.get("order"),articleDTOList);
            return articleDTOList;
        }
        if (articleDTOS != null){
            articleDTOList = articleDTOS.stream()
                    .filter(articleDTO -> articleDTO.getCategory().equalsIgnoreCase(params.get("category")))
                    .collect(Collectors.toList());
        }

        return articleDTOList;
    }

    /**
     * Filtra por dos categorías o por una categoría y un orden.
     * @param articleDTOS
     * @param params
     * @return
     * @throws IOException
     */
    @Override
    public List<ArticleDTO> findProductsByCategories(List<ArticleDTO> articleDTOS,Map<String,String> params) throws IOException {
        List<ArticleDTO> articleDTOList=new ArrayList<>();

        if(params.get("order")!=null&&params.size()<3){
            if (articleDTOS != null){
                articleDTOList = articleDTOS.stream()
                        .filter(articleDTO -> articleDTO.getCategory().equalsIgnoreCase(params.get("category")))
                        .collect(Collectors.toList());
            }
            articleDTOList=getOrder(params.get("order"),articleDTOList);
            return articleDTOList;
        }

        String keys[]=new String[params.size()];
        int i=0;
        for(String param: params.keySet()){
            keys[i]=param;
            i++;
        }

        if (articleDTOS != null){
                articleDTOList = articleDTOS.stream()
                        .filter(articleDTO -> articleDTO.getAttribute(keys[0]).equalsIgnoreCase(params.get(keys[0]))
                        && articleDTO.getAttribute(keys[1]).equalsIgnoreCase(params.get(keys[1])))
                        .collect(Collectors.toList());
        }

        return articleDTOList;
    }

    /**
     * Filtra por 2 categorías y 1 orden.
     * @param articleDTOS
     * @param params
     * @return
     * @throws IOException
     * @throws ArticleDTONotFoudException
     */
    @Override
    public List<ArticleDTO> findProductsByCategoriesAndOrder(List<ArticleDTO> articleDTOS, Map<String,String> params) throws IOException, ArticleDTONotFoudException {
        List<ArticleDTO> articleDTOList=findProductsByCategories(articleDTOS,params);
        articleDTOList=getOrder(params.get("order"),articleDTOList);

        return articleDTOList;
    }

    public List<ArticleDTO> getOrder(String order, List<ArticleDTO> articleDTOList){
        switch (order){
            case "0": articleDTOList = sortAlphaAsc(articleDTOList);
                break;
            case "1": articleDTOList = sortAlphaDesc(articleDTOList);
                break;
            case "2": articleDTOList = sortAlphaHighestPrice(articleDTOList);
                break;
            case "3": articleDTOList = sortAlphaLowestPrice(articleDTOList);
                break;
        }
        return articleDTOList;
    }

    /**
     * Busca un artículo por nombre.
     * @param name
     * @param brand
     * @return
     * @throws ArticleDTONotFoudException
     */
    @Override
    public ArticleDTO findArticleByName(String name, String brand) throws ArticleDTONotFoudException {
        List<ArticleDTO> articleDTOS = null;
        articleDTOS = this.articleDTOS;
        ArticleDTO result = null;
        if (articleDTOS != null){
            Optional<ArticleDTO> item = articleDTOS.stream()
                    .filter(articleDTO -> articleDTO.getName().equalsIgnoreCase(name)
                            && articleDTO.getBrand().equalsIgnoreCase(brand))
                    .findFirst();
            if (item.isPresent())
                result = item.get();
            else throw new ArticleDTONotFoudException(name);
        }

        return result;
    }

    /**
     * Carga la base de datos.
     * @return
     */
    public List<ArticleDTO> loadDataBase() {
        FileReader fileReader = null;
        List<ArticleDTO> articles = new ArrayList<>();

        try {
            fileReader = new FileReader("src/main/resources/dbProductos.csv");

            BufferedReader csvReader = new BufferedReader(fileReader);

            String row;
            boolean firstTime = true;
            int idCounter=1;
            while ((row = csvReader.readLine()) != null) {
                if (firstTime) {
                    firstTime = false;
                } else {
                    String[] data = row.split(",");
                    articles.add(objectMapper(data,idCounter));
                    idCounter++;
                }
            }
            csvReader.close();

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            //throw FileNotFound
        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;
    }

    private ArticleDTO objectMapper(String[] data, int idCounter) {

        String name, category, brand, price, quantity, freeShipping, prestige;

        name = data[0];
        category = data[1];
        brand = data[2];
        price = data[3];
        quantity = data[4];
        freeShipping = data[5];
        prestige = data[6];

        return new ArticleDTO(idCounter,name, category, brand,
                Integer.parseInt(price.replaceAll("[^0-9]","")),
                Integer.parseInt(quantity),
                freeShipping, prestige);

    }

    public List<ArticleDTO> sortAlphaAsc(List<ArticleDTO> articles){
        articles.sort(Comparator.comparing(ArticleDTO::getName));

        return articles;
    }

    public List<ArticleDTO> sortAlphaDesc(List<ArticleDTO> articles){
        articles.sort(Comparator.comparing(ArticleDTO::getName).reversed());

        return articles;
    }

    public List<ArticleDTO> sortAlphaHighestPrice(List<ArticleDTO> articles){
        articles.sort(Comparator.comparing(ArticleDTO::getPrice));

        return articles;
    }

    public List<ArticleDTO> sortAlphaLowestPrice(List<ArticleDTO> articles){
        articles.sort(Comparator.comparing(ArticleDTO::getPrice).reversed());

        return articles;
    }
}
