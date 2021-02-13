package com.moneyapp.model.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the establecimiento database table.
 * 
 */
@Entity
@NamedQuery(name="Establecimiento.findAll", query="SELECT e FROM Establecimiento e")
public class Establecimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idestablecimiento;

	private String nombre;

	//bi-directional many-to-one association to Cometido
	@OneToMany(mappedBy="establecimiento")
	@JsonIgnore
	private List<Cometido> cometidos;

	public Establecimiento() {
	}

	public int getIdestablecimiento() {
		return this.idestablecimiento;
	}

	public void setIdestablecimiento(int idestablecimiento) {
		this.idestablecimiento = idestablecimiento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Cometido> getCometidos() {
		return this.cometidos;
	}

	public void setCometidos(List<Cometido> cometidos) {
		this.cometidos = cometidos;
	}

	public Cometido addCometido(Cometido cometido) {
		getCometidos().add(cometido);
		cometido.setEstablecimiento(this);

		return cometido;
	}

	public Cometido removeCometido(Cometido cometido) {
		getCometidos().remove(cometido);
		cometido.setEstablecimiento(null);

		return cometido;
	}

}