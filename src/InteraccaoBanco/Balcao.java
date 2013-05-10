package InteraccaoBanco;

import java.util.ArrayList;
import java.util.Scanner;

import Banco.Banco;
import Cliente.Cliente;
import Contas.Conta;

public class Balcao extends InteraccaoBanco {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Banco.iniciar();

		Scanner teclado = new Scanner(System.in);
		int opcao = 0;

		while (opcao != 7) {

			System.out.println("Menu Principal\n");
			System.out.println("1 -> Criar Cliente");
			System.out.println("2 -> Desactivar Cliente");
			System.out.println("3 -> Criar Conta");
			System.out.println("4 -> Desactivar Conta");
			System.out.println("5 -> Listar Clientes");
			System.out.println("6 -> Operacoes sobre Cliente");
			System.out.println("7 -> Sair");

			opcao = teclado.nextInt();

			switch (opcao) {
			case 1:
				teclado.nextLine();
				System.out.println("Insira o nome do novo cliente");
				String nome = teclado.nextLine();
				System.out.println("Insira a password do cliente");
				int password = teclado.nextInt();
				int userid = Banco.gerarNumCliente();
				Banco.criarCliente(nome, userid, password);
				System.out.println("O id do cliente " + nome + " é " + userid);
				break;
			case 2:
				System.out.println("Insira o id do cliente a desactivar");
				int idcliente = teclado.nextInt();
				if (Banco.desactivarCliente(idcliente) == false) {

					System.out.println("O cliente não existe");

				} else {
					System.out.println("O cliente com o id " + idcliente
							+ " está desactivado!");
				}
				break;
			case 3:
				System.out.println("Insira o id do cliente a operar");
				int clienteid = teclado.nextInt();
				System.out
						.println("Insira o tipo de conta a criar \n 1. Prazo \n 2. Debito");
				int tipoconta = teclado.nextInt();
				if (tipoconta > 0 && tipoconta < 3) {

					Cliente c = Banco.procurarCliente(clienteid);

					if (c == null) {
						System.out.println("O cliente não existe!");
					} else {
						Banco.criarConta(c, tipoconta);
					}

				} else {

					System.out.println("Tipo de conta inválido!");

				}
				break;
			case 4:
				System.out.println("Insira o nib da conta a desactivar");
				int nib = teclado.nextInt();
				Conta c = Banco.obterConta(nib);
				if (c != null) {
					c.desactivar();

					System.out.println("A conta " + c.obterNib()
							+ " está desactivada!");
				} else {

					System.out.println("A conta não existe!");

				}
				break;
			case 5:
				System.out
						.println("Insira o nome do cliente a listar(Enter para listar todos)");
				String criterionome = teclado.nextLine();
				Banco.listarClientes(criterionome);

				break;
			case 6:
				System.out.println("Insira o id do cliente a operar");
				int cliente = teclado.nextInt();
				cli = Banco.procurarCliente(cliente);
				if (cli != null) {
					if (cli.obterActivacao() == false) {
						System.out.println("O cliente está desactivo");
					} else {

						processaMenuContas(cli.obterContas());
					}
				} else {
					System.out.println("O cliente não existe!");
				}
				break;
			case 7:
				break;
			}

		}

		Banco.guardarDados();

	}
}
