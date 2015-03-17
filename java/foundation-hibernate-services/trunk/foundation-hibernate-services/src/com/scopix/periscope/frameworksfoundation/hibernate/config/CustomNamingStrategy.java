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
 */
package com.scopix.periscope.frameworksfoundation.hibernate.config;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * This class defines the strategy of naming for the tables in the db. <br>
 * It is based on the class {@link ImprovedNamingStrategy} provided by <br>
 * Hibernate and modifies following:
 * <ul>
 * <li>A preffix "rel_" is added to the relation tables </li>
 * <li>A suffix "_{referencedColumnName}" is added to the columns with FK </li>
 * </ul>
 *
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class CustomNamingStrategy extends ImprovedNamingStrategy {

    /**
     * Singleton object
     */
    private static CustomNamingStrategy strategyInstance;

    /**
     *
     * @return CustomNamingStrategy
     */
    public static CustomNamingStrategy getInstance() {
        if (CustomNamingStrategy.strategyInstance == null) {
            CustomNamingStrategy.strategyInstance = new CustomNamingStrategy();
        }
        return CustomNamingStrategy.strategyInstance;
    }

    private CustomNamingStrategy() {
    }

    /**
     * Maps collection to association table name
     *
     * @param ownerEntity
     * @param ownerEntityTable
     * @param associatedEntity
     * @param propertyName
     * @param associatedEntityTable
     * @return String
     */
    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity,
            String associatedEntityTable, String propertyName) {
        String name = super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity, associatedEntityTable,
                propertyName);
        name = "rel_" + name;
        return name;
    }

    /**
     * Maps object association to foreign key column name
     *
     * @param propertyName
     * @param propertyTableName
     * @param propertyEntityName
     * @param referencedColumnName
     * @return
     */
    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName,
            String referencedColumnName) {
        String tableName = super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName);
        String suffix = "_" + referencedColumnName;

        tableName += suffix;
        return tableName;
    }
}
