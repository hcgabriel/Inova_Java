package br.com.inova.views;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import br.com.inova.utils.Conexao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//A linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;

public class TelaCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCliNome;
	private JTextField txtCliEndereco;
	private JTextField txtCliFone;
	private JTextField txtCliEmail;
	private JTextField txtCliPesquisar;
	private JTable tblClientes;
	private JTextField txtCliID;
	private static JButton btnAdicionar;
	// Conexao com o Banco
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaCliente() {
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				pesquisar_cliente();
			}
		});

		setTitle("Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1440, 900);
		setExtendedState( MAXIMIZED_BOTH );
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 72, 1384, 126);
		contentPane.add(scrollPane);

		tblClientes = new JTable();
		tblClientes.setEnabled(false);
		tblClientes.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"ID", "Nome", "Endereco", "Telefone", "Email"
				}
				));
		scrollPane.setViewportView(tblClientes);

		JLabel lblCliNome = new JLabel("*Nome");
		lblCliNome.setForeground(SystemColor.textText);
		lblCliNome.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblCliNome.setBounds(38, 337, 136, 16);
		contentPane.add(lblCliNome);

		JLabel lblCliEndereco = new JLabel("Endereço");
		lblCliEndereco.setForeground(SystemColor.textText);
		lblCliEndereco.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblCliEndereco.setBounds(38, 405, 136, 16);
		contentPane.add(lblCliEndereco);

		JLabel lblCliFone = new JLabel("*Fone");
		lblCliFone.setForeground(SystemColor.textText);
		lblCliFone.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblCliFone.setBounds(38, 483, 136, 16);
		contentPane.add(lblCliFone);

		JLabel lblCliEmail = new JLabel("Email");
		lblCliEmail.setForeground(SystemColor.textText);
		lblCliEmail.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblCliEmail.setBounds(38, 559, 136, 16);
		contentPane.add(lblCliEmail);

		JLabel lblcamposObrigatrios = new JLabel("*Campos Obrigatórios");
		lblcamposObrigatrios.setFont(new Font("Arial", Font.PLAIN, 11));
		lblcamposObrigatrios.setBounds(38, 233, 167, 16);
		contentPane.add(lblcamposObrigatrios);

		txtCliNome = new JTextField();
		txtCliNome.setBounds(38, 365, 685, 26);
		contentPane.add(txtCliNome);
		txtCliNome.setColumns(10);

		txtCliEndereco = new JTextField();
		txtCliEndereco.setBounds(38, 433, 685, 26);
		contentPane.add(txtCliEndereco);
		txtCliEndereco.setColumns(10);

		txtCliFone = new JTextField();
		txtCliFone.setBounds(38, 511, 685, 26);
		contentPane.add(txtCliFone);
		txtCliFone.setColumns(10);

		txtCliEmail = new JTextField();
		txtCliEmail.setBounds(38, 587, 684, 26);
		contentPane.add(txtCliEmail);
		txtCliEmail.setColumns(10);

		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(UIManager.getColor("Button.background"));
		btnAdicionar.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(null);
		btnAdicionar.setBounds(329, 678, 150, 50);
		contentPane.add(btnAdicionar);

		JButton btnRemover = new JButton("Deletar");
		btnRemover.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnRemover.setToolTipText("Deletar");
		btnRemover.setIcon(null);
		btnRemover.setBounds(561, 678, 150, 50);
		contentPane.add(btnRemover);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnAlterar.setToolTipText("Alterar");
		btnAlterar.setIcon(null);
		btnAlterar.setBounds(787, 678, 150, 50);
		contentPane.add(btnAlterar);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultar();
			}
		});
		btnConsultar.setIcon(null);
		btnConsultar.setBounds(1016, 678, 150, 50);
		contentPane.add(btnConsultar);

		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//O evento abaixo é do tipo 'Enquanto for  digitando'
				pesquisar_cliente();
			}
		});
		txtCliPesquisar.setBounds(38, 18, 424, 26);
		contentPane.add(txtCliPesquisar);
		txtCliPesquisar.setColumns(10);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/Users/gabriel/Desktop/Workspace Gabriel/Prj Inova/src/br/com/inova/icones/search.png"));
		lblNewLabel.setBounds(474, 18, 61, 41);
		contentPane.add(lblNewLabel);

		JLabel lblIdCliente = new JLabel("ID Cliente");
		lblIdCliente.setForeground(SystemColor.textText);
		lblIdCliente.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblIdCliente.setBounds(38, 273, 136, 16);
		contentPane.add(lblIdCliente);

		txtCliID = new JTextField();
		txtCliID.setBounds(38, 301, 89, 26);
		contentPane.add(txtCliID);
		txtCliID.setColumns(10);
		
		JLabel lblPesquisaPorNome = new JLabel("Pesquisa por nome");
		lblPesquisaPorNome.setBounds(515, 23, 136, 16);
		contentPane.add(lblPesquisaPorNome);

		//Conexao com o banco
		conexao = Conexao.conector();
	}

	private void adicionar() {
		String sql = "insert into tbclientes (nomecliente, endereco, fone, email) values (?,?,?,?)";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, txtCliNome.getText());
			pst.setString(2, txtCliEndereco.getText());
			pst.setString(3, txtCliFone.getText());
			pst.setString(4, txtCliEmail.getText());
			//Validacao dos campos obrigatorios
			if (((txtCliNome.getText().isEmpty()) || (txtCliFone.getText().isEmpty()))) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				//A estrutura abaixo é usada para confirmar a insercao dos dados no banco
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso");
					//As linhas abaixo limpam os campos
					txtCliNome.setText(null);
					txtCliEndereco.setText(null);
					txtCliFone.setText(null);
					txtCliEmail.setText(null);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}

	}

	private void pesquisar_cliente() {
		String sql = "select idclientes as ID, nomecliente as Nome, endereco as Endereço, fone as Telefone, email as Email from tbclientes where nomecliente like ?";
		try {
			pst = conexao.prepareStatement(sql);
			//Passando o conteudo da caixa de pesquisa para o interroga
			//ATENCAO ao "%""que é a continucao da String sql
			pst.setString(1,txtCliPesquisar.getText() + "%");
			rs = pst.executeQuery();
			//A linha abaixo usa a biblioteca rs2xml para preencher a tabela

			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));


		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}
	}

	private void alterar() {
		String sql = "update tbclientes set nomecliente=?, endereco=?, fone=?, email=? where idclientes=?";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, txtCliNome.getText());
			pst.setString(2, txtCliEndereco.getText());
			pst.setString(3, txtCliFone.getText());
			pst.setString(4, txtCliEmail.getText());
			pst.setString(5, txtCliID.getText());

			if (((txtCliNome.getText().isEmpty()) || (txtCliFone.getText().isEmpty()))) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				//A estrutura abaixo é usada para confirmar a insercao dos dados no banco
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Dados do cliente alterado com sucesso");
					//As linhas abaixo limpam os campos
					txtCliNome.setText(null);
					txtCliEndereco.setText(null);
					txtCliFone.setText(null);
					txtCliEmail.setText(null);
					btnAdicionar.setEnabled(true);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}

	}

	private void remover() {
		//A estrutura abaixo confirma a remocao do usuario
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?", "Atenção", JOptionPane.YES_NO_OPTION);
		if(confirma==JOptionPane.YES_OPTION) {
			String sql = "delete from tbclientes where idclientes=?";
			try {
				pst=conexao.prepareStatement(sql);
				pst.setString(1, txtCliID.getText());
				int apagado = pst.executeUpdate();
				if(apagado>0) {
					JOptionPane.showMessageDialog(null, "Cliente Removido com sucesso");
					txtCliNome.setText(null);
					txtCliID.setText(null);
					txtCliEndereco.setText(null);
					txtCliEmail.setText(null);
					txtCliFone.setText(null);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e);
			}
		}

	}

	private void consultar() {
		String sql = "select * from tbclientes where idclientes=?";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1,txtCliID.getText());
			rs=pst.executeQuery();
			if(rs.next()) {
				txtCliNome.setText(rs.getString(2));
				txtCliEndereco.setText(rs.getString(3));
				txtCliFone.setText(rs.getString(4));
				txtCliEmail.setText(rs.getString(5));
			} else {
				JOptionPane.showMessageDialog(null,"Usuário não cadastrado");
				//As linhas abaixo limpam os campos
				txtCliNome.setText(null);
				txtCliFone.setText(null);
				txtCliEmail.setText(null);
				txtCliEndereco.setText(null);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}
	}
}