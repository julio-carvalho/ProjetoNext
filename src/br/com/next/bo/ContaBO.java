package br.com.next.bo;

import java.util.Date;
import java.util.UUID;

import br.com.next.bean.Cliente;
import br.com.next.bean.Conta;
import br.com.next.bean.ContaCorrente;
import br.com.next.bean.ContaPoupanca;
import br.com.next.bean.Pix;
import br.com.next.bean.TipoCliente;
import br.com.next.utils.BancoDeDados;


public class ContaBO {

	
	public Conta cadastrarConta(Cliente cliente, String senha) {		
		Conta cc = new Conta();
		
		ContaCorrente contaCorrente = new ContaCorrente();
		ContaPoupanca contaPoupanca = new ContaPoupanca();
		Pix pix = new Pix();
		
		cc.setContaCorrente(contaCorrente);
		cc.setContaPoupanca(contaPoupanca);
		cc.setPix(pix);
		cc.getPix().setChave(" ");
		
		cc.setCliente(cliente);
		cc.setNumero(UUID.randomUUID().toString());
		cc.setSaldo(0);
		cc.setSenha(senha);
		cc.getCliente().setTipoCliente(TipoCliente.COMUM);
		
		cc.getContaCorrente().setAtivado(true);
		
		cc.getContaCorrente().setTaxaManutencao(0.45);
		BancoDeDados.insereConta(cc.getNumero(), cc);
		System.out.println("\nConta cadastrada com sucesso!\nO número da sua conta é: " + cc.getNumero());
		
		return cc;
	}
	
	//Cadastra conta poupança
	public Conta cadastraContaPoupanca(Conta conta) {
		ContaPoupanca cp = new ContaPoupanca();
		conta.setContaPoupanca(cp);
		cp.setAtivado(true);
		cp.setContaPoupanca(cp);
		cp.setCliente(conta.getCliente());
		cp.setNumero(UUID.randomUUID().toString());
		cp.setSaldo(0);
		cp.setTaxaRendimento(0.003);
		
		BancoDeDados.insereConta(cp.getNumero(), cp);
		System.out.println("\nConta Poupança cadastrada com sucesso!\nO número da sua conta é: " + cp.getNumero());
		return cp;
	}
		
	public boolean login(String cpf, String senha) {
		boolean var = false;
		Conta conta = BancoDeDados.buscaContaPorCPF(cpf);
		if(conta != null) {
			if(conta.getSenha().equalsIgnoreCase(senha)) {
				var = true;
			} else {
				var = false;
			}
		}
		return var;
	}
	
	public void consultarSaldoContaCorrente(Conta conta) {
		String nome = conta.getCliente().getNome();
		String cpf = conta.getCliente().getCpf();
		double valor = conta.getSaldo();
		TipoCliente tp = conta.getCliente().getTipoCliente();
		
		System.out.println("Conta Corrente");
		System.out.println("Nome: " + nome);
		System.out.println("CPF: " + cpf);
		System.out.println("Valor: R$" + valor);
		System.out.println("Tipo: " + tp);
	}
	
	public void depositar(Conta conta, double valor) {
		double saldo = conta.getSaldo();
		saldo += valor;
		conta.setSaldo(saldo);
		
		System.out.println("Depósito realizado com sucesso!\nSaldo atual: R$" + conta.getSaldo());
		
		//valida e atualiza o tipo do cliente
		TipoCliente aux = verificaTipo(conta.getSaldo());
		conta.getCliente().setTipoCliente(aux);
		
		BancoDeDados.insereConta(conta.getNumero(), conta);
	}
	
	public void sacar(Conta conta, double valor) {
		double saldo = conta.getSaldo();
		if(saldo >= valor) {
			saldo -= valor;
			conta.setSaldo(saldo);
			System.out.println("\nSaque realizado com sucesso!\nSaldo atual: R$" + conta.getSaldo());
			
			//valida e atualiza o tipo do cliente
			TipoCliente aux = verificaTipo(conta.getSaldo());
			conta.getCliente().setTipoCliente(aux);
			
			BancoDeDados.insereConta(conta.getNumero(), conta);
		} else {
			System.out.println("\nSaldo insuficiente!\nSaldo atual: R$" + conta.getSaldo());
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
			System.out.println("\nTransferência realizado com sucesso!\nSaldo atual: R$" + conta.getSaldo());
			
			//valida e atualiza o tipo do cliente
			TipoCliente aux = verificaTipo(conta.getSaldo());
			conta.getCliente().setTipoCliente(aux);
			
			BancoDeDados.insereConta(conta.getNumero(), conta);
		} else {
			System.out.println("\nSaldo insuficiente!\nSaldo atual: R$" + conta.getSaldo());
		}
	}
		
	public void descontarTaxa(Conta conta) {
		double saldo = conta.getSaldo() * (1-(conta.getContaCorrente().getTaxaManutencao()/100));
		conta.setSaldo(saldo);
		System.out.println("Taxa aplicada!\nSaldo atual: R$" + conta.getSaldo());
		
		//valida e atualiza o tipo do cliente
		TipoCliente aux = verificaTipo(conta.getSaldo());
		conta.getCliente().setTipoCliente(aux);
		
		BancoDeDados.insereConta(conta.getNumero(), conta);
	}
	
	
	//Verifica se o tipo da Conta é Comum, Super ou Premium
	public TipoCliente verificaTipo(double valor) {
		if(valor < 5000)
			//conta.setTipo(TipoCliente.COMUM);
			return TipoCliente.COMUM;
		else if(valor >= 5000 && valor < 15000)
			//conta.setTipo(TipoCliente.SUPER);
			return TipoCliente.SUPER;
		else
			//conta.setTipo(TipoCliente.PREMIUM);
			return TipoCliente.PREMIUM;
	}
}
