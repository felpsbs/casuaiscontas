package br.com.casuaiscontas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.service.StateService;
import br.com.casuaiscontas.service.UserService;
import br.com.casuaiscontas.service.exception.CpfAlreadyExistsException;
import br.com.casuaiscontas.service.exception.EmailAlreadyExistsException;

@Controller
@RequestMapping("/usuarios")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private StateService stateService;

	@GetMapping("/novo")
	public ModelAndView registerForm(User user) {
		ModelAndView mv = new ModelAndView("user/RegisterUser");
		mv.addObject("states", stateService.findAll());
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView register(@Valid User user, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return registerForm(user);
		}

		try {
			userService.save(user);
		} catch (EmailAlreadyExistsException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return registerForm(user);
		} catch (CpfAlreadyExistsException e) {
			result.rejectValue("cpf", e.getMessage(), e.getMessage());
			return registerForm(user);
		}

		attr.addFlashAttribute("success", true);
		attr.addFlashAttribute("message", "Cadastro realizado com sucesso");
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	@GetMapping("/{id}")
	public ModelAndView editForm(@PathVariable Long id) {
		User user = userService.findById(id);
		ModelAndView mv = registerForm(user);
		mv.addObject(user);
		return mv;
	}
		
}
