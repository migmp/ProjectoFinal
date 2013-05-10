package Banco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import Transaccoes.Deposito;
import Transaccoes.Levantamento;
import Transaccoes.Transaccao;
import Transaccoes.Transferencia;

import Cliente.Cliente;
import Contas.Conta;
import Contas.Debito;
import Contas.Prazo;

public class Banco {
	private static ArrayList<Cliente> clientes;
	private static int numerocliente; // utilizado para as numeracoes dos
										// clientes
	private static int numeroconta; // utilizado para as numeracoes das contas
	private static final String FICHEIROCLIENTES = "dados/clientes.csv";
	private static final String FICHEIROCONTAS = "dados/contas.csv";
	private static final String FICHEIROTRANSACCOES = "dados/transaccoes.csv";
	private static final String FICHEIROCONFIGURACOES = "dados/configuracoes.txt";

	public static void guardarDados() {
		guardarTransaccoes();
		guardarContas();
		guardarClientes();
	}

	private static void guardarClientes() {

		try {
			PrintWriter pw = new PrintWriter(FICHEIROCLIENTES);

			for (int i = 0; i < clientes.size(); ++i) {

				clientes.get(i).escreverFicheiro(pw);

			}

			pw.flush();
			pw.close();

		} catch (FileNotFoundException e) {
			System.out.println("A pasta para gravação de clientes não existe");
		}

	}

	private static void guardarContas() {

		try {
			PrintWriter pw = new PrintWriter(FICHEIROCONTAS);

			for (int i = 0; i < clientes.size(); i++) {
				Cliente cliente = clientes.get(i);
				ArrayList<Conta> contas = cliente.obterContas();
				
				for (int e = 0; e < contas.size(); e++) {

					int userid = cliente.obterUserId();
					contas.get(e).escreverFicheiro(userid, pw);

				}

			}

			pw.flush();
			pw.close();

		} catch (FileNotFoundException e) {
			System.out.println("A pasta para gravação de clientes não existe");
		}

	}

	private static void guardarTransaccoes() {

		try {
			PrintWriter pw = new PrintWriter(FICHEIROTRANSACCOES);

			for (int i = 0; i < clientes.size(); i++) {
				Cliente cliente = clientes.get(i);
				ArrayList<Conta> contas = cliente.obterContas();
				for (int e = 0; e < contas.size(); e++) {
					Conta conta = contas.get(e);
					ArrayList<Transaccao> transaccao = conta.obterTransaccoes();
					for (int a = 0; a < transaccao.size(); a++) {
						
						transaccao.get(a).escreverFicheiro(pw);
						
					}

				}

			}

			pw.flush();
			pw.close();

		} catch (FileNotFoundException e) {
			System.out.println("A pasta para gravação de clientes não existe");
		}

	}

	private static void carregarDados() {

		File ficheiroclientes = new File(FICHEIROCLIENTES);
		File ficheirocontas = new File(FICHEIROCONTAS);
		File ficheirotransaccoes = new File(FICHEIROTRANSACCOES);
		File ficheiroconfiguracoes = new File(FICHEIROCONFIGURACOES);

		if (!ficheiroclientes.exists()) {
			System.out
					.println("Não é possível carregar dados pois não existe o ficheiro de clientes para carregar");
			return;
		}
		if (!ficheirocontas.exists()) {
			System.out
					.println("Não é possível carregar dados pois não existe o ficheiro de contas para carregar");
			return;
		}
		if (!ficheirotransaccoes.exists()) {
			System.out
					.println("Não é possível carregar dados pois não existe o ficheiro de transacções para carregar");
			return;
		}
		try {
			importarClientes(ficheiroclientes);
			importarContas(ficheirocontas);
			importarTransaccoes(ficheirotransaccoes);
		} catch (Exception e) {
			System.out.println("Erro na importação de ficheiros");
		}
	}

