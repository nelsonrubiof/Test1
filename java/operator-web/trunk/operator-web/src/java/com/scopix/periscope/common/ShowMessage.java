package com.scopix.periscope.common;

import java.io.Serializable;

import org.zkoss.zul.Messagebox;

/**
 * Clase para mostrar mensajes en pantalla
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class ShowMessage implements Serializable {

    private static ShowMessage instance;
    private static final long serialVersionUID = -4558288342917727143L;

    /**
     * Obtiene instancia de la clase
     * 
     * @author carlos polo
     * @version 1.0.0
     * @return ShowMessage instancia de la clase
     * @since 6.0
     * @date 19/02/2013
     */
    public static ShowMessage getInstance() {
        if (instance == null) {
            instance = new ShowMessage();
        }
        return instance;
    }

    /**
     * Muestra mensaje en pantalla
     * 
     * @author carlos polo
     * @version 1.0
     * @param titulo título de la ventana del mensaje
     * @param mensaje mensaje
     * @param tipo tipo de mensaje (warning, exclamación...)
     * @since 6.0
     * @date 08/02/2013
     */
    public void mostrarMensaje(String titulo, String mensaje, String tipo) {
        Messagebox.show(mensaje, titulo, Messagebox.OK, tipo);
    }

    public void setInstance(ShowMessage showMessage) {
        instance = showMessage;
    }
}