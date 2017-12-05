/**
 * ZSFI_CUSTOMER_IMP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package functions.rfc.sap.document.sap_com;


/**
 * customer data (importing)
 */
public class ZSFI_CUSTOMER_IMP  implements java.io.Serializable {
    /* BSS客户编号 */
    private java.lang.String KUNNR;

    /* Name */
    private java.lang.String NAME1;

    /* Sort field */
    private java.lang.String SORTL;

    /* House number and street */
    private java.lang.String STRAS;

    /* First telephone number */
    private java.lang.String TELF1;

    /* VAT Registration Number */
    private java.lang.String STCEG;

    /* Account Holder Name */
    private java.lang.String KOINH;

    /* Bank account number */
    private java.lang.String BANKN;

    /* status of customer */
    private java.lang.String STATU;

    /* if related */
    private java.lang.String RELAT;

    /* invoicement type */
    private java.lang.String ITYPE;

    public ZSFI_CUSTOMER_IMP() {
    }

    public ZSFI_CUSTOMER_IMP(
           java.lang.String KUNNR,
           java.lang.String NAME1,
           java.lang.String SORTL,
           java.lang.String STRAS,
           java.lang.String TELF1,
           java.lang.String STCEG,
           java.lang.String KOINH,
           java.lang.String BANKN,
           java.lang.String STATU,
           java.lang.String RELAT,
           java.lang.String ITYPE) {
           this.KUNNR = KUNNR;
           this.NAME1 = NAME1;
           this.SORTL = SORTL;
           this.STRAS = STRAS;
           this.TELF1 = TELF1;
           this.STCEG = STCEG;
           this.KOINH = KOINH;
           this.BANKN = BANKN;
           this.STATU = STATU;
           this.RELAT = RELAT;
           this.ITYPE = ITYPE;
    }


    /**
     * Gets the KUNNR value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return KUNNR   * BSS客户编号
     */
    public java.lang.String getKUNNR() {
        return KUNNR;
    }


    /**
     * Sets the KUNNR value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param KUNNR   * BSS客户编号
     */
    public void setKUNNR(java.lang.String KUNNR) {
        this.KUNNR = KUNNR;
    }


    /**
     * Gets the NAME1 value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return NAME1   * Name
     */
    public java.lang.String getNAME1() {
        return NAME1;
    }


    /**
     * Sets the NAME1 value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param NAME1   * Name
     */
    public void setNAME1(java.lang.String NAME1) {
        this.NAME1 = NAME1;
    }


    /**
     * Gets the SORTL value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return SORTL   * Sort field
     */
    public java.lang.String getSORTL() {
        return SORTL;
    }


    /**
     * Sets the SORTL value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param SORTL   * Sort field
     */
    public void setSORTL(java.lang.String SORTL) {
        this.SORTL = SORTL;
    }


    /**
     * Gets the STRAS value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return STRAS   * House number and street
     */
    public java.lang.String getSTRAS() {
        return STRAS;
    }


    /**
     * Sets the STRAS value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param STRAS   * House number and street
     */
    public void setSTRAS(java.lang.String STRAS) {
        this.STRAS = STRAS;
    }


    /**
     * Gets the TELF1 value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return TELF1   * First telephone number
     */
    public java.lang.String getTELF1() {
        return TELF1;
    }


    /**
     * Sets the TELF1 value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param TELF1   * First telephone number
     */
    public void setTELF1(java.lang.String TELF1) {
        this.TELF1 = TELF1;
    }


    /**
     * Gets the STCEG value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return STCEG   * VAT Registration Number
     */
    public java.lang.String getSTCEG() {
        return STCEG;
    }


    /**
     * Sets the STCEG value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param STCEG   * VAT Registration Number
     */
    public void setSTCEG(java.lang.String STCEG) {
        this.STCEG = STCEG;
    }


    /**
     * Gets the KOINH value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return KOINH   * Account Holder Name
     */
    public java.lang.String getKOINH() {
        return KOINH;
    }


    /**
     * Sets the KOINH value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param KOINH   * Account Holder Name
     */
    public void setKOINH(java.lang.String KOINH) {
        this.KOINH = KOINH;
    }


    /**
     * Gets the BANKN value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return BANKN   * Bank account number
     */
    public java.lang.String getBANKN() {
        return BANKN;
    }


    /**
     * Sets the BANKN value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param BANKN   * Bank account number
     */
    public void setBANKN(java.lang.String BANKN) {
        this.BANKN = BANKN;
    }


