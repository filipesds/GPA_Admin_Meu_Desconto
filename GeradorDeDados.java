package utils;

import java.util.Random;

public class GeradorDeDados {
    private String cpf;

    public static int randomizar(int n){
        int numeroRandomico = (int) (Math.random())* n;

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