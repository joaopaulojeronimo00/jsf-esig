package test;

import javax.inject.Inject;

import repositories.TarefaRepository;
import service.CadastroTarefas;

public class teste {
	
	@Inject
	private TarefaRepository repository;
	
	public void main(String[] args) {
		
		System.out.println(repository.findAll());
	}
	
	
	
}
