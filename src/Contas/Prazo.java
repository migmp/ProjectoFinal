package Contas;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Transaccoes.CapitalizacaoJuros;
import Transaccoes.Levantamento;
import Transaccoes.Transferencia;

public class Prazo extends Conta {
	private static double TAXA_JURO = 0.05;
	private double juroacumulado;
	private Date datavalidade;

	public Prazo(Date datavalidade1) {

		datavalidade = datavalidade1;

	}

	public Prazo() {
		super();

		/*
		 * long currentmilis = (new Date()).getTime(); datavalidade = new Date(
		 * currentmilis + 1000*60*60*24*30*12 );
		 */

		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(new Date());

		calendario.add(Calendar.YEAR, 1);

		datavalidade = calendario.getTime();
	}

	public Prazo(double saldo1, int nib1, boolean activa1, Date datacriacao1,
			double juroacumulado1, Date datavalidade1) {
		super();

		saldo = saldo1;
		nib = nib1;
		activa = activa1;
		datacriacao = datacriacao1;
		juroacumulado = juroacumulado1;
		datavalidade = datavalidade1;

	}

	public double obtertaxajuro() {

		return TAXA_JURO;

	}

	public void aplicartaxajuro(int montante) {

		Date agora = new Date();
		long diferenca = datavalidade.getTime() - agora.getTime();
		long um_ano = datavalidade.getTime() - datacriacao.getTime();

		long tempo_decorrido = um_ano - diferenca;
		long percentagem = tempo_decorrido / um_ano;

		juroacumulado += percentagem * TAXA_JURO * montante;

		int juroscapitalizados = (int) (percentagem * TAXA_JURO * montante);
		
		if (juroscapitalizados > 0 ){
			transaccoes.add(new CapitalizacaoJuros(new Date(), this,
					juroscapitalizados));
		}
	}

	@Override
	public boolean levantar(double valor) {
		// TODO Auto-generated method stub
		if (valor <= saldo) {
			saldo -= valor;
			aplicartaxajuro((int) valor);
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

		return "Prazo --> " + nib;

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
		pw.write(userid + ";" + "Prazo" + ";" + saldo + ";" + nib + ";" + activa
				+ ";" + formato.format(datacriacao) + ";" + juroacumulado + ";" + formato.format(datavalidade) + "\n");
	}

}
