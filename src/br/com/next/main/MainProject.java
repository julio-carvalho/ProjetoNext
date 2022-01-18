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
import br.com.next.utils.Menu;

public class MainProject {
	
	static Menu menu = new Menu();
	
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
			menu.menuPrincipal();
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
					
					System.out.println("Digite seu CPF: ");
					cpf = scan.next();
					
					//verifica se o cpf possui 11 caracteres e apenas números
					while (!cpf.matches("[0-9]*") || cpf.length()!=11) {
						System.out.println("CPF inválido, insira apenas os 11 digitos do CPF: ");
						cpf = scan.next();
					}
					
					//validação caso o cpf digitado já esteja sendo utilizado
					boolean valida = false;
					valida = BancoDeDados.validaContaCPF(cpf);
					
					while(valida) {
						System.out.println("CPF em uso, digite um novo CPF: ");
						cpf = scan.next();
						valida = BancoDeDados.validaContaCPF(cpf);
					}
					
					//data de nascimento
					boolean verificaData = false;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date dataNascimento = null;

					while(!verificaData) {
						
						System.out.println("Informe a data de nascimento: (dd/MM/yyyy)");
						String data = scan.next();
						
						try {
							dataNascimento = sdf.parse(data);
							verificaData = true;
						} catch (ParseException e) {
							System.out.println("Data de nascimento inválida.");
							verificaData = false;
						}
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
					
					//se ver for igual a true, entra no "menuLogado()", se for false informa que os dados estão inválidos
					if(ver)
						menuLogado(auxCpf);
					else
						System.out.println("Usuário ou senha inválida!");
						
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
		
		ContaBO contaBO = new ContaBO(); 
		ContaPoupancaBO cpBO = new ContaPoupancaBO();
		PixBO pixBO = new PixBO();
		CartaoBO cartaoBO = new CartaoBO();
		
		//armazena a conta logada
		Conta conta = BancoDeDados.buscaContaPorCPF(cpf);

		
		int opcMenu = 0;
		double valor = 0;
		boolean continuar = true, valida = false;
		
		while(continuar) {
			menu.menuLogado();
			opcMenu = scan.nextInt();

			switch(opcMenu) {
				case 1:
					System.out.println("\nSaque");
					System.out.println("Digite o valor do saque: ");
					valor = scan.nextDouble();
					
					//verifica o valor digitado não é um número negativo
					while(valor < 0) {
						System.out.println("Valor inválido, digite novamente o valor da transferência: ");
						valor = scan.nextDouble();
					}
					
					contaBO.sacar(conta, valor);
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
					System.out.println("\nTransferir");
					System.out.println("Digite o número da conta destino: ");
					String numeroConta = scan.next();
					
					Conta contaDestino = BancoDeDados.buscaContaPorNumero(numeroConta);
					
					if(contaDestino != null) {
						System.out.println("Digite o valor da transferência: ");
						valor = scan.nextDouble();
						
						//verifica o valor digitado não é um número negativo
						while(valor < 0) {
							System.out.println("Valor inválido, digite novamente o valor do depósito: ");
							valor = scan.nextDouble();
						}
						
						contaBO.transferir(conta, contaDestino, valor);
					} else {
						continue;
					}
					
				break;
				case 5:
					System.out.println("\nAplicar taxa de manutenção");
					contaBO.descontarTaxa(conta);
				break;
				case 6:
					System.out.println("\nCriar Conta Poupança");
					
					if(!conta.getContaPoupanca().isAtivado()) {
						int opcConta = 0;
						System.out.println("Você deseja abrir uma Conta Poupança?"
								+ "\nA conta poupança tem a vantagem de oferecer um rendimento em cima do valor de saldo, na porcentagem de 0.03%."
								+ "\n1 - Sim\n2 - Não");
						opcConta = scan.nextInt();
						
						if(opcConta == 1) {
							//cadastrando conta poupança	
							contaBO.cadastraContaPoupanca(conta);
						} else {
							System.out.println("Voltando ao menu");
							continue;
						}
					} else {
						System.out.println("Sua Conta Poupança já está ativada.");
					}
				break;
				case 7:
					System.out.println("\nAcessar menu Conta Poupança");
					//valida se a conta poupança está ativada
					if(conta.getContaPoupanca().isAtivado())
						menuContaPoupanca(conta.getContaPoupanca(), conta);
					else
						System.out.println("\nSua Conta Poupança está desativada!");
					
				break;
				case 8:
					System.out.println("\nAtivar PIX");
					
					//valida se o pix já está ativado
					if(!conta.getPix().isAtivado()) {
						String chave = "";
						System.out.println("Digite a opção do tipo de chave que você deseja cadastrar: ");
						System.out.println("1 - CPF\n2 - EMAIL\n3 - TELEFONE\n4 - CHAVE ALEATÓRIA");
						int opcChave = scan.nextInt();
						
						while(opcChave < 1 && opcChave > 4) {
							System.out.println("Opção inválida, digite novamente a opção do tipo de chave que você deseja cadastrar: ");
							System.out.println("1 - CPF\n2 - EMAIL\n3 - TELEFONE\n4 - CHAVE ALEATÓRIA");
							opcChave = scan.nextInt();
						}
						
						if(opcChave == 4) {
							chave = UUID.randomUUID().toString();
						} else {
							System.out.println("Digite a chave do pix: ");
							chave = scan.next();
						}
						pixBO.cadastrarPix(conta, opcChave, chave);
					} else {
						System.out.println("Seu pix já está ativado.");
					}
				break;
				case 9:
					System.out.println("Fazer PIX");
					
					//valida se o pix está ativado
					if(conta.getPix().isAtivado()) {
						int opcPix = 0;
						valida = false;
						
						System.out.println("Digite a chave do pix: ");
						String chave = scan.next();
						
						valida = BancoDeDados.validaPix(chave);
						
						//valida se a chave digita está cadastrada em alguma conta
						if(valida) {
							//armazena a conta destino buscando pela chave do pix
							Conta cd = BancoDeDados.buscaPix(chave);
							
							System.out.println("Digite o valor do pix: ");
							valor = scan.nextDouble();
							
							//verifica o valor digitado não é um número negativo
							while(valor < 0) {
								System.out.println("Valor inválido, digite novamente o valor do depósito: ");
								valor = scan.nextDouble();
							}
							
							pixBO.pagarPix(conta, cd, valor);
						} else {
							System.out.println("\nChave não encontrada!");
						}
						
					} else {
						System.out.println("\nO seu pix está desativado!");
					}
					
				break;
				case 10:
					System.out.println("\nAtivar Cartão de Débito");
					if(!conta.getCartaoDebito().isAtivo()) {
						System.out.println("Digite o limite de transação: ");
						double limite = scan.nextDouble();
						
						System.out.println("Digite a senha do cartão: ");
						String senha = scan.next();
						
						//verifica se a senha possui 4 números
						while (!senha.matches("[0-9]*") || senha.length() != 4) {
							System.out.println("Senha inválida, a senha deve possuir 4 números. Digite novamente: ");
							senha = scan.next();
						}
						
						System.out.println("Digite o tipo de bandeira que você deseja: ");
						System.out.println("1 - VISA\n2 - MASTERCARD\n3 - ELO");
						int bandeira = scan.nextInt();
						
						while(bandeira < 1 || bandeira > 3) {
							System.out.println("Opção inválida, digite novamente o tipo de bandeira que você deseja: ");
							System.out.println("1 - VISA\n2 - MASTERCARD\n3 - ELO");
							bandeira = scan.nextInt();
						}
						cartaoBO.cadastraCartaoDebito(conta, bandeira, limite, senha);
					} else {
						System.out.println("\nO seu cartão já está ativado!");
					}
					break;
				case 11:
					System.out.println("\nDesativar Cartão Débito");
					if(conta.getCartaoDebito().isAtivo()) {
						cartaoBO.desativaCartao(conta, 1);
					} else {
						System.out.println("\nO cartão de débito já está desativado");
					}
					break;
				case 12:
					System.out.println("\nRealizar compra com débito");	
					if(conta.getCartaoDebito().isAtivo()) {
						System.out.println("Digite a senha do cartão: ");
						String senha = scan.next();
						
						if(senha.equalsIgnoreCase(conta.getCartaoDebito().getSenha())) {
							System.out.println("Digite o valor da compra: ");
							valor = scan.nextDouble();
							
							cartaoBO.comprarCartaoDebito(conta, valor);
						} else {
							System.out.println("\nSenha inválida");
							continue;
						}
					} else {
						System.out.println("\nO cartão de débito está desativado!");
					}
					break;
				case 13:
					System.out.println("\nAtivar Cartão de Crédito");
					if(!conta.getCartaoCredito().isAtivo()) {
						TipoCliente tp = conta.getCliente().getTipoCliente();
						double limite = 0;
						
						if(tp == TipoCliente.COMUM)
							limite = 1000;
						else if(tp == TipoCliente.PREMIUM)
							limite = 5000;
						else
							limite = 12000;
						
						System.out.println("\nO limite do seu cartão é de: R$" + limite);
						System.out.println("Deseja prosseguir na ativação do cartão de crédito?\n1 - Sim\n2 - Não");
						int opcProsseguir = scan.nextInt();
						
						if(opcProsseguir == 1) {
							System.out.println("Digite a senha do cartão: ");
							String senha = scan.next();
							
							//verifica se a senha possui 4 números
							while (!senha.matches("[0-9]*") || senha.length() != 4) {
								System.out.println("Senha inválida, a senha deve possuir 4 números. Digite novamente: ");
								senha = scan.next();
							}
							
							System.out.println("Digite o tipo de bandeira que você deseja: ");
							System.out.println("1 - VISA\n2 - MASTERCARD\n3 - ELO");
							int bandeira = scan.nextInt();
							
							while(bandeira < 1 || bandeira > 3) {
								System.out.println("Opção inválida, digite novamente o tipo de bandeira que você deseja: ");
								System.out.println("1 - VISA\n2 - MASTERCARD\n3 - ELO");
								bandeira = scan.nextInt();
							}
							
							menu.menuDiaVencimento();
							int dia = scan.nextInt();
							
							while(dia < 1 || dia > 5) {
								System.out.println("Opção inválida.");
								menu.menuDiaVencimento();
								dia = scan.nextInt();
							}
							
							cartaoBO.cadastraCartaoCredito(conta, bandeira, limite, senha, dia);
						} else {
							System.out.println("\nVoltando para o menu");
							continue;
						}
					} else {
						System.out.println("\nO seu cartão já está ativado!");
					}
					break;
				case 14:
					System.out.println("\nDesativar cartão crédito");
					if(conta.getCartaoCredito().isAtivo()) {
						cartaoBO.desativaCartao(conta, 2);
					} else {
						System.out.println("\nO cartão de crédito já está desativado");
					}
					break;
				case 15:
					System.out.println("\nRealizar compra com crédito");
					if(conta.getCartaoCredito().isAtivo()) {
						System.out.println("Digite a senha do cartão: ");
						String senha = scan.next();
						
						if(senha.equalsIgnoreCase(conta.getCartaoCredito().getSenha())) {
							System.out.println("Digite o valor da compra: ");
							valor = scan.nextDouble();
							
							cartaoBO.comprarCartaoCredito(conta, valor);
						} else {
							System.out.println("\nSenha inválida");
							continue;
						}
					} else {
						System.out.println("\nO cartão de crédito está desativado!");
					}
					break;
				case 16:
					System.out.println("\nExibir Fatura");
					if(conta.getCartaoCredito().isAtivo())
						cartaoBO.exibirFatura(conta);
					else
						System.out.println("\nO cartão de crédito está desativado!");
					
				break;
				case 17:
					System.out.println("\nPagar fatura do cartão de crédito");
					
					if(conta.getCartaoCredito().isAtivo()) {
						System.out.println("A fatura atual é : R$" + conta.getCartaoCredito().getFatura());
						System.out.println("Dseja pagar a fatura usando o seu saldo atual?\n1 - Sim\n2 - Não");
						int opcFatura = scan.nextInt();
						
						if(opcFatura == 1)
							cartaoBO.pagarFatura(conta);
						else
							continue;
						
					} else {
						System.out.println("\nO cartão de crédito está desativado!");
					}

				break;
				case 18:
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
	
	public static void menuContaPoupanca(ContaPoupanca contaPoup, Conta conta) {
		Scanner scan = new Scanner(System.in);
		Scanner scanString = new Scanner(System.in);
		ContaPoupancaBO cpb = new ContaPoupancaBO();
		
		int opcMenu = 0;
		double valor = 0;
		boolean continuar = true;
		
		while(continuar) {
			menu.menuContaPoupanca();
			opcMenu = scan.nextInt();

			switch(opcMenu) {
				case 1:
					System.out.println("\nSaque");
					System.out.println("Digite o valor do saque: ");
					valor = scan.nextDouble();
					
					while(valor < 0) {
						System.out.println("Valor inválido, digite novamente o valor da transferência: ");
						valor = scan.nextDouble();
					}
					
					cpb.sacar(contaPoup, valor);
				break;
				case 2:
					System.out.println("\nDepositar dinheiro");
					System.out.println("Digite o valor do depósito: ");
					valor = scan.nextDouble();
					
					while(valor < 0) {
						System.out.println("Valor inválido, digite novamente o valor do depósito: ");
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
					System.out.println("Digite o valor da transferência: ");
					valor = scan.nextDouble();
					
					while(valor < 0) {
						System.out.println("Valor inválido, digite novamente o valor do depósito: ");
						valor = scan.nextDouble();
					}
										
					cpb.transferir(contaPoup, conta, valor);
				break;
				case 5:
					System.out.println("\nTransferir para outro tipo de conta");
					System.out.println("Digite o número da conta destino: ");
					String numeroConta = scan.next();
					
					Conta contaDestino = BancoDeDados.buscaContaPorNumero(numeroConta);
					
					//valida se o numero da conta digita pelo usuario existe no sistema, se existir continua com a transação
					if(contaDestino != null) {
						System.out.println("Digite o valor da transferência: ");
						valor = scan.nextDouble();
						
						//verifica o valor digitado não é um número negativo
						while(valor < 0) {
							System.out.println("Valor inválido, digite novamente o valor do depósito: ");
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
					System.out.println("\nOpção inválida");
				break;
			}
		}
	}
}
