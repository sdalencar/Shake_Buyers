package com.allin.sdainfo.shakebuyers.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.allin.sdainfo.shakebuyers.R;
import com.allin.sdainfo.shakebuyers.helper.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class AnuncioActivity extends AppCompatActivity {

    private FirebaseAuth autentincacao;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tela_anunciar));
        setSupportActionBar(toolbar);

        autentincacao = ConfiguracaoFirebase.getReferenciaAutenticacao();


    }
    //cria o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_anuncio, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //prepara o menu pra exibir
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(autentincacao.getCurrentUser() == null){//deslogado
            menu.setGroupVisible(R.id.menu_deslogado, true);
        }else {//logado
            menu.setGroupVisible(R.id.menu_logado, true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    //acoes menu


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair:
                autentincacao.signOut();
                invalidateOptionsMenu();
                break;
            case R.id.menu_anuncios:
                startActivity(new Intent(getApplicationContext(), MeusAnunciosActivity.class));
                break;
            case R.id.menu_cadastrar:
                startActivity(new Intent(getApplicationContext(), CadastrarActivity.class));
                break;
            case R.id.menu_logar:
                startActivity(new Intent(getApplicationContext(), LogarActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}