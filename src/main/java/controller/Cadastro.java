package controller;

import model.BancoDados;
import model.Contato;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cadastro {
	public static String leString(String mensagem){
		Scanner ler = new Scanner(System.in);
		System.out.print(mensagem+": ");
		return ler.nextLine();
	}
	public static void main(String[] args) {
		BancoDados banco = new BancoDados("aulaMysql", "12346", "aulaMysql");
		if(banco.estaConectado()){
			String opc;
			do{
				System.out.println("Selecione uma opção");
				System.out.println("c - cadastrar um contato");
				System.out.println("l - listar os contatos");
				System.out.println("d - deletar um contato");
				System.out.println("a - alterar um contato");
				System.out.println("s - sair");
				opc = leString("");
				switch (opc){
					case "c"->{
						Contato c1 = new Contato(banco.getConexao());
						String nome = leString("Nome");
						String eMail = leString("E-mail");
						String telefone = leString("Telefone");
						c1.setNome(nome);
						c1.seteMail(eMail);
						c1.setTelefone(telefone);
						try{
							c1.gravar();
							System.out.println("Contato gravado obteve o ID "+c1.getContatoId());

						}
						catch (SQLException e){
							System.out.println("Ops! Erro ao gravar o contato!");
						}
					}
					case "l"->{
						Contato c1 = new Contato(banco.getConexao());
						try {
							ArrayList<Contato> lista = c1.obterListaArray();
							System.out.println(lista);
						}
						catch (SQLException e){
							System.out.println("Não foi possível obter a lista de contatos");
						}
					}
					case "d"->{
						Contato c1 = new Contato(banco.getConexao());
						String idStr = leString("Qual id deseja apagar: ");
						int contatoId = Integer.parseInt(idStr);
						try{
							c1.deletar(contatoId);
							System.out.println("Se o id existia, o contato foi apagado");
						}
						catch (SQLException e){
							System.out.println("Erro ao apagar o contato!");
						}
					}
					case "a"->{
						Contato c1 = new Contato(banco.getConexao());
						String idStr = leString("Qual id deseja atualizar: ");
						int contatoId = Integer.parseInt(idStr);
						String nome = leString("Nome");
						String eMail = leString("E-mail");
						String telefone = leString("Telefone");
						c1.setContatoId(contatoId);
						c1.setNome(nome);
						c1.seteMail(eMail);
						c1.setTelefone(telefone);
						try{
							c1.atualiza();
							System.out.println("Contato atualizado!");
						}
						catch (SQLException e){
							System.out.println("Erro ao atualizar o registro");
						}
					}
				}
			}
			while(!opc.equals("s"));
		}
		else{
			System.out.println("Não conectado!");
		}
	}
}
