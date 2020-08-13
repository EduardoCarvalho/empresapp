package com.empresapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.empresapp.models.Empresa;


public interface EmpresaRepository extends CrudRepository<Empresa, String> {
	Empresa findById(long id);
}
