package com.allin.sdainfo.shakebuyers.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.allin.sdainfo.shakebuyers.R;

public class Mensagem {

    private Context context;
    public Mensagem(Context context) {
        this.context = context;
    }

    public void mensagemCurta(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void mensagemLonga(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


}












