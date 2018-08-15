package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class RelatorioUtils {
	private static final Logger logger = LogManager.getLogger();
	private static WebDriver driver = null;
	private static Summary summary = null;
	private static String resumo = "";
	public static String nomeCenario;
	public static String caminhoWindowsPRD = "C:\\pf-slave\\workspace\\PF-RelatorioGermud\\relatorios\\img\\";
	public static String caminhoWindowsDEV = "C:\\Users\\filipe.silva\\Jenkins\\workspace\\MD-RelatorioGermud\\relatorios\\";
	
	

	public static void enviarRelatorioFalhas() {
		summary = XmlUtils.lerXmlJunit();
		resumo = ArquivoUtils.lerArquivo("logs/resumo.txt");
		if (!resumo.isEmpty()) {
			String corpoEmail = montaCorpoEmail();
			enviarRelatorio(corpoEmail);
		}
		else {
			logger.info("Arquivo de resumo vazio.");
		}
	}

	private static String montaCorpoEmail() {
		logger.info("Montando corpo do email.");
		StringBuffer corpoEmail = new StringBuffer();
		corpoEmail.append("<img src=\"cid:imageheader\"/>");
		corpoEmail.append("<br><br>");
		
		corpoEmail.append(montaSummary());
		corpoEmail.append(montaResumo());

		corpoEmail.append("<img src=\"cid:legenda\"/>");
		corpoEmail.append("<img src=\"cid:footer\"/>");
		
    	return corpoEmail.toString();
    }

	private static String montaSummary(){
		String retorno = "";	
		if(summary != null){
			retorno = " <body style=' font:normal 68% verdana,arial,helvetica;color:#000000;'>  " +
					" <h2 style='margin-top: 1em; margin-bottom: 0.5em; font: bold 125% verdana,arial,helvetica'>Summary</h2>  " +
					" <table border='0' cellpadding='5' cellspacing='2' width='95%'>  " +
					" <tr valign='top'>  " +
					" <th style=' font-weight: bold;text-align:left;background:#ffc000;' >Tests</th>  " +
					" <th style=' font-weight: bold;text-align:left;background:#ffc000;' >Failures</th>  " +
					" <th style=' font-weight: bold;text-align:left;background:#ffc000;' >Error</th>  " +
					" <th style=' font-weight: bold;text-align:left;background:#ffc000;' >Skipped</th>  " +
					" <th style=' font-weight: bold;text-align:left;background:#ffc000;' >Success Rate</th>  " +
					" <th style=' font-weight: bold;text-align:left;background:#ffc000;' >Time</th>  " +
					" </tr>  " +
					" <tr valign='top'>  " +
					" <td style=' background:#eeeee0;'><a>"+summary.getTests()+"</a></td>  " +
					" <td style=' background:#eeeee0;'><a>"+summary.getFailures()+"</a></td>  " +
					" <td style=' background:#eeeee0;'><a><span style='color: red'>"+summary.getErrors()+"</span></a></td>  " +
					" <td style=' background:#eeeee0;'><a>"+summary.getSkipped()+"</a></td>  " +
					" <td style=' background:#eeeee0;'>"+summary.getSuccessRate()+"%</td>  " +
					" <td style=' background:#eeeee0;'>"+summary.getTimeString()+"</td>  " +
					" </tr>  " +
					" </table>  " +
					" </body>  " +
					" <br> ";
		}
		return retorno;
	}
	
	private static String montaResumo() {
		StringBuilder sb = new StringBuilder();
		sb.append("<body style=' font:normal 68% verdana,arial,helvetica;color:#000000;'>");
		sb.append("<h2 style='margin-top: 1em; margin-bottom: 0.5em; font: bold 125% verdana,arial,helvetica'>Errors</h2>");
		sb.append("<table border='0' cellpadding='5' cellspacing='2' width='95%'>");
		sb.append("<tr valign='top'>");
		sb.append("<th style=' font-weight: bold;text-align:left;background:#ffc000;'>Test Case</th>");
		sb.append("<th style=' font-weight: bold;text-align:left;background:#ffc000;'>Test Classe</th>");
		sb.append("<th style=' font-weight: bold;text-align:left;background:#ffc000;'>Bug</th>");
		sb.append("</tr>");
		sb.append(resumo);
		sb.append("</table>");
		sb.append("</body>");
		sb.append("<br>");
		
		return sb.toString();
	}
	
	private static void enviarRelatorio(String corpoEmail) {
		try {
			List<String> anexos = new ArrayList<String>();
			String[] anexosAux = new File("anexos/").list();
			for (int i = 0; i < anexosAux.length; i++) {
				anexos.add("anexos/" + anexosAux[i]);
			}
			String anexosZip = "Falhas-Consolidado.zip";
			ArquivoUtils.compactarParaZip(anexosZip, anexos);	
			String assunto = summary.getJobName() + " - BUILD-NUMBER:" + summary.getBuildNumber();
			boolean enviou = SendMail.execute(assunto, corpoEmail, Arrays.asList(anexosZip));
			if (enviou) {
				logger.info("Enviou email com sucesso.");
				ArquivoUtils.limparDiretorio("logs/");
				ArquivoUtils.limparDiretorio("screenshots/");
				ArquivoUtils.limparDiretorio("anexos/");
			} 
			else {
				logger.error("Erro ao enviar email.");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Erro ao enviar email.");
		}
	}
	
	public static void salvaRelatorioSimplesPDF() throws ParserConfigurationException, SAXException, IOException{
		List conteudo = XmlUtils.lerXmlJunitPDFSimples();
		criaPDFSImples(conteudo);
		
	}
	
	private static void criaPDFSImples(List conteudo){
		String projeto = System.getProperty("user.dir");
		Document document = new Document(PageSize.A4, 0, 0, 0, 0);
		
        try {
            PdfWriter.getInstance(document, new FileOutputStream(projeto + "/MD-Germud.pdf"));
            document.open();
            
            	Image img = Image.getInstance(projeto + "/evidencias/config/cwi.png");
            	int indentation = 0;

            	float scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - indentation) / img.getWidth()) * 25;
            	img.scalePercent(scaler);
            	img.setAbsolutePosition(30, 750);
            	document.add(img);
            	
            	Image imgbandeira = Image.getInstance(projeto + "/evidencias/config/MD.png");
            	int indentation2 = 0;

            	float scaler2 = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - indentation2) / img.getWidth()) * 40;
            	imgbandeira.scalePercent(scaler2);
