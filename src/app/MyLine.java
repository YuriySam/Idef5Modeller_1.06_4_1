/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Admin
 * Стрелка <--------.
 * Описывает соединительную линию между графическими фигурами.
 * Графические фигуры имеют зарезервированные точки соединения с линиями. 
 * Эти точки собраны в массив.
 * Для каждого вида фигур точки разные.
 * Привязку начала и конца линии делаем по тексту начальной и конечной фигур.
 * 6/09/2015 Планирую дописать методы для отображения линий на панели MyJFrame.
 */
public class MyLine extends Line2D implements CopyTo{
   /**Координаты начала н конца линии.*/
   private double x1,y1,x2,y2;
   /**Точки нначала и конца линии*/
   private MyPoint p1,p2;
   /**длина линии  lineLength*/
   private double lineLength;
   /**Cтрелочки линии.*/
    private Line2D line1, line2;
    /**текст начальной и конечной фигур*/
    private String text1,text2;
    /**координаты начальной и конечной фигур*/
   private double tx1,ty1,tx2,ty2;
   /**Укажем, надо ли выделять цветом вигуру*/
    protected Color colorLine;
    /**Список сайтов/мест, где встретилась пара. */
    private ArrayList <PriznakUrl> priznakUrlAr;
    
    
   
   MyLine(){
       initComponent();
   }
   private void initComponent(){
       this.text1="";
       this.text2="";
       this.colorLine=Color.BLACK;
       priznakUrlAr=new  ArrayList<PriznakUrl>();
       
   }
   
   /**строим линию между двумя точками с координатами*/
   MyLine(double x1, double y1, double x2, double y2) {
       initComponent();
       this.setLine(x1, y1, x2, y2);
    }
   /**строим линию между двумя точками*/
   MyLine (MyPoint p1, MyPoint p2){
       initComponent();
       this.setLine(p1, p2);
    }
   /**строим короткую линию между двумя элипсами*/
    MyLine(MyEll f1 , MyEll f2){
        initComponent();
        this.setLine(f1, f2);
    }
    
       
    /**Находим длину линии.
     * @param p1
     * @param p2
     * @return  */
    public double lengthMyLine( MyPoint p1, MyPoint p2){
        return Math.sqrt ((Math.pow((p2.x-p1.x),2)+Math.pow((p2.y-p1.y),2)));
    }
    /**Находим длину этой линии. */
    private double lengthMyLine(){
        return Math.sqrt ((Math.pow((x2-x1),2))+(Math.pow((y2-y1),2)));
    }
    @Override
    public double getX1(){
        return x1;
    }
    @Override
    public double getY1(){
        return y1;
    }
    @Override
    public Point2D getP1(){
        return p1;
    }
    @Override
    public double getX2(){
        return x2;
    }
    @Override
    public double getY2(){
        return y2;
    }
    @Override
    public Point2D getP2(){
        return p2;
    }
    public Line2D getLine1(){
        return  line1;
    }
    public Line2D getLine2(){
        return  line2;
    }
    /**Возвращает text1 - текст, который содержит начальная фигура линии.
     * @return  */
    public String getText1() {
        return text1;
    }
    /**Возвращает text2 - текст, который содержит конечная фигура линии.
     * @return  */
    public String getText2() {
        return text2;
    }
    /**Возвращает tx1 - значение координаты начальной фигуры линии.
     * @return  */
    public double getTx1() {
        return tx1;
    }
    /**Возвращает tx2 - значение координаты конечной фигуры линии.
     * @return  */
    public double getTx2() {
        return tx2;
    }
    /**возвращает ty1  - значение координаты начальной фигуры линии.
     * @return  */
    public double getTy1() {
        return ty1;
    }
    /**возвращает ty2 - значение координаты конечной фигуры линии.
     * @return  */
    public double getTy2() {
        return ty2;
    }
    public void setX1(double x1){
        this.x1=x1;
    }
    public void setY1(double y1){
        this.y1= y1;
    }
    
    public void setX2(double x2){
        this.x2= x2;
    }
    public void setY2(double y2){
        this.y2= y2;
    }
    
    
    
