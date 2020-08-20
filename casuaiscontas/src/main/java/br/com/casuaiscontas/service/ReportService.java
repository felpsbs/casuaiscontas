package br.com.casuaiscontas.service;

import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.casuaiscontas.dto.CustomReport;
import br.com.casuaiscontas.service.exception.GeneratingReportException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class ReportService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);

	@Autowired
	private DataSource dataSource;

	public byte[] generateMonthlyReport(CustomReport customReport, Long id) {
		Date dateFrom = Date.from(LocalDateTime.of(customReport.getDateFrom(), LocalTime.of(0, 0, 0)).atZone(ZoneId.systemDefault()).toInstant());
		Date dateTo = Date.from(LocalDateTime.of(customReport.getDateTo(), LocalTime.of(23, 59, 59)).atZone(ZoneId.systemDefault()).toInstant());
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("date_from", dateFrom);
		parametros.put("date_to", dateTo);
		parametros.put("id_user", id);
		parametros.put("logo_path", "static/img/logo.png");

		InputStream inputStream = this.getClass().getResourceAsStream("/reports/relatorio_contas_entre_datas.jasper");

		return exportReport(parametros, inputStream);		
	}
	
	public byte[] generateMonthReport(Long id) throws Exception {		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");		
		parametros.put("id_user", id);
		parametros.put("logo_path", "static/img/logo.png");
		
		InputStream inputStream = this.getClass().getResourceAsStream("/reports/relatorio_contas_do_mes.jasper");
		
		return exportReport(parametros, inputStream);		
	}

	private byte[] exportReport(Map<String, Object> parametros, InputStream inputStream) {
		try (Connection connection = this.dataSource.getConnection()) {
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, connection);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			LOGGER.debug("Falha ao tentar gerar relatório", e);
			throw new GeneratingReportException("Não foi possível gerar o relatório, por favor verifique todos os campos ou tente mais tarde.");
		}
	}
}
