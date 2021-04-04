package com.example.SpringLinkTracker.controllers;

import com.example.SpringLinkTracker.dtos.LinkDTO;
import com.example.SpringLinkTracker.exceptions.InvalidUrlException;
import com.example.SpringLinkTracker.exceptions.UrlNotFoundException;
import com.example.SpringLinkTracker.services.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class UserController {

    @Autowired
    private ServiceImpl service;

    @PostMapping("/tracker")
    public ResponseEntity validarURL(@RequestBody LinkDTO linkDTO) throws InvalidUrlException {
        return new ResponseEntity(service.crearLinkDTO(linkDTO), HttpStatus.OK);
    }


    @GetMapping("/redirect")
    public RedirectView redirectWithUsingRedirectView(@RequestParam (value = "id",defaultValue = "Value") Long id,
            RedirectAttributes attributes) throws UrlNotFoundException {
        attributes.addFlashAttribute("flashAttribute", "redirect");
        attributes.addAttribute("attribute", "redirect");

        return new RedirectView(service.buscarUrl(id));
    }

    @GetMapping("/metrica")
    public ResponseEntity obtenerEstadisticas(@RequestParam(value = "id", defaultValue = "1" ) Long id) throws InvalidUrlException {
        return new ResponseEntity(service.obtenerStats(id), HttpStatus.OK);
    }

    @GetMapping("/invalidate")
    public ResponseEntity eliminarEndPoint(@RequestParam(value = "id", defaultValue = "1" ) Long id) throws InvalidUrlException {
        return new ResponseEntity(service.eliminarLinkDTO(id), HttpStatus.OK);
    }


}
