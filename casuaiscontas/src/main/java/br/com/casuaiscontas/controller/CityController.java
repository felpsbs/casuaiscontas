package br.com.casuaiscontas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.casuaiscontas.model.City;
import br.com.casuaiscontas.service.CityService;

@Controller
@RequestMapping("/cidades")
public class CityController {

	@Autowired
	private CityService service;

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<City> findCitiesByState(@RequestParam(name = "state", defaultValue = "-1") Long stateId) {
		return service.findByState(stateId);
	}
}
