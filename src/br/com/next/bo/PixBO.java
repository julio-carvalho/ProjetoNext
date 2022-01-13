package br.com.next.bo;

import br.com.next.bean.Conta;
import br.com.next.bean.Pix;
import br.com.next.bean.TipoChavePix;

public class PixBO {
	
	public Pix cadastrarPix(Conta conta, int tipoChave, String chave) {
		Pix pix = new Pix();
		conta.setPix(pix);
		TipoChavePix tcp = null;
		pix.setAtivado(true);
		pix.setChave(chave);
		
		//1 - CPF
		//2 - EMAIL
		//3 - TELEFONE
		//4 - ALEATORIO
		if(tipoChave == 1) {
			tcp = TipoChavePix.CPF;
		} else if(tipoChave == 2) {
			tcp = TipoChavePix.EMAIL;
		} else if(tipoChave == 3) {
			tcp = TipoChavePix.TELEFONE;
		} else if(tipoChave == 4) {
			tcp = TipoChavePix.ALEATORIO;
		}
		pix.setTipoChavePix(tcp);
		
		conta.setPix(pix);
		
		System.out.println("PIX cadastrado com sucesso!\nA chave do seu PIX é: " + pix.getChave());
		return pix;
	}
	
	public boolean validaPix(Pix pix) {
		boolean valida = false;
		if(pix.isAtivado())
			valida = true;
		else
			valida = false;
		
		return valida;
	}
	
	public void pagarPix(Conta conta, Conta contaDestino, double valor) {
		double saldoAtual = conta.getSaldo();
		double saldoDestino = contaDestino.getSaldo();
		
		if(saldoAtual >= valor) {
			saldoAtual -= valor;
			conta.setSaldo(saldoAtual);
			
			saldoDestino += valor;
			contaDestino.setSaldo(saldoDestino);
			System.out.println("PIX realizado com sucesso!\nSaldo atual: R$" + conta.getSaldo());
		} else {
			System.out.println("\nSaldo insuficiente!\nSaldo atual: R$" + conta.getSaldo());
		}
	}
}
