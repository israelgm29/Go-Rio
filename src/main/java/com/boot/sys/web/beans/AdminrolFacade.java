/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boot.sys.web.beans;

import com.boot.sys.web.entidaes.Adminrol;
import java.util.Iterator;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 *
 * @author Mainfrek
 */
@Stateless
public class AdminrolFacade extends AbstractFacade<Adminrol> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminrolFacade() {
        super(Adminrol.class);
    }

    public Adminrol LoginUser(String email, String password) {
        try {
            Adminrol adminrol = (Adminrol) em.createNativeQuery("SELECT * FROM adminrol WHERE email = ? AND password = ? ;", Adminrol.class)
                    .setParameter(1, email)
                    .setParameter(2, password)
                    .getSingleResult();

            return adminrol;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean UpdatePassword(String email, String oldpass, String newpass) {
        boolean confirm = false;
        System.out.println(email);
        System.out.println(oldpass);
        System.out.println(newpass);
        try {
            int q = em.createQuery("UPDATE Adminrol a SET a.password = :newpassword WHERE a.email = :email AND a.password = :password")
                    .setParameter("newpassword", newpass)
                    .setParameter("email", email)
                    .setParameter("password", oldpass)
                    .executeUpdate();
            if (q != 0) {
                confirm = true;
                return confirm;
            } else {
                return confirm;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

}
