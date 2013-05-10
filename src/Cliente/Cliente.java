package Cliente;

import java.io.PrintWriter;
import java.util.ArrayList;

import Contas.Conta;

public class Cliente implements Comparable {

	private String nome;
	private int password, userid;
	private boolean activo;
	private ArrayList<Conta> contas;

	public Cliente(String nome1, int userid1, int password1) {
		nome = nome1;
		userid = userid1;
		password = password1;
		activo = true;

		contas = new ArrayList<Conta>();

	}

	public Cliente(String nome1, int userid1, int password1, boolean activo1) {
		nome = nome1;
		userid = userid1;
		password = password1;
		activo = activo1;

		contas = new ArrayList<Conta>();

	}

	public String obterNome() {

		return nome;

	}

	public void escreverNome(String nome1) {

		nome = nome1;

	}

	public int obterUserId() {

		return userid;

	}

	public void escreverUserId(int userid1) {

		userid = userid1;

	}

	public int obterPassword() {

		return password;

	}

	public void escreverPassword(int password1) {

		password = password1;

	}

	public boolean obterActivacao() {

		return activo;

	}

	public void definirActivacao(boolean activo1) {

		activo = activo1;

	}

	public String obterInformacoes() {

		return "Cliente [" + nome
				+ "]: \n\nInformações pessoais ---- \n\n\tId - " + userid
				+ "\n\n\tActivo - " + activo + "\n\n";

	}

	public void adicionarConta(Conta c) {

		contas.add(c);

	}

	public ArrayList<Conta> obterContas() {

		return contas;

	}

	public Conta obterConta(int nib1) {

		for (int i = 0; i < contas.size(); i++) {
			Conta c = contas.get(i);
			if (c.obterNib() == nib1) {
				return c;
			}
		}
		return null;

	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Cliente clienteAComparar = (Cliente) arg0;
		return nome.compareTo(clienteAComparar.nome);
	}

	public void escreverFicheiro(PrintWriter pw) {
		pw.write(nome + ";" + userid + ";" + password + ";" + activo + "\n");
	}
}
