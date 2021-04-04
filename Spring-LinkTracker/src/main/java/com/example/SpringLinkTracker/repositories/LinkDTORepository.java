package com.example.SpringLinkTracker.repositories;

import com.example.SpringLinkTracker.dtos.LinkDTO;
import com.example.SpringLinkTracker.dtos.StatsDTO;
import com.example.SpringLinkTracker.exceptions.InvalidUrlException;
import com.example.SpringLinkTracker.exceptions.UrlNotFoundException;

public interface LinkDTORepository {

    public void insertarUrl(LinkDTO link);
    public LinkDTO findLinkById(Long id) throws UrlNotFoundException;
    public StatsDTO getStats(Long id) throws InvalidUrlException;
    public LinkDTO deleteLinkById(Long id) throws InvalidUrlException;
}
