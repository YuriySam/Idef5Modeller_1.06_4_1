/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
Класс с массивами обектов окна отношений Таксономии и методы для работы с элементами класса.
В этом классе собрано все, что относится к таксономическим отношениям вцелом.
Он включает в себя:
    - временный вспомогательный массив для визуального создания отношений таксономии,
    - массив отношений таксономии, 
    - размеры рисунка для вывода его на экран, 
    - методы для вычисления размероврисунка для вывода отношений таксономии,
    - иассив элементов класса соединительных линий для визуализации отношений таксономии,
    - и др.
Также есть возможность автоматического упорядочивания элементов Таксономии, присутствующих в проекте.
Расчет размеров окна для их отображения.
Или использование сохраненных размеров окна Таксономии прошлого сеанса(сохранения).
 */

package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author 655
 * Массивы отношений Таксономии. 
 * Отображение на экране. 
 * Заполнение.
 * Изменение.
 * Сохранение.
 * Импорт-экспорт.
 * 
 */ 
public class MyTaksArray implements CopyTo{

    private ArrayList <MyTaks> myTaksArray;

    private ArrayList <MyEll> myTaksArrayToIn;
    /**Класс массива элементов соединительных линий. Создается при наполнении массива отношений Таксономии.
    Линия направлена от Элемента к Классу.*/
    private MyLineArray myLineArray;
    /**Отступ между рисунком таксономии и границами окна. 
    * Используется при автоиатическом расположении объектов  разных классов отношений на экране.
    Расстояние между рисун*/
    private int w;
    /**Размер рисунка для вывода всех элементов массива отношений таксономии и формируемых отношений.
     Расчитываем высоту сверху как:
      w+((высита эллипса)
      +(расстояние до более нижнего элипса))
      *(количество элементов в массиве элементов)
      +w
     Пересчитываем при:
      - создании экземпляра класса;
      - добавлении объекта в любой массив объектов отношения;
      - импорте(копировании).*/
    private Dimension windowSize;
    /**Массив для отображения эллипсов всех отношений Таксономий на рисунке.*/
    private ArrayList <MyEll> paintTaksEll;
    /**Флаг для необходимости пересчета размера экрана*/
    private boolean resizeWindow;
                
    
    MyTaksArray(){
        
       
    initComponent();
    }
    private void initComponent(){
        this.paintTaksEll=new  ArrayList<MyEll>();
        this.myTaksArrayToIn = new  ArrayList<MyEll>();
        this.myTaksArray = new  ArrayList<MyTaks>();
        this.myLineArray=new MyLineArray();
        this.w=30;
        this.windowSize=new Dimension(w,w);
        this.resizeWindow=true;           
    }
  
    /**Добавим к текущему массиву таксономии новое отношение myTaks.
      Формируем новые размеры окна для отображения объектов отношений.
     * @param myTaks
     */
    public void addToMyTaksArray(MyTaks myTaks){
        this.myTaksArray.add(myTaks);
        //Сформируем размеры окна для отображения объектов отношения.
        //Добавим к текущим размерам окна размеры нового отношения.
        windowSize.setSize(windowSize.getHeight()+myTaks.getWindowSise().getHeight(), windowSize.getWidth()+myTaks.getWindowSise().getWidth());
    }
    /**Сформируем массив линий для отношений таксономии.
     Просматриваем массив отношений таксономий. 
     Берем текст каждой пары отношений.
     * Просматриваем массив нарисованых эллипсов.
     Соединяем линией эллипсы с найденным текстом попарно.
     Созданный линии записываем в массив линий отношений таксономии.*/
    public void createLinesArrayFromTaksArray(){
        //Для каждого отношения таксономии.
        for(int i=0;i<this.myTaksArray.size();i++){
            MyTaks myTaks =this.myTaksArray.get(i);
        //просмотрим все элементы отношения и создадим линию от каждого элемента к классу.
        MyEll klassEll= myTaks.getKlassEll();
        for(int il=0;il<myTaks.getElemEllArray().getEllArray().size();il++){
            MyEll elemEll=  myTaks.getElemEllArray().getEllArray().get(il);
            //Создадим линию от элемента к классу по тексту фигур.
            //MyLine myLine = new MyLine(this.foundPaintTaksEll(elemEll.getText()),this.foundPaintTaksEll(klassEll.getText()));
             //Создадим линию нового образца от элемента к классу по тексту фигур.
            MyLine2 myLine = new MyLine2(this.findPaintEll(elemEll.getText()),this.findPaintEll(klassEll.getText()));
            //Сохраним созданные линии в массиве линий отношений таксономии.
            myLineArray.getLineArray().add(myLine);
        }
        }
    
    }
    /**Добавим к текущему массиву таксономий отношения из массива.
     Фрмируем массив линий зависимости от элемента к классу.
     Формируем новые размеры окна для отображения объектов отношений.
     * @param myTaksAr
     */
    public void addMyTaksArray(ArrayList <MyTaks> myTaksAr){
        for(int i=0;i<myTaksAr.size();i++){
            addToMyTaksArray(myTaksAr.get(i));
            
            
        }
    }
    /**Получим значение массива отношений
     * @return .*/
    public ArrayList <MyTaks> getMyTaksArray(){
        return this.myTaksArray;
    }
    /**Добавим к текущему массиву заготовок таксономии новую заготовку MyEl
     * @param myEll*/
    public void addToMyTaksArrayToIn(MyEll myEll){
        this.myTaksArrayToIn.add(myEll);
    }
    /**Получим значение массива заготовок отношений
     * @return .*/
    public ArrayList <MyEll> getMyTaksArrayToIn(){
    return this.myTaksArrayToIn;
    }
    
    /**Получим значения массива лини
     * @return й*/
    public MyLineArray  getMyLineArray(){
    return this.myLineArray;
    }
   
    
    
    
     /**выводим на консоль массив элипсов элементов отношения*/
    void printElem(){
        for(int i=0;i<myTaksArray.size();i++){
            MyEll meKlass=myTaksArray.get(i).getKlassEll();
            for(int j=0;j<myTaksArray.get(i).getElemEllArray().getEllArray().size();j++){
                MyEll meElem= myTaksArray.get(i).getElemEllArray().getEllArray().get(j);
                meKlass.printMyEll(meKlass,meElem);
            }
        }
        //System.out.println("103 [MuTaks]");
    }
    /**выводим на консоль массив элипсов класса и элементов отношения
    void print(){
        //System.out.println("127 [MuTaks] klass(");
        klass.printMyEll();
        System.out.println("129 [MuTaks] elemArray(");
        for(int i=0;i<elemArray.size();i++){
            MyEll me=elemArray.get(i);
            me.printMyEll();
        }
        System.out.println("134 [MuTaks] the End of print     klass + elemArray");
    }
    */
    
 /**ищем, принадлежит ли данная точка (Point2D p) фигурам класса MyTaksArray
 массивов:
 myTaksArray, 
 myTaksArrayToIn
 если да - возвращаем фигуру Ellipse2D        
 иначе - возвращаем null
     * @param x
     * @param y
     * @return  */
    public MyEll findEll(double x,double y) {
        //point2D p = new Point2D();
        for (int i=0;i<myTaksArray.size();i++){
            MyEll klass= myTaksArray.get(i).getKlassEll();
            if (klass.contains(x, y)) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return klass;
            }
            for(int j=0;j<myTaksArray.get(i).getElemEllArray().getEllArray().size();j++){
            MyEll elem= myTaksArray.get(i).getElemEllArray().getEllArray().get(j);
            if (elem.contains(x, y)) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return elem;
            }
            }
            
        }
        for (int i=0;i<myTaksArrayToIn.size();i++){
            MyEll e= myTaksArrayToIn.get(i);
            if (e.contains(x, y)) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return e;
            }
        }
        
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+" null");
        return null;
    }
 /**Ищем, принадлежит ли данная точка (Point2D p) фигурам класса MyTaksArray
 массивов:
 myTaksArray, 
 myTaksArrayToIn
 если да - возвращаем true        
 иначе - возвращаем false
     * @param x
     * @param y
     * @return  */
    public boolean findEllB(double x,double y) {
        //point2D p = new Point2D();
        for (int i=0;i<myTaksArray.size();i++){
            MyEll klass= myTaksArray.get(i).getKlassEll();
            if (klass.contains(x, y)) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return true;
            }
            for(int j=0;j<myTaksArray.get(i).getElemEllArray().getEllArray().size();j++){
            MyEll elem= myTaksArray.get(i).getElemEllArray().getEllArray().get(j);
            if (elem.contains(x, y)) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return true;
            }
            }
            
        }
        for (int i=0;i<myTaksArrayToIn.size();i++){
            MyEll e= myTaksArrayToIn.get(i);
            if (e.contains(x, y)) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return true;
            }
        }
        
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+" null");
        return false;
    }
    
