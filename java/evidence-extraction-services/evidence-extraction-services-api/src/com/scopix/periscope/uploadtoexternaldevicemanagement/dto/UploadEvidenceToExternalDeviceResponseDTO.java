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
 * UploadEvidenceToExternalDeviceResponseDTO.java
 *
 * Created on 11-01-2010, 02:01:22 PM
 *
 */
package com.scopix.periscope.uploadtoexternaldevicemanagement.dto;

/**
 * 
 * @author Gustavo Alvarez
 */
public class UploadEvidenceToExternalDeviceResponseDTO {

	private boolean respuesta;
	private String descripcion;
	private Integer archivosPorCopiar;
	private Integer cantidadArchivosCopiados;

	public boolean isRespuesta() {
		return respuesta;
	}

	public void setRespuesta(boolean respuesta) {
		this.respuesta = respuesta;
	}

	public Integer getCantidadArchivosCopiados() {
		return cantidadArchivosCopiados;
	}

	public void setCantidadArchivosCopiados(Integer cantidadArchivosCopiados) {
		this.cantidadArchivosCopiados = cantidadArchivosCopiados;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getArchivosPorCopiar() {
		return archivosPorCopiar;
	}

	public void setArchivosPorCopiar(Integer archivosPorCopiar) {
		this.archivosPorCopiar = archivosPorCopiar;
	}
}