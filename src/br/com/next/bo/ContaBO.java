package br.com.next.bo;

import java.util.Date;
import java.util.UUID;

import br.com.next.bean.Cliente;
import br.com.next.bean.Conta;
import br.com.next.bean.ContaCorrente;
import br.com.next.bean.ContaPoupanca;
import br.com.next.bean.TipoCliente;
import br.com.next.utils.BancoDeDados;


public class ContaBO {
	/*private Conta conta;
	
	
	public ContaBO(Conta conta) {
		this.conta = conta;
	}
	
	
	public ContaBO(Cliente cliente, int tipoConta, String senha) {
		this.conta = this.gerarConta(cliente, tipoConta, senha);
	}*/
	
	public Conta cadastrarConta(Cliente cliente, int tipoConta, String senha) {
		//1 - Conta Corrente
		//2 - Conta Poupança
		if(tipoConta == 1) {
			Conta cc = new Conta();
			ContaCorrente contaCorrente = new ContaCorrente();
			
			cc.setContaCorrente(contaCorrente);
			cc.setCliente(cliente);
			cc.setNumero(UUID.randomUUID().toString());
			cc.setSaldo(0);
			cc.setSenha(senha);
			cc.getContaCorrente().setAtivado(true);
			cc.getContaCorrente().setTaxaManutencao(0.45);
			BancoDeDados.insereConta(cc.getNumero(), cc);
			System.out.println("\nConta cadastrada com sucesso!\nO número da sua conta é: " + cc.getNumero());
			
			return cc;
			
		} else if(tipoConta == 2) {
			ContaPoupanca cp = new ContaPoupanca();
			cp.setCliente(cliente);
			cp.setNumero(UUID.randomUUID().toString());
			cp.setSaldo(0);
			cp.getContaPoupanca().setAtivado(true);
			cp.getContaPoupanca().setTaxaRendimento(0.003);
			cp.setContaPoupanca(cp);
			
			BancoDeDados.insereConta(cp.getNumero(), cp);
			System.out.println("\nConta Poupança cadastrada com sucesso!\nO número da sua conta é: " + cp.getNumero());
			
			return cp;
		}
		
	return null;
	}
	
	public void consultarSaldoContaCorrente(Conta conta) {
		String nome = conta.getCliente().getNome();
		String cpf = conta.getCliente().getCpf();
		double valor = conta.getSaldo();
		TipoCliente tp = conta.getCliente().getTipoCliente();
		
		System.out.println("Conta Corrente");
		System.out.println("Nome: " + nome);
		System.out.println("CPF: " + cpf);
		System.out.println("Valor: " + valor);
		System.out.println("Tipo: " + tp);
	}
	
	public void depositar(Conta conta, double valor) {
		double saldo = conta.getSaldo();
		saldo += valor;
		conta.setSaldo(saldo);
		
		System.out.println("Depósito realizado com sucesso!\nSaldo atual: " + conta.getSaldo());
		BancoDeDados.insereConta(conta.getNumero(), conta);
	}
	
	public void sacar(Conta conta, double valor) {
		double saldo = conta.getSaldo();
		if(saldo >= valor) {
			saldo -= valor;
			conta.setSaldo(saldo);
			System.out.println("\nSaque realizado com sucesso!\nSaldo atual: " + conta.getSaldo());
			BancoDeDados.insereConta(conta.getNumero(), conta);
		} else {
			System.out.println("\nSaldo insuficiente!\nSaldo atual: " + conta.getSaldo());
		}
	}
	
	public void transferir(Conta conta, Conta contaDestino, double valor) {
		double saldoAtual = conta.getSaldo();
		double saldoDestino = contaDestino.getSaldo();
		
		if(saldoAtual >= valor) {
			saldoAtual -= valor;
			conta.setSaldo(saldoAtual);
			
			saldoDestino += valor;
			contaDestino.setSaldo(saldoDestino);
			System.out.println("Transferência realizado com sucesso!\nSaldo atual: " + conta.getSaldo());
		} else {
			System.out.println("\nSaldo insuficiente!\nSaldo atual: " + conta.getSaldo());
		}
	}
	
	public boolean login(String cpf, String senha) {
		boolean var = false;
		Conta conta = BancoDeDados.buscarContaPorCPF(cpf);
		if(conta != null) {
			if(conta.getSenha().equalsIgnoreCase(senha)) {
				var = true;
			} else {
				var = false;
			}
		}
		return var;
	}
	
	public Conta validaConta(String cpf) {
		Conta conta = null;
		conta = BancoDeDados.buscarContaPorCPF(cpf);
		if(conta == null) {
			return null;
		}
		return conta;
	}
	
	public boolean valida2(String cpf) {
		boolean var = false;
		boolean verifica = false;
		verifica = BancoDeDados.validaCPF(cpf);
		if(verifica == true) {
			System.out.println("Entro no true");
			var = true;
		} else {
			System.out.println("entrou no false");
			var = false;
		}
		return var;
	}
}
