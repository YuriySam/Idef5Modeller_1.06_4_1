/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app;


import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author 655
 */
public class MyLineArray implements CopyTo{
    /***/
    private ArrayList <MyLine> lineArray;
    
    MyLineArray(){
    initComponent();
    }
    private void  initComponent(){
    this.lineArray=new ArrayList<MyLine>();
    }
    
    /**Возвращаем массив линий
     * @return .*/
    public ArrayList <MyLine> getLineArray(){
    return  lineArray ;
    }
    /***/
    /***/
    /***/
    /***/
    /**Заменим массив линий.*/
    void setLineArray(ArrayList <MyLine> lineArray){
        this.lineArray=lineArray;
    }
    /**Ищем линию, которая начинается от эллипса  MyEll k.
     * Поиск проводим в массиве линий по тексту эллипса.
     * @param k
     * @return true если нашли линию*/
    public boolean findLineText1EllTextB (MyEll k){
        if (k==null) return false;
        //Ищем и удаляем входящие и исходящие линии.
        //если массив линий имеет элементы
        if(!lineArray.isEmpty()){
            //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<lineArray.size();i++){
                //перебираем все линии массива линий
                if ( lineArray.get(i).getText1().equals(k.getText())){
                    return true;
                }
            }
        }
        return false;
    }
    /**Ищем все линии, которые заканчиваются на эллипсе  MyEll k.
     * Поиск проводим в массиве линий по тексту эллипса.
     * @param k
     * @return true если нашли линию*/
    public boolean findLineText2EllTextB (MyEll k){
        if (k==null) return false;
        //Ищем  входящие линии, если массив линий имеет элементы
        if(!lineArray.isEmpty()){
            //смотрим массив линий, пересчитываем входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<lineArray.size();i++){
                //перебираем все линии массива линий
                if ( lineArray.get(i).getText2().equals(k.getText())){
                    return true;
                }
            }
        }
        return false;
    }
    /**Ищем все линии, которые заканчиваются на эллипсе  MyEll k.
     * Поиск проводим в массиве линий по тексту эллипса.
     * @param k
     * @return */
    public ArrayList <MyLine> findLineText2EllText (MyEll k){
        if (k==null) return null;
        ArrayList <MyLine> inLineAr = new ArrayList<MyLine> ();
        //Ищем  входящие линии, если массив линий имеет элементы
        if(!lineArray.isEmpty()){
            //смотрим массив линий, пересчитываем входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<lineArray.size();i++){
                //перебираем все линии массива линий
                if ( lineArray.get(i).getText2().equals(k.getText())){
                    inLineAr.add(lineArray.get(i));
                }
            }
        }
        if(inLineAr.size()>=0){
            return inLineAr;
        }
        return null;
    }
    /**Ищем все линии, которые начинаются на эллипсе  MyEll k.
     * Поиск проводим в массиве линий по тексту эллипса.
     * @param k
     * @return */
    public ArrayList <MyLine> findLineText1EllText (MyEll k){
        if (k==null) return null;
        ArrayList <MyLine> inLineAr = new ArrayList<MyLine>();
        //Ищем  входящие линии, если массив линий имеет элементы
        if(!lineArray.isEmpty()){
            //смотрим массив линий, пересчитываем входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<lineArray.size();i++){
                //перебираем все линии массива линий
                if ( lineArray.get(i).getText1().equals(k.getText())){
                    inLineAr.add(lineArray.get(i));
                }
            }
        }
        if(inLineAr.size()>=0){
            return inLineAr;
        }
        return null;
    }
    /***/
    /***/
    /***/
    /***/
    /***/
    /***/
    
    /**удаляем все линии к/от выбранного эллипс
     * @param k*/
    public void removeLineEll (MyEll k){
        if (k==null) return;
        //Ищем и удаляем входящие и исходящие линии.
        //если массив линий имеет элементы
        if(!lineArray.isEmpty()){
            //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<lineArray.size();){
                //перебираем все линии массива линий
                //MyLine ml=(MyLine) lineArray.get(i);
                //удаляем исходящие линии для эллипса
                if ( lineArray.get(i).getText1().equals(k.getText())){
                    lineArray.remove(lineArray.get(i));
                    i=0;
                }else{
                    i++;
                }
            }
            for (int i=0;i<lineArray.size();){
                //перебираем все линии массива линий
                //MyLine ml=(MyLine) lineArray.get(i);
                //удаляем входящие линии для эллипса
                if (lineArray.get(i).getText2().equals(k.getText())){
                    lineArray.remove(lineArray.get(i));
                    i=0;
                }else{
                    i++;
                }
            }
        }
        
    }
    /**удаляем все линии от выбранного эллипса*/
    public void removeLineEllOut (MyEll k){
        if (k==null) return;
        //перебираем точки массива точек соединений выбранного эллипса
        for (int j=0;j<k.getMyEllPoints().size();j++){
            MyPoint p=(MyPoint)k.getMyEllPoints().get(j);
            //перебираем все линии массива линий
            for(int i=0;i<lineArray.size();i++){
                MyLine currentLine= lineArray.get(i);
                //если точка эллипса равна точке начала линии 
                if(p.getX()==currentLine.getX1()&p.getY()==currentLine.getY1()){
                    //то удаляем линию
                    removeLine(currentLine);
                    i=0;j=0;
                    
                }
            }
        }
        
    }
    
    /**удаляем всю линию со стрелко
     * @param k*/
    public void removeLine (MyLine k){
        if (k==null) return;
        // ?  if (k==currentLine) {            currentLine=null;        }

        lineArray.remove(k);
        //repaint();
    }
    
   
    /** Ищем с приближением 3.0, принадлежит ли любая данная точка (Point2D p)  основной части 
     * линии MyLine массива circleLine.
     * Под основной частью следует понимать отрезок, соединяющий точки начала и конца линии.
     * если да - возвращаем линию MyLine circleLine       
     * если нет - возвращаем null 
     * @param p - точка, которая проверяется на принадлежность к линии. 
     * @return - линию, (отрезку) основной части которой принадлежит точка.*/ 
    public MyLine findMyLine (Point2D p) {
        for (int k=0; k<lineArray.size();k++) {
            //берем первую линию массива линий  
            MyLine myLine= lineArray.get(k);
            //Проверим принадлежность точки линии.
        //    if (myLine.ptLineDist(p)<3.0){
                //Проверим принадлежность точки (отрезку) основной части линии.
       //         if(myLine.getP1().distance(p)+myLine.getP2().distance(p)<=myLine.getLineLength()+3){
        //            return myLine;
        //        }
        //    }
            if((myLine.ifPointPresentTo((Point)p))){
                return myLine;
                }
        }
        return null;    
    }
    /** Ищем с приближением 3.0, принадлежит ли любая данная точка (Point2D p)  основной части 
     * линии MyLine массива circleLine.
     * Под основной частью следует понимать отрезок, соединяющий точки начала и конца линии.
     * если да - возвращаем линию MyLine circleLine       
     * если нет - возвращаем null 
     * @param p - точка, которая проверяется на принадлежность к линии. 
     * @return - true, если найдена линия, (отрезок) основной части которой принадлежит точка.
                */ 
    public Boolean findMyLineB (Point2D p) {
        for (int k=0; k<lineArray.size();k++) {
            //берем первую линию массива линий  
            MyLine myLine= lineArray.get(k);
            //Проверим принадлежность точки линии.
        //    if (myLine.ptLineDist(p)<3.0){
                //Проверим принадлежность точки (отрезку) основной части линии.
        //        if(myLine.getP1().distance(p)+myLine.getP2().distance(p)<=myLine.getLineLength()+3){
        //            return true;
        //        }
                
        //    }
            if((myLine.ifPointPresentTo((Point)p))){
                return true;
                }
        }
        
        return false;    
    }
    /**Подсчитаем количество входящих линий для эллипса
     * @param text.
     * @return */
    public int countMyLineIn(String text){
        int count=0;
        for(int i=0;i<lineArray.size();i++){
            if(lineArray.get(i).getText2().equals(text)){
                count++;
            }
        }
        return count;
    }
    /**Подсчитаем количество выходящих линий для эллипса
     * @param text.
     * @return */
    public int countMyLineOut(String text){
        int count=0;
        for(int i=0;i<lineArray.size();i++){
            if(lineArray.get(i).getText1().equals(text)){
                count++;
            }
        }
        return count;
    }
    /**удаляем линию из массива линий.
     * @param line целевая линия.
     * 655 24.07.2016
     */
    public void removeMyLine(MyLine line){
        for(MyLine mLine: this.lineArray){
            if(mLine.getText1().equals(line.getText1()) && mLine.getText2().equals(line.getText2())){
                this.lineArray.remove(mLine);
                break;//чтоб не выскакивало исключение, ведь одновременно смотрим и изменяем один и и тот же массив.
            }
        }
       
    }
    /**Для копирования экземпляра Класса в новый объект.*/
    @Override
    public MyLineArray copyTo() {
        MyLineArray myLineArrayNew = new MyLineArray();
        if(this.lineArray!=null&&this.lineArray.size()>0){ //если в массиве есть объекты
            for(MyLine myLineOld:this.lineArray){
                MyLine myLineNew = myLineOld.copyTo();
                myLineArrayNew.lineArray.add(myLineNew);
            }
        }
        return myLineArrayNew;
    }
}
