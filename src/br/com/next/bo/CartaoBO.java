package br.com.next.bo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.next.bean.CartaoCredito;
import br.com.next.bean.CartaoDebito;
import br.com.next.bean.Compra;
import br.com.next.bean.Conta;
import br.com.next.utils.BancoDeDados;

public class CartaoBO {
	
	public void cadastraCartaoDebito(Conta conta, int bandeira, double limite, String senha) {
		CartaoDebito cartaoDebito = new CartaoDebito();
		conta.setCartaoDebito(cartaoDebito);
		cartaoDebito.setAtivo(true);
		cartaoDebito.setId(UUID.randomUUID().toString());
		cartaoDebito.setNumero(UUID.randomUUID().toString());
		cartaoDebito.setLimitePorTransacao(limite);
		cartaoDebito.setSenha(senha);
		
		//defini tipo bandeira
		if(bandeira == 1)
			cartaoDebito.setBandeira("VISA");
		else if(bandeira == 2)
			cartaoDebito.setBandeira("MASTERCARD");
		else
			cartaoDebito.setBandeira("ELO");
		
		System.out.println("\nCartão criado com sucesso!\nO número do cartão é: " + cartaoDebito.getNumero());
	}
		
	//compra com o cartão de débito
	public void comprarCartaoDebito(Conta conta, double valor) {
		double saldoAtual = conta.getSaldo();
	
		if(conta.getCartaoDebito().getLimitePorTransacao() >= valor) {
			if(saldoAtual >= valor) {
				saldoAtual -= valor;
				conta.setSaldo(saldoAtual);
				
				BancoDeDados.insereConta(conta.getNumero(), conta);
				System.out.println("\nCompra realizada com sucesso!\nSaldo atual: R$" + conta.getSaldo());
			} else {
				System.out.println("\nSaldo insuficiente!\nSaldo atual: R$" + conta.getSaldo());
			}
		} else {
			System.out.println("\nO valor da compra é maior que o limite de transação.\nLimite de transação: R$" + conta.getCartaoDebito().getLimitePorTransacao());
		}
	}
	
	public void cadastraCartaoCredito(Conta conta, int bandeira, double limite, String senha, int dia) {
		CartaoCredito cartaoCredito = new CartaoCredito();
		conta.setCartaoCredito(cartaoCredito);
		cartaoCredito.setAtivo(true);
		cartaoCredito.setId(UUID.randomUUID().toString());
		cartaoCredito.setNumero(UUID.randomUUID().toString());
		cartaoCredito.setLimite(limite);
		cartaoCredito.setSenha(senha);
		cartaoCredito.setDataVencimento(this.ajustaData(dia));
		cartaoCredito.setFatura(0);
		
		//defini tipo bandeira
		if(bandeira == 1)
			cartaoCredito.setBandeira("VISA");
		else if(bandeira == 2)
			cartaoCredito.setBandeira("MASTERCARD");
		else
			cartaoCredito.setBandeira("ELO");
		
		System.out.println("\nCartão criado com sucesso!\nO número do cartão é: " + cartaoCredito.getNumero());
	}
		
	//realiza compra com cartão de crédito
	public void comprarCartaoCredito(Conta conta, double valor) {
		double limite = conta.getCartaoCredito().getLimite();
		if(limite >= valor) {

			adicionaCompra(conta, valor);
			double fatura = conta.getCartaoCredito().getFatura();
			
				
			limite -= valor;
			conta.getCartaoCredito().setLimite(limite);
				
			fatura += valor;
			conta.getCartaoCredito().setFatura(fatura);
							
			BancoDeDados.insereConta(conta.getNumero(), conta);
			
			System.out.println("\nCompra realizada com sucesso!\nFatura atual: R$" + conta.getCartaoCredito().getFatura());
		} else {
			System.out.println("Limite de compra atingido!");
		}
	}
	
	//adiciona compra
	public void adicionaCompra(Conta conta, double valor) {
		Compra compra = new Compra(new Date(), valor);
		conta.getCartaoCredito().getCompras().add(compra);
	}
	
	//exibi a fatura e os dados do cartão de crédito
	public void exibirFatura(Conta conta) {
		List<Compra> lCompra = conta.getCartaoCredito().getCompras();
		
		
		System.out.println("\n+----------------------------------------------------------------------+");
		System.out.println("|                                DADOS                                 |");
		System.out.println("+----------------------------------------------------------------------+");
		System.out.println(" * Número da conta: " + conta.getNumero());
		System.out.println(" * Nome: " + conta.getCliente().getNome());
		System.out.println(" * Limite disponível: " + conta.getCartaoCredito().getLimite());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String data = sdf.format(conta.getCartaoCredito().getDataVencimento());
		System.out.println(" * Data vencimento da próxima fatura: " + data);
		
		System.out.println("+----------------------------------------------------------------------+");
		System.out.println("|                               COMPRAS                                |");
		System.out.println("+----------------------------------------------------------------------+");
		
		SimpleDateFormat sdfComHora = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		
		for(Compra compra : lCompra) {
			String dataDaCompra = sdfComHora.format(compra.getData());
			double valorDaCompra = compra.getValor();
			
			System.out.println(" * Compra realizada no dia " + dataDaCompra + " no valor de R$" + valorDaCompra);
		}
		System.out.println("+----------------------------------------------------------------------+");
		System.out.println(" * Fatura Total: R$: " + conta.getCartaoCredito().getFatura());
		System.out.println("+----------------------------------------------------------------------+\n");
		
	}
	
	public void pagarFatura(Conta conta) {
		double saldoAtual = conta.getSaldo();
		double fatura = conta.getCartaoCredito().getFatura();
		
		if(saldoAtual >= fatura) {
			saldoAtual -= fatura;
			conta.setSaldo(saldoAtual);
			conta.getCartaoCredito().setFatura(0);
			System.out.println("\nFatura paga com sucesso.\nSaldo atual: R$" + conta.getSaldo());
			BancoDeDados.insereConta(conta.getNumero(), conta);
		} else {
			System.out.println("\nSaldo insuficiente.\nSaldo atual: R$" + conta.getSaldo());
		}
	}
	
	//desativa cartao
	public void desativaCartao(Conta conta, int opc) {
		//1 - Cartão de débito
		//2 - Cartão de crédito
		if(opc == 1) {
			conta.getCartaoDebito().setAtivo(false);
			System.out.println("Cartão de Débito desativado");
		} else {
			if(conta.getCartaoCredito().getFatura() == 0) {
				conta.getCartaoCredito().setAtivo(false);
				System.out.println("Cartão de Crédito desativado");
			} else {
				System.out.println("Você precisa pagar a fatura do cartão para poder cancela-lo.\nFatura atual: R$" + conta.getCartaoCredito().getFatura());
			}
		}
	}
		
	public Date ajustaData(int diaDoMes) {
		//verifica qual o dia o usuário escolhe
		int dia = 0;
		if(diaDoMes == 1)
			dia = 3;
		else if(diaDoMes == 2)
			dia = 12;
		else if(diaDoMes == 3)
			dia = 16;
		else if(diaDoMes == 4)
			dia = 22;
		else if(diaDoMes == 5)
			dia = 27;
				
		Calendar cal = Calendar.getInstance();
		//adiciona o dia solicitado
		cal.set(Calendar.DAY_OF_MONTH, dia);
		//acrescenta um mes
		cal.add(Calendar.MONTH, 1);
		
		Date data = cal.getTime();
		return data;
	}
}
