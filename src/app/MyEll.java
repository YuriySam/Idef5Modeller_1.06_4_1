 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Admin
 * Описывает графическую фигуру элипс.
 * Он имеет зарезервированные точки соединения с линиями. 
 * Для отрисовки MyLine1 
 * Эти точки собраны в массив.
 * Имеет слово вокруг которого рисуется.
 * 6/09/2015 Планирую дописать методы для отображения элипса на панели MyJFrame.
 
 */
public class MyEll extends Ellipse2D implements CopyTo{
    /**double x,y, w,h   координаты точки начала рисования эллипса, его высоты и ширины*/
    private double x,y, w,h;
    /**Основной текст Эллипса*/
    private String text;
    /**Текст признака. Для Элемента отношения Таксономии*/
    private String priznak;
    /**точки для соединения линиями*/
    private MyPoint1 P0,P1,P2,P3;
    /**объявляем и создаем масссив точек соединения линий*/
    private ArrayList <MyPoint1> MyEllPoints;
    /**Укажем, надо ли выделять цветом вигуру*/
    private Color colorEll;
    /**Уровень отношения в дереве выборки Предметной области.
     Используется и задается в зависимости от выборки, кот. формируется ситуационно.*/
    private int level;
    /**Кнопка для скрытия/отображения дерева элементов*/
    private Detail detail;
    //JToggleButton jToggleButton;
    /**Поле, значение которого показывает, отображать ли эллипс на екране, или нет, 
     * т.е. является ли этот эллипс скрытым, или нет.*/
    private boolean hide;
    /**массив объектов скрытого дерева*/
    private MyTaksArray myTaksArrayHide;
    
    
    
    
    /** объявление фигуры - эллипс */
    MyEll(){
        initComponent();
    }
    private void initComponent(){
        this.colorEll=Color.BLACK;
        this.MyEllPoints=new ArrayList<MyPoint1>(4);
        
        this.text="Класс/Элемент";
        this.priznak="";
        //jToggleButton= new JToggleButton() ;
        detail = new Detail(x, y, 17, 17, true);//элемент скрыть/отобразить дерево от текущего эллипса
        hide=false;//по-умолчанию все эллипсы отображаются на экране.
        myTaksArrayHide= new MyTaksArray();//создадим пустое скрытое дерево для этого эллипса.
    }
    

