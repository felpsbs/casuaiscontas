package br.com.casuaiscontas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casuaiscontas.model.City;
import br.com.casuaiscontas.service.CityService;
import br.com.casuaiscontas.service.StateService;
import br.com.casuaiscontas.service.exception.CityAlreadyExistsException;

@Controller
@RequestMapping("/cidades")
public class CityController {

	@Autowired
	private CityService cityService;
	
	@Autowired
	private StateService stateService;
	
	@GetMapping("/nova")
	public ModelAndView registerForm(City city) {
		ModelAndView mv = new ModelAndView("city/RegisterCity");
		mv.addObject("states", stateService.findAll());
		return mv;
	}
	
	@CacheEvict(value = "cities", key = "#city.state.id", condition = "#city.hasState()")
	@PostMapping("/nova")
	public ModelAndView register(@Valid City city, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			return registerForm(city);
		}
		
		try {
			cityService.save(city);
		} catch (CityAlreadyExistsException e) {
			result.rejectValue("name", e.getMessage(), e.getMessage());
			return registerForm(city);
		}
		
		attr.addFlashAttribute("success", true);
		attr.addFlashAttribute("message", "Operação realizada com sucesso");
		return new ModelAndView("redirect:/cidades/nova");
	}

	@Cacheable(value = "cities", key = "#stateId")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<City> findCitiesByState(@RequestParam(name = "state", defaultValue = "-1") Long stateId) {
		return cityService.findByState(stateId);
	}
	
}
