package jonathanalvarez.webservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IDS Comercial on 29/09/2017.
 */

public class AgregarComentario extends AppCompatActivity {

    EditText user,opinion;
    Button guardaropi;
    TextView calificacion;
    SeekBar barracalif;
    int calif;
    int idrestaurant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.agregar_activity);
        super.onCreate(savedInstanceState);

        idrestaurant=getIntent().getExtras().getInt("idrestaurante");

        user=(EditText)findViewById(R.id.usu);
        opinion=(EditText)findViewById(R.id.opi);
        guardaropi=(Button)findViewById(R.id.agregar);
        calificacion=(TextView)findViewById(R.id.calificacion);
        barracalif=(SeekBar)findViewById(R.id.barra);

        barracalif.setProgress(1);
        barracalif.setMax(5);

        barracalif.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progreso, boolean b) {
                calif=progreso;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                calificacion.setText("Calificación "+calif+"/5");
            }
        });

        guardaropi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirComentario();
            }
        });
    }

    public void subirComentario() {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo Comentario", "Por Favor espere ... ", false, false);
        String url = "http://alexchaps.com/atl/nuevaResena.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Toast.makeText(AgregarComentario.this, "Comentario subido con éxito :)", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(AgregarComentario.this,RestauranteActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                System.out.println("<<<<"+String.valueOf(idrestaurant));
                Toast.makeText(AgregarComentario.this, "Error al subir comentario, intente de nuevo :(", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("restaurante", String.valueOf(idrestaurant));
                params.put("usuario", user.getText().toString());
                params.put("comentario", opinion.getText().toString());
                params.put("estrellas", String.valueOf(calif));
                return params;
            }
        };
        AppController.getInstance().addtoRecuestQueue(request);
    }
}
