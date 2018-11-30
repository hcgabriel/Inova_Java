package br.com.inova.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import javax.swing.JButton;


public class TelaPrincipalVisitas extends JFrame {

	//Declaracao das variaveis
	private static final long serialVersionUID = 1L;
	public static JPanel contentPane;
	public static JLabel lblData;
	private JTextField txtOSPesquisar;
	private JTable tblVisita;

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

	//Criacao da Tela
	public TelaPrincipalVisitas() {
		//Quando a tela inicia a data aparece automaticamente com a linha abaixo
		addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
				lblData.setText(formatador.format(data));
				pesquisar_visita();
			}
		});
		setTitle("Inova Tarefas ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setBounds(100, 100, 819, 800);
		//Data
		lblData = new JLabel("Data");
		lblData.setForeground(SystemColor.textText);
		lblData.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblData.setBounds(695, 70, 86, 15);
		contentPane.add(lblData);

		JLabel lblBemVindo = new JLabel("Bem Vindo");
		lblBemVindo.setForeground(SystemColor.textText);
		lblBemVindo.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblBemVindo.setBounds(674, 42, 124, 16);
		contentPane.add(lblBemVindo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 152, 591, 580);
		contentPane.add(scrollPane);

		tblVisita = new JTable();
		tblVisita.setEnabled(false);
		tblVisita.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"ID", "Cliente", "Fone", "Data", "Imovel", "Definição", "Descrição"
				}
				));
		scrollPane.setViewportView(tblVisita);

		txtOSPesquisar = new JTextField();
		txtOSPesquisar.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				pesquisar_visita();
			}
		});
		txtOSPesquisar.setBounds(20, 89, 289, 26);
		contentPane.add(txtOSPesquisar);
		txtOSPesquisar.setColumns(10);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/inova/icones/search.png")));
		label_1.setBounds(321, 89, 69, 37);
		contentPane.add(label_1);

		JLabel lblProcurarOs = new JLabel("Procurar Visitas");
		lblProcurarOs.setBackground(Color.BLACK);
		lblProcurarOs.setForeground(Color.BLACK);
		lblProcurarOs.setBounds(26, 71, 114, 16);
		contentPane.add(lblProcurarOs);
		
		JButton btnVisitas = new JButton("Visitas");
		btnVisitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaVisitas os = new TelaVisitas();
				os.setVisible(true);
			}
		});
		btnVisitas.setBounds(681, 152, 117, 29);
		contentPane.add(btnVisitas);
		
		JButton btnRelatrio = new JButton("Relatório");
		btnRelatrio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

					int confirma = JOptionPane.showConfirmDialog(null, "Confirma a emissao deste relatório?", "Atencao", JOptionPane.YES_NO_OPTION);
					if (confirma == JOptionPane.YES_OPTION) {
						try {
							JasperPrint print = JasperFillManager.fillReport("/Users/gabriel/Desktop/Reports/ovisi.jasper", null, conexao);
							JasperViewer.viewReport(print, false);
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, e2);
						}
					}
			}
		});
		btnRelatrio.setBounds(681, 191, 117, 29);
		contentPane.add(btnRelatrio);
		
		JButton tbnOpc = new JButton("OS");
		tbnOpc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaPrincipal tela = new TelaPrincipal();
				tela.setVisible(true);
			}
		});
		tbnOpc.setBounds(681, 234, 117, 29);
		contentPane.add(tbnOpc);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Exibir caixa de dialogo
				int sair = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja sair?","Atenção",JOptionPane.YES_NO_OPTION);
				if (sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		btnSair.setBounds(681, 275, 117, 29);
		contentPane.add(btnSair);
		
		JLabel lblPesquisaPorNome = new JLabel("Pesquisa por nome do cliente");
		lblPesquisaPorNome.setFont(new Font("Arial", Font.PLAIN, 11));
		lblPesquisaPorNome.setBounds(361, 94, 169, 16);
		contentPane.add(lblPesquisaPorNome);

		//Conexao com o banco
		conexao = Conexao.conector();
	}

	private void pesquisar_visita() {

		String sql = "select idvisita as ID, cliente as Cliente, fone as Fone, date(data_visita) as Data, imovel as Imovel, conversao as Definição, descricao as Descrição from tbvisitas where cliente like ?";

		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtOSPesquisar.getText()+"%");
			rs = pst.executeQuery();
			tblVisita.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}
}
