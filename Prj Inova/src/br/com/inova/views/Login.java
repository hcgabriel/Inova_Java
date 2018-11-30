package br.com.inova.views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.sql.*;
import br.com.inova.utils.Conexao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import br.com.inova.views.TelaPrincipal;
import javax.swing.ImageIcon;
import java.awt.Color;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	// Conexao com o Banco
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	//A Linha abaixo é usada para auxiliar na declaracao da senha
	@SuppressWarnings("deprecation")
	public void logar(){
		String sql = "select * from tbusuarios where login=? and senha=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtLogin.getText());
			pst.setString(2, txtSenha.getText());

			rs = pst.executeQuery();

			if (rs.next()) {
					btnOs.setVisible(true);
					btnVisitas.setVisible(true);
				
			}else{
				JOptionPane.showMessageDialog(null,"Usuario ou Senha Invalido");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}
	//Declaracao das variaveis
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	public static JButton btnOs;
	public static JButton btnVisitas;

	//Inicio da aplicacao
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

	//Criacao da tela
	public Login() {
		setResizable(false);
		setTitle("Login Inova");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 310);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(211, 65, 68));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsuario = new JLabel("Login");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setBounds(37, 39, 61, 16);
		contentPane.add(lblUsuario);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(new Color(255, 255, 255));
		lblSenha.setBounds(37, 93, 61, 16);
		contentPane.add(lblSenha);

		txtLogin = new JTextField();
		txtLogin.setBounds(37, 55, 255, 26);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(37, 111, 255, 26);
		contentPane.add(txtSenha);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Chamando o método logar
				logar();
			}
		});
		btnLogin.setBounds(213, 149, 79, 29);
		contentPane.add(btnLogin);
				
						JLabel label = new JLabel("");
						label.setIcon(new ImageIcon("/Users/gabriel/Desktop/Workspace Gabriel/Prj Inova/src/br/com/inova/icones/Logo.png"));
						label.setBounds(319, 55, 306, 146);
						contentPane.add(label);
		
				JPanel panel = new JPanel();
				panel.setBackground(new Color(255, 255, 255));
				panel.setBounds(319, 0, 281, 288);
				contentPane.add(panel);
				
				btnVisitas = new JButton("Visitas");
				btnVisitas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TelaPrincipalVisitas visita = new TelaPrincipalVisitas();
						visita.setVisible(true);
					}
				});
				btnVisitas.setVisible(false);
				btnVisitas.setBounds(46, 227, 117, 29);
				contentPane.add(btnVisitas);
				
				btnOs = new JButton("OS");
				btnOs.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						TelaPrincipal principal = new TelaPrincipal();
						principal.setVisible(true);
					}
				});
                btnOs.setVisible(false);
				btnOs.setBounds(175, 227, 117, 29);
				contentPane.add(btnOs);

		conexao = Conexao.conector();
		// A linha abaixo serve de apoio ao status da conexao
		// System.out.println(conexao);

	}
}
