package br.univel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Main {

	public Main(Cliente c1, Cliente c2, Cliente c3) throws SQLException {
		deletaTabela();
        criaTabela();
        salva(c1);
        salva(c2);
        salva(c3);

        listaTodos();
        busca(1);

        c1.setNomeCliente("Maria");
        c1.setEnd("Avenida Flor de Lis");
        c1.setTelefone("4532428907");
        c1.setEstadocivil(Estado_Civil.Casado);

        atualiza(c1);
        exclui(2);

        listaTodos();
    }

    public void criaTabela() throws SQLException {
        Sqlimpl ex = new Sqlimpl();
        Connection con = ex.abrirconexcao();
        PreparedStatement ps = con.prepareStatement(ex.getCreateTable(new Cliente()));
		
		int res = ps.executeUpdate();
        con.close();
    }

    public void deletaTabela() throws SQLException {
        Sqlimpl ex = new Sqlimpl();
        Connection con = ex.abrirconexcao();
        PreparedStatement ps = con.prepareStatement(ex.getDropTable(new Cliente()));
        int res = ps.executeUpdate();
    }

    public void salva(Cliente cl) {
        Daoimpl dao = new Daoimpl();
        dao.salvar(cl);
    }

    public void atualiza(Cliente cl) {
        Daoimpl dao = new Daoimpl();
        dao.atualizar(cl);
    }
    public void busca(Integer id) {
        Daoimpl dao = new Daoimpl();
        Cliente cl = dao.buscar(id);

        System.out.println("ID: \t\t\t" + cl.getId());
        System.out.println("NOME: \t\t\t" + cl.getNomeCliente());
        System.out.println("ENDERECO: \t\t" + cl.getEnd());
        System.out.println("TELEFONE: \t\t" + cl.getTelefone());
        System.out.println("ESTADO CIVIL: \t" + cl.getEstadocivil());
        System.out.println();
    }
    public void exclui(Integer id) {
        Daoimpl dao = new Daoimpl();
        dao.excluir(1);
    }

    

    public void listaTodos() {
        Daoimpl dao = new Daoimpl();
        List<Cliente> list = dao.listarTodos();

        System.out.println("LISTAR TODOS\n");
        for (Cliente l: list) {
            System.out.println("ID: \t\t" + l.getId());
            System.out.println("NOME: \t\t" + l.getNomeCliente());
            System.out.println("ENDERECO: \t" + l.getEnd());
            System.out.println("TELEFONE: \t" + l.getTelefone());
            System.out.println("ESTADO CIVIL: \t\n" + l.getEstadocivil());
        }
    }

    public static void main(String[] args) {
        Cliente c1 = new Cliente(1, "Jose", "Rua Tanto faz", "4532349812", Estado_Civil.Casado);
        Cliente c2 = new Cliente(2, "Vanessa", "Rua Boca de Leao", "453238977", Estado_Civil.Solteiro);
        Cliente c3 = new Cliente(3, "Joao", "Avenida Qualquer", "4591234567", Estado_Civil.Divorsiado);
        
        try {
			new Main( c1, c2, c3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}

	

