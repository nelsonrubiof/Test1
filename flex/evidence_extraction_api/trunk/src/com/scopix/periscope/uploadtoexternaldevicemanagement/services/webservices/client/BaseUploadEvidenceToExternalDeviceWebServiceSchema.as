package com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client
{
	 import mx.rpc.xml.Schema
	 public class BaseUploadEvidenceToExternalDeviceWebServiceSchema
	{
		 public var schemas:Array = new Array();
		 public var targetNamespaces:Array = new Array();
		 public function BaseUploadEvidenceToExternalDeviceWebServiceSchema():void
		{
			 var xsdXML0:XML = <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:ns1="http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com" xmlns:ns2="Periscope" xmlns:soap11="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope" xmlns:soapenc11="http://schemas.xmlsoap.org/soap/encoding/" xmlns:soapenc12="http://www.w3.org/2003/05/soap-encoding" xmlns:tns="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:wsdlsoap="http://schemas.xmlsoap.org/wsdl/soap/" attributeFormDefault="qualified" elementFormDefault="qualified" targetNamespace="http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com">
    <xsd:complexType name="UploadEvidenceToExternalDeviceDTO">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="operacion" nillable="true" type="xsd:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:complexType name="UploadEvidenceToExternalDeviceResponseDTO">
        <xsd:sequence>
            <xsd:element minOccurs="0" name="archivosPorCopiar" nillable="true" type="xsd:int"/>
            <xsd:element minOccurs="0" name="cantidadArchivosCopiados" nillable="true" type="xsd:int"/>
            <xsd:element minOccurs="0" name="descripcion" nillable="true" type="xsd:string"/>
            <xsd:element minOccurs="0" name="respuesta" type="xsd:boolean"/>
        </xsd:sequence>
    </xsd:complexType>
</xsd:schema>
;
			 var xsdSchema0:Schema = new Schema(xsdXML0);
			schemas.push(xsdSchema0);
			targetNamespaces.push(new Namespace('','http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com'));
			 var xsdXML1:XML = <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:ns1="http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com" xmlns:ns2="Periscope" xmlns:soap11="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope" xmlns:soapenc11="http://schemas.xmlsoap.org/soap/encoding/" xmlns:soapenc12="http://www.w3.org/2003/05/soap-encoding" xmlns:tns="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:wsdlsoap="http://schemas.xmlsoap.org/wsdl/soap/" attributeFormDefault="qualified" elementFormDefault="qualified" targetNamespace="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com">
    <xsd:element name="unMountDevice">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="in0" nillable="true" type="ns1:UploadEvidenceToExternalDeviceDTO"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="unMountDeviceResponse">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="out" nillable="true" type="ns1:UploadEvidenceToExternalDeviceResponseDTO"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:complexType name="ArrayOfString">
        <xsd:sequence>
            <xsd:element maxOccurs="unbounded" minOccurs="0" name="string" nillable="true" type="xsd:string"/>
        </xsd:sequence>
    </xsd:complexType>
    <xsd:element name="formatDevice">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="in0" nillable="true" type="ns1:UploadEvidenceToExternalDeviceDTO"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="formatDeviceResponse">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="out" nillable="true" type="ns1:UploadEvidenceToExternalDeviceResponseDTO"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="getCopyInfo">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="in0" nillable="true" type="ns1:UploadEvidenceToExternalDeviceDTO"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="getCopyInfoResponse">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="out" nillable="true" type="ns1:UploadEvidenceToExternalDeviceResponseDTO"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="mountDevice">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="in0" nillable="true" type="ns1:UploadEvidenceToExternalDeviceDTO"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
    <xsd:element name="mountDeviceResponse">
        <xsd:complexType>
            <xsd:sequence>
                <xsd:element name="out" nillable="true" type="ns1:UploadEvidenceToExternalDeviceResponseDTO"/>
            </xsd:sequence>
        </xsd:complexType>
    </xsd:element>
</xsd:schema>
;
			 var xsdSchema1:Schema = new Schema(xsdXML1);
			schemas.push(xsdSchema1);
			targetNamespaces.push(new Namespace('','http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com'));
			 var xsdXML2:XML = <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:ns1="http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com" xmlns:ns2="Periscope" xmlns:soap11="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope" xmlns:soapenc11="http://schemas.xmlsoap.org/soap/encoding/" xmlns:soapenc12="http://www.w3.org/2003/05/soap-encoding" xmlns:tns="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:wsdlsoap="http://schemas.xmlsoap.org/wsdl/soap/" attributeFormDefault="qualified" elementFormDefault="qualified" targetNamespace="Periscope">
    <xsd:element name="Periscope_Exception" type="tns:ArrayOfString"/>
</xsd:schema>
;
			 var xsdSchema2:Schema = new Schema(xsdXML2);
			schemas.push(xsdSchema2);
			targetNamespaces.push(new Namespace('','Periscope'));
		}
	}
}