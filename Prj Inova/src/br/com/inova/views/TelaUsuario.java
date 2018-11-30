package br.com.inova.views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.sql.*;
import br.com.inova.utils.Conexao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.SystemColor;


public class TelaUsuario extends JFrame {

	//Declaracao das variaveis
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuID;
	private JTextField txtUsuNome;
	private JTextField txtUsuLogin;
	private JTextField txtUsuSenha;
	
	// Conexao com o Banco
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;


	//Inicio da aplicacao
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaUsuario frame = new TelaUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Criacao da tela
	public TelaUsuario() {
		setTitle("Usuário");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1440, 900);
		setExtendedState( MAXIMIZED_BOTH );
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(SystemColor.window);

		JLabel lblID = new JLabel("ID");
		lblID.setForeground(SystemColor.windowText);
		lblID.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblID.setBounds(120, 80, 61, 16);
		contentPane.add(lblID);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setForeground(SystemColor.windowText);
		lblNome.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblNome.setBounds(120, 160, 61, 16);
		contentPane.add(lblNome);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setForeground(SystemColor.windowText);
		lblLogin.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblLogin.setBounds(120, 240, 61, 26);
		contentPane.add(lblLogin);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(SystemColor.windowText);
		lblSenha.setFont(new Font("DIN Alternate", Font.PLAIN, 15));
		lblSenha.setBounds(120, 320, 61, 16);
		contentPane.add(lblSenha);

		txtUsuID = new JTextField();
		txtUsuID.setBounds(120, 98, 150, 26);
		contentPane.add(txtUsuID);
		txtUsuID.setColumns(10);

		txtUsuNome = new JTextField();
		txtUsuNome.setBounds(120, 179, 578, 26);
		contentPane.add(txtUsuNome);
		txtUsuNome.setColumns(10);

		txtUsuLogin = new JTextField();
		txtUsuLogin.setBounds(120, 265, 242, 26);
		contentPane.add(txtUsuLogin);
		txtUsuLogin.setColumns(10);

		txtUsuSenha = new JTextField();
		txtUsuSenha.setBounds(120, 340, 337, 26);
		contentPane.add(txtUsuSenha);
		txtUsuSenha.setColumns(10);

		JButton btnUsuCreate = new JButton("Cadastrar");
		btnUsuCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnUsuCreate.setIcon(null);
		btnUsuCreate.setToolTipText("Adicionar");
		btnUsuCreate.setBounds(280, 640, 150, 50);
		contentPane.add(btnUsuCreate);

		JButton btnUsuDelete = new JButton("Deletar");
		btnUsuDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnUsuDelete.setToolTipText("Deletar");
		btnUsuDelete.setIcon(null);
		btnUsuDelete.setBounds(518, 640, 150, 50);
		contentPane.add(btnUsuDelete);

		JButton btnUsuUpdate = new JButton("Alterar");
		btnUsuUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnUsuUpdate.setToolTipText("Alterar");
		btnUsuUpdate.setIcon(null);
		btnUsuUpdate.setBounds(785, 640, 150, 50);
		contentPane.add(btnUsuUpdate);

		JButton btnUsuRead = new JButton("Consultar");
		btnUsuRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultar();
			}
		});
		btnUsuRead.setToolTipText("Consultar");
		btnUsuRead.setIcon(null);
		btnUsuRead.setBounds(1040, 640, 150, 50);
		contentPane.add(btnUsuRead);

		JLabel lbltodosOsCampos = new JLabel("*Todos os campos são obrigatórios");
		lbltodosOsCampos.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lbltodosOsCampos.setBounds(120, 416, 224, 16);
		contentPane.add(lbltodosOsCampos);
		//Conexao com o banco
		conexao = Conexao.conector();

	}

	private void consultar() {
		String sql = "select * from tbusuarios where iduser=?";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1,txtUsuID.getText());
			rs=pst.executeQuery();
			if(rs.next()) {
				txtUsuNome.setText(rs.getString(2));
				txtUsuLogin.setText(rs.getString(3));
				txtUsuSenha.setText(rs.getString(4));
			} else {
				JOptionPane.showMessageDialog(null,"Usuário não cadastrado");
				//As linhas abaixo limpam os campos
				txtUsuNome.setText(null);
				txtUsuLogin.setText(null);
				txtUsuSenha.setText(null);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}
	}

	private void adicionar() {
		String sql = "insert into tbusuarios (iduser, usuario, login, senha) values (?,?,?,?)";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, txtUsuID.getText());
			pst.setString(2, txtUsuNome.getText());
			pst.setString(3, txtUsuLogin.getText());
			pst.setString(4, txtUsuSenha.getText());
			//Validacao dos campos obrigatorios
			if ((((txtUsuID.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())))) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				//A estrutura abaixo é usada para confirmar a insercao dos dados no banco
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");
					//As linhas abaixo limpam os campos
					txtUsuID.setText(null);
					txtUsuNome.setText(null);
					txtUsuLogin.setText(null);
					txtUsuSenha.setText(null);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}

	}

	private void alterar() {
		String sql = "update tbusuarios set usuario=?, login=?, senha=? where iduser=?";
		try {
			pst=conexao.prepareStatement(sql);
			pst.setString(1, txtUsuNome.getText());
			pst.setString(2, txtUsuLogin.getText());
			pst.setString(3, txtUsuSenha.getText());
			pst.setString(5, txtUsuID.getText());

			if ((((txtUsuID.getText().isEmpty()) || (txtUsuNome.getText().isEmpty()) || (txtUsuLogin.getText().isEmpty()) || (txtUsuSenha.getText().isEmpty())))) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				//A estrutura abaixo é usada para confirmar a alteracao dos dados no banco
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Dados do Usuário alterados com sucesso");
					//As linhas abaixo limpam os campos
					txtUsuID.setText(null);
					txtUsuNome.setText(null);
					txtUsuLogin.setText(null);
					txtUsuSenha.setText(null);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}

	}

	private void remover() {
		//A estrutura abaixo confirma a remocao do usuario
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuario", "Atenção", JOptionPane.YES_NO_OPTION);
		if(confirma==JOptionPane.YES_OPTION) {
			String sql = "delete from tbusuarios where iduser=?";
			try {
				pst=conexao.prepareStatement(sql);
				pst.setString(1, txtUsuID.getText());
				int apagado = pst.executeUpdate();
				if(apagado>0) {
					JOptionPane.showMessageDialog(null, "Usuário Removido com sucesso");
					txtUsuID.setText(null);
					txtUsuNome.setText(null);
					txtUsuLogin.setText(null);
					txtUsuSenha.setText(null);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e);
			}
		}

	}

}
