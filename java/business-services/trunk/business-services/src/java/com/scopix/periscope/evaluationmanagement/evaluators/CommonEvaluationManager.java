package com.scopix.periscope.evaluationmanagement.evaluators;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.scopix.periscope.evaluationmanagement.EvaluationQueue;
import com.scopix.periscope.evaluationmanagement.EvaluationQueueManager;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.commands.AddEvidenceEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetPendingEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.UpdatePendingEvaluationCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.states.exception.InvalidOperationException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;

@SpringBean(rootClass = CommonEvaluationManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class CommonEvaluationManager {

    private ExecutorService executor = null;
    private static final String SYSTEM_USERNAME = "SYSTEM";
    private static Logger log = Logger.getLogger(CommonEvaluationManager.class);

    /**
     * Checks if pending evaluation is live and has expired
     *
     * @param pendingEvaluation
     * @param userName
     * @return boolean
     * @throws ScopixException
     */
    public boolean checkExpiredPendingEvaluation(PendingEvaluation pendingEvaluation, String userName) throws ScopixException {
        log.info("start, pendingEvaluationId: [" + pendingEvaluation.getId() + "], userName: [" + userName + "]");

        SituationTemplate situationTemplate = pendingEvaluation.getObservedSituation().getSituation().getSituationTemplate();
        boolean isLive = situationTemplate.isLive();

        //if situation template is live, validates expiration time
        if (!isLive) {
            return false;
        }

        long delayTimeInMillis = situationTemplate.getDelayInMinutes() * 60 * 1000;

        //gets evidence date
        Date evidenceDate = pendingEvaluation.getObservedSituation().getObservedMetrics().get(0).getEvidences().get(0)
            .getEvidenceDate();
        String timeZoneId = pendingEvaluation.getObservedSituation().getObservedMetrics().get(0).getMetric().getStore()
            .getTimeZoneId();

        //convert evidence date to server date
        double diff = TimeZoneUtils.getDiffHourTimezoneToServer(timeZoneId);
        Date serverDate = new Date(evidenceDate.getTime());
        serverDate = DateUtils.addHours(serverDate, (int) diff);

        Date currentTime = Calendar.getInstance().getTime();
        Long elapsedTime = currentTime.getTime() - serverDate.getTime();

        log.debug("currentTime: [" + currentTime + "], delayTimeInMillis: [" + delayTimeInMillis + "], evidenceDate: ["
            + evidenceDate + "], evidenceDate in server: [" + serverDate + "], timeZoneDiff: [" + diff
            + "], elapsedTime (miliseconds): [" + elapsedTime + "]");

        if (elapsedTime < delayTimeInMillis) {
            return false;
        }

        asingToCheckPendingEvaluation(pendingEvaluation, userName);

        log.debug("preparing execution of evalExpirationThread, pendingEvalId: [" + pendingEvaluation.getId() + "]");
        getExecutor().execute(new EvaluationExpirationThread(pendingEvaluation.getId()));
        log.info("end");

        return true;
    }

    public void asingToCheckPendingEvaluation(PendingEvaluation pendingEvaluation, String userName) throws
        InvalidOperationException, ScopixException {
        log.info("start [pendingEvaluation:" + pendingEvaluation.getId() + "]");
        pendingEvaluation.setEvaluationQueue(EvaluationQueue.OPERATOR);
        //changes evaluation state from default to enqueued
        pendingEvaluation.setEvaluationState(EvaluationState.CHECKING);
        //if userName is null will register the system username
        pendingEvaluation.setUserName(userName == null ? SYSTEM_USERNAME : userName);

        //sets pending evaluation to checking in order to grab this evaluation
//        pendingEvaluation.asignToCheck();
        updatePendingEvaluation(pendingEvaluation);

        log.debug("pending evaluation [id:" + pendingEvaluation.getId() + "]"
            + "[state:" + pendingEvaluation.getStateType().getName() + "]");

        log.info("end");
    }

    /**
     * Finishes the evaluation as cant'do
     *
     * @param pendingEvaluation
     * @throws ScopixException
     */
    public void expirePendingEvaluation(PendingEvaluation pendingEvaluation) throws ScopixException {
        log.info("start, pendingEvaluationId: [" + pendingEvaluation.getId() + "]");
        EvaluationQueueManager evaluationQueueManager = SpringSupport.getInstance().findBeanByClassName(
            EvaluationQueueManager.class);

        Date now = new Date();
        pendingEvaluation.setEvaluationStartDate(now);
        pendingEvaluation.setEvaluationEndDate(now);

        List<EvidenceEvaluation> evidenceEvaluations = new ArrayList<EvidenceEvaluation>();

        for (ObservedMetric observedMetric : pendingEvaluation.getObservedSituation().getObservedMetrics()) {
            //if evidence_evaluation not exist 
            evaluate(observedMetric, pendingEvaluation);

            log.debug("after evaluate om.getEvidenceEvaluations: [" + observedMetric.getEvidenceEvaluations().size() + "]");
            evidenceEvaluations.addAll(observedMetric.getEvidenceEvaluations());

            //change state from observedmetric to enqueued
            observedMetric.setEvaluationState(EvaluationState.ENQUEUED);
            evaluationQueueManager.enqueue(observedMetric, EvaluationQueue.METRIC);
        }

        AddEvidenceEvaluationCommand addEvidenceEvaluationCommand = new AddEvidenceEvaluationCommand();
        for (EvidenceEvaluation evidenceEval : evidenceEvaluations) {
            addEvidenceEvaluationCommand.execute(evidenceEval);
        }

        //change state to Finished 
        pendingEvaluation.computeResult();
        log.info("end");
    }

    public void expirePendingEvaluation(Integer pendingEvaluationId) throws ScopixException {
        log.info("start");
        GetPendingEvaluationCommand command = new GetPendingEvaluationCommand();
        PendingEvaluation pendingEvaluation = command.execute(pendingEvaluationId);
        log.debug("pending evaluation [id:" + pendingEvaluationId + "]"
            + "[state:" + pendingEvaluation.getStateType().getName() + "]");
        //finishes the evaluation as cant'do
        expirePendingEvaluation(pendingEvaluation);

        if (!pendingEvaluation.getEvaluationState().equals(EvaluationState.FINISHED)) {
            pendingEvaluation.setEvaluationState(EvaluationState.FINISHED);
        }
        updatePendingEvaluation(pendingEvaluation);
        log.info("end");
    }

    private void evaluate(ObservedMetric observedMetric, PendingEvaluation pendingEvaluation) {
        log.info("start, pendingEvalId: [" + pendingEvaluation.getId() + "], observedMetric evidences: ["
            + observedMetric.getEvidences().size() + "]");
        List<EvidenceEvaluation> evaluations = new ArrayList<EvidenceEvaluation>();

        for (Evidence evidence : observedMetric.getEvidences()) {
            log.debug("[evidenceId: [" + evidence.getId() + "], evidencePath: [" + evidence.getEvidencePath() + "]");
            Date currentDate = new Date();
            EvidenceEvaluation ee = existEvaluation(observedMetric, evidence);
            if (ee == null) {
                //verify exist evaluation for evidence in observed_metrics
                // generates evidence evaluation            
                EvidenceEvaluation evidenceEvaluation = genEvidenceEvaluation(evidence, observedMetric, pendingEvaluation.getId(),
                    pendingEvaluation.getUserName(), currentDate, currentDate);

                evidenceEvaluation.setObservedMetric(observedMetric);
                evaluations.add(evidenceEvaluation);
            } else {
                ee.setObservedMetric(observedMetric);
                evaluations.add(ee);

            }
        }
        observedMetric.getEvidenceEvaluations().addAll(evaluations);
    }

    private EvidenceEvaluation existEvaluation(ObservedMetric observedMetric, Evidence evidence) {
        log.info("start verify exist evaluation [om:" + observedMetric.getId() + "][evidence:" + evidence.getId() + "]");
        EvidenceEvaluation ret = null;
        for (EvidenceEvaluation ee : observedMetric.getEvidenceEvaluations()) {
            for (Evidence e : ee.getEvidences()) {
                if (!ee.isRejected()) {
                    if (e.getId().equals(evidence.getId())) {
                        ret = ee;
                        break;
                    }
                }
            }
        }
        log.info("end [ret:" + ret + "]");
        return ret;
    }

    private EvidenceEvaluation genEvidenceEvaluation(Evidence evidence, ObservedMetric observedMetric, Integer pendingEvalId,
        String evaluationUser, Date initEvaluation, Date endEvaluation) {

        log.info("start, evidenceId: [" + evidence.getId() + "], observedMetricId: [" + observedMetric.getId() + "], "
            + "pendingEvalId: [" + pendingEvalId + "], evaluationUser: [" + evaluationUser + "]");
        EvidenceEvaluation evidenceEvaluation = new EvidenceEvaluation();
        evidenceEvaluation.setEvaluationDate(new Date());
        evidenceEvaluation.setEvaluationUser(evaluationUser);
        evidenceEvaluation.setEvidenceResult(null);
        evidenceEvaluation.setCantDoReason("LIVE DELAYED");
        evidenceEvaluation.setEvaluationTimeInSeconds(0l);
        evidenceEvaluation.setRejected(false);
        evidenceEvaluation.getEvidences().add(evidence);
        evidenceEvaluation.setObservedMetric(observedMetric);
        evidenceEvaluation.setPendingEvaluation(new PendingEvaluation());
        evidenceEvaluation.getPendingEvaluation().setId(pendingEvalId);

        evidenceEvaluation.setInitEvaluation(initEvaluation);
        evidenceEvaluation.setEndEvaluation(endEvaluation);

        log.info("end");
        return evidenceEvaluation;
    }

    public void updatePendingEvaluation(PendingEvaluation pendingEvaluation) throws ScopixException {
        log.info("start");
        if (pendingEvaluation == null) {
            // PENDING error message
            throw new ScopixException("PENDING");
        }
        UpdatePendingEvaluationCommand command = new UpdatePendingEvaluationCommand();
        command.execute(pendingEvaluation);
        log.info("end");
    }

    /**
     * @return the executor
     */
    public ExecutorService getExecutor() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(10);
        }
        return executor;
    }
}
