package jonathanalvarez.webservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by IDS Comercial on 29/09/2017.
 */

public class RestauranteActivity extends AppCompatActivity{

    ArrayList<Restaurante> restaurants;
    ListView lista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.lista_restaurantes);
        super.onCreate(savedInstanceState);

            MyPreference preference=new MyPreference(RestauranteActivity.this);
            if (preference.isFirstTime()){
                Toast.makeText(this, "Bienvenido a la Aplicación", Toast.LENGTH_SHORT).show();
                preference.setOld(true);
        }else{
                Toast.makeText(this, "Gracias por volver a la Aplicación", Toast.LENGTH_SHORT).show();
            }



        lista=(ListView)findViewById(R.id.listarestaurantes);

        restaurants=new ArrayList<Restaurante>();
        restaurants.add(new Restaurante(0,"Burger King",R.drawable.burger));
        restaurants.add(new Restaurante(1,"Dominos Pizza",R.drawable.dominos));
        restaurants.add(new Restaurante(2,"Restaurante de Sushi",R.drawable.sushi));

        lista.setAdapter(new AdapterRestaurant());

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent("ids.restaurantes.opiniones");
                intent.putExtra("restaurante",restaurants.get(i));
                startActivity(intent);
            }
        });
    }

    class AdapterRestaurant extends ArrayAdapter<Restaurante> {
        AdapterRestaurant(){
            super(RestauranteActivity.this,R.layout.row_restaurante,restaurants);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View row= inflater.inflate(R.layout.row_restaurante,parent,false);

            ImageView imagen=(ImageView)row.findViewById(R.id.imagen);
            TextView nombrerest=(TextView)row.findViewById(R.id.nombreresta);

            Restaurante rest=restaurants.get(position);

            imagen.setImageResource(rest.getImagen());
            nombrerest.setText(rest.getNombre());

            return row;
        }
    }

}
