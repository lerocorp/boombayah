package com.retobackend.controllers;

import com.retobackend.domain.Trama;
import com.retobackend.domain.TramaDTO;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;


@RestController
public class RetoBackend {

    @Value("${servicio.jsonplaceholder.typicode}")
    public String ruta;

    public Gson gson = new Gson();
    public List<TramaDTO> main = new LinkedList<>();

    @GetMapping({"/",""})
    public String validarGet(){
        Client url = Client.create();
        WebResource recurso = url.resource(ruta);
        return recurso.header("vacio","vacio")
                                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                                .type(MediaType.APPLICATION_JSON_VALUE)
                                                .get(ClientResponse.class)
                                                .getEntity(String.class);
    }

    @PostMapping(value = "/post")
    public String obtenerReestructuracion(){
        Client url = Client.create();
        WebResource recurso = url.resource(ruta);
        Trama[] json =  gson.fromJson(recurso.header("head","head")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .type(MediaType.APPLICATION_JSON_VALUE)
                                .get(ClientResponse.class)
                                .getEntity(String.class), Trama[].class);

        for(Trama aux : json){
            TramaDTO dto = new TramaDTO();
            dto.setPostId(aux.getPostId());
            dto.setId(aux.getId());
            dto.setEmail(aux.getEmail());
            main.add(dto);
        }
        return gson.toJson(main.toArray());
    }
}
