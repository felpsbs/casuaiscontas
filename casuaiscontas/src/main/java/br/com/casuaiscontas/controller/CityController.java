package br.com.casuaiscontas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casuaiscontas.controller.page.PageWrapper;
import br.com.casuaiscontas.model.City;
import br.com.casuaiscontas.repository.filter.CityFilter;
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
	@PostMapping({ "/nova", "{\\d+}" })
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
	
	@PreAuthorize("hasRole('ROLE_CADASTRAR_CIDADE')")
	@GetMapping
	public ModelAndView search(CityFilter cityFilter, BindingResult result, @PageableDefault(size = 5) Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("city/SearchCity");
		mv.addObject("states", stateService.findAll());
		mv.addObject("page", new PageWrapper<>(cityService.filter(cityFilter, pageable), request));
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView editForm(@PathVariable Long id) {
		City city = cityService.findCityWithState(id);
		ModelAndView mv = registerForm(city);
		mv.addObject(city);
		return mv;
	}

	@Cacheable(value = "cities", key = "#stateId")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<City> findCitiesByState(@RequestParam(name = "state", defaultValue = "-1") Long stateId) {
		return cityService.findByState(stateId);
	}
	
}
