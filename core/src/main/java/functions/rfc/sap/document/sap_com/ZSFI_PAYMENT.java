/**
 * ZSFI_PAYMENT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package functions.rfc.sap.document.sap_com;


/**
 * payment
 */
public class ZSFI_PAYMENT  implements java.io.Serializable {
    /* BSS账单编号 */
    private java.lang.String BELNR;

    /* Posting Date in the Document */
    private functions.rfc.sap.document.sap_com.Date BUDAT;

    /* BSS客户编号 */
    private java.lang.String CUSNO;

    /* 款项性质 */
    private java.lang.String KXXZ;

    /* Amount in Local Currency */
    private java.math.BigDecimal AMTTT;

    public ZSFI_PAYMENT() {
    }

    public ZSFI_PAYMENT(
           java.lang.String BELNR,
           functions.rfc.sap.document.sap_com.Date BUDAT,
           java.lang.String CUSNO,
           java.lang.String KXXZ,
           java.math.BigDecimal AMTTT) {
           this.BELNR = BELNR;
           this.BUDAT = BUDAT;
           this.CUSNO = CUSNO;
           this.KXXZ = KXXZ;
           this.AMTTT = AMTTT;
    }


    /**
     * Gets the BELNR value for this ZSFI_PAYMENT.
     * 
     * @return BELNR   * BSS账单编号
     */
    public java.lang.String getBELNR() {
        return BELNR;
    }


    /**
     * Sets the BELNR value for this ZSFI_PAYMENT.
     * 
     * @param BELNR   * BSS账单编号
     */
    public void setBELNR(java.lang.String BELNR) {
        this.BELNR = BELNR;
    }


    /**
     * Gets the BUDAT value for this ZSFI_PAYMENT.
     * 
     * @return BUDAT   * Posting Date in the Document
     */
    public functions.rfc.sap.document.sap_com.Date getBUDAT() {
        return BUDAT;
    }


    /**
     * Sets the BUDAT value for this ZSFI_PAYMENT.
     * 
     * @param BUDAT   * Posting Date in the Document
     */
    public void setBUDAT(functions.rfc.sap.document.sap_com.Date BUDAT) {
        this.BUDAT = BUDAT;
    }


    /**
     * Gets the CUSNO value for this ZSFI_PAYMENT.
     * 
     * @return CUSNO   * BSS客户编号
     */
    public java.lang.String getCUSNO() {
        return CUSNO;
    }


    /**
     * Sets the CUSNO value for this ZSFI_PAYMENT.
     * 
     * @param CUSNO   * BSS客户编号
     */
    public void setCUSNO(java.lang.String CUSNO) {
        this.CUSNO = CUSNO;
    }


    /**
     * Gets the KXXZ value for this ZSFI_PAYMENT.
     * 
     * @return KXXZ   * 款项性质
     */
    public java.lang.String getKXXZ() {
        return KXXZ;
    }


    /**
     * Sets the KXXZ value for this ZSFI_PAYMENT.
     * 
     * @param KXXZ   * 款项性质
     */
    public void setKXXZ(java.lang.String KXXZ) {
        this.KXXZ = KXXZ;
    }


    /**
     * Gets the AMTTT value for this ZSFI_PAYMENT.
     * 
     * @return AMTTT   * Amount in Local Currency
     */
    public java.math.BigDecimal getAMTTT() {
        return AMTTT;
    }


    /**
     * Sets the AMTTT value for this ZSFI_PAYMENT.
     * 
     * @param AMTTT   * Amount in Local Currency
     */
    public void setAMTTT(java.math.BigDecimal AMTTT) {
        this.AMTTT = AMTTT;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZSFI_PAYMENT)) return false;
        ZSFI_PAYMENT other = (ZSFI_PAYMENT) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.BELNR==null && other.getBELNR()==null) || 
             (this.BELNR!=null &&
              this.BELNR.equals(other.getBELNR()))) &&
            ((this.BUDAT==null && other.getBUDAT()==null) || 
             (this.BUDAT!=null &&
              this.BUDAT.equals(other.getBUDAT()))) &&
            ((this.CUSNO==null && other.getCUSNO()==null) || 
             (this.CUSNO!=null &&
              this.CUSNO.equals(other.getCUSNO()))) &&
            ((this.KXXZ==null && other.getKXXZ()==null) || 
             (this.KXXZ!=null &&
              this.KXXZ.equals(other.getKXXZ()))) &&
            ((this.AMTTT==null && other.getAMTTT()==null) || 
             (this.AMTTT!=null &&
              this.AMTTT.equals(other.getAMTTT())));
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
        if (getBELNR() != null) {
            _hashCode += getBELNR().hashCode();
        }
        if (getBUDAT() != null) {
            _hashCode += getBUDAT().hashCode();
        }
        if (getCUSNO() != null) {
            _hashCode += getCUSNO().hashCode();
        }
        if (getKXXZ() != null) {
            _hashCode += getKXXZ().hashCode();
        }
        if (getAMTTT() != null) {
            _hashCode += getAMTTT().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZSFI_PAYMENT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSFI_PAYMENT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BELNR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BELNR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BUDAT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BUDAT"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUSNO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUSNO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KXXZ");
        elemField.setXmlName(new javax.xml.namespace.QName("", "KXXZ"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AMTTT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AMTTT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
