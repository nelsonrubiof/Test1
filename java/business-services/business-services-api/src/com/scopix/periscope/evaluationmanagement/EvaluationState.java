/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * EvaluationState.java
 *
 * Created on 20-05-2008, 01:12:58 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.states.configs.StateType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public enum EvaluationState implements StateType {

    FINISHED {

        @Override
        protected Boolean shouldBeReEnqueued() {
            return Boolean.FALSE;
        }

        @Override
        public Boolean isPriorityChangeable() {
            return Boolean.FALSE;
        }

        @Override
        protected Boolean stateForChange() {
            return Boolean.FALSE;
        }
    },
    DELETED {

        @Override
        protected Boolean shouldBeReEnqueued() {
            return Boolean.FALSE;
        }

        @Override
        public Boolean isPriorityChangeable() {
            return Boolean.TRUE;
        }

        @Override
        protected Boolean stateForChange() {
            return Boolean.TRUE;
        }
    },
    ENQUEUED {

        @Override
        protected Boolean shouldBeReEnqueued() {
            return Boolean.TRUE;
        }

        @Override
        public Boolean isPriorityChangeable() {
            return Boolean.TRUE;
        }

        @Override
        protected Boolean stateForChange() {
            return Boolean.TRUE;
        }
    },
    CHECKING {

        @Override
        protected Boolean shouldBeReEnqueued() {
            return Boolean.FALSE;
        }

        @Override
        public Boolean isPriorityChangeable() {
            return Boolean.FALSE;
        }

        @Override
        protected Boolean stateForChange() {
            return Boolean.TRUE;
        }
    },
    ERROR {

        @Override
        protected Boolean shouldBeReEnqueued() {
            return Boolean.FALSE;
        }

        @Override
        public Boolean isPriorityChangeable() {
            return Boolean.FALSE;
        }

        @Override
        protected Boolean stateForChange() {
            return Boolean.TRUE;
        }
    };

    protected abstract Boolean shouldBeReEnqueued();

    public abstract Boolean isPriorityChangeable();

    protected abstract Boolean stateForChange();

    public static List<EvaluationState> getStatesToBeReEnqueued() {
        List<EvaluationState> enumsToBeReEnqueued = new ArrayList<EvaluationState>();
        EvaluationState[] enums = values();
        for (EvaluationState enumm : enums) {
            if (enumm.shouldBeReEnqueued()) {
                enumsToBeReEnqueued.add(enumm);
            }
        }
        return enumsToBeReEnqueued;
    }

    public static List<EvaluationState> getStatesForChange() {
        List<EvaluationState> enumsForChange = new ArrayList<EvaluationState>();
        EvaluationState[] enums = values();
        for (EvaluationState enumm : enums) {
            if (enumm.stateForChange()) {
                enumsForChange.add(enumm);
            }
        }
        return enumsForChange;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String getName() {
        return this.toString();
    }
};
