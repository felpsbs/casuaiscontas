package br.com.casuaiscontas.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casuaiscontas.model.Bill;
import br.com.casuaiscontas.model.BillStatus;

@Controller
@RequestMapping("/contas")
public class BillController {

	@GetMapping("/nova")
	public ModelAndView registerForm(Bill bill) {
		ModelAndView mv = new ModelAndView("bill/RegisterBill");
		mv.addObject("allStatus", BillStatus.values());
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView register(@Valid Bill bill, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			return registerForm(bill);
		}
		
		
		attr.addFlashAttribute("success", true);
		attr.addFlashAttribute("message", "Operação realizada com sucesso");
		return new ModelAndView("redirect:/contas/nova");
	}
	
}
