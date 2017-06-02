package br.com.felipemira.objects.object;

import java.util.HashMap;
import java.util.Map;

public class CasoDeTeste {
	
	private String negocio;
	private String siglaCasoDeTeste;
	private String idCasoDeTeste;
	private String itemDeReferencia;
	private String cenarioDeTeste;
	private String casoDeTesteCondicao;
	private String regressaoObrigatoria;
	private Map<Integer, String> preCondicoes;
	private Map<Integer, String> procedimentosDeExecucao;
	private Map<Integer, String> resultadoEsperado;
	private String ondeComoValidarResultado;
	private String nomeDoTestador;
	private String observacoes;
	
	public CasoDeTeste() {
		super();
		preCondicoes = new HashMap<Integer, String>();
		procedimentosDeExecucao = new HashMap<Integer, String>();
		resultadoEsperado = new HashMap<Integer, String>();
	}
	
	public String getNegocio() {
		return negocio;
	}
	public void setNegocio(String negocio) {
		this.negocio = negocio;
	}
	public String getSiglaCasoDeTeste() {
		return siglaCasoDeTeste;
	}
	public void setSiglaCasoDeTeste(String siglaCasoDeTeste) {
		this.siglaCasoDeTeste = siglaCasoDeTeste;
	}
	public String getIdCasoDeTeste() {
		return idCasoDeTeste;
	}
	public void setIdCasoDeTeste(String idCasoDeTeste) {
		this.idCasoDeTeste = idCasoDeTeste;
	}
	public String getItemDeReferencia() {
		return itemDeReferencia;
	}
	public void setItemDeReferencia(String itemDeReferencia) {
		this.itemDeReferencia = itemDeReferencia;
	}
	public String getCenarioDeTeste() {
		return cenarioDeTeste;
	}
	public void setCenarioDeTeste(String cenarioDeTeste) {
		this.cenarioDeTeste = cenarioDeTeste;
	}
	public String getCasoDeTesteCondicao() {
		return casoDeTesteCondicao;
	}
	public void setCasoDeTesteCondicao(String casoDeTesteCondicao) {
		this.casoDeTesteCondicao = casoDeTesteCondicao;
	}
	public String getRegressaoObrigatoria() {
		return regressaoObrigatoria;
	}
	public void setRegressaoObrigatoria(String regressaoObrigatoria) {
		this.regressaoObrigatoria = regressaoObrigatoria;
	}
	public Map<Integer, String> getPreCondicoes() {
		return preCondicoes;
	}
	public void setPreCondicoes(Map<Integer, String> preCondicoes) {
		this.preCondicoes = preCondicoes;
	}
	public Map<Integer, String> getProcedimentosDeExecucao() {
		return procedimentosDeExecucao;
	}
	public void setProcedimentosDeExecucao(Map<Integer, String> procedimentosDeExecucao) {
		this.procedimentosDeExecucao = procedimentosDeExecucao;
	}
	public Map<Integer, String> getResultadoEsperado() {
		return resultadoEsperado;
	}
	public void setResultadoEsperado(Map<Integer, String> resultadoEsperado) {
		this.resultadoEsperado = resultadoEsperado;
	}
	public String getOndeComoValidarResultado() {
		return ondeComoValidarResultado;
	}
	public void setOndeComoValidarResultado(String ondeComoValidarResultado) {
		this.ondeComoValidarResultado = ondeComoValidarResultado;
	}
	public String getNomeDoTestador() {
		return nomeDoTestador;
	}
	public void setNomeDoTestador(String nomeDoTestador) {
		this.nomeDoTestador = nomeDoTestador;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	
}
