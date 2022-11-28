package controllers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.context.RequestContext;

import entities.Tarefa;
import entities.enums.Prioridade;
import repositories.TarefaRepository;
import service.CadastroTarefas;
import service.NegocioException;
import util.JpaUtil;

@ManagedBean
@ViewScoped
public class ConsultaTarefasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Tarefa> tarefas;

	private String termoPesquisa;

	private Tarefa tarefa = new Tarefa();

	private Tarefa aux = new Tarefa();

	private Long id;
	
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
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Prioridade[] getPrioridades() {
		return Prioridade.values();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAux(Tarefa aux) {
		this.aux = aux;
	}

	public Tarefa getAux() {
		return aux;
	}

	public void findAll() {
		EntityManager em = JpaUtil.getEntityManager();
		TarefaRepository tr = new TarefaRepository(em);

		tarefas = tr.findAll();
		em.close();
	}

	public void findByName() {
		EntityManager em = JpaUtil.getEntityManager();
		TarefaRepository tr = new TarefaRepository(em);

		tarefas = tr.findByName(termoPesquisa);
		em.close();
	}

	public void salvar() {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			et.begin();

			CadastroTarefas cadastroTarefas = new CadastroTarefas(new TarefaRepository(em));

			cadastroTarefas.salvar(tarefa);

			tarefa = new Tarefa();
			context.addMessage(null, new FacesMessage("Cadastro salvo com sucesso!"));

			et.commit();
		} catch (NegocioException e) {
			et.rollback();

			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, message);
		} finally {
			em.close();
		}
		atualizarRegistros();
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:tarefaDataTable"));
	}

	public void excluir() {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();

		try {

			et.begin();
			CadastroTarefas cadastroTarefas = new CadastroTarefas(new TarefaRepository(em));
			System.out.println("1");
			cadastroTarefas.excluir(id);
			System.out.println("2");

			context.addMessage(null, new FacesMessage("Cadastro excluído com sucesso!"));

			et.commit();
		} finally {

			id = null;
			if (em != null)
				em.close();
		}

		atualizarRegistros();
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:tarefaDataTable"));
	}

	public void concluir() {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();

		et.begin();

		CadastroTarefas cadastroTarefas = new CadastroTarefas(new TarefaRepository(em));
		System.out.println(id);
		cadastroTarefas.concluir(id);
		
		id = null;

		context.addMessage(null, new FacesMessage("Tarefa concluída com sucesso!"));

		et.commit();
		em.close();

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
		EntityManager em = JpaUtil.getEntityManager();
		TarefaRepository tr = new TarefaRepository(em);

		tarefa = tr.findById(7l);
		em.close();
		id = null;
		}

	public void editar() {
		
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			et.begin();

			CadastroTarefas cadastroTarefas = new CadastroTarefas(new TarefaRepository(em));
			
			cadastroTarefas.editar(tarefa);

			aux = new Tarefa();
			tarefa = new Tarefa();
			context.addMessage(null, new FacesMessage("Cadastro salvo com sucesso!"));

			et.commit();
		} finally {
			em.close();
		}
		atualizarRegistros();
		RequestContext.getCurrentInstance().update(Arrays.asList("frm:tarefaDataTable"));
	}
}
