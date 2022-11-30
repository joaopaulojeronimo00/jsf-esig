package cria;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entities.Tarefa;
import entities.enums.Prioridade;
import util.JpaUtil;

public class CriaTarefas {
	
	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		
		Tarefa t1 = new Tarefa();
		t1.setTitulo("Tarefa 1");
		t1.setDescricao("Descrição 1");
		t1.setResponsavel("Rodrigo");
		t1.setPrioridade(Prioridade.BAIXA);
		t1.setDeadline(sdf.parse("21/04/2023"));
		
		Tarefa t2 = new Tarefa();
		t2.setTitulo("Tarefa 2");
		t2.setDescricao("Descrição 2");
		t2.setResponsavel("Amanda");
		t2.setPrioridade(Prioridade.ALTA);
		t2.setDeadline(sdf.parse("21/01/2023"));

		Tarefa t3 = new Tarefa();
		t3.setTitulo("Tarefa 3");
		t3.setDescricao("Descrição 3");
		t3.setResponsavel("Pedro");
		t3.setPrioridade(Prioridade.ALTA);
		t3.setDeadline(sdf.parse("21/01/2024"));
		
		Tarefa t4 = new Tarefa();
		t4.setTitulo("Tarefa 4");
		t4.setDescricao("Descrição 4");
		t4.setResponsavel("Laís");
		t4.setPrioridade(Prioridade.ALTA);
		t4.setDeadline(sdf.parse("21/03/2023"));
				
		em.persist(t1);
		em.persist(t2);
		em.persist(t3);
		em.persist(t4);
		
		et.commit();	
		em.close();		
	}
}
