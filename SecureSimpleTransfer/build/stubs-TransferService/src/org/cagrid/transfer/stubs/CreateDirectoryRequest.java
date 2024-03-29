/**
 * CreateDirectoryRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.cagrid.transfer.stubs;

public class CreateDirectoryRequest  implements java.io.Serializable {
    private java.lang.String directory;

    public CreateDirectoryRequest() {
    }

    public CreateDirectoryRequest(
           java.lang.String directory) {
           this.directory = directory;
    }


    /**
     * Gets the directory value for this CreateDirectoryRequest.
     * 
     * @return directory
     */
    public java.lang.String getDirectory() {
        return directory;
    }


    /**
     * Sets the directory value for this CreateDirectoryRequest.
     * 
     * @param directory
     */
    public void setDirectory(java.lang.String directory) {
        this.directory = directory;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateDirectoryRequest)) return false;
        CreateDirectoryRequest other = (CreateDirectoryRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.directory==null && other.getDirectory()==null) || 
             (this.directory!=null &&
              this.directory.equals(other.getDirectory())));
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
        if (getDirectory() != null) {
            _hashCode += getDirectory().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateDirectoryRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://transfer.cagrid.org/TransferService", ">CreateDirectoryRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("directory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://transfer.cagrid.org/TransferService", "directory"));
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
