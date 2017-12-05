package wang.huaichao.data.entity;

/**
 * Created by Administrator on 8/17/2016.
 */
public class PriceRate {
    private String code;
    private float rate;
    private float serviceRate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(float serviceRate) {
        this.serviceRate = serviceRate;
    }
}
