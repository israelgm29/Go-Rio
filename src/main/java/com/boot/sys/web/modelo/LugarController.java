package com.boot.sys.web.modelo;

import com.boot.sys.web.Const;
import com.boot.sys.web.entidaes.Lugar;
import com.boot.sys.web.modelo.util.JsfUtil;
import com.boot.sys.web.modelo.util.PaginationHelper;
import com.boot.sys.web.beans.LugarFacade;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;

@Named("lugarController")
@SessionScoped
public class LugarController implements Serializable {

    private Lugar current;
    private DataModel items = null;
    @EJB
    private com.boot.sys.web.beans.LugarFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Part imagen;
    private String destination;
    private String uploadlink;

    public LugarController() {
    }

    public Lugar getSelected() {
        if (current == null) {
            current = new Lugar();
            selectedItemIndex = -1;
        }
        return current;
    }

    private LugarFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Lugar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Lugar();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LugarCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Lugar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LugarUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Lugar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LugarDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Lugar getLugar(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public Part getImagen() {
        return imagen;
    }

    public void setImagen(Part imagen) {
        this.imagen = imagen;
    }
    
      private boolean upload() {
        try {
            destination = Const.URL;
            System.err.println(destination);
            File folder = new File(destination);
            if (!folder.exists()) {
                folder.mkdir();
            }
            copyFile(getImagen().getName(), getImagen());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

     public void copyFile(String filename, Part in) throws IOException {
        String fileName = Paths.get(in.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        String extension = FilenameUtils.getExtension(fileName);
        System.err.println(extension);
        File savedFile = new File(Const.URL,""+current.getNombre()+"."+extension+"");
        uploadlink= ""+Const.URLSERVER+Const.SEPARATOR + current.getNombre()+"."+extension+"";

        try (InputStream input = in.getInputStream()) {
            Files.copy(input, savedFile.toPath());
            current.setImagen(uploadlink);
        } catch (IOException e) {
            // Show faces message?
        }
    }

    public void newImage() {
        if (upload()) {
            current.setImagen(uploadlink);
            create();
            destination = "";
        }
    }


    @FacesConverter(forClass = Lugar.class)
    public static class LugarControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LugarController controller = (LugarController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lugarController");
            return controller.getLugar(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Lugar) {
                Lugar o = (Lugar) object;
                return getStringKey(o.getIdLugar());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Lugar.class.getName());
            }
        }

    }

}
