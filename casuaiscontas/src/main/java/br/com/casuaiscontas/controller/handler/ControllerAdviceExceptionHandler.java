package br.com.casuaiscontas.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casuaiscontas.service.exception.BillNotFoundException;
import br.com.casuaiscontas.service.exception.CityNotFoundException;
import br.com.casuaiscontas.service.exception.EntityBeenUsedException;
import br.com.casuaiscontas.service.exception.GeneratingReportException;
import br.com.casuaiscontas.service.exception.UserNotFoundException;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

	@ExceptionHandler({ BillNotFoundException.class, UserNotFoundException.class, CityNotFoundException.class })
	public ModelAndView resourceNotFoundException() {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("status", HttpStatus.NOT_FOUND.value());
		return mv;
	}
	
	@ExceptionHandler(EntityBeenUsedException.class)
	public @ResponseBody ResponseEntity<?> entityBeenUsedException(EntityBeenUsedException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}	
	
	@ExceptionHandler(GeneratingReportException.class)
	public @ResponseBody ModelAndView generatingReportException(GeneratingReportException e, RedirectAttributes attr) {
		attr.addFlashAttribute("message", e.getMessage());
		return new ModelAndView("redirect:/relatorios");
	}	
	
}
