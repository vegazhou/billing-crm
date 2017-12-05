/**
 * BAPIRET2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package functions.rfc.sap.document.sap_com;


/**
 * Return Parameter
 */
public class BAPIRET2  implements java.io.Serializable {
    /* Message type: S Success, E Error, W Warning, I Info, A Abort */
    private java.lang.String TYPE;

    /* Message Class */
    private java.lang.String ID;

    /* Message Number */
    private java.lang.String NUMBER;

    /* Message Text */
    private java.lang.String MESSAGE;

    /* Application log: log number */
    private java.lang.String LOG_NO;

    /* Application log: Internal message serial number */
    private java.lang.String LOG_MSG_NO;

    /* Message Variable */
    private java.lang.String MESSAGE_V1;

    /* Message Variable */
    private java.lang.String MESSAGE_V2;

    /* Message Variable */
    private java.lang.String MESSAGE_V3;

    /* Message Variable */
    private java.lang.String MESSAGE_V4;

    /* Parameter Name */
    private java.lang.String PARAMETER;

    /* Lines in parameter */
    private java.math.BigInteger ROW;

    /* Field in parameter */
    private java.lang.String FIELD;

    /* Logical system from which message originates */
    private java.lang.String SYSTEM;

    public BAPIRET2() {
    }

    public BAPIRET2(
           java.lang.String TYPE,
           java.lang.String ID,
           java.lang.String NUMBER,
           java.lang.String MESSAGE,
           java.lang.String LOG_NO,
           java.lang.String LOG_MSG_NO,
           java.lang.String MESSAGE_V1,
           java.lang.String MESSAGE_V2,
           java.lang.String MESSAGE_V3,
           java.lang.String MESSAGE_V4,
           java.lang.String PARAMETER,
           java.math.BigInteger ROW,
           java.lang.String FIELD,
           java.lang.String SYSTEM) {
           this.TYPE = TYPE;
           this.ID = ID;
           this.NUMBER = NUMBER;
           this.MESSAGE = MESSAGE;
           this.LOG_NO = LOG_NO;
           this.LOG_MSG_NO = LOG_MSG_NO;
           this.MESSAGE_V1 = MESSAGE_V1;
           this.MESSAGE_V2 = MESSAGE_V2;
           this.MESSAGE_V3 = MESSAGE_V3;
           this.MESSAGE_V4 = MESSAGE_V4;
           this.PARAMETER = PARAMETER;
           this.ROW = ROW;
           this.FIELD = FIELD;
           this.SYSTEM = SYSTEM;
    }


    /**
     * Gets the TYPE value for this BAPIRET2.
     * 
     * @return TYPE   * Message type: S Success, E Error, W Warning, I Info, A Abort
     */
    public java.lang.String getTYPE() {
        return TYPE;
    }


    /**
     * Sets the TYPE value for this BAPIRET2.
     * 
     * @param TYPE   * Message type: S Success, E Error, W Warning, I Info, A Abort
     */
    public void setTYPE(java.lang.String TYPE) {
        this.TYPE = TYPE;
    }


    /**
     * Gets the ID value for this BAPIRET2.
     * 
     * @return ID   * Message Class
     */
    public java.lang.String getID() {
        return ID;
    }


    /**
     * Sets the ID value for this BAPIRET2.
     * 
     * @param ID   * Message Class
     */
    public void setID(java.lang.String ID) {
        this.ID = ID;
    }


    /**
     * Gets the NUMBER value for this BAPIRET2.
     * 
     * @return NUMBER   * Message Number
     */
    public java.lang.String getNUMBER() {
        return NUMBER;
    }


    /**
     * Sets the NUMBER value for this BAPIRET2.
     * 
     * @param NUMBER   * Message Number
     */
    public void setNUMBER(java.lang.String NUMBER) {
        this.NUMBER = NUMBER;
    }


    /**
     * Gets the MESSAGE value for this BAPIRET2.
     * 
     * @return MESSAGE   * Message Text
     */
    public java.lang.String getMESSAGE() {
        return MESSAGE;
    }


    /**
     * Sets the MESSAGE value for this BAPIRET2.
     * 
     * @param MESSAGE   * Message Text
     */
    public void setMESSAGE(java.lang.String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }


    /**
     * Gets the LOG_NO value for this BAPIRET2.
     * 
     * @return LOG_NO   * Application log: log number
     */
    public java.lang.String getLOG_NO() {
        return LOG_NO;
    }


    /**
     * Sets the LOG_NO value for this BAPIRET2.
     * 
     * @param LOG_NO   * Application log: log number
     */
    public void setLOG_NO(java.lang.String LOG_NO) {
        this.LOG_NO = LOG_NO;
    }