//            	imgbandeira.setAbsolutePosition(absoluteX, absoluteY);
            	imgbandeira.setAbsolutePosition(430, 750);
            	document.add(imgbandeira);
            	document.add(new Paragraph("\n"));
            	document.add(new Paragraph("\n"));
            	document.add(new Paragraph("\n"));
            	document.add(new Paragraph("\n"));
            	document.add(new Paragraph("\n"));
            	document.add(new Paragraph("     ____________________________________________________________________________________"));
            	document.add(new Paragraph("\n"));
            	
//               							CONFIGURANDO TITULO DA PAGINA TABELA 1
    			   
            	String tituloTab1 = "RELATÓRIO DE REGRESSÃO";
            	Paragraph tituloTabela1 = new Paragraph();
            	tituloTabela1.setFont(FontFactory.getFont(FontFactory.COURIER, 16));
            	tituloTabela1.setAlignment(Element.ALIGN_CENTER);
            	tituloTabela1.add(tituloTab1);
            	document.add(tituloTabela1);
            	document.add(new Paragraph("     ____________________________________________________________________________________"));
 			   	document.add(new Paragraph("\n"));

//								FIM DA CONFIGURAÇÃO DO TITULO DA PAGINA COM O PRINT DA HOME
            	
            	PdfPTable table = new PdfPTable(2);
            	PdfPTable table2 = new PdfPTable(2);
            	int tamanhoTotalConteudo = conteudo.size();
            	System.out.println("Quantidade de dados a ser Inserido nas Tabelas: "+tamanhoTotalConteudo);
            	for (int i = 0; i <= 81; i++) {            		
            		switch (conteudo.get(i).toString()) {
					case "Passed":
						PdfPCell corPassed = new PdfPCell(new Paragraph(conteudo.get(i).toString(),FontFactory.getFont(FontFactory.COURIER, 8)));
            			corPassed.setBackgroundColor(new BaseColor(157, 212, 157)); //131, 210, 200
            			table.addCell(corPassed);
						break;
					case "Fail":
						PdfPCell corFail = new PdfPCell(new Paragraph(conteudo.get(i).toString(),FontFactory.getFont(FontFactory.COURIER, 8)));
            			corFail.setBackgroundColor(new BaseColor(255, 0, 0));
            			table.addCell(corFail);
						break;	
					case " ":
						PdfPCell separador = new PdfPCell(new Paragraph(conteudo.get(i).toString(),FontFactory.getFont(FontFactory.COURIER, 8)));
						separador.setBackgroundColor(new BaseColor(13, 14, 14));
            			table.addCell(separador);
						break;	
					default:
						if(i % 2 == 0 ){
							PdfPCell coll = new PdfPCell(new Paragraph(conteudo.get(i).toString(),FontFactory.getFont(FontFactory.COURIER_BOLD, 8)));
	            			table.addCell(coll);
						}else{
							PdfPCell coll = new PdfPCell(new Paragraph(conteudo.get(i).toString(),FontFactory.getFont(FontFactory.COURIER, 8)));
	            			table.addCell(coll);
						}			
						break;
					}
            	}
            	document.add(table);
