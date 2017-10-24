package com.android.csolarpanelapp.csolarpanelapp;

import android.content.Intent;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ManufacterData extends AppCompatActivity {
    public int a = 0;
    public int b = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacter_data);
    }
    public void sendData(View viwe){
        //Datos de fabricante por defecto
        double Iscn = 8.68; //Corriente nominal de cortocircuito [A]
        double Vocn = 37.6; //Tensión nominal de circuito abierto [V]
        double Imp = 8.10; //Corriente del punto de máxima potencia [A]
        double Vmp = 30.9; //Tensión del punto de máxima potencia [V]
        double Pmax_e = Vmp * Imp; //Potencia máxima de salida [W]
        double kv = 0.329;
        double Kv = kv * Vmp / 100; //Coeficiente de temperatura/Tensión [V/K]
        double ki = 0.038;
        double Ki = ki * Imp / 100; //Coeficiente de temperatura/Corriente [A/K]
        double Ns = 60; //Nº de células en serie
        Toast.makeText(this,"Espere un momento",Toast.LENGTH_LONG).show();
        String mensaje;
        //Devolver valores a main
        ModelData model = new ModelData();
        model.setIscn(Iscn);
        model.setVocn(Vocn);
        model.setImp(Imp);
        model.setVmp(Vmp);
        model.setPmax_e(Pmax_e);
        model.setkv(kv);
        model.setKv(Kv);
        model.setki(ki);
        model.setKi(Ki);
        model.setNs(Ns);
        Intent data = new Intent();
        data.putExtra("Resultado",model);
        setResult(RESULT_OK, data);
        finish();
    }

}