    /**
     * Gets the LOG_MSG_NO value for this BAPIRET2.
     * 
     * @return LOG_MSG_NO   * Application log: Internal message serial number
     */
    public java.lang.String getLOG_MSG_NO() {
        return LOG_MSG_NO;
    }


    /**
     * Sets the LOG_MSG_NO value for this BAPIRET2.
     * 
     * @param LOG_MSG_NO   * Application log: Internal message serial number
     */
    public void setLOG_MSG_NO(java.lang.String LOG_MSG_NO) {
        this.LOG_MSG_NO = LOG_MSG_NO;
    }


    /**
     * Gets the MESSAGE_V1 value for this BAPIRET2.
     * 
     * @return MESSAGE_V1   * Message Variable
     */
    public java.lang.String getMESSAGE_V1() {
        return MESSAGE_V1;
    }


    /**
     * Sets the MESSAGE_V1 value for this BAPIRET2.
     * 
     * @param MESSAGE_V1   * Message Variable
     */
    public void setMESSAGE_V1(java.lang.String MESSAGE_V1) {
        this.MESSAGE_V1 = MESSAGE_V1;
    }


    /**
     * Gets the MESSAGE_V2 value for this BAPIRET2.
     * 
     * @return MESSAGE_V2   * Message Variable
     */
    public java.lang.String getMESSAGE_V2() {
        return MESSAGE_V2;
    }


    /**
     * Sets the MESSAGE_V2 value for this BAPIRET2.
     * 
     * @param MESSAGE_V2   * Message Variable
     */
    public void setMESSAGE_V2(java.lang.String MESSAGE_V2) {
        this.MESSAGE_V2 = MESSAGE_V2;
    }


    /**
     * Gets the MESSAGE_V3 value for this BAPIRET2.
     * 
     * @return MESSAGE_V3   * Message Variable
     */
    public java.lang.String getMESSAGE_V3() {
        return MESSAGE_V3;
    }


    /**
     * Sets the MESSAGE_V3 value for this BAPIRET2.
     * 
     * @param MESSAGE_V3   * Message Variable
     */
    public void setMESSAGE_V3(java.lang.String MESSAGE_V3) {
        this.MESSAGE_V3 = MESSAGE_V3;
    }


    /**
     * Gets the MESSAGE_V4 value for this BAPIRET2.
     * 
     * @return MESSAGE_V4   * Message Variable
     */
    public java.lang.String getMESSAGE_V4() {
        return MESSAGE_V4;
    }


    /**
     * Sets the MESSAGE_V4 value for this BAPIRET2.
     * 
     * @param MESSAGE_V4   * Message Variable
     */
    public void setMESSAGE_V4(java.lang.String MESSAGE_V4) {
        this.MESSAGE_V4 = MESSAGE_V4;
    }


    /**
     * Gets the PARAMETER value for this BAPIRET2.
     * 
     * @return PARAMETER   * Parameter Name
     */
    public java.lang.String getPARAMETER() {
        return PARAMETER;
    }


    /**
     * Sets the PARAMETER value for this BAPIRET2.
     * 
     * @param PARAMETER   * Parameter Name
     */
    public void setPARAMETER(java.lang.String PARAMETER) {
        this.PARAMETER = PARAMETER;
    }


    /**
     * Gets the ROW value for this BAPIRET2.
     * 
     * @return ROW   * Lines in parameter
     */
    public java.math.BigInteger getROW() {
        return ROW;
    }


    /**
     * Sets the ROW value for this BAPIRET2.
     * 
     * @param ROW   * Lines in parameter
     */
    public void setROW(java.math.BigInteger ROW) {
        this.ROW = ROW;
    }


    /**
     * Gets the FIELD value for this BAPIRET2.
     * 
     * @return FIELD   * Field in parameter
     */
    public java.lang.String getFIELD() {
        return FIELD;
    }


    /**
     * Sets the FIELD value for this BAPIRET2.
     * 
     * @param FIELD   * Field in parameter
     */
    public void setFIELD(java.lang.String FIELD) {
        this.FIELD = FIELD;
    }


    /**
     * Gets the SYSTEM value for this BAPIRET2.
     * 
     * @return SYSTEM   * Logical system from which message originates
     */
    public java.lang.String getSYSTEM() {
        return SYSTEM;
    }


