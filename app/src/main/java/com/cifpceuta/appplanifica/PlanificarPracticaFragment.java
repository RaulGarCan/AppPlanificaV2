package com.cifpceuta.appplanifica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanificarPracticaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanificarPracticaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_GRUPOS = "grupos";

    // TODO: Rename and change types of parameters
    private String grupos;

    public PlanificarPracticaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanificarPracticaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanificarPracticaFragment newInstance(String grupos) {
        PlanificarPracticaFragment fragment = new PlanificarPracticaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GRUPOS, grupos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            grupos = getArguments().getString(ARG_GRUPOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_planificar_practica, container, false);
        Spinner spGrupo = rootView.findViewById(R.id.sp_grupo_plan_practica);
        Spinner spModulo = rootView.findViewById(R.id.sp_modulo_plan_practica);
        Button btnGuardar = rootView.findViewById(R.id.btn_guardar_plan_practica);
        EditText etTituloPrac = rootView.findViewById(R.id.et_titulo_practica_plan_practica);
        EditText etFechaInicioPrac = rootView.findViewById(R.id.et_fecha_inicio_plan_practica);
        EditText etFechaFinPrac = rootView.findViewById(R.id.et_fecha_fin_plan_practica);
        EditText etDescripcionPrac = rootView.findViewById(R.id.et_descripcion_plan_practica);

        String[] grupos = new String[Grupos.values().length];
        for(int i = 0; i<Grupos.values().length; i++){
            grupos[i] = Grupos.values()[i].name();
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item,grupos);
        spGrupo.setAdapter(adapter);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloPrac = etTituloPrac.getText().toString();
                String fechaInicioPrac = etFechaInicioPrac.getText().toString();
                String fechaFinPrac = etFechaFinPrac.getText().toString();
                String grupoPrac = spGrupo.getSelectedItem().toString();
                String moduloPrac = spModulo.getSelectedItem().toString();
                String descripcionPrac = etDescripcionPrac.getText().toString();

                Map<String, Object> datos = new HashMap<>();
                datos.put("titulo",tituloPrac);
                datos.put("fechaInicio",fechaInicioPrac);
                datos.put("fechaFin",fechaFinPrac);
                datos.put("descripcion",descripcionPrac);

                db.collection("practicas").document(grupoPrac+":"+moduloPrac).set(datos).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d("GuardarPracticaNueva","Datos guardados correctamente");
                                    Toast.makeText(PlanificarPracticaFragment.this.getContext(),"Practica guardada correctamente",Toast.LENGTH_LONG).show();
                                    etTituloPrac.setText("");
                                    etFechaInicioPrac.setText("");
                                    etFechaFinPrac.setText("");
                                    etDescripcionPrac.setText("");
                                } else {
                                    Log.w("GuardarPracticaNueva","Error: "+task.getException());
                                }
                            }
                        });
            }
        });

        spGrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String grupo = parent.getItemAtPosition(position).toString();
                db.collection("modulos").document(grupo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Map<String, Object> datos = document.getData();
                                ArrayList<String> listaModulos = (ArrayList<String>) datos.get(grupo);
                                String[] modulos = new String[listaModulos.size()];
                                for(int i = 0; i<modulos.length; i++){
                                    modulos[i] = listaModulos.get(i);
                                }
                                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(PlanificarPracticaFragment.this.getContext(), android.R.layout.simple_spinner_dropdown_item,modulos);
                                spModulo.setAdapter(adapter);
                            } else {
                                Log.w("ModulosGrupos","Documento no encontrado");
                            }
                        } else {
                            Log.w("ModulosGrupos","Error: "+task.getException());
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
}