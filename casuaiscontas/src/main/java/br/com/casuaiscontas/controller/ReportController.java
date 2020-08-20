package br.com.casuaiscontas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casuaiscontas.dto.CustomReport;
import br.com.casuaiscontas.service.ReportService;
import br.com.casuaiscontas.service.exception.GeneratingReportException;

@Controller
@RequestMapping("/relatorios")
public class ReportController {
		
	@Autowired
	private ReportService reportService;

	@GetMapping
	public ModelAndView report() {
		return new ModelAndView("report/Report");
	}
	
	@GetMapping("/personalizado")
	public ModelAndView customReportForm() {
		ModelAndView mv = new ModelAndView("report/CustomReport");
		mv.addObject(new CustomReport());
		return mv;
	}
	
	@PreAuthorize("#id == principal.user.id")
	@PostMapping("/{id}/personalizado")
	public ResponseEntity<byte[]> customReport(@Valid CustomReport customReport, BindingResult result, @PathVariable Long id, RedirectAttributes attr) throws Exception {
		if(result.hasErrors()) {
			throw new GeneratingReportException("Não foi possível gerar o relatório, por favor verifique todos os campos ou tente mais tarde.");
		}
		
		byte[] report = reportService.generateMonthlyReport(customReport, id);		
		return send(report);
	}
	
	@PreAuthorize("#id == principal.user.id")
	@GetMapping("/{id}/mes-atual")
	public ResponseEntity<byte[]> currentMonthReport(@PathVariable Long id) throws Exception {
		byte[] report = reportService.generateMonthReport(id);		
		return send(report);
	}

	private ResponseEntity<byte[]> send(byte[] report) {
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(report);
	}
	
}
