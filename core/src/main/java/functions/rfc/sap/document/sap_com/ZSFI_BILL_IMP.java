/**
 * ZSFI_BILL_IMP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package functions.rfc.sap.document.sap_com;


/**
 * Bills to import
 */
public class ZSFI_BILL_IMP  implements java.io.Serializable {
    /* Fiscal year */
    private java.lang.String BYEAR;

    /* Number of months */
    private java.lang.String BMONT;

    /* BSS账单编号 */
    private java.lang.String BILNO;

    /* BSS客户编号 */
    private java.lang.String CUSNO;

    /* FI 费用项目 */
    private java.lang.String ITMTY;

    /* Amount in Local Currency */
    private java.math.BigDecimal AMTTT;

    /* Amount in Local Currency */
    private java.math.BigDecimal AMTPR;

    /* Amount in Local Currency */
    private java.math.BigDecimal AMTAC;

    public ZSFI_BILL_IMP() {
    }

    public ZSFI_BILL_IMP(
           java.lang.String BYEAR,
           java.lang.String BMONT,
           java.lang.String BILNO,
           java.lang.String CUSNO,
           java.lang.String ITMTY,
           java.math.BigDecimal AMTTT,
           java.math.BigDecimal AMTPR,
           java.math.BigDecimal AMTAC) {
           this.BYEAR = BYEAR;
           this.BMONT = BMONT;
           this.BILNO = BILNO;
           this.CUSNO = CUSNO;
           this.ITMTY = ITMTY;
           this.AMTTT = AMTTT;
           this.AMTPR = AMTPR;
           this.AMTAC = AMTAC;
    }


    /**
     * Gets the BYEAR value for this ZSFI_BILL_IMP.
     * 
     * @return BYEAR   * Fiscal year
     */
    public java.lang.String getBYEAR() {
        return BYEAR;
    }


    /**
     * Sets the BYEAR value for this ZSFI_BILL_IMP.
     * 
     * @param BYEAR   * Fiscal year
     */
    public void setBYEAR(java.lang.String BYEAR) {
        this.BYEAR = BYEAR;
    }


    /**
     * Gets the BMONT value for this ZSFI_BILL_IMP.
     * 
     * @return BMONT   * Number of months
     */
    public java.lang.String getBMONT() {
        return BMONT;
    }


    /**
     * Sets the BMONT value for this ZSFI_BILL_IMP.
     * 
     * @param BMONT   * Number of months
     */
    public void setBMONT(java.lang.String BMONT) {
        this.BMONT = BMONT;
    }


    /**
     * Gets the BILNO value for this ZSFI_BILL_IMP.
     * 
     * @return BILNO   * BSS账单编号
     */
    public java.lang.String getBILNO() {
        return BILNO;
    }


    /**
     * Sets the BILNO value for this ZSFI_BILL_IMP.
     * 
     * @param BILNO   * BSS账单编号
     */
    public void setBILNO(java.lang.String BILNO) {
        this.BILNO = BILNO;
    }


    /**
     * Gets the CUSNO value for this ZSFI_BILL_IMP.
     * 
     * @return CUSNO   * BSS客户编号
     */
    public java.lang.String getCUSNO() {
        return CUSNO;
    }


    /**
     * Sets the CUSNO value for this ZSFI_BILL_IMP.
     * 
     * @param CUSNO   * BSS客户编号
     */
    public void setCUSNO(java.lang.String CUSNO) {
        this.CUSNO = CUSNO;
    }


    /**
     * Gets the ITMTY value for this ZSFI_BILL_IMP.
     * 
     * @return ITMTY   * FI 费用项目
     */
    public java.lang.String getITMTY() {
        return ITMTY;
    }


    /**
     * Sets the ITMTY value for this ZSFI_BILL_IMP.
     * 
     * @param ITMTY   * FI 费用项目
     */
    public void setITMTY(java.lang.String ITMTY) {
        this.ITMTY = ITMTY;
    }


