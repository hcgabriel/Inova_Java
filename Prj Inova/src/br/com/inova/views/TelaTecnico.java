package br.com.inova.views;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import br.com.inova.utils.Conexao;
import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.SystemColor;

public class TelaTecnico extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtFone;
	private JTextField txtNome;
	// Conexao com o Banco
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTable tblTec;
	private JTextField txtTecPesquisar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaTecnico frame = new TelaTecnico();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaTecnico() {
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				pesquisar_tecnico();
			}
		});

		setTitle("Técnico");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1440, 900);
		setExtendedState( MAXIMIZED_BOTH );
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(SystemColor.window);

		JLabel lblId = new JLabel("*ID");
		lblId.setForeground(SystemColor.windowText);
		lblId.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblId.setBounds(60, 305, 61, 16);
		contentPane.add(lblId);

		txtID = new JTextField();
		txtID.setBounds(60, 323, 87, 26);
		contentPane.add(txtID);
		txtID.setColumns(10);

		JLabel lblFone = new JLabel("*Fone");
		lblFone.setForeground(SystemColor.windowText);
		lblFone.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblFone.setBounds(60, 424, 61, 16);
		contentPane.add(lblFone);

		txtFone = new JTextField();
		txtFone.setBounds(60, 442, 301, 26);
		contentPane.add(txtFone);
		txtFone.setColumns(10);

		JLabel lblNome = new JLabel("*Nome");
		lblNome.setForeground(SystemColor.windowText);
		lblNome.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblNome.setBounds(60, 361, 61, 16);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(60, 381, 675, 26);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnNewButton.setIcon(null);
		btnNewButton.setBounds(484, 636, 150, 50);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Deletar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnNewButton_1.setIcon(null);
		btnNewButton_1.setBounds(684, 636, 150, 50);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Alterar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnNewButton_2.setIcon(null);
		btnNewButton_2.setBounds(883, 636, 150, 50);
		contentPane.add(btnNewButton_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(60, 57, 1325, 223);
		contentPane.add(scrollPane);

		tblTec = new JTable();
		tblTec.setEnabled(false);
		tblTec.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"ID", "Nome", "Telefone", 
				}
				));
		scrollPane.setViewportView(tblTec);

		txtTecPesquisar = new JTextField();
		txtTecPesquisar.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				pesquisar_tecnico();
			}
		});
		txtTecPesquisar.setBounds(60, 17, 422, 26);
		contentPane.add(txtTecPesquisar);
		txtTecPesquisar.setColumns(10);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(TelaTecnico.class.getResource("/br/com/inova/icones/search.png")));
		label.setBounds(484, 11, 76, 50);
		contentPane.add(label);

		//Conexao com o banco
		conexao = Conexao.conector();
	}

	private void adicionar() {
		String sql = "insert into tbtecnico (idtecnico, nometecnico, fonetecnico) values (?,?,?)";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, txtID.getText());
			pst.setString(2, txtNome.getText());
			pst.setString(3, txtFone.getText());
			//Validacao dos campos obrigatorios
			if (((txtID.getText().isEmpty()) || (txtNome.getText().isEmpty()) || (txtFone.getText().isEmpty()))) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				//A estrutura abaixo é usada para confirmar a insercao dos dados no banco
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");
					//As linhas abaixo limpam os campos
					txtID.setText(null);
					txtNome.setText(null);
					txtFone.setText(null);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}

	}

	private void alterar() {
		String sql = "update tbtecnico set nometecnico=?, fonetecnico=? where idtecnico=?";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtFone.getText());
			pst.setString(3, txtID.getText());
			if (((txtID.getText().isEmpty()) || (txtNome.getText().isEmpty()) || (txtFone.getText().isEmpty()))) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				//A estrutura abaixo é usada para confirmar a alteracao dos dados no banco
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Dados do Usuário alterados com sucesso");
					//As linhas abaixo limpam os campos
					txtID.setText(null);
					txtNome.setText(null);
					txtFone.setText(null);
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}

	}

	private void remover() {
		//A estrutura abaixo confirma a remocao do usuario
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este técnico?", "Atenção", JOptionPane.YES_NO_OPTION);
		if(confirma==JOptionPane.YES_OPTION) {
			String sql = "delete from tbtecnico where idtecnico=?";
			try {
				pst=conexao.prepareStatement(sql);
				pst.setString(1, txtID.getText());
				int apagado = pst.executeUpdate();
				if(apagado>0) {
					JOptionPane.showMessageDialog(null, "Técnico Removido com sucesso");
					txtID.setText(null);
					txtNome.setText(null);
					txtFone.setText(null);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e);
			}
		}

	}

	private void pesquisar_tecnico() {
		String sql = "Select idtecnico as ID, nometecnico as Nome, fonetecnico as Fone from tbtecnico where nometecnico like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtTecPesquisar.getText()+"%");
			rs = pst.executeQuery();
			tblTec.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
