/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author Admin
 */
/**к фигуре квадрат добавлено текстовое поле private String text*/
public class MyRectangle extends Rectangle2D {
    private double x,y, w,h;
    private String text;

    MyRectangle(double x, double y, double w, double h, String text) {
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.text=text;
    }

public void setWidht(double w){
this.w=w;
}

    
    public void setRect(double x, double y, double w, double h, String text) {
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.text=text;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text=text;
    }
    @Override
    public void setRect(double x, double y, double w, double h) {
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        
    }

    @Override
    public int outcode(double x, double y) {
        return outcode(getX(), getY());
    }

    @Override
    public Rectangle2D createIntersection(Rectangle2D r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Rectangle2D createUnion(Rectangle2D r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return w;
    }

    @Override
    public double getHeight() {
        return h;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
    
}
