/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * ObservedSituationEvaluation.java
 *
 * Created on 27-03-2008, 02:38:11 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.NotNull;

/**
 *
 * @author C�sar Abarza Suazo
 */
@Entity
public class ObservedSituationEvaluation extends BusinessObject {

    @ManyToOne
    private ObservedSituation observedSituation;

    private Double evaluationResult;

    private Integer compliant;

    @Lob
    private String ruleName;

    //Guardar en orden
    private Double metric1;
    private Integer metricId1;
    private Double metric2;
    private Integer metricId2;
    private Double metric3;
    private Integer metricId3;
    private Double metric4;
    private Integer metricId4;
    private Double metric5;
    private Integer metricId5;
    private Double metric6;
    private Integer metricId6;
    private Double metric7;
    private Integer metricId7;
    private Double metric8;
    private Integer metricId8;
    private Double metric9;
    private Integer metricId9;
    private Double metric10;
    private Integer metricId10;
    private Double metric11;
    private Integer metricId11;
    private Double metric12;
    private Integer metricId12;
    private Double metric13;
    private Integer metricId13;
    private Double metric14;
    private Integer metricId14;
    private Double metric15;
    private Integer metricId15;
    private Double metric16;
    private Integer metricId16;
    private Double metric17;
    private Integer metricId17;
    private Double metric18;
    private Integer metricId18;
    private Double metric19;
    private Integer metricId19;
    private Double metric20;
    private Integer metricId20;
    private Double metric21;
    private Integer metricId21;
    private Double metric22;
    private Integer metricId22;
    private Double metric23;
    private Integer metricId23;
    private Double metric24;
    private Integer metricId24;
    private Double metric25;
    private Integer metricId25;
    private Double metric26;
    private Integer metricId26;
    private Double metric27;
    private Integer metricId27;
    private Double metric28;
    private Integer metricId28;
    private Double metric29;
    private Integer metricId29;
    private Double metric30;
    private Integer metricId30;
    private Double metric31;
    private Integer metricId31;
    private Double metric32;
    private Integer metricId32;
    private Double metric33;
    private Integer metricId33;
    private Double metric34;
    private Integer metricId34;
    private Double metric35;
    private Integer metricId35;
    private Double metric36;
    private Integer metricId36;
    private Double metric37;
    private Integer metricId37;
    private Double metric38;
    private Integer metricId38;
    private Double metric39;
    private Integer metricId39;
    private Double metric40;
    private Integer metricId40;
    private Double metric41;
    private Integer metricId41;
    private Double metric42;
    private Integer metricId42;
    private Double metric43;
    private Integer metricId43;
    private Double metric44;
    private Integer metricId44;
    private Double metric45;
    private Integer metricId45;
    private Double metric46;
    private Integer metricId46;
    private Double metric47;
    private Integer metricId47;
    private Double metric48;
    private Integer metricId48;
    private Double metric49;
    private Integer metricId49;
    private Double metric50;
    private Integer metricId50;
    
    //Variables para la regla
    private Double var1;

    private Double var2;

    private Double var3;

    private Double var4;

    private Double var5;

    private Double var6;

    private Double var7;

    private Double var8;

    private Double var9;

    private Double var10;
    
    @NotNull
    private boolean sentToMIS;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentToMISDate;

    private Double target;
    
    private Double standard;
    
    private Integer metricCount;
    
    @Lob
    private String department;
    
    @Lob
    private String product;
    
    private Integer areaId;
    
    private Integer storeId;
    
    @Lob
    private String state;
    @Temporal(TemporalType.TIMESTAMP)
    private Date evaluationDate;
    

    public ObservedSituation getObservedSituation() {
        return observedSituation;
    }

    public void setObservedSituation(ObservedSituation observedSituation) {
        this.observedSituation = observedSituation;
    }

    public Integer getCompliant() {
        return compliant;
    }

