package com.android.csolarpanelapp.csolarpanelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.TextView;
import android.widget.Toast;

public class Statistics extends AppCompatActivity {
    ModelData info = new ModelData();
    double [] pI;
    double [] pV;
    double [] pP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ModelData data = getIntent().getParcelableExtra("Resultado");
        if(data != null) {
            Modelc(data);
            updateTextViews(data);
        }else {
            Toast.makeText(this, "No product to show ", Toast.LENGTH_LONG).show();
        }
    }
    private void updateTextViews(ModelData data){
        TextView txtCambiado = (TextView)findViewById(R.id.Iscn);
        txtCambiado.setText(Double.toString(data.getIscn()));
        txtCambiado = (TextView)findViewById(R.id.Vocn);
        txtCambiado.setText(Double.toString(data.getVocn()));
        txtCambiado = (TextView)findViewById(R.id.Imp);
        txtCambiado.setText(Double.toString(data.getImp()));
        txtCambiado = (TextView)findViewById(R.id.Vmp);
        txtCambiado.setText(Double.toString(data.getVmp()));
        txtCambiado = (TextView)findViewById(R.id.Pmax_e);
        txtCambiado.setText(Double.toString(data.getPmax_e()));
        txtCambiado = (TextView)findViewById(R.id.kv);
        txtCambiado.setText(Double.toString(data.getkv()));
        txtCambiado = (TextView)findViewById(R.id.Kv);
        txtCambiado.setText(Double.toString(data.getKv()));
        txtCambiado = (TextView)findViewById(R.id.ki);
        txtCambiado.setText(Double.toString(data.getki()));
        txtCambiado = (TextView)findViewById(R.id.Ki);
        txtCambiado.setText(Double.toString(data.getKi()));
        txtCambiado = (TextView)findViewById(R.id.Ns);
        txtCambiado.setText(Double.toString(data.getNs()));

    }
    private void Modelc(ModelData data){
        Toast.makeText(this,"Wait",Toast.LENGTH_LONG).show();
        //Requerimientos de Energía.
        double Q = 1000; // en watts
        double H = 6; //Horas promedio anual de luz diurna
        double GAH[] = {6.09, 6.84, 7.36, 6.98, 6.37, 6.25, 5.94, 5.64, 5.55, 5.94, 6.38, 5.72};
        double Ep = 0.75; //Eficiencia del panel solar
        double CW = 4.46; //Costo de producción de 1 watt incluyendo materiales e instalación.
        /////////////////////////////////////////7
        // Datos del fabricante

        double Iscn = data.getIscn() ; //Corriente nominal de cortocircuito [A]
        double Vocn = data.getVocn(); //Tensión nominal de circuito abierto [V]
        double Imp = data.getImp(); //Corriente del punto de máxima potencia [A]
        double Vmp = data.getVmp(); //Tensión del punto de máxima potencia [V]
        double Pmax_e = Vmp * Imp; //Potencia máxima de salida [W]
        double kv = data.getkv();
        double Kv = kv * Vmp / 100; //Coeficiente de temperatura/Tensión [V/K]
        double ki = data.getki();
        double Ki = ki * Imp / 100; //Coeficiente de temperatura/Corriente [A/K]
        double Ns = data.getNs(); //Nº de células en serie
        /*
        double Iscn = 8.21; //Corriente nominal de cortocircuito [A]
        double Vocn = 32.9; //Tensión nominal de circuito abierto [V]
        double Imp = 7.61; //Corriente del punto de máxima potencia [A]
        double Vmp = 26.3; //Tensión del punto de máxima potencia [V]
        double Pmax_e = Vmp * Imp; //Potencia máxima de salida [W]
        double kv = -0.1230;
        double Kv = -0.1230 * Vmp / 100; //Coeficiente de temperatura/Tensión [V/K]
        double ki = 0.0032;
        double Ki = 0.0032 * Imp / 100; //Coeficiente de temperatura/Corriente [A/K]
        double Ns = 54; //Nº de células en serie
        */
        // Constantes
        double k = 1.3806503e-23; //Boltzmann (J/K)
        double q = 1.60217646e-19; //Carga del electrón [C]
        double a = 1.3; //Constante del diodo
        //Valores nominales
        double Gn = 1000; //Irradiancia nominal [W/m^2] a 25ºC
        double Tn = 25 + 273.15; //Temperatura de operación nominal [K
        //El modelo se ajusta a las condiciones nominales
        double T = 25 + 273.15;
        double G = 1000;
        double Vtn = k * Tn / q; //Tensión térmica nominal
        double Ion = Iscn / (Math.exp(Vocn / a / Ns / Vtn) - 1); //Corriente de saturaci�n nominal del diodo
        //Valores de referencia de Rs y Rp
        double Rs_max = (Vocn - Vmp) / Imp;
        double Rp_min = Vmp / (Iscn - Imp) - Rs_max;
        //Condiciones iniciales de Rp y Rs
        double Rp = Rp_min;
        double Rs = 0;
        double tol = 0.001; //Tolerancia para el error de la potencia calculada
        double error = 1000000000; //Valor por defecto
        // Proceso iterativo para Rs y Rp hasta que la Pmax del modelo=Pmax experimental
        int s = 0;
        double Ipvn=0;
        double[] V = new double[1000];
        //I=zeros(1,size(V,2));
        double[] P = new double[1000];
        double Pmax_m;
        double[] I = new double[1000];
        double[] I_ = new double[1000];
        double[] g = new double[1000];
        double[] glin = new double[1000];
        while (error > tol){
            s = s + 1;
            Ipvn = (Rs + Rp) / Rp * Iscn; //Corriente fotogenerada nominal
            //Incremento de Rs
            Rs = Rs + .01;
            Rp = Vmp * (Vmp + Imp * Rs) / (Vmp * Ipvn - Vmp * Ion * Math.exp((Vmp + Imp * Rs) / Vtn / Ns / a) + Vmp * Ion - Pmax_e);
            //Resoluci�n
            //clear I
            //clear V
            //V=0:Vocn/1000:Vocn; %se cambio 50 por Vocn.
            for (int i = 0; i < 1000; i++) {
                V[i] = i * Vocn / 1000;
                I[i] = 0; //zeros
            }
            for (int j = 0; j < 1000; j++ ){
                g[j] = Ipvn - Ion * (Math.exp((V[j] + I[j] * Rs) / Vtn / Ns / a) - 1) - (V[j] + I[j] * Rs) / Rp - I[j];
                while (Math.abs(g[j]) > 0.001) {
                    g[j] = Ipvn - Ion * (Math.exp((V[j] + I[j] * Rs) / Vtn / Ns / a) - 1) - (V[j] + I[j] * Rs) / Rp - I[j];
                    glin[j] = -Ion * Rs / Vtn / Ns / a * Math.exp((V[j] + I[j] * Rs) / Vtn / Ns / a) - Rs / Rp - 1;
                    I_[j] = I[j] - g[j] / glin[j];
                    I[j] = I_[j];
                }
            }
            //Calcular la potencia usando la ecuaci�n I-V
            for(int i=0;i<1000;i++) {
                P[i] = (Ipvn - Ion * (Math.exp((V[i] + I[i]*Rs) / Vtn / Ns / a) - 1) - (V[i] + I[i]* Rs) / Rp)*V[i];
            }
            Pmax_m=0;
            for(int i=0; i<1000; i++) {
                if (P[i] > Pmax_m)
                    Pmax_m = P[i];
            }
            //%Pmax_2=max(V.*I);% funcion alterna
            error=Math.abs(Pmax_m-Pmax_e);
        }
        String mensaje;
        TextView txtCambiado = (TextView)findViewById(R.id.Rp_min);
        txtCambiado.setText(Double.toString(Rp_min));
        txtCambiado = (TextView)findViewById(R.id.Rp);
        txtCambiado.setText(Double.toString(Rp));
        txtCambiado = (TextView)findViewById(R.id.Rs_max);
        txtCambiado.setText(Double.toString(Rs_max));
        txtCambiado = (TextView)findViewById(R.id.Rs);
        txtCambiado.setText(Double.toString(Rs));
        txtCambiado = (TextView)findViewById(R.id.a);
        txtCambiado.setText(Double.toString(a));
        txtCambiado = (TextView)findViewById(R.id.T);
        txtCambiado.setText(Double.toString(T-273.15));
        txtCambiado = (TextView)findViewById(R.id.G);
        txtCambiado.setText(Double.toString(G));
        txtCambiado = (TextView)findViewById(R.id.Pmax_m);
        txtCambiado.setText(Double.toString(Pmax_e));
        txtCambiado = (TextView)findViewById(R.id.tol);
        txtCambiado.setText(Double.toString(tol));
        txtCambiado = (TextView)findViewById(R.id.P_error);
        txtCambiado.setText(Double.toString(error));
        txtCambiado = (TextView)findViewById(R.id.Ipv);
        txtCambiado.setText(Double.toString(Ipvn));
        txtCambiado = (TextView)findViewById(R.id.Isc);
        txtCambiado.setText(Double.toString(Iscn));
        txtCambiado = (TextView)findViewById(R.id.Ion);
        txtCambiado.setText(Double.toString(Ion));
        txtCambiado = (TextView)findViewById(R.id.Ion);
        txtCambiado.setText(Double.toString(Ion));
        //Cálculo con variaciones de irradancia y temperatura Anual.
        double[] PG = new double[12];
        double[] PTemp=new double[12];
        //Datos meteorologicos de la nasa de Salamanca, Guanajuato.
        // Horas totales de luz diurna.
        double[] HS = {11.0,11.4,12.0,12.6,13.1,13.3,13.2,12.8,12.2,11.7,11.1,10.9};
        //Irradiancia por mes para una celda con inclinación de 20°
        double []GA=new double[12];
        for (int i=0;i<12;i++){
            GA[i]=1000*GAH[i]/HS[i];
        }
        //Temperatura del aire a una altura de diez metros.
        double[] TC={13.9/15.6/18.0,20.5,21.4,19.5,18.5,18.5,17.7,16.5,15.1,14.1};
        String[] Meses={"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
        for(int i =0;i<12;i++){
            FCORR(Iscn,Vocn,Ns,kv,ki,a,Rs,Rp,GA[i],25);
        }

    }
    public void FCORR(double Isc, double Voc, double Ns, double Kv, double Ki, double A, double Rs, double Rsh, double G, double TC) {
        //Función que corrige de acuerdo a la temperatura TC, y la irradación G.
        double Gref = 1000;
        double q = 1.60217646e-19;
        double k = 1.3806503e-23;
        double TK = TC + 273.15;  //Temperatura de la celda en Kelvin
        double vt = (A * k * TK * Ns) / q; //voltaje termíco
        double Isc_T = Isc + ((Isc * (Ki / 100)) * (TC - 25)); //Isc con coeficiente de Temperatur
        double Voc_T = Voc + ((Voc * (Kv / 100)) * (TC - 25)); //Voc con coeficiente de Temperature
        double I0 = Isc_T / (Math.exp(Voc_T / vt) - 1); //Corriente de saturación inversa
        double IL = Isc_T * (G / Gref); //Corriente con Luz
        double i = 0; //Fija la corriente inicial i = 0
        int idx = 1;
        //I = zeros(1, length(0:Voc_T / 1000:Voc_T));
        pI = new double[(int)(Voc_T*1000)];
        pV = new double[(int)(Voc_T*1000)];
        pP = new double[(int)(Voc_T*1000)];
        for(i=0;i<Voc_T*1000;i++){
            pI[(int)i] = 0;
            pV[(int)i] = 0;
            pP[(int)i] = 0;
        }
        for (double V=0; V<=Voc_T*1000;V+=Voc_T/1000){
            pI[idx] = IL - I0 * (Math.exp((V + (i * Rs)) / vt) - 1) - ((V + (i * Rs)) / Rsh);
            i = pI[idx]; //Actualiza la corriente
            idx++;
        }
        //V = 0:Voc_T / 1000:Voc_T;
        for(i=0;i<(Voc_T*1000)-1;i++){
            pV[(int)i+1] +=pV[(int)i]+Voc_T / 1000;
        }
        //P = I. * V;
        String Fmensaje=" ";
        for(i=0;i<(Voc_T*1000)-1;i++){
            pP[(int)i] = pI[(int)i]*pV[(int)i];
            if(i<20)
                Fmensaje+=Double.toString(pP[(int)i]);

        }
        //Toast.makeText(this,Fmensaje,Toast.LENGTH_LONG).show();
    }
}