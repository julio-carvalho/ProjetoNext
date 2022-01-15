//Julio Cesar Menezes Carvalho

package br.com.next.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import br.com.next.bean.Cliente;
import br.com.next.bean.Conta;
import br.com.next.bean.ContaPoupanca;
import br.com.next.bean.Endereco;
import br.com.next.bean.Pix;
import br.com.next.bean.TipoCliente;
import br.com.next.bo.CartaoBO;
import br.com.next.bo.ClienteBO;
import br.com.next.bo.ContaBO;
import br.com.next.bo.ContaPoupancaBO;
import br.com.next.bo.EnderecoBO;
import br.com.next.bo.PixBO;
import br.com.next.utils.BancoDeDados;

public class MainProject {
	ClienteBO clienteBo = new ClienteBO();
	
	public static void main(String[] args) {
		menuPrincipal();
		System.exit(0);
	}
	
	public static void menuPrincipal() {
		Scanner scan = new Scanner(System.in);
		Scanner scanInt = new Scanner(System.in);
		int opc = 0, numero;
		boolean continuar = true;
		String nome, cpf = " ", senha, logradouro, cep, bairro, cidade, estado;
		
		//enquanto continuar for true, roda o menu
		while(continuar) {
			//op�oes do menu principal, usuario nao consegue logar sem criar conta
			System.out.println("\n=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=");
			System.out.println("=-=-=-=-=-=-=- MENU -=-=-=-=-=-=-=");
			System.out.println("=   --> Digite uma op��o:        =");
			System.out.println("=   1 - Criar conta              =");
			System.out.println("=   2 - Logar                    =");
			System.out.println("=   3 - Sair                     =");
			System.out.println("=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=");
			opc = scan.nextInt();
			
			switch(opc) {
				case 1:
					ClienteBO clienteBO = new ClienteBO();
					EnderecoBO enderecoBO = new EnderecoBO();
					ContaBO contaBO = new ContaBO();
					
					System.out.println("\nCriar Conta");
					scan.nextLine();
					System.out.println("Digite seu nome: ");
					nome = scan.nextLine();
					
					System.out.println("Digite seu cpf: ");
					cpf = scan.next();
					
					//verifica se o cpf possui 11 caracteres e apenas n�meros
					while (!cpf.matches("[0-9]*") || cpf.length()!=11) {
						System.out.println("CPF inv�lido, insira apenas os 11 digitos do CPF: ");
						cpf = scan.next();
					}
					
					//valida��o caso o cpf digitado j� esteja sendo utilizado
					boolean valida = false;
					valida = BancoDeDados.validaContaCPF(cpf);
					
					while(valida) {
						System.out.println("CPF em uso, digite um novo CPF: ");
						cpf = scan.next();
						valida = BancoDeDados.validaContaCPF(cpf);
					}
					
					//data de nascimento
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					System.out.println("Informe a data de nascimento: (dd/MM/yyyy)");
					String data = scan.next();
					Date dataNascimento = null;
					try {
						dataNascimento = sdf.parse(data);
					} catch (ParseException e) {
						System.out.println("Data de nascimento inv�lida");
					}
					
					//leitura dados do endere�o
					System.out.println("\nCadastro Endere�o");
					System.out.println("Digite a rua: ");
					scan.next();
					logradouro = scan.nextLine();
					
					System.out.println("Digite o n�mero: ");
					numero = scan.nextInt();
					
					System.out.println("Digite o CEP: ");
					cep = scan.next();
					
					System.out.println("Digite o bairro: ");
					scan.next();
					bairro = scan.nextLine();
					
					System.out.println("Digite a cidade: ");
					scan.next();
					cidade = scan.nextLine();
					
					System.out.println("Digite o estado: ");
					estado = scan.next();
					
					Endereco ender = enderecoBO.cadastrarEndereco(logradouro, numero, cep, bairro, cidade, estado);
					//cadastra cliente
					Cliente cliente = clienteBO.cadastrarCliente(nome, cpf, dataNascimento, ender);
					
					//leitura da senha da conta do cliente
					System.out.println("\nDigite a senha da sua conta: ");
					senha = scan.next();
					
					//verifica se a senha possui 4 caracteres
					while (senha.length() != 4) {
						System.out.println("Senha inv�lida, a senha deve possuir 4 caracteres. Digite novamente: ");
						senha = scan.next();
					}
					
					//cadastra o cliente na conta
					contaBO.cadastrarConta(cliente, senha);
				break;
				case 2:
					ContaBO contaBOlogin = new ContaBO();
					boolean ver = false;
					System.out.println("\nLogin");
					System.out.println("Digite seu CPF: ");
					String auxCpf = scan.next();
					
					System.out.println("Digite sua senha: ");
					String auxSenha = scan.next();
					
					ver = contaBOlogin.login(auxCpf, auxSenha);
					//System.out.println(ver);
					
					//se ver for igual a true, entra no "menuLogado()", se for false informa que os dados est�o inv�lidos
					if(ver)
						menuLogado(auxCpf);
					else
						System.out.println("Usu�rio ou senha inv�lida!");
						
				break;
				case 3:
					//caso o usuario digite 3, o programa finaliza deixando o continuar como false
					System.out.println("Programa finalizado.");
					continuar = false;
				break;
				default:
					System.out.println("Op��o inv�lida.");
				break;
			}
		}
	}
	
