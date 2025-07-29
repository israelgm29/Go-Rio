/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boot.sys.web.beans;

import com.boot.sys.web.entidaes.Lugar;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 *
 * @author Mainfrek
 */
@Stateless
public class LugarFacade extends AbstractFacade<Lugar> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LugarFacade() {
        super(Lugar.class);
    }

    public List searchLugar(String search) {

        Query q = em.createNamedQuery("Lugar.findByNombre", Lugar.class);
        q.setParameter("nombre", search);
        System.err.println(q);
        return q.getResultList();

    }

   
    
    public List allPlacesSta(){
        Query q = em.createNativeQuery("SELECT COUNT (categoria) FROM lugar GROUP BY categoria ORDER BY categoria");
         List count = q.getResultList();
        System.out.println(count);
        return count;
    
    }
}