    /**Присвоим текст начальной фигуры началу линии
     * @param text1.*/
   public void setText1(String text1){
       this.text1=text1;
   }
   /**Присвоим текст конечной фигуры концу линии
     * @param text2.*/
   public void setText2(String text2){
       this.text2=text2;
   }
   /**Присваивает tx1 - значение координаты начальной фигуры линии.
     * @param tx1 */
    public void setTx1(double tx1) {
        this.tx1= tx1;
    }
    /**Присваивает tx2 - значение координаты конечной фигуры линии.
     * @param tx2 */
    public void setTx2(double tx2) {
        this.tx2=tx2;
    }
    /**Присваивает ty1  - значение координаты начальной фигуры линии.
     * @param ty1 */
    public void setTy1(double ty1) {
        this.ty1= ty1;
    }
    /**Присваивает ty2 - значение координаты конечной фигуры линии.
     * @param ty2 */
    public void setTy2(double ty2) {
        this.ty2= ty2;
    }
    /**строим линию между двумя точками с координатам
     * @param x1
     * @param y1
     * @param x2
     * @param y2*/
   @Override
    public void setLine(double x1, double y1, double x2, double y2) {
        
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
        this.p1=new MyPoint(x1,y1);
        this.p2=new MyPoint(x2,y2);
        this.lineLength=this.lengthMyLine();
        //System.out.println(" MyLine  135 setLine(double x1, double y1, double x2, double y2) " );
    
        double r=Math.atan2(x1-x2,y1-y2);//угол линии
        //находим одно крыло стрелки
        this.line1= new Line2D.Double(x2, y2,(x2+13*Math.sin(r+0.5)),(y2+13*Math.cos(r+0.5)));
        //находим другое "крыло": стрелки
        this.line2= new Line2D.Double(x2, y2,(x2+13*Math.sin(r-0.5)),(y2+13*Math.cos(r-0.5)));
        
    }
    /**Строим линию между двумя точками.
     * @param p1
     * @param p2 */
    public void setLine(MyPoint p1, MyPoint p2){
        
        this.x1=p1.getX();
        this.x2=p2.getX();
        this.y1=p1.getY();
        this.y2=p2.getY();
        this.p1=p1;
        this.p2=p2;
        this.lineLength=this.lengthMyLine();
        //System.out.println(" MyLine  135 setLine(double x1, double y1, double x2, double y2) " );
    
        double r=Math.atan2(x1-x2,y1-y2);//угол линии
        //находим одно крыло стрелки
        this.line1= new Line2D.Double(x2, y2,(x2+13*Math.sin(r+0.5)),(y2+13*Math.cos(r+0.5)));
        //находим другое "крыло": стрелки
        this.line2= new Line2D.Double(x2, y2,(x2+13*Math.sin(r-0.5)),(y2+13*Math.cos(r-0.5)));
        this.colorLine=Color.BLACK;
    }
    /**строим короткую линию между двумя элипсами*/
    void setLine(MyEll f1 , MyEll f2){
        //пересчитаем размеры текстов и эллипсов перед построением линии
        
        if(f1!=null&f2!=null) {
            //назначаем эталонные точки соединения объектов
            MyPoint p1Line=f1.getP0();
            MyPoint p2Line=f2.getP0();
//System.out.println("213 MyLine расстояние p1Line p2Line= "+p1Line.length(p2Line));
            //перебираем все точки первого эллипса
            for (int i=0; i<f1.getMyEllPoints().size();i++) {
                MyPoint pi=(MyPoint)f1.getMyEllPoints().get(i);
                //перебираем все точки второго эллипса
                for(int j=0;j<f2.getMyEllPoints().size();j++) {
                    MyPoint pj=(MyPoint)f2.getMyEllPoints().get(j);
                    //сравниваем расстояние с этолоном, если эталон больше - меняем эталон
                    if (p1Line.length(p2Line) >= pi.length(pj)){
                        p1Line=pi;
                        p2Line=pj;
//System.out.println("226 MyLine расстояние p1Line "+i+" из "+f1.getMyEllPoints().size()+" p2Line "+j+" из "+f2.getMyEllPoints().size()+" = "+p1Line.length(p2Line));
            
                    }
                 }
            }
        setLine(p1Line,p2Line);
        this.text1=f1.getText();
        this.text2=f2.getText();
        this.tx1=f1.getX();
        this.tx2=f2.getX();
        this.ty1=f1.getY();
        this.ty2=f2.getY();
        this.lineLength=this.lengthMyLine();
        //this.colorLine=Color.BLACK;
        }
        //System.out.println(" MyLine 79     f1!=null&f2!=null");
    }
    /**строим короткую линию между элипсом и точкой*/
    void setLine(MyEll f1 , Point p){
        //пересчитаем размеры текстов и эллипсов перед построением линии
        
        if(f1!=null&p!=null) {
            //назначаем эталонные точки соединения объектов
            MyPoint p1Line=f1.getP0();
            MyPoint p2Line=new MyPoint(p.getX(),p.getY());
//System.out.println("213 MyLine расстояние p1Line p2Line= "+p1Line.length(p2Line));
            //перебираем все точки первого эллипса
            for (int i=0; i<f1.getMyEllPoints().size();i++) {
                MyPoint pi=(MyPoint)f1.getMyEllPoints().get(i);
                //перебираем все точки второго эллипса
                //for(int j=0;j<f2.getMyEllPoints().size();j++) {
                    //MyPoint pj=(MyPoint)f2.getMyEllPoints().get(j);
                    //сравниваем расстояние с этолоном, если эталон больше - меняем эталон
                    if (p1Line.length(p2Line) >= pi.length(p2Line)){
                        p1Line=pi;
                        
//System.out.println("226 MyLine расстояние p1Line "+i+" из "+f1.getMyEllPoints().size()+" p2Line "+j+" из "+f2.getMyEllPoints().size()+" = "+p1Line.length(p2Line));
            
                    }
                 //}
            }
        setLine(p1Line,p2Line);
        this.text1=f1.getText();
        
        this.tx1=f1.getX();
        this.tx2=p2Line.getX();
        this.ty1=f1.getY();
        this.ty2=p2Line.getY();
        this.lineLength=this.lengthMyLine();
        //this.colorLine=Color.BLACK;
        }
        //System.out.println(" MyLine 79     f1!=null&f2!=null");
    }
    
