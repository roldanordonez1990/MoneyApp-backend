package com.moneyapp.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.moneyapp.model.entities.Cometido;
import com.moneyapp.model.entities.Cuenta;
import com.moneyapp.model.entities.Establecimiento;

public interface CuentaRepository extends CrudRepository<Cuenta, Integer>{
	
	
	@Query(value="SELECT c.* FROM cuenta as c where c.id_usuario=?", nativeQuery = true)
	public List<Cuenta>getTodasLasCuentasPorUsuario(int idUsuario);
	
	@Query(value="UPDATE cuenta SET saldo = ? WHERE num_cuenta = ? and id_usuario = ?", nativeQuery = true)
	public List<Cuenta>modificaSaldoCuenta(float saldo, int num_cuenta, int idUsuario);
	
	@Query(value="SELECT c.* FROM cuenta as c where c.id_usuario=?", nativeQuery = true)
	public Cuenta getCuentaUsuario(int idUsuario);
	
	@Query(value="SELECT c.* FROM cuenta as c where c.num_cuenta=?", nativeQuery = true)
	public Cuenta getCuentaPorNumero(int num_cuenta);
}
