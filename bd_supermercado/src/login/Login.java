package login;

public class Login {
	private int idLogin;
	private String usuario;
	private String senha;
	
	public Login(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

	public int getIdLogin() {
		return idLogin;
	}
	public void setIdLogin(int id) {
		this.idLogin = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
