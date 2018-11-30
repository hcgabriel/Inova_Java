package br.com.inova.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class TelaSobre extends JFrame {
	//Declaracao das variaveis
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//Inicio da Aplicacao
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaSobre frame = new TelaSobre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//Inicio da criacao da tela 
	public TelaSobre() {
		setResizable(false);
		setTitle("Sobre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSistemaParaControle = new JLabel("Sistema para controle de tarefas");
		lblSistemaParaControle.setBounds(36, 45, 298, 16);
		contentPane.add(lblSistemaParaControle);

		JLabel lblDesenvolvidoPorGabriel = new JLabel("Desenvolvido por Gabriel Henrique Camargo");
		lblDesenvolvidoPorGabriel.setBounds(36, 87, 318, 16);
		contentPane.add(lblDesenvolvidoPorGabriel);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("/Users/gabriel/Desktop/Workspace Gabriel/Prj Inova/src/br/com/inova/icones/LogoPessoal.png"));
		label_1.setBounds(229, 146, 200, 126);
		contentPane.add(label_1);

		JLabel lblSoftwareDeCdigo = new JLabel("Software de código livre");
		lblSoftwareDeCdigo.setBounds(36, 127, 279, 16);
		contentPane.add(lblSoftwareDeCdigo);
	}
}
