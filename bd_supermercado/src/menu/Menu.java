package menu;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import banco.Banco;
import login.Login;
import produto.Produto;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Menu extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public DefaultTableModel dadosTabela = new DefaultTableModel();
	private JTextField textInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu(null);
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
	public Menu(Login login) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(480, 200, 470, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Compra compra = new Compra(login);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 296, 263);
		contentPane.add(scrollPane);
		
		table = new JTable(dadosTabela);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Object valor = ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 1);
				textInfo.setText(""+ valor);
			}
		});
		table.setBounds(10, 245, 185, -235);
		scrollPane.setViewportView(table);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(404, 11, 40, 20);
		contentPane.add(spinner);
		
		textInfo = new JTextField();
		textInfo.setEditable(false);
		textInfo.setBounds(306, 11, 86, 20);
		contentPane.add(textInfo);
		textInfo.setColumns(10);
		
		JButton btnAdicionar = new JButton("Adicionar Compra");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object valorCod = ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 0);
				int cod = Integer.valueOf((String) valorCod);
				
				Object valorNome = ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 1);
				String nome = (String) valorNome;
				
				Object valorPreco = ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 2);
				Float preco = Float.valueOf((String) valorPreco);
				
				int qtd = 0;
				qtd = Integer.parseInt(spinner.getValue().toString());
				
				Produto produto = new Produto(cod, nome, (preco * qtd), qtd);
				compra.adicionarCompra(produto);
				
				JOptionPane.showMessageDialog(null, "Produto adicionado ao carrinho de compras!");
			}
		});
		btnAdicionar.setBounds(306, 41, 138, 23);
		contentPane.add(btnAdicionar);
		
		JButton btnCompra = new JButton("Compras");
		btnCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compra.popularTabela();
				compra.setVisible(true);
			}
		});
		btnCompra.setBounds(306, 75, 138, 23);
		contentPane.add(btnCompra);
		
		JButton btnRelatorio = new JButton("Relat\u00F3rio");
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorio relatorio = new Relatorio(login);
				relatorio.setVisible(true);
			}
		});
		btnRelatorio.setBounds(306, 109, 138, 23);
		contentPane.add(btnRelatorio);
		
		JButton btnNotific = new JButton("Notifica\u00E7\u00F5es");
		btnNotific.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				String produto = "";
				try {
					produto = Banco.bigData(login);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String texto = "<html><font color= #FF0000 > Seu produto preferido está na PROMOÇÃO!!! </font></html>";
				String att = "\n<html><font color= #008000 >"+ produto.toUpperCase() +"</font></html>";
				String str = texto + "" + att;
        		JOptionPane.showMessageDialog(null, str);
			}
		});
		btnNotific.setBounds(306, 143, 138, 23);
		contentPane.add(btnNotific);
		
		JLabel label = new JLabel("");
		label.setBounds(306, 177, 138, 73);
		contentPane.add(label);
		//label.setIcon(new ImageIcon("image\\produtos.png"));
		
		dadosTabela.addColumn("Codigo");
		dadosTabela.addColumn("Produto");
		dadosTabela.addColumn("Valor");

		dadosTabela.addRow(new String[]{"1", "Alface", "1"});
		dadosTabela.addRow(new String[]{"2", "Arroz", "4"});
		dadosTabela.addRow(new String[]{"3", "Sabonete", "2"});
		dadosTabela.addRow(new String[]{"4", "Cereal", "3"});
		dadosTabela.addRow(new String[]{"5", "Cerveja", "3"});
		dadosTabela.addRow(new String[]{"6", "Coca-Cola", "5"});
		dadosTabela.addRow(new String[]{"7", "Iogurte 1L", "4"});
		dadosTabela.addRow(new String[]{"8", "Maça", "2"});
		dadosTabela.addRow(new String[]{"9", "Miojo", "1"});
		dadosTabela.addRow(new String[]{"10", "Pão", "1"});
		dadosTabela.addRow(new String[]{"11", "Pasta de Dente", "3"});
		
	}
}
