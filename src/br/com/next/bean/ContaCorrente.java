package br.com.next.bean;

public class ContaCorrente extends Conta {
	private boolean ativado;
	private double taxaManutencao;
	
	public boolean isAtivado() {
		return ativado;
	}

	public void setAtivado(boolean ativado) {
		this.ativado = ativado;
	}

	public double getTaxaManutencao() {
		return taxaManutencao;
	}

	public void setTaxaManutencao(double taxaManutencao) {
		this.taxaManutencao = taxaManutencao;
	}
	
	
}
