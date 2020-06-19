package br.com.casuaiscontas.config.format;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public abstract class TemporalFormatter<T extends Temporal> implements Formatter<T> {

	@Override
	public String print(T object, Locale locale) {
		DateTimeFormatter formatter = getDateTimeFormatter(locale);
		return formatter.format(object);
	}

	@Override
	public T parse(String text, Locale locale) throws ParseException {
		DateTimeFormatter formatter = getDateTimeFormatter(locale);
		return parse(text, formatter);
	}
	
	public abstract String pattern(Locale locale);
	
	public abstract T parse(String text, DateTimeFormatter formatter);
	
	private DateTimeFormatter getDateTimeFormatter(Locale locale) {
		return DateTimeFormatter.ofPattern(pattern(locale));
	}

}
