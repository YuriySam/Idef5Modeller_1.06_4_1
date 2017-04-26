/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author 5521
 */
/**Класс, который описывает элемент, видимый справа вверху каждого эллипса. 
 * Служит для того, чтоб развернуть или свернуть дерево, вершиной которого есть этот эллипс.
 В ненажатом состоянии на кнопке изображен знак "+", 
 а в нажатом состоянии  - знак "-".
 При клике на "-" элементы дерева скрываются с экрана.
 При клике на "+" элементы дерева отображаются на экране.*/
public final class Detail extends Rectangle2D implements CopyTo {
    //private static final long serialVersionUID = 112223348444455678L; // Change number as appropriate
    private double x,y, w,h;
    private boolean open;
    private Color colorRect;
    private Color colorCross;
    private Color colorDefault;
    boolean makeCrossColor;
    
        
    Detail(double x, double y, double w, double h, boolean open ) {
        setRect(x,y,w,h);
        this.open = open;
        colorRect=Color.GRAY;
        colorCross=Color.black;
        colorDefault=Color.black;
        makeCrossColor=false;
        }

      
    /**Рисуенм линию объекта класса MyEll
     * @param g2*/
    public void drawDetail(Graphics2D g2){
        if(makeCrossColor){
            colorCross=Color.red;
        }else{
            colorCross=colorDefault;
        }
        g2.setColor(Color.WHITE);
        g2.fill(this);
        //установим цвет для объекта
        g2.setColor(this.colorDefault);
        if(open){//рисуем картинку поля Свернуть/Развернуть, отступая от начала эллипса
            g2.setColor(colorCross);
            g2.drawLine((int)(x+2), (int)(y+h/2), (int)(x-2+w), (int)(y+h/2));//рисуем горизонтальную линию
            g2.setColor(colorDefault);
        }else{//рисуем крестик для свернутого дерева
            g2.setColor(colorCross);
            g2.drawLine((int)(x+2), (int)(y+h/2), (int)(x-2+w), (int)(y+h/2));//рисуем горизонтальную линию
            g2.drawLine((int)(x+w/2), (int)(y+2), (int)(x+w/2), (int)(y-1+h));//рисуем вертикальную линию
            g2.setColor(colorDefault);
        }
        g2.setColor(colorRect);
        
        g2.drawOval((int)x, (int)y, (int)w, (int)h);
        //.draw(this);//рисуем квадратик
        g2.setColor(colorDefault);
        
    }
    
    @Override
     public void setRect(double x, double y, double w, double h) {
        this.x=x+w-7;//прилепим  его к правому верхнему углу эллипса
        this.y=y;//прилепим  его к правому верхнему углу эллипса
        //this.x=x+w-10;//прилепим  его к серединке правого края эллипса
        //this.y=y*h/2;//прилепим  его к серединке правого края эллипса
        this.w=10;
        this.h=10;
        
    }

    @Override
    public int outcode(double d, double d1) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return 0;
    }

    @Override
    public Rectangle2D createIntersection(Rectangle2D rd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D createUnion(Rectangle2D rd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        if(this.isEmpty()){return true;}
        return false;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Color getColorRect() {
        return colorRect;
    }

    public void setColorRect(Color colorRect) {
        this.colorRect = colorRect;
    }

    public Color getColorCross() {
        return colorCross;
    }

    public void setColorCross(Color colorCross) {
        this.colorCross = colorCross;
    }

    public boolean isMakeCrossColor() {
        return makeCrossColor;
    }

    public void setMakeCrossColor(boolean makeCrossColor) {
        this.makeCrossColor = makeCrossColor;
    }
    /**Для копирования экземпляра Класса в новый объект.*/
    @Override
    public Detail copyTo() {
        //создадим новый объект
        //скопируем в него поля выделенного объекта
        Detail detailNew= new Detail(x,y,w,h,open);
        detailNew.colorRect=colorRect;
        detailNew.colorCross=colorCross;
        detailNew.colorDefault=colorDefault;
        detailNew.makeCrossColor=makeCrossColor;
        return detailNew;
    }
        
        
        
        
        
    }

    
