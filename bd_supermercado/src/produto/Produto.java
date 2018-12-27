package produto;

public class Produto {
	private int cod;
	private String nome;
	private float preco;
	private int qtd;
	
	public Produto(int cod, String nome, float preco, int qtd) {
        this.cod = cod;
        this.nome = nome;
        this.preco = preco;
        this.qtd = qtd;
    }
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
}
