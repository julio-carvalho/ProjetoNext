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
		
		System.out.println("Cart�o criado com sucesso!\nO n�mero do cart�o �: " + cartaoDebito.getNumero());
	}
	
	//valida se o cart�o de d�bito est� ativado
	public boolean validaCartaoDebito(Conta conta) {
		boolean valida = false;
		
		
		if(conta.getCartaoDebito().isAtivo()) {
			valida = true;
		}
		
		return valida;
	}
	
	//compra com o cart�o de d�bito
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
			System.out.println("\nO valor da compra � maior que o limite de transa��o.\nLimite de transa��o: R$" + conta.getCartaoDebito().getLimitePorTransacao());
		}
	}
	
	public void cadastraCartaoCredito(Conta conta, int bandeira, double limite, String senha) {
		CartaoCredito cartaoCredito = new CartaoCredito();
		conta.setCartaoCredito(cartaoCredito);
		cartaoCredito.setAtivo(true);
		cartaoCredito.setId(UUID.randomUUID().toString());
		cartaoCredito.setNumero(UUID.randomUUID().toString());
		cartaoCredito.setLimite(limite);
		cartaoCredito.setSenha(senha);
		cartaoCredito.setDataVencimento(this.getAdiciona1Mes());
		cartaoCredito.setFatura(0);
		
		//defini tipo bandeira
		if(bandeira == 1)
			cartaoCredito.setBandeira("VISA");
		else if(bandeira == 2)
			cartaoCredito.setBandeira("MASTERCARD");
		else
			cartaoCredito.setBandeira("ELO");
		
		System.out.println("Cart�o criado com sucesso!\nO n�mero do cart�o �: " + cartaoCredito.getNumero());
	}
	

	//valida se o cart�o de credito est� ativado
	public boolean validaCartaoCredito(Conta conta) {
		boolean valida = false;
		
		if(conta.getCartaoCredito().isAtivo())
			valida = true;
		
		return valida;
	}
	
	//realiza compra com cart�o de cr�dito
	public void comprarCartaoCredito(Conta conta, double valor) {
		double saldoAtual = conta.getSaldo();
		double limite = conta.getCartaoCredito().getLimite();
		if(limite >= valor) {
				
				 Compra compra = new Compra(new Date(), valor);
				 conta.getCartaoCredito().addCompras(compra);
				
			double fatura = conta.getCartaoCredito().getFatura();
			//adicionaCompra(conta, valor);
				
			limite -= valor;
			conta.getCartaoCredito().setLimite(limite);
				
			fatura += valor;
			conta.getCartaoCredito().setFatura(fatura);
							
			BancoDeDados.insereConta(conta.getNumero(), conta);
			
			System.out.println("\nCompra realizada com sucesso!\nSaldo atual: R$" + conta.getSaldo());
		} else {
			System.out.println("Limite de compra atingido!");
		}
	}
	
	//exibi a fatura e os dados do cart�o de cr�dito
	public void exibirFatura(Conta conta) {
		List<Compra> lCompra = conta.getCartaoCredito().getCompras();
		
		System.out.println("\n=-=-=-=-=-= DADOS =-=-=-=-=-=");
		
		System.out.println("\nDados Cart�o do Cr�dito da conta: " + conta.getNumero());
		System.out.println("Nome: " + conta.getCliente().getNome());
		System.out.println("Limite: " + conta.getCartaoCredito().getLimite());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String data = sdf.format(conta.getCartaoCredito().getDataVencimento());
		System.out.println("Data vencimento da fatura: " + data);
		
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		System.out.println("=-=-=-=-=- COMPRAS -=-=-=-=-=");
		
		SimpleDateFormat sdfComHora = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		
		for(Compra compra : lCompra) {
			String dataDaCompra = sdfComHora.format(compra.getData());
			double valorDaCompra = compra.getValor();
			
			System.out.println("Compra realizada no dia " + dataDaCompra + " no valor de R$" + valorDaCompra);
		}
		System.out.println("Fatura Total: R$: " + conta.getCartaoCredito().getFatura());
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		
	}
	public void adicionaCompra(Conta conta, double valor) {
		Compra compra = new Compra(new Date(), valor);
		conta.getCartaoCredito().getCompras().add(compra);
	}
	
	//desativa cartao
	public void desativaCartao(Conta conta, int opc) {
		//1 - Cart�o de d�bito
		//2 - Cart�o de cr�dito
		if(opc == 1) {
			conta.getCartaoDebito().setAtivo(false);
			System.out.println("Cart�o de D�bito desativado");
		} else {
			if(conta.getCartaoCredito().getFatura() == 0) {
				
			} else {
				System.out.println("Voc� precisa pagar a fatura do cart�o para poder cancela-lo");
			}
		}
	}
	
	
	public Date getAdiciona1Mes() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		Date data  = cal.getTime();
		
		return data;
	}


}
