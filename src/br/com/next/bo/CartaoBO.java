package br.com.next.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import br.com.next.bean.CartaoCredito;
import br.com.next.bean.CartaoDebito;
import br.com.next.bean.Conta;

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
		
		System.out.println("Cartão criado com sucesso!\nO número do cartão é: " + cartaoDebito.getNumero());
	}
	
	//valida se o cartão de débito está ativado
	public boolean validaCartaoDebito(Conta conta) {
		boolean valida = false;
		valida = conta.getCartaoDebito().isAtivo();
		
		if(valida) {
			valida = true;
		}
		
		return valida;
	}
	
	public void cadastraCartaoCredito(Conta conta, int bandeira, double limite, String senha) {
		CartaoCredito cartaoCredito = new CartaoCredito();
		conta.setCartaoCredito(cartaoCredito);
		cartaoCredito.setAtivo(true);
		cartaoCredito.setId(UUID.randomUUID().toString());
		cartaoCredito.setNumero(UUID.randomUUID().toString());
		cartaoCredito.setLimite(limite);
		cartaoCredito.setSenha(senha);
		cartaoCredito.setDataVencimento(this.getDateAdd1Month());
		cartaoCredito.setValorFatura(0);
		
		//defini tipo bandeira
		if(bandeira == 1)
			cartaoCredito.setBandeira("VISA");
		else if(bandeira == 2)
			cartaoCredito.setBandeira("MASTERCARD");
		else
			cartaoCredito.setBandeira("ELO");
		
		System.out.println("Cartão criado com sucesso!\nO número do cartão é: " + cartaoCredito.getNumero());
	}
	
	//valida se o cartão de credito está ativado
	public boolean validaCartaoCredito(Conta conta) {
		boolean valida = false;
		
		if(conta.getCartaoCredito().isAtivo())
			valida = true;
		
		return valida;
	}
	
	public Date getDateAdd1Month() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		Date data  = cal.getTime();
		
		return data;
	}
}
