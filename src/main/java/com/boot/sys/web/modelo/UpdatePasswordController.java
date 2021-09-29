/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boot.sys.web.modelo;

import com.boot.sys.web.beans.AdminrolFacade;
import com.boot.sys.web.entidaes.Adminrol;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Mainfrek
 */
@Named(value = "updatePasswordController")
@RequestScoped
public class UpdatePasswordController {
    
    private String oldPass;
    private String newPass;
    private Adminrol current;
    @EJB
    private AdminrolFacade adminrolFacade;
    public UpdatePasswordController() {
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
    
    public Adminrol getSelected() {
        if (current == null) {
            current = new Adminrol();

        }
        return current;
    }
    public void UpdatePasswordAdmin(){
        FacesContext context = FacesContext.getCurrentInstance();
        boolean update = false;
         String oldpassMD5 = DigestUtils.md5Hex(current.getPassword());
         String newpassMD5 = DigestUtils.md5Hex(newPass);
         update = adminrolFacade.UpdatePassword(current.getEmail(), oldpassMD5, newpassMD5);
         System.out.println(update);
         
         if (update) {
             context.getPartialViewContext().getEvalScripts().add("Swal.fire({\n"
                    + "  icon: 'error',\n"
                    + "  title: 'Oops...',\n"
                    + "  text: '!Contraseña Actualizada¡',\n"
                    + "}) ");
        }else{
         
           context.getPartialViewContext().getEvalScripts().add("Swal.fire({\n"
                    + "  icon: 'error',\n"
                    + "  title: 'Oops...',\n"
                    + "  text: '!Error¡',\n"
                    + "}) ");
         }
    
    }
    
}
