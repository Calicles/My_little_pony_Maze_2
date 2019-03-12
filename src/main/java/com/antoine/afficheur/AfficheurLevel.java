package com.antoine.afficheur;

import com.antoine.contracts.IStructure;
import com.antoine.contracts.IVisiteur;

import java.awt.Graphics;

public class AfficheurLevel extends AbstractAfficheur implements IVisiteur {


    public AfficheurLevel(Graphics g){
        super(g);
    }


    @Override
    public void visit(IStructure structure) {

    }
}