    /**
     * Gets the STATU value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return STATU   * status of customer
     */
    public java.lang.String getSTATU() {
        return STATU;
    }


    /**
     * Sets the STATU value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param STATU   * status of customer
     */
    public void setSTATU(java.lang.String STATU) {
        this.STATU = STATU;
    }


    /**
     * Gets the RELAT value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return RELAT   * if related
     */
    public java.lang.String getRELAT() {
        return RELAT;
    }


    /**
     * Sets the RELAT value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param RELAT   * if related
     */
    public void setRELAT(java.lang.String RELAT) {
        this.RELAT = RELAT;
    }


    /**
     * Gets the ITYPE value for this ZSFI_CUSTOMER_IMP.
     * 
     * @return ITYPE   * invoicement type
     */
    public java.lang.String getITYPE() {
        return ITYPE;
    }


    /**
     * Sets the ITYPE value for this ZSFI_CUSTOMER_IMP.
     * 
     * @param ITYPE   * invoicement type
     */
    public void setITYPE(java.lang.String ITYPE) {
        this.ITYPE = ITYPE;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZSFI_CUSTOMER_IMP)) return false;
        ZSFI_CUSTOMER_IMP other = (ZSFI_CUSTOMER_IMP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.KUNNR==null && other.getKUNNR()==null) || 
             (this.KUNNR!=null &&
              this.KUNNR.equals(other.getKUNNR()))) &&
            ((this.NAME1==null && other.getNAME1()==null) || 
             (this.NAME1!=null &&
              this.NAME1.equals(other.getNAME1()))) &&
            ((this.SORTL==null && other.getSORTL()==null) || 
             (this.SORTL!=null &&
              this.SORTL.equals(other.getSORTL()))) &&
            ((this.STRAS==null && other.getSTRAS()==null) || 
             (this.STRAS!=null &&
              this.STRAS.equals(other.getSTRAS()))) &&
            ((this.TELF1==null && other.getTELF1()==null) || 
             (this.TELF1!=null &&
              this.TELF1.equals(other.getTELF1()))) &&
            ((this.STCEG==null && other.getSTCEG()==null) || 
             (this.STCEG!=null &&
              this.STCEG.equals(other.getSTCEG()))) &&
            ((this.KOINH==null && other.getKOINH()==null) || 
             (this.KOINH!=null &&
              this.KOINH.equals(other.getKOINH()))) &&
            ((this.BANKN==null && other.getBANKN()==null) || 
             (this.BANKN!=null &&
              this.BANKN.equals(other.getBANKN()))) &&
            ((this.STATU==null && other.getSTATU()==null) || 
             (this.STATU!=null &&
              this.STATU.equals(other.getSTATU()))) &&
            ((this.RELAT==null && other.getRELAT()==null) || 
             (this.RELAT!=null &&
              this.RELAT.equals(other.getRELAT()))) &&
            ((this.ITYPE==null && other.getITYPE()==null) || 
             (this.ITYPE!=null &&
              this.ITYPE.equals(other.getITYPE())));
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
        if (getKUNNR() != null) {
            _hashCode += getKUNNR().hashCode();
        }
        if (getNAME1() != null) {
            _hashCode += getNAME1().hashCode();
        }
        if (getSORTL() != null) {
            _hashCode += getSORTL().hashCode();
        }
        if (getSTRAS() != null) {
            _hashCode += getSTRAS().hashCode();
        }
        if (getTELF1() != null) {
            _hashCode += getTELF1().hashCode();
        }
        if (getSTCEG() != null) {
            _hashCode += getSTCEG().hashCode();
        }
        if (getKOINH() != null) {
            _hashCode += getKOINH().hashCode();
        }
        if (getBANKN() != null) {
            _hashCode += getBANKN().hashCode();
        }
        if (getSTATU() != null) {
            _hashCode += getSTATU().hashCode();
        }
        if (getRELAT() != null) {
            _hashCode += getRELAT().hashCode();
        }
        if (getITYPE() != null) {
            _hashCode += getITYPE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZSFI_CUSTOMER_IMP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "ZSFI_CUSTOMER_IMP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KUNNR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "KUNNR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NAME1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NAME1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SORTL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SORTL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STRAS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STRAS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TELF1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TELF1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STCEG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STCEG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KOINH");
        elemField.setXmlName(new javax.xml.namespace.QName("", "KOINH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BANKN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BANKN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATU");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STATU"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RELAT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RELAT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ITYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ITYPE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
