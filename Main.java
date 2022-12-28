package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	    JFrame obj= new JFrame();
        Gameplay game= new Gameplay();
        obj.setBounds(10,10,700,600);
        obj.setTitle("oussama Game");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);
    }
}