    /**
     * Sets the SYSTEM value for this BAPIRET2.
     * 
     * @param SYSTEM   * Logical system from which message originates
     */
    public void setSYSTEM(java.lang.String SYSTEM) {
        this.SYSTEM = SYSTEM;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BAPIRET2)) return false;
        BAPIRET2 other = (BAPIRET2) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.TYPE==null && other.getTYPE()==null) || 
             (this.TYPE!=null &&
              this.TYPE.equals(other.getTYPE()))) &&
            ((this.ID==null && other.getID()==null) || 
             (this.ID!=null &&
              this.ID.equals(other.getID()))) &&
            ((this.NUMBER==null && other.getNUMBER()==null) || 
             (this.NUMBER!=null &&
              this.NUMBER.equals(other.getNUMBER()))) &&
            ((this.MESSAGE==null && other.getMESSAGE()==null) || 
             (this.MESSAGE!=null &&
              this.MESSAGE.equals(other.getMESSAGE()))) &&
            ((this.LOG_NO==null && other.getLOG_NO()==null) || 
             (this.LOG_NO!=null &&
              this.LOG_NO.equals(other.getLOG_NO()))) &&
            ((this.LOG_MSG_NO==null && other.getLOG_MSG_NO()==null) || 
             (this.LOG_MSG_NO!=null &&
              this.LOG_MSG_NO.equals(other.getLOG_MSG_NO()))) &&
            ((this.MESSAGE_V1==null && other.getMESSAGE_V1()==null) || 
             (this.MESSAGE_V1!=null &&
              this.MESSAGE_V1.equals(other.getMESSAGE_V1()))) &&
            ((this.MESSAGE_V2==null && other.getMESSAGE_V2()==null) || 
             (this.MESSAGE_V2!=null &&
              this.MESSAGE_V2.equals(other.getMESSAGE_V2()))) &&
            ((this.MESSAGE_V3==null && other.getMESSAGE_V3()==null) || 
             (this.MESSAGE_V3!=null &&
              this.MESSAGE_V3.equals(other.getMESSAGE_V3()))) &&
            ((this.MESSAGE_V4==null && other.getMESSAGE_V4()==null) || 
             (this.MESSAGE_V4!=null &&
              this.MESSAGE_V4.equals(other.getMESSAGE_V4()))) &&
            ((this.PARAMETER==null && other.getPARAMETER()==null) || 
             (this.PARAMETER!=null &&
              this.PARAMETER.equals(other.getPARAMETER()))) &&
            ((this.ROW==null && other.getROW()==null) || 
             (this.ROW!=null &&
              this.ROW.equals(other.getROW()))) &&
            ((this.FIELD==null && other.getFIELD()==null) || 
             (this.FIELD!=null &&
              this.FIELD.equals(other.getFIELD()))) &&
            ((this.SYSTEM==null && other.getSYSTEM()==null) || 
             (this.SYSTEM!=null &&
              this.SYSTEM.equals(other.getSYSTEM())));
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
        if (getTYPE() != null) {
            _hashCode += getTYPE().hashCode();
        }
        if (getID() != null) {
            _hashCode += getID().hashCode();
        }
        if (getNUMBER() != null) {
            _hashCode += getNUMBER().hashCode();
        }
        if (getMESSAGE() != null) {
            _hashCode += getMESSAGE().hashCode();
        }
        if (getLOG_NO() != null) {
            _hashCode += getLOG_NO().hashCode();
        }
        if (getLOG_MSG_NO() != null) {
            _hashCode += getLOG_MSG_NO().hashCode();
        }
        if (getMESSAGE_V1() != null) {
            _hashCode += getMESSAGE_V1().hashCode();
        }
        if (getMESSAGE_V2() != null) {
            _hashCode += getMESSAGE_V2().hashCode();
        }
        if (getMESSAGE_V3() != null) {
            _hashCode += getMESSAGE_V3().hashCode();
        }
        if (getMESSAGE_V4() != null) {
            _hashCode += getMESSAGE_V4().hashCode();
        }
        if (getPARAMETER() != null) {
            _hashCode += getPARAMETER().hashCode();
        }
        if (getROW() != null) {
            _hashCode += getROW().hashCode();
        }
        if (getFIELD() != null) {
            _hashCode += getFIELD().hashCode();
        }
        if (getSYSTEM() != null) {
            _hashCode += getSYSTEM().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BAPIRET2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", "BAPIRET2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TYPE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NUMBER");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NUMBER"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MESSAGE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MESSAGE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LOG_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LOG_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LOG_MSG_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LOG_MSG_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MESSAGE_V1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MESSAGE_V1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MESSAGE_V2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MESSAGE_V2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MESSAGE_V3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MESSAGE_V3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MESSAGE_V4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MESSAGE_V4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PARAMETER");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PARAMETER"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ROW");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ROW"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FIELD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FIELD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SYSTEM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SYSTEM"));
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
