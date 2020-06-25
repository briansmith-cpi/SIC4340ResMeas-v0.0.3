package com.sic.resmeasure.daos;

/**
 * @author ztrix
 * @version 1.0.0
 * @since 21-Feb-16
 */
public class RegisterItemDao {

    private byte idrvRange1;
    private byte idrvValue1;
    private byte idrvRange2;
    private byte idrvValue2;
    private byte dac1Value;
    private float limitVolt12;
    private float regressionParamA1;
    private float regressionParamA2;
    private float regressionParamA3;
    private float regressionParamB1;
    private float regressionParamB2;
    private float regressionParamB3;
    private boolean biasSwitch;
    private byte channel;

    public byte getIdrvRange1() {
        return idrvRange1;
    }

    public void setIdrvRange1(byte idrvRange1) {
        this.idrvRange1 = idrvRange1;
    }

    public byte getIdrvValue1() {
        return idrvValue1;
    }

    public void setIdrvValue1(byte idrvValue1) {
        this.idrvValue1 = idrvValue1;
    }

    public void setDac1Value(byte dac1Value) {
        this.dac1Value = dac1Value;
    }

    public float getLimitVolt12() {
        return limitVolt12;
    }

    public boolean isBiasSwitch() {
        return biasSwitch;
    }

    public float getRegressionParamA1() {
        return regressionParamA1;
    }

    public float getRegressionParamA2() {
        return regressionParamA2;
    }

    public float getRegressionParamA3() {
        return regressionParamA3;
    }

    public float getRegressionParamB1() {
        return regressionParamB1;
    }

    public float getRegressionParamB2() {
        return regressionParamB2;
    }

    public float getRegressionParamB3() {
        return regressionParamB3;
    }

    public byte getChannel() {
        return channel;
    }

    public void setChannel(byte channel) {
        this.channel = channel;
    }
}
