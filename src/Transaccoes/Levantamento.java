package Transaccoes;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import Contas.Conta;

public class Levantamento extends Transaccao {

	public Levantamento(Date data1, Conta c1, int valor1) {
		super(data1, c1, valor1);
		// TODO Auto-generated constructor stub
	}

	public Levantamento(int niborigem1, double valor1,
			Date datatransaccao1) {
		super(datatransaccao1, niborigem1, (int) valor1);
	}

	@Override
	public String obterTipo() {
		// TODO Auto-generated method stub
		return "Levantamento";
	}

	@Override
	public String mostrar() {
		// TODO Auto-generated method stub
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		return obterTipo() + " - " + formato.format(data) + " - " + nib + " - "
				+ valor + "�";
	}

	@Override
	public void escreverFicheiro(PrintWriter pw) {
		// TODO Auto-generated method stub
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

		pw.write(nib + ";" + "Levantamento" + ";" + nib + ";" + valor
				+ ";" + formato.format(data) + "\n");
	}

}
