package com.scopix.periscope.extractionmanagement;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;

import org.apache.log4j.Logger;

@Entity
@SuppressWarnings("serial")
public class VadaroPeopleCountingExtractionRequest extends EvidenceExtractionRequest {

	public static final int DELAY = 18;
    private static Logger log = Logger.getLogger(VadaroPeopleCountingExtractionRequest.class);

    @Override
    public Date getExtractionStartTime() {
        log.info("start");
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getRequestedTime());
        cal.add(Calendar.MINUTE, DELAY);

        log.info("end, cal.getTime(): [" + cal.getTime() + "]");
        return cal.getTime();
    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return true;
    }
}