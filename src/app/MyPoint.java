/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author Admin
 * Объект точка.
 */
public class  MyPoint  extends Point2D{
    /**координаты точки*/
    double x,y;
    
    // конструктор по умолчанию, создающий объект 
    MyPoint() {
        
    }
   // конструктор, создающий точку с указанными координатами
    MyPoint(double x,double y){
        this.x=x;
        this.y=y;
    }
   // конструктор, создающий указанную точку
    MyPoint(Point2D p){
        this.x=p.getX();
        this.y=p.getY();
    }
    // возвращает строку с описанием точки
    @Override
    public String toString() {
        return "("+x+";"+y+")";
    }
    // выводит на экран описание точки
    public void printPoint() {
        System.out.println(this.toString());
    } 
    @Override
    // метод вычисляющий расстояние между точками
    public  double distance (double x,double y){
    double dx=this.x-x;
    double dy=this.y-y;
    return Math.sqrt(dx*dy+dy*dy);
}
    // метод вычисляющий расстояние между точками
    public double length(MyPoint p) {
        return Math.sqrt( Math.pow(p.x-x,2) + Math.pow(p.y-y,2) );
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
    // метод изменяет координаты точки на указанные
    public void setLocation(double x, double y) {
        this.x=x;
        this.y=y;
    }
    // метод изменяет координаты точки на указанные
    public void setPoint(double a, double b) {
        x = a;
        y = b;
    } 

    // метод проверяющий совпадают ли точки
    public boolean equalsPoint(MyPoint p) {
        if(this.x == p.x && this.y == p.y) {
            return true;
        } else {
            return false;
        }
    }  
    /**Увеличим значения координат точки на числ
     * @param p
     * @param move */
    public void movePoint(MyPoint p,int move){
        this.x=p.x+move;
        this.y=p.y+move;
        
    }
    
}
