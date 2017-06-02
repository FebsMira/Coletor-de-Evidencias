package br.com.felipemira.arquivos.office;

import java.awt.AWTException;
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import br.com.felipemira.arquivos.office.custom.document.CustomXWPFDocument;
import br.com.felipemira.arquivos.office.iterator.WordIterator;
import br.com.felipemira.copy.Copy;
import br.com.felipemira.objects.object.CasoDeTeste;
import br.com.felipemira.objects.object.Error;

public class WordAutomacao {
	
	private String nomeArquivo;
	@SuppressWarnings("unused")
	private String nomeComHora;
	private String horaExecucao;
	private Calendar horaInicio;
	@SuppressWarnings("unused")
	private String caminhoTemplate;
	private String caminhoImagem;
	private String caminhoDoc;
	private int countFalhou = 0;
	private CustomXWPFDocument document;
	
	/**
	 * Cria um documento Word atraves do template.
	 * @param nomeArquivo - String.
	 * @param caminhoDoc - String.
	 * @param caminhoTemplate - String.
	 * @param caminhoImagem - String.
	 */
	public WordAutomacao(String nomeArquivo, String caminhoDoc, String caminhoTemplate, String caminhoImagem, CasoDeTeste casoDeTeste){
		
		this.nomeArquivo = nomeArquivo;
		this.caminhoDoc = caminhoDoc;
		this.caminhoTemplate = caminhoTemplate;
		this.caminhoImagem = caminhoImagem;
		
		try {
			criarDocumento(casoDeTeste);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public WordAutomacao(String caminhoDoc, String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
		this.caminhoDoc = caminhoDoc;
		
		CustomXWPFDocument document = null;
		
		try{
        	InputStream arquivo = new FileInputStream(caminhoDoc);
			CustomXWPFDocument copiaDocumento = new CustomXWPFDocument(arquivo);
	        document = copiaDocumento;
	        this.document = document;
    	}catch(Exception ex){
    		ex.printStackTrace();
    		Error.error = ex.getMessage().toString();
    	}
	}

	/**
	 * Pega o templete da evidencia e cria um novo documento word.
	 * @throws IOException
	 */
	private void criarDocumento(CasoDeTeste casoDeTeste){
		//Pega a hora que foi criado o documento.
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        this.horaInicio = cal;
        this.horaExecucao = dateFormat.format(cal.getTime());
        String date = this.horaExecucao.replaceAll("/", "_").replaceAll(" ", "_").replaceAll(":", "_");
        
        //Implementa o nome do arquivo de evidencias que sera criado.
        this.nomeComHora = nomeArquivo + "_" + date;
        
        //Defino para toda a classe o caminho da minha evidencia
        //this.caminhoDoc = this.caminhoDoc + this.nomeComHora + ".docx";
        
        //Atualizacao para nao ser colocado o dia e hora no nome do caso de teste.
        this.caminhoDoc = this.caminhoDoc + nomeArquivo + ".docx";
        
        //this.caminhoDoc = this.caminhoDoc.replaceAll("/", "_");
		CustomXWPFDocument document = null;
		
        //Crio um file com o template.
		
		@SuppressWarnings("static-access")
		InputStream in = ClassLoader.getSystemClassLoader().getSystemResourceAsStream("br/com/felipemira/arquivos/office/templates/Modelo Itau.docx");
		//BufferedReader input = new BufferedReader(new InputStreamReader(in));
        File file = Copy.streamTofile(in, "Modelo", "Itau.docx");
        
        //Faco uma copia do template renomeada com o nome da minha evidencia
        if(file.exists()) {
        	File copiaFile = new File(caminhoDoc);
        	//Se o arquivo ja existir ira deletar o mesmo.
        	if(copiaFile.exists()){
        		copiaFile.delete();
        	}
        	try{
        		Copy.copyFile(file, copiaFile);
            	InputStream arquivo = new FileInputStream(caminhoDoc);
    			CustomXWPFDocument copiaDocumento = new CustomXWPFDocument(arquivo);
    	        document = copiaDocumento;
    	        this.document = document;
        	}catch(Exception ex){
        		Error.error = ex.getMessage().toString();
        	}
        	
        }else{
        	Error.error = "Template nao consta na pasta informada!";
        	System.out.println("Template nao consta na pasta informada!");
        }
        
        try{
        	//Insere o nome do CT na tabela um, primeira linha da primeira coluna.
            //WordIterator.inserirDadoTabela(this.caminhoDoc, document, 0, 0, 0, this.nomeArquivo);
            //Insere a hora de execucao na tabela um, primeira linha da segunda coluna.
            //WordIterator.inserirDadoTabela(this.caminhoDoc, document, 0, 0, 1, this.horaExecucao);
            
            //Insere o titulo no documento word.
            //WordIterator.inserirTitulo(this.caminhoDoc, document, this.nomeArquivo);
            
            WordIterator.inserirDadoTabela(this.caminhoDoc, document, 0, 0, 0, "Projeto - " + casoDeTeste.getNegocio(), true);
            //WordIterator.inserirDadoTabela(this.caminhoDoc, document, 0, 1, 3, this.horaExecucao.substring(0, 10), false);
            
            //WordIterator.inserirDadoTabela(this.caminhoDoc, document, 0, 2, 1, casoDeTeste.getSiglaCasoDeTeste() + " - " + casoDeTeste.getIdCasoDeTeste(), false);
            WordIterator.inserirDadoTabela(this.caminhoDoc, document, 0, 2, 1, casoDeTeste.getCasoDeTesteCondicao(), false);
            
            //Objetivo
            WordIterator.inserirDadoTabela(this.caminhoDoc, document, 0, 3, 1, casoDeTeste.getCenarioDeTeste(), false);
            //Resultado Esperado
            WordIterator.inserirDadoTabela(this.caminhoDoc, document, 0, 4, 1, casoDeTeste.getCenarioDeTeste(), false);
            //WordIterator.inserirDadoTabela(this.caminhoDoc, document, 0, 6, 1, (casoDeTeste.getNomeDoTestador() == null)? " - " : casoDeTeste.getNomeDoTestador(), false);
        }catch(Exception ex){
        	Error.error = ex.getMessage().toString();
        }
        
	}
	
	
	/**
	 * Insere uma evidencia no arquivo Word. Para gerar imagem e necessario passar true no parametro imagem.
	 * @param mensagem - String
	 * @param passouFalhou - Boolean
	 * @param imagem - Boolean
	 */
	public void inserirEvidencia(String mensagem, Boolean passouFalhou, Boolean imagem){
		if(passouFalhou){
			mensagem = mensagem + " - Passou";
		}else{
			mensagem = mensagem + " - Falhou";
			this.countFalhou = 1;
		}
		try {
			gerarEvidencia(mensagem, imagem);
		} catch (Exception e) {
			Error.error = e.getMessage().toString();
		}
	}
	
	/**
	 * Insere uma evidencia no arquivo Word. Para gerar imagem e necessario passar true no parametro imagem.
	 * @param mensagem - String
	 * @param passouFalhou - Boolean
	 * @param imagem - Boolean
	 */
	public void inserirEvidenciaManual(Boolean passouFalhou, Boolean imagem, int passo){
		String mensagem = "";
		if(passouFalhou){
			mensagem = "Passou";
		}else{
			mensagem = "Falhou";
			this.countFalhou = 1;
		}
		try {
			gerarEvidenciaManual(mensagem, imagem, passo);
		} catch (Exception e) {
			e.printStackTrace();
			Error.error = e.getMessage().toString();
		}
	}
	
	/**
	 * Gera uma evidencia e insere no arquivo Word.
	 * @param mensagem - String
	 * @param geraImagem - Boolean
	 * @throws IOException
	 * @throws AWTException
	 * @throws InvalidFormatException
	 */
	private void gerarEvidencia(String mensagem, Boolean geraImagem){
		try{
			if(geraImagem){
		    	WordIterator.inserirTexto(this.caminhoDoc, this.document, mensagem);
		    	WordIterator.inserirImagem(this.caminhoDoc, this.document, this.caminhoImagem);
		    }else{
		    	WordIterator.inserirTexto(this.caminhoDoc, this.document, mensagem);
		    }
		}catch(Exception ex){
			Error.error = ex.getMessage().toString();
		}
	    
	}
	
	/**
	 * Gera uma evidencia e insere no arquivo Word.
	 * @param mensagem - String
	 * @param geraImagem - Boolean
	 * @throws IOException
	 * @throws AWTException
	 * @throws InvalidFormatException
	 */
	private void gerarEvidenciaManual(String mensagem, Boolean geraImagem, int passo){
		try{
		    inserirDadoTabela(passo, 3, 1, mensagem, false);
		    inserirDadoImagemTabela(passo, 6, 0, this.caminhoImagem);
		   
		}catch(Exception ex){
			ex.printStackTrace();
			Error.error = ex.getMessage().toString();
		}
	    
	}
	
	/**
	 * Calcula a duracao da execucao.
	 * @return String.
	 */
	@SuppressWarnings("unused")
	private String calcularDuracao(){
        
        long diferenca = System.currentTimeMillis() - this.horaInicio.getTimeInMillis();
        long diferencaHoras = diferenca / (60 * 60 * 1000);
        long diferencaMin = (diferenca % (60 * 60 * 1000))/ (60 * 1000);
        long diferencaSeg = (diferenca % (60 * 60 * 1000))% (60 * 1000) / 1000;
        
        String horas = "";
        String minutos = "";
        String segundos = "";
        
        if(String.valueOf(diferencaHoras).length() == 1){
        	horas = "0" + String.valueOf(diferencaHoras);
        }else{
        	horas = String.valueOf(diferencaHoras);
        }
        
        if(String.valueOf(diferencaMin).length() == 1){
        	minutos = "0" + String.valueOf(diferencaMin);
        }else{
        	minutos = String.valueOf(diferencaMin);
        }
        
        if(String.valueOf(diferencaSeg).length() == 1){
        	segundos = "0" + String.valueOf(diferencaSeg);
        }else{
        	segundos = String.valueOf(diferencaSeg);
        }
        
        
        return horas + ":" + minutos + ":" + segundos;
	}
	
	/**
	 * Finaliza a evidencia e cria o arquivo PDF
	 * @throws IOException
	 */
	public void finalizarEvidencia(){
	    
		try{
	    	
			//Insere a duracao do CT na tabela um, segunda linha, segunda coluna.
		    //WordIterator.inserirDadoTabela(this.caminhoDoc, this.document, 0, 1, 1, calcularDuracao(), false);
		    
		    //Insere o Status do caso de teste na tabela.
		    if(this.countFalhou == 1){
		    	WordIterator.inserirDadoTabela(this.caminhoDoc, this.document, 0, 5, 1, "Falhou", false);
		    }else{
		    	WordIterator.inserirDadoTabela(this.caminhoDoc, this.document, 0, 5, 1, "Passou", false);
		    }
		    
		    //ConvertToPDF.convert(this.caminhoDoc, this.nomeComHora);
	    }
	    catch(Exception ex){
	    	ex.printStackTrace();
	    	Error.error = ex.getMessage().toString();
	    }
	}
	
	/**
	 * Insere dados em uma tabela especificada pelo numero.
	 * @param caminhoDoc - String com o caminho.
	 * @param documento - CustomXWPFDocument.
	 * @param numeroTabela - int - Comeca com 0.
	 * @param numeroLinha - int - comeca com 0.
	 * @param numeroColuna - int - comeca com 0.
	 * @param dado - String a ser inserida.
	 * @param negrito - Boolean - se o texto deve ser em negrito.
	 * @throws IOException
	 */
	public void inserirDadoTabela(int numeroTabela, int numeroLinha, int numeroColuna, String dado, boolean negrito){
		try {
			WordIterator.inserirDadoTabela(this.caminhoDoc, this.document, numeroTabela, numeroLinha, numeroColuna, dado, negrito);
		} catch (IOException e) {
			Error.error = e.getMessage().toString();
			e.printStackTrace();
		}
	}
	
	/**
	 * Insere dados em uma tabela especificada pelo numero.
	 * @param caminhoDoc - String com o caminho.
	 * @param documento - CustomXWPFDocument.
	 * @param numeroTabela - int - Comeca com 0.
	 * @param numeroLinha - int - comeca com 0.
	 * @param numeroColuna - int - comeca com 0.
	 * @param caminhoImagem - String - caminho da imagem.
	 * @throws IOException
	 */
	public void inserirDadoImagemTabela(int numeroTabela, int numeroLinha, int numeroColuna, String caminhoImagem){
		try {
			WordIterator.inserirImagemTabela(this.caminhoDoc, this.document, numeroTabela, numeroLinha, numeroColuna, caminhoImagem);
		} catch (Exception e) {
			Error.error = e.getMessage().toString();
			e.printStackTrace();
		}
	}

	public void newPage() {
		WordIterator.newPage(this.caminhoDoc, this.document);
		
	}

	public void newTableFuncional(int numeroIterator) {
		try {
			WordIterator.newTableFuncional(this.caminhoDoc, this.document, numeroIterator);
		} catch (Exception e) {
			Error.error = e.getMessage().toString();
			e.printStackTrace();
		}
		
	}
	
	public int getNumTables(){
		return WordIterator.getNumTables(this.caminhoDoc, this.document);
	}
}
