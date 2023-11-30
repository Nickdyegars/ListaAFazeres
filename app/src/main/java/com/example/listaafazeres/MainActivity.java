package com.example.listaafazeres;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.listaafazeres.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;
    private Globals globals;
    private AdapterItens adapterItens;
    private List<AFazer> listaAFazer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        globals = new Globals(this);
        listaAFazer = globals.consultarAFazer();
        configurarLayout();
    }

    private void configurarLayout()
    {
        try {
            binding.botaoAdicionarAFazer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    configurarModal();
                }
            });

            configurarAdapter();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void configurarAdapter(){
        adapterItens = new AdapterItens(listaAFazer, getBaseContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.reyclerItens.setLayoutManager(layoutManager);
        binding.reyclerItens.setHasFixedSize(true);
        binding.reyclerItens.setAdapter(adapterItens);
        adapterItens.notifyDataSetChanged();
    }

    private void configurarModal()
    {
        // Inflar o layout do conteúdo do modal
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.modal_adcionar_a_fazer, null);

        // Acessar elementos do layout personalizado (por exemplo, TextView)
        AppCompatEditText editAFazer = view.findViewById(R.id.editAdicionar);


        // Configurar o AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view)
                .setTitle("Adicionar a fazer")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AFazer aFazerNovo = new AFazer();
                        aFazerNovo.setNome(editAFazer.getText().toString());
                        aFazerNovo.setFinalizado(false);
                        globals.adicionarAFazer(aFazerNovo);
                        listaAFazer = globals.consultarAFazer();
                        configurarAdapter();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // Exibir o AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}