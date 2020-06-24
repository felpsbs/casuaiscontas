package br.com.casuaiscontas.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.casuaiscontas.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.casuaiscontas.thymeleaf.processor.FieldErrorElementTagProcessor;
import br.com.casuaiscontas.thymeleaf.processor.MessageElementTagProcessor;

@Component
public class CasuaisContasDialect extends AbstractProcessorDialect {

	public CasuaisContasDialect() {
		super("CasuaisContas", "casuaiscontas", StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processors = new HashSet<>();
		processors.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processors.add(new FieldErrorElementTagProcessor(dialectPrefix));
		processors.add(new MessageElementTagProcessor(dialectPrefix));
		
		return processors;
	}

}
