package com.scopix.periscope.evaluationmanagement.evaluators;

import org.apache.log4j.Logger;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * 
 * @author Carlos
 *
 */
public class EvaluationExpirationThread implements Runnable {
	
	private Integer pendingEvaluationId;
	private CommonEvaluationManager commonEvalManager;
	private static Logger log = Logger.getLogger(EvaluationExpirationThread.class);
	
	public EvaluationExpirationThread(Integer pendingEvaluationId) {
		log.info("start, pendingEvalId: [" + pendingEvaluationId + "]");
		setPendingEvaluationId(pendingEvaluationId);
		log.info("end");
	}
	
	@Override
	public void run() {
		log.info("start, pendingEvalId: [" + getPendingEvaluationId() + "]");
		try {
			Thread.sleep(120000); //waits for 2 minutes

			getCommonEvalManager().expirePendingEvaluation(getPendingEvaluationId());

		} catch (InterruptedException e) {
			log.warn(e.getMessage(), e);
		} catch (ScopixException e) {
			log.error("Exception expiring pendingEvaluation: "
					+ "["+e.getMessage()+"], pendingEvalId: ["+getPendingEvaluationId()+"]", e);
		}		
		log.info("end, pendingEvalId: [" + getPendingEvaluationId() + "]");
	}

	/**
	 * @return the pendingEvaluation
	 */
	public Integer getPendingEvaluationId() {
		return pendingEvaluationId;
	}

	/**
	 * @param pendingEvaluation the pendingEvaluation to set
	 */
	public void setPendingEvaluationId(Integer pendingEvaluation) {
		this.pendingEvaluationId = pendingEvaluation;
	}
	
	/**
	 * @return the commonEvalManager
	 */
	public CommonEvaluationManager getCommonEvalManager() {
		if(commonEvalManager==null){
			commonEvalManager = SpringSupport.getInstance().findBeanByClassName(CommonEvaluationManager.class);
		}
		return commonEvalManager;
	}
}