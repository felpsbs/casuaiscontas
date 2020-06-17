package br.com.casuaiscontas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casuaiscontas.repository.StateRepository;
  
@Controller  
@RequestMapping("/usuarios")
public class UserController {  
	    
	@Autowired
	private StateRepository stateRepository;

	@GetMapping("/novo")  
	public ModelAndView registerForm() {
		ModelAndView mv = new ModelAndView("user/registerUser");
		mv.addObject("states", stateRepository.findAll());  
		    
		return mv;
	}      
	
}  
