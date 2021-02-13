package com.moneyapp.model.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the cometido database table.
 * 
 */
@Entity
@NamedQuery(name="Cometido.findAll", query="SELECT c FROM Cometido c")
public class Cometido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idcometido;

	private String categoria;

	private int comision;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private int gasto;

	//bi-directional many-to-one association to Cuenta
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_cuenta")
	@JsonIgnore
	private Cuenta cuenta;

	//bi-directional many-to-one association to Establecimiento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="lugar")
	@JsonIgnore
	private Establecimiento establecimiento;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_user")
	@JsonIgnore
	private Usuario usuario;

	public Cometido() {
	}

	public int getIdcometido() {
		return this.idcometido;
	}

	public void setIdcometido(int idcometido) {
		this.idcometido = idcometido;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getComision() {
		return this.comision;
	}

	public void setComision(int comision) {
		this.comision = comision;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getGasto() {
		return this.gasto;
	}

	public void setGasto(int gasto) {
		this.gasto = gasto;
	}

	public Cuenta getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Establecimiento getEstablecimiento() {
		return this.establecimiento;
	}

	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}