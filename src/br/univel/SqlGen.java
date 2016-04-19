package br.univel;

import java.sql.PreparedStatement;

public abstract class SqlGen {
	//DDL
	protected abstract String getCreateTable(Object obj);
	
	protected abstract String getDropTable(Object obj);
	
	// DML
	protected abstract PreparedStatement getSqlInsert(Object obj);
	
	protected abstract PreparedStatement getSqlSelectAll(Object obj);
	
	protected abstract PreparedStatement getSqlSelectById(Object obj);
	
	protected abstract PreparedStatement getSqlUpdateById(Object obj);
	
	protected abstract PreparedStatement getSqlDeleteById(Object obj);
}
