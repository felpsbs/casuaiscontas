package br.com.casuaiscontas.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.casuaiscontas.model.State;

@Component
public class StateConverter implements Converter<String, State> {

	@Override
	public State convert(String id) { 
		if(!StringUtils.isEmpty(id)) {
			State state = new State();
			state.setId(Long.valueOf(id));
			return state;
		}
		
		return null;
	}

}
