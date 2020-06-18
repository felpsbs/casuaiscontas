package br.com.casuaiscontas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.repository.StateRepository;
  
@Controller  
@RequestMapping("/usuarios")
public class UserController {  
	    
	@Autowired
	private StateRepository stateRepository;

	@GetMapping("/novo")  
	public ModelAndView registerForm(User user) {
		ModelAndView mv = new ModelAndView("user/registerUser");
		mv.addObject("states", stateRepository.findAll());  
		    
		return mv;
	}    
	
	@PostMapping("/novo")
	public ModelAndView register(@Valid User user, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			return registerForm(user);
		}
		
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
}  
