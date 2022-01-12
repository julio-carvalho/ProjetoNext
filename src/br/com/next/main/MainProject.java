package br.com.next.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import br.com.next.bean.Cliente;
import br.com.next.bean.Conta;
import br.com.next.bean.Endereco;
import br.com.next.bo.ClienteBO;
import br.com.next.bo.ContaBO;
import br.com.next.bo.EnderecoBO;
import br.com.next.utils.BancoDeDados;

public class MainProject {
	ClienteBO clienteBo = new ClienteBO();
	
	public static void main(String[] args) {
		menuPrincipal();
	}
	
	public static void menuPrincipal() {
		Scanner scan = new Scanner(System.in);
		Scanner scanInt = new Scanner(System.in);
		int opc = 0, numero;
		boolean continuar = true;
		String nome, cpf = " ", senha, logradouro, cep, bairro, cidade, estado;
		
		//enquanto continuar for true, roda o menu
		while(continuar) {
			//opçoes do menu principal, usuario nao consegue logar sem criar conta
			System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			System.out.println("            MENU\n* - Digite uma opção:\n1 - Criar conta\n2 - Logar\n3 - Sair");
			System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
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
					
					//verifica se o cpf possui 11 caracteres e apenas números
					while (!cpf.matches("[0-9]*") || cpf.length()!=11) {
						System.out.println("Insira apenas os 11 digitos do CPF");
						cpf = scan.next();
					}
					
					//data de nascimento
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					System.out.println("Informe a data de nascimento: (dd/MM/yyyy)");
					String data = scan.next();
					Date dataNascimento = null;
					try {
						dataNascimento = sdf.parse(data);
					} catch (ParseException e) {
						System.out.println("Data de nascimento inválida");
					}
					
					//leitura dados do endereço
					System.out.println("\nCadastro Endereço");
					System.out.println("Digite a rua: ");
					scan.next();
					logradouro = scan.nextLine();
					
					System.out.println("Digite o número: ");
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
						System.out.println("Senha inválida, a senha deve possuir 4 caracteres. Digite novamente: ");
						senha = scan.next();
					}
					
					//cadastra o cliente na conta
					contaBO.cadastrarConta(cliente, 1, senha);
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
					System.out.println(ver);
					
					//se ver for igual a true, entra no "menuLogado()", se for false informa que os dados estão inválidos
					if(ver) {
						System.out.println("Logado!");
						menuLogado(auxCpf);
					} else {
						System.out.println("Usuário ou senha inválida!");
					}
				break;
				case 3:
					//caso o usuario digite 3, o programa finaliza deixando o continuar como false
					System.out.println("Programa finalizado.");
					continuar = false;
				break;
				default:
					System.out.println("Opção inválida.");
				break;
			}
		}
	}
	
	public static void menuLogado(String cpf) {
		Scanner scan = new Scanner(System.in);
		Scanner scanString = new Scanner(System.in);
		Conta conta = BancoDeDados.buscarContaPorCPF(cpf);
		ContaBO contaBO = new ContaBO(); 
		int menu = 0;
		double valor = 0;
		boolean continuar = true;
		
		while(continuar) {
			System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			System.out.println("            LOGADO\n*  - Digite uma opção: \n1  - Saque\n2  - Depositar dinheiro\n3  - Consultar Saldo"
														+ "\n4  - Transferir\n5  - Aplicar Taxa de manutenção\n6  - Abrir uma Conta Poupança\n7  - Acessar menu da Conta Poupança"
														+ "\n8  - Ativar PIX\n9  - Fazer PIX\n10 - Sair");
			System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			menu = scan.nextInt();

			switch(menu) {
				case 1:
					System.out.println("\nSaque");
					System.out.println("Digite o valor do saque: ");
					valor = scan.nextDouble();
					
					//verifica o valor digitado não é um número negativo
					while(valor < 0) {
						System.out.println("Valor inválido, digite novamente o valor da transferência: ");
						valor = scan.nextDouble();
					}
					
					//contaCorrente.saque(valor, auxCpf);
				break;
				case 2:
					System.out.println("\nDepositar dinheiro");
					System.out.println("Digite o valor do depósito: ");
					valor = scan.nextDouble();
					
					//verifica o valor digitado não é um número negativo
					while(valor < 0) {
						System.out.println("Valor inválido, digite novamente o valor do depósito: ");
						valor = scan.nextDouble();
					}
					
					contaBO.depositar(conta, valor);
				break;
				case 3:
					System.out.println("\nConsultar saldo");
					contaBO.consultarSaldoContaCorrente(conta);
				break;
				case 4:
					String cpfDestino = " ";
					System.out.println("\nTransferir");
					System.out.println("Digite o valor da transferência: ");
					valor = scan.nextDouble();
					
					//verifica o valor digitado não é um número negativo
					while(valor < 0) {
						System.out.println("Valor inválido, digite novamente o valor do depósito: ");
						valor = scan.nextDouble();
					}
					
					System.out.println("Digite o CPF destino: ");
					cpfDestino = scanString.next();
					
					//verifica se o cpf possui 11 caracteres e apenas números
					while (!cpfDestino.matches("[0-9]*") || cpfDestino.length()!=11) {
						System.out.println("Insira apenas os 11 digitos do CPF: ");
						cpfDestino = scan.next();
					}
					
					boolean var = false;
					var = contaBO.valida2(cpfDestino);
					System.out.println(var);
					
					//validação
					if(var) {
						Conta contaDestino = BancoDeDados.buscarContaPorCPF(cpfDestino);
						contaBO.transferir(conta, contaDestino, valor);
					} else {
						System.out.println("Conta não encontrada!");
					}
				break;
				case 5:
					System.out.println("\nAplicar taxa de manutenção");
					//contaCorrente.descontarTaxa();
				break;
				case 6:
					int opcConta = 0;
					System.out.println("Criar Conta Poupança");
					System.out.println("Você deseja abrir uma Conta Poupança?"
							+ "\nA conta poupança tem a vantagem de oferecer um rendimento em cima do valor de saldo, na porcentagem de 0.03%."
							+ "\n1 - Sim\n2 - Não");
					opcConta = scan.nextInt();
					
					if(opcConta == 1) {
						/*valida se a conta já foi criada
						if(!contaPoupanca.validaConta())
							contaPoupanca.cadastrarCP(clienteBo, auxCpf);
						else
							System.out.println("Sua Conta Poupança já está ativada.");*/
					} else {
						System.out.println("Voltando ao menu");
					}
				break;
				case 7:
					/*System.out.println("\nAcessar menu Conta Poupança");
					
					//valida se a conta poupança foi criada, se for true entra no menu da conta poupança
					if(contaPoupanca.validaConta()) {
						System.out.println("\nAcessando menu Conta Poupança!");
						menuContaPoupanca(auxCpf, clienteBo);
					} else {
						System.out.println("\nSua Conta Poupança está desativada!");
					}*/
				break;
				case 8:
					/*
					System.out.println("Ativar PIX");
					if(!pix.validaPix())
						pix.ativarPix();
					else
						System.out.println("Seu pix já está ativado.");
					*/
				break;
				case 9:
					/*
					System.out.println("Fazer PIX");
					
					//valida se o pix já está ativado
					if(pix.validaPix()) {
						int opcPix = 0;
						System.out.println("Digite o tipo de método que você deseja pagar com o pix");
						System.out.println("1 - Email\n2 - CPF\n3 - Telefone\n4 - Chave aleatória");
						opcPix = scan.nextInt();
						
						while(opcPix < 1 && opcPix > 4) {
							System.out.println("Opção inválida! Digite novamente o tipo de método que você deseja pagar com o pix");
							System.out.println("1 - Email\n2 - CPF\n3 - Telefone\n4 - Chave aleatória");
							opcPix = scan.nextInt();
						}
						
						System.out.println("Digite o valor do pix: ");
						valor = scan.nextDouble();
						
						//verifica o valor digitado não é um número negativo
						while(valor < 0) {
							System.out.println("Valor inválido, digite novamente o valor do depósito: ");
							valor = scan.nextDouble();
						}
						
						pix.pagarPix(opcPix, valor, contaCorrente);
					} else {
						System.out.println("\nO seu pix está desativado!");
					}
					*/
				break;
				case 10:
					//deixa continuar como false e desloga da conta
					System.out.println("Deslogando!");
					continuar = false;
				break;
				default:
					System.out.println("Opção inválida");
				break;
			}
		}
	}
}
