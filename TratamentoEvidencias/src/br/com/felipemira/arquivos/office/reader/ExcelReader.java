package br.com.felipemira.arquivos.office.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.felipemira.objects.iterator.CasoDeTesteIterator;
import br.com.felipemira.objects.object.CasoDeTeste;
import br.com.felipemira.objects.object.Error;

public class ExcelReader {
	
	//Apenas para verificar se existe formulas no excel;
	private static Workbook workbook;
	
	private static String caminhoExcel;
	
	/**
	 * Recebe uma planilha excel, que sera preparada para a leitura.
	 * @param caminhoExcel - String.
	 */
	public ExcelReader(String caminhoExcel){
		ExcelReader.caminhoExcel = caminhoExcel;
	}
	
	/**
	 * La o excel apartir dos parametros passados. Deve receber a linha de inicio e a linha final para leitura, assim como a coluna de inicio e a coluna final.
	 * @param linhaInicio - int.
	 * @param linhaFim - int.
	 * @throws IOException
	 */
	
	public Map<Integer, CasoDeTeste> readerDelimited(int linhaInicio, int linhaFim){
		try{
			
			//instancia um Map que retornara para a classe que chamou o metodo os casos de teste que o Excel contem, nas linhas delimitadas.
			Map<Integer, CasoDeTeste> listaCasosDeTeste = new HashMap<Integer, CasoDeTeste>();
			
			//Leitor de arquivos recebe o excel informado.
			FileInputStream inputStream = new FileInputStream(new File(caminhoExcel));
			
			//O Workbook do POI recebe o excel.
			Workbook workbook = new XSSFWorkbook(inputStream);
			
			//Mando a instancia de workbook para ExcelReader.workbook, sera usada para verificar formulas.
			ExcelReader.workbook = workbook;
			
			//Aba do excel a ser selecionada.
			Sheet sheet = workbook.getSheetAt(0);
			
			//A lista de casos de teste recebe o Map com todos os casos de teste lidos.
			listaCasosDeTeste = readerRows(workbook, sheet, linhaInicio, linhaFim);
			
			workbook.close();
			inputStream.close();
			
			return listaCasosDeTeste;
		}catch(Exception ex){
			Error.error = ex.getMessage().toString();
			return null;
		}
	}
	
	/**
	 * Percorre o excel delimitado por linha e coluna, e chama o metodo readerCell passando as colunas que devem ser lidas.
	 * @param workbook - Workbook.
	 * @param sheet - Sheet.
	 * @param linhaInicio - int.
	 * @param linhaFim - int.
	 */
	private static Map<Integer, CasoDeTeste> readerRows(Workbook workbook, Sheet sheet, int linhaInicio, int linhaFim){
		Sheet firstSheet = sheet;
		
		Row row;
		
		//Inicio das colunas do Caso de Teste
		int colunaInicio = 2;
		//Fim das colunas do Caso de Teste
		int colunaFim = 13;
		
		@SuppressWarnings("rawtypes")
		Iterator iterator = firstSheet.rowIterator();
		
		int countLinha = 0;
		
		//Instancia de um objeto CasoDeTeste, para ser preenchida e adicionada a lista.
		CasoDeTeste casoDeTeste = new CasoDeTeste();
		//Recebe o negocio da celula D7 do excel.
		String negocio = readerRow(workbook, firstSheet, 7, 4);
		//Insere o negocio no caso de teste.
		casoDeTeste.setNegocio(negocio);
		
		
		//Contador responsavel por contar a quantidade dos casos de testes e de atribuir a chave para o Map.
		int countCasosDeTeste = 0;
		
		//Lista que recebera os casos de testes.
		Map<Integer, CasoDeTeste> listaCasosDeTeste = new HashMap<Integer, CasoDeTeste>();
		
		//Percorre as linhas informadas.
		while((iterator.hasNext() && countLinha == 0) || (iterator.hasNext() && countLinha <= (linhaFim - 1))){
			row = (Row) iterator.next();
			
			if(countLinha >= (linhaInicio-1) && countLinha <= (linhaFim - 1)){
				//readerCells(row, colunaInicio, colunaFim);
				
				//Le cada uma das celulas da linha, se nao encontrar nada na celula 8 ou 9 e sinalizado que acabou os passos do caso de teste.
				if(readerCellsDelimited(casoDeTeste, row, colunaInicio, colunaFim) || row.getRowNum() == (linhaFim - 1)){
					//Se o Caso de Teste chegou ao fim implementa o contador, que e o controle para adicao de elementos no Map.
					countCasosDeTeste ++;
					//Preenche o Map com o caso de teste, com a Key do contador.
					listaCasosDeTeste.put(countCasosDeTeste, casoDeTeste);
					//Cria uma nova instancia para o objeto casoDeTeste.
					casoDeTeste = new CasoDeTeste();
					//Insere o negocio no caso de teste.
					casoDeTeste.setNegocio(negocio);
				}
			}
			countLinha ++;
		}
		return listaCasosDeTeste;
	}
	
