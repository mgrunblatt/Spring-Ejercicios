package com.example.SpringLinkTracker.repositories;

import com.example.SpringLinkTracker.dtos.LinkDTO;
import com.example.SpringLinkTracker.dtos.StatsDTO;
import com.example.SpringLinkTracker.exceptions.InvalidUrlException;
import com.example.SpringLinkTracker.exceptions.UrlNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LinkDTORepositoryImpl implements LinkDTORepository {

    private Map<Long,LinkDTO> linkDTOMap = new HashMap<Long,LinkDTO>();
    private int countStats=0;

    @Override
    public void insertarUrl(LinkDTO link) {
        this.linkDTOMap.put(link.getId(),link);
    }

    @Override
    public LinkDTO findLinkById(Long id) throws UrlNotFoundException {
        if(linkDTOMap.containsKey(id)){
            linkDTOMap.get(id).setStats(linkDTOMap.get(id).getStats()+1);
            return linkDTOMap.get(id);
        }else{
            throw new UrlNotFoundException("Id:" + id);
        }
    }

    @Override
    public StatsDTO getStats(Long id) throws InvalidUrlException {
        if(linkDTOMap.get(id)!=null) {
            return new StatsDTO(id, linkDTOMap.get(id).getStats());
        }else throw new InvalidUrlException(""+id);
    }

    @Override
    public LinkDTO deleteLinkById(Long id) throws InvalidUrlException {
        LinkDTO linkDTORemoved=new LinkDTO(id,linkDTOMap.get(id).getUrl(),linkDTOMap.get(id).getStats());
        if(linkDTOMap.get(id)!=null) {
            linkDTOMap.remove(id, linkDTOMap.get(id));
            return linkDTORemoved;
        }else throw new InvalidUrlException(""+id);
    }
}
