package com.example.retrofitpytania;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<Pytanie> pytania;
    TextView textViewTrescPytania;
    RadioButton radioButtonA;
    RadioButton radioButtonB;
    RadioButton radioButtonC;
    RadioGroup radioGroup;

    Button buttonDalej;
    int licznik =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTrescPytania = findViewById(R.id.textViewTrescPytania);
        radioButtonA = findViewById(R.id.radioButton);
        radioButtonB = findViewById(R.id.radioButton2);
        radioButtonC = findViewById(R.id.radioButton3);
        buttonDalej = findViewById(R.id.button);
        radioGroup = findViewById(R.id.radioGroup);
      /*  int idRadio[] = new int[]{R.id.radioButton,
                R.id.radioButton2,
                R.id.radioButton3};
        int poprawna = 1;
        int zaznaczona = radioGroup.getCheckedRadioButtonId();
        if(idRadio[poprawna] == zaznaczona)
        */

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/mechaniktgmobilne/retrofit_pytania/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Pytanie>> call =jsonPlaceHolderApi.getPytania();
        call.enqueue(
                new Callback<List<Pytanie>>() {
                    @Override
                    public void onResponse(Call<List<Pytanie>> call, Response<List<Pytanie>> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(MainActivity.this,
                                    ""+response.code(),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pytania = response.body();
                        Toast.makeText(MainActivity.this,
                                pytania.get(0).getTrescPytania()
                                , Toast.LENGTH_SHORT).show();
                        wypelnijPytania(0);
                    }

                    @Override
                    public void onFailure(Call<List<Pytanie>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                ""+t.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        );

        buttonDalej.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //TODO: sprawdzić poprawność odpowiedzi, liczyć punkty
                        if(licznik<pytania.size()-1) {
                            licznik++;
                            wypelnijPytania(licznik);
                        }
                        else{
                            //jak dopisać punkty do json

                        }
                    }
                }
        );

    }
    private void wypelnijPytania(int ktore){
        textViewTrescPytania.setText(pytania.get(ktore).getTrescPytania());
        radioButtonA.setText(pytania.get(ktore).getOdpa());
        radioButtonB.setText(pytania.get(ktore).getOdpb());
        radioButtonC.setText(pytania.get(ktore).getOdpc());

    }


}
