package br.com.next.bo;

import br.com.next.bean.Conta;
import br.com.next.bean.Pix;
import br.com.next.bean.TipoChavePix;

public class PixBO {
	
	public Pix cadastrarPix(Conta conta, int tipoChave, String chave) {
		Pix pix = new Pix();
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
		return pix;
	}
}
