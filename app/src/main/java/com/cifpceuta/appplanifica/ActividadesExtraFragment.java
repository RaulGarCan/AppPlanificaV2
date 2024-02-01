package com.cifpceuta.appplanifica;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActividadesExtraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActividadesExtraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ActividadExtra actividadExtra;

    // TODO: Rename and change types of parameters

    public ActividadesExtraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActividadesExtraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActividadesExtraFragment newInstance(String param1, String param2) {
        ActividadesExtraFragment fragment = new ActividadesExtraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_actividades_extra, container, false);
        FloatingActionButton fabBtn;
        RecyclerView rvActExtras;
        fabBtn = rootView.findViewById(R.id.fab_add_actividad_extra);
        rvActExtras = rootView.findViewById(R.id.rv_actividades_extra);
        rvActExtras.setAdapter(new ActExtrasAdapter(new ArrayList<>()));
        rvActExtras.setLayoutManager(new LinearLayoutManager(this.getContext()));
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearDialog(rvActExtras);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
    public void crearDialog(RecyclerView rvActExtras){
        //Creamos el DIALOG asociado al ActivityMAIN
        Dialog dialog = new Dialog(ActividadesExtraFragment.this.getContext());

        //Le asociamos el layout correspondiente
        dialog.setContentView(R.layout.layout_dialog_add_actividades_extra);

        //Recuperamos los views dentro de dicho layout para recuperar sus valores posteriormente
        EditText etTitulo = dialog.findViewById(R.id.et_titulo_actividad_dialog);
        EditText etFecha = dialog.findViewById(R.id.et_fecha_actividad_extra);
        Button btnDialogAdd = dialog.findViewById(R.id.btn_add_actividad_extra);
        Spinner spGrupos = dialog.findViewById(R.id.sp_grupos_dialog);
        ActExtrasAdapter adapterRv = (ActExtrasAdapter) rvActExtras.getAdapter();

        String[] grupos = {"DAM1","DAM2","SMR1","SMR2"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item,grupos);
        spGrupos.setAdapter(adapter);


        //Establecemos el listener para capturar datos y realizar acción de añadir
        btnDialogAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo, grupo, fecha;
                titulo = etTitulo.getText().toString();
                grupo = spGrupos.getSelectedItem().toString();
                fecha = etFecha.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> datos = new HashMap<>();
                datos.put("titulo",titulo);
                datos.put("grupo",grupo);
                datos.put("fecha",fecha);
                actividadExtra = new ActividadExtra(titulo, grupo, fecha);
                adapterRv.addActividadExtra(actividadExtra);
                db.collection("actextra").document().set(datos).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ActividadesExtraFragment.this.getContext(),"Actividad extraescolar almacenada correctamente en Firestore",Toast.LENGTH_LONG).show();
                        } else {
                            Log.w("ActividadesExtra","Error: "+task.getException());
                        }
                    }
                });

                //Esta llamada cierra el dialogo
                dialog.dismiss();

            }
        });


        //Esta llamada abre el diálogo
        dialog.show();

    }
}