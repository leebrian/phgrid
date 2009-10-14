/**
 * MedleeOutputDocument.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package edu.columbia.dbmi.medlee;

public class MedleeOutputDocument  implements java.io.Serializable {
    private java.lang.String XMLOutput;
    private java.lang.String identifier;  // attribute

    public MedleeOutputDocument() {
    }

    public MedleeOutputDocument(
           java.lang.String XMLOutput,
           java.lang.String identifier) {
           this.XMLOutput = XMLOutput;
           this.identifier = identifier;
    }


    /**
     * Gets the XMLOutput value for this MedleeOutputDocument.
     * 
     * @return XMLOutput
     */
    public java.lang.String getXMLOutput() {
        return XMLOutput;
    }


    /**
     * Sets the XMLOutput value for this MedleeOutputDocument.
     * 
     * @param XMLOutput
     */
    public void setXMLOutput(java.lang.String XMLOutput) {
        this.XMLOutput = XMLOutput;
    }


    /**
     * Gets the identifier value for this MedleeOutputDocument.
     * 
     * @return identifier
     */
    public java.lang.String getIdentifier() {
        return identifier;
    }


    /**
     * Sets the identifier value for this MedleeOutputDocument.
     * 
     * @param identifier
     */
    public void setIdentifier(java.lang.String identifier) {
        this.identifier = identifier;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MedleeOutputDocument)) return false;
        MedleeOutputDocument other = (MedleeOutputDocument) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.XMLOutput==null && other.getXMLOutput()==null) || 
             (this.XMLOutput!=null &&
              this.XMLOutput.equals(other.getXMLOutput()))) &&
            ((this.identifier==null && other.getIdentifier()==null) || 
             (this.identifier!=null &&
              this.identifier.equals(other.getIdentifier())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getXMLOutput() != null) {
            _hashCode += getXMLOutput().hashCode();
        }
        if (getIdentifier() != null) {
            _hashCode += getIdentifier().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MedleeOutputDocument.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "MedleeOutputDocument"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("identifier");
        attrField.setXmlName(new javax.xml.namespace.QName("", "identifier"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XMLOutput");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "XMLOutput"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