    @Override
    public Rectangle2D getBounds2D() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**вывод всех данных (кроме линий направления стрелки)по выбранному линии
     * @return  */
    @Override
    public String toString(){
        return getClass().getName()+"[ x1= "+x1+" y1= "+y1+" x2= "+x2+" y2= "+y2+" p1= "+p1+" p2= "
                +p2+" text1= "+text1+" text2= "+text2+" tx1="+tx1+" ty1="+ty1+" tx2="+tx2+" ty2="+ty2+"]";
    }
/** выводит на экран описание линии (кроме линий направления стрелки)*/
    public void printMyLine() {
        System.out.println(this.toString());
    }
    /**Присвоим значение флагу: Надо ли отмечать этот эллипс цвето
     * @param colorLine
     * */
    public void setColorLine(Color colorLine){
    this.colorLine=colorLine;
    }
    /**Возвратим значение флага: Надо ли отмечать этот эллипс цвето
     * @return м*/
    public Color getColorLine(){
        return this.colorLine;
    }
    /**
     * @param p1*/
    public void setP1(MyPoint p1) {
        this.x1=p1.getX();
        this.y1=p1.getY();
        this.p1=p1;
        
    }
    /**
     * @param p2*/
    public void setP2(MyPoint p2) {
        this.x2=p2.getX();
        this.y2=p2.getY();
        this.p2=p2;
        
    }
    /**Возвращает длину линии.
     * @return  */
    public double getLineLength(){
        return this.lineLength;
    }
    /**Находим длину этой линии. */
    public void setLinelength(){
        this.lineLength= Math.sqrt ((Math.pow((x2-x1),2))+(Math.pow((y2-y1),2)));
    }
    
    /**Добавим url сайта где найдено отношение и признак отношения.
     * @param url
     * @param priznak*/
    public void addPriznakUrlAr(String url, String priznak){
        int count=0;
        if(this.priznakUrlAr.size()!=0){
            for(PriznakUrl priznakUrl: this.priznakUrlAr ){
                if(priznakUrl.get_url().equals(url)&&priznakUrl.get_priznak().equals(priznak)){
                    count++;
                }
            }
        }
        if (count==0)this.priznakUrlAr.add(new PriznakUrl(url,priznak));
    }
    
