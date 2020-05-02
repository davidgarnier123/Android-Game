package com.example.ballgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //player circle
    TextView player;
    //middle
    TextView middle;
    //player circle active
    boolean playerActive = true;
    //layout player and ennemies
    ConstraintLayout layoutPlayer;
    ConstraintLayout layoutEnnemies;
    //Size of layoutPlayer
    int viewWidth;
    int viewHeight;
    //Middle coordonates
    float middleX =0;
    float middleY =0;
    //score var
    int score = 0;
    //Score view -> textview
    TextView scoreView;

    // Nombre d'ennemies en cours
    int nbrEnnemies = 0;
    // Nombre d'ennemies voulu
    int nbrEnnemiesApop = 3;

    List<Ennemies> ennemiesList = new ArrayList();
    List<Integer> toDeleteEnemmy = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Récuperation des layouts
        this.layoutPlayer = (ConstraintLayout) findViewById(R.id.layoutPlayer);
        this.layoutEnnemies = (ConstraintLayout) findViewById(R.id.layoutEnnemies);

        //Récupération du cercle
        this.player = (TextView) findViewById(R.id.txtCircle);
        //Récupération de la vue du score
        this.scoreView = (TextView) findViewById(R.id.scoreView);

        //Recupere les coordonées du milieu de l'ecran

        if(getMiddle()){
            //addListener and start Thread to detect active circle
            initGame();
            //Start thread for loop game
            loopGame();
        }


    }

    //Methode qui va initailiser le jeu
    private void initGame(){

        //Timer pour mettre à jour le score dans le Thread principal
        new CountDownTimer(3000000, 100) {
            public void onTick(long millisUntilFinished) {
                scoreView.setText(Integer.toString(score));
                deleteEnnemies();
                if(score > 1000){
                    nbrEnnemiesApop = 4;
                }
                loopGame();

                for (Ennemies en : ennemiesList){

                    if(playerActive && en.testCollision()){
                        player.setBackgroundResource(R.drawable.circlehit);
                    }
                    if(!en.move()){


                        try{
                            layoutEnnemies.removeView(en.viewEnnemy);

                            toDeleteEnemmy.add(ennemiesList.indexOf(en));

                        }catch (Exception e){
                            System.out.println("Bug suppression ennemi");

                        }

                    }
                }

            }

            public void onFinish() {
                scoreView.setText(Integer.toString(score));
            }
        }.start();



        //Initalisation du score
        this.scoreView.setText(Integer.toString(score));

        this.layoutPlayer.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                playerActive = false;

                return false;
            }
        });

        this.layoutPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                playerActive = true;
            }
        });

        //creation d'un thread pour gérer les events/checker si active
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(100);

                        if (playerActive){
                            player.getBackground().setAlpha(255);
                            score ++;
                        } else {
                            player.getBackground().setAlpha(100);
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    private void loopGame(){


        //Check le nobmre d'ennemies
        if(this.layoutEnnemies.getChildCount() < nbrEnnemiesApop){

            player.setBackgroundResource(R.drawable.circle);
            TextView enView = new TextView(this.layoutEnnemies.getContext());

            Ennemies en = new Ennemies(enView, middleX, middleY);
            player.setAlpha(1);
            ennemiesList.add(en);

            Random enR = new Random();
            int type = enR.nextInt(3)+1;
            if(type == 1){
                en.viewEnnemy.setBackgroundResource(R.drawable.ennemy);
            } if (type == 2 ){
                en.viewEnnemy.setBackgroundResource(R.drawable.ennemy2);
            } if (type == 3){
                en.viewEnnemy.setBackgroundResource(R.drawable.ennemy3);
            }

            en.viewEnnemy.setY(en.y);
            en.viewEnnemy.setX(en.x);

            this.layoutEnnemies.addView(enView);
            this.nbrEnnemies++;


        } else {





        }


    }

    private void deleteEnnemies(){

        //supprimer l'ennemi de la liste
        for (Integer enToDelete : toDeleteEnemmy ){

            try{
                ennemiesList.remove(ennemiesList.get(enToDelete));
            } catch (Exception e){

            }


        }
        toDeleteEnemmy.clear();
    }
    private boolean getMiddle(){
        //Initialise les coordonées du milieu du Layout


            //Récupération taille de l'ecran
            ViewTreeObserver viewTreeObserver = layoutEnnemies.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        layoutEnnemies.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        viewWidth = layoutEnnemies.getWidth();
                        viewHeight = layoutEnnemies.getHeight();

                        middleX = (viewWidth/2)-10;
                        middleY = (viewHeight/2)-10;

                         middle = new TextView(layoutPlayer.getContext());
                        middle.setY(middleY);
                        middle.setX(middleX);
                        middle.setWidth(5);
                        middle.setHeight(5);
                        middle.setBackgroundColor(Color.RED);
                        layoutPlayer.addView(middle);

                    }
                });
            }
return true;

        }


}
