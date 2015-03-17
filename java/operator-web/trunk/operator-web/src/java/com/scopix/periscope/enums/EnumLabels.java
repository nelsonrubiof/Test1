package com.scopix.periscope.enums;

import java.io.Serializable;

import org.zkoss.util.resource.Labels;

/**
 * Enumeración para manejo de textos i18n
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public enum EnumLabels implements Serializable {

    // Pantalla login
    COMBO_DEFAULT(Labels.getLabel("default.opcionPredeterminada")), // Por favor seleccione...
    CLIENTE(Labels.getLabel("login.cliente")), // Cliente
    CLIENTE_NO_COLAS(Labels.getLabel("login.clienteNoColas")), // No existen colas para el cliente seleccionado
    CMBCLIENTES_TOOLTIP(Labels.getLabel("login.cmbClientesTooltip")), // Por favor seleccione un cliente
    COLA(Labels.getLabel("login.cola")), // Cola
    CMBCOLAS_TOOLTIP(Labels.getLabel("login.cmbColasTooltip")), // Por favor seleccione una cola
    INGRESO(Labels.getLabel("login.ingreso")), // Ingreso
    LOGIN_ERROR(Labels.getLabel("login.errorAutenticacion")), // Usuario o clave incorrectos
    OFICINA(Labels.getLabel("login.oficina")), // Oficina
    CMBOFICINA_TOOLTIP(Labels.getLabel("login.cmboficina.tooltip")), // Por favor seleccione la oficina en donde se encuentre
                                                                     // ubicado
    // Pantalla evaluación
    USUARIO(Labels.getLabel("login.usuario")); // Usuario

    private final String name;

    private EnumLabels(String s) {
        name = s;
    }

    // public boolean equalsName(String otherName){
    // return (otherName == null)? false:name.equalsIgnoreCase(otherName);
    // }

    @Override
    public String toString() {
        return name;
    }
}