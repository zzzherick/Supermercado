package login;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import pessoa.CadastroCliente;
import principal.Principal;
import banco.Banco;
import menu.Menu;

//import pessoa.Fisica;

import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
	private JTextField textLogin;
	private JPasswordField passwordLogin;
    private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	

	/**
	 * Create the frame.
	 * @param c 
	 */
	public LoginFrame() {
        //this.setClosable(true);
		setBounds(590, 300, 225, 145);
		getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogin.setBounds(10, 11, 46, 17);
		getContentPane().add(lblLogin);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha.setBounds(10, 43, 46, 14);
		getContentPane().add(lblSenha);
		
		textLogin = new JTextField();
		textLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textLogin.setBounds(66, 11, 128, 20);
		getContentPane().add(textLogin);
		textLogin.setColumns(10);
		
		passwordLogin = new JPasswordField();
		passwordLogin.setBounds(66, 42, 128, 20);
		getContentPane().add(passwordLogin);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CadastroCliente().setVisible(true);
			}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCadastrar.setBounds(10, 73, 89, 23);
		getContentPane().add(btnCadastrar);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(e -> {
			Login login = new Login(textLogin.getText(), (new String(passwordLogin.getPassword())));
		
		   	if(Banco.validarLogin(login)) {
		   		//login feito
		   		Menu menu = new Menu(login);
		   		menu.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null,"Login inválido",
		                   "ERRO", JOptionPane.INFORMATION_MESSAGE);
			}
		   	//Juridica pessoaJuridica = new Juridica(login, senha, nomePosto, email, telefone, cnpj, gasolinaComum,
		   	//		gasolinaAditivada, cidade, bairro, rua, numero, complemento);
			
	    });
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogin.setBounds(105, 73, 89, 23);
		getContentPane().add(btnLogin);

	}

	 public static void main(String[] args) {
	       // new LoginFrame().setVisible(true);
	}   
}
