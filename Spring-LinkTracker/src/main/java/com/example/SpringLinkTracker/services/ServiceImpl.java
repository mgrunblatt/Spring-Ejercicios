package com.example.SpringLinkTracker.services;

import com.example.SpringLinkTracker.dtos.LinkDTO;
import com.example.SpringLinkTracker.dtos.StatsDTO;
import com.example.SpringLinkTracker.exceptions.InvalidUrlException;
import com.example.SpringLinkTracker.exceptions.UrlNotFoundException;
import com.example.SpringLinkTracker.repositories.LinkDTORepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

    @Autowired
    LinkDTORepositoryImpl linkDTORepository;

    private AtomicLong contador = new AtomicLong();

    @Override
    public LinkDTO crearLinkDTO(LinkDTO link) throws InvalidUrlException {
        if(validateUrl(link.getUrl())){
            LinkDTO linkDTO=new LinkDTO(contador.incrementAndGet(),link.getUrl(),0);
            linkDTORepository.insertarUrl(linkDTO);
            return linkDTO;
        }else{
            throw new InvalidUrlException(link.getUrl());
        }
    }

    @Override
    public String buscarUrl(Long id) throws UrlNotFoundException {
        return linkDTORepository.findLinkById(id).getUrl();
    }

    @Override
    public StatsDTO obtenerStats(Long id) throws InvalidUrlException {
        return linkDTORepository.getStats(id);
    }

    @Override
    public LinkDTO eliminarLinkDTO(Long id) throws InvalidUrlException {
        return linkDTORepository.deleteLinkById(id);
    }

    /*public boolean urlValidate(String url) {
        UrlValidator urlValidator = new UrlValidator();

        return urlValidator.isValid(url);
    }*/

    private boolean validateUrl (String url) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }

}
