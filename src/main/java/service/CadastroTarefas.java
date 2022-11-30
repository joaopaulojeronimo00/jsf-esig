package service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import entities.Tarefa;
import repositories.TarefaRepository;
import util.Transacional;

public class CadastroTarefas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TarefaRepository repository;

	public CadastroTarefas() {
	}

	@Transacional
	public void salvar(Tarefa tarefa) throws NegocioException {
		if (tarefa.getDeadline() != null && tarefa.getDeadline().before(new Date())) {
			throw new NegocioException("O Deadline deve ser uma data Futura");
		}
		repository.insert(tarefa);
	}

	@Transacional
	public void excluir(Long id) {
		repository.delete(repository.findById(id));
	}

	public void concluir(Long id) {
		repository.concluir(repository.findById(id));
	}

	public void editar(Tarefa tarefa) {
		repository.update(tarefa);
	}

	public List<Tarefa> findAll() {
		return repository.findAll();
	}

	public List<Tarefa> findByName(String termoPesquisa) {
		return repository.findByName(termoPesquisa);
	}

	public Tarefa findById(long id) {
		return repository.findById(id);
	}

}
