package com.example.SpringLinkTracker.services;

import com.example.SpringLinkTracker.dtos.LinkDTO;
import com.example.SpringLinkTracker.exceptions.InvalidUrlException;
import com.example.SpringLinkTracker.exceptions.UrlNotFoundException;
import com.example.SpringLinkTracker.repositories.LinkDTORepositoryImpl;
import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

    @Autowired
    LinkDTORepositoryImpl linkDTORepository;

    private AtomicLong contador = new AtomicLong();

    @Override
    public LinkDTO crearLinkDTO(String url) throws InvalidUrlException {
        if(validateUrl(url)){
            LinkDTO linkDTO=new LinkDTO(contador.incrementAndGet(),url);
            linkDTORepository.insertarUrl(linkDTO);
            return linkDTO;
        }else{
            throw new InvalidUrlException(url);
        }
    }

    @Override
    public String buscarUrl(Long id) throws UrlNotFoundException {
        return linkDTORepository.findLinkById(id).getUrl();
    }

    /*public boolean urlValidate(String url) {
        UrlValidator urlValidator = new UrlValidator();

        return urlValidator.isValid(url);
    }*/

    public boolean validateUrl (String url) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }

}
