/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author 655
 * Класс отношений Таксономии.
 * Содержит массивы и методы для работы с отношениями таксономии
 */
public class MyTaks extends MyTaksArray implements CopyTo{
    //public String oblastTaks= new String();
    /**Объект Класс отношения таксономии*/
    private MyEll klassEll;
    /**Массив объектов Элемент отношения туксономии*/
    private MyEllArray elemEllArray;
    
    /**Высота картинки для отображения класса и элементов одного отношения таксономии.
     Состоит из (самой высокой высоты эллипса элемента отношения 
     * + расстояние между эллипсами по высоте)
     Взятых вместе для каждого элемента массива элементов*/
    private double windowH;
    /**Высота самой высокой высоты эллипса элемента отношения*/
    private double windowHElem;
    /**Расстояние между эллипсами по высоте.*/
    private double windowHh;
    /**Ширина картинки для отображения класса и элементов одного отношения таксономии.
     Состоит из ширины объекта класса отношения 
     * + ширина самого широкого объекта элемента отношения
     * + расстояния между эллипсами по длинне*/
    private double windowW;
    /**Ширина самого широкого объекта элемента отношения*/
    private double windowWElem;
    /**Расстояние между эллипсом класса и его элементами по длинне.*/
    private double windowWw;
    /**Размер рисунка для вывода всех элементов отношения таксономии и формируемых отношений.
     Расчитываем высоту сверху как:
      (высита эллипса)
      +(расстояние до более нижнего элипса)
      *(количество элементов в массиве элементов)
     Пересчитываем при:
      - создании экземпляра класса;
      - добавлении объекта в любой массив объектов отношения;
      - импорте(копировании).*/
    private Dimension windowSize;
    /**Уровень отношения в дереве выборки Предметной области*/
    private int level;
    /**Список сайтов/мест, где встретилась пара. */
    private ArrayList <PriznakUrl> priznakUrlAr;
    
    
    MyTaks(){
        initComponents();
    }
    /**Начальная инициация переменных*/
    private void initComponents(){
        klassEll=new MyEll();//пустой класс отношения для заполнения.
        elemEllArray = new MyEllArray();//пустой массив элементов отношения для заполнения.
        windowH=0;//Необходимая высота окна для отображения объектов отношения
        windowHh=40;//Расстояние между эллипсами по высоте.
        windowHElem=30;//Высота самой высокой высоты эллипса элемента отношения
        windowW=0;//Необходимая ширина окна для отображения объектов отношения
        windowWw=40;//Расстояние между эллипсами по ширине.
        windowWElem=30;//Ширина самой высокой ширины эллипса элемента отношения
        windowSize=new Dimension(0,0);//отношение не имеет объектов , а значит и размера рисунка.
        priznakUrlAr=new  ArrayList<PriznakUrl>();
    }
    
