package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

public class ArquivoUtils {
	private static final Logger logger = LogManager.getLogger();
	public static String arquivo = null;
	
	public static void gravaDadosDaURLNoArquivo(WebDriver driver, File file) {	
		try {
			if (file.isFile()) {
			}
			else {
			}
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			for (LogEntry entry : driver.manage().logs().get(LogType.PERFORMANCE)) {
				pw.println(entry.toString());
	 		}
			pw.close();
		} 
		catch (Exception e) {
			logger.error("ERROR: Não foi possível gravar os dados no arquivo."+ e.getMessage());
		}
	}
   static final int TAMANHO_BUFFER = 4096; // 4kb
   
   public static void compactarParaZip(String arqSaida, List<String> arquivosEntrada) throws IOException {
		int cont;
		byte[] dados = new byte[TAMANHO_BUFFER];
		BufferedInputStream origem = null;
		FileInputStream streamDeEntrada = null;
		FileOutputStream destino = null;
		ZipOutputStream saida = null;
		ZipEntry entry = null;
		try {
			destino = new FileOutputStream(new File(arqSaida));
			saida = new ZipOutputStream(new BufferedOutputStream(destino));
			for (String arqEntrada : arquivosEntrada) {
				File file = new File(arqEntrada);
				streamDeEntrada = new FileInputStream(file);
				origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
				entry = new ZipEntry(file.getName());
				saida.putNextEntry(entry);
				while ((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
					saida.write(dados, 0, cont);
				}
				origem.close();
			}
			saida.close();
		}
		catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}
   
   public static long obterTamanhoArquivoMb(String arquivo){
	   File file = new File(arquivo);
	   
	   return obterTamanhoArquivoMb(file);
   }
   
   public static long obterTamanhoArquivoMb(File file){
	   long size = file.length();
	   size = (size / 1024) / 1024;
	  
	   return size;
   }
   
   public static String lerArquivo(String arquivo) {
		logger.info("Lendo arquivo " + arquivo);
		String retorno = "";
		byte[] encod = null;
		try {
			Path file = Paths.get(arquivo);
			if (file.toFile().exists()) {
				encod = Files.readAllBytes(file);
				retorno = new String(encod, StandardCharsets.ISO_8859_1);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			logger.error("Erro ao ler arquivo " + arquivo + ".");
		}
		return retorno;
	}
   
   public static void limparDiretorio(String diretorio) {
		File folder = new File(diretorio);
		remover(folder);
	}

	public static void remover (File f) {
       if (f.isDirectory()) {
       	File[] sun = f.listFiles();
			for (File toDelete : sun) {
				remover(toDelete);
			}
       }
       f.delete();
   }
}