package controllers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import entities.Tarefa;
import entities.enums.Prioridade;
import service.CadastroTarefas;
import service.NegocioException;

@Named
@javax.faces.view.ViewScoped
public class ConsultaTarefasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroTarefas cadastroTarefas;

	private List<Tarefa> tarefas;

	private String termoPesquisa;

	private Tarefa tarefa = new Tarefa();

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public Tarefa getTarefa() {
		 if (tarefa == null) {
		      tarefa = new Tarefa();
		   }
		   return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Prioridade[] getPrioridades() {
		return Prioridade.values();
	}

	public void findAll() {

		tarefas = cadastroTarefas.findAll();
		tarefas.removeIf(tarefa -> tarefa.getConcluido() == true);

	}

	public void findByName() {
		tarefas = cadastroTarefas.findByName(termoPesquisa);
		tarefas.removeIf(tarefa -> tarefa.getConcluido() == true);

	}

	public void salvar() throws NegocioException {
		
		cadastroTarefas.salvar(tarefa);
		tarefa = new Tarefa();
		
		atualizarRegistros();
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:tarefaDataTable"));
	}

	public void excluir() {
		cadastroTarefas.excluir(tarefa.getId());
		
		tarefa = new Tarefa();

		atualizarRegistros();
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:tarefaDataTable"));
	}

	public void concluir() {
		cadastroTarefas.concluir(tarefa.getId());

		tarefa = new Tarefa();
		atualizarRegistros();
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:tarefaDataTable"));
	}

	private boolean jaHouvePesquisa() {
		return termoPesquisa != null && !"".equals(termoPesquisa);
	}

	private void atualizarRegistros() {
		if (jaHouvePesquisa()) {
			findByName();

		} else {
			findAll();
		}
	}

	public void prepararEdicao() {
		System.out.println("preparaEdicao");

		tarefa = cadastroTarefas.findById(tarefa.getId());
	}

	public void editar() {

		cadastroTarefas.editar(tarefa);
		
		tarefa = new Tarefa();
		atualizarRegistros();
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:tarefaDataTable"));
	}

	public boolean isTarefaSelecionada() {
		return tarefa != null && tarefa.getId() != null;
	}
	
	public void preparaNovaTarefa() {
		System.out.println("preparaNovaTarefa");
		tarefa = new Tarefa();
	}
}
