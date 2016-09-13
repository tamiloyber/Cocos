package com.example.a41400475.cocos.model;

import android.util.Log;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateBy;
import org.cocos2d.actions.interval.RotateTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 41400475 on 13/9/2016.
 */
public class clsJuego {

    CCGLSurfaceView _vistaJuego;
    CCSize pantallaDispositivo;
    Sprite naveJugador;
    Sprite imagenFondo;
    Label tituloJuego;
    Sprite enemigo;

    public clsJuego(CCGLSurfaceView vistaJuego) {
        _vistaJuego = vistaJuego;
    }

    public void ComenzarJuego(){
        Log.d("Comenzar", "Comienza el juego");
        Director.sharedDirector().attachInView(_vistaJuego);
        pantallaDispositivo = Director.sharedDirector().displaySize();
        Director.sharedDirector().runWithScene(EscenaJuego());
    }

    private Scene EscenaJuego(){
        Scene escenaDevolver = Scene.node();

        CapaFondo capaFondo = new CapaFondo();
        CapaFrente capaFrente = new CapaFrente();
        escenaDevolver.addChild(capaFondo, -10);
        escenaDevolver.addChild(capaFrente, 10);

        return escenaDevolver;
    }

    class CapaFondo extends Layer {

        public CapaFondo(){
            PonerCapaFondo();
            PonerTitulo();
        }

        private void PonerCapaFondo(){
            imagenFondo = Sprite.sprite("fondo.png");
            imagenFondo.setPosition(pantallaDispositivo.width/2, pantallaDispositivo.height/2);
            imagenFondo.runAction(ScaleBy.action(0.01f, 3.0f, 4.0f));
            super.addChild(imagenFondo);
        }

        private void PonerTitulo(){
            tituloJuego = Label.label("Mi juego", "Verdana", 30);

            float altoTitulo;
            altoTitulo = tituloJuego.getHeight();

            tituloJuego.setPosition(pantallaDispositivo.width/2, pantallaDispositivo.height-altoTitulo/2);
        }

    }

    class CapaFrente extends Layer {

        public CapaFrente(){
            PonerNavePosInicial();

            TimerTask tareaPonerEnemigos = new TimerTask() {
                @Override
                public void run() {
                    PonerEnemigo();
                }
            };

            Timer relojEnemigos = new Timer();
            relojEnemigos.schedule(tareaPonerEnemigos, 0, 1000);
        }

        private void PonerNavePosInicial(){
            naveJugador = Sprite.sprite("rocket_mini.png");

            float posicionInicialX, posicionInicialY;
            posicionInicialX = pantallaDispositivo.width/2;
            posicionInicialY = naveJugador.getHeight()/2;
            naveJugador.setPosition(posicionInicialX,posicionInicialY);

            super.addChild(naveJugador);
        }

        void PonerEnemigo(){
            enemigo = Sprite.sprite("enemigo.gif");

            CCPoint posInicial = new CCPoint();

            float alturaEnemigo, anchoEnemigo;
            alturaEnemigo = enemigo.getHeight();
            anchoEnemigo = enemigo.getWidth();
            posInicial.y = pantallaDispositivo.height + alturaEnemigo/2;

            Random generadorAzar = new Random();
            posInicial.x = generadorAzar.nextInt((int) pantallaDispositivo.width - (int) anchoEnemigo) + anchoEnemigo/2;

            enemigo.setPosition(posInicial.x, posInicial.y);
            enemigo.runAction(RotateTo.action(0.01f, 180f));

            CCPoint posFinal = new CCPoint();
            posFinal.x = posInicial.x;
            posFinal.y = - alturaEnemigo/2;
            enemigo.runAction(MoveTo.action(3, posFinal.x, posFinal.y));

            super.addChild(enemigo);
        }

        boolean InterseccionSprites(Sprite sprite1, Sprite sprite2){
            boolean devolver = false;
            int sprite1Izq, sprite1Der, sprite1Abajo, sprite1Arriba;
            int sprite2Izq, sprite2Der, sprite2Abajo, sprite2Arriba;

            sprite1Izq = (int) (sprite1.getPositionX() - sprite1.getWidth()/2);
            sprite1Der = (int) (sprite1.getPositionX() + sprite1.getWidth()/2);
            sprite1Abajo = (int) (sprite1.getPositionY() - sprite1.getHeight()/2);
            sprite1Arriba = (int) (sprite1.getPositionY() + sprite1.getHeight()/2);

            sprite2Izq = (int) (sprite2.getPositionX() - sprite2.getWidth()/2);
            sprite2Der = (int) (sprite2.getPositionX() + sprite2.getWidth()/2);
            sprite2Abajo = (int) (sprite2.getPositionY() - sprite2.getHeight()/2);
            sprite2Arriba = (int) (sprite2.getPositionY() + sprite2.getHeight()/2);

            if (estaEntre(sprite1Izq, sprite2Izq, sprite2Der) &&
                    estaEntre(sprite1Abajo, sprite2Abajo, sprite2Arriba)){
                devolver = true;
            }

            if (estaEntre(sprite1Izq, sprite2Izq, sprite2Der) &&
                    estaEntre(sprite1Arriba, sprite2Abajo, sprite2Arriba)) {
                devolver=true;
            }

            if (estaEntre(sprite1Der, sprite2Izq, sprite2Der) &&
                    estaEntre(sprite1Arriba, sprite2Abajo, sprite2Arriba)) {
                devolver=true;
            }

            if (estaEntre(sprite1Der, sprite2Izq, sprite2Der) &&
                    estaEntre(sprite1Abajo, sprite2Abajo, sprite2Arriba)) {
                devolver=true;
            }

            if (estaEntre(sprite2Izq, sprite1Izq, sprite1Der) &&
                    estaEntre(sprite2Abajo, sprite1Abajo, sprite1Arriba)) {
                devolver=true;
            }

            if (estaEntre(sprite2Izq, sprite1Izq, sprite1Der) &&
                    estaEntre(sprite2Arriba, sprite1Abajo, sprite1Arriba)) {
                devolver=true;
            }

            if (estaEntre(sprite2Der, sprite1Izq, sprite1Der) &&
                    estaEntre(sprite2Arriba, sprite1Abajo, sprite1Arriba)) {
                devolver=true;
            }

            if (estaEntre(sprite2Der, sprite1Izq, sprite1Der) &&
                    estaEntre(sprite2Abajo, sprite1Abajo, sprite1Arriba)) {
                devolver=true;
            }

            return devolver;
        }

        boolean estaEntre (int numComparar, int numMenor, int numMayor){
            boolean devolver;

            if (numMenor > numMayor){
                int auxiliar;
                auxiliar = numMayor;
                numMayor = numMenor;
                numMenor = auxiliar;
            }

            if (numComparar >= numMenor && numComparar <= numMayor){
                devolver = true;
            } else {
                devolver = false;
            }

            return devolver;
        }

    }
}
