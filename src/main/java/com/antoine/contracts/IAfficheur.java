package com.antoine.contracts;

import java.awt.*;

public interface IAfficheur {

    void visit(IStructure structure);

    void setGraphics(Graphics g);

    void freeGraphics();
}
