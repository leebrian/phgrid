/**
 * InvokeGridMedleeRequestMedleeInvocationConfiguration.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package edu.columbia.dbmi.stubs;

public class InvokeGridMedleeRequestMedleeInvocationConfiguration  implements java.io.Serializable {
    private edu.columbia.dbmi.medlee.MedleeInvocationConfiguration medleeInvocationConfiguration;

    public InvokeGridMedleeRequestMedleeInvocationConfiguration() {
    }

    public InvokeGridMedleeRequestMedleeInvocationConfiguration(
           edu.columbia.dbmi.medlee.MedleeInvocationConfiguration medleeInvocationConfiguration) {
           this.medleeInvocationConfiguration = medleeInvocationConfiguration;
    }


    /**
     * Gets the medleeInvocationConfiguration value for this InvokeGridMedleeRequestMedleeInvocationConfiguration.
     * 
     * @return medleeInvocationConfiguration
     */
    public edu.columbia.dbmi.medlee.MedleeInvocationConfiguration getMedleeInvocationConfiguration() {
        return medleeInvocationConfiguration;
    }


    /**
     * Sets the medleeInvocationConfiguration value for this InvokeGridMedleeRequestMedleeInvocationConfiguration.
     * 
     * @param medleeInvocationConfiguration
     */
    public void setMedleeInvocationConfiguration(edu.columbia.dbmi.medlee.MedleeInvocationConfiguration medleeInvocationConfiguration) {
        this.medleeInvocationConfiguration = medleeInvocationConfiguration;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InvokeGridMedleeRequestMedleeInvocationConfiguration)) return false;
        InvokeGridMedleeRequestMedleeInvocationConfiguration other = (InvokeGridMedleeRequestMedleeInvocationConfiguration) obj;
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
              this.medleeInvocationConfiguration.equals(other.getMedleeInvocationConfiguration())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InvokeGridMedleeRequestMedleeInvocationConfiguration.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee", ">>InvokeGridMedleeRequest>medleeInvocationConfiguration"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("medleeInvocationConfiguration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "MedleeInvocationConfiguration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "MedleeInvocationConfiguration"));
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
