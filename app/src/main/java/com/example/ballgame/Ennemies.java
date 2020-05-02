package com.example.ballgame;

import android.app.Application;
import android.graphics.Color;
import android.provider.Settings;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class Ennemies {

    //id, x, y de l'ennemy
    int id ;
    float x;
    float y;
    //variance entre le point et la destination
    float varX;
    float varY;
    //middle of layout
    float middleX;
    float middleY;

    //position entre 1 et 4
    int position;
    //Vue de l'ennemie
    TextView viewEnnemy;

    public Ennemies(TextView viewEnnemy, float middleX, float middleY) {

        this.middleX = middleX;
        this.middleY = middleY;


        Random Rside = new Random();
        //Random entre 0 et 4, 1 = HG, 2 = HD, 3 = BG, 4 = BD
        this.position = Rside.nextInt(4)+1;


        if(this.position == 1){
            Random r = new Random();
            this.x = r.nextInt(540-250);
            this.y = r.nextInt( 745-250);
        } else if(this.position == 2){
            Random r = new Random();
            this.x = r.nextInt(540+250)+540;
            this.y = r.nextInt( 745-250);
        } else if(this.position == 3){
            Random r = new Random();
            this.x = r.nextInt(540-250);
            this.y = r.nextInt( 745+250)+745;
        } else if(this.position == 4){
            Random r = new Random();
            this.x = r.nextInt(540+250)+540;
            this.y = r.nextInt( 745+250)+745;
        }



        //Determine la variance selon la position
        if(this.x < middleX && this.y < middleY){
            this.varX = middleX - this.x;
            this.varY = middleY - this.y;
        } else if (this.x > middleX && this.y > middleY){
            this.varX = this.x - middleX;
            this.varY = this.y - middleY;
        } else if(this.x < middleX && this.y > middleY){
            this.varX = this.x + middleX;
            this.varY = this.y - middleY;
        } else if (this.x > middleX && this.y < middleY){
            this.varX = this.x - middleX;
            this.varY = this.y + middleY;
        }



        //System.out.println("varx = " + this.varX + " vary = " +this.varY);

        this.viewEnnemy = viewEnnemy;
    }
    //retourn un bool, si false => l'ennemi est detruit
    public boolean move() {


        if( this.x < middleX && this.y < middleY ){

            if(this.x <=middleX-varX/50){
                this.x = this.x + (varX/50);
                this.viewEnnemy.setX(this.x);

            }
            if( this.y <= middleY - varY/50 ){
                this.y = this.y + (varY/50);
                this.viewEnnemy.setY(this.y);

            }

        } else if( this.x > middleX  && this.y > middleY  ) {

            if( this.x >= middleX + varX/50 ){
                this.x = this.x - (varX/50);
                this.viewEnnemy.setX(this.x);

            }
            if(this.y >= middleY + varY/50){
                this.y = this.y - (varY/50);
                this.viewEnnemy.setY(this.y);

            }


        } else if ( this.x < middleX && this.y > middleY ){

            if( this.x <= middleX - varX/50){
                this.x = this.x + (varX/50);
                this.viewEnnemy.setX(this.x);

            }
            if(this.y >= middleY +varY/50){
                this.y = this.y - (varY/50);
                this.viewEnnemy.setY(this.y);

            }

        } else if ( this.x > middleX && this.y < middleY){

            if( this.x >= middleX+varX/50 ){
                this.x = this.x - (varX/50);
                this.viewEnnemy.setX(this.x);

            }
            if( this.y <= middleY -varY/50 ){
                this.y = this.y + (varY/50);
                this.viewEnnemy.setY(this.y);

            }

        }  if( ((this.x <= middleX+125 && this.x > middleX-125) || (this.x >= middleX-125 && this.x < middleX+125) ) && ( (this.y <= middleY+125 && this.y > middleY-125)  || (this.y >= middleY-125 && this.y < middleY+125)   )){
            return false;
        }

       return true;
    }

    public boolean testCollision(){
        if( ((this.x <= middleX+205 && this.x > middleX-230) || (this.x >= middleX-230 && this.x < middleX+205) ) && ( (this.y <= middleY+200 && this.y > middleY-210)  || (this.y >= middleY-205 && this.y < middleY+200)   )){


            return true;
        }
        return false;
    }

}
