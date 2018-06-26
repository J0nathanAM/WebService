package jonathanalvarez.webservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by IDS Comercial on 29/09/2017.
 */

public class OpninionesActivity extends AppCompatActivity {

    TextView name;
    ArrayList<Comentario> comentarios;
    ListView listacom;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.datos_restaurante);
        super.onCreate(savedInstanceState);

        final Restaurante restaurante =(Restaurante) getIntent().getExtras().getSerializable("restaurante");
        name=(TextView)findViewById(R.id.namerest);

        name.setText(restaurante.getNombre());
        btn=(Button)findViewById(R.id.btnagregar);

        listacom=(ListView)findViewById(R.id.listacomentarios);

        ConexionDetector cd=new ConexionDetector(this);
        if (cd.isConnectedInternet()){
            getOniniosOnLine(restaurante.getId());
        }else{
            getOpinionsFromFile();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OpninionesActivity.this,AgregarComentario.class);
                intent.putExtra("idrestaurante",restaurante.getId());
                startActivity(intent);
            }
        });

    }

    class AdapterComentario extends ArrayAdapter<Comentario> {
        AdapterComentario(){
            super(OpninionesActivity.this,R.layout.comentario_row,comentarios);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View row= inflater.inflate(R.layout.comentario_row,parent,false);

            ImageView imagen=(ImageView)row.findViewById(R.id.imagen);
            TextView usuario=(TextView)row.findViewById(R.id.usuariorow);
            TextView  coment=(TextView)row.findViewById(R.id.comentariorow);

            Comentario com=comentarios.get(position);

            usuario.setText(com.getUsuario());
            coment.setText(com.getComentario());
            if (com.getCalificacion().equals("1") || com.getCalificacion().equals("2")){
                imagen.setImageResource(R.drawable.triste);
            }
            if (com.getCalificacion().equals("3")){
                imagen.setImageResource(R.drawable.normal);
            }
            if (com.getCalificacion().equals("4") || com.getCalificacion().equals("5")){
                imagen.setImageResource(R.drawable.feliz);
            }

            return row;
        }
    }

    public void getOniniosOnLine(int idrestaurante){
        final String url="http://alexchaps.com/atl/resenas.php?restaurante=";

        final JsonObjectRequest objectRequest=new JsonObjectRequest(com.android.volley.Request.Method.GET,
                url + String.valueOf(idrestaurante), (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("respuesta", response.toString());
                comentarios=new ArrayList<Comentario>();
                try{
                    JSONArray array=response.getJSONArray("resenas");
                    for (int i=0;i<array.length();i++){
                        JSONObject jsonObject= array.getJSONObject(i);
                        String usuario=jsonObject.getString("usuario");
                        String comentario=jsonObject.getString("comentario");
                        String calif=jsonObject.getString("estrellas");

                        comentarios.add(new Comentario(usuario,comentario,calif));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listacom.setAdapter(new AdapterComentario());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("respuesta", error.toString());
            }
        }
        );
        AppController.getInstance().addtoRecuestQueue(objectRequest);
    }

    public void getOpinionsFromFile(){

    }

}
