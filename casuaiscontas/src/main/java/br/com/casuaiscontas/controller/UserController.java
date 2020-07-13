package br.com.casuaiscontas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.service.GroupService;
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

	@Autowired
	private GroupService groupService;

	@GetMapping("/novo")
	public ModelAndView registerForm(User testarUsuario) {
		ModelAndView mv = new ModelAndView("user/RegisterUser");
		mv.addObject("states", stateService.findAll());
		mv.addObject("grupos", groupService.findAll());
		return mv;
	}

	@PostMapping({ "/novo", "{\\d+}" })
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
		attr.addFlashAttribute("message", successMessage(user));
		return new ModelAndView("redirect:/usuarios/novo");
	}

	@PreAuthorize("#id == principal.user.id or hasRole('ROLE_CADASTRAR_USUARIO')")
	@GetMapping("/{id}")
	public ModelAndView editForm(@PathVariable Long id) {
		User user = userService.findUserWithGroups(id);
		ModelAndView mv = registerForm(user);
		mv.addObject(user);
		return mv;
	}
	
	private String successMessage(User user) {
		return user.isNew() ? "Cadastro realizado com sucesso, verifique seu e-mail!" : "Operação realizada com sucesso"; 
	}

}
