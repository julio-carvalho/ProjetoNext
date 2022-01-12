package br.com.next.bean;

public class Pix {
	private boolean ativado;
	private String chave;
	private TipoChavePix tipoChavePix;
	
	public boolean isAtivado() {
		return ativado;
	}
	public void setAtivado(boolean ativado) {
		this.ativado = ativado;
	}
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public TipoChavePix getTipoChavePix() {
		return tipoChavePix;
	}
	public void setTipoChavePix(TipoChavePix tipoChavePix) {
		this.tipoChavePix = tipoChavePix;
	}
}
