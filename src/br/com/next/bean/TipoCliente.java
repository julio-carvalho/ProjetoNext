package br.com.next.bean;

public enum TipoCliente {
	COMUM(0), SUPER(1), PREMIUM(2);
	
	private int idTipoCliente;
	
	TipoCliente(int id){
		this.idTipoCliente = id;
	}
	
	public int getIdTipoCliente() {
		return this.idTipoCliente;
	}
}
