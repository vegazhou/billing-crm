/**
 * ZFI_CUSTOMER_IMP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package functions.rfc.sap.document.sap_com;

public class ZFI_CUSTOMER_IMP  implements java.io.Serializable {
    private functions.rfc.sap.document.sap_com.ZSFI_CUSTOMER_IMP[] IT_DATA;

    public ZFI_CUSTOMER_IMP() {
    }

    public ZFI_CUSTOMER_IMP(
           functions.rfc.sap.document.sap_com.ZSFI_CUSTOMER_IMP[] IT_DATA) {
           this.IT_DATA = IT_DATA;
    }


    /**
     * Gets the IT_DATA value for this ZFI_CUSTOMER_IMP.
     * 
     * @return IT_DATA
     */
    public functions.rfc.sap.document.sap_com.ZSFI_CUSTOMER_IMP[] getIT_DATA() {
        return IT_DATA;
    }


    /**
     * Sets the IT_DATA value for this ZFI_CUSTOMER_IMP.
     * 
     * @param IT_DATA
     */
    public void setIT_DATA(functions.rfc.sap.document.sap_com.ZSFI_CUSTOMER_IMP[] IT_DATA) {
        this.IT_DATA = IT_DATA;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZFI_CUSTOMER_IMP)) return false;
        ZFI_CUSTOMER_IMP other = (ZFI_CUSTOMER_IMP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.IT_DATA==null && other.getIT_DATA()==null) || 
             (this.IT_DATA!=null &&
              java.util.Arrays.equals(this.IT_DATA, other.getIT_DATA())));
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
        if (getIT_DATA() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIT_DATA());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIT_DATA(), i);
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
        new org.apache.axis.description.TypeDesc(ZFI_CUSTOMER_IMP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", ">ZFI_CUSTOMER_IMP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IT_DATA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IT_DATA"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSFI_CUSTOMER_IMP"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "item"));
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
