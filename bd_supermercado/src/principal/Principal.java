package principal;

import javax.swing.JFrame;

import banco.Banco;
import login.LoginFrame;
import pessoa.CadastroCliente;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Principal extends JFrame{
	
	public Principal() {
		super("Trabalho Banco de Dados");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setSize(800, 600);
        setResizable(false);
        setBounds(300, 50, 800, 600);
        getContentPane().setLayout(null);

        Banco.getConexao();
        
        JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
        
        JLabel lbl1 = new JLabel("");
        lbl1.setBounds(276, 86, 404, 340);
        getContentPane().add(lbl1);
        lbl1.setIcon(new ImageIcon("image\\carrinho.png"));
        
        JButton btnLogin = new JButton("LOGIN");
        btnLogin.addActionListener(e -> {
        	LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
        menuBar.add(btnLogin);
        
        JButton btnCadastro = new JButton("CADASTRO");
        btnCadastro.addActionListener(e -> {
        	CadastroCliente cadastroCliente = new CadastroCliente();
            cadastroCliente.setVisible(true);
        	
        });
        menuBar.add(btnCadastro);
        
	}
	 public static void main(String[] args) {
	        new Principal().setVisible(true);
	}   
}
