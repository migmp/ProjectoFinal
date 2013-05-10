package Transaccoes;

import java.io.PrintWriter;
import java.util.Date;

import Contas.Conta;

public abstract class Transaccao {
	protected String tipo;
	protected Date data;
	protected int nib;
	protected double valor;

	public Transaccao(Date data1, Conta c1, int valor1) {

		data = data1;
		nib = c1.obterNib();
		valor = valor1;

	}

	public Transaccao(Date data1, int nib1, int valor1) {

		data = data1;
		nib = nib1;
		valor = valor1;

	}

	public abstract String obterTipo();

	public abstract String mostrar();

	public abstract void escreverFicheiro(PrintWriter pw);

}
