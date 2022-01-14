package br.com.next.bean;

import java.util.Date;

public class Compra {
	private Date data;
	private double valor;
	
	public Compra(Date data, double valor) {
		super();
		this.data = data;
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
}
