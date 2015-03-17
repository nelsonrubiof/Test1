package com.scopix.periscope.model;

import java.io.Serializable;
import java.util.List;

/**
 * Clase para referenciar datos de las oficinas de la aplicación
 *
 * @author carlos polo
 * @version 2.0.5
 * @since 6.0
 */
public class Office implements Serializable, Comparable<Office> {

    private String name;
    private List<Equivalence> equivalences;
    private static final long serialVersionUID = 3323763829849801178L;

    @Override
    public int compareTo(Office office) {
        int result = 0;
        if (this.getName() != null) {
            String name2 = office.getName();
            if (name2 != null) {
                result = this.getName().compareToIgnoreCase(name2);
            }
        }
        return result;
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
     * @return the equivalences
     */
    public List<Equivalence> getEquivalences() {
        return equivalences;
    }

    /**
     * @param equivalences the equivalences to set
     */
    public void setEquivalences(List<Equivalence> equivalences) {
        this.equivalences = equivalences;
    }
}