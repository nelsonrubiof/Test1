/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.periscopefoundation.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class SortUtil {

    /**
     * Retorna una lista ordenada por el Map<String, Boolean> donde el key es la columna
     * y el Boolean determina si es reversible o no
     * @param cols LinkedHashMap con columnas a ordenar llave es nombre de columna y valor Corresponde a boolena de reversible
     * @param lista lista que se desea ordenar
     */
    public static void sortByColumn(LinkedHashMap<String, Boolean> cols, List<?> lista) {
        ComparatorChain comparatorChain = new ComparatorChain();
        for (String key : cols.keySet()) {
            Boolean reverse = cols.get(key);
            if (reverse == Boolean.TRUE) {
                comparatorChain.addComparator(new ReverseComparator(new BeanComparator(key, new NullComparator())));
            } else {
                comparatorChain.addComparator(new BeanComparator(key, new NullComparator()));
            }
        }
        Collections.sort(lista, comparatorChain);
    }
}
