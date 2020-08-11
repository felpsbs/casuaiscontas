package br.com.casuaiscontas.model;

public enum LegalEntity {

	NATURAL_PERSON {
		
		@Override
		public String format(String cpf) {
			return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
		}
		
	};
	
	public static String removeFormatting(String cpf) {
		return cpf.replaceAll("\\.|-|/", "");
	}
	
	public abstract String format(String cpf);
}
