package com.example.myapplic;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editEntrada;
    Spinner spinnerEntrada;
    Spinner spinnerSaida;
    TextView textResultado;

    private void converter(){
        try{
            Double entrada = Double.parseDouble(editEntrada.getText()
                    .toString()
                    .replace(".", "")
                    .replace(",", "."));

            int indexEntrada = spinnerEntrada.getSelectedItemPosition();
            int indexSaida = spinnerSaida.getSelectedItemPosition();

            int diferenca = Math.abs(indexEntrada - indexSaida);

            double resultado = entrada;
            if(indexEntrada > indexSaida){
                resultado = entrada / (Math.pow(10,diferenca));
            } else if (indexEntrada < indexSaida) {
                resultado = entrada * (Math.pow(10,diferenca));
            }

            String[] siglasUnidades = {
                    "km",
                    "hm",
                    "dam",
                    "m",
                    "dm",
                    "cm",
                    "mm"
            };
            NumberFormat formato = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
            formato.setMinimumFractionDigits(2);
            formato.setMaximumFractionDigits(2);

            String stringResultado = formato.format(resultado);

            textResultado.setText(stringResultado + " " + siglasUnidades[indexSaida]);
        }catch (Exception e){
            textResultado.setText("0,00");
            System.out.println(e.getMessage());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editEntrada = findViewById(R.id.editEntrada);
        spinnerEntrada = findViewById(R.id.spinnerEntrada);
        spinnerSaida = findViewById(R.id.spinnerSaida);
        textResultado = findViewById(R.id.textResultado);


        editEntrada.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                converter();
            }
        });

        spinnerEntrada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                converter();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinnerSaida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                converter();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }
}