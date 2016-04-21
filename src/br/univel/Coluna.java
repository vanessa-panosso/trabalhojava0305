package br.univel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Coluna {
	
	String nomeCliente() default "";
	
	String end() default"";
	
	String telefone() default"";
	
	boolean pk() default false;

}
