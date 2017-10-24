package com.android.csolarpanelapp.csolarpanelapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eduardo on 5/10/17.
 */

public class ModelData implements Parcelable {
    private Double Iscn=0.0;
    private Double Vocn=0.0;
    private Double Imp=0.0;
    private Double Vmp=0.0;
    private Double Pmax_e=0.0;
    private Double kv=0.0;
    private Double Kv=0.0;
    private Double ki=0.0;
    private Double Ki=0.0;
    private Double Ns=0.0;
    public void setIscn(Double value){
        this.Iscn = value;
    }
    public void setVocn(Double value){
        this.Vocn = value;
    }
    public void setImp(Double value){
        this.Imp = value;
    }
    public void setVmp(Double value){
        this.Vmp = value;
    }
    public void setPmax_e(Double value){
        this.Pmax_e = value;
    }
    public void setkv(Double value){
        this.kv = value;
    }
    public void setKv(Double value){
        this.Kv = value;
    }
    public void setki(Double value){
        this.ki = value;
    }
    public void setKi(Double value){
        this.Ki = value;
    }
    public void setNs(Double value){
        this.Ns = value;
    }
    //Metodos Get
    public Double getIscn(){
        return Iscn;
    }
    public Double getVocn(){
        return Vocn;
    }
    public Double getImp(){
        return Imp;
    }
    public Double getVmp(){
        return Vmp;
    }
    public Double getPmax_e(){
        return Pmax_e;
    }
    public Double getkv(){
        return kv;
    }
    public Double getKv(){
        return Kv;
    }
    public Double getki(){
        return ki;
    }
    public Double getKi(){
        return Ki;
    }
    public Double getNs(){
        return Ns;
    }
    public ModelData(){

    }
    public static final Parcelable.Creator<ModelData> CREATOR = new Parcelable.Creator<ModelData>() {

        @Override
        public ModelData createFromParcel(Parcel source) {
            return new ModelData(source);
        }

        @Override
        public ModelData[] newArray(int size) {
            return new ModelData[size];
        }

    };
    //Constructor privado
    private ModelData(Parcel in) {
        this.Iscn = in.readDouble();
        this.Vocn = in.readDouble();
        this.Imp = in.readDouble();
        this.Vmp = in.readDouble();
        this.Pmax_e = in.readDouble();
        this.kv = in.readDouble();
        this.Kv = in.readDouble();
        this.ki = in.readDouble();
        this.Ki = in.readDouble();
        this.Ns = in.readDouble();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeDouble(Iscn);
        dest.writeDouble(Vocn);
        dest.writeDouble(Imp);
        dest.writeDouble(Vmp);
        dest.writeDouble(Pmax_e);
        dest.writeDouble(kv);
        dest.writeDouble(Kv);
        dest.writeDouble(ki);
        dest.writeDouble(Ki);
        dest.writeDouble(Ns);
    }
    private void readFromParcel(Parcel in) {
        Iscn = in.readDouble();
        Vocn = in.readDouble();
        Imp = in.readDouble();
        Vmp = in.readDouble();
        Pmax_e = in.readDouble();
        kv = in.readDouble();
        Kv = in.readDouble();
        ki = in.readDouble();
        Ki = in.readDouble();
        Ns = in.readDouble();
    }
}
