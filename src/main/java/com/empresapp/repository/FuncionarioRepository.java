package com.empresapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.empresapp.models.Empresa;
import com.empresapp.models.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, String> {
	Iterable<Funcionario> findByEmpresa(Empresa empresa);
	Funcionario findById(long id);
}
