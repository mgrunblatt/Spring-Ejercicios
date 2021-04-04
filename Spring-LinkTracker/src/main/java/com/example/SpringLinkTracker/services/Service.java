package com.example.SpringLinkTracker.services;

import com.example.SpringLinkTracker.dtos.LinkDTO;
import com.example.SpringLinkTracker.dtos.StatsDTO;
import com.example.SpringLinkTracker.exceptions.InvalidUrlException;
import com.example.SpringLinkTracker.exceptions.UrlNotFoundException;

public interface Service {


    public LinkDTO crearLinkDTO (LinkDTO lind) throws InvalidUrlException;
    public String buscarUrl (Long id) throws UrlNotFoundException;
    public StatsDTO obtenerStats(Long id) throws InvalidUrlException;
    public LinkDTO eliminarLinkDTO(Long id) throws InvalidUrlException;
}