    /**Возвратим значение массива признаков и url.
     * @return */
    public ArrayList <PriznakUrl> getPriznakUrlAr(){
    return this.priznakUrlAr;
    }
    /**Рисуенм линию объекта класса MyLine
     * @param g2
     * @param myLine*/
    public void drawLine(Graphics2D g2,MyLine myLine){
        //установим цвет для объекта
                g2.setColor(myLine.getColorLine());
                //рисуем линию
                g2.draw((Line2D)myLine);
                //рисуем стрелку
                //g2.draw((Line2D)line1);
                //g2.draw((Line2D)line2);
    }
    /**проверяем, или принадлежит данная точка линии или ее объектам.
     Проверку проводим с точностью +-3 точки
     * @param p.
     * @return */
    public boolean ifPointPresentTo(Point p){
        //проверим принадлежность точки основной линии.
        if (this.ptLineDist(p)<3.0){
            //Проверим принадлежность точки (отрезку) основной части линии.
            if(getP1().distance(p)+getP2().distance(p)<=getLineLength()+3){
                return true;
               
            }
        }
        //проверим принадлежность точки линии из середины линии.
        
        //проверим принадлежность точки квадрату над серединой линии.
        
    
    return false;
    } 
    /**изменим текст квадрата линии. Используем в одном наследнике - MyLineState*/
    public void editMyEllRectStateText(){
        //myEllRectState.editText();
        
    }
    /**получим текст квадрата линии. Используем в одном наследнике - MyLineState*/
    public String getMyEllRectStateText(){
        return "";
                //myEllRectState.getText();
    } 
    /**получим список дат квадрата линии. Используем в одном наследнике - MyLineStat
     * @return e*/
    public TimeDate getTimeDate_MyEllRectState(){
        return null;
                //myEllRectState.getText();
    }
    /**Добавим массив priznakUrlArAdd к текущему массиву, .
     * Не реализовано: только неповторяющиеся члены (уникальные).
     * Не реализовано: Если url == "", добавляем все.
     * @param priznakUrlArAdd)*/
    public boolean addPriznakUrlAr(ArrayList<PriznakUrl> priznakUrlArAdd) {
        boolean end=false;
        //ArrayList<PriznakUrl> priznakUrlArTemp=new ArrayList<PriznakUrl>();
        int size=priznakUrlArAdd.size();
        for(int i=0;i<size;i++){
            this.priznakUrlAr.add(new PriznakUrl());
            end=true;
        }
        if(priznakUrlArAdd.size()==0){
            this.priznakUrlAr.add(new PriznakUrl());
             end=true;
        }
        
    return  end;   
    }
    /**Перенесем массив priznakUrlArAdd вместо текущего массива, .
     * Не реализовано: только неповторяющиеся члены (уникальные).
     * Не реализовано: Если url == "", добавляем все.
     * @param priznakUrlArAdd)
     * @return */
    public boolean setPriznakUrlAr(ArrayList<PriznakUrl> priznakUrlArAdd) {
        boolean end=false;
        this.priznakUrlAr.clear();//очистим текущий массив
        int size=priznakUrlArAdd.size();
        for(int i=0;i<size;i++){//добавим все элементы в текущий массив
            this.priznakUrlAr.add(new PriznakUrl());
            end=true;
        }
        if(priznakUrlArAdd.size()==0){
            this.priznakUrlAr.add(new PriznakUrl());
             end=true;
        }
        return  end;   
    }

    public void setLine1(Line2D line1) {
        this.line1 = line1;
    }

    public void setLine2(Line2D line2) {
        this.line2 = line2;
    }

    /**Для копирования экземпляра Класса в новый объект.*/
    @Override
    public MyLine copyTo() {
        MyLine myLineNew = new MyLine();
        myLineNew.x1=x1;
        myLineNew.y1= y1;
        myLineNew.x2= x2;
        myLineNew.y2= y2;
        myLineNew.p1= new MyPoint (p1.getX(),p1.getY());
        myLineNew.p2= new MyPoint (p2.getX(),p2.getY());
        myLineNew.lineLength= lineLength;
        double r=Math.atan2(x1-x2,y1-y2);//угол линии
        //находим одно крыло стрелки
        myLineNew.line1= new Line2D.Double(x2, y2,(x2+13*Math.sin(r+0.5)),(y2+13*Math.cos(r+0.5)));
        //находим другое "крыло": стрелки
        myLineNew.line2= new Line2D.Double(x2, y2,(x2+13*Math.sin(r-0.5)),(y2+13*Math.cos(r-0.5)));
        myLineNew.text1= text1;
        myLineNew.text2= text2;
        myLineNew.tx1= tx1;
        myLineNew.ty1= ty1;
        myLineNew.tx2= tx2;
        myLineNew.ty2= ty2;
        myLineNew.colorLine=colorLine;
        if(this.priznakUrlAr!=null&&this.priznakUrlAr.size()>0){ //если в массиве есть объекты
            for(PriznakUrl priznakUrlOld:this.priznakUrlAr){
                PriznakUrl priznakUrlNew = priznakUrlOld.copyTo();
                myLineNew.priznakUrlAr.add(priznakUrlNew);
            }
        }
        return myLineNew;
    }
    
}
