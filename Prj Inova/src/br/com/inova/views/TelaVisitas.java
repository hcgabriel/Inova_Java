package br.com.inova.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import java.sql.*;
import br.com.inova.utils.Conexao;
import net.proteanit.sql.DbUtils;

import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaVisitas extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNVis;
	private JTextField txtData;
	private JTextField txtFone;
	private static JButton btnCadastrar;

	// Conexao com o Banco
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTextField txtDescricao;
	private JTextField txtImovel;
	private JTextField txtCli;
	private JTable tblVisita;
	private JTextField txtConv;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaVisitas frame = new TelaVisitas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public TelaVisitas() {
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				pesquisar();
			}
		});

		setTitle("OS");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1440, 900);
		setExtendedState( MAXIMIZED_BOTH );
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(6, 28, 381, 86);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNOs = new JLabel("Nº Visita");
		lblNOs.setBounds(6, 6, 90, 16);
		panel.add(lblNOs);

		JLabel lblData = new JLabel("Data");
		lblData.setBounds(142, 6, 29, 16);
		panel.add(lblData);

		txtNVis = new JTextField();
		txtNVis.setEditable(false);
		txtNVis.setBounds(6, 34, 90, 26);
		panel.add(txtNVis);
		txtNVis.setColumns(10);

		txtData = new JTextField();
		txtData.setFont(new Font("Lucida Grande", Font.BOLD, 9));
		txtData.setEditable(false);
		txtData.setBounds(142, 34, 173, 26);
		panel.add(txtData);
		txtData.setColumns(10);

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblCliente.setForeground(SystemColor.windowText);
		lblCliente.setBounds(16, 269, 135, 16);
		contentPane.add(lblCliente);

		JLabel lblFone = new JLabel("Fone");
		lblFone.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblFone.setForeground(SystemColor.windowText);
		lblFone.setBounds(16, 350, 135, 16);
		contentPane.add(lblFone);

		txtFone = new JTextField();
		txtFone.setBounds(16, 378, 238, 27);
		contentPane.add(txtFone);
		txtFone.setColumns(10);

		btnCadastrar = new JButton("Adicionar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emitir_visita();
			}
		});
		btnCadastrar.setToolTipText("Adicionar");
		btnCadastrar.setIcon(null);
		btnCadastrar.setBounds(368, 701, 150, 50);
		contentPane.add(btnCadastrar);

		JButton btnDeletar = new JButton("Deletar");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir_visita();
			}
		});
		btnDeletar.setToolTipText("Excluir");
		btnDeletar.setIcon(null);
		btnDeletar.setBounds(548, 701, 150, 50);
		contentPane.add(btnDeletar);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar_visita();
			}
		});
		btnAlterar.setToolTipText("Alterar");
		btnAlterar.setIcon(null);
		btnAlterar.setBounds(728, 701, 150, 50);
		contentPane.add(btnAlterar);

		JButton btnPesquisar = new JButton("Consultar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar_visita();
			}
		});
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setIcon(null);
		btnPesquisar.setBounds(908, 701, 150, 50);
		contentPane.add(btnPesquisar);

		JLabel lblcamposObrigatrios = new JLabel("*Campos Obrigatórios");
		lblcamposObrigatrios.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblcamposObrigatrios.setBounds(6, 226, 145, 16);
		contentPane.add(lblcamposObrigatrios);

		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblDescricao.setForeground(SystemColor.windowText);
		lblDescricao.setBounds(16, 611, 135, 16);
		contentPane.add(lblDescricao);

		txtDescricao = new JTextField();
		txtDescricao.setBounds(16, 639, 670, 50);
		contentPane.add(txtDescricao);
		txtDescricao.setColumns(10);

		JLabel lblImovel = new JLabel("Imóvel");
		lblImovel.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblImovel.setForeground(SystemColor.windowText);
		lblImovel.setBounds(16, 428, 135, 16);
		contentPane.add(lblImovel);

		txtImovel = new JTextField();
		txtImovel.setBounds(16, 456, 448, 26);
		contentPane.add(txtImovel);
		txtImovel.setColumns(10);
		
		txtCli = new JTextField();
		txtCli.setBounds(16, 297, 381, 26);
		contentPane.add(txtCli);
		txtCli.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(419, 28, 1001, 214);
		contentPane.add(scrollPane);

		tblVisita = new JTable();
		tblVisita.setEnabled(false);
		tblVisita.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"ID", "Cliente", "Fone", "Data", "Imóvel", "Descrição"
				}
				));
		scrollPane.setViewportView(tblVisita);
		
		JLabel lblDefinio = new JLabel("Definição");
		lblDefinio.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblDefinio.setBounds(16, 505, 78, 16);
		contentPane.add(lblDefinio);
		
		txtConv = new JTextField();
		txtConv.setBounds(16, 544, 670, 55);
		contentPane.add(txtConv);
		txtConv.setColumns(10);


		//Conexao com o banco
		conexao = Conexao.conector();

	}


	private void emitir_visita() {
		String sql = "insert into tbvisitas (cliente, fone, imovel, conversao, descricao) values (?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCli.getText());
			pst.setString(2, txtFone.getText());
			pst.setString(3, txtImovel.getText());
			pst.setString(4, txtConv.getText());
			pst.setString(5, txtDescricao.getText());
			//Validacao dos campos obrigatorios

			if ((txtCli.getText().isEmpty() || (txtFone.getText().isEmpty() || (txtImovel.getText().isEmpty())))) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				int adicionado = pst.executeUpdate();
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Visita emitida com sucesso");
					txtCli.setText(null);
					txtFone.setText(null);
					txtImovel.setText(null);
					txtDescricao.setText(null);
					txtConv.setText(null);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void pesquisar_visita() {
		String num_vis = JOptionPane.showInputDialog("Numero da Visita");
		String sql = "select * from tbvisitas where idvisita= " +num_vis;

		try {
			pst= conexao.prepareStatement(sql);
			rs=pst.executeQuery();

			if (rs.next()) {
				txtNVis.setText(rs.getString(1));
				txtCli.setText(rs.getString(2));
				txtFone.setText(rs.getString(3));
				txtData.setText(rs.getString(4));
				txtImovel.setText(rs.getString(5));
				txtConv.setText(rs.getString(6));
				txtDescricao.setText(rs.getString(7));
				btnCadastrar.setEnabled(false);

			} else {
				JOptionPane.showMessageDialog(null, "Visita não cadastrada");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void alterar_visita() {
		String sql = "update tbvisitas set cliente=?, fone=?, imovel=?, conversao=?, descricao=? where idvisita=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCli.getText());
			pst.setString(2, txtFone.getText());
			pst.setString(3, txtImovel.getText());
			pst.setString(4, txtConv.getText());
			pst.setString(5, txtDescricao.getText());
			pst.setString(6, txtNVis.getText());
			//Validacao dos campos obrigatorios

			int adicionado = pst.executeUpdate();
			if(adicionado > 0) {
				JOptionPane.showMessageDialog(null, "Visita alterada com sucesso");
				txtNVis.setText(null);
				txtData.setText(null);
				txtCli.setText(null);
				txtFone.setText(null);
				txtDescricao.setText(null);
				txtImovel.setText(null);
				txtConv.setText(null);
				//Habilitar os objetos
				btnCadastrar.setEnabled(true);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void excluir_visita() {
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta visita?", "Atenção", JOptionPane.YES_NO_OPTION);
		if(confirma==JOptionPane.YES_OPTION) {
			String sql = "delete from tbvisitas where idvisita=?";

			try {
				pst=conexao.prepareStatement(sql);
				pst.setString(1, txtNVis.getText());

				int apagado = pst.executeUpdate();
				if (apagado>0) {
					JOptionPane.showMessageDialog(null, "Visita excluida com sucesso");
					txtNVis.setText(null);
					txtData.setText(null);
					txtCli.setText(null);
					txtFone.setText(null);
					txtDescricao.setText(null);
					txtImovel.setText(null);
					txtConv.setText(null);
					//Habilitar os objetos
					btnCadastrar.setEnabled(true);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	
	private void pesquisar() {
		String sql = "select idvisita as ID, cliente as Cliente, fone as Fone, data_visita as Data, imovel as Imovel, conversao as Definição, descricao as Descrição from tbvisitas";
		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			//A linha abaixo usa a biblioteca rs2xml para preencher a tabela

			tblVisita.setModel(DbUtils.resultSetToTableModel(rs));


		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}
	}
}