	public static void menuLogado(String cpf) {
		Scanner scan = new Scanner(System.in);
		Scanner scanString = new Scanner(System.in);
		
		ContaBO contaBO = new ContaBO(); 
		ContaPoupancaBO cpBO = new ContaPoupancaBO();
		PixBO pixBO = new PixBO();
		CartaoBO cartaoBO = new CartaoBO();
		
		//armazena a conta logada
		Conta conta = BancoDeDados.buscaContaPorCPF(cpf);

		
		int menu = 0;
		double valor = 0;
		boolean continuar = true;
		
		while(continuar) {
			System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			System.out.println("            LOGADO\n*  - Digite uma op��o: \n1  - Saque\n2  - Depositar dinheiro\n3  - Consultar Saldo"
														+ "\n4  - Transferir\n5  - Aplicar Taxa de manuten��o\n6  - Abrir uma Conta Poupan�a\n7  - Acessar menu da Conta Poupan�a"
														+ "\n8  - Ativar PIX\n9  - Fazer PIX\n10 - Ativar Cart�o de D�bito\n11 - Ativar Cart�o de Cr�dito\n12 - Sair");
			System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			menu = scan.nextInt();

			switch(menu) {
				case 1:
					System.out.println("\nSaque");
					System.out.println("Digite o valor do saque: ");
					valor = scan.nextDouble();
					
					//verifica o valor digitado n�o � um n�mero negativo
					while(valor < 0) {
						System.out.println("Valor inv�lido, digite novamente o valor da transfer�ncia: ");
						valor = scan.nextDouble();
					}
					
					contaBO.sacar(conta, valor);
				break;
				case 2:
					System.out.println("\nDepositar dinheiro");
					System.out.println("Digite o valor do dep�sito: ");
					valor = scan.nextDouble();
					
					//verifica o valor digitado n�o � um n�mero negativo
					while(valor < 0) {
						System.out.println("Valor inv�lido, digite novamente o valor do dep�sito: ");
						valor = scan.nextDouble();
					}
					
					contaBO.depositar(conta, valor);
				break;
				case 3:
					System.out.println("\nConsultar saldo");
					contaBO.consultarSaldoContaCorrente(conta);
				break;
				case 4:
					System.out.println("\nTransferir");
					System.out.println("Digite o n�mero da conta destino: ");
					String numeroConta = scan.next();
					
					Conta contaDestino = BancoDeDados.buscaContaPorNumero(numeroConta);
					
					if(contaDestino != null) {
						System.out.println("Digite o valor da transfer�ncia: ");
						valor = scan.nextDouble();
						
						//verifica o valor digitado n�o � um n�mero negativo
						while(valor < 0) {
							System.out.println("Valor inv�lido, digite novamente o valor do dep�sito: ");
							valor = scan.nextDouble();
						}
						
						contaBO.transferir(conta, contaDestino, valor);
					} else {
						continue;
					}
					
				break;
				case 5:
					System.out.println("\nAplicar taxa de manuten��o");
					contaBO.descontarTaxa(conta);
				break;
				case 6:
					System.out.println("\nCriar Conta Poupan�a");
					
					int opcConta = 0;
					System.out.println("Voc� deseja abrir uma Conta Poupan�a?"
							+ "\nA conta poupan�a tem a vantagem de oferecer um rendimento em cima do valor de saldo, na porcentagem de 0.03%."
							+ "\n1 - Sim\n2 - N�o");
					opcConta = scan.nextInt();
					
					if(opcConta == 1) {
						boolean validacao = false;
						
						//valida se a conta j� foi criada
						ContaPoupanca contaP = conta.getContaPoupanca();
						validacao = cpBO.validaContaPoupanca(contaP);
						if(!validacao)
							contaBO.cadastraContaPoupanca(conta);
						else
							System.out.println("Sua Conta Poupan�a j� est� ativada.");
					} else {
						System.out.println("Voltando ao menu");
					}
				break;
				case 7:
					System.out.println("\nAcessar menu Conta Poupan�a");
					boolean validacao = false;
					
					//valida se a conta j� foi criada
					ContaPoupanca contaP = conta.getContaPoupanca();
					validacao = cpBO.validaContaPoupanca(contaP);
					if(validacao) {
						System.out.println("\nAcessando menu Conta Poupan�a!");
						menuContaPoupanca(contaP, conta);
					} else {
						System.out.println("\nSua Conta Poupan�a est� desativada!");
					}
				break;
				case 8:
					System.out.println("\nAtivar PIX");
					
					Pix pix = conta.getPix();
					
					if(!pixBO.validaPix(pix)) {
						String chave = "";
						System.out.println("Digite a op��o do tipo de chave que voc� deseja cadastrar: ");
						System.out.println("1 - CPF\n2 - EMAIL\n3 - TELEFONE\n4 - CHAVE ALEAT�RIA");
						int opcChave = scan.nextInt();
						
						while(opcChave < 1 && opcChave > 4) {
							System.out.println("Op��o inv�lida, digite novamente a op��o do tipo de chave que voc� deseja cadastrar: ");
							System.out.println("1 - CPF\n2 - EMAIL\n3 - TELEFONE\n4 - CHAVE ALEAT�RIA");
							opcChave = scan.nextInt();
						}
						
						if(opcChave == 4) {
							chave = UUID.randomUUID().toString();
						} else {
							System.out.println("Digite a chave do pix: ");
							chave = scan.next();
						}
						pixBO.cadastrarPix(conta, opcChave, chave);
					}else {
						System.out.println("Seu pix j� est� ativado.");
					}
				break;
				case 9:
					System.out.println("Fazer PIX");
					Pix pixValida = conta.getPix();
					
					//valida se o pix j� est� ativado
					if(pixBO.validaPix(pixValida)) {
						int opcPix = 0;
						boolean valida = false;
						
						System.out.println("Digite a chave do pix: ");
						String chave = scan.next();
						
						valida = BancoDeDados.validaPix(chave);
						
						//valida se a chave digita est� cadastrada em alguma conta
						if(valida) {
							//armazena a conta destino buscando pela chave do pix
							Conta cd = BancoDeDados.buscaPix(chave);
							
							System.out.println("Digite o valor do pix: ");
							valor = scan.nextDouble();
							
							//verifica o valor digitado n�o � um n�mero negativo
							while(valor < 0) {
								System.out.println("Valor inv�lido, digite novamente o valor do dep�sito: ");
								valor = scan.nextDouble();
							}
							
							pixBO.pagarPix(conta, cd, valor);
						} else {
							System.out.println("\nChave n�o encontrada!");
						}
						
					} else {
						System.out.println("\nO seu pix est� desativado!");
					}
					
				break;
				case 10:
					System.out.println("\nAtivar Cart�o de D�bito");
					
					boolean validaCD = false;
					validaCD = cartaoBO.validaCartaoDebito(conta);
					
					if(!validaCD) {
						System.out.println("Digite o limite de transa��o: ");
						double limite = scan.nextDouble();
						
						System.out.println("Digite a senha do cart�o: ");
						String senha = scan.next();
						
						System.out.println("Digite o tipo de bandeira que voc� deseja: ");
						System.out.println("1 - VISA\n2 - MASTERCARD\n3 - ELO");
						int bandeira = scan.nextInt();
						
						while(bandeira < 1 && bandeira > 3) {
							System.out.println("Op��o inv�lida, digite novamente o tipo de bandeira que voc� deseja: ");
							System.out.println("1 - VISA\n2 - MASTERCARD\n3 - ELO");
							bandeira = scan.nextInt();
						}
						cartaoBO.cadastraCartaoDebito(conta, bandeira, limite, senha);
					} else {
						System.out.println("\nO seu cart�o j� est� ativado!");
					}
					break;
				case 11:
					System.out.println("\nAtivar Cart�o de Cr�dito");
					
					boolean validaCC = false;
					validaCC = cartaoBO.validaCartaoCredito(conta);
					
					if(!validaCC) {
						TipoCliente tp = conta.getCliente().getTipoCliente();
						double limite = 0;
						
						if(tp == TipoCliente.COMUM)
							limite = 1000;
						else if(tp == TipoCliente.PREMIUM)
							limite = 5000;
						else
							limite = 12000;
						
						System.out.println("\nO limite do seu cart�o � de: R$" + limite);
						System.out.println("Deseja prosseguir na ativa��o do cart�o de cr�dito?\n1 - Sim\n2 - N�o");
						int opcProsseguir = scan.nextInt();
						
						if(opcProsseguir == 1) {
							System.out.println("Digite a senha do cart�o: ");
							String senha = scan.next();
							
							System.out.println("Digite o tipo de bandeira que voc� deseja: ");
							System.out.println("1 - VISA\n2 - MASTERCARD\n3 - ELO");
							int bandeira = scan.nextInt();
							
							while(bandeira < 1 && bandeira > 3) {
								System.out.println("Op��o inv�lida, digite novamente o tipo de bandeira que voc� deseja: ");
								System.out.println("1 - VISA\n2 - MASTERCARD\n3 - ELO");
								bandeira = scan.nextInt();
							}
							cartaoBO.cadastraCartaoCredito(conta, bandeira, limite, senha);
						} else {
							System.out.println("\nVoltando para o menu");
							continue;
						}
					} else {
						System.out.println("\nO seu cart�o j� est� ativado!");
					}
					break;
				case 12:
					//deixa continuar como false e desloga da conta
					System.out.println("Deslogando!");
					continuar = false;
				break;
				default:
					System.out.println("Op��o inv�lida");
				break;
			}
		}
	}
	
