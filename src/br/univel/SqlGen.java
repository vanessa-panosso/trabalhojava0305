package br.univel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SqlGen {
	//DDL
	protected abstract String getCreateTable(Object obj) throws SQLException;
	
	protected abstract String getDropTable(Object obj);
	
	// DML
	protected abstract PreparedStatement getSqlInsert(Connection con, Object obj);
	
	protected abstract PreparedStatement getSqlSelectAll(Connection con, Object obj);
	
	protected abstract PreparedStatement getSqlSelectById(Connection con, Object obj, int id );
	
	protected abstract PreparedStatement getSqlUpdateById(Connection con, Object obj, int id);
	

	protected abstract PreparedStatement getSqlDeleteById(Connection con, Object obj, int id) ;

	
}
