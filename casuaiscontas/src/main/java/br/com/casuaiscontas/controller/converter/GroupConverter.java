package br.com.casuaiscontas.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.casuaiscontas.model.Group;

@Component
public class GroupConverter implements Converter<String, Group> {

	@Override
	public Group convert(String id) { 
		if(!StringUtils.isEmpty(id)) {
			Group group = new Group();
			group.setId(Long.valueOf(id));
			return group;
		}
		
		return null;
	}

}
