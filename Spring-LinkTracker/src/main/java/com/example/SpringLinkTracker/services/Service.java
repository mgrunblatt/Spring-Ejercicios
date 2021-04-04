package com.example.SpringLinkTracker.services;

import com.example.SpringLinkTracker.dtos.LinkDTO;
import com.example.SpringLinkTracker.dtos.StatsDTO;
import com.example.SpringLinkTracker.exceptions.InvalidUrlException;
import com.example.SpringLinkTracker.exceptions.UrlNotFoundException;

import java.util.UUID;

public interface Service {


    public LinkDTO crearLinkDTO (String url) throws InvalidUrlException;
    public String buscarUrl (Long id) throws UrlNotFoundException;
    public StatsDTO obtenerStats(Long id);
    public LinkDTO eliminarLinkDTO(Long id);
}
