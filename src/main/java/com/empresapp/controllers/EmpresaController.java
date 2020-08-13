package com.empresapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.empresapp.models.Empresa;
import com.empresapp.models.Funcionario;
import com.empresapp.repository.EmpresaRepository;
import com.empresapp.repository.FuncionarioRepository;

@Controller
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository er;
	
	@Autowired
	private FuncionarioRepository fr;
	
	@RequestMapping(value="/cadastrarEmpresa", method=RequestMethod.GET)
	public String form() {
		return "empresa/formEmpresa";
	}
	
	@RequestMapping(value="/cadastrarEmpresa", method=RequestMethod.POST)
	public String form(Empresa empresa) {
		er.save(empresa);
		return "redirect:/cadastrarEmpresa";
	}
	
	@RequestMapping("/empresas")
	public ModelAndView listaEmpresas() {
		ModelAndView mv = new ModelAndView("Index");
		Iterable<Empresa> empresas = er.findAll();
		mv.addObject("empresas", empresas);
		return mv;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView detalhesEmpresa(@PathVariable("id") long id) {
		Empresa empresa = er.findById(id);
		ModelAndView mv = new ModelAndView("empresa/detalhesEmpresa");
		mv.addObject("empresa", empresa);
		Iterable<Funcionario> funcionarios = fr.findByEmpresa(empresa);
		mv.addObject("funcionarios", funcionarios);
		return mv;
	}
	
	@RequestMapping("/deletarEmpresa")
	public String deletarEmpresa(long id) {
		Empresa empresa = er.findById(id);
		er.delete(empresa);
		return "redirect:/empresas";
	}
	
	@RequestMapping("/deletarFuncionario")
	public String deletarFuncionario(long id) {
		Funcionario funcionario = fr.findById(id);
		fr.delete(funcionario);
		Empresa empresa = funcionario.getEmpresa();
		long idEmpresa = empresa.getId();
		String idEmp = "" + idEmpresa;
		return "redirect:/" + idEmp;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public String detalhesEmpresaPost(@PathVariable("id") long id, Funcionario funcionario) {
		Empresa empresa = er.findById(id);
		funcionario.setEmpresa(empresa);
		fr.save(funcionario);
		return "redirect:/{id}";
	}

}
