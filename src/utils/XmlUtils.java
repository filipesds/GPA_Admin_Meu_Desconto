package utils;

import java.io.File;
import java.io.FileFilter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public class XmlUtils {
	
	private static final Logger logger = LogManager.getLogger();
	private static List conteudo = new ArrayList(); //conteudo;
	static String status = null;
	static String erros;
	private static Font fonteVermelha = new Font(Font.FontFamily.TIMES_ROMAN,12, Font.NORMAL, BaseColor.BLUE);
	
	public static Summary lerXmlJunit() {
		Summary summary = new Summary();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			Document doc = docBuilder.parse(new File("junit/TESTS-TestSuites.xml"));
			Element testsuitesTag = doc.getDocumentElement();
			List<Node> listTestsuiteTag = getNodesInTree(testsuitesTag, new String[] { "testsuite" });
			for (Node testsuite : listTestsuiteTag) {
				Element testsuiteTag = (Element) testsuite;
				String tests = testsuiteTag.getAttribute("tests");
				summary.setTests(summary.getTests() + Integer.valueOf(tests));
				String failures = testsuiteTag.getAttribute("failures");
				summary.setFailures(summary.getFailures() + Integer.valueOf(failures));
				String errors = testsuiteTag.getAttribute("errors");
				summary.setErrors(summary.getErrors() + Integer.valueOf(errors));
				String skipped = testsuiteTag.getAttribute("skipped");
				summary.setSkipped(summary.getSkipped() + (skipped.isEmpty() ? 0 : Integer.valueOf(skipped)));
				Long time = new Long(testsuiteTag.getAttribute("time").replace(".", ""));
				summary.setTime(summary.getTime() + time);
				if (summary.getBuildNumber().isEmpty() || summary.getJobName().isEmpty()) {
					List<Node> ListPropertyTag = getNodesInTree(testsuiteTag,new String[] { "properties", "property" });		
					String projectName = "";
					String buildConfName = "";
					String jobName = "";
					String buildNumber = "";			
					for (Node property : ListPropertyTag) {
						Element propertyTag = (Element) property;				
						if ("ant.project.name".equalsIgnoreCase(propertyTag.getAttribute("name"))) {
							projectName = propertyTag.getAttribute("value");
						}
						if ("jenkins.buildConfName".equalsIgnoreCase(propertyTag.getAttribute("name"))) {
							buildConfName = propertyTag.getAttribute("value");
						}
						if ("env.JOB_NAME".equalsIgnoreCase(propertyTag.getAttribute("name"))) {
							jobName = propertyTag.getAttribute("value");
						}
						if ("env.BUILD_NUMBER".equalsIgnoreCase(propertyTag.getAttribute("name"))) {
							buildNumber = propertyTag.getAttribute("value");
						}
					}
					summary.setJobName(jobName.isEmpty() ? (projectName + " " + buildConfName) : jobName);
					summary.setBuildNumber(buildNumber);
				}
			}
			// formatando time
			Long time = summary.getTime();
			Long segundos = (time / 1000) % 60;
			Long minutos = (time / 60000) % 60;
			Long horas = (time / 3600000) % 24;
			summary.setTimeString(String.format("%02d:%02d:%02d", horas, minutos, segundos));
			// percentual de sucesso
			BigDecimal errosAndFailures = new BigDecimal(summary.getErrors()).add(new BigDecimal(summary.getFailures()));
			BigDecimal resultado = new BigDecimal("100").subtract((errosAndFailures.multiply(new BigDecimal("100")).divide(new BigDecimal(summary.getTests()), 2, RoundingMode.HALF_EVEN)));
			Locale brasil = new Locale("pt", "BR");
			DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(brasil));
			summary.setSuccessRate(String.valueOf(df.format(resultado)));
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Erro ao ler arquivo junit.");
			
			return null;
		}
		return summary;
	}
	
	private static List<Node> getNodesInTree(Node pai, String[] niveis){
		NodeList childs = pai.getChildNodes();		
		List<Node> retorno = new ArrayList<Node>();
		for(int i=0; i<childs.getLength(); i++){
			Node child = childs.item(i);
			if(niveis.length==1){// buscando ultimo nível (tag procurada)
				if(child.getNodeName().equals(niveis[0])){
					retorno.add(child);
				}				
			}
			else if(child.getNodeName().equals(niveis[0])){
					niveis = Arrays.copyOfRange(niveis, 1, niveis.length);
					retorno = getNodesInTree(child, niveis);					
			}
		}		
		return retorno; 
	}

