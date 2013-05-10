package InteraccaoBanco;

import java.util.ArrayList;
import java.util.Scanner;

import Banco.Banco;
import Cliente.Cliente;
import Contas.Conta;

public abstract class InteraccaoBanco {

	protected static Cliente cli; // � medida que os menus avan�am o cliente a
									// ser usado � guardado nesta variavel
	protected static Conta con;// � medida que os menus avan�am a conta a ser
								// usada � guardada nesta variavel

	/**
	 * Este metodo est� em repeti��o a mostrar o menu de opera��es disponiveis
	 * numa conta. Para cada uma das op��es existentes e atrav�s de um switch
	 * solicita a informa��o necessaria ao utilizador e invoca os metodos
	 * correspondentes.
	 */
	protected static void processaMenuConta() {
		// implementar o codigo deste m�todo
		Scanner teclado = new Scanner(System.in);
		int opcao = 0;
		double valor = 0;

		while (opcao != 7) {

			System.out.println("Menu Opera��es Conta\n");
			System.out.println("1 -> Levantar");
			System.out.println("2 -> Depositar");
			System.out.println("3 -> Transferir");
			System.out.println("4 -> Obter Extracto");
			System.out.println("5 -> Obter Saldo");
			System.out.println("6 -> Obter informa��es");
			System.out.println("7 -> Sair do Menu");

			opcao = teclado.nextInt();

			switch (opcao) {

			case 1:
				System.out.println("Quanto dinheiro quer levantar?");
				valor = teclado.nextDouble();
				boolean levantou = con.levantar(valor);

				if (!levantou) {
					System.out
							.println("N�o tem saldo para levantar o valor introduzido");
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

					System.out.println("A conta n�o existe!");
					break;

				}
				System.out.println("Quanto dinheiro quer transferir?");
				valor = teclado.nextDouble();
				boolean transferiu = con.transferir(valor, contadestino);

				if (!transferiu) {
					System.out
							.println("N�o tem saldo para levantar o valor introduzido");
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
				System.out.println("Op��o errada!");
				break;
			}
		}
	}

	/**
	 * Este metodo est� em repeti��o a mostrar o menu de contas disponiveis do
	 * cliente. De notar que APENAS AS CONTAS ACTIVAS s�o mostradas. Ap�s ser
	 * seleccionada uma conta � invocado o metodo processaMenuConta referente �
	 * conta escolhida
	 * 
	 * @param contascliente
	 *            Cliente sobre o qual se quer visualizar as contas
	 */
	protected static void processaMenuContas(ArrayList<Conta> contascliente) {
		// implementar o codigo deste m�todo
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

					System.out.println("N�o � poss�vel usar a conta!");

				}
			} else if (opcao == contascliente.size() + 1) {
				break;
			} else {
				System.out.println("Op��o Errada!");
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
