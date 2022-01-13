package br.com.next.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.next.bean.Conta;

public class BancoDeDados {
	private static Map<String, Conta> BANCO_DE_DADOS = new HashMap<String, Conta>();
	
	
	//insere conto no banco de dados
	public static void insereConta(String numeroConta, Conta conta) {
		BancoDeDados.BANCO_DE_DADOS.put(numeroConta, conta);
	}
	
	
	//busca a conta pelo numero
	public static Conta buscaContaPorNumero(String numeroConta) {
		
		Conta conta = BancoDeDados.BANCO_DE_DADOS.get(numeroConta);
		
		
		if(conta == null) {
			System.out.println("Conta n�o encontrada!");
		}
				
		
		return conta;
	}
		
	//busca a conta pelo cpf
	public static Conta buscaContaPorCPF(String cpf){
		Conta conta = null;
		Conta c = null;
		for(Map.Entry<String, Conta> mapaConta: BancoDeDados.BANCO_DE_DADOS.entrySet()) {
			conta = mapaConta.getValue();
			if(conta.getCliente().getCpf().equals(cpf)) {
				c = conta;
			}
		}
		return c;
	}
	
	//busca a conta pelo cpf retornando true ou false
	public static boolean validaContaCPF(String cpf) {
		boolean var = false;
		
		for(Map.Entry<String, Conta> mapaConta: BancoDeDados.BANCO_DE_DADOS.entrySet()) {
			Conta conta = mapaConta.getValue();
			if(conta.getCliente().getCpf().equals(cpf)) {
				var = true;
			} 
		}
		return var;
	}
	
	public static Conta buscaPix(String chave) {
		Conta conta = null;
		Conta c = null;
		for(Map.Entry<String, Conta> mapaConta: BancoDeDados.BANCO_DE_DADOS.entrySet()) {
			conta = mapaConta.getValue();
			if(conta.getPix().getChave().equals(chave)) {
				c = conta;
			}
		}
		return c;
	}
	
	public static boolean validaPix(String chave) {
		boolean var = false;
		
		for(Map.Entry<String, Conta> mapaConta: BancoDeDados.BANCO_DE_DADOS.entrySet()) {
			Conta conta = mapaConta.getValue();
			if(conta.getPix().getChave().equals(chave)) {
				var = true;
			} 
		}
		return var;
	}
}
