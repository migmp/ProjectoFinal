package InteraccaoBanco;

import java.util.Scanner;

import Banco.Banco;
import Cliente.Cliente;

public class Multibanco extends InteraccaoBanco {
	private static int userid;
	private static int password;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner opcao = new Scanner(System.in);
		Banco.iniciar();

		System.out.println("Insira o seu Login");
		userid = opcao.nextInt();
		System.out.println("Insira a sua password");
		password = opcao.nextInt();
		if (Banco.validarLogin(userid, password) != null) {
			Cliente c = Banco.procurarCliente(userid);

			if (c.obterActivacao() == true) {
				System.out.println("Bem-Vindo!");
				processaMenuContas(c.obterContas());
			} else {

				System.out.println("O cliente encontra-se desactivado");

			}
		} else {

			System.out.println("Os dados estão incorrectos!");

		}

		Banco.guardarDados();
	}
}
