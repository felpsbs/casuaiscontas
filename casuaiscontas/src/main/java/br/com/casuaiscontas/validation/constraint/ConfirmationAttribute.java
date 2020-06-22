package br.com.casuaiscontas.validation.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import br.com.casuaiscontas.validation.validator.ConfirmationAttributeValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME) 
@Constraint(validatedBy = { ConfirmationAttributeValidator.class })
public @interface ConfirmationAttribute {
	
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Atributos não conferem";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	String attribute();
	
	String confirmationAttribute();
}
