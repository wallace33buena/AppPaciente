package br.com.senac.apppaciente;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.senac.apppaciente.modelo.AppPaciente;
import br.com.senac.apppaciente.webservice.Api;
import br.com.senac.apppaciente.webservice.RequestHandler;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    ListView listView;
    List<AppPaciente> apppacienteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.barraProgresso);
        listView = findViewById(R.id.listViewTarefa);
        apppacienteList = new ArrayList<>();

        readAppPaciente();

    }

    private void readAppPaciente() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_APPPACIENTE,null,CODE_GET_REQUEST);
        request.execute();
    }
    private  void  deleteAppPaciente(int id){
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_APPPACIENTE + id,null,CODE_GET_REQUEST);
        request.execute();
    }
    private  void refreshAppPacienteList(JSONArray apppaciente)throws JSONException {
        apppacienteList.clear();

        for (int i = 0; i < apppaciente.length(); i++){
            JSONObject obj = apppaciente.getJSONObject(i);
            apppacienteList.add(new AppPaciente(
                    obj.getInt("id"),
                    obj.getString("nomepaciente"),
                    obj.getString("horariopaciente"),
                    obj.getString("medicopaciente"),
                    obj.getString("datapaciente")
            ));
        }
        AppPacienteAdapter adapter = new AppPacienteAdapter(apppacienteList);
        listView.setAdapter(adapter);

    }
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(),object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshAppPacienteList(object.getJSONArray("apppaciente"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class AppPacienteAdapter extends ArrayAdapter<AppPaciente> {

        List<AppPaciente> apppacienteList;
        public AppPacienteAdapter(List<AppPaciente> apppacienteList){
            super(MainActivity.this, R.layout.layout_apppaciente_list, apppacienteList);
            this.apppacienteList = apppacienteList;
        }
        public  View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View listviewItem = inflater.inflate(R.layout.layout_apppaciente_list,null,true);
            TextView textViewServico = listviewItem.findViewById(R.id.textViewServico);
            TextView textViewAtendido = listviewItem.findViewById(R.id.textViewAtendido);
            final AppPaciente appPaciente = apppacienteList.get(position);
            textViewServico.setText(appPaciente.getNomepaciente());
            textViewAtendido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Atendido" + appPaciente.getNomepaciente())
                            .setMessage("O paciente foi Atendido ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteAppPaciente(appPaciente.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
            return listviewItem;
        }

    }
}
