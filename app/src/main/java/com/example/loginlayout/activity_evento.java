package com.example.loginlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import static android.widget.Toast.LENGTH_SHORT;

public class activity_evento extends AppCompatActivity {
    private TextView deporteText;
    private TextView fechaText;
    private TextView horaText;
    private TextView numeroParticipantes;
    private TextView numeroAsistentes;
    private TextView descripcionText;
    private TextView eventoText;
    private Button buttonModificar;
    private Button buttonEliminar;
    private String idEvento;

    private RequestQueue queue; //cola de las solicitudes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        deporteText = findViewById(R.id.deporte);
        fechaText = findViewById(R.id.tv_fecha);
        horaText = findViewById(R.id.tv_hora2);
        numeroParticipantes = findViewById(R.id.numeroParticipantes);
        numeroAsistentes = findViewById(R.id.numeroAsistentes);
        descripcionText = findViewById(R.id.descriptionText);
        eventoText = findViewById(R.id.eventoText);
        buttonModificar = findViewById(R.id.buttonModificar);
        buttonEliminar = findViewById(R.id.buttonEliminar);

        idEvento = "5ddeb5c0b43de93e240d015d";

        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Entra en Modificar");
                Intent i = new Intent(v.getContext(),activity_modificar_evento.class);
                i.putExtra("id",idEvento);
                System.out.println("Peta en Modificar");
                startActivity(i);
            }
        });

        queue = Volley.newRequestQueue(this); //inicializar el requestqueue
        Request(idEvento);
    }

    public void Request(String id){
        String url = "http://10.4.41.144:3000/event/info/"+ id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    deporteText.setText("Evento de " + response.getString("sport"));
                    descripcionText.setText(response.getString("description"));
                    horaText.setText(response.getString("date").substring(11, 16));
                    fechaText.setText(response.getString("date").substring(0, 10));
                    numeroParticipantes.setText(Integer.toString(response.getInt("max_users")));
                    numeroAsistentes.setText(Integer.toString(response.getInt("initial_users")));
                    eventoText.setText(response.getString("level"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity_evento.this, error.toString(), LENGTH_SHORT).show();
                System.out.println(error.toString());
            }
        });
        queue.add(request);
    }


    public void Eliminar(View v){

        String idEvento = "5ddeb5c0b43de93e240d015d";
        String url = "http://10.4.41.144:3000/event/delete/" + idEvento;

        JSONObject req = new JSONObject();

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, req, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String responses = response.toString();
                Toast.makeText(activity_evento.this, responses, LENGTH_SHORT).show();
                System.out.println(responses);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity_evento.this, error.toString(), LENGTH_SHORT).show();
                System.out.println(error.toString());
            }
        });
        queue.add(request);
    }





}
