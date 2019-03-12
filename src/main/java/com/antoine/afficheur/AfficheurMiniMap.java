package com.antoine.afficheur;

import com.antoine.contracts.IStructure;
import com.antoine.contracts.IVisiteur;

import java.awt.*;

public class AfficheurMiniMap extends AbstractAfficheur implements IVisiteur{


    public AfficheurMiniMap(Graphics g){
        super(g);
    }


    @Override
    public void visit(IStructure structure) {

    }
}
