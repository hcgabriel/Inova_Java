package br.com.inova.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.sql.*;
import br.com.inova.utils.Conexao;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;

public class TelaOs extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNOS;
	private JTextField txtData;
	private JTextField txtCliPesquisar;
	private JTextField txtCliID;
	private JTextField txtDescricao;
	private JTable tblClientes;
	private JTable tblTecnico;
	private static JComboBox<?> cboOsSit;
	private JTextField txtTecPesquisar;
	private JTextField txtTecID;
	private static JButton btnCadastrar;

	// Conexao com o Banco
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTextField txtObs;
	private JTextField txtValor;
	public static JComboBox cbPag;
	public static JButton btnDeletar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaOs frame = new TelaOs();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public TelaOs() {
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				pesquisar_cliente();
				pesquisar_tecnico();
				
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
		panel.setBounds(6, 28, 334, 86);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNOs = new JLabel("Nº OS");
		lblNOs.setBounds(6, 6, 37, 16);
		panel.add(lblNOs);

		JLabel lblData = new JLabel("Data");
		lblData.setBounds(142, 6, 29, 16);
		panel.add(lblData);

		txtNOS = new JTextField();
		txtNOS.setEditable(false);
		txtNOS.setBounds(6, 34, 90, 26);
		panel.add(txtNOS);
		txtNOS.setColumns(10);

		txtData = new JTextField();
		txtData.setFont(new Font("Lucida Grande", Font.BOLD, 9));
		txtData.setEditable(false);
		txtData.setBounds(142, 34, 173, 26);
		panel.add(txtData);
		txtData.setColumns(10);

		JLabel lblSituao = new JLabel("Situação");
		lblSituao.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblSituao.setForeground(SystemColor.windowText);
		lblSituao.setBounds(16, 269, 135, 16);
		contentPane.add(lblSituao);

		cboOsSit = new JComboBox();
		cboOsSit.setModel(new DefaultComboBoxModel(new String[] {"Resolvido", "Em andamento", "Não Resolvido"}));
		cboOsSit.setBounds(6, 297, 208, 27);
		contentPane.add(cboOsSit);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(886, 17, 527, 225);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//Chamando o método pesquisar clientes
				pesquisar_cliente();
			}
		});
		txtCliPesquisar.setBounds(16, 31, 244, 26);
		panel_1.add(txtCliPesquisar);
		txtCliPesquisar.setColumns(10);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/inova/icones/search.png")));
		label.setBounds(272, 20, 61, 50);
		panel_1.add(label);

		JLabel lblNewLabel = new JLabel("*ID");
		lblNewLabel.setBounds(363, 36, 61, 16);
		panel_1.add(lblNewLabel);

		txtCliID = new JTextField();
		txtCliID.setBounds(395, 31, 82, 26);
		panel_1.add(txtCliID);
		txtCliID.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 69, 493, 133);
		panel_1.add(scrollPane);

		tblClientes = new JTable();
		tblClientes.setEnabled(false);
		tblClientes.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"ID","Nome", "Fone"
				}
				));
		scrollPane.setViewportView(tblClientes);
		
		JLabel label_1 = new JLabel("Pesquisa por nome");
		label_1.setFont(new Font("Arial", Font.PLAIN, 11));
		label_1.setBounds(25, 17, 169, 16);
		panel_1.add(label_1);

		JLabel lblDescrio = new JLabel("*Descrição");
		lblDescrio.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblDescrio.setForeground(SystemColor.windowText);
		lblDescrio.setBounds(16, 359, 135, 16);
		contentPane.add(lblDescrio);

		txtDescricao = new JTextField();
		txtDescricao.setBounds(16, 387, 448, 27);
		contentPane.add(txtDescricao);
		txtDescricao.setColumns(10);

		btnCadastrar = new JButton("Adicionar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emitir_os();
			}
		});
		btnCadastrar.setToolTipText("Adicionar");
		btnCadastrar.setIcon(null);
		btnCadastrar.setBounds(367, 705, 150, 50);
		contentPane.add(btnCadastrar);

		btnDeletar = new JButton("Deletar");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir_os();
			}
		});
		btnDeletar.setToolTipText("Excluir");
		btnDeletar.setIcon(null);
		btnDeletar.setBounds(547, 705, 150, 50);
		contentPane.add(btnDeletar);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar_os();
			}
		});
		btnAlterar.setToolTipText("Alterar");
		btnAlterar.setIcon(null);
		btnAlterar.setBounds(727, 705, 150, 50);
		contentPane.add(btnAlterar);

		JButton btnPesquisar = new JButton("Consultar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar_os();
			}
		});
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setIcon(null);
		btnPesquisar.setBounds(907, 705, 150, 50);
		contentPane.add(btnPesquisar);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Técnico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(367, 17, 487, 225);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel pesquisar = new JLabel("");
		pesquisar.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/inova/icones/search.png")));
		pesquisar.setBounds(204, 21, 61, 50);
		panel_2.add(pesquisar);

		JLabel lblNewLabel_1 = new JLabel("*ID");
		lblNewLabel_1.setBounds(335, 36, 61, 16);
		panel_2.add(lblNewLabel_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(16, 69, 456, 140);
		panel_2.add(scrollPane_1);

		tblTecnico = new JTable();
		tblTecnico.setEnabled(false);
		tblTecnico.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"ID","Nome", "Fone"
				}
				));
		scrollPane_1.setViewportView(tblTecnico);

		txtTecPesquisar = new JTextField();
		txtTecPesquisar.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				pesquisar_tecnico();
			}
		});
		txtTecPesquisar.setBounds(16, 31, 176, 26);
		panel_2.add(txtTecPesquisar);
		txtTecPesquisar.setColumns(10);

		txtTecID = new JTextField();
		txtTecID.setBounds(364, 31, 85, 26);
		panel_2.add(txtTecID);
		txtTecID.setColumns(10);
		
		JLabel lblPesquisaPorNome = new JLabel("Pesquisa por nome");
		lblPesquisaPorNome.setFont(new Font("Arial", Font.PLAIN, 11));
		lblPesquisaPorNome.setBounds(23, 16, 169, 16);
		panel_2.add(lblPesquisaPorNome);

		JLabel lblcamposObrigatrios = new JLabel("*Campos Obrigatórios");
		lblcamposObrigatrios.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblcamposObrigatrios.setBounds(6, 226, 145, 16);
		contentPane.add(lblcamposObrigatrios);

		JLabel lblObservao = new JLabel("Observação");
		lblObservao.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblObservao.setForeground(SystemColor.windowText);
		lblObservao.setBounds(16, 515, 135, 16);
		contentPane.add(lblObservao);

		txtObs = new JTextField();
		txtObs.setBounds(16, 543, 448, 26);
		contentPane.add(txtObs);
		txtObs.setColumns(10);

		JLabel lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblValor.setForeground(SystemColor.windowText);
		lblValor.setBounds(16, 437, 135, 16);
		contentPane.add(lblValor);

		txtValor = new JTextField();
		txtValor.setText("R$");
		txtValor.setBounds(16, 465, 130, 26);
		contentPane.add(txtValor);
		txtValor.setColumns(10);
		
		JLabel lblPagamento = new JLabel("Pagamento");
		lblPagamento.setForeground(SystemColor.windowText);
		lblPagamento.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblPagamento.setBounds(16, 594, 86, 27);
		contentPane.add(lblPagamento);
		
		cbPag = new JComboBox();
		cbPag.setModel(new DefaultComboBoxModel(new String[] {"Recebido", "Não Recebido"}));
		cbPag.setBounds(16, 633, 198, 27);
		contentPane.add(cbPag);


		//Conexao com o banco
		conexao = Conexao.conector();

	}

	private void pesquisar_cliente() {
		String sql = "Select idclientes as ID, nomecliente as Nome, fone as Fone from tbclientes where nomecliente like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliPesquisar.getText()+"%");
			rs = pst.executeQuery();
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void pesquisar_tecnico() {
		String sql = "Select idtecnico as ID, nometecnico as Nome, fonetecnico as Fone from tbtecnico where nometecnico like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtTecPesquisar.getText()+"%");
			rs = pst.executeQuery();
			tblTecnico.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void emitir_os() {
		String sql = "insert into tbos (descricao, situacao, observacao, valor, pagamento, idclientes, idtecnico) values (?,?,?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtDescricao.getText());
			pst.setString(2, cboOsSit.getSelectedItem().toString());
			pst.setString(3, txtObs.getText());
			pst.setString(4, txtValor.getText());
			pst.setString(5, cbPag.getSelectedItem().toString());
			pst.setString(6, txtCliID.getText());
			pst.setString(7, txtTecID.getText());
			//Validacao dos campos obrigatorios

			if ((txtDescricao.getText().isEmpty() || (txtCliID.getText().isEmpty() || (txtTecID.getText().isEmpty())))) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				int adicionado = pst.executeUpdate();
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS emitida com sucesso");
					txtCliID.setText(null);
					txtDescricao.setText(null);
					txtTecID.setText(null);
					txtValor.setText(null);
					txtObs.setText(null);
				}
			}
		
			JasperPrint printos = JasperFillManager.fillReport("/Users/gabriel/Desktop/Reports/Os.jasper", null, conexao);
			JasperViewer.viewReport(printos, false);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void pesquisar_os() {
		String num_os = JOptionPane.showInputDialog("Numero da OS");
		String sql = "select * from tbos where os= " +num_os;

		try {
			pst= conexao.prepareStatement(sql);
			rs=pst.executeQuery();

			if (rs.next()) {
				txtNOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtDescricao.setText(rs.getString(3));
				cboOsSit.setSelectedItem(rs.getString(4));
				txtObs.setText(rs.getString(5));
				txtValor.setText(rs.getString(6));
				cboOsSit.setSelectedItem(rs.getString(7));
				btnCadastrar.setEnabled(false);
				txtCliPesquisar.setEnabled(false);
				txtTecPesquisar.setEnabled(false);
				tblTecnico.setVisible(false);
				tblClientes.setVisible(false);

			} else {
				JOptionPane.showMessageDialog(null, "OS não cadastrada");
				txtNOS.setText(null);
				txtData.setText(null);
				txtCliID.setText(null);
				txtDescricao.setText(null);
				txtTecID.setText(null);
				txtObs.setText(null);
				txtValor.setText(null);
				//Habilitar os objetos
				btnCadastrar.setEnabled(true);
				txtCliPesquisar.setEnabled(true);
				txtTecPesquisar.setEnabled(true);
				tblTecnico.setVisible(true);
				tblClientes.setVisible(true);
			}
			
			String pagamento=rs.getString(7);
			if (pagamento.equals("Não Recebido")) {
				btnDeletar.setEnabled(false);
				
			} else {
				btnDeletar.setEnabled(true);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void alterar_os() {
		String sql = "update tbos set descricao=?, situacao=?, observacao=?, valor=?, pagamento=? where os=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtDescricao.getText());
			pst.setString(2, cboOsSit.getSelectedItem().toString());
			pst.setString(3, txtObs.getText());
			pst.setString(4, txtValor.getText());
			pst.setString(5, cbPag.getSelectedItem().toString());
			pst.setString(6, txtNOS.getText());
			//Validacao dos campos obrigatorios

			int adicionado = pst.executeUpdate();
			if(adicionado > 0) {
				JOptionPane.showMessageDialog(null, "OS alterada com sucesso");
				txtNOS.setText(null);
				txtData.setText(null);
				txtCliID.setText(null);
				txtDescricao.setText(null);
				txtTecID.setText(null);
				txtObs.setText(null);
				txtValor.setText(null);
				//Habilitar os objetos
				btnCadastrar.setEnabled(true);
				txtCliPesquisar.setEnabled(true);
				txtTecPesquisar.setEnabled(true);
				tblTecnico.setVisible(true);
				tblClientes.setVisible(true);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void excluir_os() {
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta OS?", "Atenção", JOptionPane.YES_NO_OPTION);
		if(confirma==JOptionPane.YES_OPTION) {
			String sql = "delete from tbos where os=?";

			try {
				pst=conexao.prepareStatement(sql);
				pst.setString(1, txtNOS.getText());

				int apagado = pst.executeUpdate();
				if (apagado>0) {
					JOptionPane.showMessageDialog(null, "OS excluida com sucesso");
					txtNOS.setText(null);
					txtData.setText(null);
					txtCliID.setText(null);
					txtDescricao.setText(null);
					txtTecID.setText(null);
					txtObs.setText(null);
					txtValor.setText(null);
					//Habilitar os objetos
					btnCadastrar.setEnabled(true);
					txtCliPesquisar.setEnabled(true);
					txtTecPesquisar.setEnabled(true);
					tblTecnico.setVisible(true);
					tblClientes.setVisible(true);

				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
}