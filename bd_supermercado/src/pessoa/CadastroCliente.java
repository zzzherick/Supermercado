package pessoa;

import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import banco.Banco;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class CadastroCliente extends JFrame {
	private JTextField textNome;
	private JTextField textLogin;
	private JTextField textEmail;
	private JFormattedTextField textTelefone;
	private JPasswordField passwordSenha;
	private JPasswordField passwordSenhaConfirma;
	private JFormattedTextField textCpf;

    private MaskFormatter maskFormatter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroCliente frame = new CadastroCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadastroCliente() {
        //this.setClosable(true);
		setBounds(570, 240, 290, 260);
		getContentPane().setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(77, 11, 46, 14);
		getContentPane().add(lblNome);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogin.setBounds(77, 36, 46, 14);
		getContentPane().add(lblLogin);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(77, 61, 46, 14);
		getContentPane().add(lblEmail);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelefone.setBounds(61, 86, 62, 14);
		getContentPane().add(lblTelefone);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha.setBounds(77, 136, 46, 14);
		getContentPane().add(lblSenha);
		
		JLabel lblConfirmaSenha = new JLabel("Confirma Senha:");
		lblConfirmaSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmaSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConfirmaSenha.setBounds(10, 161, 113, 14);
		getContentPane().add(lblConfirmaSenha);
		
		textNome = new JTextField();
		textNome.setBounds(133, 10, 125, 20);
		getContentPane().add(textNome);
		textNome.setColumns(10);
		
		textLogin = new JTextField();
		textLogin.setBounds(133, 35, 125, 20);
		getContentPane().add(textLogin);
		textLogin.setColumns(10);
		
		textEmail = new JTextField();
		textEmail.setBounds(133, 60, 125, 20);
		getContentPane().add(textEmail);
		textEmail.setColumns(10);
		
		try {
            maskFormatter = new MaskFormatter("(##) #####-####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException pex) {
        }
		textTelefone = new JFormattedTextField(maskFormatter);
		textTelefone.setBounds(133, 85, 125, 20);
		getContentPane().add(textTelefone);
		textTelefone.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(23, 191, 100, 23);
		getContentPane().add(btnCancelar);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = textNome.getText();
		        String login = textLogin.getText();
		        String email = textEmail.getText();
		        String telefone = textTelefone.getText();
		        String cpf = textCpf.getText();
		        String senha = new String(passwordSenha.getPassword());
		        String confirmaSenha = new String(passwordSenhaConfirma.getPassword());
		        
		        if(senha.equals(confirmaSenha)) {
		        	if(!nome.equals("")) {

			        	Pessoa cliente = new Pessoa(login, senha, nome, cpf, email, telefone);
			        	
				        textNome.setText("");
				        textLogin.setText("");
				        textEmail.setText("");
				        textTelefone.setText("");
				        textCpf.setText("");
				        passwordSenha.setText("");
				        passwordSenhaConfirma.setText("");
				        
				        try {
							Banco.inserirPessoa(cliente);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	}
		        	else {
						JOptionPane.showMessageDialog(null, "Certifique que todos os campos estão preenchidos!!!");	
		        	}
		        } else {
					JOptionPane.showMessageDialog(null, "Senha diferente!!!");
		        }
		        
		        
		        
			}
		});
		
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCadastrar.setBounds(133, 191, 100, 23);
		getContentPane().add(btnCadastrar);
		
		passwordSenha = new JPasswordField();
		passwordSenha.setBounds(133, 160, 125, 20);
		getContentPane().add(passwordSenha);
		
		passwordSenhaConfirma = new JPasswordField();
		passwordSenhaConfirma.setBounds(133, 135, 125, 20);
		getContentPane().add(passwordSenhaConfirma);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCpf.setBounds(77, 111, 46, 14);
		getContentPane().add(lblCpf);
		
		try {
            maskFormatter = new MaskFormatter("###.###.###-##");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException pex) {
        }
		textCpf = new JFormattedTextField(maskFormatter);
		textCpf.setBounds(133, 110, 125, 20);
		getContentPane().add(textCpf);
		textCpf.setColumns(10);

	}
}
