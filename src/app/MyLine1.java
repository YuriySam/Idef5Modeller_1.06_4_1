/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
Класс для объекта линия со стрелкой. Рисуем другой вариант стредки.

----<--

 */
package app;

import java.awt.Color;
import java.awt.geom.Line2D;

/**
 *
 * @author 655
 * Стрелка ----<--.
 * Описывает соединительную линию между графическими фигурами.
 * Графические фигуры имеют зарезервированные точки соединения с линиями. 
 * Эти точки собраны в массив.
 * Для каждого вида фигур точки разные.
 * Привязку начала и конца линии делаем по тексту начальной и конечной фигур.
 * Соединительная линия(стрелка) формирует крылья не в конечной точке линии, а на длину крыла отступив от начала линии.
 */
public class MyLine1 extends MyLine{
    
    /**Точка начала крыльев стрелки*/
    private MyPoint p;
    /**Cтрелочки линии.*/
    private Line2D line1, line2;
    
   MyLine1(){
       initComponent();
   }
   private void initComponent(){
       //p=new MyPoint();
   }
   
    /**Строим линию между двумя точками. 
     Крылья линии начинаются отступив 13 поинтов от начала линии.*/
    MyLine1 (MyPoint p1, MyPoint p2){
        setLine(p1,p2);
    }
   /**строим короткую линию между двумя элипсами.
    Крылья линии начинаются отступив 13 поинтов от начала линии.*/
    MyLine1(MyEll f1 , MyEll f2){
        this.setLine(f1, f2);
        //System.out.println(" MyLine 79     f1!=null&f2!=null");
    }
    /**Строим линию между двумя точками.
     * @param p1
     * @param p2 
    @Override
    public void setLine(MyPoint p1, MyPoint p2){
        super.setP1(p1);
        super.setP2(p2);
        super.setLinelength();
        //Крылья линии начинаются отступив 13 поинтов от начала линии.
        p=new MyPoint();
        p=findPointP(p1, p2, 13);
        double r=Math.atan2(p1.x-p2.x,p1.y-p2.y);//угол линии
        //находим одно крыло стрелки от точки p2.
        this.line1= new Line2D.Double(p.x, p.y,(p.x+13*Math.sin(r+0.5)),(p.y+13*Math.cos(r+0.5)));
        //находим другое "крыло": стрелки от точки p2.
        this.line2= new Line2D.Double(p.x, p.y,(p.x+13*Math.sin(r-0.5)),(p.y+13*Math.cos(r-0.5)));
        this.colorLine=Color.BLACK;
    }
    */ 
    

    
    /**Найдем точку на линии, которая удалена от указанной точки на указанное расстояние.
     * @param p1 - точка начала линии.
     * @param p2 - точка конца линии.
     * @param r - расстояние от начала линии.
     * @return - возвращаем найденную точку*/
    private MyPoint findPointP (MyPoint p1,MyPoint p2, int r){
        p=new MyPoint();
        //Просчитаем коефициент.
        double k=r/super.lengthMyLine(p1, p2);
        //Найдем координаты точки на r отдаленной от начала линии.
        p.x=p1.x+(p2.x-p1.x)*k;
        p.y=p1.y+(p2.y-p1.y)*k;
        return p;
    }
    
    @Override
    public Line2D getLine1(){
        return  line1;
    }
    @Override
    public Line2D getLine2(){
        return  line2;
    }
    /**строим короткую линию между двумя элипсами*/
    @Override
    void setLine(MyEll f1 , MyEll f2){
        p=new MyPoint();
        if(f1!=null&f2!=null) {
            //назначаем эталонные точки соединения объектов
            MyPoint1 p1Line=(MyPoint1)f1.getP0();
            MyPoint1 p2Line=(MyPoint1)f2.getP0();
            //перебираем все точки первого эллипса
            for (int i=0; i<f1.getMyEllPoints().size();i++) {
                MyPoint1 pi=f1.getMyEllPoints().get(i);
                //перебираем все точки второго эллипса
                for(int j=0;j<f2.getMyEllPoints().size();j++) {
                    MyPoint1 pj=f2.getMyEllPoints().get(j);
                    //сравниваем расстояние с этолоном, если эталон больше - меняем эталон
                    if (p1Line.length(p2Line) >= pi.length(pj)){
                        p1Line=pi;
                        p2Line=pj;
                    } 
                 }
            }
            setLine(p1Line,p2Line);
            super.setText1(f1.getText());
            super.setText2(f2.getText());
            super.setTx1(f1.getX());
            super.setTx2(f2.getX());
            super.setTy1(f1.getY());
            super.setTy2(f2.getY());
                
        }
        //System.out.println(" MyLine 79     f1!=null&f2!=null");
    }
    /**Строим линию между двумя точками.
     * @param p1
     * @param p2 */
    public void setLine(MyPoint1 p1, MyPoint1 p2){
        super.setP1(p1);
        super.setP2(p2);
        super.setLinelength();
        //System.out.println(" MyLine  135 setLine(double x1, double y1, double x2, double y2) " );
    //Крылья линии начинаются отступив 13 поинтов от начала линии.
            p=new MyPoint();
            p=findPointP(p1, p2, 13);
            double r=Math.atan2(p1.x-p2.x,p1.y-p2.y);//угол линии
            //находим одно крыло стрелки от точки p2.
            //было this.line1= new Line2D.Double(p.x, p.y,(p.x+13*Math.sin(r+0.5)),(p.y+13*Math.cos(r+0.5)));
            this.line1= new Line2D.Double(p.x, p.y,p1.getPL1().getX(),p1.getPL1().getY());
            //находим другое "крыло": стрелки от точки p2.
            //было this.line2= new Line2D.Double(p.x, p.y,(p.x+13*Math.sin(r-0.5)),(p.y+13*Math.cos(r-0.5)));
            this.line2= new Line2D.Double(p.x, p.y,p1.getPL2().getX(),p1.getPL2().getY());
            //this.colorLine=Color.BLACK;
        
        this.colorLine=Color.BLACK;
    }
}
