/**
 * GetDataTransferDescriptorResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.cagrid.transfer.context.stubs;

public class GetDataTransferDescriptorResponse  implements java.io.Serializable {
    private org.cagrid.transfer.descriptor.DataTransferDescriptor dataTransferDescriptor;

    public GetDataTransferDescriptorResponse() {
    }

    public GetDataTransferDescriptorResponse(
           org.cagrid.transfer.descriptor.DataTransferDescriptor dataTransferDescriptor) {
           this.dataTransferDescriptor = dataTransferDescriptor;
    }


    /**
     * Gets the dataTransferDescriptor value for this GetDataTransferDescriptorResponse.
     * 
     * @return dataTransferDescriptor
     */
    public org.cagrid.transfer.descriptor.DataTransferDescriptor getDataTransferDescriptor() {
        return dataTransferDescriptor;
    }


    /**
     * Sets the dataTransferDescriptor value for this GetDataTransferDescriptorResponse.
     * 
     * @param dataTransferDescriptor
     */
    public void setDataTransferDescriptor(org.cagrid.transfer.descriptor.DataTransferDescriptor dataTransferDescriptor) {
        this.dataTransferDescriptor = dataTransferDescriptor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetDataTransferDescriptorResponse)) return false;
        GetDataTransferDescriptorResponse other = (GetDataTransferDescriptorResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dataTransferDescriptor==null && other.getDataTransferDescriptor()==null) || 
             (this.dataTransferDescriptor!=null &&
              this.dataTransferDescriptor.equals(other.getDataTransferDescriptor())));
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
        if (getDataTransferDescriptor() != null) {
            _hashCode += getDataTransferDescriptor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetDataTransferDescriptorResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://transfer.cagrid.org/TransferService/Context", ">GetDataTransferDescriptorResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataTransferDescriptor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://transfer.cagrid.org/Transfer", "DataTransferDescriptor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://transfer.cagrid.org/Transfer", "DataTransferDescriptor"));
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
