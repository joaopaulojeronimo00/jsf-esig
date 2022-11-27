package service;

import java.io.Serializable;
import java.util.Date;

import entities.Tarefa;
import repositories.TarefaRepository;

public class CadastroTarefas implements Serializable{

	private static final long serialVersionUID = 1L;

	private TarefaRepository repository;
	
	public CadastroTarefas(TarefaRepository repository) {
		this.repository = repository;
	}
	
	public void salvar(Tarefa tarefa) throws NegocioException {
		if(tarefa.getDeadline() != null && tarefa.getDeadline().before(new Date())) {
			throw new NegocioException("O Deadline deve ser uma data Futura");
		}
		repository.insert(tarefa);
	}
	
	public void excluir(Long id) {
		System.out.println("3");
		repository.delete(repository.findById(id));		
		System.out.println("5");
	}
	
	public void concluir(Long id) {
		repository.concluir(repository.findById(id));
	}
	
	public void editar(Tarefa tarefa) {
		repository.update(tarefa);
	}
}
