package com.example.Springdesafio.services;

import com.example.Springdesafio.dtos.ArticleDTO;
import com.example.Springdesafio.dtos.StatusCodeDTO;
import com.example.Springdesafio.dtos.TicketDTO;
import com.example.Springdesafio.dtos.TicketResponseDTO;
import com.example.Springdesafio.exceptions.ArticleDTONotFoudException;
import com.example.Springdesafio.exceptions.ExceededArgumentLimitException;
import com.example.Springdesafio.exceptions.ExceededProductStockException;
import com.example.Springdesafio.repositories.ArticleDTORepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDTORepositoryImpl articleDTORepository;
    private double totalPurchase=0;
    private int ticketId=0;

    /**
     * Lista los productos con cero argumentos, por categoría, categoría+orden o 2 filtros y 1 orden.
     * @param params
     * @return
     * @throws IOException
     * @throws ExceededArgumentLimitException
     * @throws ArticleDTONotFoudException
     */
    @Override
    public List<ArticleDTO> listarProductos(Map<String,String> params) throws IOException, ExceededArgumentLimitException, ArticleDTONotFoudException {
        List<ArticleDTO> articleDTOS=articleDTORepository.loadDataBase();
        for (int i =0; i < articleDTOS.size();i++){
            if(articleDTOS.get(i).getFreeShipping().equals("SI")){
                articleDTOS.get(i).setFreeShipping("true");
            }else{
                articleDTOS.get(i).setFreeShipping("false");
            }
        }

        if(params.isEmpty())
            return articleDTOS;
        else
            switch (params.size()){
                case 1: articleDTOS = articleDTORepository.findProductsByCategory(articleDTOS, params);
                    break;
                case 2: articleDTOS = articleDTORepository.findProductsByCategories(articleDTOS, params);
                    break;
                case 3: articleDTOS = articleDTORepository.findProductsByCategoriesAndOrder(articleDTOS, params);
                    break;
                default: throw new ExceededArgumentLimitException("too many arguments. Max: 3 -> (2 filters, 1 order)");
            }
            return articleDTOS;
    }

    /**
     * Procesa una compra y hace manejo de stock siempre en memoria.
     * @param ticketDTO
     * @return
     * @throws ArticleDTONotFoudException
     * @throws IOException
     * @throws ExceededProductStockException
     */
    @Override
    public TicketResponseDTO procesarCompra(TicketDTO ticketDTO) throws ArticleDTONotFoudException, IOException, ExceededProductStockException {

        for (int i = 0; i < ticketDTO.getArticles().size(); i++) {
            ArticleDTO articleDTO = articleDTORepository.findArticleByName(ticketDTO.getArticles().get(i).getName(), ticketDTO.getArticles().get(i).getBrand());
            if(ticketDTO.getArticles().get(i).getQuantity()>articleDTO.getQuantity()){
                throw new ExceededProductStockException(""+articleDTO.getQuantity()+" unidades de "+articleDTO.getName());
            }

            totalPurchase += articleDTO.getPrice()*ticketDTO.getArticles().get(i).getQuantity();
            articleDTO.setQuantity(articleDTO.getQuantity()-ticketDTO.getArticles().get(i).getQuantity());
        }
        ticketId++;
        ticketDTO.setId(ticketId);
        ticketDTO.setArticles(ticketDTO.getArticles());
        ticketDTO.setTotal(totalPurchase);

        return new TicketResponseDTO(ticketDTO,
                new StatusCodeDTO(200,"La solicitud de compra se completó con exito"));
    }


}