    /**Присваиваем значение классу отношения.
     * Размер окна должен быть не меньше размера эллипса класса.
     * Т.к. при приведении, теряется часть после запятой, увеличим на 1.
     * @param klassEll.*/
    public void setKlassEll(MyEll klassEll){
        if(klassEll!=null){
        this.klassEll=klassEll;//Получим эллипс класса.
        this.windowSize.height=(int) klassEll.getHeight()+1;//Просчитаем высоту рисунка.
        this.windowSize.width=(int) klassEll.getWidth()+1;//Просчитаем ширину рисунка.
        }
    }
    /**Присваиваем значение массиву объектов Элементов отношения.
     * Переборем все элементы массива, найдем самую высокую высоту и самую широкую ширину.
     * Вычислим высоту окна: к высоте добавим расстояние между соседними объектами и умножим на количество объектов.
     * Вычислим ширину окна: к ширине класса добавим расстояние до элемента и добавим ширину самого широкого элемента.
     * @param elemEllArray*/
    public void setElemEllArray(MyEllArray elemEllArray){
        this.elemEllArray=elemEllArray;//получим массив эллипсов элементов.
        setCountWindowSize(elemEllArray);//вычислим размер окна для массива отношений
        
    }
    /**Добавляем объект Элемент отношения к массиву объектов Элементов отношения.
     * @param elemEll.*/
    public void addToElemEllArray(MyEll elemEll){
        this.elemEllArray.addEllToArray(elemEll);
        setCountWindowSize(elemEll);//вычислим размер окна для массива отношений
    }
    /**Возвращаем объект Класс отношения.
     * @return .*/
    public MyEll getKlassEll(){
        return this.klassEll;
    }
   /**Возвращаем иассив объектов Элементы отношения
     * @return .*/
    public MyEllArray getElemEllArray(){
        return this.elemEllArray;
    }
    /**Возвращаем выбранный объект Элемент отношения из массива объектов Элементов отношения
     * @return  */
    public MyEll getFromElemEllArray(int i){
        return  this.elemEllArray.getEllArray().get(i);
    }
   /**Возвращаем значение переменной: Высота окна для отображения элементов таксономии.
    * @return double getWindowH().*/
    public double getWindowH(){
        return this.windowH;
    }
    /**Возвращаем значение переменной: расстояние между элипсами по ширине.
    * @return int getWindowWw().*/
    public double getWindowHh(){
         return this.windowHh;
    } 
    /**Возвращаем значение переменной: расстояние между элипсом класса и его элементами по ширине.
    * @return int getWindowWw().*/
    public double getWindowWw(){
         return this.windowWw;
    } 
    /**Задаем значение переменной: расстояние между элипсом класса и его элементами по ширине.
    * @set int getWindowHh().*/
    public void setWindowWw( double windowWw){
        this.windowWw=windowWw;
    } 
   /**Просчитаем размеры окна для массива эллипсов*/
    private void setCountWindowSize(MyEllArray elemEllArray){
          //Вычислим высоту и ширину окна:
            //вычислим максимальную высоту элемента массива элементов
        windowHElem=elemEllArray.getHMax();
            //вычислим максимальную ширину элемента массива элементов
        windowWElem=elemEllArray.getWMax();
        //вычислим максимальную высоту окна
        //к максимальной высоте элемента массива добавим расстояние между соседними объектами и умножим на количество объектов.
        windowH=(windowHElem+windowHh)*elemEllArray.getEllArray().size();
         //вычислим максимальную ширину окна
        //к максимальной ширине элемента массива добавим расстояние до объекта класса и добавим ширину объекта класса.
        windowW=windowWElem+windowWw+klassEll.getHeight();
        //зададим размеры окна
        this.windowSize.setSize(windowH, windowW);
    }
    /**Просчитаем размеры окна для возросшего массива эллипсов*/
    private void setCountWindowSize(MyEll elemEll){
          //Вычислим высоту и ширину окна:
            //вычислим максимальную высоту элемента массива элементов
        if(windowHElem<elemEll.getHeight())windowHElem=elemEll.getHeight();
            //вычислим максимальную ширину элемента массива элементов
        if(windowWElem<elemEll.getWidth()) windowWElem=elemEll.getWidth();
        //вычислим максимальную высоту окна
        //к максимальной высоте элемента массива добавим расстояние между соседними объектами и умножим на количество объектов.
        windowH=(windowHElem+windowHh)*elemEllArray.getEllArray().size();
         //вычислим максимальную ширину окна
        //к максимальной ширине элемента массива добавим расстояние до объекта класса и добавим ширину объекта класса.
        windowW=windowWElem+windowWw+klassEll.getHeight();
        //зададим размеры окна
        this.windowSize.setSize(windowH, windowW);
    }
   /**Возвращает размер рисунка для отрисовки объектов отношения.*/
   public Dimension getWindowSise(){
       return this.windowSize;
   }
   
   /**
     * @return */
   public int getLevel(){
    return this.level;
   }
   /**
     * @param level*/
   public void setLevel(int level){
       this.level=level;
   }
   /**Количество сайтов, на которых найдено отношение с любым признаком.
     * @return */
    public int getPriznakUrlArSize(){
        return this.priznakUrlAr.size();
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
   /**Для копирования экземпляра Класса в новый объект.*/
   public MyTaks copyTo(){
        MyTaks myTaksNew = new MyTaks();
       
        myTaksNew.klassEll=klassEll.copyTo();
        myTaksNew.elemEllArray= this.elemEllArray.copyTo();
        myTaksNew.windowW= windowH;
        myTaksNew.windowHElem= windowHElem;
        myTaksNew.windowHh= windowHh;
        myTaksNew.windowW= windowW;
        myTaksNew.windowWElem= windowWElem;
        myTaksNew.windowWw= windowWw;
        myTaksNew.windowSize= windowSize;
        myTaksNew.level= level;
        //myTaksNew.priznakUrlAr= priznakUrlAr.copyTo;
        //MyLineArray myLineArrayNew = new MyLineArray();
        if(this.priznakUrlAr!=null&&this.priznakUrlAr.size()>0){ //если в массиве есть объекты
            for(PriznakUrl priznakUrlOld:this.priznakUrlAr){
                PriznakUrl priznakUrlNew = priznakUrlOld.copyTo();
                myTaksNew.priznakUrlAr.add(priznakUrlNew);
            }
        }
    return myTaksNew;
   }
   /***/
   /***/
   /***/
   /***/
   /***/
   /***/
   /***/
   /***/
   
    
    
    
}
