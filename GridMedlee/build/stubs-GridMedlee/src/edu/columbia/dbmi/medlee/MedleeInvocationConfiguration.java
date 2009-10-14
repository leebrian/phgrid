/**
 * MedleeInvocationConfiguration.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package edu.columbia.dbmi.medlee;

public class MedleeInvocationConfiguration  implements java.io.Serializable {
    private edu.columbia.dbmi.medlee.ParseModeOptions parseMode;
    private edu.columbia.dbmi.medlee.OutputFormatOptions outputFormat;
    private edu.columbia.dbmi.medlee.CodeOptions code;

    public MedleeInvocationConfiguration() {
    }

    public MedleeInvocationConfiguration(
           edu.columbia.dbmi.medlee.CodeOptions code,
           edu.columbia.dbmi.medlee.OutputFormatOptions outputFormat,
           edu.columbia.dbmi.medlee.ParseModeOptions parseMode) {
           this.parseMode = parseMode;
           this.outputFormat = outputFormat;
           this.code = code;
    }


    /**
     * Gets the parseMode value for this MedleeInvocationConfiguration.
     * 
     * @return parseMode
     */
    public edu.columbia.dbmi.medlee.ParseModeOptions getParseMode() {
        return parseMode;
    }


    /**
     * Sets the parseMode value for this MedleeInvocationConfiguration.
     * 
     * @param parseMode
     */
    public void setParseMode(edu.columbia.dbmi.medlee.ParseModeOptions parseMode) {
        this.parseMode = parseMode;
    }


    /**
     * Gets the outputFormat value for this MedleeInvocationConfiguration.
     * 
     * @return outputFormat
     */
    public edu.columbia.dbmi.medlee.OutputFormatOptions getOutputFormat() {
        return outputFormat;
    }


    /**
     * Sets the outputFormat value for this MedleeInvocationConfiguration.
     * 
     * @param outputFormat
     */
    public void setOutputFormat(edu.columbia.dbmi.medlee.OutputFormatOptions outputFormat) {
        this.outputFormat = outputFormat;
    }


    /**
     * Gets the code value for this MedleeInvocationConfiguration.
     * 
     * @return code
     */
    public edu.columbia.dbmi.medlee.CodeOptions getCode() {
        return code;
    }


    /**
     * Sets the code value for this MedleeInvocationConfiguration.
     * 
     * @param code
     */
    public void setCode(edu.columbia.dbmi.medlee.CodeOptions code) {
        this.code = code;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MedleeInvocationConfiguration)) return false;
        MedleeInvocationConfiguration other = (MedleeInvocationConfiguration) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.parseMode==null && other.getParseMode()==null) || 
             (this.parseMode!=null &&
              this.parseMode.equals(other.getParseMode()))) &&
            ((this.outputFormat==null && other.getOutputFormat()==null) || 
             (this.outputFormat!=null &&
              this.outputFormat.equals(other.getOutputFormat()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode())));
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
        if (getParseMode() != null) {
            _hashCode += getParseMode().hashCode();
        }
        if (getOutputFormat() != null) {
            _hashCode += getOutputFormat().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MedleeInvocationConfiguration.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "MedleeInvocationConfiguration"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parseMode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "ParseMode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "ParseModeOptions"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputFormat");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "OutputFormat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "OutputFormatOptions"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "CodeOptions"));
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
