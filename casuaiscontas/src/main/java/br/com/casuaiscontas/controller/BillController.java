package br.com.casuaiscontas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casuaiscontas.model.Bill;
import br.com.casuaiscontas.model.BillStatus;
import br.com.casuaiscontas.security.SystemUser;
import br.com.casuaiscontas.service.BillService;

@Controller
@RequestMapping("/contas")
public class BillController {
	
	@Autowired
	private BillService service;

	@GetMapping("/nova")
	public ModelAndView registerForm(Bill bill) {
		ModelAndView mv = new ModelAndView("bill/RegisterBill");
		mv.addObject("allStatus", BillStatus.values());
		return mv;
	}
	
	@PostMapping({ "/nova", "{\\d+}" })
	public ModelAndView register(@Valid Bill bill, BindingResult result, RedirectAttributes attr, @AuthenticationPrincipal SystemUser user) {		
		if(result.hasErrors()) {
			return registerForm(bill);
		}
			
		service.save(bill, user);
		
		attr.addFlashAttribute("success", true);
		attr.addFlashAttribute("message", "Operação realizada com sucesso");
		return new ModelAndView("redirect:/contas/nova");
	}
	
	
	@PreAuthorize("#userId == principal.user.id")
	@GetMapping("/{userId}/{billId}")
	public ModelAndView editForm(@PathVariable Long userId, @PathVariable Long billId) {
		Bill bill = service.findById(billId);		
		ModelAndView mv = registerForm(bill);
		mv.addObject(bill);
		return mv;
	}
	
}