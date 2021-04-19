package com.allin.sdainfo.shakebuyers.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allin.sdainfo.shakebuyers.R;
import com.allin.sdainfo.shakebuyers.helper.ConfiguracaoFirebase;
import com.allin.sdainfo.shakebuyers.helper.Mensagem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogarActivity extends AppCompatActivity {

    private EditText et_email, et_senha;
    private Mensagem msg;
    private Button bt_entrar;
    private TextView tv_cadastrar;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tela_logar));
        setSupportActionBar(toolbar);

        et_email = findViewById(R.id.et_logar_email);
        et_senha = findViewById(R.id.et_logar_senha);
        tv_cadastrar = findViewById(R.id.tv_logar_cadastrar);
        bt_entrar = findViewById(R.id.bt_logar_salvar);

        autenticacao =  ConfiguracaoFirebase.getReferenciaAutenticacao();

        msg = new Mensagem(getApplicationContext());

        tv_cadastrar.setOnClickListener(view -> {
            startActivity(new Intent(LogarActivity.this, CadastrarActivity.class));
        });

        bt_entrar.setOnClickListener(view -> {
            String nome = et_email.getText().toString();
            String senha = et_senha.getText().toString();

            if (!nome.isEmpty()) {
                if (!senha.isEmpty()) {
                    limpar();
                    //logar
                    autenticacao.signInWithEmailAndPassword(
                            nome, senha
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                limpar();
                                msg.mensagemCurta("Entrando...");
                                startActivity(new Intent(LogarActivity.this, AnuncioActivity.class));
                            } else {
                                msg.mensagemCurta("Erro: " + task.getException());
                            }
                        }
                    })
                    ;

                } else {
                    msg.mensagemCurta("Preecha a sua senha");
                }
            } else {
                msg.mensagemCurta("Preecha a seu nome");
            }
        });

    }

    private void limpar() {
        et_email.setText("");
        et_senha.setText("");
    }

}





