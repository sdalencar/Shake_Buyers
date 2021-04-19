package com.allin.sdainfo.shakebuyers.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.allin.sdainfo.shakebuyers.R;
import com.allin.sdainfo.shakebuyers.helper.Mensagem;
import com.allin.sdainfo.shakebuyers.helper.Permissoes;
import com.blackcat.currencyedittext.CurrencyEditText;
import com.santalu.maskara.widget.MaskEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CadastrarAnunciosActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_um, iv_dois, iv_tres;
    private Mensagem msg;
    private EditText et_titulo, et_descricao;
    private CurrencyEditText et_valor;
    private Button bt_anunciar;
    private MaskEditText maskEditText_contato;
    private String[] permissoes = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private List<String> listaFotos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_anuncios);

        Permissoes.validarPermissoes(permissoes, this, 1);
        iniciaComponentes();

        bt_anunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void iniciaComponentes() {

        iv_um = findViewById(R.id.iv_um);
        iv_dois = findViewById(R.id.iv_dois);
        iv_tres = findViewById(R.id.iv_tres);
        iv_um.setOnClickListener(this);
         iv_dois.setOnClickListener(this);
        iv_tres.setOnClickListener(this );

        et_titulo = findViewById(R.id.et_cad_titulo);
        et_descricao = findViewById(R.id.et_cad_descricao);
        bt_anunciar = findViewById(R.id.bt_cad_salvar);
        maskEditText_contato = findViewById(R.id.et_cad_telefone);
        msg = new Mensagem(getApplicationContext());

        et_valor = findViewById(R.id.et_cad_valor);
        //configurar o local para setar a mascara da moeda
        Locale locale = new Locale("pt", "BR");
        et_valor.setLocale(locale);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int permissaoRetorno : grantResults) {
            if (permissaoRetorno == PackageManager.PERMISSION_DENIED) {
                mensagemAlerta();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_um:
                setarImagem(1);
                msg.mensagemCurta("um");
                break;
            case R.id.iv_dois:
                setarImagem(2);
                msg.mensagemCurta("doi");
                break;
            case R.id.iv_tres:
                setarImagem(3);
                msg.mensagemCurta("três");
                break;
        }
    }

    private void setarImagem(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK) {
            Uri imagemSelecionada = data.getData();
            String localImagem = imagemSelecionada.toString();

            //configurar imagem no recyclerview
            if (requestCode == 1) {
                iv_um.setImageURI(imagemSelecionada);
                }else if (requestCode == 2) {
                    iv_dois.setImageURI(imagemSelecionada);
                } else if (requestCode == 3) {
                    iv_tres.setImageURI(imagemSelecionada);
                }
                listaFotos.add(localImagem);
            }
        }

    public void mensagemAlerta(){
        AlertDialog.Builder builder =  new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para adicionar fotos preceisamos das permissões.");
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.confirmar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish(); 
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}













