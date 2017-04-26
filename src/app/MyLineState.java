/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
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
public class MyLineState extends MyLine{
    
    /**Точка начала крыльев стрелки и концов двух стрелок.*/
    private MyPoint pl,pl1,pl2;
    /**Точка середины линии*/
    private MyPoint pCenter;
    /**Точка, удаленная от середины линии для рисования квадрата Признака(процесса)*/
    private MyPoint pPriznak;
    /**расстояние на которое удалена от середины линии точка для рисования квадрата Признака(процесса) */
    int pPs;
    /**линия от центра к квадрату Действия/Признака*/
    private Line2D linePriznak;
    /**текст квадрата Действия/Признака*/
    //private String pText;
    /**квадрат с текстом квадрата Действия/Признака*/
    private MyEllRectState myEllRectState;

    
    
   /**основной конструктор 
   MyLineState(){
       initComponent();
   }
   */
   private void initComponent(){
       //p=new MyPoint();
       pPs=100;
       //pText="текст процесса";
       //myRectState=new MyEllRectState();
   }
   
    /**Строим линию между двумя точками. 
     Крылья линии начинаются отступив 13 поинтов от начала линии.
    MyLineState (MyPoint p1, MyPoint p2){
        initComponent();
        this.setLine(p1, p2);
    }
    */
   /**строим короткую линию между двумя элипсами.
    Крылья линии начинаются отступив 13 поинтов от начала линии.*/
    MyLineState(MyEll f1 , MyEll f2){
        initComponent();
        this.setLine(f1, f2);
        this.myEllRectState= new MyEllRectState(pPriznak,colorLine,null,"","","");
        //super.getPriznakUrlAr().add(new PriznakUrl());//установим счетчик количества линий ==1.(это первая линия отношения), правда с нулевыми параметрами
        //System.out.println(" MyLine 79     f1!=null&f2!=null");
    }
    /**строим короткую линию с текстом процесса между двумя элипсами.
    Крылья линии начинаются отступив 13 поинтов от начала линии.*/
    MyLineState(MyEll f1 , MyEll f2,String text,String start,String end,String interval){
        initComponent();
        this.setLine(f1, f2);
        this.myEllRectState= new MyEllRectState(pPriznak,colorLine,text,start,end,interval);
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
        this.setPCenterLine();//найдем середину для линии.
        //this.setPPriznak(pPs);//найдем Точка, удаленная от середины линии для рисования квадрата Признака(процесса)
        //найдем точку соединения с объектом Признак(действие)
        this.pPriznak=new MyPoint(pCenter.x+70*Math.sin(3.1415926535898),pCenter.y+70*Math.cos(3.1415926535898));
        //создадим линию для соединения основной линии с квадратом признака/действия
        this.linePriznak= new MyLine(this.pCenter,this.pPriznak);
        if(myEllRectState!=null){
            this.myEllRectState.setMyEllRectState(pPriznak,colorLine);
        }
       
        
    }
    /**Рисуенм линию объекта класса MyLineState
     * @param g2
     * @param myLine*/
    public void drawLine(Graphics2D g2,MyLineState myLine){
        //установим цвет для объекта
        g2.setColor(myLine.getColorLine());
        //рисуем линию
        g2.draw((Line2D)myLine);
        //Рисуем стрелочку на линии.
        int xpoints[] = {(int)myLine.getPl().x, (int)myLine.getPl1().x, (int)myLine.getPl2().x};
        int ypoints[] = {(int)myLine.getPl().y, (int)myLine.getPl1().y, (int)myLine.getPl2().y};
        int npoints = 3;
        g2.fillPolygon(xpoints, ypoints, npoints);
        g2.drawLine((int)this.pCenter.x, (int)this.pCenter.y, (int)this.pPriznak.x, (int)this.pPriznak.y);
        g2.setColor(Color.WHITE);
        g2.fillOval((int)pCenter.x-5, (int)pCenter.y-5, 10, 10);//рисуем круг на середине линии
        g2.setColor(myLine.getColorLine());
        g2.drawOval((int)pCenter.x-5, (int)pCenter.y-5, 10, 10);//рисуем круг на середине линии
        myEllRectState.draw(g2, myEllRectState);//рисуем овал признака с ег текстом.
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
    /**Найдем середину линии*/
    private void setPCenterLine(){
        this.pCenter= new MyPoint((this.getX1()+this.getX2())/2,(this.getY1()+this.getY2())/2);
    }
    /**проверяем, или принадлежит данная точка линии или ее объектам.
     Проверку проводим с точностью +-3 точки
     * @param p.
     * @return */
    public boolean ifPointPresentTo(Point p){
        //проверим принадлежность точки основной линии.
        if (ptLineDist(p)<3.0){
            //Проверим принадлежность точки (отрезку) основной части линии.
            if(getP1().distance(p)+getP2().distance(p)<=getLineLength()+3){
                return true;
            }
            
            
        }
        //проверим принадлежность точки линии из середины линии.
            if(this.pCenter.distance(p)+this.pPriznak.distance(p)<=getLineLength(pCenter,pPriznak)+3){
                return true;
            }
        //проверим принадлежность точки квадрату над серединой линии.
            if(this.myEllRectState.contains(p.getX(),p.getY())){
                return true;
            }
        
    
    return false;
    }
    /**Возвращает длину линии.
     * @param pFirst
     * @param pSecond
     * @return  */
    public double getLineLength(MyPoint pFirst,MyPoint pSecond){
        return Math.sqrt ((Math.pow((pSecond.x-pFirst.x),2))+(Math.pow((pSecond.y-pFirst.y),2)));
    }
    public void setMyEllRectStateText(String text){
        this.myEllRectState.setText(text);
    }
    public String getMyEllRectStateText(){
        return myEllRectState.getText();
    }
    /**изменим текст квадрата линии*/
    public void editMyEllRectStateText(){
        myEllRectState.editText();
        
    }
    public MyEllRectState getMyEllRectState() {
        return myEllRectState;
    }

    public void setMyEllRectState(MyEllRectState myEllRectState) {
        this.myEllRectState = myEllRectState;
    }
    /**получим список дат квадрата линии. Используем в одном наследнике MyLine - MyLineStat
     * @return 
    */
    @Override
    public TimeDate getTimeDate_MyEllRectState(){
        return this.myEllRectState.getTimeDate();
    }
    
}