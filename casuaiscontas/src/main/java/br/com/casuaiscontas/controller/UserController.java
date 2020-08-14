package br.com.casuaiscontas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casuaiscontas.controller.page.PageWrapper;
import br.com.casuaiscontas.dto.user.UserDto;
import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.model.UserStatus;
import br.com.casuaiscontas.repository.filter.UserFilter;
import br.com.casuaiscontas.service.GroupService;
import br.com.casuaiscontas.service.StateService;
import br.com.casuaiscontas.service.UserService;
import br.com.casuaiscontas.service.exception.CpfAlreadyExistsException;
import br.com.casuaiscontas.service.exception.EmailAlreadyExistsException;
import br.com.casuaiscontas.service.exception.UserNotFoundException;

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
	public ModelAndView registerForm(User user) {
		ModelAndView mv = new ModelAndView("user/RegisterUser");
		mv.addObject("states", stateService.findAll());
		mv.addObject("grupos", groupService.findAll());
		return mv;
	}

	@PostMapping({ "/novo", "{\\d+}" })
	public ModelAndView register(@Valid User user, BindingResult result, RedirectAttributes attr) {
		String message = getFeedbackMessage(user);
		
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
		attr.addFlashAttribute("message", message);
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
	
	@GetMapping("/senha")
	public ModelAndView updatePasswordForm(UserDto userDto) {				
		return new ModelAndView("user/UpdatePassword");		
	}
	
	@PreAuthorize("#id == principal.user.id")
	@PostMapping("/{id}/senha")
	public ModelAndView updatePassword(@Valid UserDto userDto, BindingResult result, @PathVariable Long id, RedirectAttributes attr) {				
		if(result.hasErrors()) {
			return updatePasswordForm(userDto);
		}
			
		userService.updatePassword(id, userDto);			
		
		attr.addFlashAttribute("success", true);
		attr.addFlashAttribute("message", "Operação realizada com sucesso.");
		return new ModelAndView("redirect:/usuarios/senha");
	}
	
	@PreAuthorize("hasRole('ROLE_CADASTRAR_USUARIO')")
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/status")
	public void updateStatus(@RequestParam("ids[]") Long[] ids, @RequestParam("status") UserStatus userStatus) {
		userService.updateStatus(ids, userStatus);
	}
	
	@PreAuthorize("hasRole('ROLE_CADASTRAR_USUARIO')")
	@GetMapping
	public ModelAndView search(UserFilter userFilter, BindingResult result, @PageableDefault(size = 5) Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("user/SearchUser");
		mv.addObject("grupos", groupService.findAll());
		mv.addObject("page", new PageWrapper<>(userService.filter(userFilter, pageable), request));
		return mv;
	}	
	
	@GetMapping("/cadastro/confirmacao")
	public ModelAndView confirmRegister(@RequestParam("id") Long id) {
		userService.confirmRegister(id);	
		return new ModelAndView("redirect:/login");
	}
	
	@GetMapping("/recuperar/senha")
	public ModelAndView resetPasswordForm() {
		return new ModelAndView("user/PasswordReset");
	}
	
	@GetMapping("/recuperar/senha/{email}")
	public ModelAndView resetPasswordForm(@PathVariable String email) {
		UserDto userDto = userService.verify(email);
		ModelAndView mv = updatePasswordForm(userDto); 
		mv.addObject(userDto);
		return mv;
	}
	
	@PostMapping("/recuperar/senha")
	public ModelAndView resetPassword(String email, RedirectAttributes attr) {
		try {
			userService.generateCod(email);			
		} catch (UserNotFoundException e) {
			attr.addFlashAttribute("message", "E-mail inválido.");
			return new ModelAndView("redirect:/usuarios/recuperar/senha");
		}
		
		attr.addFlashAttribute("success", true);
		attr.addFlashAttribute("message", String.format("Um email foi enviado para  %s com os passos para redefinir sua senha.", email));
		return new ModelAndView("redirect:/usuarios/recuperar/senha");
	}
	
	@PostMapping("/redefinir/senha")
	public ModelAndView resetPassword(@Valid UserDto userDto, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			return updatePasswordForm(userDto); 
		}
		
		try {
			userService.updatePassword(userDto);			
		} catch (IllegalArgumentException e) {
			result.rejectValue("cod", e.getMessage(), e.getMessage());
			return updatePasswordForm(userDto);
		}
		
		attr.addFlashAttribute("success", true);
		attr.addFlashAttribute("message", "Senha redefinida com sucesso.");
		return new ModelAndView("redirect:/login");
	}

	private String getFeedbackMessage(User user) {
		return user.isNew() ? "Cadastro realizado com sucesso, verifique seu e-mail!" : "Operação realizada com sucesso"; 
	}	

}
