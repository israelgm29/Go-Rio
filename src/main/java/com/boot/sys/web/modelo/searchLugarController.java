/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boot.sys.web.modelo;

import com.boot.sys.web.beans.LugarFacade;
import com.boot.sys.web.entidaes.Lugar;
import java.io.Serializable;
import java.util.List;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.DataModel;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 *
 * @author Mainfrek
 */
@Named(value = "searchLugarController")
@ViewScoped
public class searchLugarController implements Serializable{
    private String buscar;
    private boolean render= false;
    private boolean b = true;
    private List<Lugar> findLugar;
    @EJB
    private LugarFacade lugarFacade;
    private DataModel<Lugar> lugarDataModel;

    
    public searchLugarController() {
    }
    
    public List<Lugar> getFindLugar() {
        return findLugar;
    }

    public void setFindLugar(List<Lugar> findLugar) {
        this.findLugar = findLugar;
    }
  
    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }
    
 
    public void searchLugar(){
        System.out.println("asd");
        findLugar = lugarFacade.searchLugar(buscar);
        if (!findLugar.isEmpty()) {
            render=true;
            b=false;
        }
    
    }
     

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }
}