	public static void menuContaPoupanca(ContaPoupanca contaPoup, Conta conta) {
		Scanner scan = new Scanner(System.in);
		Scanner scanString = new Scanner(System.in);
		ContaPoupancaBO cpb = new ContaPoupancaBO();
		
		int menu = 0;
		double valor = 0;
		boolean continuar = true;
		
		while(continuar) {
			System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			System.out.println("        MENU CONTA POUPAN�A\n* - Digite uma op��o: \n1 - Saque\n2 - Depositar dinheiro\n3 - Consultar Saldo"
														+ "\n4 - Transferir para Conta Corrente\n5 - Transferir para outro tipo de conta\n6 - Aplicar rendimento\n7 - Sair");
			System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			menu = scan.nextInt();

			switch(menu) {
				case 1:
					System.out.println("\nSaque");
					System.out.println("Digite o valor do saque: ");
					valor = scan.nextDouble();
					
					while(valor < 0) {
						System.out.println("Valor inv�lido, digite novamente o valor da transfer�ncia: ");
						valor = scan.nextDouble();
					}
					
					cpb.sacar(contaPoup, valor);
				break;
				case 2:
					System.out.println("\nDepositar dinheiro");
					System.out.println("Digite o valor do dep�sito: ");
					valor = scan.nextDouble();
					
					while(valor < 0) {
						System.out.println("Valor inv�lido, digite novamente o valor do dep�sito: ");
						valor = scan.nextDouble();
					}
					
					cpb.depositar(contaPoup, valor);
				break;
				case 3:
					System.out.println("\nConsultar saldo");
					cpb.consultarSaldoContaPoupanca(contaPoup);
				break;
				case 4:
					System.out.println("\nTransferir para Conta Corrente");
					System.out.println("Digite o valor da transfer�ncia: ");
					valor = scan.nextDouble();
					
					while(valor < 0) {
						System.out.println("Valor inv�lido, digite novamente o valor do dep�sito: ");
						valor = scan.nextDouble();
					}
					
					cpb.transferir(contaPoup, conta, valor);
				break;
				case 5:
					System.out.println("\nTransferir para outro tipo de conta");
					System.out.println("Digite o n�mero da conta destino: ");
					String numeroConta = scan.next();
					
					Conta contaDestino = BancoDeDados.buscaContaPorNumero(numeroConta);
					
					//valida se o numero da conta digita pelo usuario existe no sistema, se existir continua com a transa��o
					if(contaDestino != null) {
						System.out.println("Digite o valor da transfer�ncia: ");
						valor = scan.nextDouble();
						
						//verifica o valor digitado n�o � um n�mero negativo
						while(valor < 0) {
							System.out.println("Valor inv�lido, digite novamente o valor do dep�sito: ");
							valor = scan.nextDouble();
						}
						
						cpb.transferir(contaPoup, contaDestino, valor);
					} else {
						continue;
					}
					
					break;
				case 6:
					System.out.println("\nAplicar rendimento");
					cpb.aplicarRendimento(contaPoup);
					break;
				case 7:
					System.out.println("\nIr para o menu da Conta Corrente!");
					continuar = false;
				break;
				default:
					System.out.println("\nOp��o inv�lida");
				break;
			}
		}
	}
}
