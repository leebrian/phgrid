/**
 * InvokeGridMedleeRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package edu.columbia.dbmi.stubs;

public class InvokeGridMedleeRequest  implements java.io.Serializable {
    private edu.columbia.dbmi.stubs.InvokeGridMedleeRequestMedleeInvocationConfiguration medleeInvocationConfiguration;
    private edu.columbia.dbmi.stubs.InvokeGridMedleeRequestMedleeInputDocuments medleeInputDocuments;

    public InvokeGridMedleeRequest() {
    }

    public InvokeGridMedleeRequest(
           edu.columbia.dbmi.stubs.InvokeGridMedleeRequestMedleeInputDocuments medleeInputDocuments,
           edu.columbia.dbmi.stubs.InvokeGridMedleeRequestMedleeInvocationConfiguration medleeInvocationConfiguration) {
           this.medleeInvocationConfiguration = medleeInvocationConfiguration;
           this.medleeInputDocuments = medleeInputDocuments;
    }


    /**
     * Gets the medleeInvocationConfiguration value for this InvokeGridMedleeRequest.
     * 
     * @return medleeInvocationConfiguration
     */
    public edu.columbia.dbmi.stubs.InvokeGridMedleeRequestMedleeInvocationConfiguration getMedleeInvocationConfiguration() {
        return medleeInvocationConfiguration;
    }


    /**
     * Sets the medleeInvocationConfiguration value for this InvokeGridMedleeRequest.
     * 
     * @param medleeInvocationConfiguration
     */
    public void setMedleeInvocationConfiguration(edu.columbia.dbmi.stubs.InvokeGridMedleeRequestMedleeInvocationConfiguration medleeInvocationConfiguration) {
        this.medleeInvocationConfiguration = medleeInvocationConfiguration;
    }


    /**
     * Gets the medleeInputDocuments value for this InvokeGridMedleeRequest.
     * 
     * @return medleeInputDocuments
     */
    public edu.columbia.dbmi.stubs.InvokeGridMedleeRequestMedleeInputDocuments getMedleeInputDocuments() {
        return medleeInputDocuments;
    }


    /**
     * Sets the medleeInputDocuments value for this InvokeGridMedleeRequest.
     * 
     * @param medleeInputDocuments
     */
    public void setMedleeInputDocuments(edu.columbia.dbmi.stubs.InvokeGridMedleeRequestMedleeInputDocuments medleeInputDocuments) {
        this.medleeInputDocuments = medleeInputDocuments;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InvokeGridMedleeRequest)) return false;
        InvokeGridMedleeRequest other = (InvokeGridMedleeRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.medleeInvocationConfiguration==null && other.getMedleeInvocationConfiguration()==null) || 
             (this.medleeInvocationConfiguration!=null &&
              this.medleeInvocationConfiguration.equals(other.getMedleeInvocationConfiguration()))) &&
            ((this.medleeInputDocuments==null && other.getMedleeInputDocuments()==null) || 
             (this.medleeInputDocuments!=null &&
              this.medleeInputDocuments.equals(other.getMedleeInputDocuments())));
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
        if (getMedleeInvocationConfiguration() != null) {
            _hashCode += getMedleeInvocationConfiguration().hashCode();
        }
        if (getMedleeInputDocuments() != null) {
            _hashCode += getMedleeInputDocuments().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InvokeGridMedleeRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee", ">InvokeGridMedleeRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("medleeInvocationConfiguration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee", "medleeInvocationConfiguration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee", ">>InvokeGridMedleeRequest>medleeInvocationConfiguration"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("medleeInputDocuments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee", "medleeInputDocuments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee", ">>InvokeGridMedleeRequest>medleeInputDocuments"));
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
