package br.com.next.bo;

import br.com.next.bean.ContaPoupanca;
import br.com.next.bean.TipoCliente;

public class ContaPoupancaBO {
	private ContaPoupanca conta;
	
	
	public ContaPoupancaBO(ContaPoupanca conta) {
		this.conta = conta;
	}
	
	public void consultarSaldoContaPoupanca(ContaPoupanca contaP) {
		String nome = contaP.getCliente().getNome();
		String cpf = contaP.getCliente().getCpf();
		double valor = contaP.getSaldo();
		TipoCliente tp = contaP.getCliente().getTipoCliente();
		
		System.out.println("Conta Poupança");
		System.out.println("Nome: " + nome);
		System.out.println("CPF: " + cpf);
		System.out.println("Valor: " + valor);
		//System.out.println("Tipo: " + tp);
	}
}
