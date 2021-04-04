package com.example.SpringLinkTracker.repositories;

import com.example.SpringLinkTracker.dtos.LinkDTO;
import com.example.SpringLinkTracker.dtos.StatsDTO;
import com.example.SpringLinkTracker.exceptions.UrlNotFoundException;

import java.util.UUID;

public interface LinkDTORepository {

    public void insertarUrl(LinkDTO link);
    public LinkDTO findLinkById(Long id) throws UrlNotFoundException;
    public StatsDTO getStats(Long id);
    public LinkDTO deleteLinkById(Long id);
}
