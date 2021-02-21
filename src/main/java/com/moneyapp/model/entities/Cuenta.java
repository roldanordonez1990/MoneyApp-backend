package com.moneyapp.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cuenta database table.
 * 
 */
@Entity
@NamedQuery(name="Cuenta.findAll", query="SELECT c FROM Cuenta c")
public class Cuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="num_cuenta")
	private int numCuenta;

	private float saldo;

	private String tipo;

	//bi-directional many-to-one association to Cometido
	@OneToMany(mappedBy="cuenta")
	private List<Cometido> cometidos;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public Cuenta() {
	}

	public int getNumCuenta() {
		return this.numCuenta;
	}

	public void setNumCuenta(int numCuenta) {
		this.numCuenta = numCuenta;
	}

	public float getSaldo() {
		return this.saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Cometido> getCometidos() {
		return this.cometidos;
	}

	public void setCometidos(List<Cometido> cometidos) {
		this.cometidos = cometidos;
	}

	public Cometido addCometido(Cometido cometido) {
		getCometidos().add(cometido);
		cometido.setCuenta(this);

		return cometido;
	}

	public Cometido removeCometido(Cometido cometido) {
		getCometidos().remove(cometido);
		cometido.setCuenta(null);

		return cometido;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}