    /**длина, высота и точки соединения эллипса P0, P1, P2, P3
     * расчитывается в зависимости от длины и высоты текста text */
    MyEll(double x, double y, String text) {
        initComponent();
        setMyEll(x,y,text);    
    }
    /**длина, высота и точки соединения эллипса P0, P1, P2, P3
     * расчитывается в зависимости от длины и высоты текста text */
   // MyEll(double x, double y, String text, String priznak) {
    //    setMyEll(x,y,text);
   //     this.priznak=priznak;
   // }
    /**длина, высота и точки соединения эллипса P0, P1, P2, P3
     * расчитывается в зависимости от длины и высоты текста text */
    MyEll(double x, double y, String text, String priznak) {
        initComponent();
        setMyEll(x,y,text);
        this.priznak=priznak;
        
    }
/**длина, высота и точки соединения эллипса P0, P1, P2, P3
* расчитывается в зависимости от длины и высоты текста text
* значение переменной text - уникально, и состоит из 
* значение по-умолчанию + координата Х */
    MyEll(double x, double y) {
        initComponent();
        this.text=text+" "+x;
        setMyEll(x,y,text);
    }
/**Для копирования экземпляра Класса в новый объект.*/
    @Override
    public MyEll copyTo(){
        //создадим новый объект
        //скопируем в него поля выделенного объекта
        MyEll myEllNew=new MyEll();
        myEllNew.x=x;
        myEllNew.y=y;
        myEllNew.w=w;
        myEllNew.h=h;
        myEllNew.text=text;
        myEllNew.priznak=priznak;
        myEllNew.P0=new MyPoint1(P0.getX(),P0.getY());
        myEllNew.P1=new MyPoint1(P1.getX(),P1.getY());
        myEllNew.P2=new MyPoint1(P2.getX(),P2.getY());
        myEllNew.P3=new MyPoint1(P3.getX(),P3.getY());
        if(myEllNew.MyEllPoints.size()!=0){
            myEllNew.MyEllPoints.set(0, P0);
            myEllNew.MyEllPoints.set(1, P1);
            myEllNew.MyEllPoints.set(2, P2);
            myEllNew.MyEllPoints.set(3, P3);
        }else{
            myEllNew.MyEllPoints.add(0,P0);
            myEllNew.MyEllPoints.add(1,P1);
            myEllNew.MyEllPoints.add(2,P2);
            myEllNew.MyEllPoints.add(3,P3);
        }
        myEllNew.colorEll=colorEll;
        myEllNew.level=level;
        myEllNew.detail=detail.copyTo();
        myEllNew.hide=hide;
        
        //если в массиве скрытых эллипсов есть объекты, то для каждого из них выполним копирование
        if(getMyTaksArrayHide().getPaintTaksEll().size()>0){
            for(MyEll myEll:getMyTaksArrayHide().getPaintTaksEll() ){//возьмем каждый из них в массив скрытых объектов копируемого эллипса
                if(!text.equals(myEll.getText())){//если тексты эллипса не совпадает с текстом эллипса из массива скрытых объектов, добавим его
                    myEllNew.getMyTaksArrayHide().getPaintTaksEll().add(myEll.copyTo());
                }
            }
            //на 02/11/2016 
            //в массиве линий копируемого объекта при наличии скрытых эллипсов содержится ссылка на массив линий окна.
            //Надо: скопировать из него только те линии, в которых учавствуют скрытые эллипсы.
            //если в массиве скопированных линий есть линии, то возьмем их копии в массив скрытых объектов эллипса
            if(getMyTaksArrayHide().getMyLineArray().getLineArray().size()>0){
                //возьмем каждую из их в массив скрытых линий копируемого эллипса
                for(MyLine myLine2: getMyTaksArrayHide().getMyLineArray().getLineArray())
                //for(int i=0; i<getMyTaksArrayHide().getMyLineArray().getLineArray().size();i++)
                {
                    //MyLine2 myLine2 = (MyLine2) myEllNew.getMyTaksArrayHide().getMyLineArray().getLineArray().get(i);
                    for(MyEll myEllforLine:getMyTaksArrayHide().getPaintTaksEll() ){//смотрим каждый скрытый эллипс 
                        if (myEllforLine.getText().equals(myLine2.getText1())||myEllforLine.getText().equals(myLine2.getText2())){//если текст начала или конца линии совпадает с текстом скрытого эллипса, берем такую линию
                            MyLine2 myLineEll= (MyLine2) myLine2.copyTo();
                            //добавим созданный объект к массиву объектов для копирования
                            myEllNew.getMyTaksArrayHide().getMyLineArray().getLineArray().add(myLineEll);
                        }
                    }
                    
                    //myEllNew.getMyTaksArrayHide().getMyLineArray().getLineArray().add(myLine.copyTo());
                }
            }
            
            
        }
        //myEllNew.setMyTaksArrayHide(getMyTaksArrayHide().copyTo());
        //.getMyTaksArrayHide().copyTo();
        return myEllNew;
    }
/**Для копирования из экземпляра Класса в новый объект.*/
    public MyEll copyFrom(MyEll myEllFrom){
        //создадим новый объект
        //скопируем в него поля выделенного объекта
        MyEll myEllNew=new MyEll();
        myEllNew.x=myEllFrom.x;
        myEllNew.y=myEllFrom.y;
        myEllNew.w=myEllFrom.w;
        myEllNew.h=myEllFrom.h;
        myEllNew.text=myEllFrom.text;
        myEllNew.priznak=myEllFrom.priznak;
        myEllNew.P0=new MyPoint1(myEllFrom.P0.getX(),myEllFrom.P0.getY());
        myEllNew.P1=new MyPoint1(myEllFrom.P1.getX(),myEllFrom.P1.getY());
        myEllNew.P2=new MyPoint1(myEllFrom.P2.getX(),myEllFrom.P2.getY());
        myEllNew.P3=new MyPoint1(myEllFrom.P3.getX(),myEllFrom.P3.getY());
        if(myEllNew.MyEllPoints.size()!=0){
            myEllNew.MyEllPoints.set(0, P0);
            myEllNew.MyEllPoints.set(1, P1);
            myEllNew.MyEllPoints.set(2, P2);
            myEllNew.MyEllPoints.set(3, P3);
        }else{
            myEllNew.MyEllPoints.add(0,P0);
            myEllNew.MyEllPoints.add(1,P1);
            myEllNew.MyEllPoints.add(2,P2);
            myEllNew.MyEllPoints.add(3,P3);
        }
        myEllNew.colorEll=myEllFrom.colorEll;
        myEllNew.level=myEllFrom.level;
        myEllNew.detail=myEllFrom.detail.copyTo();
        myEllNew.hide=myEllFrom.hide;
        //если в массиве скрытых эллипсов есть объекты, то для каждого из них выполним копирование
        if(getMyTaksArrayHide().getPaintTaksEll().size()>0){
            //возьмем каждый из них в массив скрытых объектов копируемого эллипса
            for(MyEll myEll:getMyTaksArrayHide().getPaintTaksEll() ){
                myEllNew.getMyTaksArrayHide().getPaintTaksEll().add(myEll.copyFrom(myEll));
            }
            //если в массиве скопированных линий есть линии, то возьмем их копии в массив скрытых объектов эллипса
            if(getMyTaksArrayHide().getMyLineArray().getLineArray().size()>0){
                //возьмем каждую из их в массив скрытых линий копируемого эллипса
                for(MyLine myLine:myEllNew.getMyTaksArrayHide().getMyLineArray().getLineArray()){
                    //myEllNew.getMyTaksArrayHide().getMyLineArray().getLineArray().add(myLine.copyTo());
                }
                
            }
        }
        //myEllNew.setMyTaksArrayHide(getMyTaksArrayHide().copyTo());
        //.getMyTaksArrayHide().copyTo();
        
        return myEllNew;
    }
    
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    
    /**присвоение ширины эллипс
     * @param w*/
    public void setWidht(double w){
        this.w=w;
    }
    /**расчет ширины текста*/
    private double getWidhtText(String text){
        Dimension dm = GraphicText.getTextPxSize(new Font(Font.SANS_SERIF,Font.PLAIN,14), text);
        return dm.width; //ширина
        }
    /**Присваивает значение text. 
     Пересчитываем размеры и точки соединения эллипса
     * @param text.*/
    public void setText(String text) {
        
        this.setMyEll(x, y, text);
    }
    /**расчет высоты текста*/
    private double getHeightText(String text){
        Dimension dm = GraphicText.getTextPxSize(new Font(Font.SANS_SERIF,Font.PLAIN,14), text);
        return  dm.height*1.6;  //высота
        }
    /**расчет точки P0 (середина верхней стороны)  для соединения линий
     * @return  */
    public MyPoint getP0(){
        //P0=new MyPoint((x+w/2),y);
        return P0;
    }
    /**расчет точки P1 (середина правой стороны) для соединения линий
     * @return  */
    public MyPoint getP1(){
        //P1=new MyPoint((x+w),y+h/2);
        return P1;
    }
    /**расчет точки P2 (середина нижней стороны фигуры) для соединения линий
     * @return  */
    public MyPoint getP2(){
        //P2=new MyPoint((x+w/2),y+h);
        return P2;
    }
    /**расчет точки P3 (середина левой строны) для соединения линий
     * @return  */
    public MyPoint getP3(){
        //P3=new MyPoint(x,y+h/2);
        return P3;
    }
    /**возвращает MyEllPoints
     * @return  */
    public ArrayList<MyPoint1> getMyEllPoints(){
        return MyEllPoints;
    }    
    /**возвращает text
     * @return  */
    public String getText() {
        return text;
    }
    /**возвращает x
     * @return  */
    @Override
    public double getX() {
        return x;
    }
    /**возвращает y
     * @return  */
    @Override
    public double getY() {
        return y;
    }
    /**возвращает ширин
     * @return у*/
    @Override
    public double getWidth() {
        return w;
    }
    /**возвращает высот
     * @return у*/
    @Override
    public double getHeight() {
        return h;
    }
    /**не используетс
     * @return я*/
    @Override
    public boolean isEmpty() {
        if(this.text.length()==0)return true;
        return false;
    }  
    
    
    /**Присвоим эллипсу новые координат
     * @param x    
     * @param y    
     * @param w    
     * @param h*/    
    @Override
    public void setFrame(double x, double y, double w, double h) {
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.P0=new MyPoint1((x+w/2),y);
        this.P0.setPL1(x+w/2-7,y);//Место соединения первого крыла линии MyLine1.
        this.P0.setPL2(x+w/2+7,y);//Место соединения второго крыла линии MyLine1.      
        this.P1=new MyPoint1((x+w),y+h/2);
        this.P1.setPL1(x+w-3,y+h/2-7);//Место соединения первого крыла линии MyLine1.
        this.P1.setPL2(x+w-3,y+h/2+7);//Место соединения второго крыла линии MyLine1.      
        this.P2=new MyPoint1((x+w/2),y+h);
        this.P2.setPL1((x+w/2)-7,y+h);//Место соединения первого крыла линии MyLine1.
        this.P2.setPL2((x+w/2)+7,y+h);//Место соединения второго крыла линии MyLine1.      
        this.P3=new MyPoint1(x,y+h/2);
        this.P3.setPL1(x+3,y+h/2-6);//Место соединения первого крыла линии MyLine1.
        this.P3.setPL2(x+3,y+h/2+6);//Место соединения второго крыла линии MyLine1.    
        if(this.MyEllPoints.isEmpty()){
            this.MyEllPoints.add(P0);
            this.MyEllPoints.add(P1);
            this.MyEllPoints.add(P2);
            this.MyEllPoints.add(P3);
        }
        MyEllPoints.set(0, P0);
        MyEllPoints.set(1, P1);
        MyEllPoints.set(2, P2);
        MyEllPoints.set(3, P3);
        detail.setRect(x, y, w, h);
    }

    

/**не успользуется*/
    @Override
    public Rectangle2D getBounds2D() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

/**вывод всех данных (кроме массива точек соединения)по выбранному эллипсу*/
    @Override
    public String toString(){
        return getClass().getName()+"[ x= "+x+" y= "+y+" Height= "+h+" Weght= "
                +w+" Text= "+text+" P0="+P0+" P1="+P1+" P2="+P2+" P3="+P3+"]";
    }
/** выводит на экран описание эллипса (кроме массива точек соединения)*/
    public void printMyEll() {
        System.out.println(this.toString());
    }
/** выводит на экран описание эллипсов таксономического отношения (кроме массива точек соединения)*/
    public void printMyEll(MyEll klass, MyEll elem) {
        System.out.println("(Класс)"+klass.toString()+" (Элемент) "+elem.toString());
    }
    /**определение точек соединения фигуры (аргумента) и линий
     Вход: аргумент (MyEll f)
     Выход: массив точек  присоединения линий ArrayList =circleEllPoint= */
/*   public ArrayList circleEllPoints(){
        ArrayList circleEllPoints=new ArrayList();
        circleEllPoints.add(0,P0); //середина верхней стороны
        circleEllPoints.add(1, P1); //середина правой стороны
        circleEllPoints.add(2, P2); //середина нижней стороны фигуры
        circleEllPoints.add(3, P3); //середина левой строны

        return circleEllPoints;
    }
*/
    /**Присвоим значение полю признак.
     * @param priznak*/
    public void setPriznak(String priznak){
        this.priznak=priznak;
    }
    /**Возвратим значение поля признак.
     * @return */
    public String getPriznak(){
        return this.priznak;
    }
    /**Возвратим значение флага: Надо ли отмечать этот эллипс цветом*/
    public Color getColorEll(){
        return colorEll;
    }
    /**Присвоим значение флагу: Надо ли отмечать этот эллипс цветом*/
    public void setColorEll(Color colorEll){
    this.colorEll=colorEll;
    }
    /**длина, высота и точки соединения эллипса P0, P1, P2, P3
     * расчитывается в зависимости от длины и высоты текста text */
    private void setMyEll(double x, double y, String text) {
        
        this.x=x;
        this.y=y;
        this.text=text;
        this.h=getHeightText(text);//высота
        //расчитаем длину так, чтоб она была не меньше двух высот
        double tempW = getWidhtText(text)+5;//длина
        if(3*h>tempW){ this.w=3*h;}else{
            this.w=getWidhtText(text)+5;//длина
        }
        //jToggleButton.setBounds((int)x+60, (int)y+60, 17, 17);
        //detailButton.setBounds((int)x+30, (int)y+30, 17, 17);//Изменим позицию элнмента Скрыть/Обобразить дерево от текущего эллипса.
        detail.setRect(x, y, w, h);//элемент скрыть/отобразить дерево от текущего эллипса
        
        this.P0=new MyPoint1((x+w/2),y);
        this.P0.setPL1(x+w/2-7,y);//Место соединения первого крыла линии MyLine1.
        this.P0.setPL2(x+w/2+7,y);//Место соединения второго крыла линии MyLine1.      
        this.P1=new MyPoint1((x+w),y+h/2);
        this.P1.setPL1(x+w-3,y+h/2-7);//Место соединения первого крыла линии MyLine1.
        this.P1.setPL2(x+w-3,y+h/2+7);//Место соединения второго крыла линии MyLine1.      
        this.P2=new MyPoint1((x+w/2),y+h);
        this.P2.setPL1((x+w/2)-7,y+h);//Место соединения первого крыла линии MyLine1.
        this.P2.setPL2((x+w/2)+7,y+h);//Место соединения второго крыла линии MyLine1.      
        this.P3=new MyPoint1(x,y+h/2);
        this.P3.setPL1(x+3,y+h/2-6);//Место соединения первого крыла линии MyLine1.
        this.P3.setPL2(x+3,y+h/2+6);//Место соединения второго крыла линии MyLine1.      
        if(MyEllPoints.size()!=0){
        MyEllPoints.set(0, P0);
        MyEllPoints.set(1, P1);
        MyEllPoints.set(2, P2);
        MyEllPoints.set(3, P3);
        }else{
        MyEllPoints.add(0,P0);
        MyEllPoints.add(1,P1);
        MyEllPoints.add(2,P2);
        MyEllPoints.add(3,P3);
        }
        this.colorEll=Color.BLACK;
        
    }
    
