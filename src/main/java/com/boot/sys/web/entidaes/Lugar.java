/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boot.sys.web.entidaes;

import com.boot.sys.web.entidaes.Categoria;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Mainfrek
 */
@Entity
@Table(name = "lugar")
@NamedQueries({
    @NamedQuery(name = "Lugar.findAll", query = "SELECT l FROM Lugar l"),
    @NamedQuery(name = "Lugar.findByIdLugar", query = "SELECT l FROM Lugar l WHERE l.idLugar = :idLugar"),
    @NamedQuery(name = "Lugar.findByNombre", query = "SELECT l FROM Lugar l WHERE l.nombre = :nombre"),
    @NamedQuery(name = "Lugar.findByDescripcion", query = "SELECT l FROM Lugar l WHERE l.descripcion = :descripcion"),
    @NamedQuery(name = "Lugar.findByPuntox", query = "SELECT l FROM Lugar l WHERE l.puntox = :puntox"),
    @NamedQuery(name = "Lugar.findByPuntoy", query = "SELECT l FROM Lugar l WHERE l.puntoy = :puntoy"),
    @NamedQuery(name = "Lugar.findByImagen", query = "SELECT l FROM Lugar l WHERE l.imagen = :imagen")})
public class Lugar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lugar")
    private Integer idLugar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "puntox")
    private double puntox;
    @Basic(optional = false)
    @NotNull
    @Column(name = "puntoy")
    private double puntoy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "imagen")
    private String imagen;
    @JoinColumn(name = "categoria", referencedColumnName = "id_categoria")
    @ManyToOne(optional = false)
    private Categoria categoria;

    public Lugar() {
    }

    public Lugar(Integer idLugar) {
        this.idLugar = idLugar;
    }

    public Lugar(Integer idLugar, String nombre, String descripcion, double puntox, double puntoy, String imagen) {
        this.idLugar = idLugar;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntox = puntox;
        this.puntoy = puntoy;
        this.imagen = imagen;
    }

    public Integer getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(Integer idLugar) {
        this.idLugar = idLugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPuntox() {
        return puntox;
    }

    public void setPuntox(double puntox) {
        this.puntox = puntox;
    }

    public double getPuntoy() {
        return puntoy;
    }

    public void setPuntoy(double puntoy) {
        this.puntoy = puntoy;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLugar != null ? idLugar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lugar)) {
            return false;
        }
        Lugar other = (Lugar) object;
        if ((this.idLugar == null && other.idLugar != null) || (this.idLugar != null && !this.idLugar.equals(other.idLugar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boot.sys.asdfg.Lugar[ idLugar=" + idLugar + " ]";
    }
    
}
