package com.cifpceuta.appplanifica;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilEstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilEstudianteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_CORREO = "correo";
    private static final String ARG_TURNO = "turno";
    private static final String ARG_GRUPO = "grupo";

    // TODO: Rename and change types of parameters
    private String mNombre;
    private String mCorreo;
    private String mTurno;
    private String mGrupo;


    public PerfilEstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nombre Nombre.
     * @param correo Correo.
     * @param turno Turno.
     * @param grupo Grupo.
     * @return A new instance of fragment PerfilEstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilEstudianteFragment newInstance(String nombre, String correo, String turno, String grupo) {
        PerfilEstudianteFragment fragment = new PerfilEstudianteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, nombre);
        args.putString(ARG_CORREO, correo);
        args.putString(ARG_TURNO, turno);
        args.putString(ARG_GRUPO, grupo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNombre = getArguments().getString(ARG_NOMBRE);
            mCorreo = getArguments().getString(ARG_CORREO);
            mTurno = getArguments().getString(ARG_TURNO);
            mGrupo = getArguments().getString(ARG_GRUPO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView tvNombre, tvCorreo, tvTurno, tvGrupo;
        View rootView = inflater.inflate(R.layout.fragment_perfil_estudiante, container, false);
        if(getArguments()!=null){
            mNombre = getArguments().getString(ARG_NOMBRE);
            mCorreo = getArguments().getString(ARG_CORREO);
            mTurno = getArguments().getString(ARG_TURNO);
            mGrupo = getArguments().getString(ARG_GRUPO);
        }
        tvNombre = rootView.findViewById(R.id.tv_nombre_perfil);
        tvCorreo = rootView.findViewById(R.id.tv_correo_perfil);
        tvTurno = rootView.findViewById(R.id.tv_turno_perfil);
        tvGrupo = rootView.findViewById(R.id.tv_grupo_perfil);
        tvNombre.setText(mNombre);
        tvCorreo.setText(mCorreo);
        tvTurno.setText(mTurno);
        tvGrupo.setText(mGrupo);
        // Inflate the layout for this fragment
        return rootView;
    }
}