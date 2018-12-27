package menu;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import banco.Banco;
import login.Login;
import produto.Produto;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class Compra extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public DefaultTableModel dadosTabela = new DefaultTableModel();
	private ArrayList<Produto> list = new ArrayList<Produto>();
	private JButton btnComprar;
	private JButton btnRemover;
	private JButton btnEditarQuantidade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Compra frame = new Compra();
					//frame.setVisible(true);
					
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
	public Compra(Login login) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(485, 200, 460, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 306, 263);
		contentPane.add(scrollPane);
		
		table = new JTable(dadosTabela);
		table.setBounds(0, 0, 1, 1);
		scrollPane.setViewportView(table);
		
		btnComprar = new JButton("Comprar");
		btnComprar.addActionListener(new ActionListener() {
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent e) {
				//insere no banco as compras

				int valor = 0;
				int id = 0;
				String data = getDateTime();
				try {
					id = Banco.relatorio(data, (float) valor);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				for(int i = 0; i < list.size(); i++) {
					int cod = list.get(i).getCod();
					String nome = list.get(i).getNome();
					Float preco = list.get(i).getPreco();
					int qtd = list.get(i).getQtd();
					
					valor += preco;
					
					Produto produto = new Produto(cod, nome, preco, qtd);
					try {
						Banco.inserirProduto(login, produto, id);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "Compra realizada com sucesso!");
				try {
					Banco.updateRelatorio(id, (float) valor);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dadosTabela.setNumRows(0);
				list.clear();
			}
		});
		btnComprar.setBounds(316, 11, 118, 23);
		contentPane.add(btnComprar);
		
		btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object valorCod = ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 0);
				int cod = Integer.valueOf((String) valorCod);
				
				int deletar = 0;
				for(int i = 0; i < list.size(); i++) {
					if(cod == list.get(i).getCod()) {
						deletar = i;
						i = list.size();
					}
				}
				list.remove(deletar);
				popularTabela();
				JOptionPane.showMessageDialog(null, "Produto removido do seu carrinho!");
			}
		});
		btnRemover.setBounds(316, 45, 118, 23);
		contentPane.add(btnRemover);
		
		btnEditarQuantidade = new JButton("Edit Quantidade");
		btnEditarQuantidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object valorQtd = ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 3);
				int qtd = Integer.valueOf((String) valorQtd);
				
				Object valorCod = ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 0);
				int cod = Integer.valueOf((String) valorCod);
				
				for(int i = 0; i < list.size(); i++) {
					if(cod == list.get(i).getCod()) {
						int qtdAntiga = list.get(i).getQtd();
						float precoAntigo = list.get(i).getPreco();
						float precoUnidade = precoAntigo / qtdAntiga;
						
						list.get(i).setPreco((precoUnidade * qtd));
						list.get(i).setQtd(qtd);
						i = list.size();
					}
				}
				popularTabela();
				JOptionPane.showMessageDialog(null, "Quantidade do produto alterado!");
			}
		});
		btnEditarQuantidade.setBounds(316, 79, 118, 23);
		contentPane.add(btnEditarQuantidade);
		
		dadosTabela.addColumn("Codigo");
		dadosTabela.addColumn("Produto");
		dadosTabela.addColumn("Preco");
		dadosTabela.addColumn("Quantidade");
	}
	private String getDateTime() { 
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss"); 
		Date date = new Date(); 
		return dateFormat.format(date); 
	}
	
	public void adicionarCompra(Produto produto) {
		for(int i = 0; i < list.size(); i++) {
			if(produto.getCod() == list.get(i).getCod()) {
				list.get(i).setQtd(((produto.getQtd()) + list.get(i).getQtd()));
				return;
			}
		}
		list.add(produto);
	}

	public void popularTabela() {
		dadosTabela.setNumRows(0);
		for(int i = 0; i < list.size(); i++) {
			
			dadosTabela.addRow(new String[]{""+ list.get(i).getCod(), ""+ list.get(i).getNome(), ""+ list.get(i).getPreco(), ""+ list.get(i).getQtd()});
		}
	}

}
