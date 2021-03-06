package utils;

public class Seletores {

	//LOGIN PAGE
	public static final String CAMPOUSUARIOLOGIN = "[ng-model='loginCtrl.user.username']";
	public static final String CAMPOSENHALOGIN = "[ng-model='loginCtrl.user.password']";
	public static final String BOTAOACESSARSUACONTA  = "//*[contains(text(), 'Acessar sua Conta')]";


	//PRODUTO PAGE
	public static final String BOTAOSAIR = "[ng-click='adminCtrl.logout()']";
	public static final String BOTAOADICIONARPRODUTO = "[ui-sref='admin.productAdd']";
	public static final String CAMPODESCRICAO = "[ng-model='prodCtrl.productDescription']";
	public static final String BOTAOPESQUISAR = "//*[contains(text(), 'Pesquisar')]";
	public static final String SCROLLPROXIMAPAGE = "[ng-change='prodCtrl.nextPage()']";
	public static final String NOMEPRODUTODESCRICAO = "[class='col-sm-8 col-md-8 product-info']"; // ok para busca por descri��o
	public static final String CAMPOCODIGO = "[ng-model='newTag.text']";
	public static final String CODIGOPRODUTO = "//*[@id='page-wrapper']/div[2]/div[3]/div[2]/div[2]/small";
	public static final String DESCRICAOPRODUTOCODIGO = "//*[@id='page-wrapper']/div[2]/div[3]/div[2]/div[2]/h5";
	public static final String BOTAOEDITARPRODUTO = "//*[contains(text(), 'Editar Produto')]";
	public static final String EDITARDESCRICAOPROD =  "[ng-model='prodDetailCtrl.product.descricao']";
	public static final String CAMPOTAGPRODUTO = "[ng-model='newTag.text']";
	public static final String CLICARSALVARALTERACOES = "//*[contains(text(), 'Salvar Altera��es')]";
	public static final String CLICARSALVARMODAL = "[ng-click='prodModalCtrl.save()']";
	public static final String EXCLUIRTAG = "[class='remove-button ng-binding ng-scope']";
	public static final String BOTAOVOLTAR = "//*[contains(text(), 'Voltar')]";
	public static final String CAMPOCODIGONOVOPRODUTO = "[ng-model='prodAddCtrl.product.codpluPrinc']";
	public static final String CAMPODESCRICAONOVOPRODUTO = "[ng-model='prodAddCtrl.product.descricao']";
	public static final String CAMPOMEDIDANOVOPRODUTO = "[class='ui-select-match ng-scope']";
	public static final String CLICARMEDIDAKG = "//*[@id='ui-select-choices-row-0-1']/span";
	public static final String CLICARMEDIDAUNIDADE = "//*[@id='ui-select-choices-row-0-0']/span";
	public static final String CAMPOURLNOVOPRODUTO = "[ng-model='prodAddCtrl.product.urlImg']";
	public static final String CAMPOTAGNOVOPRODUTO = "[ng-model='newTag.text']";
	public static final String BOTAOSALVARNOVOPRODUTO = "//*[contains(text(), 'Adicionar Produto')]";
	public static final String SALVARNOVOPRODUTO = "[ng-click='prodAddModalCtrl.save()']";


	//PROMOCOES PAGE
	public static final String ABAPROMOCOES = "[ui-sref='admin.promotion']";
	public static final String BOTAOPESQUISARTODAS = "//*[contains(text(),'Pesquisar Todos')]";
	public static final String SCROLLBOTAODESATIVAR = "[ng-click='promoCtrl.disablePromotion(promotion)']";
	public static final String CAMPOCODIGOPROMOCAO = "[name='promotionId']";
	public static final String CAMPOCODIGOPRODUTO = "[name='productId']";
	public static final String CAMPODESCRICAOPROMOCAO = "[name='productDescription']";
	public static final String CODIGOPROMOCAO = "//*[@id='page-wrapper']/div[2]/form/div/div[6]/div[2]/div/div[3]/div[2]";
	public static final String CAMPOESTADO = "[ng-click='$select.activate()']";
	public static final String CLICARESTADOINSERIDO = "[class='ui-select-choices-row-inner']";
	public static final String CALENDARIODTFINAL = "[ng-click='promoCtrl.isOpenEndDate = !promoCtrl.isOpenEndDate']";
	public static final String TAGREGIONALIZADA = "[ng-if='promotion.promoState']";
	public static final String TEXTOESTADOSVIGENTES = "//*[@id='page-wrapper']/div[2]/form/div/div[6]/div[3]/div/div[6]/div[3]/small";

	//PROMOCOESCLIENTE PAGE
	public static final String ABAPROMOCOESCLIENTE = "[ui-sref='admin.customer']";
	public static final String CAMPOCPF = "[ng-model='customerCtrl.clientCPF']";
	public static final String SELECTMARCA = "//*[@id='page-wrapper']/div[2]/div[2]/form/div[1]/div/div[2]/div/div[1]/span";
	public static final String SELECTESTADO = "//*[@id='page-wrapper']/div[2]/div[2]/form/div[1]/div/div[3]/div/div[1]/span";
	public static final String BOTAOLIMPARFILTROS = "[ng-click='customerCtrl.clear()']";
	public static final String BOTAOPESQUISARCLIENTE = "//*[@id='page-wrapper']/div[2]/div[2]/form/div[2]/div/button[2]";
	public static final String CLICARITEMSELECIONADO = "[type='search']";
	public static final String ESTADOAPOSCLICAR = "//*[@id='page-wrapper']/div[2]/div[2]/form/div[1]/div/div[3]/div/input[1]";
	public static final String ESTADOSELECIONADO = "[id='ui-select-choices-1']";
	public static final String BOTAPESQUISARCLIENTE = "//*[contains(text(), 'Pesquisar')]";
	public static final String BOTAOATIVARPROMOCAO = "[ng-click='customerCtrl.activate(promotion)']";
	public static final String TAGREGIONALIZADACLIENTE = "[ng-if='promotion.is_regional']";
	public static final String NOMEPROMOCAOCLIENTE = "[class='text-container-custom text-center ng-binding ex']";
	public static final String PROMOCLIENTENAOENCONTRADA = "[class='border-bottom white-bg dashboard-header']";
	public static final String CLICACAMPOMEDIDA = "[type='search']";
	public static final String CLICAMARCAPA = "//*[@id='ui-select-choices-row-0-0']/span";
	public static final String CLICAMARCAEX = "//*[@id='ui-select-choices-row-0-1']/span";
}