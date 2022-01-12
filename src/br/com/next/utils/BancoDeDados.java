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
			System.out.println("Conta não encontrada!");
		}
		
		return conta;
	}
	
	
	//busca a conta pelo cpf
	public static Conta buscarContaPorCPF(String cpf){
		//List<Conta> lConta = new ArrayList<Conta>();
		Conta conta = null;
		for(Map.Entry<String, Conta> mapaConta: BancoDeDados.BANCO_DE_DADOS.entrySet()) {
			conta = mapaConta.getValue();
			if(conta.getCliente().getCpf().equals(cpf)) {
				return conta;
			}
		}
		return conta;
	}
	
	public static boolean validaCPF(String cpf) {
		boolean var = false;
		
		for(Map.Entry<String, Conta> mapaConta: BancoDeDados.BANCO_DE_DADOS.entrySet()) {
			Conta conta = mapaConta.getValue();
			if(conta.getCliente().getCpf().equals(cpf)) {
				var = true;
			} else {
				var = false;
			}
		}
		return var;
	}
}
