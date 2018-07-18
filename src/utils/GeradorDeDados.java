package utils;

import java.util.Random;

public class GeradorDeDados {
	private String cpf;

	public static int randomizar(int n){
		int numeroRandomico = new Random().nextInt(n)+ 1;

		return numeroRandomico;
	}

	public static String selecionaDescricaoProdutoAleatoriamente(){
		String [] descricao = {"café", "teste", "refrigerante", "carne", "sabonete", "chocolate", "batata", "pão", "filé", "queijo",
				"Sardinha Fresca","filé mignon bovino", "condicionador", "limpador", "vinho", "cerveja"};
		String produto;
		String prodSelecionado = "";
		for(int i = 0; i < 1; i++){
			produto = descricao[new Random().nextInt(descricao.length)];
			prodSelecionado = prodSelecionado + produto;
		}
		return prodSelecionado;
	}

	public static String selecionaDescricaoProdutoPromocaoAleatoriamente(){
		String [] descricao = {"Gelatina", "Bovina", "PastaBox", "Bife", "Vinho", "Limpador", "teste", "Presunto", "Cerveja", "Garrafa",
				"Queijo","Editado", "chocolate preto", "Alcatra Bovina", "NACHO LOCO", "Limpador para Sanitário"};
		String produto;
		String prodSelecionado = "";
		for(int i = 0; i < 1; i++){
			produto = descricao[new Random().nextInt(descricao.length)];
			prodSelecionado = prodSelecionado + produto;
		}
		return prodSelecionado;
	}

	public static String selecionaCodigoProdutoAleatoriamente(){
		String [] codigo = {"0250702", "0000089", "0015615", "0015059", "0031813", "0043694", "0061117","0115872", "0115322", "0113847",
				"0137324", "0202954", "0239110", "9999974", "9999978", "8585554", "6818586", "6209254", "5982257", "5612833",
				"5196197", "7083693", "5097487", "4914921", "3904053"};
		String plu;
		String produtoSelecionado = "";
		for(int i = 0; i<1;i++){
			plu = codigo[new Random().nextInt(codigo.length)];
			produtoSelecionado = produtoSelecionado + plu;
		}
		return produtoSelecionado;
	}

	public static String informaCodigoNovoProduto(){
		String [] plu = {"0000980", "0000981", "0000982", "0000983", "0000984", "0000985", "0000986", "0000988", "0000989", "0000990",
				"0000991", "0000992", "0000993", "0000994", "0000995", "0000996", "0000997", "0000998", "0000999", "1000001"};
		String cod;
		String prodSelected = "";
		for(int i = 0; i < 1; i++){
			cod = plu[new Random().nextInt(plu.length)];
			prodSelected = prodSelected + cod;
		}
		return prodSelected;
	}

	public static String selecionaCodigoPromocaoAleatoriamente(){
		String [] codPromocao ={"0789592", "910191", "910333", "913619", "990137", "990136", "990133", "990138", "913511", "1000000"};
		String code;
		String promocaoSelecionada = "";
		for(int i = 0; i< 1;i++){
			code = codPromocao[new Random().nextInt(codPromocao.length)];
			promocaoSelecionada = promocaoSelecionada + code;
		}
		return promocaoSelecionada;
	}

	public static String selecionaCodigoProdutoPromocaoAleatoriamente(){
		String [] codProduto ={"0789592", "0041041", "1094875", "0077231", "0000001", "5520181", "1096449", "4778776", "0118620", "0104357"};
		String codigo;
		String produtoSelecionado = "";
		for(int i = 0; i< 1;i++){
			codigo = codProduto[new Random().nextInt(codProduto.length)];
			produtoSelecionado = produtoSelecionado + codigo;
		}
		return produtoSelecionado;
	}

	public static String selecionaMarcaAleatoriamente(){
		String [] marca = {"PA", "EX"};
		String brand;
		String marcaSelecionada = "";
		for(int i = 0; i<1; i++){
			brand = marca[new Random().nextInt(marca.length)];
			marcaSelecionada = marcaSelecionada + brand;
		}
		return marcaSelecionada;
	}

	public static String selecionaMedidaAleatoriamente(){
		String [] medida = {"Unidade", "KG"};
		String med;
		String medidaSelecionada = "";
		for(int i = 0; i<1; i++){
			med = medida[new Random().nextInt(medida.length)];
			medidaSelecionada = medidaSelecionada + med;
		}
		return medidaSelecionada;
	}

	public static String selecionaEstadoAleatoriamente(){
		String [] estados = {"Rio Grande do Sul", "Santa Catarina", "Paraná", "São Paulo", "Rio de Janeiro", "Minas Gerais", "Espírito Santo",
				"Mato Grosso", "Mato Grosso do Sul", "Goiás", "Distrito Federal", "Bahia", "Pernambuco", "Sergipe", "Alagoas",
				"Piauí", "Paraíba", "Rio Grande do Norte", "Ceará", "Maranhão", "Tocantins", "Pará", "Amapá", "Roraima",
				"Rondônia", "Acre", "Amazonas"};
		String uf;
		String estadoSelecionado = "";
		for(int i = 0; i<1;i++){
			uf = estados[new Random().nextInt(estados.length)];
			estadoSelecionado = estadoSelecionado + uf;
		}
		return estadoSelecionado;
	}

	public static String geraCPF(){
		String iniciais = "";
		Integer numero;
		for(int i =0; i<9; i++){
			numero = new Integer((int) (Math.random()*10));
			iniciais += numero.toString();
		}
		return iniciais + calculoVerificaDigito(iniciais);
	}

	private static String calculoVerificaDigito(String num){
		Integer primeiroDigito;
		Integer segundoDigito;
		int soma = 0;
		int peso = 10;
		for(int i = 0; i<num.length();i++){
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		}

		if (soma % 11 == 0 | soma % 11 == 1) {
			primeiroDigito = new Integer(0);
		}
		else {
			primeiroDigito = new Integer(11 - (soma % 11));
		}
		soma = 0;
		peso = 11;
		for (int i = 0; i < num.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		}
		soma += primeiroDigito.intValue() * 2;
		if (soma % 11 == 0 | soma % 11 == 1) {
			segundoDigito = new Integer(0);
		}
		else {
			segundoDigito = new Integer(11 - (soma % 11));
		}
		return primeiroDigito.toString() + segundoDigito.toString();
	}
}