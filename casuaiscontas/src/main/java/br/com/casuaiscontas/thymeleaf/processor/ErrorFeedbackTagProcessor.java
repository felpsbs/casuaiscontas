package br.com.casuaiscontas.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class ErrorFeedbackTagProcessor extends AbstractElementTagProcessor {

	private static final String TAG_NAME = "errorfeedback";
	private static final int PRECEDENCE = 1000;
	
	public ErrorFeedbackTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
	}
	
	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		
		IModelFactory modelFactory = context.getModelFactory();
		
		IAttribute field = tag.getAttribute("field");

		IModel model = modelFactory.createModel();
		model.add(modelFactory.createStandaloneElementTag(
				"th:block", 
				"th:replace", 
				String.format("fragments/errorMessages :: feedback (%s)", field.getValue())
		));	
		
		structureHandler.replaceWith(model, true);
	}

}
