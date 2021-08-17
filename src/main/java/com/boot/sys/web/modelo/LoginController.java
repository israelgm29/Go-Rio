/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boot.sys.web.modelo;

import com.boot.sys.web.beans.AdminrolFacade;
import com.boot.sys.web.entidaes.Adminrol;
import com.boot.sys.web.modelo.util.SessionUtils;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mainfrek
 */
@Named(value = "loginController")
@ApplicationScoped
public class LoginController {

    private Adminrol current;
    @EJB
    private AdminrolFacade adminrolFacade;

    public LoginController() {
    }

    public Adminrol getCurrent() {
        return current;
    }

    public void setCurrent(Adminrol current) {
        this.current = current;
    }

    public Adminrol getSelected() {
        if (current == null) {
            current = new Adminrol();

        }
        return current;
    }

    public String Login() {
        FacesContext context = FacesContext.getCurrentInstance();

        Adminrol adminrol = adminrolFacade.LoginUser(current.getEmail(), current.getPassword());

        if (adminrol != null) {
            if (adminrol.getEmail().contains(current.getEmail()) && adminrol.getPassword().contains(current.getPassword())) {
                context.getExternalContext().getSessionMap().put("userlog", adminrol);
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("username", adminrol.getNombre());
                return "index";
            }
        } else {
            context.getPartialViewContext().getEvalScripts().add("Swal.fire({\n"
                    + "  icon: 'error',\n"
                    + "  title: 'Oops...',\n"
                    + "  text: '!Email o contraseña incorrectos¡',\n"
                    + "}) ");
            return null;
        }
        return null;
    }
    
    public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "/faces/Login";
	}

}
