/**
 * MedleeInputDocuments.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package edu.columbia.dbmi.medlee;

public class MedleeInputDocuments  implements java.io.Serializable {
    private edu.columbia.dbmi.medlee.MedleeInputDocument[] medleeInputDocument;

    public MedleeInputDocuments() {
    }

    public MedleeInputDocuments(
           edu.columbia.dbmi.medlee.MedleeInputDocument[] medleeInputDocument) {
           this.medleeInputDocument = medleeInputDocument;
    }


    /**
     * Gets the medleeInputDocument value for this MedleeInputDocuments.
     * 
     * @return medleeInputDocument
     */
    public edu.columbia.dbmi.medlee.MedleeInputDocument[] getMedleeInputDocument() {
        return medleeInputDocument;
    }


    /**
     * Sets the medleeInputDocument value for this MedleeInputDocuments.
     * 
     * @param medleeInputDocument
     */
    public void setMedleeInputDocument(edu.columbia.dbmi.medlee.MedleeInputDocument[] medleeInputDocument) {
        this.medleeInputDocument = medleeInputDocument;
    }

    public edu.columbia.dbmi.medlee.MedleeInputDocument getMedleeInputDocument(int i) {
        return this.medleeInputDocument[i];
    }

    public void setMedleeInputDocument(int i, edu.columbia.dbmi.medlee.MedleeInputDocument _value) {
        this.medleeInputDocument[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MedleeInputDocuments)) return false;
        MedleeInputDocuments other = (MedleeInputDocuments) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.medleeInputDocument==null && other.getMedleeInputDocument()==null) || 
             (this.medleeInputDocument!=null &&
              java.util.Arrays.equals(this.medleeInputDocument, other.getMedleeInputDocument())));
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
        if (getMedleeInputDocument() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMedleeInputDocument());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMedleeInputDocument(), i);
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
        new org.apache.axis.description.TypeDesc(MedleeInputDocuments.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "MedleeInputDocuments"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("medleeInputDocument");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "MedleeInputDocument"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "MedleeInputDocument"));
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
