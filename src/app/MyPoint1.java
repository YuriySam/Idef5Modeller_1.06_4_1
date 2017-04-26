/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author 655
 */
public class MyPoint1 extends MyPoint{
    /**Две дополнительных точки, которые создаются рядом с основной,
     слева и справа от нее по линии эллипса. 
     * Эти точки создаются при создания эллипса.
     * Для отрисовки крыльев MyLine1.*/
    private MyPoint pL1;
    /**Две дополнительных точки, которые создаются рядом с основной,
     слева и справа от нее по линии эллипса. 
     * Эти точки создаются при создания эллипса.
     * Для отрисовки крыльев MyLine1.*/
    private MyPoint  pL2;
    private static final long serialVersionUID = 12345678L; // Change number as appropriate

    MyPoint1(double x, double y) {
        
        this.x=x;
        this.y=y;
        this.pL1=new MyPoint();
        this.pL2=new MyPoint();
    }
    // конструктор, создающий указанную точку
    MyPoint1(MyPoint1 p){
        this.x=p.getX();
        this.y=p.getY();
        this.pL1=new MyPoint();
        this.pL2=new MyPoint();
    }
    /**Возвращаем первую точку крыла линии.
     * @return  */
    public MyPoint getPL1(){
        return this.pL1;
    }
    /**Возвращаем вторую точку крыла линии.
     * @return  */
    public MyPoint getPL2(){
        return this.pL2;
    }
    /**Присваиваем значение первой точке крыла линии.
     * @param point */
    public void setPL1(MyPoint point){
        this.pL1=point;
    } 
    /**Присваиваем значение первой точке крыла линии.
     * @param x
     * @param y */
    public void setPL1(double x, double y){
        this.pL1.setLocation(x, y);
    }
    /**Присваиваем значение второй точке крыла линии.
     * @param point */
    public void setPL2(MyPoint point){
        this.pL2=point;
    } 
    /**Присваиваем значение второй точке крыла линии.
     * @param x
     * @param y */
    public void setPL2(double x, double y){
        this.pL2.setLocation(x, y);
    }
    
}