/** Удаляем выбранный эллипс с экрана. 
 *  удаляем входящие и исходящие линии выбранного эллипса.
 * @author 655  29.06.2016
 * @param e эллипс, который удаляем.
*/    
    public void removePaintEllipse (MyEll e) {
        if (e==null) return;
        //Удаляем выбранный эллипс с экрана
        this.paintTaksEll.remove(e);
        //Удалим все линии выбранного эллипса с экрана.
        this.myLineArray.removeLineEll(e);
        
    }
    public void removeEllipseOld (MyEll e) {
        if (e==null) return;
        //Удаляем выбранный эллипс с экрана
        this.paintTaksEll.remove(e);
        //ищем и удаляем выбранный эллипс из массива myTaksArrayToIn
        if( !myTaksArrayToIn.remove(e)){
            //если это не случилось, ищем и удаляем выбранный эллипс  из массива отношений таксономии myTaksArray
            for(int i=0;i<myTaksArray.size();i++){//Смотрим каждое отношение
                // Смотрим элементы отношения. Ищем и удаляем там.
                for(int j =0; j<myTaksArray.get(i).getElemEllArray().getEllArray().size();j++){
                    MyEll rElem= myTaksArray.get(i).getElemEllArray().getEllArray().get(j);
                    if (rElem.getText().equals(e.getText())){
                        //если это не последний элемент отношения, удаляем элемент отношения.
                        
                        if(myTaksArray.get(i).getElemEllArray().getEllArray().size()>1){
                            //удаляем  линии фигуры 
                            myLineArray.removeLineEll(e);
                            //удаляем элемент из отношения
                            myTaksArray.get(i).getElemEllArray().getEllArray().remove(j);
                            j=-1;
                        }
                         //если это последний элемент отношения, удаляем элемент отношения.
                        if(myTaksArray.get(i).getElemEllArray().getEllArray().size()==1){//если это последний элемент отношения, 
                            //переносим класс в массив myTaksArrayToIn:
                            myTaksArrayToIn.add(myTaksArray.get(i).getKlassEll());
                            //удаляем отношение
                            myTaksArray.remove(i);
                            //удаляем  линии фигуры 
                            myLineArray.removeLineEll(e);
                            j=-1;
                        }
                    }
                    
                }
            }
            //ищем и удаляем выбранный эллипс как объект класса из массива отношений таксономии myTaksArray
            for(int i=0;i<myTaksArray.size();i++){//Смотрим каждое отношение
            //Если в отношении таксономии эллипс был классом,
                MyEll rKlass = myTaksArray.get(i).getKlassEll();
                if(rKlass.getText().equals(e.getText())){
                    //удаляем линии класса
                    myLineArray.removeLineEll(myTaksArray.get(i).getKlassEll());
                    //Смотрим элементы отношения.
                    for(int ri=0;ri<myTaksArray.get(i).getElemEllArray().getEllArray().size();ri++){
                        //Если елементы не используются в других отношениях как элемент,
                        //и елементы не используются в других отношениях как класс, переносим их в массив myTaksArrayToIn
                        if(!myLineArray.findLineText1EllTextB( myTaksArray.get(i).getElemEllArray().getEllArray().get(ri))
                                &&!myLineArray.findLineText2EllTextB( myTaksArray.get(i).getElemEllArray().getEllArray().get(ri))){
                            myTaksArrayToIn.add( myTaksArray.get(i).getElemEllArray().getEllArray().get(ri));
                        } 
                    }
                    //удаляем отношения с этим rKlass
                    myTaksArray.remove(i);
                    i=-1;
                }
            }
        }
    }
    /**Удаляем элемент отношения.
     Смотрим массив элементов для каждого MyTaks.
     если элемент с таким текстом найден и удален, возвращаем true*/
    private boolean removeMyTaksElemText(String text){
        for(int i=0;i<myTaksArray.size();i++){//Смотрим каждое отношение
            // Смотрим элементы отношения. Ищем и удаляем там.
            for(int j =0; j<myTaksArray.get(i).getElemEllArray().getEllArray().size();j++){
                MyEll rElem= myTaksArray.get(i).getElemEllArray().getEllArray().get(j);
                if (rElem.getText().equals(text)){
                    //если это не последний элемент отношения, удаляем элемент отношения.
                    if(myTaksArray.get(i).getElemEllArray().getEllArray().size()>1){
                        //удаляем элемент из отношения
                        myTaksArray.get(i).getElemEllArray().getEllArray().remove(j);
                        return true;
                        }else{//если это последний элемент отношения, 
                            //переносим класс в массив myTaksArrayToIn:
                            myTaksArrayToIn.add(myTaksArray.get(i).getKlassEll());
                            //удаляем отношение
                            myTaksArray.remove(i);
                            return true;
                        }
                    }
                }
            }
        return false;
    }
    /**Удаляем класс отношения.
     Смотрим поле класса для каждого MyTaks.
     если класс с таким текстом найден и удален, возвращаем true*/
    private boolean removeMyTaksKlassText(String text){
        //ищем и удаляем выбранный эллипс как объект класса из массива отношений таксономии myTaksArray
        for(int i=0;i<myTaksArray.size();i++){//Смотрим каждое отношение
            //Если в отношении таксономии эллипс был классом,
            MyEll rKlass = myTaksArray.get(i).getKlassEll();
            if(rKlass.getText().equals(text)){
                //смотрим массив его элементов.
                //если элемент входит в другое отношение таксономии, его не трогаем.
                //если элемент не входит ни в одно другое отношение таксономии, переносим его в MyTaksArrayToIn
                //for(int j =0; j<myTaksArray.get(i).getElemEllArray().getEllArray().size();j++)
                for (MyEll rElem :myTaksArray.get(i).getElemEllArray().getEllArray()){
                    //MyEll rElem=(MyEll) myTaksArray.get(i).getElemEllArray().getEllArray().get(j);
                    //если есть элементы без входящих и исходящих линий
                    if (!this.myLineArray.findLineText1EllTextB(rElem)&&!this.myLineArray.findLineText2EllTextB(rElem)){
                        myTaksArrayToIn.add(rElem);
                    }
                    //если у элемента есть свои элементы и он не есть ничьим элементом
                    if (this.myLineArray.findLineText2EllTextB(rElem)&&!this.myLineArray.findLineText1EllTextB(rElem)){
                        //найдем отношение, где этот элемент является классом.
                        for(MyTaks myTaks: this.myTaksArray){
                            if(myTaks.getKlassEll().getText().equals(rElem.getText())){
                                this.paintTaksEll.remove(rElem);
                                this.paintTaksEll.add(myTaks.getKlassEll());
                                renewEllLine(myTaks.getKlassEll());
                            }
                        }
                    }
                    //если у элемента есть свои элементы и он есть чьим-то элементом
                    if (this.myLineArray.findLineText2EllTextB(rElem)&&!this.myLineArray.findLineText1EllTextB(rElem)){
                        //найдем другое отношение, где этот элемент является элементом.
                        for(MyTaks myTaks: this.myTaksArray){
                            //если элемент не является классом в проссматриваемом выражении.
                            if(!myTaks.getKlassEll().getText().equals(rElem.getText())){
                                //а выбранный элемент тоже является элементом
                                for(MyEll myEll: myTaks.getElemEllArray().getEllArray()){
                                    if(myEll.getText().equals(rElem.getText())){
                                        //this.paintTaksEll.remove(rElem);
                                        //this.paintTaksEll.add(myEll);
                                        //renewEllLine(myEll);
                                    }
                                    
                                }
                            }
                        }
                    }
                }
                
                //удаляем отношения с этим rKlass
                myTaksArray.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**Переназначим линии к перемещенному эллипсу
     * @param mEll.*/
    public void renewLineMyEll(MyEll mEll){
        //если массив линий имеет элементы
        if(!myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                MyLine2 ml=(MyLine2) myLineArray.getLineArray().get(i);
                //переназначаем исходящие линии для перемещаемого эллипса .contains - стреляет неточно.
                //if (ml.getText1().contains(makeEll.getText())) ml.setLine(makeEll, (MyEll) myProject.getMyTaksArray().findEll(ml.getText2()));
                //переназначаем исходящие линии для перемещаемого эллипса
                if (ml.getText1().equals(mEll.getText())) ml.setLine(mEll, findEll(ml.getText2()));
                //переназначаем входящие линии для пнрнмещаемого эллипса
                if (ml.getText2().equals(mEll.getText()))  ml.setLine( findEll(ml.getText1()), mEll);
            }
            //добавить описание действия 
            //jLabel1.setText("mouse Drag="+e.getPoint().getX()+","+e.getPoint().getY()+"размер окна: "+MyJFrame.windowTaks.height+" "+MyJFrame.windowTaks.width);
        }
    }
  
 /**Ищем эллипс, которому принадлежит данный текст.
 * Просматриваем элементы массивов:
 * myTaksArray, 
 * myTaksArrayToIn
 * если да - возвращаем фигуру Ellipse2D        
 * иначе - возвращаем null
     * @return  */
    public MyEll findEll(String textEll) {
        //point2D p = new Point2D();
        if(textEll.length()==0)return null;
        for (int i=0;i<myTaksArray.size();i++){
            MyEll klass= myTaksArray.get(i).getKlassEll();
            if (klass.getText().trim().equals(textEll.trim())) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return klass;
            }
            for(int j=0;j<myTaksArray.get(i).getElemEllArray().getEllArray().size();j++){
            MyEll elem= myTaksArray.get(i).getElemEllArray().getEllArray().get(j);
            if (elem.getText().trim().equals( textEll.trim())) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return elem;
            }
            }
            
        }
        for (int i=0;i<myTaksArrayToIn.size();i++){
            MyEll e= myTaksArrayToIn.get(i);
            if (e.getText().trim().equals(textEll.trim())) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return e;
            }
        }
        
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+" null");
        return null;
    }
  /**В массиве myEllArray Ищем эллипс, которому принадлежит данный текст String textEll.
 * Просматриваем элементы массива: myEllArray
 *  * если да - возвращаем фигуру MyEll        
 * иначе - возвращаем null
 * @autor 655 29.06.2016/
     * @param myEllArray
     * @param textEll
     * @return  */
    public MyEll findEll(ArrayList <MyEll> myEllArray, String textEll) {
        //point2D p = new Point2D();
        if(textEll.length()==0)return null;
        if(myEllArray.size()<1)return null;
        for (MyEll ell: myEllArray){
            if (ell.getText().trim().equals(textEll.trim())) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return ell;
            }
        }
        return null;
    }
    /**В массиве myEllArray Ищем эллипс, которому принадлежит данный текст String textEll.
 * Просматриваем элементы массива: myEllArray
 *  * если да - возвращаем фигуру MyEll        
 * иначе - возвращаем null
 * @autor 655 29.06.2016/
     * @param myEllArray
     * @param textEll
     * @return  */
    public boolean findEllB(ArrayList <MyEll> myEllArray, String textEll) {
        //point2D p = new Point2D();
        if(textEll.length()==0)return false;
        if(myEllArray.size()<1)return false;
        for (MyEll ell: myEllArray){
            if (ell.getText().trim().equals(textEll.trim())) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                return true;
            }
        }
        return false;
    }
    /**Узнаем количество в массиве myLineArray линий, которые начинаются от эллипса с текстом String textEll.
 * Просматриваем элементы массива: myEllArray
     * @param myLineArray
 * @autor 655 29.06.2016/
     * @param textEll
     * @return  */
    public int countLineText1(ArrayList <MyLine> myLineArray, String textEll) {
        //point2D p = new Point2D();
        int count=0;
        if(textEll.length()==0)return 0;
        if(myLineArray.size()<1)return 0;
        for (MyLine line: myLineArray){
            if (line.getText1().trim().equals(textEll.trim())) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                count++;
            }
        }
        return count;
    }
    /**Узнаем количество в массиве myLineArray линий, которые заканчиваются на эллипс с текстом String textEll.
 * Просматриваем элементы массива: myEllArray
     * @param myLineArray
 * @autor 655 29.06.2016/
     * @param textEll
     * @return  */
    public int countLineText2(ArrayList <MyLine> myLineArray, String textEll) {
        //point2D p = new Point2D();
        int count=0;
        if(textEll.length()==0)return 0;
        if(myLineArray.size()<1)return 0;
        for (MyLine line: myLineArray){
            if (line.getText2().trim().equals(textEll.trim())) {
                //System.out.println("MyPanel public MyEll findEll(Point2D p)"+e);
                count++;
            }
        }
        return count;
    }
    /**Возвращает отступ между элипсом класса и элемента отношения
     * @return  */
    public int getW(){
    return w;
    }
    
    /**Возвращает размеры рисунк
     * @return а*/
    public Dimension getWindowSize(){
        return windowSize;
    }
    
    /**Перепривязывает имеющиеся линии от- и к= эллипсу.
     * Когда положение эллипса изменилось, а текст эллипса не изменился.
     * Ищет в БД линий все линии от- и к- эллипсу с таким текстом и перепривязывает линии по новым координатам.
     * @param ell.*/
    public void renewEllLine(MyEll ell){
        //если массив линий имеет элементы
        if(!myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                MyLine ml= myLineArray.getLineArray().get(i);
                //переназначаем исходящие линии для перемещаемого эллипса
                if (ml.getText1().contains(ell.getText())) ml.setLine(ell,  findEll(ml.getText2()));
                //переназначаем входящие линии для пнрнмещаемого эллипса
                if (ml.getText2().contains(ell.getText()))  ml.setLine( findEll(ml.getText1()), ell);
            }
            //добавить описание действия 
            //jLabel1.setText("mouse Drag="+e.getPoint().getX()+","+e.getPoint().getY()+"размер окна: "+MyJFrame.windowTaks.height+" "+MyJFrame.windowTaks.width);
        }
    
    }
    /**Присвоим тексту в эллипсе новый текст.
     * Подгоним размер эллипса под размер нового текста.
     * Эллипс имеет линии. Значит он из массива myTaksArray.
       Эллипс не имеет линий. Значит он из массива myTaksArrayToIn.
     * Перепривяжем линии к/от старого эллипса к новому эллипсу.
     * Ищет в БД линий все линии от- и к- эллипсу со старым текстом и перепривязывает линии к эллипсу с новым текстом.
     * @param ell.
     * @param oldText*/
    public void renewEllTextAndLine(MyEll ell, String oldText){
        //если массив линий имеет элементы
        if(!myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий
            for (int i=0;i<myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                MyLine ml= myLineArray.getLineArray().get(i);
                //если линия имеет одинаковый начальный и конечный тексты, удалим ее из массива линий
                
                //переназначаем исходящие линии для перемещаемого эллипса
                if (ml.getText1().equals(oldText)) ml.setLine(ell,  findPaintEll(ml.getText2()));
                //переназначаем входящие линии для пнрнмещаемого эллипса
                if (ml.getText2().equals(oldText))  ml.setLine( findPaintEll(ml.getText1()), ell);
            }
            //добавить описание действия 
            //jLabel1.setText("mouse Drag="+e.getPoint().getX()+","+e.getPoint().getY()+"размер окна: "+MyJFrame.windowTaks.height+" "+MyJFrame.windowTaks.width);
        }
    
    }
    /**если линия имеет одинаковый начальный и конечный тексты, удалим ее из массива линий*/
    public void removeErrorLine(){
        if(!myLineArray.getLineArray().isEmpty()){
            boolean nonStop= true;
            while(nonStop){
                for(MyLine myLine: myLineArray.getLineArray()){
                    if(myLine.getText1().equals(myLine.getText2())){
                        this.myLineArray.removeMyLine(myLine);
                        nonStop= true;
                        break;
                    }
                }
                nonStop=false;
            }
            
        }
    }
    /**Добавим эллипс на экран, проверив, что эллипса с таким же текстом на экране нет. 
     * @param myEll.*/
    public void addPaintTaksEll(MyEll myEll){
        //Если эллипс с таким же текстом уже есть на экране, сообщим об этом и добавлять его не будем
        if(findPaintEllB(myEll.getText())){
            //JOptionPane.showMessageDialog(null, "Объект с именем "+myEll.getText()+" уже присутствует на экране.");
            
            //собственно перемещение эллипса на новые координаты
            moveEllandLine(myEll.getX(), myEll.getY(),findPaintEll(myEll.getText()));           
            return;
        }
        this.paintTaksEll=unique(this.paintTaksEll,myEll);
        
        
    }
    /**переместим эллипс и связанные с ним линиии на новые координаты
     * @param draggX
     * @param draggY
     * @param makeEll*/
    public void moveEllandLine(double draggX, double draggY, MyEll makeEll){
        //собственно перемещение эллипса
                        //if()
                        makeEll.setFrame(draggX, draggY, makeEll.getWidth(), makeEll.getHeight());
                        
                        if (!getMyLineArray().getLineArray().isEmpty()) {
                            //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
                            for (int i = 0; i < getMyLineArray().getLineArray().size(); i++) {
                                //перебираем все линии массива линий
                                MyLine2 ml = (MyLine2) getMyLineArray().getLineArray().get(i);
                                //переназначаем исходящие линии для перемещаемого эллипса .contains - стреляет неточно.
                                //if (ml.getText1().contains(makeEll.getText())) ml.setLine(makeEll, (MyEll) myProject.getMyTaksArray().findEll(ml.getText2()));
                                //переназначаем исходящие линии для перемещаемого эллипса
                                if (ml.getText1().equals(makeEll.getText())) {
                                    ml.setLine(makeEll,  findPaintEll(ml.getText2()));
                                }
                                //переназначаем входящие линии для пнрнмещаемого эллипса
                                if (ml.getText2().equals(makeEll.getText())) {
                                    ml.setLine( findPaintEll(ml.getText1()), makeEll);
                                }
                            }
                            //добавить описание действия 
                            //jLabel1.setText("mouse Drag="+e.getPoint().getX()+","+e.getPoint().getY()+"размер окна: "+MyJFrame.windowTaks.height+" "+MyJFrame.windowTaks.width);
                        }
    }
    
    /**Получим эллипсы с экрана
     * @return .*/
    public ArrayList <MyEll> getPaintTaksEll(){
        return paintTaksEll;
    }
    /**Найдем эллипс на экране которому принадлежит данная точка.
     * @param point
     * @return null - если эллипс на экране не найден. MyEll - если эллипс на экране найден.*/
    public MyEll findPaintEll (Point2D point){
        for(int i=0;i<paintTaksEll.size();i++){
            MyEll ell= paintTaksEll.get(i);
            if(ell.contains(point)){
                return ell;
            }
        }
        return null;
    }
    /**Проверим, есть ли эллипс на экране которому принадлежит данная точка.
     * Скрытые эллипсы не учитываются
     * @param point
     * @return null - если эллипс на экране не найден. MyEll - если эллипс на экране найден.*/
    public boolean findPaintEllB (Point point){
        for(int i=0;i<paintTaksEll.size();i++){
            MyEll ell= paintTaksEll.get(i);
            if(ell.contains(point.getX(),point.getY())){
                return true;
            }
        }
        return false;
    }
    
    /**Найдем эллипс на экране
     * @param text.
     * @return */
    public MyEll findPaintEll (String text){
        for(int i=0;i<paintTaksEll.size();i++){
            MyEll ell= paintTaksEll.get(i);
            if(ell.getText().trim().equals(text.trim())){
                return ell;
            }
        }
        return null;
    }
    /**Проверим или есть эллипс на экране
     * @param text?
     * @return */
    public boolean findPaintEllB (String text){
        for(int i=0;i<paintTaksEll.size();i++){
            MyEll ell= paintTaksEll.get(i);
            if(ell.getText().trim().equals(text.trim())){
                return true;
            }
        }
        return false;
    }
    /**Удалим эллипс с экрана
     * @param ell.*/
    public void removePaintEll(MyEll ell){
        for(int i=0;i<paintTaksEll.size();i++){
            MyEll e= paintTaksEll.get(i);
            if(ell.equals(e)){
                paintTaksEll.remove(i);
            }
        }
    }
    /**Занесем имеющиеся отношения Проекта в массив рисования элементов Проекта.
     Если эллипс с текстом уже присутствует в массиве рисования, 
     * то двойника в массив рисования не добавляем.*/
    public void importToPaintEll(){
        //Ниже рисуем элипсы из массива таксономий
        for (int ti=0; ti< myTaksArray.size();ti++){
            //достаем Класс объекта Таксономическое отношение
            MyEll klassEll=myTaksArray.get(ti).getKlassEll();
            //Добавим собственно эллипс Класса, эсли элипса с таким текстом нет.
            if(!this.findPaintEllB(klassEll.getText())){
                paintTaksEll.add(klassEll);
            }
            //currentRectText=null;
            //Добавляем элементы Класса Таксономического отношения
            for (int tik=0; tik< myTaksArray.get(ti).getElemEllArray().getEllArray().size();tik++){
                //достаем элемент Класса объекта Таксономическое отношение
                MyEll elemEll=myTaksArray.get(ti).getElemEllArray().getEllArray().get(tik);
                //Добавим собственно эллипс Эемента отношения,
                //если эллипса с таким текстом в массиве рисования нет.
                if(!this.findPaintEllB(elemEll.getText())){
                    paintTaksEll.add(elemEll);
                }
                //currentRectText=null;
            }

        }
    
    }
    /**Занесем имеющиеся отношения Проекта в массив рисования элементов Проекта.
     Если эллипс с текстом уже присутствует в массиве рисования, 
     * то двойника в массив рисования не добавляем.*/
    public void importToPaintTaksArrayToIn(){
        //Ниже рисуем элипсы из массива таксономий
        for (int ti=0; ti< this.myTaksArrayToIn.size();ti++){
            //достаем Класс объекта Таксономическое отношение
            MyEll ell=myTaksArrayToIn.get(ti);
            //Добавим собственно эллипс Класса, эсли элипса с таким текстом нет.
                paintTaksEll.add(ell);
            
           
        }
    
    }
    /**Найдем эллипс среди классов отношений
     * @param text.
     * @return */
    public MyEll foundMyTaksArrayKlass (String text){
        for(int i=0;i<myTaksArray.size();i++){
            MyEll ell= myTaksArray.get(i).getKlassEll();
            if(ell.getText().equals(text)){
                return ell;
            }
        }
        return null;
    }
    /**Проверим или есть эллипс среди классов отношений
     * @param text?
     * @return */
    public boolean foundMyTaksArrayKlassB (String text){
        for(int i=0;i<myTaksArray.size();i++){
            MyEll ell= myTaksArray.get(i).getKlassEll();
            if(ell.getText().equals(text)){
                return true;
            }
        }
        return false;
    }
    /**Найдем эллипс среди элементов отношений myTaksArray
     * @param text.
     * @return */
    public MyEll findEllInMyTaksArrayElement (String text){
        for(int i=0;i<myTaksArray.size();i++){
            for(int j=0; j<myTaksArray.get(i).getElemEllArray().getEllArray().size();j++){
                MyEll ell= myTaksArray.get(i).getElemEllArray().getEllArray().get(j);
                if(ell.getText().equals(text)){
                    return ell;
                }
            }
        }
        return null;
    }
    /**Проверим или есть эллипс среди классов отношений
     * @param text?
     * @return */
    public boolean findEllInMyTaksArrayElementB (String text){
        for(int i=0;i<myTaksArray.size();i++){
            for(int j=0; j<myTaksArray.get(i).getElemEllArray().getEllArray().size();j++){
                MyEll ell= myTaksArray.get(i).getElemEllArray().getEllArray().get(j);
                if(ell.getText().equals(text)){
                    return true;
                }
            }
        }
        return false;
    }
    /**Удаляетем линию.
     Сначала удаляем линию с экрана.
     Т.к. линия создает отношение, 
     * проверяем, или текст класса отношения, созданного линией является элементом другого отношения.
     * Если да, то ничего с ним не делаем.
     * Иначе, переводим эллипс класса в массив нераспределенных эллипсов.
     * Проверяем, или текст элемента отношения является ли классом в другом отношении?
     * Если да, то ничего с ним не делаем.
     * Иначе, переводим эллипс элемента отношения в массив нераспределенных эллипсов.
     * @param deleteLine
     */
    public void removeLine(MyLine deleteLine){
        
        //Удаляем линию из массива линий.
        myLineArray.removeLine(deleteLine);
        
                
                
    }
    /**Изменяет цвет эллипса и линиий его и связанных с ним эллипсов 1-й очереди
     * @param myEll
     * @param color*/
   public void setColorPaintEll_Line_1Ell(MyEll myEll, Color color){
       myEll.setColorEll(color);//изменим цвет эллипсу
       setColorLineEll(myEll,color);//изменим цвет линий от него и к нему
       //изменим цвет эллипсов 1-й очереди
       //Ищем линии с текстом эллипса в начале или в конце линии. Присвоим им заданный цвет.
       for(int i=0;i<this.myLineArray.getLineArray().size();i++){
           MyLine line =this.myLineArray.getLineArray().get(i);
           //Ищем линии с текстом эллипса в начале или в конце линии. Присвоим им заданный цвет.
           if(line.getText1().equals(myEll.getText())){
               line.setColorLine(color);
               findPaintEll(line.getText2()).setColorEll(color);//ищем еллипс 1-й очереди  по оставшемуся тексту другого эллипса линии и изменяем его цвет
           }
            if (line.getText2().equals(myEll.getText())){
                line.setColorLine(color);
                findPaintEll(line.getText1()).setColorEll(color);//ищем еллипс 1-й очереди  по оставшемуся тексту другого эллипса линии и изменяем его цвет
            }   
        }
       
    }
   /**Изменяет цвет эллипсам, которые нарисованы с текстом начала и конца линии 
     * @param line.
     * @param color*/
   public void setColorPaintEllLine(MyLine line, Color color){
       //Ищем эллипс с текстом начала линии. Присвоим ему заданный цвет.
       this.findPaintEll(line.getText1()).setColorEll(color);
       //Ищем эллипс с текстом конца линии. Присвоим ему заданный цвет.
       this.findPaintEll(line.getText2()).setColorEll(color);
    }
   /**Изменяет цвет линиям к и от эллипса
     * @param ell
     * @param color*/
   public void setColorLineEll(MyEll ell, Color color){
       //Ищем линии с текстом эллипса в начале или в конце линии. Присвоим им заданный цвет.
       for(int i=0;i<this.myLineArray.getLineArray().size();i++){
           MyLine line =this.myLineArray.getLineArray().get(i);
           if(line.getText1().equals(ell.getText())||line.getText2().equals(ell.getText())){
               line.setColorLine(color);
           }
       }
       
    }
    /**Выделим эллипс и его дерево на экране. Расширенный.
     Если эллипс не имеет дерева отношений, вернем null.
     * @autor 655 29.06.2016
     * @param myEll эллипс, чье дерево выделяем.
     * @return  вернем массив MyTaks , что есть поуровневое дерево от выбранного эллипса.*/
    public ArrayList<MyEll> makeEllTreeExtended(MyEll myEll){
        ArrayList <MyEll> makeAr= new ArrayList<MyEll>();//Итоговый массив.
        ArrayList <MyEll> makeArLevel= new ArrayList<MyEll>();//Промежуточный массив.
        //смотрим все линии, если линия входящая, эллипс-источник возьмем в массив.
        for(MyLine myLine: this.myLineArray.getLineArray()){
            if(myLine.getText2().equals(myEll.getText())){
                makeArLevel.add(this.findPaintEll(myLine.getText1()));
            }
        }
        //Итог.Собрали эллипсы 1-го уровня.
        //Добавим эллипсы 0-гог и 1-го уровней в массив эллипсов.
        makeAr.add(myEll);
        for(MyEll myEll2: makeArLevel){
            makeAr=this.unique(makeAr, myEll2);
        }        
        //Соберем 2-й и остальные уровни.
        while(makeArLevel.size()>0){
            ArrayList <MyEll> makeArLevelTemp= new ArrayList<MyEll>();//Промежуточный массив.
            for(MyEll myEll1 : makeArLevel){//для каждого эллипса предыдущего уровня
                for(MyLine myLine: this.myLineArray.getLineArray()){//ищем среди линий
                    if(myLine.getText2().equals(myEll1.getText())){//входящие линии
                        makeArLevelTemp.add(this.findPaintEll(myLine.getText1()));//добавим их
                    }
                }
            }
            for(MyEll myEll2: makeArLevel){
                makeAr=this.unique(makeAr, myEll2);
            }
            makeArLevel=makeArLevelTemp;
        }
        //Итог. Нашли и сохранили level-й уровень дерева отношений от выбраннго эллипса.
        
        return makeAr;
   }//public ArrayList<MyEll> makeEllTreeExtended(MyEll myEll){
    /**Выделим эллипс и его дерево на экране. Строгий.
     Если эллипс не имеет дерева отношений, вернем null.
     * Формируем массив объектов расширенного дерева.
     * Формируем массив линий расширенного дерева.
     * Смотрим на каждый объект расширенного дерева.
     *  Смотрим на каждую входящую линию.
     *      Если линия выходит из объекта расширенного дерева.
     *  Если все входящие линии начинаются в пределах дерева, проверим исходящие линии.
     *      Смотрим на каждую исходящую линию.
               Если объект, куда она попадает, это объект дерева,
     *  Если все выходящие линии заканчиваются в пределах Расширенного дерева,
     *      берем рассматриваемый объект в Строгое дерево.
     * 
     * 
     * @autor 655 29.06.2016
     * @param myEll эллипс, чье дерево выделяем.
     * @return  вернем массив MyTaks , что есть поуровневое дерево от выбранного эллипса.*/
    public ArrayList<MyEll> makeEllTreeStrict(MyEll myEll){
        //Создадим массив строгого дерева.
        ArrayList <MyEll> makeArStr =new ArrayList<MyEll>();//Итоговый массив.
        //Создадим массив исключенных из строгого дерева.
        //ArrayList <MyEll> makeArStrErr =new ArrayList();//Исключенные из итогового массива.
        //Сформируем массив расширенного дерева.
        ArrayList <MyEll> makeArExt= this.makeEllTreeExtended(myEll);
        //Итог. Получили данные Эллипсов makeArExt расширенного дерева.
        //Надо. Сформируем из них Строгое дерево.
        //Добавим вершину дерева в массив строгого дерева.
        makeArStr.add(myEll);
        //Для каждого элемента расширенного дерева, кроме вершины дерева:
        //for(int i=1;i<makeArExt.size()-1;i++){
          //  MyEll myEllExt = makeArExt.get(i);
            
        for(MyEll myEllExt: makeArExt){    
            //Из массива линий сформируем массив линий, что приходят к елементу Расширенного дерева.
            ArrayList<MyLine> makeArLine2= this.myLineArray.findLineText2EllText(myEllExt);
            //Из массива линий дерева сформируем массив линий, что приходят к елементу Расширенного дерева.
            ArrayList <MyLine> myArLineEllIn= this.findLineText2EllText(makeArExt, myEllExt);
            //Если каждая входящая линия выходит из объекта расширенного дерева.
            //т.е. количество линий к эллипсу от объектов дерева не меньше общего количества линий к эллипсу.
            if(makeArLine2.size()==myArLineEllIn.size()){
                //Из массива линий сформируем массив линий, что выходят из елемента Расширенного дерева.
                ArrayList<MyLine> makeArLine1= this.myLineArray.findLineText1EllText(myEllExt);
                //Из массива линий дерева сформируем массив линий, что выходят из елемента Расширенного дерева.
                ArrayList <MyLine> myArLineEllOut= this.findLineText1EllText(makeArExt, myEllExt);
                //Если каждая выходящая линия попадает в объект расширенного дерева.
                //т.е. количество линий от эллипса к объектам дерева не меньше общего количества линий к эллипсу.
                if(makeArLine1.size()==myArLineEllOut.size()){
                    //если выходящие линии попадают только в эллипсы Строгого итогового дерева
                    if(!this.uniqueLineText2B(makeArStr, myArLineEllOut)){
                        //берем рассматриваемый объект в Строгое дерево.
                        makeArStr=this.unique(makeArStr, myEllExt);
                    }
                    
                    //если выходящие линии не попадают ни в один в эллипс из массива исключений деревьев Строгого дерева.
                    //if(this.uniqueLineText2B(makeArStrErr, myArLineEllOut)){
                        //берем рассматриваемый объект в Строгое дерево.
                    //    makeArStr=this.unique(makeArStr, myEllExt);
                    //}else{//берем рассматриваемый объект в массив исключенных деревьев из Строгого дерева.
                    //    makeArStrErr=this.unique(makeArStrErr, myEllExt);
                    //}
                    
                }
                //else{//берем рассматриваемый объект в массив исключенных деревьев из Строгого дерева.
                //    makeArStrErr=this.unique(makeArStrErr, myEllExt);
                //}
            }
        //Итог. Нашли и сохранили объекты строгого дерева отношений.
        }
        int n=0;
        for(MyEll ell:makeArStr){
            //System.out.println("makeArStr "+n+++" "+ell.getAmount()+" "+ell.getText());
        }
        int n1=0;
        for(MyEll ell:makeArExt){
            //System.out.println("makeArExt "+n1+++" "+ell.getAmount()+" "+ell.getText());
        }
        return makeArStr;
    }//public ArrayList<MyEll> makeEllTreeStrict(MyEll myEll){
    
    /**Ищем все линии, которые заканчиваются на эллипсе  MyEll ellk.
     * а начинаются из элементов массива дерева эллипса ellArray1.
     * Поиск проводим в массиве линий по тексту эллипса.
     * @param ellArray1  массив эллипсов дерева.
     * @param ellk эллипс, на котором заканчиваются линии.
     * @return массив линий, что начинаются из эллипсов массива ellArray1 и заканчивают ся на эллипсе ellk.
     * @autor 655 29.06.2016
     */
    public ArrayList<MyLine> findLineText2EllText(ArrayList <MyEll> ellArray1, MyEll ellk){
        //if (k==null) return null;
        ArrayList <MyLine> inLineAr = new ArrayList <MyLine> ();
        //Ищем  входящие линии, если массив линий имеет элементы
        if(!this.myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий, пересчитываем входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                if ( this.myLineArray.getLineArray().get(i).getText2().equals(ellk.getText())){
                    //проверим, что ее начало в дереве отношений.
                    if(findEllB(ellArray1,this.myLineArray.getLineArray().get(i).getText1())){
                        //добавим линию к выходному массиву.
                        inLineAr.add(this.myLineArray.getLineArray().get(i));
                    }
                }
            }
        }
        return inLineAr;
    } //public ArrayList<MyLine> findLineText2EllText(ArrayList <MyEll> ellArray1, MyEll ellk){
    /**Ищем все линии, которые заканчиваются на эллипсах, с которых начинаются линии входящего массива линий lineLevel.
     * а начинаются из элементов массива дерева эллипса ellArray1.
     * Поиск проводим в массиве линий по тексту эллипса.
     * @param ellArray1  массив эллипсов дерева.
     * @param lineLevel массив линий высшего уровня дерева.
     * @return массив линий, что начинаются из эллипсов массива ellArray1 и заканчивают ся на эллипсе ellk.
     * @autor 655 29.06.2016
     */
    public ArrayList<MyLine> findLineText2LineArText1(ArrayList <MyEll> ellArray1, ArrayList <MyLine> lineLevel){
        if (lineLevel==null) return null;
        if (lineLevel.isEmpty()) return new  ArrayList<MyLine>() ;
        
        //создадим массив эллипсов, на которых должны закончиться линии, что мы ищем.
        ArrayList <MyEll> serchEllAr=new ArrayList<MyEll> ();
        for(MyLine line: lineLevel){
            MyEll ellS=this.findPaintEll(line.getText1());
            serchEllAr.add(ellS);
        }
        //создадим выходной массив линий.
        ArrayList <MyLine> inLineAr = new ArrayList<MyLine>();
        //для каждого эллипса массива добавим связанные эллипсы
        for(MyEll ellk: serchEllAr){//ellk эллипс, на котором заканчиваются линии.
            //Ищем  входящие линии, если массив линий имеет элементы
            if(!this.myLineArray.getLineArray().isEmpty()){
                //смотрим массив линий, пересчитываем входящие линии (с привязкой к тексту эллипса)
                for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                    //перебираем все линии массива линий
                    if ( this.myLineArray.getLineArray().get(i).getText2().equals(ellk.getText())){
                        //проверим, что ее начало в дереве отношений.
                        if(findEllB(ellArray1,this.myLineArray.getLineArray().get(i).getText1())){
                            //добавим линию к выходному массиву.
                            inLineAr.add(this.myLineArray.getLineArray().get(i));
                        }
                    }
                }
            }
        }
        return inLineAr;
    } //public ArrayList<MyLine> findLineText2EllText(ArrayList <MyEll> ellArray1, MyEll ellk){
    /**Ищем все линии, которые начинаются на эллипсе  MyEll k.
     * а заканчиваются на элементах массива дерева эллипса.
     * Поиск проводим в массиве линий по тексту эллипса.
     * @param ellArray массив эллипсов дерева.
     * @autor 655 29.06.2016
     * @param k
     * @return */
    public ArrayList <MyLine> findLineText1EllText (ArrayList <MyEll> ellArray, MyEll k){
        if (k==null) return null;
        ArrayList <MyLine> inLineAr = new ArrayList<MyLine>();
        //Ищем  входящие линии, если массив линий имеет элементы
        if(!this.myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий, пересчитываем входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                if ( this.myLineArray.getLineArray().get(i).getText1().equals(k.getText())){//если найдена линия с началом в эллипсе
                    if(findEllB(ellArray,this.myLineArray.getLineArray().get(i).getText2())){//проверим, что ее конец в дереве отношений.
                        //добавим линию к выходному массиву.
                        inLineAr.add(this.myLineArray.getLineArray().get(i));
                    }
                    
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
     * @param ellArray массив эллипсов дерева.
     * @autor 655 29.06.2016
     * @param k
     * @return */
    public ArrayList <MyLine> findLineText1Line (ArrayList <MyEll> ellArray, MyEll k){
        if (k==null) return null;
        ArrayList <MyLine> inLineAr = new ArrayList<MyLine> ();
        //Ищем  входящие линии, если массив линий имеет элементы
        if(!this.myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий, пересчитываем входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                if ( this.myLineArray.getLineArray().get(i).getText1().equals(k.getText())){//если найдена линия с началом в эллипсе
                    if(findEllB(ellArray,this.myLineArray.getLineArray().get(i).getText2())){//проверим, что ее конец в дереве отношений.
                        //добавим линию к выходному массиву.
                        inLineAr.add(this.myLineArray.getLineArray().get(i));
                    }
                    
                }
            }
        }
        if(inLineAr.size()>=0){
            return inLineAr;
        }
        return null;
    }
    /**Ищем все линии, которые начинаются на эллипсе  MyEll k.
     * а заканчиваются на элементах массива дерева эллипса.
     * Поиск проводим в массиве линий по тексту эллипса.
     * @param ellArray массив эллипсов дерева.
     * @param sk
     * @autor 655 29.06.2016
     * @return */
    public ArrayList <MyLine> findLineText1EllText1 (ArrayList <MyEll> ellArray, String sk){
        if (sk.length()<1) return null;
        ArrayList <MyLine> inLineAr = new ArrayList<MyLine>();
        //Ищем  входящие линии, если массив линий имеет элементы
        if(!this.myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий, пересчитываем входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                if ( this.myLineArray.getLineArray().get(i).getText1().equals(sk)){//если найдена линия с началом в эллипсе
                    if(findEllB(ellArray,this.myLineArray.getLineArray().get(i).getText2())){//проверим, что ее конец в дереве отношений.
                        //добавим линию к выходному массиву.
                        inLineAr.add(this.myLineArray.getLineArray().get(i));
                    }
                    
                }
            }
        }
        if(inLineAr.size()>=0){
            return inLineAr;
        }
        return null;
    }
    /**Ищем линию в масссиве линий таксономии, которая начинаются на эллипсе с текстом String sk.
     * а заканчиваются на любом из эллипсов массива ArrayList <MyEll> ellArray.
     * @param ellArray массив эллипсов дерева.
     * @param sk должен быть длинной 1 или больше символов.
     * @autor 655 29.06.2016
     * @return */
    public boolean findLineText1EllText1B (ArrayList <MyEll> ellArray, String sk){
        if (sk.length()<1) return false;
        //ArrayList <MyLine> inLineAr = new ArrayList();
        //Ищем  входящие линии, если массив линий имеет элементы
        if(!this.myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий, пересчитываем входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                if ( this.myLineArray.getLineArray().get(i).getText1().equals(sk)){//если найдена линия с началом в эллипсе
                    if(findEllB(ellArray,this.myLineArray.getLineArray().get(i).getText2())){//проверим, что ее конец в массиве ArrayList <MyEll> ellArray.
                        //такую линию нашли.
                        return true;
                    }
                    
                }
            }
        }
        
        return false;
    }
    
    /**Ищем линию в масссиве линий таксономии, которая начинаются на эллипсе с текстом String fk.
     * а заканчиваются на  эллипсе с текстом String sk.
     * @param fk текст первого эллипса
     * @param sk текст второго эллипса линии 
     * Тексты у эллипсов должен быть длинной 1 или больше символов.
     * @autor 655 29.06.2016
     * @return */
    public boolean findLineText1EllText1B (String fk, String sk){
        if (fk.length()<1&&sk.length()<1) return false;
        //ArrayList <MyLine> inLineAr = new ArrayList();
        //Ищем  входящие линии, если массив линий имеет элементы
        if(!this.myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий, пересчитываем входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                if ( this.myLineArray.getLineArray().get(i).getText1().equals(fk)){//если найдена линия с началом в эллипсе с текстом fk
                    if(this.myLineArray.getLineArray().get(i).getText2().equals(sk)){//и концом в эллипсе с текстом sk
                        //такую линию нашли.
                        return true;
                    }
                    
                }
            }
        }
        
        return false;
    }
    /**Ищем линию в масссиве линий таксономии, которая начинаются на эллипсе с текстом String fk.
     * а заканчиваются на  эллипсе с текстом String sk.
     * @param fk текст первого эллипса
     * @param sk текст второго эллипса линии 
     * Тексты у эллипсов должен быть длинной 1 или больше символов.
     * @autor 655 29.06.2016
     * @return найденную линию*/
    public MyLine2  findLineText1EllText1 (String fk, String sk){
        if (fk.length()<1&&sk.length()<1) return null;
        //ArrayList <MyLine> inLineAr = new ArrayList();
        // если массив линий имеет элементы
        if(!this.myLineArray.getLineArray().isEmpty()){
            //Ищем линии, массив линий
            for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                if ( this.myLineArray.getLineArray().get(i).getText1().equals(fk)){//если найдена линия с началом в эллипсе с текстом fk
                    if(this.myLineArray.getLineArray().get(i).getText2().equals(sk)){//и концом в эллипсе с текстом sk
                        //такую линию нашли.
                        return (MyLine2)this.myLineArray.getLineArray().get(i);
                    }
                    
                }
            }
        }
        
        return null;
    }
    /**Добавим эллипс myEll в массив myEllArr, если эллипса с таким текстом в массиве нет.
     * @param myEllArr
     * @param myEll
     * @return 
     @autor 655 29.06.2016
     */
    public ArrayList <MyEll> unique(ArrayList<MyEll> myEllArr, MyEll myEll){
        //ArrayList <MyEll> myEllArr= new ArrayList();
        boolean adding = true;
        if(!myEllArr.isEmpty()&&myEllArr.size()>0){
        
        
        for(int i=0;i<myEllArr.size();i++){
            //System.out.println(myEllArr.get(i).getText()+" .equals "+myEll.getText());
            if(myEllArr.get(i).getText().equals(myEll.getText())){
                adding=false;
            } 
        } 
        }
        if(adding)myEllArr.add(myEll);
       
        return myEllArr;
    }
    /**Вернем ИСТИНА, если эллипса с таким текстом в массиве myEllArr нет.
     * Вернем ЛОЖЬ, если эллипс с таким текстом в массиве есть.
     * @param myEllArr массив, где ищем эллипс с текстом myEllText.
     * @param myEllText текст, который ищем в массиве эллипсов myEllArr.
     * @return 
     @autor 655 29.06.2016
     */
    public boolean uniqueB(ArrayList<MyEll> myEllArr, String myEllText){
        //ArrayList <MyEll> myEllArr= new ArrayList();
        boolean adding = true;
        for(int i=0;i<myEllArr.size();i++){
            if(myEllArr.get(i).getText().equals(myEllText)){
                adding=false;
            } 
        }
        
        
        return adding;
    }
    /** если эллипса с текстом куда попадает хоть одна из линий в массиве myEllArr нет, Вернем ИСТИНА.
     *  если эллипс с таким текстом в массиве есть, Вернем ЛОЖЬ,.
     * @param myEllArr массив, где ищем эллипс с текстом myEllText.
     * @param lineAr массив линий, текст целей которых ищем в массиве эллипсов myEllArr. 
     * @return 
     @autor 655 29.06.2016
     */
    public boolean uniqueLineText2B(ArrayList<MyEll> myEllArr, ArrayList <MyLine> lineAr){
        //ArrayList <MyEll> myEllArr= new ArrayList();
        boolean adding = true;
        for(MyLine line: lineAr){
            for(int i=0;i<myEllArr.size();i++){
                if(myEllArr.get(i).getText().equals(line.getText2())){
                    adding=false;
                } 
            }
        }
        
        return adding;
    }
    /**Добавим линию myLine в массив myLineArr, если линии с таким текстом начала и конца в массиве нет.
     * @param myLineArr
     * @param myLine
     * @return 
     @autor 655 29.06.2016
     */
    public ArrayList <MyLine> uniqueMyLine(ArrayList<MyLine> myLineArr, MyLine myLine){
        boolean adding=true;
        for(MyLine myLine1:myLineArr){
            if(myLine1.getText1().trim().equals(myLine.getText1().trim())&&myLine1.getText2().trim().equals(myLine.getText2().trim())){
                adding=false;
            }
        }
        if(adding)myLineArr.add(myLine);
        return myLineArr;
    }
    /**Добавим линию myLine в массив myLineArr, если линии с таким текстом начала и конца в массиве нет.
     * Если такая линия есть, увеличим параметр количества линий.
     * @param myLineArr
     * @param myLine 
     @autor 655 29.06.2016
     */
    public void uniqueMyLinePlus(ArrayList<MyLine> myLineArr, MyLine myLine){
        boolean adding=true;//информирует, о необходимости добавить новую линию.
        for(MyLine myLine1:myLineArr){//Смотрим каждую линию Проекта
            //если тексты начала и конца линии совпадают
            if(myLine1.getText1().trim().equals(myLine.getText1().trim())&&myLine1.getText2().trim().equals(myLine.getText2().trim())){
                //то увеличим счетчик отношений, который находится на линии.
                //myLine1.getPriznakUrlAr().size();
                //myLine.getPriznakUrlAr().size();
                //if(!myLine.getPriznakUrlAr().isEmpty()){
        //        myLine1.addPriznakUrlAr(myLine.getPriznakUrlAr());
                
                adding=!myLine1.addPriznakUrlAr(myLine.getPriznakUrlAr());//т.к. увеличен счетчик отношений линии, новую линию добавлять нет необходимости.
        
            }
        
        }
        //if(adding)myLineArr.add(myLine);
        if(adding){//если среди линий проекта такой линии нет
            //присвоим объектам начала и конца линии эллипсы Проекта с текстами начала и конца линии
            myLine.setLine(this.findPaintEll(myLine.getText1()),this.findPaintEll(myLine.getText2()));
            //Добавим созданную линию в проект
            myLineArr.add(myLine);
        }
        
        //return myLineArr;
    }
    /**Удаляем эллипс и все его дерево (включая линии) с экрана.
     * @param myEll
     @autpr 655 29.06.2016
     */
   public void removePaintEllipseTree (MyEll myEll){
       //выделим строгое дерево от полученного эллипса на экране.
       ArrayList <MyEll> arEll= this.makeEllTreeStrict(myEll);
       for(MyEll myEll5: arEll){
           this.removePaintEllipse(myEll5);
           
       }
       
   }
   
   
    
    /**Сформируем общий массив входящих и выходящих линий каждого эллипса расширенного дерева.
     * @param myEllArr массив эллипсов расширенного дерева.
     * @return массив линий расширенного дерева
     @autor 655 29.06.2016
     */
    public ArrayList <MyLine> makeLineTree(ArrayList <MyEll> myEllArr){
        ArrayList <MyLine> makeLineAr= new ArrayList<MyLine>();//Выходной массив.
        //добавим в выходной массив все линии первого эллипса - вершину дерева.
        //соберем в массив выходящие линии.
        ArrayList <MyLine> lineArrOut=this.getMyLineArray().findLineText1EllText(myEllArr.get(0));
        //соберем в массив входящие линии
        ArrayList <MyLine> lineArrIn=this.getMyLineArray().findLineText2EllText(myEllArr.get(0));
        //добавим входящие линии в итоговый массив с контролем уникальности.
        for(MyLine line:lineArrIn){
            makeLineAr=this.uniqueMyLine(makeLineAr, line);
        }
        //добавим выходящие линии в итоговый массив с контролем уникальности.
        for(MyLine line:lineArrOut){
            makeLineAr=this.uniqueMyLine(makeLineAr, line);
        }
        
        //смотрим каждый элемент массива
        for(MyEll ell:myEllArr){
            //соберем в массив выходящие линии.
            lineArrOut=this.getMyLineArray().findLineText1EllText(ell);
            //соберем в массив входящие линии
            lineArrIn=this.getMyLineArray().findLineText2EllText(ell);
            //добавим входящие линии в итоговый массив с контролем уникальности.
            for(MyLine line:lineArrIn){
                makeLineAr=this.uniqueMyLine(makeLineAr, line);
            }
            //добавим выходящие линии в итоговый массив с контролем уникальности.
            for(MyLine line:lineArrOut){
                makeLineAr=this.uniqueMyLine(makeLineAr, line);
            }
        }
        return makeLineAr;
        
    }
   /**Сформируем общий массив входящих и выходящих линий каждого эллипса Строгого дерева.
    * В отличии от makeLineTreeExtended, здесь в выходной в массив включены лини от внешних элементов
     * @param myEllArr массив эллипсов строгого дерева.
     * @return массив линий строгого дерева
     @autor 655 29.06.2016
     
    public ArrayList <MyLine> makeLineTreeStrict(ArrayList <MyEll> myEllArr){
        ArrayList <MyLine> makeLineAr= new ArrayList<MyLine>();//Выходной массив.
        //добавим в выходной массив все линии первого эллипса - вершину дерева.
        ArrayList <MyLine> lineArrOut=this.getMyLineArray().findLineText1EllText(myEllArr.get(0));//соберем в массив выходящие линии.
        ArrayList <MyLine> lineArrIn=this.getMyLineArray().findLineText2EllText(myEllArr.get(0));//соберем в массив входящие линии
        for(MyLine line:lineArrIn){//добавим входящие линии в итоговый массив с контролем уникальности.
            makeLineAr=this.uniqueMyLine(makeLineAr, line);
        }
        for(MyLine line:lineArrOut){//добавим выходящие линии в итоговый массив с контролем уникальности.
            makeLineAr=this.uniqueMyLine(makeLineAr, line);
        }
        for(MyEll ell:myEllArr){//смотрим каждый элемент массива
            lineArrOut=this.getMyLineArray().findLineText1EllText(ell);//соберем в массив выходящие линии.
            lineArrIn=this.getMyLineArray().findLineText2EllText(ell);//соберем в массив входящие линии
            for(MyLine line:lineArrIn){//добавим входящие линии в итоговый массив с контролем уникальности.
                makeLineAr=this.uniqueMyLine(makeLineAr, line);
            }
            for(MyLine line:lineArrOut){//добавим выходящие линии в итоговый массив с контролем уникальности.
                makeLineAr=this.uniqueMyLine(makeLineAr, line);
            }
        }
        return makeLineAr;
    }
   */
    
    /**Сформируем массив отношений таксономии из линий и эллипсов, что есть на экране.
     * @param pOblast.*/
    public void renewMyTaksFromPaint(String pOblast){
        //удаляем старые данные из массива отношений проекта.
        this.myTaksArray.clear();
        //берем название предметной области с шапки проекта.
        //ищем эллипс текст которого совпадает с название предметной области с шапки проекта.
        MyEll el=this.findPaintEll(pOblast);
        if(el==null||this.myLineArray.findLineText1EllTextB(el)||!this.myLineArray.findLineText2EllTextB(el)){
            //если такого эллипса нет, или у этого эллипса есть хоть одна исходящая линия, 
            //или нет входящих линий, пусть Юзер разрулит ситуацию.
            System.out.println("если такого эллипса нет, "
                    + "или у этого эллипса есть хоть одна исходящая линия, "
                    + "или нет входящих линий, пусть Юзер разрулит ситуацию.");
        }
        //если такой эллипс есть и у него только входящие линии,
        //берем его как первую вершину и формируем расширенное дерево.
        ArrayList <MyEll> paintArEll = new ArrayList<MyEll>();
        paintArEll.add(el);
        //Добавляем к массиву дерева оставшиеся эллипсы.
        for(MyEll myEll: this.paintTaksEll){
            paintArEll=this.unique(paintArEll, myEll);
        }
        //Итог.Имеем массив эллипсов и массив линий. 
        //Надо получить из них массив отношений таксономии. Но сначала надо сформировать массив пар отношений.
        //Добавим пары в  массив отношений.
        OntoTaks ontoTaks;
        ArrayList <OntoTaks> ontoTaksAr= new ArrayList<OntoTaks>();//массив пар отношений.
        //если есть линии
        if(myLineArray.getLineArray().size()>0){
            //смотрим линии
            for(MyLine myLine:myLineArray.getLineArray()){
                //берем линию и смотрим на каждый ее сайт с признаком отношения
                if(myLine.getPriznakUrlAr().size()>0){
                    for(PriznakUrl priznakUrl: myLine.getPriznakUrlAr()){
                        //строим отношение.
                        ontoTaks=new OntoTaks(
                                    this.findPaintEll(myLine.getText2()), 
                                    this.findPaintEll(myLine.getText1()), 
                                    priznakUrl.get_priznak(), 
                                    priznakUrl.get_url(),"Таксономии");
                            //добавим его в массив отношений.
                            ontoTaksAr=uniqueMyLine(ontoTaksAr,ontoTaks);
                        }
                    }else{
                        //строим отношение.
                            ontoTaks=new OntoTaks(
                                    this.findPaintEll(myLine.getText2()),
                                    this.findPaintEll(myLine.getText1()), 
                                    "", 
                                    "","Таксономии");
                            //добавим его в массив отношений.
                            ontoTaksAr=uniqueMyLine(ontoTaksAr,ontoTaks);
                    }
                }
            }
       
        //преобразуем массив ontoTaksAr в массив проекта MyTaksArray/
        this.myTaksArray= convertOntoTaksArToMyTaksAr( ontoTaksAr);
    }
    
    /**Добавим класс в массив классов, если класса такими значениями полей в массиве нет.
     * @param ontoTaksAr
     * @param ontoTaks
     * @return 
     @autor 655 29.06.2016
     */
    public ArrayList <OntoTaks> uniqueMyLine(ArrayList <OntoTaks> ontoTaksAr, OntoTaks ontoTaks){
        int count=0;
        for(OntoTaks ontoTaks1:ontoTaksAr){
            if(ontoTaks1.getKlass().trim().equals(ontoTaks.getKlass().trim())
                    &&ontoTaks1.getElem().trim().equals(ontoTaks.getElem().trim())
                    &&ontoTaks1.getPriznak().trim().equals(ontoTaks.getPriznak().trim())
                    &&ontoTaks1.getUrl().trim().equals(ontoTaks.getUrl().trim())){
                count++;
            }
        }
        if(count==0)ontoTaksAr.add(ontoTaks);
        return ontoTaksAr;
    }
    /**Для метода public void renewMyTaksFromPaint(String pOblast)
     //преобразуем массив ontoTaksAr в массив проекта MyTaksArray
     */
    private ArrayList <MyTaks>  convertOntoTaksArToMyTaksAr(ArrayList <OntoTaks> ontoTaksAr){
        ArrayList <MyTaks> myTaksArrOnto= new ArrayList<MyTaks>();//выходной массив
        for(OntoTaks ontoTaks: ontoTaksAr){
            //если такого текста класса еще не было в массиве myTaksArray.
            if (!findKlassB(myTaksArrOnto,ontoTaks.getKlass())){
                //возьмем текст класса первого отношения массива.
                //соберем в промежуточный массив ArrayList <OntoTaks>  все отношения с этим классом из ontoTaksAr.
                ArrayList <OntoTaks> ontoTaksArKlass=new ArrayList<OntoTaks>();
                for(OntoTaks ontoTaksKlass: ontoTaksAr){
                    if(ontoTaksKlass.getKlass().equals(ontoTaks.getKlass())){
                        ontoTaksArKlass=this.uniqueMyLine(ontoTaksArKlass, ontoTaksKlass);

                    }
                }
                //добавим найденные отношения в массив MyTaks. Сначала Классы.
                MyTaks myTaks= new MyTaks();
                myTaks.setKlassEll(new MyEll(ontoTaks.getKlassX(),
                        ontoTaks.getKlassY(), 
                        ontoTaks.getKlass(), 
                        ontoTaks.getPriznak()));
                //Затем, для каждого члена массива ontoTaksArKlass, 
                //добавим в отношение myTaks элементы.
                for(OntoTaks ontoTaksKlass: ontoTaksArKlass){
                    myTaks.addToElemEllArray(new MyEll(ontoTaksKlass.getElemX(),
                        ontoTaksKlass.getElemY(), 
                        ontoTaksKlass.getElem(), 
                        ontoTaksKlass.getPriznak()
                    ));

                }
                myTaksArrOnto.add(myTaks);
            }
            
        }
        
        return myTaksArrOnto;
    }
    /**Для метода private ArrayList <MyTaks>  convertOntoTaksArToMyTaksAr(ArrayList <OntoTaks> ontoTaksAr)
     * Подсчитаем, сколько отношений с такими парами класс-элемент записано в массиве.*/
    private int countAmountOntoTaks(ArrayList <OntoTaks> ontoTaksArKlassAr,OntoTaks ontoTaks ){
        int count=0;
        for(OntoTaks ontoTaksKlass: ontoTaksArKlassAr){
            if(ontoTaksKlass.getKlass().equals(ontoTaks.getKlass())
                    &&ontoTaksKlass.getElem().equals(ontoTaks.getElem())){
                    count++;
                    
                }
            }
        return count;
    }
    /**Проверим, или есть в массиве отношение с данным текстом класса
     * @param myTaksArrOnto.
     * @param klass
     * @return */
    public boolean findKlassB(ArrayList <MyTaks> myTaksArrOnto, String klass){
        if(myTaksArrOnto.size()==0)return false;
        for(MyTaks myTaks: myTaksArrOnto){
            if(myTaks.getKlassEll().getText().equals(klass)){
            return true;
            }
        }
        return false;
    }
    /**Найдем в выходной массив эллипсы массива allEllAr, которые не имеют исходящих линий. 
     * Т.е. являются вершинами деревьев.
     * Добавим эллипс topTreeEll как первую вершину дерева.
     
     */
    private ArrayList  <MyEll> compareTopTree(ArrayList <MyEll> allEllAr, MyEll topTreeEll){
        ArrayList <MyEll> compareTopTreeAr= new ArrayList<MyEll>();
        //Сразу добавим основную вершину.
        compareTopTreeAr.add(topTreeEll);
        //Смотрим все эл., ищем у кого нет исходящих линий и добавляем их в массив.
        for(MyEll myEll:allEllAr){
            //найдем все линии, которые начинаются на эллипсе.
            ArrayList <MyLine> paintLineEllInAr = this.myLineArray.findLineText1EllText(myEll);
            //Если их нет, берем эллипс в массив вершин деревьев
            if(paintLineEllInAr.size()>0){
            compareTopTreeAr=this.unique(compareTopTreeAr, myEll);
            }
        }
        return compareTopTreeAr;
    }
    
    
    /**Собирает на экране дерево объектов к выбранной вершине.
     Все, что не относится к данному дереву, отодвигается.
     * расположим объекты на экране справа и вниз от выбранной вершины.
     * @param treeEllFirst вершина от которой строим дерево.
     */
    public void bildPaintTree(MyEll treeEllFirst){
        //- собираем расширенное дерево эллипсов с вершиной в выбранном эллипсе.
        ArrayList <MyEll> treeEll = makeEllTreeExtended(treeEllFirst);
        // соберем в массив линии расширенного дерева.
        //ArrayList <MyLine> treeLine = this.makeLineTree(treeEll);
        //расположим объекты на экране справа и вниз от выбранной вершины.
        //при расположении, выполняем сдвиги эллипсов, что перекрываются вниз. 
        //Проверим, что массив эллипсов не пуст.
        if(!treeEll.isEmpty()){
            //универсальный метод для всех уровней;
            int level=0;//флаг уровня
            double maxWidth= findMaxWidth(treeEll);//максимальная длинни эллипса
            //Создадим временный массив входящих линий 1-го уровня, что попадают в вершину.
            ArrayList <MyLine> lineLevelAr = this.findLineText2EllText(treeEll, treeEllFirst);
            while(lineLevelAr.size()>0){
                //первый эллипс будет расположен на след.уроне, вровень с вершинным эллипсом, следующий - ниже и т.д.
                double x= treeEllFirst.getX()+treeEllFirst.getWidth()+50;
                double y= treeEllFirst.getY();
                x=x+level*250;
                //if(level>1)x=x+maxWidth+50;
                //Для каждой линии временного массива вход.Линий 1-го уровня
                for(MyLine line:lineLevelAr){
                    //найдем в массиве дерева эллипс с текстом начала линии
                    MyEll workEll=this.findEll(treeEll, line.getText1());
                    if(maxWidth<workEll.getWidth())maxWidth=workEll.getWidth();
                    if(workEll==null){System.out.println("1368 [MyArrayTaks] Гм... А эллипс-то с текстом "+line.getText1()+" не нашли. =((");}
                    if(!line.equals(lineLevelAr.get(0))){//если это не первый эллипс уровня, т.е. не первая линия массива
                        //x= treeEllFirst.getX()+treeEllFirst.getWidth()+50;
                        y= y+workEll.getHeight()+10;
                    }
                    workEll.setFrame(x,y, workEll.getWidth(), workEll.getHeight());
                    //переместим и его линии
                    //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
                    for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                        //перебираем все линии массива линий
                        MyLine2 ml=(MyLine2) myLineArray.getLineArray().get(i);
                        //переназначаем исходящие линии для перемещаемого эллипса .contains - стреляет неточно.
                        //if (ml.getText1().contains(makeEll.getText())) ml.setLine(makeEll, (MyEll) myProject.getMyTaksArray().findEll(ml.getText2()));
                        //переназначаем исходящие линии для перемещаемого эллипса
                        if (ml.getText1().equals(workEll.getText())) ml.setLine(workEll,  findPaintEll(ml.getText2()));
                        //переназначаем входящие линии для пнрнмещаемого эллипса
                        if (ml.getText2().equals(workEll.getText()))  ml.setLine( findPaintEll(ml.getText1()), workEll);
                    }
                    //добавить описание действия 
                    //jLabel1.setText("mouse Drag="+e.getPoint().getX()+","+e.getPoint().getY()+"размер окна: "+MyJFrame.windowTaks.height+" "+MyJFrame.windowTaks.width);
                
                    //Итог. Мы разместили первый эллипс первого уровня в удобном месте.
                    //Дальше. Если удобное место было занято дркгим эллипсом, сдвинуть его и все другие перекрытые эллипсы.
                    //Сдвинем все перекрытые эллипсы вниз(по Y)
                    bildPaintTreeCompareY(workEll);
                
                    //сдвинем эллипс, на который поставили эллипс дерева  чуть ниже.
                    //y=y+treeEllFirst.getHeight();
                }
                //Создадим временный массив входящих линий низшего уровня, что попадают в вершины высшего уровня.
                lineLevelAr=findLineText2LineArText1(treeEll,lineLevelAr);
                level++;
                
            }
        }
        
    }
    /**Собирает на экране дерево объектов к выбранной вершине.
     Все, что не относится к данному дереву, отодвигается.
     * расположим объекты на экране справа и вниз от выбранной вершины.
     * @param treeEllFirst вершина от которой строим дерево.
     * @return по Х - позиция нижнего элемента дерева, а по Y -  позиция правого элемента дерева
     */
    public Dimension bildPaintTreePosition(MyEll treeEllFirst){
        Dimension bottomPosition= new Dimension(0,0);
        //- собираем расширенное дерево эллипсов с вершиной в выбранном эллипсе.
        ArrayList <MyEll> treeEll = makeEllTreeExtended(treeEllFirst);
        // соберем в массив линии расширенного дерева.
        //ArrayList <MyLine> treeLine = this.makeLineTree(treeEll);
        //расположим объекты на экране справа и вниз от выбранной вершины.
        //при расположении, выполняем сдвиги эллипсов, что перекрываются вниз. 
        //Проверим, что массив эллипсов не пуст.
        if(!treeEll.isEmpty()){
            //универсальный метод для всех уровней;
            int level=0;//флаг уровня
            double maxWidth= findMaxWidth(treeEll);//максимальная длинни эллипса
            //Создадим временный массив входящих линий 1-го уровня, что попадают в вершину.
            ArrayList <MyLine> lineLevelAr = this.findLineText2EllText(treeEll, treeEllFirst);
            while(lineLevelAr.size()>0){
                //первый эллипс будет расположен на след.уроне, вровень с вершинным эллипсом, следующий - ниже и т.д.
                double x= treeEllFirst.getX()+treeEllFirst.getWidth()+50;
                double y= treeEllFirst.getY();
                x=x+level*250;
                //if(level>1)x=x+maxWidth+50;
                //Для каждой линии временного массива вход.Линий 1-го уровня
                for(MyLine line:lineLevelAr){
                    //найдем в массиве дерева эллипс с текстом начала линии
                    MyEll workEll=this.findEll(treeEll, line.getText1());
                    if(maxWidth<workEll.getWidth())maxWidth=workEll.getWidth();
                    if(workEll==null){System.out.println("1368 [MyArrayTaks] Гм... А эллипс-то с текстом "+line.getText1()+" не нашли. =((");}
                    if(!line.equals(lineLevelAr.get(0))){//если это не первый эллипс уровня, т.е. не первая линия массива
                        //x= treeEllFirst.getX()+treeEllFirst.getWidth()+50;
                        y= y+workEll.getHeight()+10;
                    }
                    workEll.setFrame(x,y, workEll.getWidth(), workEll.getHeight());
                    //переместим и его линии
                    //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
                    for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                        //перебираем все линии массива линий
                        MyLine2 ml=(MyLine2) myLineArray.getLineArray().get(i);
                        //переназначаем исходящие линии для перемещаемого эллипса .contains - стреляет неточно.
                        //if (ml.getText1().contains(makeEll.getText())) ml.setLine(makeEll, (MyEll) myProject.getMyTaksArray().findEll(ml.getText2()));
                        //переназначаем исходящие линии для перемещаемого эллипса
                        if (ml.getText1().equals(workEll.getText())) ml.setLine(workEll,  findPaintEll(ml.getText2()));
                        //переназначаем входящие линии для пнрнмещаемого эллипса
                        if (ml.getText2().equals(workEll.getText()))  ml.setLine( findPaintEll(ml.getText1()), workEll);
                    }
                    //добавить описание действия 
                    //jLabel1.setText("mouse Drag="+e.getPoint().getX()+","+e.getPoint().getY()+"размер окна: "+MyJFrame.windowTaks.height+" "+MyJFrame.windowTaks.width);
                
                    //Итог. Мы разместили первый эллипс первого уровня в удобном месте.
                    //Дальше. Если удобное место было занято дркгим эллипсом, сдвинуть его и все другие перекрытые эллипсы.
                    //Сдвинем все перекрытые эллипсы вниз(по Y)
                    bildPaintTreeCompareY(workEll);
                
                    //сдвинем эллипс, на который поставили эллипс дерева  чуть ниже.
                    //y=y+treeEllFirst.getHeight();
                }
                if(bottomPosition.width<x){bottomPosition.setSize(x, bottomPosition.height);}
                if(bottomPosition.height<y){bottomPosition.setSize(bottomPosition.width, y);}
                
                //Создадим временный массив входящих линий низшего уровня, что попадают в вершины высшего уровня.
                lineLevelAr=findLineText2LineArText1(treeEll,lineLevelAr);
                level++;
                
            }
        }
        return bottomPosition;
    }
    /**Сдвинем все эллипсы, перекрытые данным эллипсом вниз(по Y).
     * @param workEll эллипс, который сдвигает остальные эллипсы по оси Y
    */
    public void bildPaintTreeCompareY(MyEll workEll){
        //выполним поиск и упорядочивание перекрытых эллипсов столько раз, сколько найдем хоть одно перекрытие.
        //для запуска цикла поиска перекрытий установим флаг в ИСТИНА.
        boolean compare = true;//флаг наличия перекрытия элипса эллипсом на экране.   
        while(compare){
            MyEll testEll=bildPaintEllCompare(workEll);//найдем эллипс, который оказался перекрытым эллипсом workEll
            if(testEll!=null){//Если удобное место было занято другим эллипсом,
                //сдвинем  его по Y.
                double test_x= workEll.getX();
                double test_y= workEll.getY()+workEll.getHeight()+10;
                testEll.setFrame(test_x,test_y, testEll.getWidth(), testEll.getHeight());
                //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
                for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                        //перебираем все линии массива линий
                        MyLine2 ml=(MyLine2) myLineArray.getLineArray().get(i);
                        //переназначаем исходящие линии для перемещаемого эллипса .contains - стреляет неточно.
                        //if (ml.getText1().contains(makeEll.getText())) ml.setLine(makeEll, (MyEll) myProject.getMyTaksArray().findEll(ml.getText2()));
                        //переназначаем исходящие линии для перемещаемого эллипса
                        if (ml.getText1().equals(testEll.getText())) ml.setLine(testEll,  findEll(ml.getText2()));
                        //переназначаем входящие линии для пнрнмещаемого эллипса
                        if (ml.getText2().equals(testEll.getText()))  ml.setLine( findEll(ml.getText1()), testEll);

                    //добавить описание действия 
                    //jLabel1.setText("mouse Drag="+e.getPoint().getX()+","+e.getPoint().getY()+"размер окна: "+MyJFrame.windowTaks.height+" "+MyJFrame.windowTaks.width);
                }
                compare = true;//флаг наличия перекрытия элипса эллипсом на экране.
            }else{
                compare = false;//флаг наличия перекрытия элипса эллипсом на экране.
            }

        }
    }
    
    /**метод, проверяет, что поставленный нами эллипс не пересекается с любым другим эллипсом.
     * @param workEll эллипс, который мы переместили.
     * @return если эллипс с экрана имеет общие точки с workEll, вернем другой эллитпс, иначе - вернем null.
    */
    public MyEll bildPaintEllCompare(MyEll workEll){
        //смотрим каждый эллипс на экране.
        for(MyEll ell: this.paintTaksEll){
            if(ell.getFrame().contains(workEll.getFrame())&&!ell.getText().equals(workEll.getText())){
                return ell;
            }
        }
        //если эллипс с экрана имеет общие точки с workEll, вернем ИСТИНА.  
        return null;
    }
    /**Добавим на экран новое отношение таксономии OntoTak. Либо увеличим счетчик повторений у существующего отношения.
     * При добавления отношения проверяем, Если эллипс с текстом Класса уже есть на экране, ведем линию от него.
     * А если на экране и эллипс с текстом Элемента и между ними установлена связь, то увеличиваем счетчик количества связей на 1
     * @param ontoTaks.*/
    public void addPaintOntoTaks(OntoTaks ontoTaks){
        //проверим, есть ли на экране линия между эллипсами с текстом Элемента к Классу.
        if(this.findLineText1EllText1B(ontoTaks.getEllElem().getText(), ontoTaks.getEllKlass().getText())){
            //увеличим счетчик отношений элемента найденного отношения .
            this.findLineText1EllText1(ontoTaks.getEllElem().getText(), ontoTaks.getEllKlass().getText()).getPriznakUrlAr().add(new PriznakUrl());
        }else{
            //проверим, есть ли на экране линия между эллипсами с текстом  Класса к Элементу, т.е. противоположного направления.
            if(this.findLineText1EllText1B(ontoTaks.getEllKlass().getText(),ontoTaks.getEllElem().getText() )){
                //пропустим это отношение
                return;
            }
            //проверим, если текст начала линии совпадает с текстом конца линии.
            if(ontoTaks.getEllKlass().getText().equals(ontoTaks.getEllElem().getText() )){
                //пропустим это отношение
                return;
            }
            
            //Добавим на экран пару нового отношения.
            this.paintTaksEll=unique(this.paintTaksEll,ontoTaks.getEllKlass());
            this.paintTaksEll=unique(this.paintTaksEll,ontoTaks.getEllElem());
            //поищем на экране второй эллипс линии и поиспользуем его.
            MyEll firstEll;
            //проверим, есть ли на экране эллипс с текстом Класса отношения
            if(this.findPaintEllB(ontoTaks.getEllElem().getText())){
                //если на экране есть эллипс с таким текстом, то первым эллипсом линии будет он.
                firstEll=this.findPaintEll(ontoTaks.getEllElem().getText());            
            }else{
                firstEll=ontoTaks.getEllElem();
            }
             //поищем на экране второй эллипс линии и поиспользуем его.
            MyEll secondEll;
            //проверим, есть ли на экране эллипс с текстом Класса отношения
            if(this.findPaintEllB(ontoTaks.getEllKlass().getText())){
                //если на экране есть эллипс с таким текстом, то вторым эллипсом линии будет он.
                secondEll=this.findPaintEll(ontoTaks.getEllKlass().getText());            
            }else{
                secondEll=ontoTaks.getEllKlass();
            }
            this.myLineArray.setLineArray(uniqueMyLine(this.myLineArray.getLineArray(), new MyLine2(firstEll,secondEll) ));
            
            
        }
        System.out.println("");
        
        //this.myLineArray.getLineArray().add(new MyLine2(ontoTaks.getEllElem(),ontoTaks.getEllKlass()));
    }
 /***/
    private double findMaxWidth(ArrayList <MyEll> ellArray){
        double maxWidth=0;
        for(MyEll myEll: ellArray){
            if(myEll.getWidth()>maxWidth){
                maxWidth=myEll.getWidth();
            }
        }
        return maxWidth;
    }
     
    /**расположить объекты в виде деревьев*/
    public void ellPositionedTree() {
        //Найдем все эллипсы, что являются вершинами деревьев или одиночные эллипсы. 
        ArrayList <MyEll> ellTemp= new  ArrayList<MyEll>();
        for(MyEll myEll: getPaintTaksEll()){
            //Если у эллипса нет Выходящих линий,берем его в массив.
            if(!findLineText1EllText1B(getPaintTaksEll(), myEll.getText())){
                ellTemp.add(myEll);
            }
        }
        //расположим найденные вершины друг под дружкой
        double ell_x=ellTemp.get(0).getX();
        double ell_y=ellTemp.get(0).getY();
        Dimension dim= new Dimension();
        for(MyEll myEll:ellTemp){
            
            myEll.setFrame(ell_x, ell_y, myEll.getWidth(), myEll.getHeight());
            // ell_y = ell_y+50;
            dim= bildPaintTreePosition(myEll);
            ell_y = dim.height+50;
        }
        //Выстраиваем дерево каждой найденной вершины
        for(MyEll myEll:ellTemp){
         //- собираем расширенное дерево с вершиной в выбранном объекте.
                    //ArrayList <MyEll> treeEll = myProject.getMyTaksArray().makeEllTreeExtended(ferstTreeEll);
                    //- располагаем первый объект нижнего уровня дерева на линии с вершиной.
                    bildPaintTree(myEll);
                    //myProject.setResizeWindow(true);//подгоним размер экрана под расположенные данные
                    //repaint();
        } 
                
    }
