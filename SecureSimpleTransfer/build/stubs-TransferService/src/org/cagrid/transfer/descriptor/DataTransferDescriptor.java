/**
 * DataTransferDescriptor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.cagrid.transfer.descriptor;

public class DataTransferDescriptor  implements java.io.Serializable {
    private java.lang.String url;
    private org.cagrid.transfer.descriptor.DataDescriptor dataDescriptor;

    public DataTransferDescriptor() {
    }

    public DataTransferDescriptor(
           org.cagrid.transfer.descriptor.DataDescriptor dataDescriptor,
           java.lang.String url) {
           this.url = url;
           this.dataDescriptor = dataDescriptor;
    }


    /**
     * Gets the url value for this DataTransferDescriptor.
     * 
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }


    /**
     * Sets the url value for this DataTransferDescriptor.
     * 
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }


    /**
     * Gets the dataDescriptor value for this DataTransferDescriptor.
     * 
     * @return dataDescriptor
     */
    public org.cagrid.transfer.descriptor.DataDescriptor getDataDescriptor() {
        return dataDescriptor;
    }


    /**
     * Sets the dataDescriptor value for this DataTransferDescriptor.
     * 
     * @param dataDescriptor
     */
    public void setDataDescriptor(org.cagrid.transfer.descriptor.DataDescriptor dataDescriptor) {
        this.dataDescriptor = dataDescriptor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataTransferDescriptor)) return false;
        DataTransferDescriptor other = (DataTransferDescriptor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.url==null && other.getUrl()==null) || 
             (this.url!=null &&
              this.url.equals(other.getUrl()))) &&
            ((this.dataDescriptor==null && other.getDataDescriptor()==null) || 
             (this.dataDescriptor!=null &&
              this.dataDescriptor.equals(other.getDataDescriptor())));
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
        if (getUrl() != null) {
            _hashCode += getUrl().hashCode();
        }
        if (getDataDescriptor() != null) {
            _hashCode += getDataDescriptor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DataTransferDescriptor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://transfer.cagrid.org/Transfer", "DataTransferDescriptor"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataDescriptor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://transfer.cagrid.org/Transfer", "DataDescriptor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://transfer.cagrid.org/Transfer", "DataDescriptor"));
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
