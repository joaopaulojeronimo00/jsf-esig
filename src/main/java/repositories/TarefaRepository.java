package repositories;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entities.Tarefa;

public class TarefaRepository implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	public TarefaRepository() {}
	
	public List<Tarefa> findAll(){
		TypedQuery<Tarefa> query = em.createQuery("from Tarefa", Tarefa.class);
		return query.getResultList();
	}
	
	public Tarefa findById(Long id) {
		System.out.println("6");
		System.out.println(em.find(Tarefa.class, id) + "id");
		return em.find(Tarefa.class, id);	
	}
	
	public void insert(Tarefa tarefa) {
		
		//FacesContext context = FacesContext.getCurrentInstance();
		/*try {
			em.getTransaction().begin();
			em.persist(tarefa);		
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();

			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, message);
		}finally {
			
		}*/
		em.persist(tarefa);	
		
	}
	
	public void delete(Tarefa tarefa) {		
		em.remove(tarefa);
		
	}
	
	public Tarefa update(Tarefa tarefa) {
		return em.merge(tarefa);
	}

	public List<Tarefa> findByName(String termoPesquisa) {
		TypedQuery<Tarefa> query = em.createQuery("from Tarefa where titulo like :titulo ", Tarefa.class);
		
		query.setParameter("titulo", termoPesquisa + "%");
		
		return query.getResultList();
	}
	
	public void concluir(Tarefa tarefa) {
		tarefa.setConcluido(true);
		em.merge(tarefa);
	}
}
