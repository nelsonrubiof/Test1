/*
 * 
 * Copyright 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 */

/**
 * Oct 2, 2006 - 10:44:49 AM
 */
package com.scopix.periscope.periscopefoundation.states.clients;

import com.scopix.periscope.periscopefoundation.states.configs.StateType;

/**
 * This interface represents objects which have states. Objects whith states
 * modify actual state based on operations performed on them sobre ellos. Object
 * state {@link Stateful} created by {@link StatefulGenerator} is managed
 * automatically as result of methods calls.<br>
 * For this we need to add meta-information to the class indicating posible
 * graph of states transitions (see {@link StatefulConfiguration}) and
 * indicating which methods corresponds to state transitions (see
 * {@link StateOperation}).By this way, a method call which represents an
 * operation will validate that it can be executed based on present state and
 * after method call it will change state based on defined graph..
 * 
 * This interface defines 2 methods that must be implemented to keep object
 * state. Is resposability of subclasses to determine better way to persist
 * state.
 * 
 * @author maximiliano.vazquez
 */
public interface Stateful<E extends Enum<? extends StateType>> {

  /**
   * Changes object state. This method should be called only by
   * {@link StatefulInterceptor}
   * 
   */
  void changeStateType(E stateType);

  /**
   * Get present object state
   */
  E getStateType();

}
