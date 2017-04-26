/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 *
 * @author 655
 * Линия с закрашеной головой в начале стрелки. 
 * Рисуем закрашенную часть g2d.fillPolygon(pPoints, p1Points, p2Points).
 */
public class MyLine2 extends MyLine implements CopyTo{
    
    /**Точка начала крыльев стрелки и концов двух стрелок.*/
    private MyPoint p1g,p1g1,p1g2;
    
    
   MyLine2(){
       initComponent();
   }
   private void initComponent(){
       //p=new MyPoint();
   }
   
    /**Строим линию между двумя точками. 
     Крылья линии начинаются отступив 13 поинтов от начала линии.*/
    MyLine2 (MyPoint p1, MyPoint p2){
        initComponent();
        setLine(p1,p2);
    }
   /**строим короткую линию между двумя элипсами.
    Крылья линии начинаются отступив 13 поинтов от начала линии.*/
    MyLine2(MyEll f1 , MyEll f2){
        initComponent();
        this.setLine(f1, f2);
        super.getPriznakUrlAr().add(new PriznakUrl());//установим счетчик количества линий ==1.(это первая линия отношения), правда с нулевыми параметрами
        //System.out.println(" MyLine 79     f1!=null&f2!=null");
    }
    

    
    /**Найдем точку на линии, которая удалена от указанной точки на указанное расстояние.
     * @param p1 - точка начала линии.
     * @param p2 - точка конца линии.
     * @param r - расстояние от начала линии.
     * @return - возвращаем найденную точку*/
    private MyPoint findPointP1g (MyPoint p1,MyPoint p2, int r){
        p1g=new MyPoint();
        //Просчитаем коефициент.
        double k=r/super.lengthMyLine(p1, p2);
        //Найдем координаты точки на r отдаленной от начала линии.
        p1g.x=p1.x+(p2.x-p1.x)*k;
        p1g.y=p1.y+(p2.y-p1.y)*k;
        return p1g;
    }
    
    public MyPoint getP1g(){
        return  p1g;
    }
    public MyPoint getP1g1(){
        return  p1g1;
    }
    
    public MyPoint getP1g2(){
        return  p1g2;
    }
    /**строим короткую линию между двумя элипсами*/
    @Override
    void setLine(MyEll f1 , MyEll f2){
        //p1g=new MyPoint();
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
            this.setLine(p1Line,p2Line);
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
            p1g=new MyPoint();
            p1g=findPointP1g(p1, p2, 13);
            double r=Math.atan2(p1.x-p2.x,p1.y-p2.y);//угол линии
            //находим одно крыло стрелки от точки p2.
            //было this.line1= new Line2D.Double(p.x, p.y,(p.x+13*Math.sin(r+0.5)),(p.y+13*Math.cos(r+0.5)));
            //this.line1= new Line2D.Double(p.x, p.y,p1.getPL1().getX(),p1.getPL1().getY());
            //Найдем точку конца первого крыла.
            p1g1=new MyPoint1(p1.getPL1().getX(),p1.getPL1().getY());
            //находим другое "крыло": стрелки от точки p2.
            //было this.line2= new Line2D.Double(p.x, p.y,(p.x+13*Math.sin(r-0.5)),(p.y+13*Math.cos(r-0.5)));
            //this.line2= new Line2D.Double(p.x, p.y,p1.getPL2().getX(),p1.getPL2().getY());
            //Найдем точку конца второго крыла.
            p1g2=new MyPoint1(p1.getPL2().getX(),p1.getPL2().getY());
            //this.colorLine=Color.BLACK;
        
        //this.colorLine=Color.BLACK;
    }
    /**Присвоим значение флагу: Надо ли отмечать этот эллипс цвето
     * @param color
     * 
    @Override
    public void setColorLine(Color color){
    this.colorLine=color;
    }*/
    /**Рисуенм линию объекта класса MyLine2
     * @param g2
     * @param myLine*/
    public void drawLine(Graphics2D g2,MyLine2 myLine){
        g2.setColor(myLine.getColorLine());//установим цвет для объекта
        g2.draw((Line2D)myLine);//рисуем линию
        int xpoints[] = {(int)myLine.getP1g().x, (int)myLine.getP1g1().x, (int)myLine.getP1g2().x};//Рисуем стрелочку1 на линии.
        int ypoints[] = {(int)myLine.getP1g().y, (int)myLine.getP1g1().y, (int)myLine.getP1g2().y};//Рисуем стрелочку2 на линии.
        int npoints = 3;//Рисуем стрелочку на линии.
        g2.fillPolygon(xpoints, ypoints, npoints);//Рисуем стрелочку на линии.
        //Рисуем меточку количества отношений .
        if(myLine.getPriznakUrlAr().size()!=0){//если массив меток количества отношений не пуст
            g2.setColor(Color.MAGENTA);//установим цвет для объекта
            g2.drawString(" "+myLine.getPriznakUrlAr().size(), 
                        (int)((myLine.getX1()+myLine.getX2())/2), 
                        (int)((myLine.getY1()+myLine.getY2())/2));
            //вернем цвет
            g2.setColor(myLine.getColorLine());
        }
                
    }
    
    /**Для копирования экземпляра Класса в новый объект.*/
    @Override
    public MyLine2 copyTo() {
        MyLine2 myLineNew = new MyLine2();
        myLineNew.setX1(this.getX1());
        myLineNew.setY1(super.getY1());
        myLineNew.setX2(super.getX2());
        myLineNew.setY2(super.getY2());
        myLineNew.setP1(new MyPoint (super.getP1().getX(),super.getP1().getY()));
        myLineNew.setP2(new MyPoint (super.getP2().getX(),super.getP2().getY()));
        myLineNew.setLinelength();
        double r=Math.atan2(this.getX1()- super.getX2(),super.getY1()- super.getY2());//угол линии
        //находим одно крыло стрелки
        myLineNew.setLine1( new Line2D.Double(super.getX2(), super.getY2(),(super.getX2()+13*Math.sin(r+0.5)),(super.getY2()+13*Math.cos(r+0.5))));
        //находим другое "крыло": стрелки
        myLineNew.setLine2(new Line2D.Double(super.getX2(), super.getY2(),(super.getX2()+13*Math.sin(r-0.5)),(super.getY2()+13*Math.cos(r-0.5))));
        
        myLineNew.setText1(this.getText1());
        myLineNew.setText2(this.getText2());
        myLineNew.setTx1(this.getTx1());
        myLineNew.setTy1(this.getTy1());
        myLineNew.setTx2(this.getTx2());
        myLineNew.setTy2(this.getTy2());
        myLineNew.colorLine=colorLine;
        if(this.getPriznakUrlAr()!=null&&this.getPriznakUrlAr().size()>0){ //если в массиве есть объекты
            for(PriznakUrl priznakUrlOld:this.getPriznakUrlAr()){
                PriznakUrl priznakUrlNew = priznakUrlOld.copyTo();
                myLineNew.getPriznakUrlAr().add(priznakUrlNew);
            }
        }
        
        
        return myLineNew;
    }
}