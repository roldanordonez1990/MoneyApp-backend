package com.moneyapp.model.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idusuario;

	private String email;

	@Lob
	private byte[] foto;

	private String password;

	private String username;

	//bi-directional many-to-one association to Cometido
	@OneToMany(mappedBy="usuario")
	@JsonIgnore
	private List<Cometido> cometidos;

	//bi-directional many-to-one association to Cuenta
	@OneToMany(mappedBy="usuario")
	@JsonIgnore
	private List<Cuenta> cuentas;

	public Usuario() {
	}

	public int getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Cometido> getCometidos() {
		return this.cometidos;
	}

	public void setCometidos(List<Cometido> cometidos) {
		this.cometidos = cometidos;
	}

	public Cometido addCometido(Cometido cometido) {
		getCometidos().add(cometido);
		cometido.setUsuario(this);

		return cometido;
	}

	public Cometido removeCometido(Cometido cometido) {
		getCometidos().remove(cometido);
		cometido.setUsuario(null);

		return cometido;
	}

	public List<Cuenta> getCuentas() {
		return this.cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public Cuenta addCuenta(Cuenta cuenta) {
		getCuentas().add(cuenta);
		cuenta.setUsuario(this);

		return cuenta;
	}

	public Cuenta removeCuenta(Cuenta cuenta) {
		getCuentas().remove(cuenta);
		cuenta.setUsuario(null);

		return cuenta;
	}

}