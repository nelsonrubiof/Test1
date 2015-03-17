package com.scopix.periscope.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Clase para referenciar datos de las métricas de las evaluaciones
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class Metric implements Comparable<Metric>, Serializable {

    private String name;
    private String type;
    private Integer order;
    private String tFinal;
    private String tInicial;
    // changed, sirve para validar si una métrica previamente evaluada de forma automática
    // fue nuevamente evaluada de forma manual cambiando su valor
    private boolean changed;
    private Integer metricId;
    private boolean cantEval;
    private String urlTFinal;
    private boolean evaluada;
    private boolean multiple;
    private boolean automatic;
    private String numMetrica;
    private String cantEvalObs;
    private String urlTInicial;
    private String currentTime;
    private String description;
    private String evalInstruction;
    private Date endEvaluationTime;
    private Date startEvaluationTime;
    private String currentEvidenceId;
    private int currentCameraId = -1;
    private String snapshotTimeFinal;
    private String snapshotTimeInicial;
    private Integer evidenceEvaluationDTOId = null;
    private List<Proof> proofs = new ArrayList<Proof>();
    private List<Evidence> evidencias = new ArrayList<Evidence>();
    private static final long serialVersionUID = 4812643655329850347L;
    private HashMap<Integer, String> circlesInfo = new HashMap<Integer, String>();
    private HashMap<Integer, String> squaresInfo = new HashMap<Integer, String>();
    private HashMap<Integer, String> tiempoMarcas = new HashMap<Integer, String>();
    private HashMap<Integer, String> noSquaresInfo = new HashMap<Integer, String>();
    private HashMap<Integer, String> yesSquaresInfo = new HashMap<Integer, String>();
    private HashMap<Integer, String> iniSquaresInfo = new HashMap<Integer, String>();
    private HashMap<Integer, String> finSquaresInfo = new HashMap<Integer, String>();
    private HashMap<Integer, String> numberInputEval = new HashMap<Integer, String>();
    private HashMap<Integer, String> tiempoMarcasFinal = new HashMap<Integer, String>();
    private HashMap<Integer, String> tiempoMarcasInicial = new HashMap<Integer, String>();

    @Override
    public int compareTo(Metric metrica) {
        if (this.getOrder() != null) {
            Integer order2 = metrica.getOrder();

            if (order2 != null) {
                if (this.getOrder() < order2) {
                    return -1;
                } else if (this.getOrder() > order2) {
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * @return the metricId
     */
    public Integer getMetricId() {
        return metricId;
    }

    /**
     * @param metricId the metricId to set
     */
    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the evalInstruction
     */
    public String getEvalInstruction() {
        return evalInstruction;
    }

    /**
     * @param evalInstruction the evalInstruction to set
     */
    public void setEvalInstruction(String evalInstruction) {
        this.evalInstruction = evalInstruction;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the evidencias
     */
    public List<Evidence> getEvidencias() {
        return evidencias;
    }

    /**
     * @param evidencias the evidencias to set
     */
    public void setEvidencias(List<Evidence> evidencias) {
        this.evidencias = evidencias;
    }

    /**
     * @return the currentEvidenceId
     */
    public String getCurrentEvidenceId() {
        return currentEvidenceId;
    }

    /**
     * @param currentEvidenceId the currentEvidenceId to set
     */
    public void setCurrentEvidenceId(String currentEvidenceId) {
        this.currentEvidenceId = currentEvidenceId;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the numMetrica
     */
    public String getNumMetrica() {
        return numMetrica;
    }

    /**
     * @param numMetrica the numMetrica to set
     */
    public void setNumMetrica(String numMetrica) {
        this.numMetrica = numMetrica;
    }

    /**
     * @return the multiple
     */
    public boolean isMultiple() {
        return multiple;
    }

    /**
     * @param multiple the multiple to set
     */
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    /**
     * @return the evaluada
     */
    public boolean isEvaluada() {
        return evaluada;
    }

    /**
     * @param evaluada the evaluada to set
     */
    public void setEvaluada(boolean evaluada) {
        this.evaluada = evaluada;
    }

    /**
     * @return the tFinal
     */
    public String gettFinal() {
        return tFinal;
    }

    /**
     * @param tFinal the tFinal to set
     */
    public void settFinal(String tFinal) {
        this.tFinal = tFinal;
    }

    /**
     * @return the tInicial
     */
    public String gettInicial() {
        return tInicial;
    }

    /**
     * @param tInicial the tInicial to set
     */
    public void settInicial(String tInicial) {
        this.tInicial = tInicial;
    }

    /**
     * @return the urlTFinal
     */
    public String getUrlTFinal() {
        return urlTFinal;
    }

    /**
     * @param urlTFinal the urlTFinal to set
     */
    public void setUrlTFinal(String urlTFinal) {
        this.urlTFinal = urlTFinal;
    }

    /**
     * @return the urlTInicial
     */
    public String getUrlTInicial() {
        return urlTInicial;
    }

    /**
     * @param urlTInicial the urlTInicial to set
     */
    public void setUrlTInicial(String urlTInicial) {
        this.urlTInicial = urlTInicial;
    }

    /**
     * @return the cantEval
     */
    public boolean isCantEval() {
        return cantEval;
    }

    /**
     * @param cantEval the cantEval to set
     */
    public void setCantEval(boolean cantEval) {
        this.cantEval = cantEval;
    }

    /**
     * @return the cantEvalObs
     */
    public String getCantEvalObs() {
        return cantEvalObs;
    }

    /**
     * @param cantEvalObs the cantEvalObs to set
     */
    public void setCantEvalObs(String cantEvalObs) {
        this.cantEvalObs = cantEvalObs;
    }

    /**
     * @return the circlesInfo
     */
    public HashMap<Integer, String> getCirclesInfo() {
        return circlesInfo;
    }

    /**
     * @param circlesInfo the circlesInfo to set
     */
    public void setCirclesInfo(HashMap<Integer, String> circlesInfo) {
        this.circlesInfo = circlesInfo;
    }

    /**
     * @return the squaresInfo
     */
    public HashMap<Integer, String> getSquaresInfo() {
        return squaresInfo;
    }

    /**
     * @param squaresInfo the squaresInfo to set
     */
    public void setSquaresInfo(HashMap<Integer, String> squaresInfo) {
        this.squaresInfo = squaresInfo;
    }

    /**
     * @return the noSquaresInfo
     */
    public HashMap<Integer, String> getNoSquaresInfo() {
        return noSquaresInfo;
    }

    /**
     * @param noSquaresInfo the noSquaresInfo to set
     */
    public void setNoSquaresInfo(HashMap<Integer, String> noSquaresInfo) {
        this.noSquaresInfo = noSquaresInfo;
    }

    /**
     * @return the yesSquaresInfo
     */
    public HashMap<Integer, String> getYesSquaresInfo() {
        return yesSquaresInfo;
    }

    /**
     * @param yesSquaresInfo the yesSquaresInfo to set
     */
    public void setYesSquaresInfo(HashMap<Integer, String> yesSquaresInfo) {
        this.yesSquaresInfo = yesSquaresInfo;
    }

    /**
     * @return the currentTime
     */
    public String getCurrentTime() {
        return currentTime;
    }

    /**
     * @param currentTime the currentTime to set
     */
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * @return the currentCameraId
     */
    public int getCurrentCameraId() {
        return currentCameraId;
    }

    /**
     * @param currentCameraId the currentCameraId to set
     */
    public void setCurrentCameraId(int currentCameraId) {
        this.currentCameraId = currentCameraId;
    }

    /**
     * @return the order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * @return the startEvaluationTime
     */
    public Date getStartEvaluationTime() {
        return startEvaluationTime;
    }

    /**
     * @param startEvaluationTime the startEvaluationTime to set
     */
    public void setStartEvaluationTime(Date startEvaluationTime) {
        this.startEvaluationTime = startEvaluationTime;
    }

    /**
     * @return the endEvaluationTime
     */
    public Date getEndEvaluationTime() {
        return endEvaluationTime;
    }

    /**
     * @param endEvaluationTime the endEvaluationTime to set
     */
    public void setEndEvaluationTime(Date endEvaluationTime) {
        this.endEvaluationTime = endEvaluationTime;
    }

    /**
     * @return the proofs
     */
    public List<Proof> getProofs() {
        return proofs;
    }

    /**
     * @param proofs the proofs to set
     */
    public void setProofs(List<Proof> proofs) {
        this.proofs = proofs;
    }

    /**
     * @return the numberInputEval
     */
    public HashMap<Integer, String> getNumberInputEval() {
        return numberInputEval;
    }

    /**
     * @param numberInputEval the numberInputEval to set
     */
    public void setNumberInputEval(HashMap<Integer, String> numberInputEval) {
        this.numberInputEval = numberInputEval;
    }

    /**
     * @return the tiempoMarcas
     */
    public HashMap<Integer, String> getTiempoMarcas() {
        return tiempoMarcas;
    }

    /**
     * @param tiempoMarcas the tiempoMarcas to set
     */
    public void setTiempoMarcas(HashMap<Integer, String> tiempoMarcas) {
        this.tiempoMarcas = tiempoMarcas;
    }

    /**
     * @return the iniSquaresInfo
     */
    public HashMap<Integer, String> getIniSquaresInfo() {
        return iniSquaresInfo;
    }

    /**
     * @param iniSquaresInfo the iniSquaresInfo to set
     */
    public void setIniSquaresInfo(HashMap<Integer, String> iniSquaresInfo) {
        this.iniSquaresInfo = iniSquaresInfo;
    }

    /**
     * @return the finSquaresInfo
     */
    public HashMap<Integer, String> getFinSquaresInfo() {
        return finSquaresInfo;
    }

    /**
     * @param finSquaresInfo the finSquaresInfo to set
     */
    public void setFinSquaresInfo(HashMap<Integer, String> finSquaresInfo) {
        this.finSquaresInfo = finSquaresInfo;
    }

    /**
     * @return the tiempoMarcasFinal
     */
    public HashMap<Integer, String> getTiempoMarcasFinal() {
        return tiempoMarcasFinal;
    }

    /**
     * @param tiempoMarcasFinal the tiempoMarcasFinal to set
     */
    public void setTiempoMarcasFinal(HashMap<Integer, String> tiempoMarcasFinal) {
        this.tiempoMarcasFinal = tiempoMarcasFinal;
    }

    /**
     * @return the tiempoMarcasInicial
     */
    public HashMap<Integer, String> getTiempoMarcasInicial() {
        return tiempoMarcasInicial;
    }

    /**
     * @param tiempoMarcasInicial the tiempoMarcasInicial to set
     */
    public void setTiempoMarcasInicial(HashMap<Integer, String> tiempoMarcasInicial) {
        this.tiempoMarcasInicial = tiempoMarcasInicial;
    }

    /**
     * @return the snapshotTimeInicial
     */
    public String getSnapshotTimeInicial() {
        return snapshotTimeInicial;
    }

    /**
     * @param snapshotTimeInicial the snapshotTimeInicial to set
     */
    public void setSnapshotTimeInicial(String snapshotTimeInicial) {
        this.snapshotTimeInicial = snapshotTimeInicial;
    }

    /**
     * @return the snapshotTimeFinal
     */
    public String getSnapshotTimeFinal() {
        return snapshotTimeFinal;
    }

    /**
     * @param snapshotTimeFinal the snapshotTimeFinal to set
     */
    public void setSnapshotTimeFinal(String snapshotTimeFinal) {
        this.snapshotTimeFinal = snapshotTimeFinal;
    }

    public Integer getEvidenceEvaluationDTOId() {
        return evidenceEvaluationDTOId;
    }

    public void setEvidenceEvaluationDTOId(Integer evidenceEvaluationDTOId) {
        this.evidenceEvaluationDTOId = evidenceEvaluationDTOId;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }
}