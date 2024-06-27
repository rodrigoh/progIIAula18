package model;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class Contato {
	private int contatoId;
	private String nome;
	private String eMail;
	private String telefone;
	//Armazenar o link com o banco de dados
	private Connection conexao;

	public Contato(Connection conexao){
		this.conexao = conexao;
	}

	public Contato(){}

	public void setConexao(Connection conexao){
		this.conexao = conexao;
	}

	public int getContatoId() {
		return contatoId;
	}

	public void setContatoId(int contatoId) {
		this.contatoId = contatoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean obterContatoPeloId(int contatoId) throws SQLException{
		String sql = "select nome, e_mail, telefone from tb_contato where " +
						"contato_id = ?";
		PreparedStatement requisicao = conexao.prepareStatement(sql);
		requisicao.setInt(1,contatoId);
		ResultSet resultado = requisicao.executeQuery();
		if(resultado.next()){
			this.contatoId = contatoId;
			nome = resultado.getString("nome");
			eMail = resultado.getString("e_mail");
			telefone = resultado.getString("telefone");
			return true;
		}
		else
			return false;
	}

	public ArrayList<Contato> obterListaArray() throws SQLException{
		ArrayList<Contato> lista = new ArrayList<>();
		ResultSet contatos = obterLista();
		while(contatos.next()){
			Contato tmp = new Contato();
			tmp.setContatoId(contatos.getInt("contato_id"));
			tmp.setNome(contatos.getString("nome"));
			tmp.seteMail(contatos.getString("e_mail"));
			tmp.setTelefone(contatos.getString("telefone"));
			lista.add(tmp);
		}
		return lista;
	}

	public ResultSet obterLista() throws SQLException{
		String sql = "select contato_id, nome, e_mail, telefone from tb_contato";
		PreparedStatement requisicao = conexao.prepareStatement(sql);
		return requisicao.executeQuery();
	}

	public void gravar()throws SQLException{
		String sql = "insert into tb_contato(nome, e_mail, telefone) values(?,?,?)";
		PreparedStatement requisicao = conexao.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
		requisicao.setString(1,nome);
		requisicao.setString(2,eMail);
		requisicao.setString(3,telefone);
		requisicao.execute();
		ResultSet resp = requisicao.getGeneratedKeys();
		if(resp.next())
			//O 1 como parâmetro do getInt() é o índice da coluna que volta no
			// ResultSet
			contatoId = resp.getInt(1);
	}

	public void atualiza() throws SQLException{
		String sql = "update tb_contato set nome = ?, e_mail = ?, telefone = ? " +
						"where contato_id = ?";
		PreparedStatement requisicao = conexao.prepareStatement(sql);
		requisicao.setString(1,nome);
		requisicao.setString(2,eMail);
		requisicao.setString(3,telefone);
		requisicao.setInt(4,contatoId);
		requisicao.executeUpdate();
	}

	public void deletar(int contatoId) throws SQLException{
		String sql = "delete from tb_contato where contato_id = ?";
		PreparedStatement requisicao = conexao.prepareStatement(sql);
		requisicao.setInt(1,contatoId);
		requisicao.executeUpdate();
	}

	@Override
	public String toString(){
		String retorno = "id: "+contatoId+" Nome: "+nome+" E-mail: "+eMail+" Fone" +
						": "+telefone;
		return retorno;
	}

}
