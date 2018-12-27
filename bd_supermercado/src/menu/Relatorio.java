package menu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import banco.Banco;
import login.Login;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Relatorio extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public DefaultTableModel dadosTabela = new DefaultTableModel();
	private JButton btnDetalhes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorio frame = new Relatorio(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param login 
	 */
	public Relatorio(Login login) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(485, 200, 460, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 330, 251);
		contentPane.add(scrollPane);
		
		table = new JTable(dadosTabela);
		scrollPane.setViewportView(table);
		
		btnDetalhes = new JButton("Detalhes");
		btnDetalhes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object valorCod = ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 0);
				int cod = Integer.valueOf((String) valorCod);
				
				String texto = Banco.detalharRelatorio(cod);
				JOptionPane.showMessageDialog(null, texto);
			}
		});
		btnDetalhes.setBounds(345, 5, 89, 23);
		contentPane.add(btnDetalhes);
		
		dadosTabela.addColumn("Codigo");
		dadosTabela.addColumn("Data");
		dadosTabela.addColumn("Valor");
		
		Banco.popularRelatorio(login, dadosTabela);
	}

}
