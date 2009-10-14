/**
 * DataStorageDescriptor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.cagrid.transfer.descriptor;

public class DataStorageDescriptor  implements java.io.Serializable {
    private java.lang.String location;
    private java.lang.String userDN;
    private boolean deleteOnDestroy;
    private org.cagrid.transfer.descriptor.Status status;
    private org.cagrid.transfer.descriptor.DataDescriptor dataDescriptor;

    public DataStorageDescriptor() {
    }

    public DataStorageDescriptor(
           org.cagrid.transfer.descriptor.DataDescriptor dataDescriptor,
           boolean deleteOnDestroy,
           java.lang.String location,
           org.cagrid.transfer.descriptor.Status status,
           java.lang.String userDN) {
           this.location = location;
           this.userDN = userDN;
           this.deleteOnDestroy = deleteOnDestroy;
           this.status = status;
           this.dataDescriptor = dataDescriptor;
    }


    /**
     * Gets the location value for this DataStorageDescriptor.
     * 
     * @return location
     */
    public java.lang.String getLocation() {
        return location;
    }


    /**
     * Sets the location value for this DataStorageDescriptor.
     * 
     * @param location
     */
    public void setLocation(java.lang.String location) {
        this.location = location;
    }


    /**
     * Gets the userDN value for this DataStorageDescriptor.
     * 
     * @return userDN
     */
    public java.lang.String getUserDN() {
        return userDN;
    }


    /**
     * Sets the userDN value for this DataStorageDescriptor.
     * 
     * @param userDN
     */
    public void setUserDN(java.lang.String userDN) {
        this.userDN = userDN;
    }


    /**
     * Gets the deleteOnDestroy value for this DataStorageDescriptor.
     * 
     * @return deleteOnDestroy
     */
    public boolean isDeleteOnDestroy() {
        return deleteOnDestroy;
    }


    /**
     * Sets the deleteOnDestroy value for this DataStorageDescriptor.
     * 
     * @param deleteOnDestroy
     */
    public void setDeleteOnDestroy(boolean deleteOnDestroy) {
        this.deleteOnDestroy = deleteOnDestroy;
    }


    /**
     * Gets the status value for this DataStorageDescriptor.
     * 
     * @return status
     */
    public org.cagrid.transfer.descriptor.Status getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DataStorageDescriptor.
     * 
     * @param status
     */
    public void setStatus(org.cagrid.transfer.descriptor.Status status) {
        this.status = status;
    }


    /**
     * Gets the dataDescriptor value for this DataStorageDescriptor.
     * 
     * @return dataDescriptor
     */
    public org.cagrid.transfer.descriptor.DataDescriptor getDataDescriptor() {
        return dataDescriptor;
    }


    /**
     * Sets the dataDescriptor value for this DataStorageDescriptor.
     * 
     * @param dataDescriptor
     */
    public void setDataDescriptor(org.cagrid.transfer.descriptor.DataDescriptor dataDescriptor) {
        this.dataDescriptor = dataDescriptor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataStorageDescriptor)) return false;
        DataStorageDescriptor other = (DataStorageDescriptor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.location==null && other.getLocation()==null) || 
             (this.location!=null &&
              this.location.equals(other.getLocation()))) &&
            ((this.userDN==null && other.getUserDN()==null) || 
             (this.userDN!=null &&
              this.userDN.equals(other.getUserDN()))) &&
            this.deleteOnDestroy == other.isDeleteOnDestroy() &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
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
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        if (getUserDN() != null) {
            _hashCode += getUserDN().hashCode();
        }
        _hashCode += (isDeleteOnDestroy() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getDataDescriptor() != null) {
            _hashCode += getDataDescriptor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DataStorageDescriptor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://transfer.cagrid.org/Transfer", "DataStorageDescriptor"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("location");
        elemField.setXmlName(new javax.xml.namespace.QName("", "location"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userDN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userDN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deleteOnDestroy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "deleteOnDestroy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://transfer.cagrid.org/Transfer", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://transfer.cagrid.org/Transfer", "Status"));
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
