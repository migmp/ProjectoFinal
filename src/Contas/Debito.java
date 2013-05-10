package Contas;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import Transaccoes.Levantamento;
import Transaccoes.Transferencia;

public class Debito extends Conta {
	private static double TAXA_JURO = 0.05;
	private double juroacumulado;
	private Date datavalidade;

	public Debito(Date datavalidade1) {

		datavalidade = datavalidade1;

	}

	public Debito() {
		// TODO Auto-generated constructor stub
		super();
	}

	public Debito(double saldo1, int nib1, boolean activa1, Date datacriacao1) {
		super();

		saldo = saldo1;
		nib = nib1;
		activa = activa1;
		datacriacao = datacriacao1;

	}

	public double obtertaxajuro() {

		return TAXA_JURO;

	}

	@Override
	public boolean levantar(double valor) {
		// TODO Auto-generated method stub
		if (valor <= saldo) {
			saldo -= valor;
			transaccoes.add(new Levantamento(new Date(), this, (int) valor));
			return true;
		}

		return false;
	}

	@Override
	public boolean transferir(double valor, Conta contadestino) {
		// TODO Auto-generated method stub
		if (valor <= saldo) {
			saldo -= valor;
			contadestino.saldo += valor;
			transaccoes.add(new Transferencia(new Date(), this, (int) valor,
					contadestino));
			contadestino.transaccoes.add(new Transferencia(new Date(), this,
					(int) valor, contadestino));
			return true;
		}

		return false;
	}

	@Override
	public String mostrar() {
		// TODO Auto-generated method stub

		return "Debito --> " + nib;

	}

	@Override
	public String mostrarInformacoes() {
		// TODO Auto-generated method stub

		return "Informações:\n\n" + "Saldo: " + saldo + "\nNIB: " + nib
				+ "\nData de criação: " + datacriacao;

	}

	@Override
	public void mostrarSaldo() {
		// TODO Auto-generated method stub

		System.out.println("O saldo da sua conta é " + saldo
				+ " e o juro acumulado é " + juroacumulado);

	}

	@Override
	public String obterTipo() {
		// TODO Auto-generated method stub

		System.out.println("O tipo de conta é de Debito");

		return null;
	}

	public void escreverFicheiro(int userid, PrintWriter pw) {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		pw.write(userid + ";" + "Debito" + ";" + saldo + ";" + nib + ";" + activa + ";"
				+ formato.format(datacriacao) + "\n");
	}

}
