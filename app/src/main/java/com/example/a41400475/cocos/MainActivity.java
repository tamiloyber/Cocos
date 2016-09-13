package com.example.a41400475.cocos;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.a41400475.cocos.model.clsJuego;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {

    CCGLSurfaceView vistaPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        vistaPrincipal = new CCGLSurfaceView(this);
        setContentView(vistaPrincipal);
    }

    @Override
    protected void onStart(){
        super.onStart();
        clsJuego juego = new clsJuego(vistaPrincipal);
        juego.ComenzarJuego();
    }
}
