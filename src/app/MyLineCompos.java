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
 * ------=>
 * Линия с закрашеной головой в начале стрелки. 
 * Переделать на:
 * Линию с закрашенной головой в начале стрелки. 23-07-2016
 * Рисуем закрашенную часть g2d.fillPolygon(pPoints, p1Points, p2Points).
 */
public class MyLineCompos extends MyLine{
    
    /**Точка начала крыльев стрелки и концов двух стрелок.*/
    private MyPoint pl,pl1,pl2;
    
    
   MyLineCompos(){
       initComponent();
   }
   private void initComponent(){
       //p=new MyPoint();
   }
   
    /**Строим линию между двумя точками. 
     Крылья линии начинаются отступив 13 поинтов от начала линии.*/
    MyLineCompos (MyPoint p1, MyPoint p2){
        setLine(p1,p2);
    }
   /**строим короткую линию между двумя элипсами.
    Крылья линии начинаются отступив 13 поинтов от начала линии.*/
    MyLineCompos(MyEll f1 , MyEll f2){
        this.setLine(f1, f2);
        super.getPriznakUrlAr().add(new PriznakUrl());//установим счетчик количества линий ==1.(это первая линия отношения), правда с нулевыми параметрами
        //System.out.println(" MyLine 79     f1!=null&f2!=null");
    }
    
    

    
    /**Найдем точку на линии, которая удалена от указанной точки на указанное расстояние.
     * @param p1 - точка начала линии.
     * @param p2 - точка конца линии.
     * @param r - расстояние от начала линии.
     * @return - возвращаем найденную точку*/
    private MyPoint findPointPl (MyPoint p1,MyPoint p2, int r){
        pl=new MyPoint();
        //Просчитаем коефициент.
        double k=r/super.lengthMyLine(p1, p2);
        //Найдем координаты точки на r отдаленной от начала линии.
        pl.x=p1.x+(p2.x-p1.x)*k;
        pl.y=p1.y+(p2.y-p1.y)*k;
        return pl;
    }
    
    public MyPoint getPl(){
        return  pl;
    }
    public MyPoint getPl1(){
        return  pl1;
    }
    
    public MyPoint getPl2(){
        return  pl2;
    }
    /**строим короткую линию между двумя элипсами*/
    @Override
    void setLine(MyEll f1 , MyEll f2){
        //pl=new MyPoint();
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
    //Крылья линии начинаются в конце линии pl.
            pl=new MyPoint();
            pl=p2;
            double r=Math.atan2(p1.x-p2.x,p1.y-p2.y);//угол линии
            //находим одно крыло стрелки от точки p2.
            //this.line1= new Line2D.Double(p.x, p.y,(p.x+13*Math.sin(r+0.5)),(p.y+13*Math.cos(r+0.5)));
            //this.line1= new Line2D.Double(p.x, p.y,p1.getPL1().getX(),p1.getPL1().getY());
            //Найдем точку конца первого крыла.
            pl1=new MyPoint(pl.x+13*Math.sin(r+0.5),pl.y+13*Math.cos(r+0.5) );
            //pl1=new MyPoint1(p1.getPL1().getX(),p1.getPL1().getY());
            //находим другое "крыло": стрелки от точки p2.
            //было this.line2= new Line2D.Double(p.x, p.y,(p.x+13*Math.sin(r-0.5)),(p.y+13*Math.cos(r-0.5)));
            //this.line2= new Line2D.Double(p.x, p.y,p1.getPL2().getX(),p1.getPL2().getY());
            //Найдем точку конца второго крыла.
            pl2= new MyPoint(pl.x+13*Math.sin(r-0.5),pl.y+13*Math.cos(r-0.5));
            //pl2=new MyPoint1(p1.getPL2().getX(),p1.getPL2().getY());
            //this.colorLine=Color.BLACK;
        
        this.colorLine=Color.BLACK;
    }
    /**Рисуенм линию объекта класса MyLineCompos
     * @param g2
     * @param myLine*/
    public void drawLine(Graphics2D g2,MyLineCompos myLine){
        g2.setColor(myLine.getColorLine());//установим цвет для объекта
        g2.draw((Line2D)myLine);//рисуем линию
        int xpoints[] = {(int)myLine.getPl().x, (int)myLine.getPl1().x, (int)myLine.getPl2().x};//Рисуем стрелочку1 на линии.
        int ypoints[] = {(int)myLine.getPl().y, (int)myLine.getPl1().y, (int)myLine.getPl2().y};//Рисуем стрелочку2 на линии.
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
                //g2.fillPolygon((int)myLine.getP().x,(int)myLine.getP1().x,(int)myLine.getP2().x, 6, 3);
                //g2.draw(myLine.getLine1());
                //g2.draw(myLine.getLine2());
                //currentRectText=null;
                //Рисуем меточку количества отношений в БД Юрика.
                    //установим цвет для объекта
                    //if(myProject.getMyTaksArray().findEll(myLine.getText1())!=null){
                    //g2.setColor(Color.MAGENTA);
                    //g.drawString(" "+myProject.getMyTaksArray().findEll(myLine.getText1()).getAmount(), 
                    //        (int)((myLine.getX1()+myLine.getX2())/2), 
                    //        (int)((myLine.getY1()+myLine.getY2())/2));
                    //вернем цвет
                    //g2.setColor(myLine.getColorLine());
                    //}
    }
}