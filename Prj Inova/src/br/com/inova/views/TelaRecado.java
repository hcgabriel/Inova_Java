package br.com.inova.views;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.inova.utils.Conexao;
import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.SystemColor;

public class TelaRecado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRecado;
	// Conexao com o Banco
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTextField txtN;
	private JTable tblRecados;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaRecado() {
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				pesquisar_recado();
			}
		});
		setTitle("Recados");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1440, 900);
		setExtendedState( MAXIMIZED_BOTH );
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(SystemColor.window);

		JLabel lblRecado = new JLabel("Digite o novo recado");
		lblRecado.setForeground(SystemColor.windowText);
		lblRecado.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblRecado.setBounds(30, 378, 188, 24);
		contentPane.add(lblRecado);

		txtRecado = new JTextField();
		txtRecado.setBounds(30, 403, 748, 117);
		contentPane.add(txtRecado);
		txtRecado.setColumns(10);

		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnNewButton.setIcon(null);
		btnNewButton.setBounds(439, 618, 150, 50);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Deletar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnNewButton_1.setIcon(null);
		btnNewButton_1.setBounds(684, 618, 150, 50);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Alterar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnNewButton_2.setIcon(null);
		btnNewButton_2.setBounds(939, 618, 150, 50);
		contentPane.add(btnNewButton_2);

		JLabel lblNDoRecado = new JLabel("Nº do Recado");
		lblNDoRecado.setForeground(SystemColor.windowText);
		lblNDoRecado.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblNDoRecado.setBounds(30, 325, 151, 16);
		contentPane.add(lblNDoRecado);

		txtN = new JTextField();
		txtN.setBounds(28, 343, 68, 26);
		contentPane.add(txtN);
		txtN.setColumns(10);

		JScrollPane scrollPane2= new JScrollPane();
		scrollPane2.setBounds(28, 20, 1380, 259);
		contentPane.add(scrollPane2);

		tblRecados = new JTable();
		tblRecados.setEnabled(false);
		tblRecados.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Numero Recado","Recados"
				}
				));
		scrollPane2.setViewportView(tblRecados);

		conexao = Conexao.conector();

	}

	private void adicionar() {
		String sql = "insert into tbrecados (recado, recados) values (?,?)";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, txtN.getText());
			pst.setString(2, txtRecado.getText());
			//Validacao dos campos obrigatorios
			//A estrutura abaixo é usada para confirmar a insercao dos dados no banco
			int adicionado = pst.executeUpdate();
			if (adicionado > 0) {
				JOptionPane.showMessageDialog(null, "Recado adicionado com sucesso");
				//As linhas abaixo limpam os campos
				txtRecado.setText(null);
				txtN.setText(null);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}

	}

	private void alterar() {
		String sql = "update tbrecados set recados=? where recado=?";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, txtRecado.getText());
			pst.setString(2, txtN.getText());
			//A estrutura abaixo é usada para confirmar a alteracao dos dados no banco
			int adicionado = pst.executeUpdate();
			if (adicionado > 0) {
				JOptionPane.showMessageDialog(null, "Recado alterado com sucesso");
				//As linhas abaixo limpam os campos
				txtRecado.setText(null);
				txtN.setText(null);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}

	}

	private void remover() {
		//A estrutura abaixo confirma a remocao do usuario
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este recado?", "Atenção", JOptionPane.YES_NO_OPTION);
		if(confirma==JOptionPane.YES_OPTION) {
			String sql = "delete from tbrecados where recado=?";
			try {
				pst=conexao.prepareStatement(sql);
				pst.setString(1, txtN.getText());
				int apagado = pst.executeUpdate();
				if(apagado>0) {
					JOptionPane.showMessageDialog(null, "Recado removido com sucesso");
					txtN.setText(null);
					txtRecado.setText(null);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e);
			}
		}

	}

	private void pesquisar_recado() {
		String sql = "Select recado as N, recados as Recados from tbrecados";

		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			tblRecados.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
