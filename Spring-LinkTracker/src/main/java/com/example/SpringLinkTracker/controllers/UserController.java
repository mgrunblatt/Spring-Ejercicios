package com.example.SpringLinkTracker.controllers;

import com.example.SpringLinkTracker.exceptions.InvalidUrlException;
import com.example.SpringLinkTracker.exceptions.UrlNotFoundException;
import com.example.SpringLinkTracker.services.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private ServiceImpl service;

    @GetMapping("/tracker")
    public ResponseEntity validarURL(@RequestParam(value = "url", defaultValue = "https://www.facebook.com" ) String url) throws InvalidUrlException {
        return new ResponseEntity(service.crearLinkDTO(url), HttpStatus.OK);
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
