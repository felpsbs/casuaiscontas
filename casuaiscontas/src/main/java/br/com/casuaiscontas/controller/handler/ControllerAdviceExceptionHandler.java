package br.com.casuaiscontas.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import br.com.casuaiscontas.service.exception.BillNotFoundException;
import br.com.casuaiscontas.service.exception.CityNotFoundException;
import br.com.casuaiscontas.service.exception.UserNotFoundException;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

	@ExceptionHandler({ BillNotFoundException.class, UserNotFoundException.class, CityNotFoundException.class })
	public ModelAndView resourceNotFoundException() {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("status", HttpStatus.NOT_FOUND.value());
		return mv;
	}
	
}