//	--------------------RELATORIO GERMUD--------------------------
	public static List lerXmlJunitPDFSimples(){
		String projeto = System.getProperty("user.dir");
		try {	
			String diretorio = projeto + "/junit";
			for (int x = 1; x < 3; x++) {
			String filtroPesquisa = "tests.regressao"+x;
			ArrayList arquivos = listaArquivosdoDiretorio(diretorio, filtroPesquisa);
			for (int i = 0; i < arquivos.size(); i++) {
				File inputFile = new File(arquivos.get(i).toString());
		         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         Document doc = dBuilder.parse(inputFile);
		         doc.getDocumentElement().normalize();
		         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		         String XML = arquivos.get(i).toString();
		         XML = XML.replace(projeto + "/junit/", "");
		         System.out.println("XML: " + XML);
		         NodeList nList = doc.getElementsByTagName("testsuite");
		         System.out.println("----------------------------");
		         for (int temp = 0; temp < nList.getLength(); temp++) {
//		         int temp1 = 0;
		            Node nNode = nList.item(temp);
//		            System.out.println("\nCurrent Element :"+ nNode.getNodeName());
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		               conteudo.add("SUITE : ");
		               String nome = eElement.getAttribute("name");
		               String suiteFinal = nome.replace("tests.regressao"+x+".", "");
		               conteudo.add(suiteFinal);
		               List dadosServer = pegaIpRemoto();
		               for (int j = 0; j < dadosServer.size(); j++) {
		            	   conteudo.add(dadosServer.get(j).toString());
					}
		               
		               erros = eElement.getAttribute("errors");
//		               String data = eElement.getAttribute("timestamp");
//		               data = data.replace("T", " - ");
//		               AJUSTE DATA E HORA PARA O MOMENTO DA GERAÇÃO DO RELATORIO GERMUD
		               conteudo.add("DATA / HORA");
		               String dataHora = pegaDataHora();
		               conteudo.add(dataHora);
	            		
		  	         }
		         }
		         NodeList nList2 = doc.getElementsByTagName("testcase");

		         for (int temp = 0; temp < nList2.getLength(); temp++) {
		         		Node nNode2 = nList2.item(temp);
			            if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
			               Element eElement = (Element) nNode2;
			               if(erros=="0"){
			            	   status = "Passed";
			            	   conteudo.add(eElement.getAttribute("name"));
			            	   conteudo.add(status);
			               }else{
			            	   NodeList cenarioFail = eElement.getChildNodes();
			            	   int errorMessage = cenarioFail.getLength();
			            	   if(errorMessage==0){
			            		   status = "Passed";
			            		   conteudo.add(eElement.getAttribute("name"));
			            		   conteudo.add(status);
			            	   }else{
			            		   status = "Fail";
			            		   conteudo.add(eElement.getAttribute("name"));
			            		   conteudo.add(status);}
			            	   
			               }	      	 
			               
			               
			  	         }
			         }
		         conteudo.add(" ");
		         conteudo.add(" ");
			}}
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		return conteudo;
	}
	
	public static ArrayList listaArquivosdoDiretorio(String diretorio, final String filtroPesquisa){
		
		FileFilter filter = new FileFilter() {
		    public boolean accept(File file) {
		        return file.getName().contains(filtroPesquisa);
		    }
		};
		ArrayList arquivos = new ArrayList<>();
		File dir = new File(diretorio);
		File[] files = dir.listFiles(filter);
		for (int i = 0; i < files.length; i++) {
			arquivos.add(files[i]);
			System.out.println(files[i]);
		}
		return arquivos;	
	}
	
	public static List pegaIpRemoto() throws UnknownHostException{
		String server = "m.pontofrio.com.br";
		InetAddress inet = InetAddress.getByName(server);
		List remoteServer = new ArrayList();
		remoteServer.add("URL");
		remoteServer.add(server);
		remoteServer.add("IP");
		remoteServer.add(inet.getHostAddress());

		return remoteServer;
	}
	
	public static String pegaDataHora(){
		String data = "dd/MM/yyyy";
		String hora = "h:mm - a";
		String data1, hora1;
		java.util.Date agora = new java.util.Date();;
		SimpleDateFormat formata = new SimpleDateFormat(data);
		data1 = formata.format(agora);
		formata = new SimpleDateFormat(hora);
		hora1 = formata.format(agora);
		String dataHoraFinal = data1 + " - "+hora1;
		
		return dataHoraFinal;

	}	
}