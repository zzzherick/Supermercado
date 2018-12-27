package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import login.Login;
import pessoa.Pessoa;
import produto.Produto;
import produto.ProdutoManipulado;

public class Banco {
	
	private static String host = "localhost";
    private static String banco = "supermercado";
    private static String usuario = "root";
    private static String senha = "";
    private static String url = "jdbc:mysql://" + host + "/" + banco + "?useSSL=false&useTimezone=true&serverTimezone=UTC";
    private static Connection conexao = null;

    private static ArrayList<ProdutoManipulado> list = new ArrayList<>();
    //private static ArrayList<ProdutoManipulado> listNova = new ArrayList<>();
    
    public static Connection getConexao(){
        try{
            System.out.println(url);
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch(SQLException ex){
            System.out.println("Erro: " + ex.getMessage());
        }
        if(conexao != null) {
            System.out.println("Conectado com sucessso!");
        } else {
            System.out.println("Não foi possivel realizar conexão");
        }
        return conexao;
    }
    
    public static boolean fecharConexao(){
        if(conexao != null){
            try {
                conexao.close();
                System.out.println("Conexão fechada");
                return true;
            } catch(SQLException e){
                System.out.println("Erro:" + e.getMessage());
            }
        }
        return false;
    }
    
    public static void inserirPessoa(Pessoa pessoa) throws SQLException{
        
        try{
        	//cadastro de login
        	String sql = "INSERT INTO login(usuario, senha) VALUES(?, ?)";
        	
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, pessoa.getUsuario());
            stmt.setString(2, pessoa.getSenha());
            
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
	        rs.next();
	        int idGeradoLogin = rs.getInt(1);
	        pessoa.setIdLogin(idGeradoLogin);
	        
	        stmt.close();
	        
	        //cadastro contato
	        sql = "INSERT INTO contato(email, telefone) VALUES(?, ?)";
	        
	        stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        stmt.setString(1, pessoa.getEmail());
	        stmt.setString(2, pessoa.getTelefone());

	        stmt.execute();
	        
            rs = stmt.getGeneratedKeys();
	        rs.next();
	        int idGeradoContato = rs.getInt(1);
	        
	        stmt.close();
	        
	        //cadastro de pessoa
            sql = "INSERT INTO pessoa(nome, cpf, login_idlogin, contato_idcontato) VALUES(?, ?, ?, ?)";
            
        	stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        stmt.setString(1, pessoa.getNome());	
	        stmt.setString(2, pessoa.getCpf());
	        stmt.setInt(3, pessoa.getIdLogin());
	        stmt.setInt(4, idGeradoContato);
	        stmt.execute();
	
	        rs = stmt.getGeneratedKeys();
	        rs.next();
	        idGeradoLogin = rs.getInt(1);
	        pessoa.setIdPessoa(idGeradoLogin);
	        stmt.close();
        } catch (SQLException ex){
            System.out.println("Erro: "+ ex.getMessage());
        }
        
        JOptionPane.showMessageDialog(null,"Usuário Cadastrado",
                    "INFO", JOptionPane.INFORMATION_MESSAGE);
    }
 
