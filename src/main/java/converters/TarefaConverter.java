package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import entities.Tarefa;
import repositories.TarefaRepository;

@FacesConverter(forClass = Tarefa.class)
public class TarefaConverter implements Converter {

	
	@Inject
	TarefaRepository repository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Tarefa retorno = null;
		
		
		try {
			
			if (value != null && !"".equals(value)) {				
				
				Long id = Long.valueOf(value);
				retorno = new Tarefa();
				retorno.setConcluido(repository.findById(id).getConcluido());
				retorno.setDeadline(repository.findById(id).getDeadline());
				retorno.setId(repository.findById(id).getId());
				retorno.setTitulo(repository.findById(id).getTitulo());
				retorno.setDescricao(repository.findById(id).getDescricao());
				retorno.setPrioridade(repository.findById(id).getPrioridade());
				retorno.setResponsavel(repository.findById(id).getResponsavel());
				}
			return retorno;
		}finally {
			
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			return ((Tarefa) value).toString();
		}
		return null;
	}	
}