    /**Рисуенм линию объекта класса MyEll
     * @param g2
     * @param myEll*/
    public void drawEll(Graphics2D g2){
        g2.setColor(Color.WHITE);
        g2.fill(this);
        //установим цвет для объекта
        g2.setColor(getColorEll());
        //g2.setColor(currentEll.getColorEll());
        //рисуем текст, отступая от начала эллипса
        g2.drawString(getText()
                    ,(int)getX()+4
                    ,(int)getY()+20);
        //рисуем собственно эллипс
        g2.draw(this);
        //g2.fill(this);
        //.draw(this.getDetail());
        
        this.getDetail().drawDetail(g2);//Кнопка для скрытия/отображения дерева элементов
        
    }
/**Рисуенм линию объекта класса MyEll
     * @param g2
     * @param myEll*/
    public void drawEllWithOutDetail(Graphics2D g2){
        g2.setColor(Color.WHITE);
        g2.fill(this);
        
        //установим цвет для объекта
        g2.setColor(getColorEll());
        //g2.setColor(currentEll.getColorEll());
        //рисуем текст, отступая от начала эллипса
        g2.drawString(getText()
                    ,(int)getX()+4
                    ,(int)getY()+20);
        //рисуем собственно эллипс
        g2.draw(this);
        //g2.fill(this);
        //.draw(this.getDetail());
        //this.getDetail().drawDetail(g2);//Кнопка для скрытия/отображения дерева элементов
        
    }
    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean isHide) {
        this.hide = isHide;
    }

    public MyTaksArray getMyTaksArrayHide() {
        return myTaksArrayHide;
    }

    public void setMyTaksArrayHide(MyTaksArray myTaksArrayHide) {
        this.myTaksArrayHide = myTaksArrayHide;
    }
    
    
}