package br.univel;

@Tabela ("Cadastro_Cliente")
public class Cliente {
	@Coluna(pk=true)
	private int Cadid;

	@Coluna(nome="CadNome", tamanho=100)
	private String nomeCliente;
	
	@Coluna(nome="CadEnd", tamanho=80)
	private String end;
	
	@Coluna(nome="CadTelefone", tamanho=11)
	private String telefone;
	
	@Coluna(nome="CadEstadoCivil")
	private Estado_Civil estadocivil;
	
	public Estado_Civil getEstadocivil() {
		return estadocivil;
	}

	public void setEstadocivil(Estado_Civil estadocivil) {
		this.estadocivil = estadocivil;
	}

	public int getId() {
		return Cadid;
	}

	public void setId(int id) {
		this.Cadid = id;
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

	public Cliente() {
		this(0, null, null, null, null);
	}

	public Cliente(int id, String nome, String endereco, String telefone, Estado_Civil estCivil) {
		super();
		this.Cadid = id;
		this.nomeCliente = nome;
		this.end = endereco;
		this.telefone = telefone;
		this.estadocivil = estCivil;
	}
	
}