	/**
	 * Le e grava os parametros dentro do objeto caso de teste. Retorna true ao chegar no final do caso de teste.
	 * @param casoDeTeste - CasoDeTeste.
	 * @param row - Row.
	 * @param colunaInicio int.
	 * @param colunaFim - int.
	 * @return boolean - se acabou o caso de teste retorna true;
	 */
	private static boolean readerCellsDelimited(CasoDeTeste casoDeTeste, Row row, int colunaInicio, int colunaFim){
		//Vari�vel que ir� controlar se o caso de teste chegou ao fim.
		boolean finalCasoDeTeste = false;
		
		//Adicionado para pegar a coluna D7 onde cont�m o neg�cio relacionado a RTF
		
		
		for(int i = colunaInicio - 1; i < colunaFim; i++){
			if(row.getCell(i) != null){
				
				
				
				if(row.getCell(i).getCellTypeEnum() == CellType.STRING){
					
					//Se o elemento que est� sendo puxado do excel n�o for vazio ele adicionar� o elemento ao caso de teste passado na chamada do m�todo.
					if(!row.getCell(i).getStringCellValue().equals("")){
						CasoDeTesteIterator.gravarDados(casoDeTeste, i, row.getCell(i).getStringCellValue());
					}
					
				}else if(row.getCell(i).getCellTypeEnum() == CellType.NUMERIC){
					
					//Se o elemento que est� sendo puxado do excel n�o for vazio ele adicionar� o elemento ao caso de teste passado na chamada do m�todo.
					if(!String.valueOf(row.getCell(i).getNumericCellValue()).equals("")){
						CasoDeTesteIterator.gravarDados(casoDeTeste, i, String.valueOf(row.getCell(i).getNumericCellValue()));
					}
					
				}else if(row.getCell(i).getCellTypeEnum() == CellType.FORMULA){
					if(!String.valueOf(row.getCell(i).getCellFormula()).equals("")){
						//Verifico as formulas.
						FormulaEvaluator evaluator = ExcelReader.workbook.getCreationHelper().createFormulaEvaluator();
						CellValue cellValue = evaluator.evaluate(row.getCell(i));
						
						switch (cellValue.getCellTypeEnum()) {
							case BOOLEAN:
								CasoDeTesteIterator.gravarDados(casoDeTeste, i, String.valueOf(cellValue.getBooleanValue()));
							    break;
							case NUMERIC:
								CasoDeTesteIterator.gravarDados(casoDeTeste, i, String.valueOf(cellValue.getNumberValue()));
							    break;
							case STRING:
								CasoDeTesteIterator.gravarDados(casoDeTeste, i, String.valueOf(cellValue.getStringValue()));
							    break;
							case BLANK:
							case ERROR:
							// CELL_TYPE_FORMULA will never happen
							case FORMULA: 
							    break;
							default:
								break;
						}
					}
					
				//Se o elemento for em branco ir� verificar os �ndices 8 e 9.
				}else if(row.getCell(i).getCellTypeEnum() == CellType.BLANK){
					switch(i){
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
							break;
						//Se for a c�lula com �ndice 8 ou 9, que sinalizam os passos e resultados do caso de teste, sinaliza para a fun��o que chamou que o Caso de Teste chegou ao fim.
						case 8:
						case 9:
							finalCasoDeTeste = true;
							break;
						case 10:
						case 11:
						case 12:
							break;
					}	
				}
				
			//Para efeito de c�lulas null, o leitor passar� sobre elas sem nenhum problema.
			}else{
				switch(i){
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
						break;
					//Se for a c�lula com �ndice 8 ou 9, que sinalizam os passos e resultados do caso de teste, sinaliza para a fun��o que chamou que o Caso de Teste chegou ao fim.
					case 8:
					case 9:
						finalCasoDeTeste = true;
						break;
					case 10:
					case 11:
					case 12:
						break;
				}	
			}
		}
		
		//Retorna se o caso de teste chegou ao fim.
		if(finalCasoDeTeste){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Percorre o excel para pegar o conteudo de uma inica celula em uma inica coluna.
	 * @param workbook - Workbook
	 * @param linha - Int
	 * @param coluna -Int
	 * @return String - Contendo dado.
	 */
	private static String readerRow(Workbook workbook, Sheet sheet, int linha, int coluna){
		String negocio = null;
		Sheet firstSheet = sheet;
		
		Row row;
		
		@SuppressWarnings("rawtypes")
		Iterator iterator = firstSheet.rowIterator();
		
		int countLinha = 0;
		
		//Percorre as linhas informadas.
		while((iterator.hasNext() && countLinha <= (linha-1) )){
			row = (Row) iterator.next();
			
			if(countLinha == (linha-1)){
				negocio = readerCell(row, coluna);
			}
			countLinha ++;
		}
		return negocio;
	}
	
	/**
	 * Leitor de uma celula do Excel.
	 * @param row - Row.
	 * @param colunaInicio - int.
	 * @return String - contendo dado da celula.
	 */
	private static String readerCell(Row row, int coluna){
		String dado = null;
		
		coluna = coluna - 1;
			
		if(row.getCell(coluna).getCellTypeEnum() == CellType.STRING){
			
			//Se o elemento que est� sendo puxado do excel n�o for vazio ele guardar� na String.
			if(!row.getCell(coluna).getStringCellValue().equals("")){
				dado =  row.getCell(coluna).getStringCellValue();
			}
			
		}else if(row.getCell(coluna).getCellTypeEnum() == CellType.NUMERIC){
			
			//Se o elemento que est� sendo puxado do excel n�o for vazio ele guardar� na String.
			if(!String.valueOf(row.getCell(coluna).getNumericCellValue()).equals("")){
				dado = String.valueOf(row.getCell(coluna).getNumericCellValue());
			}
		}else if(row.getCell(coluna).getCellTypeEnum() == CellType.FORMULA){
			if(!String.valueOf(row.getCell(coluna).getCellFormula()).equals("")){
				//Verifico as formulas.
				FormulaEvaluator evaluator = ExcelReader.workbook.getCreationHelper().createFormulaEvaluator();
				CellValue cellValue = evaluator.evaluate(row.getCell(coluna));
				
				switch (cellValue.getCellTypeEnum()) {
					case BOOLEAN:
						dado = String.valueOf(cellValue.getBooleanValue());
					    break;
					case NUMERIC:
						dado = String.valueOf(cellValue.getNumberValue());
					    break;
					case STRING:
						dado = String.valueOf(cellValue.getStringValue());
					    break;
					case BLANK:
					case ERROR:
					// CELL_TYPE_FORMULA will never happen
					case FORMULA: 
					    break;
					default:
						break;
				}
			}
		}
		return dado;
	}
}
