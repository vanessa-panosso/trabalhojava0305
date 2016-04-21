package br.univel;

@Tabela ("Cadastro_Cliente")
public class Cliente {
	@Coluna(pk=true)
	private int id;

	@Coluna(nomeCliente="NOME")
	private String nome;
	
	@Coluna(end="End")
	private String endereco;
	
	@Coluna(telefone="telefone")
	private String telefone;
}