//              ---------------------------------CONFIGURAÇAO DO FOOTER DA 1 PAGINA---------------------------------
            	Image imgfooter = Image.getInstance(projeto + "/evidencias/config/footer.png");
            	imgfooter.setAlignment(Element.ALIGN_CENTER);
                float scalerfooter = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - indentation2) / img.getWidth()) * 120;
                imgfooter.scalePercent(scalerfooter);
                imgfooter.setAbsolutePosition(-60, 10);
                document.add(imgfooter);
//              ---------------------------------CONFIGURAÇAO DO CABECALHO DA 2 PAGINA---------------------------------
            	document.newPage();
            	document.add(img);
            	document.add(imgbandeira);
            	document.add(new Paragraph("\n"));
            	document.add(new Paragraph("\n"));
            	document.add(new Paragraph("\n"));
            	document.add(new Paragraph("\n"));
            	document.add(new Paragraph("\n"));
            	document.add(new Paragraph("     ____________________________________________________________________________________"));
            	document.add(new Paragraph("\n"));
            	
//										CONFIGURANDO TITULO DA PAGINA TABELA 2
 			   
            	String tituloTab2 = "RELATÓRIO DE REGRESSÃO";
            	Paragraph tituloTabela2 = new Paragraph();
            	tituloTabela2.setFont(FontFactory.getFont(FontFactory.COURIER, 16));
            	tituloTabela2.setAlignment(Element.ALIGN_CENTER);
            	tituloTabela2.add(tituloTab2);
            	document.add(tituloTabela2);
            	document.add(new Paragraph("     ____________________________________________________________________________________"));
            	document.add(new Paragraph("\n"));

//	FIM DA CONFIGURAÇÃO DO TITULO DA PAGINA COM O PRINT DA HOME
            	
//            	---------------------------------------TABLE 2------------------------------------
            	for (int l = 82; l <= conteudo.size() - 1; l++) {
            		
            		switch (conteudo.get(l).toString()) {
					case "Passed":
						PdfPCell corPassed = new PdfPCell(new Paragraph(conteudo.get(l).toString(),FontFactory.getFont(FontFactory.COURIER, 8)));
            			corPassed.setBackgroundColor(new BaseColor(157, 212, 157)); //131, 210, 200
            			table2.addCell(corPassed);
						break;
					case "Fail":
						PdfPCell corFail = new PdfPCell(new Paragraph(conteudo.get(l).toString(),FontFactory.getFont(FontFactory.COURIER, 8)));
            			corFail.setBackgroundColor(new BaseColor(255, 0, 0));
            			table2.addCell(corFail);
						break;
					case " ":
						PdfPCell separador = new PdfPCell(new Paragraph(conteudo.get(l).toString(),FontFactory.getFont(FontFactory.COURIER, 8)));
						separador.setBackgroundColor(new BaseColor(13, 14, 14));
            			table2.addCell(separador);
						break;	
					default:
						if(l % 2 == 0 ){
							PdfPCell coll = new PdfPCell(new Paragraph(conteudo.get(l).toString(),FontFactory.getFont(FontFactory.COURIER_BOLD, 8)));
	            			table2.addCell(coll);
						}else{
							PdfPCell coll = new PdfPCell(new Paragraph(conteudo.get(l).toString(),FontFactory.getFont(FontFactory.COURIER, 8)));
	            			table2.addCell(coll);
						}			
						break;
					}
            	}
            	document.add(table2);
            	
//            	---------------------------------------FIM TABLE 2--------------------------------
            	
//            	---------------------------------------CONFIGURACAO DO FOOTER DA 2 PAGINA--------------------------------
                document.add(imgfooter);
                
               String dir = projeto + "/relatorios/img/";
     		   String filtro = "cenario_falhou";
     		   String filtro2 = "homepage";
     		   XmlUtils xmlUtils = new XmlUtils();
     		   ArrayList qtdimgs = xmlUtils.listaArquivosdoDiretorio(dir, filtro);
     		  ArrayList qtdimgs2 = xmlUtils.listaArquivosdoDiretorio(dir, filtro2);