    public void setCompliant(Integer compliant) {
        this.compliant = compliant;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Double getMetric1() {
        return metric1;
    }

    public void setMetric1(Double metric1) {
        this.metric1 = metric1;
    }

    public Double getMetric2() {
        return metric2;
    }

    public void setMetric2(Double metric2) {
        this.metric2 = metric2;
    }

    public Double getMetric3() {
        return metric3;
    }

    public void setMetric3(Double metric3) {
        this.metric3 = metric3;
    }

    public Double getMetric4() {
        return metric4;
    }

    public void setMetric4(Double metric4) {
        this.metric4 = metric4;
    }

    public Double getMetric5() {
        return metric5;
    }

    public void setMetric5(Double metric5) {
        this.metric5 = metric5;
    }

    public Double getMetric6() {
        return metric6;
    }

    public void setMetric6(Double metric6) {
        this.metric6 = metric6;
    }

    public Double getMetric7() {
        return metric7;
    }

    public void setMetric7(Double metric7) {
        this.metric7 = metric7;
    }

    public Double getMetric8() {
        return metric8;
    }

    public void setMetric8(Double metric8) {
        this.metric8 = metric8;
    }

    public Double getMetric9() {
        return metric9;
    }

    public void setMetric9(Double metric9) {
        this.metric9 = metric9;
    }

    public Double getMetric10() {
        return metric10;
    }

    public void setMetric10(Double metric10) {
        this.metric10 = metric10;
    }

    public Double getVar1() {
        return var1;
    }

    public void setVar1(Double var1) {
        this.var1 = var1;
    }

    public Double getVar2() {
        return var2;
    }

    public void setVar2(Double var2) {
        this.var2 = var2;
    }

    public Double getVar3() {
        return var3;
    }

    public void setVar3(Double var3) {
        this.var3 = var3;
    }

    public Double getVar4() {
        return var4;
    }

    public void setVar4(Double var4) {
        this.var4 = var4;
    }

    public Double getVar5() {
        return var5;
    }

    public void setVar5(Double var5) {
        this.var5 = var5;
    }

    public Double getVar6() {
        return var6;
    }

    public void setVar6(Double var6) {
        this.var6 = var6;
    }

    public Double getVar7() {
        return var7;
    }

    public void setVar7(Double var7) {
        this.var7 = var7;
    }

    public Double getVar8() {
        return var8;
    }

    public void setVar8(Double var8) {
        this.var8 = var8;
    }

    public Double getVar9() {
        return var9;
    }

    public void setVar9(Double var9) {
        this.var9 = var9;
    }

    public Double getVar10() {
        return var10;
    }

    public void setVar10(Double var10) {
        this.var10 = var10;
    }

    public Double getEvaluationResult() {
        return evaluationResult;
    }

    public void setEvaluationResult(Double evaluationResult) {
        this.evaluationResult = evaluationResult;
    }

    public boolean isSentToMIS() {
        return sentToMIS;
    }

    public void setSentToMIS(boolean sentToMIS) {
        this.sentToMIS = sentToMIS;
    }

    public Date getSentToMISDate() {
        return sentToMISDate;
    }

    public void setSentToMISDate(Date sentToMISDate) {
        this.sentToMISDate = sentToMISDate;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public Integer getMetricCount() {
        return metricCount;
    }

    public void setMetricCount(Integer metricCount) {
        this.metricCount = metricCount;
    }

    public Integer getMetricId1() {
        return metricId1;
    }

    public void setMetricId1(Integer metricId1) {
        this.metricId1 = metricId1;
    }

    public Integer getMetricId2() {
        return metricId2;
    }

    public void setMetricId2(Integer metricId2) {
        this.metricId2 = metricId2;
    }

    public Integer getMetricId3() {
        return metricId3;
    }

    public Integer getMetricId4() {
        return metricId4;
    }

    public void setMetricId4(Integer metricId4) {
        this.metricId4 = metricId4;
    }

    public Integer getMetricId5() {
        return metricId5;
    }

    public void setMetricId5(Integer metricId5) {
        this.metricId5 = metricId5;
    }

    public Integer getMetricId6() {
        return metricId6;
    }

    public void setMetricId6(Integer metricId6) {
        this.metricId6 = metricId6;
    }

    public Integer getMetricId7() {
        return metricId7;
    }

    public void setMetricId7(Integer metricId7) {
        this.metricId7 = metricId7;
    }

    public Integer getMetricId8() {
        return metricId8;
    }

    public void setMetricId8(Integer metricId8) {
        this.metricId8 = metricId8;
    }

    public Integer getMetricId9() {
        return metricId9;
    }

    public void setMetricId9(Integer metricId9) {
        this.metricId9 = metricId9;
    }

    public Integer getMetricId10() {
        return metricId10;
    }

    public void setMetricId10(Integer metricId10) {
        this.metricId10 = metricId10;
    }

    public void setMetricId3(Integer metricId3) {
        this.metricId3 = metricId3;
    }

    public Double getStandard() {
        return standard;
    }

    public void setStandard(Double standard) {
        this.standard = standard;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the evaluationDate
     */
    public Date getEvaluationDate() {
        return evaluationDate;
    }

    /**
     * @param evaluationDate the evaluationDate to set
     */
    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public Double getMetric11() {
        return metric11;
    }

    public void setMetric11(Double metric11) {
        this.metric11 = metric11;
    }

    public Integer getMetricId11() {
        return metricId11;
    }

    public void setMetricId11(Integer metricId11) {
        this.metricId11 = metricId11;
    }

    public Double getMetric12() {
        return metric12;
    }

    public void setMetric12(Double metric12) {
        this.metric12 = metric12;
    }

    public Integer getMetricId12() {
        return metricId12;
    }

    public void setMetricId12(Integer metricId12) {
        this.metricId12 = metricId12;
    }

    public Double getMetric13() {
        return metric13;
    }

    public void setMetric13(Double metric13) {
        this.metric13 = metric13;
    }

    public Integer getMetricId13() {
        return metricId13;
    }

    public void setMetricId13(Integer metricId13) {
        this.metricId13 = metricId13;
    }

    public Double getMetric14() {
        return metric14;
    }

    public void setMetric14(Double metric14) {
        this.metric14 = metric14;
    }

    public Integer getMetricId14() {
        return metricId14;
    }

    public void setMetricId14(Integer metricId14) {
        this.metricId14 = metricId14;
    }

    public Double getMetric15() {
        return metric15;
    }

    public void setMetric15(Double metric15) {
        this.metric15 = metric15;
    }

    public Integer getMetricId15() {
        return metricId15;
    }

    public void setMetricId15(Integer metricId15) {
        this.metricId15 = metricId15;
    }

    public Double getMetric16() {
        return metric16;
    }

    public void setMetric16(Double metric16) {
        this.metric16 = metric16;
    }

    public Integer getMetricId16() {
        return metricId16;
    }

    public void setMetricId16(Integer metricId16) {
        this.metricId16 = metricId16;
    }

    public Double getMetric17() {
        return metric17;
    }

    public void setMetric17(Double metric17) {
        this.metric17 = metric17;
    }

    public Integer getMetricId17() {
        return metricId17;
    }

    public void setMetricId17(Integer metricId17) {
        this.metricId17 = metricId17;
    }

    public Double getMetric18() {
        return metric18;
    }

    public void setMetric18(Double metric18) {
        this.metric18 = metric18;
    }

    public Integer getMetricId18() {
        return metricId18;
    }

    public void setMetricId18(Integer metricId18) {
        this.metricId18 = metricId18;
    }

    public Double getMetric19() {
        return metric19;
    }

    public void setMetric19(Double metric19) {
        this.metric19 = metric19;
    }

    public Integer getMetricId19() {
        return metricId19;
    }

    public void setMetricId19(Integer metricId19) {
        this.metricId19 = metricId19;
    }

    public Double getMetric20() {
        return metric20;
    }

    public void setMetric20(Double metric20) {
        this.metric20 = metric20;
    }

    public Integer getMetricId20() {
        return metricId20;
    }

    public void setMetricId20(Integer metricId20) {
        this.metricId20 = metricId20;
    }

    public Double getMetric21() {
        return metric21;
    }

    public void setMetric21(Double metric21) {
        this.metric21 = metric21;
    }

    public Integer getMetricId21() {
        return metricId21;
    }

    public void setMetricId21(Integer metricId21) {
        this.metricId21 = metricId21;
    }

    public Double getMetric22() {
        return metric22;
    }

    public void setMetric22(Double metric22) {
        this.metric22 = metric22;
    }

    public Integer getMetricId22() {
        return metricId22;
    }

    public void setMetricId22(Integer metricId22) {
        this.metricId22 = metricId22;
    }

    public Double getMetric23() {
        return metric23;
    }

    public void setMetric23(Double metric23) {
        this.metric23 = metric23;
    }

    public Integer getMetricId23() {
        return metricId23;
    }

    public void setMetricId23(Integer metricId23) {
        this.metricId23 = metricId23;
    }

    public Double getMetric24() {
        return metric24;
    }

    public void setMetric24(Double metric24) {
        this.metric24 = metric24;
    }

    public Integer getMetricId24() {
        return metricId24;
    }

    public void setMetricId24(Integer metricId24) {
        this.metricId24 = metricId24;
    }

    public Double getMetric25() {
        return metric25;
    }

    public void setMetric25(Double metric25) {
        this.metric25 = metric25;
    }

    public Integer getMetricId25() {
        return metricId25;
    }

    public void setMetricId25(Integer metricId25) {
        this.metricId25 = metricId25;
    }

    public Double getMetric26() {
        return metric26;
    }

    public void setMetric26(Double metric26) {
        this.metric26 = metric26;
    }

    public Integer getMetricId26() {
        return metricId26;
    }

    public void setMetricId26(Integer metricId26) {
        this.metricId26 = metricId26;
    }

    public Double getMetric27() {
        return metric27;
    }

    public void setMetric27(Double metric27) {
        this.metric27 = metric27;
    }

    public Integer getMetricId27() {
        return metricId27;
    }

    public void setMetricId27(Integer metricId27) {
        this.metricId27 = metricId27;
    }

    public Double getMetric28() {
        return metric28;
    }

    public void setMetric28(Double metric28) {
        this.metric28 = metric28;
    }

    public Integer getMetricId28() {
        return metricId28;
    }

    public void setMetricId28(Integer metricId28) {
        this.metricId28 = metricId28;
    }

    public Double getMetric29() {
        return metric29;
    }

    public void setMetric29(Double metric29) {
        this.metric29 = metric29;
    }

    public Integer getMetricId29() {
        return metricId29;
    }

    public void setMetricId29(Integer metricId29) {
        this.metricId29 = metricId29;
    }

    public Double getMetric30() {
        return metric30;
    }

    public void setMetric30(Double metric30) {
        this.metric30 = metric30;
    }

    public Integer getMetricId30() {
        return metricId30;
    }

    public void setMetricId30(Integer metricId30) {
        this.metricId30 = metricId30;
    }

    public Double getMetric31() {
        return metric31;
    }

    public void setMetric31(Double metric31) {
        this.metric31 = metric31;
    }

    public Integer getMetricId31() {
        return metricId31;
    }

    public void setMetricId31(Integer metricId31) {
        this.metricId31 = metricId31;
    }

    public Double getMetric32() {
        return metric32;
    }

    public void setMetric32(Double metric32) {
        this.metric32 = metric32;
    }

    public Integer getMetricId32() {
        return metricId32;
    }

    public void setMetricId32(Integer metricId32) {
        this.metricId32 = metricId32;
    }

    public Double getMetric33() {
        return metric33;
    }

    public void setMetric33(Double metric33) {
        this.metric33 = metric33;
    }

    public Integer getMetricId33() {
        return metricId33;
    }

    public void setMetricId33(Integer metricId33) {
        this.metricId33 = metricId33;
    }

    public Double getMetric34() {
        return metric34;
    }

    public void setMetric34(Double metric34) {
        this.metric34 = metric34;
    }

    public Integer getMetricId34() {
        return metricId34;
    }

    public void setMetricId34(Integer metricId34) {
        this.metricId34 = metricId34;
    }

    public Double getMetric35() {
        return metric35;
    }

    public void setMetric35(Double metric35) {
        this.metric35 = metric35;
    }

    public Integer getMetricId35() {
        return metricId35;
    }

    public void setMetricId35(Integer metricId35) {
        this.metricId35 = metricId35;
    }

    public Double getMetric36() {
        return metric36;
    }

    public void setMetric36(Double metric36) {
        this.metric36 = metric36;
    }

    public Integer getMetricId36() {
        return metricId36;
    }

    public void setMetricId36(Integer metricId36) {
        this.metricId36 = metricId36;
    }

    public Double getMetric37() {
        return metric37;
    }

    public void setMetric37(Double metric37) {
        this.metric37 = metric37;
    }

    public Integer getMetricId37() {
        return metricId37;
    }

    public void setMetricId37(Integer metricId37) {
        this.metricId37 = metricId37;
    }

    public Double getMetric38() {
        return metric38;
    }

    public void setMetric38(Double metric38) {
        this.metric38 = metric38;
    }

    public Integer getMetricId38() {
        return metricId38;
    }

    public void setMetricId38(Integer metricId38) {
        this.metricId38 = metricId38;
    }

    public Double getMetric39() {
        return metric39;
    }

    public void setMetric39(Double metric39) {
        this.metric39 = metric39;
    }

    public Integer getMetricId39() {
        return metricId39;
    }

    public void setMetricId39(Integer metricId39) {
        this.metricId39 = metricId39;
    }

    public Double getMetric40() {
        return metric40;
    }

    public void setMetric40(Double metric40) {
        this.metric40 = metric40;
    }

    public Integer getMetricId40() {
        return metricId40;
    }

    public void setMetricId40(Integer metricId40) {
        this.metricId40 = metricId40;
    }

    public Double getMetric41() {
        return metric41;
    }

    public void setMetric41(Double metric41) {
        this.metric41 = metric41;
    }

    public Integer getMetricId41() {
        return metricId41;
    }

    public void setMetricId41(Integer metricId41) {
        this.metricId41 = metricId41;
    }

    public Double getMetric42() {
        return metric42;
    }

    public void setMetric42(Double metric42) {
        this.metric42 = metric42;
    }

    public Integer getMetricId42() {
        return metricId42;
    }

    public void setMetricId42(Integer metricId42) {
        this.metricId42 = metricId42;
    }

    public Double getMetric43() {
        return metric43;
    }

    public void setMetric43(Double metric43) {
        this.metric43 = metric43;
    }

    public Integer getMetricId43() {
        return metricId43;
    }

    public void setMetricId43(Integer metricId43) {
        this.metricId43 = metricId43;
    }

    public Double getMetric44() {
        return metric44;
    }

    public void setMetric44(Double metric44) {
        this.metric44 = metric44;
    }

    public Integer getMetricId44() {
        return metricId44;
    }

    public void setMetricId44(Integer metricId44) {
        this.metricId44 = metricId44;
    }

    public Double getMetric45() {
        return metric45;
    }

    public void setMetric45(Double metric45) {
        this.metric45 = metric45;
    }

    public Integer getMetricId45() {
        return metricId45;
    }

    public void setMetricId45(Integer metricId45) {
        this.metricId45 = metricId45;
    }

    public Double getMetric46() {
        return metric46;
    }

    public void setMetric46(Double metric46) {
        this.metric46 = metric46;
    }

    public Integer getMetricId46() {
        return metricId46;
    }

    public void setMetricId46(Integer metricId46) {
        this.metricId46 = metricId46;
    }

    public Double getMetric47() {
        return metric47;
    }

    public void setMetric47(Double metric47) {
        this.metric47 = metric47;
    }

    public Integer getMetricId47() {
        return metricId47;
    }

    public void setMetricId47(Integer metricId47) {
        this.metricId47 = metricId47;
    }

    public Double getMetric48() {
        return metric48;
    }

    public void setMetric48(Double metric48) {
        this.metric48 = metric48;
    }

    public Integer getMetricId48() {
        return metricId48;
    }

    public void setMetricId48(Integer metricId48) {
        this.metricId48 = metricId48;
    }

    public Double getMetric49() {
        return metric49;
    }

    public void setMetric49(Double metric49) {
        this.metric49 = metric49;
    }

    public Integer getMetricId49() {
        return metricId49;
    }

    public void setMetricId49(Integer metricId49) {
        this.metricId49 = metricId49;
    }

    public Double getMetric50() {
        return metric50;
    }

    public void setMetric50(Double metric50) {
        this.metric50 = metric50;
    }

    public Integer getMetricId50() {
        return metricId50;
    }

    public void setMetricId50(Integer metricId50) {
        this.metricId50 = metricId50;
    }
}