    /**
     * Gets the AMTTT value for this ZSFI_BILL_IMP.
     * 
     * @return AMTTT   * Amount in Local Currency
     */
    public java.math.BigDecimal getAMTTT() {
        return AMTTT;
    }


    /**
     * Sets the AMTTT value for this ZSFI_BILL_IMP.
     * 
     * @param AMTTT   * Amount in Local Currency
     */
    public void setAMTTT(java.math.BigDecimal AMTTT) {
        this.AMTTT = AMTTT;
    }


    /**
     * Gets the AMTPR value for this ZSFI_BILL_IMP.
     * 
     * @return AMTPR   * Amount in Local Currency
     */
    public java.math.BigDecimal getAMTPR() {
        return AMTPR;
    }


    /**
     * Sets the AMTPR value for this ZSFI_BILL_IMP.
     * 
     * @param AMTPR   * Amount in Local Currency
     */
    public void setAMTPR(java.math.BigDecimal AMTPR) {
        this.AMTPR = AMTPR;
    }


    /**
     * Gets the AMTAC value for this ZSFI_BILL_IMP.
     * 
     * @return AMTAC   * Amount in Local Currency
     */
    public java.math.BigDecimal getAMTAC() {
        return AMTAC;
    }


    /**
     * Sets the AMTAC value for this ZSFI_BILL_IMP.
     * 
     * @param AMTAC   * Amount in Local Currency
     */
    public void setAMTAC(java.math.BigDecimal AMTAC) {
        this.AMTAC = AMTAC;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZSFI_BILL_IMP)) return false;
        ZSFI_BILL_IMP other = (ZSFI_BILL_IMP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.BYEAR==null && other.getBYEAR()==null) || 
             (this.BYEAR!=null &&
              this.BYEAR.equals(other.getBYEAR()))) &&
            ((this.BMONT==null && other.getBMONT()==null) || 
             (this.BMONT!=null &&
              this.BMONT.equals(other.getBMONT()))) &&
            ((this.BILNO==null && other.getBILNO()==null) || 
             (this.BILNO!=null &&
              this.BILNO.equals(other.getBILNO()))) &&
            ((this.CUSNO==null && other.getCUSNO()==null) || 
             (this.CUSNO!=null &&
              this.CUSNO.equals(other.getCUSNO()))) &&
            ((this.ITMTY==null && other.getITMTY()==null) || 
             (this.ITMTY!=null &&
              this.ITMTY.equals(other.getITMTY()))) &&
            ((this.AMTTT==null && other.getAMTTT()==null) || 
             (this.AMTTT!=null &&
              this.AMTTT.equals(other.getAMTTT()))) &&
            ((this.AMTPR==null && other.getAMTPR()==null) || 
             (this.AMTPR!=null &&
              this.AMTPR.equals(other.getAMTPR()))) &&
            ((this.AMTAC==null && other.getAMTAC()==null) || 
             (this.AMTAC!=null &&
              this.AMTAC.equals(other.getAMTAC())));
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
        if (getBYEAR() != null) {
            _hashCode += getBYEAR().hashCode();
        }
        if (getBMONT() != null) {
            _hashCode += getBMONT().hashCode();
        }
        if (getBILNO() != null) {
            _hashCode += getBILNO().hashCode();
        }
        if (getCUSNO() != null) {
            _hashCode += getCUSNO().hashCode();
        }
        if (getITMTY() != null) {
            _hashCode += getITMTY().hashCode();
        }
        if (getAMTTT() != null) {
            _hashCode += getAMTTT().hashCode();
        }
        if (getAMTPR() != null) {
            _hashCode += getAMTPR().hashCode();
        }
        if (getAMTAC() != null) {
            _hashCode += getAMTAC().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZSFI_BILL_IMP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSFI_BILL_IMP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BYEAR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BYEAR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BMONT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BMONT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILNO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILNO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("ITMTY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ITMTY"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AMTPR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AMTPR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AMTAC");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AMTAC"));
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