//     		 for (int l = 0; l < qtdimgs2.size(); l++) {
   			   Image img13 = Image.getInstance(qtdimgs2.get(0).toString());
   			   System.out.println(qtdimgs2.get(0).toString());
   			   float scaler13 = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - indentation) / img.getWidth()) * 60;
            	   img13.scalePercent(scaler13);
   			   img13.setAlignment(Element.ALIGN_RIGHT);
   			   img13.setAbsolutePosition(200, 300);
   			   nomeCenario = qtdimgs2.get(0).toString();
   			   
   			   Utils utils = new Utils(driver);
 //  			   String SO = Utils.validaAmbiente();
   			   
 //  			   renomeiaPrints(SO, projeto);
   			   
   			   document.newPage();
   			   document.add(img);
   			   document.add(imgbandeira);
   			   document.add(new Paragraph("\n"));
   			   document.add(new Paragraph("\n"));
   			   document.add(new Paragraph("\n"));
   			   document.add(new Paragraph("\n"));
   			   document.add(new Paragraph("\n"));
   			   document.add(new Paragraph("     ____________________________________________________________________________________"));
   			   document.add(new Paragraph("\n"));
   			   
//   			                           CONFIGURANDO TITULO DA PAGINA COM O PRINT DA HOME
   			   
   			   String tituloHome = "SISTEMA TESTADO";
   			   Paragraph titulo1 = new Paragraph();
			   titulo1.setFont(FontFactory.getFont(FontFactory.COURIER, 16));
			   titulo1.setAlignment(Element.ALIGN_CENTER);
			   titulo1.add(tituloHome);
			   document.add(titulo1);
			   
//			   							FIM DA CONFIGURAÇÃO DO TITULO DA PAGINA COM O PRINT DA HOME
			   
   			   document.add(new Paragraph("     ____________________________________________________________________________________"));
			   document.add(new Paragraph("\n"));
   			   document.add(new Paragraph("   "+nomeCenario));
   			   document.add(img13);
//     		 }
     		document.add(imgfooter);
     		  	
     		  	for (int j = 0; j < qtdimgs.size(); j++) {
     			   Image img3 = Image.getInstance(qtdimgs.get(j).toString());
     			   System.out.println(qtdimgs.get(j).toString());
     			   float scaler3 = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - indentation) / img.getWidth()) * 60;
              	   img3.scalePercent(scaler3);
     			   img3.setAlignment(Element.ALIGN_RIGHT);
     			   img3.setAbsolutePosition(200, 300);
              	   nomeCenario = qtdimgs.get(j).toString();
              	   
   //           	   renomeiaPrints(SO, projeto);
 
     			   document.newPage();
     			   document.add(img);
     			   document.add(imgbandeira);
     			   document.add(new Paragraph("\n"));
     			   document.add(new Paragraph("\n"));
     			   document.add(new Paragraph("\n"));
     			   document.add(new Paragraph("\n"));
     			   document.add(new Paragraph("\n"));
     			   document.add(new Paragraph("     ____________________________________________________________________________________"));
     			   document.add(new Paragraph("\n"));
     			   
//                 					 CONFIGURANDO TITULO DA PAGINA COM O PRINT DOS ERROS ENCONTRADOS NO BUILD
     			   
     			   String tituloErros = "ERROS ENCONTRADOS";
     			   Paragraph titulo2 = new Paragraph();
     			   titulo2.setFont(FontFactory.getFont(FontFactory.COURIER, 16));
     			   titulo2.setAlignment(Element.ALIGN_CENTER);
     			   titulo2.add(tituloErros);
     			   document.add(titulo2);
     			   
//									FIM DA CONFIGURAÇÃO DO TITULO DA PAGINA COM O PRINT DOS ERROS DO BUILD
     			   
      			   document.add(new Paragraph("     ____________________________________________________________________________________"));
     			   document.add(new Paragraph("   "+nomeCenario));
     			   document.add(img3);

//              --------------------------------------AJUSTANDO POSICIONAMENTO DO FOOTER DAS PAGES DE IMG ANEXOS ERROS ------------------------------  
                document.add(imgfooter);

				}	
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        document.close();   
        
	}
	
	public static void renomeiaPrints(String SO, String projeto){
		if(SO.contains("Windows")){   
			   if(nomeCenario.contains(caminhoWindowsPRD)){
				  nomeCenario = nomeCenario.replace(caminhoWindowsPRD, ""); 
			   }else{
				  nomeCenario = nomeCenario.replace(caminhoWindowsDEV, "");
			   }
		   }else{
			nomeCenario = nomeCenario.replace(projeto + "/relatorios/img/", "");   
		   }
	}
}