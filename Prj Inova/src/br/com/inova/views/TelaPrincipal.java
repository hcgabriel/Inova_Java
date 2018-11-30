package br.com.inova.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javax.swing.JDesktopPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import java.awt.Color;

import javax.swing.JTextField;

import br.com.inova.utils.Conexao;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;


public class TelaPrincipal extends JFrame {

	//Declaracao das variaveis
	private static final long serialVersionUID = 1L;
	public static JPanel contentPane;
	public static JMenu menRel;
	public static JMenuItem menCadUsu;
	public static JMenuItem menCadTec;
	public static JLabel lblData;
	public static JDesktopPane desk;

	// Conexao com o Banco
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	//Inicio da Aplicacao
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JTable tblOS;
	private JTextField txtOSPesquisar;
	private JTable tblRecados;

	//Criacao da Tela
	public TelaPrincipal() {
		//Quando a tela inicia a data aparece automaticamente com a linha abaixo
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
				lblData.setText(formatador.format(data));
				pesquisar_os();
				pesquisar_recado();
			}
		});
		setTitle("Inova Tarefas ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setBounds(100, 100, 1440, 900);
		setExtendedState( MAXIMIZED_BOTH );

		//Criacao do Menu
		JMenuBar Menu = new JMenuBar();
		Menu.setFont(new Font("DIN Alternate", Font.PLAIN, 18));
		Menu.setBounds(26, 21, 397, 21);
		contentPane.add(Menu);

		JMenu menCad= new JMenu();
		menCad.setFont(new Font("DIN Alternate", Font.PLAIN, 18));
		Menu.add(menCad);
		menCad.setText("Cadastro");

		JMenuItem menCadCli = new JMenuItem("Clientes");
		menCadCli.setFont(new Font("DIN Condensed", Font.PLAIN, 20));
		menCadCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente cliente = new TelaCliente();
				cliente.setVisible(true);
			}
		});
		menCad.add(menCadCli);

		JMenuItem menCadOs = new JMenuItem("OS");
		menCadOs.setFont(new Font("DIN Condensed", Font.PLAIN, 20));
		menCadOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaOs os = new TelaOs();
				os.setVisible(true);
			}
		});
		menCad.add(menCadOs);

		menCadTec = new JMenuItem("Tecnicos");
		menCadTec.setFont(new Font("DIN Condensed", Font.PLAIN, 20));
		menCadTec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaTecnico tecnico = new TelaTecnico();
				tecnico.setVisible(true);
			}
		});
		menCad.add(menCadTec);

		menCadUsu = new JMenuItem("Usuários");
		menCadUsu.setFont(new Font("DIN Condensed", Font.PLAIN, 20));
		menCadUsu.addActionListener(new ActionListener() {
			//Chama a tela de usuario
			public void actionPerformed(ActionEvent e) {
				TelaUsuario usuario = new TelaUsuario();
				usuario.setVisible(true);
			}
		});
		menCad.add(menCadUsu);

		menRel = new JMenu();
		menRel.setFont(new Font("DIN Alternate", Font.PLAIN, 18));
		Menu.add(menRel);
		menRel.setText("Relatório");

		JMenuItem menRelSer = new JMenuItem("Serviços");
		menRelSer.setFont(new Font("DIN Condensed", Font.PLAIN, 20));
		menRelSer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a emissao deste relatório?", "Atencao", JOptionPane.YES_NO_OPTION);
				if (confirma == JOptionPane.YES_OPTION) {
					try {
						JasperPrint print = JasperFillManager.fillReport("/Users/gabriel/Desktop/Reports/servicos.jasper", null, conexao);
						JasperViewer.viewReport(print, false);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}
				}
			}
		});
		menRel.add(menRelSer);

		JMenu menRec= new JMenu();
		menRec.setFont(new Font("DIN Alternate", Font.PLAIN, 18));
		Menu.add(menRec);
		menRec.setText("Recados");

		JMenuItem menRecNovo = new JMenuItem("Recados");
		menRecNovo.setFont(new Font("DIN Condensed", Font.PLAIN, 20));
		menRecNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaRecado recado = new TelaRecado();
				recado.setVisible(true);
			}
		});
		menRec.add(menRecNovo);

		JMenu menAju= new JMenu();
		menAju.setFont(new Font("DIN Alternate", Font.PLAIN, 18));
		Menu.add(menAju);
		menAju.setText("Ajuda");

		JMenuItem menAjuManu = new JMenuItem("Manual");
		menAjuManu.setFont(new Font("DIN Condensed", Font.PLAIN, 20));
		menAju.add(menAjuManu);

		JMenuItem menAjuSob = new JMenuItem("Sobre");
		menAjuSob.setFont(new Font("DIN Condensed", Font.PLAIN, 20));
		menAjuSob.addActionListener(new ActionListener() {
			//Chama a tela Sobre
			public void actionPerformed(ActionEvent e) {
				TelaSobre sobre = new TelaSobre();
				sobre.setVisible(true);
			}
		});
		menAju.add(menAjuSob);

		JMenu menOpc= new JMenu();
		menOpc.setFont(new Font("DIN Alternate", Font.PLAIN, 18));
		Menu.add(menOpc);
		menOpc.setText("Opções");

		JMenuItem menOpcMenu = new JMenuItem("Visitas");
		menOpcMenu.setFont(new Font("DIN Condensed", Font.PLAIN, 20));
		menOpcMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaPrincipalVisitas visita = new TelaPrincipalVisitas();
				visita.setVisible(true);
			}
		});
		menOpc.add(menOpcMenu);
		
		JMenuItem menOpcSai = new JMenuItem("Sair");
		menOpcSai.setFont(new Font("DIN Condensed", Font.PLAIN, 20));
		menOpcSai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Exibir caixa de dialogo
				int sair = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja sair?","Atenção",JOptionPane.YES_NO_OPTION);
				if (sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		menOpc.add(menOpcSai);
		
		
		desk = new JDesktopPane();
		desk.setBounds(12, 467, 540, -425);
		contentPane.add(desk);
		desk.setLayout(null);
		//Data
		lblData = new JLabel("Data");
		lblData.setForeground(SystemColor.textText);
		lblData.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblData.setBounds(1257, 42, 86, 15);
		contentPane.add(lblData);

		JLabel lblBemVindo = new JLabel("Bem Vindo");
		lblBemVindo.setForeground(SystemColor.textText);
		lblBemVindo.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblBemVindo.setBounds(1234, 20, 124, 16);
		contentPane.add(lblBemVindo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 152, 591, 580);
		contentPane.add(scrollPane);

		tblOS = new JTable();
		tblOS.setEnabled(false);
		tblOS.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"OS", "Data", "Descrição", "Situação"
				}
				));
		scrollPane.setViewportView(tblOS);

		JScrollPane scrollPane2= new JScrollPane();
		scrollPane2.setBounds(657, 152, 756, 420);
		contentPane.add(scrollPane2);

		tblRecados = new JTable();
		tblRecados.setEnabled(false);
		tblRecados.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Recados", "Data"
				}
				));
		scrollPane2.setViewportView(tblRecados);

		txtOSPesquisar = new JTextField();
		txtOSPesquisar.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				pesquisar_os();
			}
		});
		txtOSPesquisar.setBounds(20, 89, 289, 26);
		contentPane.add(txtOSPesquisar);
		txtOSPesquisar.setColumns(10);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/inova/icones/search.png")));
		label_1.setBounds(321, 89, 69, 37);
		contentPane.add(label_1);

		JLabel lblProcurarOs = new JLabel("Procurar OS");
		lblProcurarOs.setBackground(Color.BLACK);
		lblProcurarOs.setForeground(Color.BLACK);
		lblProcurarOs.setBounds(26, 71, 114, 16);
		contentPane.add(lblProcurarOs);
		
		JLabel lblPesquisaPorSituao = new JLabel("Pesquisa por situação");
		lblPesquisaPorSituao.setFont(new Font("Arial", Font.PLAIN, 11));
		lblPesquisaPorSituao.setBounds(359, 94, 169, 16);
		contentPane.add(lblPesquisaPorSituao);

		//Conexao com o banco
		conexao = Conexao.conector();
	}

	private void pesquisar_os() {

		String sql = "Select os as OS, data_os as Data, descricao as Descrição, situacao as Situação, valor as Valor from tbos where situacao like ?";

		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtOSPesquisar.getText()+"%");
			rs = pst.executeQuery();
			tblOS.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void pesquisar_recado() {

		String sql = "Select recados as Recados, date(data_recado) as Data from tbrecados";

		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			tblRecados.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}
}
