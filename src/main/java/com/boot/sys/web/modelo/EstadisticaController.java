/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boot.sys.web.modelo;

import com.boot.sys.web.beans.CategoriaFacade;
import com.boot.sys.web.beans.LugarFacade;
import com.boot.sys.web.entidaes.Categoria;
import com.boot.sys.web.entidaes.Lugar;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.List;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;

/**
 *
 * @author Mainfrek
 */
@Named(value = "estadisticaController")
@ViewScoped
public class EstadisticaController implements Serializable {

    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private LugarFacade lugarFacade;

    public EstadisticaController() {
    }

    public String allCategories() {

        List<String> categorias = categoriaFacade.getAllNameCategories();
        String json = new Gson().toJson(categorias);
//        System.out.println(json);
        return json;

    }

    public List<Long> allPlaces() {

//        List<Lugar> lugares = lugarFacade.allPlaces();
//        String json = new Gson().toJson(lugares);
//        System.out.println(json);
//        return json;
        List<Long> c1 = lugarFacade.allPlacesSta();
        return c1;
    }
}
