package Transaccoes;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import Contas.Conta;

public class Transferencia extends Transaccao {
	protected int nibdestino;

	public Transferencia(Date data1, Conta c1, int valor1, Conta c2) {
		super(data1, c1, valor1);
		nibdestino = c2.obterNib();
		// TODO Auto-generated constructor stub
	}

	public Transferencia(int niborigem1, double valor1,
			Date datatransaccao1, int nibdestino1) {
		// TODO Auto-generated constructor stub
		super(datatransaccao1, niborigem1, (int) valor1);

		nibdestino = nibdestino1;

	}

	@Override
	public String obterTipo() {
		// TODO Auto-generated method stub
		return "Transferência";
	}

	@Override
	public String mostrar() {
		// TODO Auto-generated method stub
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		return obterTipo() + " - " + formato.format(data) + " - " + nib + " - "
				+ valor + "€ " + " - " + nibdestino;
	}

	public void escreverFicheiro(PrintWriter pw) {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

		pw.write(nib + ";" + "Transferencia" + ";" + nib + ";" + valor
				+ ";" + formato.format(data) + ";" +nibdestino + "\n");
	}

}
