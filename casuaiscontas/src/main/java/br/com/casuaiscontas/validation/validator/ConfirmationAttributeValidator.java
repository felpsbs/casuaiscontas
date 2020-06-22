package br.com.casuaiscontas.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import br.com.casuaiscontas.validation.constraint.ConfirmationAttribute;

public class ConfirmationAttributeValidator implements ConstraintValidator<ConfirmationAttribute, Object> {

	private String attribute;
	private String confirmationAttribute;
	
	@Override
	public void initialize(ConfirmationAttribute constraintAnnotation) {
		this.attribute = constraintAnnotation.attribute();
		this.confirmationAttribute = constraintAnnotation.confirmationAttribute();
	}
	
	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		boolean valid = false;
		try {			
			Object attrValue = BeanUtils.getProperty(object, this.attribute);
			Object attrConfirmationValue = BeanUtils.getProperty(object, this.confirmationAttribute);
			
			valid = bothNull(attrValue, attrConfirmationValue) || bothEquals(attrValue, attrConfirmationValue);
		} catch (Exception e) {
			throw new RuntimeException("Erro recuperando valores dos atributos", e);
		}
		
		if(!valid) {
			context.disableDefaultConstraintViolation();
			String message = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(message);
			violationBuilder.addPropertyNode(this.confirmationAttribute).addConstraintViolation();
		}
		
		return valid;
	}

	private boolean bothEquals(Object attrValue, Object attrConfirmationValue) {
		return attrValue != null && attrValue.equals(attrConfirmationValue);
	}

	private boolean bothNull(Object attrValue, Object attrConfirmationValue) {
		 return attrValue == null && attrConfirmationValue == null;
	}

}
