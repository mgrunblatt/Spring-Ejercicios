package com.example.SpringLinkTracker.repositories;

import com.example.SpringLinkTracker.dtos.LinkDTO;
import com.example.SpringLinkTracker.exceptions.UrlNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class LinkDTORepositoryImpl implements LinkDTORepository {

    private Map<Long,LinkDTO> linkDTOMap = new HashMap<Long,LinkDTO>();

    @Override
    public void insertarUrl(LinkDTO link) {
        this.linkDTOMap.put(link.getId(),link);
    }

    @Override
    public LinkDTO findLinkById(Long id) throws UrlNotFoundException {
        if(linkDTOMap.containsKey(id)){
            return linkDTOMap.get(id);
        }else{
            throw new UrlNotFoundException("Id:" + id);
        }
    }
}