/**расположить объекты в виде деревьев. Деревья ужаты по их вершинам. Подчиненные элементы находят друг на друга.*/
    public void ellPositionedTreeShort() {
        //Найдем все эллипсы, что являются вершинами деревьев или одиночные эллипсы. 
        ArrayList <MyEll> ellTemp= new  ArrayList<MyEll>();
        for(MyEll myEll: getPaintTaksEll()){
            //Если у эллипса нет Выходящих линий,берем его в массив.
            if(!findLineText1EllText1B(getPaintTaksEll(), myEll.getText())){
                ellTemp.add(myEll);
            }
        }
        //расположим найденные вершины друг под дружкой
        double ell_x=ellTemp.get(0).getX();
        double ell_y=ellTemp.get(0).getY();
        for(MyEll myEll:ellTemp){
            
            myEll.setFrame(ell_x, ell_y, myEll.getWidth(), myEll.getHeight());
            ell_y = ell_y+50;
        }
        //Выстраиваем дерево каждой найденной вершины
        for(MyEll myEll:ellTemp){
         //- собираем расширенное дерево с вершиной в выбранном объекте.
                    //ArrayList <MyEll> treeEll = myProject.getMyTaksArray().makeEllTreeExtended(ferstTreeEll);
                    //- располагаем первый объект нижнего уровня дерева на линии с вершиной.
                    bildPaintTree(myEll);
                    //myProject.setResizeWindow(true);//подгоним размер экрана под расположенные данные
                    //repaint();
        } 
                
    }
    public boolean isResizeWindow() {
        return resizeWindow;
    }

    public void setResizeWindow(boolean resizeWindow) {
        this.resizeWindow = resizeWindow;
    }
    /**Проверим, принадлежит ли данная точка квадрату Свернуть/Развернуть Эллипса.
     * @param point
     * @return null - если эллипс на экране не найден. MyEll - если эллипс на экране найден.*/
    public boolean findPaintEllDetailB (Point point){
        for(int i=0;i<paintTaksEll.size();i++){
            MyEll ell= paintTaksEll.get(i);
            if(ell.getDetail().contains(point.getX(),point.getY())){
                return true;
            }
        }
        return false;
    }
    /**Проверим, принадлежит ли данная точка квадрату Свернуть/Развернуть Эллипса.
     * @param point
     * @return null - если эллипс на экране не найден. MyEll - если эллипс на экране найден.*/
    public MyEll findPaintEllDetail (Point point){
        for(int i=0;i<paintTaksEll.size();i++){
            MyEll ell= paintTaksEll.get(i);
            if(ell.getDetail().contains(point.getX(),point.getY())){
                return ell;
            }
        }
        return null;
    }
    /**Метод собирает дерево объектов в его вершину MyEll myell и скрывает собранное дерево.
     * Скроем объекты дерева с вершиной в выбранном эллипсе.
     Для этого добавим к Эллипсам и линиям поле boolean isHide.
     Построит строгое дерево от выбранной вершины.
     Установим всем линиям и эллипсам строгого дерева поле boolean isHide в активное состояние.
     Добавим в метод, который рисует на экране обработку поля boolean isHide.
     Если у эллипса создано дерево, и скрыт хоть один объект, вернет true.
     Если у эллипса нет строгого дерева, вернем false
     Сбор выполним расшренного дерева.
     Скрываем только Строгое дерево.
     * @param myEll */
    public void makeHideTreeOn(MyEll myEll){
        //Построит расширенное  дерево эллипсов от выбранной вершины.
        ArrayList<MyEll> makeEllTreeExtended = makeEllTreeExtended(myEll);
        //Построим  дерево линий для расширенного дерева эллипсов от выбранного эллипса.
        ArrayList <MyLine> makeLineTreeExtended= makeLineTree(makeEllTreeExtended);
        //Скопируем эллипсы расширенного дерева в массив Скрытого дерева эллипса.
        myEll.getMyTaksArrayHide().getPaintTaksEll().addAll(makeEllTreeExtended);
        //Скопируем линии расширенного дерева в массив Скрытого дерева эллипса.
        myEll.getMyTaksArrayHide().getMyLineArray().getLineArray().addAll(makeLineTreeExtended);
        //Построим строгое дерево от выбранной вершины
        ArrayList<MyEll> makeEllTreeStrict = makeEllTreeStrict(myEll);
        //Построим дерево линий для Строгого дерева эллипсов.
        ArrayList<MyLine> makeLineTreeStrict = makeLineTree(makeEllTreeStrict);
        
        
        //Удалим из массива рисования экрана эллипсы и линии строгого дерева от выбранной вершины.
            makeEllTreeStrict.remove(0);//удалим вершину из выбранных эллипсов, чтоб оставить ее на экране.
            getPaintTaksEll().removeAll(makeEllTreeStrict);
        //Удалим из массива рисования экрана линии строгого дерева выбранной вершины
            //удалим их массива линий выходящие линии из вершины, чтоб остались ее связи на экране.
            boolean lineRemote=true;
            while(lineRemote){
                lineRemote=false;
                for(MyLine myLine:makeLineTreeStrict ){
                    if(myLine.getText1().equals(myEll.getText())){
                        makeLineTreeStrict.remove(myLine);
                        lineRemote=true;
                        break;
                    }
                }
            }
             //Удалим из массива рисования экрана линии строгого дерева выбранной вершины без выходящих линий вершины   
             getMyLineArray().getLineArray().removeAll(makeLineTreeStrict);
    }
    
    /**Метод разворачивает в дерево объектов из его вершины MyEll myell и стображает полученное дерево.
     Массив объектов скрытого дерева очищается
     Отображение выполняем с контролем дублирования линий и эллипсов. 
     Следует заметить, что добавляя линию, проверим, что такой линии на экране еще нет. 
     И сразу перерисуем линию, привязывая ее к текстам эллипсов начала и конца линии.
     И еще, если нет эллипса с текстом начала или конца линии, она не вставляется.*/
    public void makeHideTreeOff(MyEll myEll){
        //Добавим на экран скрытые эллипсы Дерева без первого эллипса - вершины дерева.
        if(myEll.getText().equals(myEll.getMyTaksArrayHide().getPaintTaksEll().get(0).getText())){
            myEll.getMyTaksArrayHide().getPaintTaksEll().remove(0);
        }
        getPaintTaksEll().addAll(myEll.getMyTaksArrayHide().getPaintTaksEll());
        //Добавим на экран скрытые линии Дерева.
        //getMyLineArray().getLineArray().addAll(myEll.getMyTaksArrayHide().getMyLineArray().getLineArray());
        for(MyLine myLine:myEll.getMyTaksArrayHide().getMyLineArray().getLineArray()){
            if(findPaintEllB(myLine.getText1())&&findPaintEllB(myLine.getText2())){//если эллипс с текстом начала линии и эллипс с текстом конца линии есть на экране
                myLine.setLine( findPaintEll(myLine.getText1()), findPaintEll(myLine.getText2()));//найдем кординаты эллипсов
                this.uniqueMyLine(getMyLineArray().getLineArray(), myLine);
            }
            
        }
        //Очистим массив скрытых эллипсов Дерева.
        myEll.getMyTaksArrayHide().getPaintTaksEll().clear();
        //Очистим массив скрытых линий Дерева.
        myEll.getMyTaksArrayHide().getMyLineArray().getLineArray().clear();
        
        
    }

    
    /**Для копирования экземпляра Класса в новый объект.*/
    @Override
    public MyTaksArray copyTo() {
        MyTaksArray myTaksArrayNew= new MyTaksArray();//создадим новый объект и скопируем в него все поля объекта этого класса
        if(this.myTaksArray!=null&&this.myTaksArray.size()>0){ //если в массиве есть объекты
            for(MyTaks myTaksOld:this.myTaksArray){
                MyTaks myTaksNew = myTaksOld.copyTo();
                myTaksArrayNew.myTaksArray.add(myTaksNew);
            }
        }
        if(this.myTaksArrayToIn!=null&&this.myTaksArrayToIn.size()>0){ //если в массиве есть объекты
            for(MyEll myEllOld:this.myTaksArrayToIn){
                MyEll myEllNew = myEllOld.copyTo();
                myTaksArrayNew.myTaksArrayToIn.add(myEllNew);
            }
        }
        myTaksArrayNew.myLineArray= myLineArray.copyTo();
        myTaksArrayNew.w= w;
        myTaksArrayNew.windowSize= windowSize;
       if(this.paintTaksEll!=null&&this.paintTaksEll.size()>0){ //если в массиве есть объекты
            for(MyEll myEllOld:this.paintTaksEll){
                MyEll myEllNew = myEllOld.copyTo();
                myTaksArrayNew.paintTaksEll.add(myEllNew);
            }
        }
        myTaksArrayNew.resizeWindow= resizeWindow;
         
        return myTaksArrayNew;
    }
    
    
    
}