    public static boolean validarLogin(Login login){
        String query = "SELECT * FROM login";
        
        try{
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next()){
                String usuario = rs.getString("usuario");
                String senha = rs.getString("senha");
                
                if(usuario.equals(login.getUsuario()) && senha.equals(login.getSenha())){
                    return true;
                }
            }
        } catch(Exception e){
            System.out.println("Erro: "+ e.getMessage());
        }
        return false;
    }
    
    public static void inserirProduto(Login login, Produto produto, int id) throws SQLException{
        try{
        	//pegando id login
        	String query = "SELECT idLogin From login where usuario = '"+ login.getUsuario()+"'";

            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery(query);
            
	        rs.next();
            int idLogin = rs.getInt("idLogin");
        	
        	//pegando id pessoa
        	String query2 = "Select idpessoa FROM pessoa pes \r\n" + 
        			"INNER JOIN login log On pes.login_idlogin = log.idlogin \r\n" +
        			"WHERE pes.login_idlogin = "+ idLogin;
        	
        	Statement st2 = conexao.createStatement();
            ResultSet rs2 = st2.executeQuery(query2);
            rs2.next();
            int idPessoa = rs2.getInt("idpessoa");
            
        	//cadastro de produto
        	String sql = "INSERT INTO produto(quantidade, nome, preco, pessoa_idpessoa, relatorio_idrelatorio) VALUES(?, ?, ?, ?, ?)";
        	
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, produto.getQtd());
            stmt.setString(2, produto.getNome());
            stmt.setFloat(3, produto.getPreco());
            stmt.setInt(4, idPessoa);
            stmt.setInt(5, id);
            
            stmt.execute();
	        stmt.close();
        } catch (SQLException ex){
            System.out.println("Erro: "+ ex.getMessage());
        }
    }

	public static int relatorio(String data, Float valor) throws SQLException {		
		//cadastro de relatorio
    	String sql = "INSERT INTO relatorio(date, valor) VALUES(?, ?)";
    	
        PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, data);
        stmt.setFloat(2, valor);

        stmt.execute();
        		
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        int idGerado = rs.getInt(1);
        
        stmt.close();
        
        return idGerado;
	}

	public static void popularRelatorio(Login login, DefaultTableModel dadosTabela) {

        try{
			////pegando id login
	    	String query = "SELECT idLogin From login where usuario = '"+ login.getUsuario()+"'";
	
	        Statement st = conexao.createStatement();
	        ResultSet rs = st.executeQuery(query);
	        
	        rs.next();
	        int idLogin = rs.getInt("idLogin");
	        
			query = "Select idrelatorio, date, valor FROM relatorio rel\r\n" + 
					"INNER JOIN produto pro ON pro.relatorio_idrelatorio = rel.idrelatorio\r\n" + 
					"INNER JOIN pessoa pes ON pes.idpessoa = pro.pessoa_idpessoa\r\n" + 
					"WHERE pes.login_idlogin = "+ idLogin;
	        
            st = conexao.createStatement();
            rs = st.executeQuery(query);
            
            ArrayList<Integer> id = new ArrayList<>();
            int aux = 0;
            while(rs.next()){
                //id.add(rs.getInt("rel.idrelatorio"));
                String date = rs.getString("rel.date");
                float valor = rs.getFloat("rel.valor");
                
                for(int i = 0; i < id.size(); i++) {
                    if(rs.getInt("rel.idrelatorio") == id.get(i).intValue()) {
                    	aux++;
                    }
                }
                if(aux == 0) {
                	id.add(rs.getInt("rel.idrelatorio"));
                    dadosTabela.addRow(new String[]{""+ rs.getInt("rel.idrelatorio"), date, ""+ valor});
                }
                aux = 0;
            }
        } catch(Exception e){
            System.out.println("Erro: "+ e.getMessage());
        }
		
	}

	public static void updateRelatorio(int id, float valor) throws SQLException {
		String sql = "UPDATE relatorio SET valor = "+ valor +" WHERE idrelatorio = "+ id;
        
		PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        stmt.execute();
        stmt.close(); 
	}

	@SuppressWarnings("unused")
	public static String bigData(Login login) throws SQLException{
		list = new ArrayList<>();
		
    	String query = "SELECT idLogin From login where usuario = '"+ login.getUsuario()+"'";

        Statement st = conexao.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        rs.next();
        int idLogin = rs.getInt("idLogin");
        
		query = "SELECT pro.nome, quantidade FROM produto pro\r\n" + 
				"INNER JOIN pessoa pes ON pes.idpessoa = pro.pessoa_idpessoa\r\n" + 
				"WHERE pes.login_idlogin = "+ idLogin;
        
        st = conexao.createStatement();
        rs = st.executeQuery(query);
       
        while(rs.next()){
            String nome = rs.getString("pro.nome");
            int quantidade = rs.getInt("pro.quantidade");
            
            ProdutoManipulado produtoManipulado = new ProdutoManipulado(nome, quantidade);
            list.add(produtoManipulado);
        }
        
        manipulandoLista();
        
        int maior = 0;
        int posMaior = 0;
        for(int i = 0; i < list.size(); i++) {
        	if(maior < list.get(i).getQuantidade()) {
        		maior = list.get(i).getQuantidade();
        		posMaior = i;
        	}
        }
        return list.get(posMaior).getNome();
	}

	//Verificando se há alimentos repetidos
	private static void manipulandoLista() {
		int tam = (list.size() - 1);
		
		while(tam != 1) {
			for(int i = 0; i < list.size(); i++) {
				if(i == tam) {
					break;
				}
				if(list.get(i).getNome().equals(list.get(tam).getNome())) {
	        		int novaQtd = list.get(i).getQuantidade() + list.get(tam).getQuantidade();
	        		list.get(i).setQuantidade(novaQtd);
	        		
	        		list.remove(tam);
	        		break;
	        	}
			}
			tam--;
		}
	}

	public static String detalharRelatorio(int cod) {
		String query = "SELECT pro.nome, preco, quantidade FROM produto pro\r\n" + 
					   "WHERE pro.relatorio_idrelatorio = "+ cod;

        String texto = "";
		try {
			Statement st = conexao.createStatement();
	        ResultSet rs = st.executeQuery(query);
	        
	        while(rs.next()){
                //id.add(rs.getInt("rel.idrelatorio"));
                String nome = rs.getString("pro.nome");
                float valor = rs.getFloat("pro.preco");
                int qtd = rs.getInt("pro.quantidade");
                
                texto += ""+ nome +" R$"+ valor +"\t Quantidade ("+ qtd +")";
                texto += "\n";
            }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texto;
	}
}