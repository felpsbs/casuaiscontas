package br.com.casuaiscontas.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.casuaiscontas.model.City;

@Component
public class CityConverter implements Converter<String, City> {

	@Override
	public City convert(String id) { 
		if(!StringUtils.isEmpty(id)) {
			City city = new City();
			city.setId(Long.valueOf(id));
			return city;
		}
		
		return null;
	}

}
