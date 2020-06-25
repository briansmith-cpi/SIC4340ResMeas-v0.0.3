package com.sic.resmeasure.daos;

/**
 * @author ztrix
 * @version 1.0.0
 * @since 21-Feb-16
 */
public class LogItemDao {
    private String vout;
    private String adcResult;
    private String iBias;
    private String ohm;
    private String buffer;
    private int range;
    private String iBias1;

    public String getVout() {
        return vout;
    }

    public void setVout(String vout) {
        this.vout = vout;
    }

    public String getAdcResult() {
        return adcResult;
    }

    public void setAdcResult(String adcResult) {
        this.adcResult = adcResult;
    }

    public String getiBias() {
        return iBias;
    }

    public void setiBias(String iBias) {
        this.iBias = iBias;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setiBias1(String iBias1) {
        this.iBias1 = iBias1;
    }

    public String getiBias1() {
        return iBias1;
    }

    public String getOhm() {
        return ohm;
    }

    public void setOhm(String ohm) {
        this.ohm = ohm;
    }
}
