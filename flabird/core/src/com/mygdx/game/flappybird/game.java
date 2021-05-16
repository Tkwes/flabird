package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class game extends ApplicationAdapter {

    private float variacao = 0;
    private float posicaoInicialVerticalPassaro = 0;
    private float posicaoCanoVertical;
    private float posicaoCanoHorizontal;
    private float espacoEntreCanos;

    private SpriteBatch batch;
    private Random random;

    BitmapFont textoPontuacao;

    private boolean passouCano = false;
    private Rectangle canodecima;
    private Rectangle canodebaixo;
    private ShapeRenderer spriterender;
    private Circle areadopassaro;


    //variavel que define o tamanho de tela de cada dispositivo
    private float alturaDispositivo;
    private float larguraDispositivo;

    private Texture fundo ;//texturadofundo


    private Texture[] passaros; //sprite para animacao de voo
    private Texture canoBaixo; //sprite cano de baixo
    private Texture canoTopo;//sprite cano de cima


    private int gravidade = 0; //variavies influencia do mundo
    private int pontos = 0; // pontuaçao dos players


    @Override
    public void create() {

        inicializaTexturas();
        inicializaObjetos();
    }

    @Override
    public void render() {

        verificarEstadoJogo();
        validarPontos();
        desenharTexturas();
        detectarColisao();
    }

    private void detectarColisao() {

        areadopassaro.set(50 + passaros[0].getWidth() / 2, posicaoInicialVerticalPassaro + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);

        canodecima.set(posicaoCanoHorizontal, alturaDispositivo / 2 - canoTopo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical, canoTopo.getWidth(), canoTopo.getHeight());
        canodebaixo.set(posicaoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical, canoBaixo.getWidth(), canoBaixo.getHeight());
        boolean colisaoCanoCima = Intersector.overlaps(areadopassaro, canodecima);
        boolean colisaoCanobaixo = Intersector.overlaps(areadopassaro, canodebaixo);

    }

    private void desenharTexturas() {

        batch.begin();
        //buscando variavel de tela para criar backgraud
        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);

        //instanciando passaro na tela
        batch.draw(passaros[(int) variacao], 50, posicaoInicialVerticalPassaro);

        //instanciando canos no mundo
        batch.draw(canoBaixo, posicaoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical);
        batch.draw(canoTopo, posicaoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical);
        textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo / 2, alturaDispositivo - 100);
        batch.end();
    }

    private void validarPontos() {

        //teste se passaro passou pelo cano
        if(posicaoCanoHorizontal < 50 - passaros[0].getWidth()){
            if(!passouCano){
                pontos++;
                passouCano = true;
            }
        }
    }

    private void verificarEstadoJogo() {

        posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;
        if(posicaoCanoHorizontal <- canoBaixo.getWidth()){
            posicaoCanoHorizontal = larguraDispositivo;
            posicaoCanoVertical = random.nextInt(400) - 200;
            passouCano = false;
        }

        //test de toque do ogador
        boolean toqueTela = Gdx.input.justTouched();

        //se toch passaro voa
        if(Gdx.input.justTouched()){
            gravidade = -25;
        }

        if(posicaoInicialVerticalPassaro > 0 || toqueTela)
            posicaoInicialVerticalPassaro -= gravidade;

        //animando passaro no voo
        variacao += Gdx.graphics.getDeltaTime() * 10;

        //loop de animaçao
        if(variacao > 3)
            variacao = 0;

        //intensificando gravidade
        gravidade++;
    }

    private void inicializaObjetos() {

        batch = new SpriteBatch();
        random = new Random();


        //incluindo na variavel o tamanho de tela
        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();

        //definindo posicao inicial da tela
        posicaoInicialVerticalPassaro = alturaDispositivo / 2;
        posicaoCanoHorizontal = larguraDispositivo;
        espacoEntreCanos = 350;

        textoPontuacao = new BitmapFont();
        textoPontuacao.setColor(Color.WHITE);
        textoPontuacao.getData().setScale(10);
    }

    private void inicializaTexturas() {

        passaros = new Texture[3];

        //buscando as imagens para animacao
        passaros[0] = new Texture("passaro1.png");
        passaros[1] = new Texture("passaro2.png");
        passaros[2] = new Texture("passaro3.png");

        //buscando imagem de backgraund e canos de colisao 
        fundo = new Texture("fundo.png");
        canoBaixo = new Texture("cano_baixo_maior.png");
        canoTopo = new Texture("cano_topo_maior.png");
    }
}
