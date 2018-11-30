package br.com.inova.utils;

import java.sql.*;

public class Conexao {
	
	// Metodo responsavel por estabelecer a conexao com o Banco
	
	public static Connection conector(){
		java.sql.Connection conexao = null;
		// A linha abaixo chama o driver 
		String driver = "com.mysql.cj.jdbc.Driver";
		// Armazenando informacoes referentes ao Banco
		String url = "jdbc:mysql://localhost:3306/dbinova?useSSL=false";
		String user = "root";
		String password = "G@bi0604";
		//Estabelecendo conexao com o Banco
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, user, password);
			return conexao;
		} catch (Exception e) {
			// A linha abaixo serve de apoio para esclarecer o erro
			//System.out.println(e);
			return null;
		}
	}

}
