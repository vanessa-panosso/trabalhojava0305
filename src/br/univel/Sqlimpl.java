package br.univel;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;

public class Sqlimpl extends SqlGen {
	
	public Sqlimpl () {
		String strCreateTable = getCreateTable(Cliente.class);
		System.out.println(strCreateTable);
		
		String strDropTable = getDropTable(Cliente.class);
		System.out.println(strDropTable);
		
		Cliente cliente = new Cliente(1, "Maria", "Rua qualquer", "452987-1234", Estado_Civil.Solteiro);

		
	}

	@Override
	protected String getCreateTable(Class<Cliente> cl) {
		try {

			StringBuilder sb = new StringBuilder();
			
			{
				String nomeTabela;
				if (cl.isAnnotationPresent(Tabela.class)) {

					Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
					nomeTabela = anotacaoTabela.value();

				} else {
					nomeTabela = cl.getSimpleName().toUpperCase();

				}
				sb.append("CREATE TABLE ").append(nomeTabela).append(" (");
			}

			Field[] atributos = cl.getDeclaredFields();

			{
				for (int i = 0; i < atributos.length; i++) {

					Field field = atributos[i];

					String nomeColuna;
					String tipoColuna = null;
					int tamanhoColuna;
					Coluna anotacaoColuna = null;
					if (field.isAnnotationPresent(Coluna.class)) {
						anotacaoColuna = field.getAnnotation(Coluna.class);

						if (anotacaoColuna.nome().isEmpty()) {
							nomeColuna = field.getName().toUpperCase();
						} else {
							nomeColuna = anotacaoColuna.nome();
							
						}

					} else {
						nomeColuna = field.getName().toUpperCase();
					}

					Class<?> tipoParametro = field.getType();

					if (tipoParametro.equals(String.class)) {
							tamanhoColuna = anotacaoColuna.tamanho();
							tipoColuna = "VARCHAR("+tamanhoColuna+")";
						
					}else if (tipoParametro.equals(int.class)) {
						tipoColuna = "INT";

					}else if (tipoParametro.equals(Estado_Civil.class)) {
						tipoColuna = "VARCHAR(20)";
					}
					
					else {
						tipoColuna = "DESCONHECIDO";
					}

					if (i > 0) {
						sb.append(",");
					}

					sb.append("\n\t").append(nomeColuna).append(' ').append(tipoColuna);
				}
			}

			{

				sb.append(",\n\tPRIMARY KEY( ");

				for (int i = 0, achou = 0; i < atributos.length; i++) {

					Field field = atributos[i];

					if (field.isAnnotationPresent(Coluna.class)) {

						Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

						if (anotacaoColuna.pk()) {

							if (achou > 0) {
								sb.append(", ");
							}

							if (anotacaoColuna.nome().isEmpty()) {
								sb.append(field.getName().toUpperCase());
							} else {
								sb.append(anotacaoColuna.nome());
							}

							achou++;
						}

					}
				}

				sb.append(" )");
			}

			sb.append("\n);");

			return sb.toString();

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	
	}

	@Override
	protected String getDropTable(Class<Cliente> cl) {
		try {

			StringBuilder sb = new StringBuilder();

			
				String nomeTabela;
				if (cl.isAnnotationPresent(Tabela.class)) {

					Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
					nomeTabela = anotacaoTabela.value();

				} else {
					nomeTabela = cl.getSimpleName().toUpperCase();

				}
				sb.append("DROP TABLE ").append(nomeTabela).append(";");
			
			return sb.toString();

	} catch (SecurityException e) {
		throw new RuntimeException(e);
	}
		
	}

	@Override
	protected PreparedStatement getSqlInsert(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlSelectAll(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlSelectById(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlUpdateById(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PreparedStatement getSqlDeleteById(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		new Sqlimpl();
	}
}
