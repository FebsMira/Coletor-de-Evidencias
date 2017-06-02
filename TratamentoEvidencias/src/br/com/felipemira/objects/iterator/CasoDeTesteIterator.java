package br.com.felipemira.objects.iterator;

import java.util.Map;

import br.com.felipemira.objects.object.CasoDeTeste;

public class CasoDeTesteIterator {
	
	public static void gravarDados(CasoDeTeste casoDeTeste, int indice, Object dado){
		
		switch(indice){
			case 1:
				casoDeTeste.setSiglaCasoDeTeste(String.valueOf(dado));
				break;
			case 2:
				casoDeTeste.setIdCasoDeTeste(String.valueOf(dado));
				break;
			case 3:
				casoDeTeste.setItemDeReferencia(String.valueOf(dado));
				break;
			case 4:
				casoDeTeste.setCenarioDeTeste(String.valueOf(dado));
				break;
			case 5:
				casoDeTeste.setCasoDeTesteCondicao(String.valueOf(dado));
				break;
			case 6:
				casoDeTeste.setRegressaoObrigatoria(String.valueOf(dado));
				break;
			case 7:
				Map<Integer, String> preCondicoes = casoDeTeste.getPreCondicoes();
				preCondicoes.put(preCondicoes.size() + 1, (String.valueOf(dado)));
				casoDeTeste.setPreCondicoes(preCondicoes);
				break;
			case 8:
				Map<Integer, String> procedimentosExecucao = casoDeTeste.getProcedimentosDeExecucao();
				procedimentosExecucao.put(procedimentosExecucao.size() + 1, String.valueOf(dado));
				casoDeTeste.setProcedimentosDeExecucao(procedimentosExecucao);
				break;
			case 9:
				Map<Integer, String> resultadosEsperados = casoDeTeste.getResultadoEsperado();
				resultadosEsperados.put(resultadosEsperados.size() + 1, String.valueOf(dado));
				casoDeTeste.setResultadoEsperado(resultadosEsperados);
				break;
			case 10:
				casoDeTeste.setOndeComoValidarResultado(String.valueOf(dado));
				break;
			case 11:
				casoDeTeste.setNomeDoTestador(String.valueOf(dado));
				break;
			case 12:
				casoDeTeste.setObservacoes(String.valueOf(dado));
				break;
			}
	}
}
