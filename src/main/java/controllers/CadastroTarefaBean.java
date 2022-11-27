package controllers;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entities.Tarefa;
import entities.enums.Prioridade;
import repositories.TarefaRepository;
import service.CadastroTarefas;
import service.NegocioException;
import util.JpaUtil;

@ManagedBean
@ViewScoped
public class CadastroTarefaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Tarefa tarefa = new Tarefa();

	private Long id;

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
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public Prioridade[] getPrioridades() {
		return Prioridade.values();
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void excluir() {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();

		et.begin();
		CadastroTarefas cadastroTarefas = new CadastroTarefas(new TarefaRepository(em));

		cadastroTarefas.excluir(id);

		context.addMessage(null, new FacesMessage("Cadastro excluído com sucesso!"));

		et.commit();
		em.close();
	}

	public void concluir() {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();

		et.begin();

		CadastroTarefas cadastroTarefas = new CadastroTarefas(new TarefaRepository(em));

		cadastroTarefas.concluir(id);

		context.addMessage(null, new FacesMessage("Tarefa concluída com sucesso!"));

		et.commit();
		em.close();
	}

}
