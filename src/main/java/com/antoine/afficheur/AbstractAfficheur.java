package com.antoine.afficheur;

import com.antoine.contracts.IStructure;

import java.awt.*;

public abstract class AbstractAfficheur {

    protected Graphics g;

    abstract void visit(IStructure structure);

    public void setGraphics(Graphics g){
        this.g= g;
    }

    public void freeGraphics(){
        this.g= null;
    }
}
