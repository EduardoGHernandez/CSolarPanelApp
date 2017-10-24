package com.android.csolarpanelapp.csolarpanelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static int Is = 0;
    ModelData info = new ModelData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Iniciar dat
        //Toast.makeText(this,Double.toString(data.getImp()),Toast.LENGTH_LONG).show();
    }
    public void act_manufacter(View view){
        Intent intent = new Intent(this, ManufacterData.class);
        //intent.putExtra("PARCELABLE",data);
        startActivityForResult(intent,Is);
    }
    public void show_data(View view){
        Intent intent = new Intent(this,Statistics.class);
        intent.putExtra("Resultado",info);
        setResult(RESULT_OK, intent);
        startActivity(intent);
        //Toast.makeText(this,Double.toString(info.getImp()),Toast.LENGTH_LONG).show();
    }
    public void act_location(View view){
        Intent intent = new Intent(this, LocationData.class);
        startActivity(intent);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == RESULT_CANCELED) {
            // Si es así mostramos mensaje de cancelado por pantalla.
            Toast.makeText(this, "Resultado cancelado", Toast.LENGTH_SHORT).show();
        } else {
            // De lo contrario, recogemos el resultado de la segunda actividad.
            info=(ModelData)data.getParcelableExtra("Resultado");
            // Y tratamos el resultado en función de si se lanzó para rellenar el
            // nombre o el apellido.
            Toast.makeText(this,Double.toString(info.getImp()),Toast.LENGTH_LONG).show();
        }
    }
}
