package br.univel;

@Tabela ("Cadastro_Cliente")
public class Cliente {
	@Coluna(pk=true)
	private int id;

	@Coluna(nome="CadNome", tamanho=100)
	private String nomeCliente;
	@Coluna(nome="CadEnd", tamanho=200)
	private String end;
	
	@Coluna(nome="CadTelefone", tamanho=11)
	private String telefone;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	
	
}
