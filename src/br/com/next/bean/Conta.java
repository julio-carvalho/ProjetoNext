package br.com.next.bean;

import java.util.Date;

public class Conta {
	private String numero;
	private double saldo;
	private String senha;
	private Cliente cliente;
	private Pix pix;
	private Date data;
	private ContaCorrente contaCorrente;
	private ContaPoupanca contaPoupanca;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Pix getPix() {
		return pix;
	}
	public void setPix(Pix pix) {
		this.pix = pix;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}
	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
	public ContaPoupanca getContaPoupanca() {
		return contaPoupanca;
	}
	public void setContaPoupanca(ContaPoupanca contaPoupanca) {
		this.contaPoupanca = contaPoupanca;
	}
	
	
	
	
}
