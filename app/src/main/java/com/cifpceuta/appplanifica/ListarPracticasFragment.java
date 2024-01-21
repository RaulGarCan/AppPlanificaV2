package com.cifpceuta.appplanifica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarPracticasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarPracticasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListarPracticasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarPracticasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarPracticasFragment newInstance(String param1, String param2) {
        ListarPracticasFragment fragment = new ListarPracticasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_listar_practicas, container, false);
        RecyclerView rvListaPracticas = rootView.findViewById(R.id.rv_lista_practicas);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("estudiantes").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        String grupo = document.get("grupo").toString();
                        db.collection("practicas").document(grupo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if(document.exists()){
                                        ArrayList<Practica> practicas = (ArrayList<Practica>) document.get("practicas");
                                        rvListaPracticas.setAdapter(new PracticasAdapter(practicas, grupo));
                                        rvListaPracticas.setLayoutManager(new LinearLayoutManager(ListarPracticasFragment.this.getContext()));
                                    } else {
                                        Log.w("RellenarRecyclerView","Documento no encontrado");
                                    }
                                } else {
                                    Log.w("RellenarRecyclerView","Error: "+task.getException());
                                }
                            }
                        });
                    } else {
                        Log.w("RecuperarGrupoRecyclerView","Documento no encontrado");
                    }
                } else {
                    Log.w("RecuperarGrupoRecyclerView","Error: "+task.getException());
                }
            }
        });

        return rootView;
    }
}