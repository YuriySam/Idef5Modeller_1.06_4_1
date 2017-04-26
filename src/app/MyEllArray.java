/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author Admin
 *Класс с массивом элементов MyEll и методами для работы с массивом. 
 */
public class MyEllArray implements CopyTo{
    /**объявление и инициация массива эллипсов.*/
    private ArrayList <MyEll> ellArray;
    /**Объявление параметра наибольшей длины элипса из присутствующих в массиве эллипсов.*/
    double wMax;
    /**Объявление параметра наибольшей высоты элипса из присутствующих в массиве эллипсов.*/
    double hMax;
    
      
    MyEllArray() {
        initComponent();
    }
    private void initComponent(){
        this.ellArray=new  ArrayList<MyEll>();
        this.wMax=0;
        this.hMax=0;
    }
    /**Запишем обозначенный в параметре массив элипсов как массив элементов.
     Расчитаем параметр наибольшей длины элипса из присутствующих в массиве эллипсов.*/
    public void setEllArray(ArrayList <MyEll> a){
        this.ellArray=a;
        for(int i=0;i<a.size();i++){
            MyEll aEll=a.get(i);
            if(this.wMax<aEll.getWidth()) this.wMax=aEll.getWidth();
            if(this.hMax<aEll.getHeight()) this.hMax=aEll.getHeight();
        }
    }
    /**Возвратим массив эллипсов - элементов*/
    public  ArrayList <MyEll> getEllArray(){
        return  this.ellArray;
    }
    /**Добавим элипс к массиву элипсов.
     Проверим параметр наибольшей длины элипса из присутствующих в массиве эллипсов.*/
    public void addEllToArray (MyEll e ){
        this.ellArray.add(e);
        if(this.wMax<e.getWidth()) this.wMax=e.getWidth();
        if(this.hMax<e.getHeight()) this.hMax=e.getHeight();
    }
    /**Очистим массив эллипсов.
     Обнулим параметр наибольшей длины элипса из присутствующих в массиве эллипсов.*/
    public void cleanEllArray(){
        this.ellArray.clear();
        this.hMax=0;
        this.wMax=0;
    }
    /**Ищем, принадлежит ли данная точка (Point2D p) фигуре Ellipse2D
    * массива circleEll  * если да - возвращаем фигуру Ellipse2D        
    * иначе - возвращаем null
     * @param p
     * @return  */
    public MyEll findEll(Point2D p) {
        for (int i=0;i<ellArray.size();i++){
            MyEll e= ellArray.get(i);
            if (e.contains(p)) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return e;
            }
        }
        //System.out.println("MyPanel public MyEll findEll(Point2D p)"+" null");
        return null;
    }
    /**ищем, принадлежит ли данная точка (Point2D p) фигуре Ellipse2D
    * массива circleEll  * если да - возвращаем фигуру Ellipse2D        
    * иначе - возвращаем null
     * @param p
     * @return  */
    public MyEll findEll(int x,int y) {
        //point2D p = new Point2D();
        for (int i=0;i<ellArray.size();i++){
            MyEll e= ellArray.get(i);
            if (e.contains(x, y)) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return e;
            }
        }
        //System.out.println("MyPanel public MyEll findEll(Point2D p)"+" null");
        return null;
    }
    //public void 
    /**создаем новый эллипс с центром в выбранной мышкой точке и уникальным шаблоном текста*/
    public void add_Ell(Point2D p) {
        MyEll currentEll=new MyEll(p.getX(),p.getY());
        ellArray.add(currentEll);
        //repaint();
    }
    
    /**ищем эллипс по его тексту*/
    public MyEll findEll(String text) {
        for (int i=0;i<ellArray.size();i++){
            MyEll e= ellArray.get(i);
            if (e.getText().contains(text)) return e;
        }
        return null;
    }
    
    /** удаляем круг (Ellipse2D e), из массива circleEll 
    * Пересчитаем поля наибольшей высоты и длинны элипса в массиве эллипсов.
    */    
    public void removeEllipse (Ellipse2D e){
        if (e==null) return;
        //if (e==currentEll) e=null;
        ellArray.remove(e);
        this.wMax=0; this.hMax=0;
        for(int i=0;i<ellArray.size();i++){
            MyEll aEll=ellArray.get(i);
            if(this.wMax<aEll.getWidth()) this.wMax=aEll.getWidth();
            if(this.hMax<aEll.getHeight()) this.hMax=aEll.getHeight();
        }
        //repaint();
    }
    /** удаляем круг (Ellipse2D e), из массива circleEll 
    * Пересчитаем поля наибольшей высоты и длинны элипса в массиве эллипсов.
     * @param text
    */    
    public void removeEllipse (String text){
        if (text.length()<1) return;
        //if (e==currentEll) e=null;
        for(int i=0;i<ellArray.size();i++){
            if(ellArray.get(i).getText().equals(text)){
                ellArray.remove(i);
            }
        }
        this.wMax=0; this.hMax=0;
        for(int i=0;i<ellArray.size();i++){
            MyEll aEll=ellArray.get(i);
            if(this.wMax<aEll.getWidth()) this.wMax=aEll.getWidth();
            if(this.hMax<aEll.getHeight()) this.hMax=aEll.getHeight();
        }
        
    }
    /**Возвратим наибольшую длинну элипса из присутствующих в массиве.*/
    public Double getWMax(){
        return wMax;
    }
    /**Возвратим наибольшую высоту элипса из присутствующих в массиве.*/
    public Double getHMax(){
        return hMax;
    }

    /**Для копирования экземпляра Класса в новый объект.*/
    @Override
    public MyEllArray copyTo() {
        MyEllArray myEllArrayNew= new MyEllArray();//создадим новый объект и скопируем в него все поля объекта этого класса
        if(this.ellArray!=null&&this.ellArray.size()>0){ //если в массиве есть объекты
            for(MyEll myEllOld:this.ellArray){
                MyEll myEllNew = myEllOld.copyTo();
                myEllArrayNew.ellArray.add(myEllNew);
            }
        }
        myEllArrayNew.hMax= this.hMax;
        myEllArrayNew.wMax= this.wMax;
                
        return myEllArrayNew;
    }
}
