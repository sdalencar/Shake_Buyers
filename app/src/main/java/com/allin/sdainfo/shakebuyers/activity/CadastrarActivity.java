package com.allin.sdainfo.shakebuyers.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.allin.sdainfo.shakebuyers.R;
import com.allin.sdainfo.shakebuyers.helper.ConfiguracaoFirebase;
import com.allin.sdainfo.shakebuyers.helper.Mensagem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastrarActivity extends AppCompatActivity {

    private EditText et_email, et_senha;
    private Mensagem msg;
    private Button bt_gravar;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tela_cadastro));
        setSupportActionBar(toolbar);

        et_email = findViewById(R.id.et_logar_email);
        et_senha = findViewById(R.id.et_logar_senha);
        bt_gravar = findViewById(R.id.bt_logar_salvar);

        autenticacao =  ConfiguracaoFirebase.getReferenciaAutenticacao();

        msg = new Mensagem(getApplicationContext());
        bt_gravar.setOnClickListener(view -> {

            String email = et_email.getText().toString();
            String senha = et_senha.getText().toString();

            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {
                    autenticacao.createUserWithEmailAndPassword(
                            email, senha
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                limpar();
                                msg.mensagemCurta("Usuário salvo com sucesso.");
                                startActivity(new Intent(CadastrarActivity.this, AnuncioActivity.class));
                            }else {
                                String erroExcecao;
                                try {
                                    throw  task.getException();
                                }catch (FirebaseAuthWeakPasswordException e){
                                    erroExcecao = "Digite uma senha mais forte";
                                }catch (FirebaseAuthInvalidCredentialsException e){
                                    erroExcecao = "Digite um e-mail válido";
                                }catch (FirebaseAuthUserCollisionException e){
                                    erroExcecao = "E-mail já cadastrado";
                                }catch (Exception e){
                                    erroExcecao = "Erro ao cadastrar usuário : " + e.getMessage();
                                    e.printStackTrace();
                                }
                                msg.mensagemCurta("Erro: " + erroExcecao);
                            }
                        }
                    });
                    limpar();
                } else {
                    msg.mensagemCurta("Preecha a sua senha");
                }
            } else {
                msg.mensagemCurta("Preecha a seu e-mail");
            }
        });

    }

    private void limpar() {

        et_email.setText("");
        et_senha.setText("");
    }

}





