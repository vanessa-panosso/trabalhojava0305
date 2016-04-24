package br.univel;

import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class SqlGen {
	//DDL
	protected abstract String getCreateTable(Class<Cliente> cl);
	
	protected abstract String getDropTable(Class<Cliente> cl);
	
	// DML
	protected abstract PreparedStatement getSqlInsert(Connection con, Object obj);
	
	protected abstract PreparedStatement getSqlSelectAll(Connection con, Object obj);
	
	protected abstract PreparedStatement getSqlSelectById(Connection con, Object obj);
	
	protected abstract PreparedStatement getSqlUpdateById(Object obj);
	
	protected abstract PreparedStatement getSqlDeleteById(Object obj);

	
}
