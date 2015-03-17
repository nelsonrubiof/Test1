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
/**
 * Oct 2, 2006 - 1:45:55 PM
 */
package com.scopix.periscope.periscopefoundation.states.interceptor;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;

/**
 * This class represent an enum matrix which contains states transitions. <br>
 * Rows are initial states, columns are operations and cells are final states.
 * If there are no states, it means that transition could not be made.
 * 
 * @author mvazquez
 */
public class EnumMatrix<R extends Enum, C extends Enum, E> {

    /**
     * Creates new {@link EnumMatrix}
     */
    public static <R extends Enum, C extends Enum, E> EnumMatrix<R, C, E> create(Class<? extends R> rowEnumClass,
            Class<? extends C> columnEnumClass) {
        int rowSize = EnumMatrix.getEnumQuantity(rowEnumClass);
        int columnSize = EnumMatrix.getEnumQuantity(columnEnumClass);

        EnumMatrix<R, C, E> matrix = new EnumMatrix<R, C, E>();
        matrix.setDimensions(rowSize, columnSize);

        return matrix; 
    }

    /**
     * This method is used to determine matrix size, based on how many enums are
     * there on specified class.
     */
    private static int getEnumQuantity(Class<? extends Enum> clase) {
        Enum[] enumConstants = clase.getEnumConstants();
        if (enumConstants == null) {
            throw new UnexpectedRuntimeException("La clase debe corresponder a un enum");
        }
        return enumConstants.length;
    }

    /**
     * Get element stored on specified row and column
     */
    public E get(R rowEnum, C columnEnum) {
        int rowIndex = rowEnum.ordinal();
        int columnIndex = columnEnum.ordinal();
        return this.matrix[rowIndex][columnIndex];
    }

    /**
     * Put element on specified row and column
     */
    public void put(R rowEnum, C columnEnum, E element) {
        int rowIndex = rowEnum.ordinal();
        int columnIndex = columnEnum.ordinal();
        this.matrix[rowIndex][columnIndex] = element;
    }

    private void setDimensions(int rowSize, int columnSize) {
        this.matrix = (E[][]) new Object[rowSize][columnSize];
    }
    private E[][] matrix;
}
