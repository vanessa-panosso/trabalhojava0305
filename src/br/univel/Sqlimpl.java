package br.univel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sqlimpl extends SqlGen {
	
	public Sqlimpl () {
		
		try {

			String url = "jdbc:h2:./banco";
			String user = "sa";
			String pass = "sa";
			Connection con = DriverManager.getConnection(url, user, pass);
		
		} catch (SQLException e) {
			e.printStackTrace();

		}
		
		
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
	protected PreparedStatement getSqlInsert(Connection con, Object obj) {

		Class<? extends Object> cl = obj.getClass();

		StringBuilder sb = new StringBuilder();

		{
			String nomeTabela;
			if (cl.isAnnotationPresent(Tabela.class)) {

				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();

			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();

			}
			sb.append("INSERT INTO ").append(nomeTabela).append(" (");
		}

		Field[] atributos = cl.getDeclaredFields();

		{
			for (int i = 0; i < atributos.length; i++) {

				Field field = atributos[i];

				String nomeColuna;

				if (field.isAnnotationPresent(Coluna.class)) {
					Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

					if (anotacaoColuna.nome().isEmpty()) {
						nomeColuna = field.getName().toUpperCase();
					} else {
						nomeColuna = anotacaoColuna.nome();
					}

				} else {
					nomeColuna = field.getName().toUpperCase();
				}

				if (i > 0) {
					sb.append(", ");
				}

				sb.append(nomeColuna);
			}
		}

		sb.append(") VALUES (");

		for (int i = 0; i < atributos.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append('?');
		}
		sb.append(')');

		String strSql = sb.toString();

		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(strSql);

			for (int i = 0; i < atributos.length; i++) {
				Field field = atributos[i];

				field.setAccessible(true);

				if (field.getType().equals(int.class)) {
					ps.setInt(i + 1, field.getInt(obj));

				} else if (field.getType().equals(String.class)) {
					ps.setString(i + 1, String.valueOf(field.get(obj)));

				} else if (field.getType().equals(Estado_Civil.class)) {
					ps.setString(i + 1, String.valueOf(field.get(obj)));
				} else {
					throw new RuntimeException("Tipo não suportado, falta implementar.");

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return ps;
	}


	@Override
	protected PreparedStatement getSqlSelectAll(Connection con, Object obj) {
		Class<? extends Object> cl = obj.getClass();

		StringBuilder sb = new StringBuilder();
		
		{
			String nomeTabela;
			if (cl.isAnnotationPresent(Tabela.class)) {

				Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
				nomeTabela = anotacaoTabela.value();

			} else {
				nomeTabela = cl.getSimpleName().toUpperCase();

			}
			sb.append("SELECT * FROM ").append(nomeTabela).append(";");
		}

		String strSql = sb.toString();

		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(strSql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return ps;

	}

	@Override
	protected PreparedStatement getSqlSelectById(Connection con, Object obj, int id ){

			Class<? extends Object> cl = obj.getClass();

			StringBuilder sb = new StringBuilder();

			{
				String nomeTabela;
				if (cl.isAnnotationPresent(Tabela.class)) {

					Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
					nomeTabela = anotacaoTabela.value();

				} else {
					nomeTabela = cl.getSimpleName().toUpperCase();

				}
				
				sb.append("SELECT * FROM ").append(nomeTabela).append(" WHERE ");
			}
			Field[] atributos = cl.getDeclaredFields();

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
			id = 1;
			sb.append(" = ").append(id);
			String strSql = sb.toString();

			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(strSql);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			return ps;	
		}

	@Override
	protected PreparedStatement getSqlUpdateById(Connection con, Object obj, int id) {

        Class<?> c = obj.getClass();
        StringBuilder sb = new StringBuilder();
        String nometabela;

        if (c.isAnnotationPresent(Tabela.class)) {
            nometabela = c.getAnnotation(Tabela.class).value();
        } else {
            nometabela = c.getSimpleName().toUpperCase();
        }

        sb.append("UPDATE ").append(nometabela).append(" SET ");

        Field[] atributos = c.getDeclaredFields();

        for (int i = 0; i < atributos.length; i++) {
            Field field = atributos[i];
            String nomecoluna;

            if (field.isAnnotationPresent(Coluna.class)) {
                Coluna coluna = field.getAnnotation(Coluna.class);
                if (coluna.nome().isEmpty()) {
                    nomecoluna = field.getName().toUpperCase();
                } else {
                    nomecoluna = coluna.nome();
                }
            } else {
                nomecoluna = field.getName().toUpperCase();
            }

            if (i > 0) {
                sb.append(", ");
            }

            sb.append(nomecoluna).append(" = ?");
        }
        sb.append(" WHERE ID = ").append(id);
        String update = sb.toString();
        System.out.println(update);

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(update);

            for (int i = 0; i < atributos.length; i++) {
                Field field = atributos[i];
                Object type = field.getType();

                field.setAccessible(true);
                if (type.equals(int.class)) {
                    ps.setInt(i + 1, field.getInt(obj));
                } else if (type.equals(String.class)) {
                    ps.setString(i + 1, String.valueOf(field.get(obj)));
                } else if (field.getType().isEnum()) {
                    Object value = field.get(obj);
                    Method m = value.getClass().getMethod("ordinal");
                    ps.setInt(i + 1, (Integer) m.invoke(value, null));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ps;
    }

	
	@Override
    protected PreparedStatement getSqlDeleteById(Connection con, Object obj, int id) {
        PreparedStatement ps = null;
        try {
            Class<?> cl = obj.getClass();
            StringBuilder sb = new StringBuilder();
            String nometabela;

            if (cl.isAnnotationPresent(Tabela.class)) {
                nometabela = cl.getAnnotation(Tabela.class).value();
            } else {
                nometabela = cl.getSimpleName().toUpperCase();
            }

            sb.append("DELETE FROM ").append(nometabela).append(" WHERE ID = ").append(id).append(";");
            String exc = sb.toString();
            System.out.println(exc);

            ps = con.prepareStatement(exc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

	public static void main(String[] args) {
		new Sqlimpl();
	}

	
}
