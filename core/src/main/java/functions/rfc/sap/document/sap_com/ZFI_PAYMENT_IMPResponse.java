/**
 * ZFI_PAYMENT_IMPResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package functions.rfc.sap.document.sap_com;

public class ZFI_PAYMENT_IMPResponse  implements java.io.Serializable {
    private functions.rfc.sap.document.sap_com.BAPIRET2 RETURN;

    private functions.rfc.sap.document.sap_com.ZSFI_PAYMENT[] t_DATA;

    public ZFI_PAYMENT_IMPResponse() {
    }

    public ZFI_PAYMENT_IMPResponse(
           functions.rfc.sap.document.sap_com.BAPIRET2 RETURN,
           functions.rfc.sap.document.sap_com.ZSFI_PAYMENT[] t_DATA) {
           this.RETURN = RETURN;
           this.t_DATA = t_DATA;
    }


    /**
     * Gets the RETURN value for this ZFI_PAYMENT_IMPResponse.
     * 
     * @return RETURN
     */
    public functions.rfc.sap.document.sap_com.BAPIRET2 getRETURN() {
        return RETURN;
    }


    /**
     * Sets the RETURN value for this ZFI_PAYMENT_IMPResponse.
     * 
     * @param RETURN
     */
    public void setRETURN(functions.rfc.sap.document.sap_com.BAPIRET2 RETURN) {
        this.RETURN = RETURN;
    }


    /**
     * Gets the t_DATA value for this ZFI_PAYMENT_IMPResponse.
     * 
     * @return t_DATA
     */
    public functions.rfc.sap.document.sap_com.ZSFI_PAYMENT[] getT_DATA() {
        return t_DATA;
    }


    /**
     * Sets the t_DATA value for this ZFI_PAYMENT_IMPResponse.
     * 
     * @param t_DATA
     */
    public void setT_DATA(functions.rfc.sap.document.sap_com.ZSFI_PAYMENT[] t_DATA) {
        this.t_DATA = t_DATA;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZFI_PAYMENT_IMPResponse)) return false;
        ZFI_PAYMENT_IMPResponse other = (ZFI_PAYMENT_IMPResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.RETURN==null && other.getRETURN()==null) || 
             (this.RETURN!=null &&
              this.RETURN.equals(other.getRETURN()))) &&
            ((this.t_DATA==null && other.getT_DATA()==null) || 
             (this.t_DATA!=null &&
              java.util.Arrays.equals(this.t_DATA, other.getT_DATA())));
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
        if (getRETURN() != null) {
            _hashCode += getRETURN().hashCode();
        }
        if (getT_DATA() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getT_DATA());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getT_DATA(), i);
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
        new org.apache.axis.description.TypeDesc(ZFI_PAYMENT_IMPResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", ">ZFI_PAYMENT_IMP.Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RETURN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RETURN"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "BAPIRET2"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("t_DATA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "T_DATA"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSFI_PAYMENT"));
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
