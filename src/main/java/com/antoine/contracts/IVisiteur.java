package com.antoine.contracts;

import java.awt.*;

public interface IVisiteur {

    void visit(IStructure structure);

    void setGraphics(Graphics g);

    void freeGraphics();
}
