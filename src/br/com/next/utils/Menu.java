package br.com.next.utils;

public class Menu {
	
	public void menuPrincipal() {
		System.out.println("\n=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=");
		System.out.println("=-=-=-=-=-=-=- MENU -=-=-=-=-=-=-=");
		System.out.println(" --> Digite uma op��o: ");
		System.out.println(" 1 - Criar conta");
		System.out.println(" 2 - Logar");
		System.out.println(" 3 - Sair");
		System.out.println("=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=");
	}
	
	public void menuLogado() {
		System.out.println("\n=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=");
		System.out.println("=-=-=-=-=-=-=-=-= LOGADO =-=-=-=-=-=-=-=-=");
		System.out.println("1  - Saque");
		System.out.println("2  - Dep�sito");
		System.out.println("3  - Consultar Saldo");
		System.out.println("4  - Transferir");
		System.out.println("5  - Aplicar Taxa de manuten��o");
		System.out.println("6  - Abrir uma Conta Poupan�a");
		System.out.println("7  - Acessar menu da Conta Poupan�a");
		System.out.println("8  - Ativar PIX");
		System.out.println("9  - Fazer PIX");
		System.out.println("10 - Ativar Cart�o de D�bito");
		System.out.println("11 - Desativar Cart�o de D�bito");
		System.out.println("12 - Realizar compra com o Cart�o de D�bito");
		System.out.println("13 - Ativar Cart�o de Cr�dito");
		System.out.println("14 - Desativar Cart�o de Cr�dito");
		System.out.println("15 - Realizar compra com o Cart�o de Cr�dito");
		System.out.println("16 - Exibir Fatura");
		System.out.println("17 - Pagar Fatura");
		System.out.println("18 - Sair");
		System.out.println("=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=");
	}
	
	public void menuContaPoupanca() {
		System.out.println("\n=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=");
		System.out.println("=-=-=-=-=-=-= CONTA POUPAN�A =-=-=-=-=-=-=");
		System.out.println("1 - Saque");
		System.out.println("2 - Dep�sito");
		System.out.println("3 - Consultar saldo");
		System.out.println("4 - Transferir para Conta Corrente");
		System.out.println("5 - Transferir para outro tipo de conta");
		System.out.println("6 - Aplicar rendimento");
		System.out.println("7 - Sair");
		System.out.println("=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=");
	}
	public void menuDiaVencimento() {
		System.out.println("Escolha o dia do vencimento da sua fatura:");
		System.out.println("1 - Dia 03");
		System.out.println("2 - Dia 12");
		System.out.println("3 - Dia 16");
		System.out.println("4 - Dia 22");
		System.out.println("5 - Dia 27");
	}
}
