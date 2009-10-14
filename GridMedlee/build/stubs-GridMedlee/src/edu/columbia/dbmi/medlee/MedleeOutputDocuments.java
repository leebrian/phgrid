/**
 * MedleeOutputDocuments.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package edu.columbia.dbmi.medlee;

public class MedleeOutputDocuments  implements java.io.Serializable {
    private edu.columbia.dbmi.medlee.MedleeOutputDocument[] medleeOutputDocument;

    public MedleeOutputDocuments() {
    }

    public MedleeOutputDocuments(
           edu.columbia.dbmi.medlee.MedleeOutputDocument[] medleeOutputDocument) {
           this.medleeOutputDocument = medleeOutputDocument;
    }


    /**
     * Gets the medleeOutputDocument value for this MedleeOutputDocuments.
     * 
     * @return medleeOutputDocument
     */
    public edu.columbia.dbmi.medlee.MedleeOutputDocument[] getMedleeOutputDocument() {
        return medleeOutputDocument;
    }


    /**
     * Sets the medleeOutputDocument value for this MedleeOutputDocuments.
     * 
     * @param medleeOutputDocument
     */
    public void setMedleeOutputDocument(edu.columbia.dbmi.medlee.MedleeOutputDocument[] medleeOutputDocument) {
        this.medleeOutputDocument = medleeOutputDocument;
    }

    public edu.columbia.dbmi.medlee.MedleeOutputDocument getMedleeOutputDocument(int i) {
        return this.medleeOutputDocument[i];
    }

    public void setMedleeOutputDocument(int i, edu.columbia.dbmi.medlee.MedleeOutputDocument _value) {
        this.medleeOutputDocument[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MedleeOutputDocuments)) return false;
        MedleeOutputDocuments other = (MedleeOutputDocuments) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.medleeOutputDocument==null && other.getMedleeOutputDocument()==null) || 
             (this.medleeOutputDocument!=null &&
              java.util.Arrays.equals(this.medleeOutputDocument, other.getMedleeOutputDocument())));
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
        if (getMedleeOutputDocument() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMedleeOutputDocument());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMedleeOutputDocument(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MedleeOutputDocuments.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "MedleeOutputDocuments"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("medleeOutputDocument");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "MedleeOutputDocument"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "MedleeOutputDocument"));
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
