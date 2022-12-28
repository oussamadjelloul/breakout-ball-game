package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false ;
    private int score =0;
    private int totallbricks=21;
    private Timer time;
    private int delay =8;
    private int playerX =310;
    private int ballposX=350;
    private int ballposY=350;
    private int ballXdir=-1;
    private int ballYdir=-2;
    private mapGenerate map;

    public Gameplay(){
        map = new mapGenerate(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time= new Timer(delay,this);
        time.start();
    }
    public void paint(Graphics ge){
        //background
        ge.setColor(Color.black);
        ge.fillRect(1,1,692,592);
        //drawing map
        map.draw((Graphics2D) ge);
        //score
        ge.setColor(Color.white);
        ge.setFont(new Font("serif",Font.BOLD,26));
        ge.drawString(""+score,590,30);
        // borders
        ge.setColor(Color.yellow);
        ge.fillRect(0,0,3,592);
        ge.fillRect(0,0,692,3);
        ge.fillRect(692,0,3,592);
        // the paddle
        ge.setColor(Color.green);
        ge.fillRect(playerX,550,100,8);
        // balle
        ge.setColor(Color.yellow);
        ge.fillOval(ballposX,ballposY,20,20);
        if(totallbricks<=0){
            play=false;
            ballXdir=0;
            ballYdir=0;
            ge.setColor(Color.ORANGE);
            ge.setFont(new Font("serif",Font.BOLD,26));
            ge.drawString("You ARE WONNER ,Score:"+score,115,300);
            ge.setFont(new Font("serif",Font.BOLD,18));
            ge.drawString("Press entre for restart the game ",125,350);
        }
        if(ballposY>570){
            play=false;
            ballXdir=0;
            ballYdir=0;
            ge.setColor(Color.ORANGE);
            ge.setFont(new Font("serif",Font.BOLD,26));
            ge.drawString("You LOSING THE GAME ,Score:"+score,115,300);

            ge.setFont(new Font("serif",Font.BOLD,18));
            ge.drawString("Press entre for restart the game ",125,350);

        }
        ge.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if( e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        else if( e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX<10){
                playerX=10;
            }
            else {
                moveLeft();
            }
        }
        else if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                ballposX=350;
                ballposY=350;
                ballXdir=-1;
                ballYdir=-2;
                playerX=310;
                score=0;
                totallbricks=21;
                map = new mapGenerate(3,7);
                repaint();
            }
        }

    }

    public void moveRight(){
        play= true;
        playerX+=20;

    }
    public void moveLeft(){
        play= true;
        playerX-=20;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }
           A: for (int i = 0; i < map.map.length; i++) {
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX=j*map.brickwidth+80;
                        int brickY=i*map.brickhight+50;
                        int brickwidth =map.brickwidth;
                        int brickhight =map.brickhight;
                        Rectangle rect = new Rectangle(brickX,brickY,brickwidth,brickhight);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect =  rect ;
                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totallbricks--;
                            score+=5;
                            if((ballposX + 19 <= brickRect.x )|| (ballposX + i >= brickRect.x + brickRect.width )){
                                ballXdir =-ballXdir;
                            }
                            else{
                                ballYdir =-ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
        }

        ballposX += ballXdir;
        ballposY += ballYdir;
            if (ballposX < 0) {
                ballXdir = -ballXdir;
             }
        if (ballposY < 0) {
                 ballYdir = -ballYdir;
             }
        if (ballposX > 670) {
                ballXdir = -ballXdir;
             }

        repaint();
    }



}
