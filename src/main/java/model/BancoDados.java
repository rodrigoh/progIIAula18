package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDados {
	private Connection conexao;
	private String usuario;
	private String senha;
	private String porta;
	private String nomeBanco;
	private boolean estaConectado;
	private String mensagemErro;

	public BancoDados(String usuario, String senha, String porta,
										String nomeBanco){
		this.usuario = usuario;
		this.senha = senha;
		this.porta = porta;
		this.nomeBanco = nomeBanco;
		conectar();
	}

	public BancoDados(String usuario, String senha, String nomeBanco){
		this.usuario = usuario;
		this.senha = senha;
		this.porta = "3306";
		this.nomeBanco = nomeBanco;
		conectar();
	}

	public Connection getConexao(){
		return conexao;
	}

	public String getMensagemErro(){
		return mensagemErro;
	}

	public boolean estaConectado(){
		return estaConectado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public void conectar(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection("jdbc:mysql://localhost:"+porta+
							"/"+nomeBanco,usuario,senha);
			estaConectado = true;
		}
		catch (ClassNotFoundException e){
			mensagemErro = "Erro ao carregar o driver...";
			estaConectado = false;
		}
		catch (SQLException e){
			mensagemErro = "Erro ao conectar "+e;
			estaConectado = false;
		}
	}

	public void fecharConexao(){
		try{
			conexao.close();
			estaConectado = false;
		}
		catch (SQLException e){
			mensagemErro = "Erro ao encerrar a conexão";
		}
	}


}
