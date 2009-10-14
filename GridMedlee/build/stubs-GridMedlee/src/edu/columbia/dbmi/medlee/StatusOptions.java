/**
 * StatusOptions.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package edu.columbia.dbmi.medlee;

public class StatusOptions implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected StatusOptions(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _READY = "READY";
    public static final java.lang.String _RUNNING = "RUNNING";
    public static final java.lang.String _COMPLETE = "COMPLETE";
    public static final java.lang.String _ERROR = "ERROR";
    public static final StatusOptions READY = new StatusOptions(_READY);
    public static final StatusOptions RUNNING = new StatusOptions(_RUNNING);
    public static final StatusOptions COMPLETE = new StatusOptions(_COMPLETE);
    public static final StatusOptions ERROR = new StatusOptions(_ERROR);
    public java.lang.String getValue() { return _value_;}
    public static StatusOptions fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        StatusOptions enumeration = (StatusOptions)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static StatusOptions fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatusOptions.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/medlee", "StatusOptions"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
