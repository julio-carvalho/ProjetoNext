package br.com.next.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartaoCredito extends Cartao {
	private List<Compra> compras;
	private double limite;
	private Date dataVencimento;
	private double fatura;
	private Conta conta;
	
	public CartaoCredito() {
		this.compras = new ArrayList<Compra>();
	}
	
	public List<Compra> getCompras() {
		return compras;
	}
	public void addCompras(Compra compra) {
		this.compras.add(compra);
	}
	public double getLimite() {
		return limite;
	}
	public void setLimite(double limite) {
		this.limite = limite;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public double getFatura() {
		return fatura;
	}
	public void setFatura(double fatura) {
		this.fatura = fatura;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