	private static void importarClientes(File ficheiroclientes)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(ficheiroclientes));
		String texto = br.readLine();

		while (texto != null) {
			String[] dadosficheiro = texto.split(";");
			String nome = dadosficheiro[0];
			int userid = Integer.parseInt(dadosficheiro[1]);
			int password = Integer.parseInt(dadosficheiro[2]);
			boolean activo = Boolean.parseBoolean(dadosficheiro[3]);

			Banco.criarCliente(nome, userid, password, activo);
			texto = br.readLine();
		}
	}

	private static void importarContas(File ficheirocontas) throws IOException,
			ParseException {
		BufferedReader br = new BufferedReader(new FileReader(ficheirocontas));
		String texto = br.readLine();

		while (texto != null) {
			String[] dadosficheiro = texto.split(";");
			int userid = Integer.parseInt(dadosficheiro[0]);
			String tipoconta = dadosficheiro[1];
			double saldo = Double.parseDouble(dadosficheiro[2]);
			int nib = Integer.parseInt(dadosficheiro[3]);
			boolean activa = Boolean.parseBoolean(dadosficheiro[4]);

			SimpleDateFormat formato = new SimpleDateFormat(
					"dd-MM-yyyy hh:mm:ss");

			Date datacriacao = formato.parse(dadosficheiro[5]);

			Cliente c = Banco.procurarCliente(userid);

			if (tipoconta.equals("Debito")) {
				Debito d = new Debito(saldo, nib, activa, datacriacao);
				c.adicionarConta(d);
			} else if (tipoconta.equals("Prazo")) {
				{
					double juroacumulado = Double.parseDouble(dadosficheiro[6]);
					Date datavalidade = formato.parse(dadosficheiro[7]);

					Prazo p = new Prazo(saldo, nib, activa, datacriacao,
							juroacumulado, datavalidade);
					c.adicionarConta(p);
				}

			}

			texto = br.readLine();
		}
	}

	private static void importarTransaccoes(File ficheirotransaccoes)
			throws IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(
				ficheirotransaccoes));
		String texto = br.readLine();

		while (texto != null) {
			String[] dadosficheiro = texto.split(";");
			int nib = Integer.parseInt(dadosficheiro[0]);

			String tipotransaccao = dadosficheiro[1];
			int niborigem = Integer.parseInt(dadosficheiro[2]);
			double valor = Double.parseDouble(dadosficheiro[3]);

			SimpleDateFormat formato = new SimpleDateFormat(
					"dd-MM-yyyy hh:mm:ss");

			Date datatransaccao = formato.parse(dadosficheiro[4]);
			Conta origem = Banco.obterConta(nib);

			if (tipotransaccao.equals("Transferencia")) {
				int nibdestino = Integer.parseInt(dadosficheiro[5]);
				Transferencia trans = new Transferencia(niborigem, valor,
						datatransaccao, nibdestino);

				origem.adicionarTransaccao(trans);

			} else if (tipotransaccao.equals("Levantamento")) {
				Levantamento lev = new Levantamento(niborigem, valor,
						datatransaccao);

				origem.adicionarTransaccao(lev);

			} else if (tipotransaccao.equals("Deposito")) {
				Deposito dep = new Deposito(niborigem, valor,
						datatransaccao);

				origem.adicionarTransaccao(dep);

			}

			texto = br.readLine();
		}
	}

	public static void iniciar() {
		clientes = new ArrayList<Cliente>();
		numeroconta = 2451;
		numerocliente = 1543;
		carregarDados();
	}

	public static int gerarNumConta() {
		return numeroconta++;
	}

	public static int gerarNumCliente() {
		return numerocliente++;
	}

	// METODOS CLIENTES

	/**
	 * Desactiva o cliente com o id passado como parametro. Para isto deve usar
	 * o metodo procurar cliente do banco para encontrar o cliente com esse id.
	 * Caso exista define o estado como inactivo através do metodo desactivar do
	 * cliente
	 * 
	 * @param idcliente
	 *            id do cliente a desactivar
	 * @return booleano a indicar se foi ou nao possivel de desactivar o cliente
	 */
	public static boolean desactivarCliente(int idcliente) {
		// implementar o codigo deste metodo
		for (int i = 0; i < clientes.size(); i++) {

			if (clientes.get(i).obterUserId() == idcliente) {

				clientes.get(i).definirActivacao(false);
				return true;
			}

		}
		return false;
	}

	/**
	 * Lista os clientes do banco que tem o nome igual ao recebido como
	 * parametro. Caso seja passada uma string vazia entao sao listados todos os
	 * clientes
	 * 
	 * @param criterionome
	 *            nome do cliente a listar
	 */
	public static void listarClientes(String criterionome) {
		// implementar o codigo deste metodo
		Collections.sort(clientes);
		if (criterionome.equals("")) {

			for (int e = 0; e < clientes.size(); e++) {

				System.out.println(clientes.get(e).obterInformacoes());

			}

		} else {
			for (int i = 0; i < clientes.size(); i++) {

				if (clientes.get(i).obterNome().equals(criterionome)) {

					System.out.println(clientes.get(i).obterInformacoes());

				}

			}
		}
	}

	/**
	 * Procura o cliente com o id recebido como parametro e devolve-o caso
	 * exista. Caso não exista devolve null. Este metodo e utilizado noutros
	 * metodos do banco.
	 * 
	 * @param userid
	 *            id do cliente a procurar
	 * @return devolve o cliente com o id procurado ou null
	 */
	public static Cliente procurarCliente(int userid) {
		// implementar o codigo deste metodo

		for (int i = 0; i < clientes.size(); i++) {

			if (clientes.get(i).obterUserId() == userid) {

				return clientes.get(i);

			}

		}
		return null;
	}

	/**
	 * Utiliza o userid para encontrar o respectivo cliente e se existir
	 * confirma que a password e a correcta para esse utilizador. Caso isso se
	 * verifique devolve o cliente que fez login
	 * 
	 * @param userid
	 *            id do utilizador que esta a fazer login
	 * @param password
	 *            passwor dod utilizador que esta a fazer login
	 * @return Cliente que acabou de fazer login ou null caso não coincidam as
	 *         credênciais
	 */
	public static Cliente validarLogin(int userid, int password) {
		// implementar o codigo deste metodo

		for (int i = 0; i < clientes.size(); i++) {

			if (clientes.get(i).obterUserId() == userid) {

				if (clientes.get(i).obterPassword() == password) {

					return clientes.get(i);

				}

				return null;

			}

		}

		return null;

	}

	/**
	 * Cria um cliente com os dados recebidos e adiciona-o à lista de clientes
	 * do banco
	 * 
	 * @param nome
	 *            nome do cliente a adicionar
	 * @param userid
	 *            userid do cliente a adicionar
	 * @param password
	 *            password do cliente a adicionar
	 */
	public static void criarCliente(String nome, int userid, int password) {
		// implementar o codigo deste metodo
		Cliente cliente = new Cliente(nome, userid, password);
		clientes.add(cliente);

	}

	public static void criarCliente(String nome, int userid, int password,
			boolean activo) {
		// implementar o codigo deste metodo
		Cliente cliente = new Cliente(nome, userid, password, activo);
		clientes.add(cliente);

	}

	/**
	 * Procura em todos os clientes por uma conta com o nib recebido como
	 * parâmetro Devolve o objecto conta caso exista ou null caso não exista
	 * 
	 * @param nib
	 *            nib da conta a procurar
	 * @return Conta com o nib especificado
	 */
	public static Conta obterConta(int nib) {
		// implementar o codigo deste método

		for (int i = 0; i < clientes.size(); i++) {
			Cliente cliente = clientes.get(i);
			ArrayList<Conta> contas = cliente.obterContas();
			for (int e = 0; e < contas.size(); e++) {

				if (contas.get(e).obterNib() == nib) {

					return contas.get(e);

				}

			}

		}
		return null;
	}

	/**
	 * Procurar o cliente com o id recebido por parâmetro. Caso este exista
	 * adiciona o objecto conta recebido por parâmetro ás contas desse cliente
	 * 
	 * @param idcliente
	 *            id do cliente a adicionar a Conta
	 * @param c
	 *            Conta a adicionar
	 */
	public static void adicionaConta(int idcliente, Conta c) {
		// implementar o codigo deste método

		for (int i = 0; i < clientes.size(); i++) {

			if (clientes.get(i).obterUserId() == idcliente) {

				clientes.get(i).adicionarConta(c);
				return;

			}

		}

	}

	/**
	 * Cria uma conta para o cliente c com o tipo tipoconta ESTE METODO ASSUME
	 * QUE O TIPO DE CONTA E VALIDO(1 - DEBITO / 2 - PRAZO)
	 * 
	 * @param c
	 *            O cliente sobre o qual vai ser criada a conta
	 * @param tipoconta
	 *            o tipo da conta a criar
	 * @return O nib da nova conta criada
	 */
	public static int criarConta(Cliente c, int tipoconta) {
		// implementar o codigo deste método

		if (tipoconta > 2 || tipoconta < 1) {

			return -1;

		} else {

			if (tipoconta == 2) {

				Debito d = new Debito();
				c.adicionarConta(d);
				return d.obterNib();

			} else {

				Prazo p = new Prazo();
				c.adicionarConta(p);
				return p.obterNib();
			}
		}

	}

	public static void simularDados() {

		Cliente joao = new Cliente("joao", 111, 222);
		joao.adicionarConta(new Prazo());
		joao.adicionarConta(new Debito());

		Cliente pedro = new Cliente("pedro", 123, 456);
		pedro.adicionarConta(new Prazo());
		pedro.adicionarConta(new Debito());

		clientes.add(joao);
		clientes.add(pedro);

	}

}
