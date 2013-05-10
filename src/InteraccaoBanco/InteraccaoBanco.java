package InteraccaoBanco;

import java.util.ArrayList;
import java.util.Scanner;

import Banco.Banco;
import Cliente.Cliente;
import Contas.Conta;

public abstract class InteraccaoBanco {

	protected static Cliente cli; // À medida que os menus avançam o cliente a
									// ser usado é guardado nesta variavel
	protected static Conta con;// À medida que os menus avançam a conta a ser
								// usada é guardada nesta variavel

	/**
	 * Este metodo está em repetição a mostrar o menu de operações disponiveis
	 * numa conta. Para cada uma das opções existentes e através de um switch
	 * solicita a informação necessaria ao utilizador e invoca os metodos
	 * correspondentes.
	 */
	protected static void processaMenuConta() {
		// implementar o codigo deste método
		Scanner teclado = new Scanner(System.in);
		int opcao = 0;
		double valor = 0;

		while (opcao != 7) {

			System.out.println("Menu Operações Conta\n");
			System.out.println("1 -> Levantar");
			System.out.println("2 -> Depositar");
			System.out.println("3 -> Transferir");
			System.out.println("4 -> Obter Extracto");
			System.out.println("5 -> Obter Saldo");
			System.out.println("6 -> Obter informações");
			System.out.println("7 -> Sair do Menu");

			opcao = teclado.nextInt();

			switch (opcao) {

			case 1:
				System.out.println("Quanto dinheiro quer levantar?");
				valor = teclado.nextDouble();
				boolean levantou = con.levantar(valor);

				if (!levantou) {
					System.out
							.println("Não tem saldo para levantar o valor introduzido");
				}
				break;
			case 2:
				System.out.println("Quanto dinheiro quer depositar");
				valor = teclado.nextDouble();
				con.depositar(valor);
				break;
			case 3:
				System.out
						.println("Qual o nib da conta que pretende transferir?");
				int nib = teclado.nextInt();
				Conta contadestino = Banco.obterConta(nib);
				if (contadestino == null) {

					System.out.println("A conta não existe!");
					break;

				}
				System.out.println("Quanto dinheiro quer transferir?");
				valor = teclado.nextDouble();
				boolean transferiu = con.transferir(valor, contadestino);

				if (!transferiu) {
					System.out
							.println("Não tem saldo para levantar o valor introduzido");
				}
				break;
			case 4:
				con.mostrarExtracto();
				break;
			case 5:
				con.mostrarSaldo();
				break;
			case 6:
				System.out.println(con.mostrarInformacoes());
				break;
			case 7:
				break;
			default:
				System.out.println("Opção errada!");
				break;
			}
		}
	}

	/**
	 * Este metodo está em repetição a mostrar o menu de contas disponiveis do
	 * cliente. De notar que APENAS AS CONTAS ACTIVAS são mostradas. Após ser
	 * seleccionada uma conta é invocado o metodo processaMenuConta referente à
	 * conta escolhida
	 * 
	 * @param contascliente
	 *            Cliente sobre o qual se quer visualizar as contas
	 */
	protected static void processaMenuContas(ArrayList<Conta> contascliente) {
		// implementar o codigo deste método
		Scanner teclado = new Scanner(System.in);
		int opcao = 0;

		while (opcao != contascliente.size() + 1) {

			System.out.println("Menu Contas\n");

			for (int i = 0; i < contascliente.size(); i++) {

				System.out.println((i + 1) + " -> "
						+ contascliente.get(i).mostrar() + " "
						+ contascliente.get(i).estaActiva());

			}

			System.out.println((contascliente.size() + 1) + " -> Sair do Menu");

			opcao = teclado.nextInt();

			if (opcao <= contascliente.size() && opcao != 0) {
				con = contascliente.get(opcao - 1);
				if (con.estaActiva() == true) {
					processaMenuConta();
				} else {

					System.out.println("Não é possível usar a conta!");

				}
			} else if (opcao == contascliente.size() + 1) {
				break;
			} else {
				System.out.println("Opção Errada!");
			}
		}

	}

	public static void main(String[] args) {

		Banco.iniciar();
		Cliente c = Banco.procurarCliente(111);
		cli = c;

		processaMenuContas(c.obterContas());
	}

	public static void escreverFicheiro() {
	}
}
