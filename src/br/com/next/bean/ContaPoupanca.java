package br.com.next.bean;

public class ContaPoupanca extends Conta {
	private boolean ativado;
	private double taxaRendimento;
	
	public boolean isAtivado() {
		return ativado;
	}
	public void setAtivado(boolean ativado) {
		this.ativado = ativado;
	}
	public double getTaxaRendimento() {
		return taxaRendimento;
	}
	public void setTaxaRendimento(double taxaRendimento) {
		this.taxaRendimento = taxaRendimento;
	}
	
	
}
