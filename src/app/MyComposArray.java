/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
Класс с массивами обектов окна отношений Композиции и методы для работы с элементами класса.
В этом классе собрано все, что относится к таксономическим отношениям вцелом.
Он включает в себя:
    - временный вспомогательный массив для визуального создания отношений таксономии,
    - массив отношений таксономии, 
    - размеры рисунка для вывода его на экран, 
    - методы для вычисления размероврисунка для вывода отношений таксономии,
    - иассив элементов класса соединительных линий для визуализации отношений таксономии,
    - и др.
Также есть возможность автоматического упорядочивания элементов Композиции, присутствующих в проекте.
Расчет размеров окна для их отображения.
Или использование сохраненных размеров окна Композиции прошлого сеанса(сохранения).
 */

package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author 655
 * Массивы отношений Композиции. 
 * Отображение на экране. 
 * Заполнение.
 * Изменение.
 * Сохранение.
 * Импорт-экспорт.
 * 
 */ 
public class MyComposArray {
    /*Массив отношений Композиции.*/
    private ArrayList <OntoCompos> myArray=new <OntoCompos> ArrayList<OntoCompos>();
    /**Массив фигур MyEll которые стремятся создать новые отношения Композиции. 
    * При создании нового элемента в окне Композиции он сразу попадает в этот массив. 
    * При создании нового отношения, элементы - участники переносятся из этого массива
    * в соответствующие позиции массива отношений Композиции. 
    * Стрелка направлена от Элемента к Классу.*/
    private ArrayList <MyEll> myArrayToIn= new  ArrayList<MyEll>();
    /**Класс массива элементов соединительных линий. Создается при наполнении массива отношений .
    Линия направлена от Элемента к Классу.*/
    private MyLineArray myLineArray=new MyLineArray();
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
    /**Массив для отображения эллипсов всех отношений на рисунке.*/
    private ArrayList <MyEll> paintEllArray;
    /**Флаг для необходимости пересчета размера экрана*/
    private boolean resizeWindow;
                
    
    MyComposArray(){
    initComponent();
    }
    private void initComponent(){
    w=30;
    windowSize=new Dimension(w,w);
    paintEllArray=new  ArrayList<MyEll>();
    resizeWindow=true;
                
    }
  
    /**Добавим к текущему массиву таксономии новое отношение .
      Формируем новые размеры окна для отображения объектов отношений.
     * @param ontoCompos
     */
    public void addToMyArray(OntoCompos ontoCompos){
        this.myArray.add(ontoCompos);
        //Сформируем размеры окна для отображения объектов отношения.
        //Добавим к текущим размерам окна размеры нового отношения.
        //windowSize.setSize(windowSize.getHeight()+ontoCompos.getWindowSise().getHeight(), windowSize.getWidth()+ontoCompos.getWindowSise().getWidth());
    }
    /**Сформируем массив линий для отношений .
     Просматриваем массив отношений . 
     Берем текст каждой пары отношений.
     * Просматриваем массив нарисованых эллипсов.
     Соединяем линией эллипсы с найденным текстом попарно.
     Созданный линии записываем в массив линий отношений.*/
    public void createLinesArrayFromArray(){
        //Для каждого отношения таксономии.
        for(int i=0;i<this.myArray.size();i++){
            OntoCompos ontoCompos =this.myArray.get(i);
            //просмотрим все элементы отношения и создадим линию от каждого элемента к классу.
            MyEll klassEll= ontoCompos.getEllKlass();
            MyEll elemEll=  ontoCompos.getEllElem();
            //Создадим линию нового образца от элемента к классу по тексту фигур.
            MyLineCompos myLine = new MyLineCompos(this.findPaintEll(elemEll.getText()),this.findPaintEll(klassEll.getText()));
            //Сохраним созданные линии в массиве линий отношений таксономии.
            myLineArray.getLineArray().add(myLine);
        }
    }
    /**Добавим к текущему массиву  отношения из массива.
     Фрмируем массив линий зависимости от элемента к классу.
     Формируем новые размеры окна для отображения объектов отношений.
     * @param ontoComposAr
     */
    public void addMyArray(ArrayList <OntoCompos> ontoComposAr){
        for(int i=0;i<ontoComposAr.size();i++){
            addToMyArray(ontoComposAr.get(i));
        }
    }
    /**Получим значение массива отношений.*/
    public ArrayList <OntoCompos> getMyArray(){
        return this.myArray;
    }
    /**Добавим к текущему массиву заготовок таксономии новую заготовку MyEll*/
    public void addToMyArrayToIn(MyEll myEll){
        this.myArrayToIn.add(myEll);
    }
    /**Получим значение массива заготовок отношений.*/
    public ArrayList <MyEll> getMyArrayToIn(){
    return this.myArrayToIn;
    }
    
    /**Получим значения массива линий*/
    public MyLineArray  getMyLineArray(){
        return this.myLineArray;
    }
   
/** Удаляем выбранный эллипс с экрана. 
 *  удаляем входящие и исходящие линии выбранного эллипса.
 * @author 655  29.06.2016
 * @param e эллипс, который удаляем.
*/    
    public void removePaintEll (MyEll e) {
        if (e==null) return;
        //Удаляем выбранный эллипс с экрана
        this.paintEllArray.remove(e);
        //Удалим все линии выбранного эллипса с экрана.
        this.myLineArray.removeLineEll(e);
        
    }
    
    
    
    /**Переназначим линии к перемещенному эллипсу.
     * Эллипс ищем по тексту.
     * Эллипс ищем на экране.
     * 655 24.07.2016
     * @param mEll.*/
    public void renewLineMyEll(MyEll mEll){
        //если массив линий имеет элементы
        if(!myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
            for (int i=0;i<myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                MyLineCompos ml=(MyLineCompos) myLineArray.getLineArray().get(i);
                //переназначаем исходящие линии для перемещаемого эллипса
                if (ml.getText1().equals(mEll.getText())) ml.setLine(mEll,  findPaintEll(ml.getText2()));
                //переназначаем входящие линии для пнрнмещаемого эллипса
                if (ml.getText2().equals(mEll.getText()))  ml.setLine( findPaintEll(ml.getText1()), mEll);
            }
        }
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
    /**В массиве this.paintEllArray Ищем эллипс, которому принадлежит данный текст String textEll.
    * Просматриваем элементы массива: this.paintEllArray
    *  * если да - возвращаем фигуру MyEll        
    * иначе - возвращаем null
    * @autor 655 29.06.2016/
     * @param textEll
     * @return  */
    public MyEll findPaintEll( String textEll) {
        //point2D p = new Point2D();
        if(textEll.length()==0)return null;
        if(this.paintEllArray.size()<1)return null;
        for (MyEll ell: this.paintEllArray){
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
    /**В массиве this.paintEllArray Ищем эллипс, которому принадлежит данный текст String textEll.
    * Просматриваем элементы массива: this.paintEllArray
    *  * если да - возвращаем фигуру MyEll        
    * иначе - возвращаем null
    * @autor 655 29.06.2016/
     * @param textEll
     * @return  */
    public boolean findPaintEllB( String textEll) {
        //point2D p = new Point2D();
        if(textEll.length()==0)return false;
        if(this.paintEllArray.size()<1)return false;
        for (MyEll ell: this.paintEllArray){
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
    
    
    /**Присвоим тексту в эллипсе новый текст.
     * Подгоним размер эллипса под размер нового текста.
     * Эллипс имеет линии. Значит он из массива ontoComposArray.
       Эллипс не имеет линий. Значит он из массива ontoComposArrayToIn.
     * Перепривяжем линии к/от старого эллипса к новому эллипсу.
     * Ищет в БД линий все линии от- и к- эллипсу со старым текстом и перепривязывает линии к эллипсу с новым текстом.
     * @param ell эллипс с измененным текстом.
     * @param oldText старый текст эллипса
     655 24.07.2016
     */
    public void renewEllTextAndLine(MyEll ell, String oldText){
        //если массив линий имеет элементы
        if(!myLineArray.getLineArray().isEmpty()){
            //смотрим массив линий
            for (int i=0;i<myLineArray.getLineArray().size();i++){
                //перебираем все линии массива линий
                MyLine ml= myLineArray.getLineArray().get(i);
                //переназначаем исходящие линии для перемещаемого эллипса
                if (ml.getText1().equals(oldText)) ml.setLine(ell,  findPaintEll(ml.getText2()));
                //переназначаем входящие линии для пнрнмещаемого эллипса
                if (ml.getText2().equals(oldText))  ml.setLine( findPaintEll(ml.getText1()), ell);
            }
        }
    
    }
    /**Добавим эллипс на экран
     * @param myEll.*/
    public void addPaintEll(MyEll myEll){
        this.paintEllArray.add(myEll);
    }
    /**Получим эллипсы с экрана
     * @return .*/
    public ArrayList <MyEll> getPaintEll(){
        return paintEllArray;
    }
    /**Найдем эллипс на экране которому принадлежит данная точка.
     * @param point
     * @return null - если эллипс на экране не найден. MyEll - если эллипс на экране найден.*/
    public MyEll findPaintEll (Point2D point){
        for(int i=0;i<paintEllArray.size();i++){
            MyEll ell= paintEllArray.get(i);
            if(ell.contains(point)){
                return ell;
            }
        }
        return null;
    }
    /**Проверим, есть ли эллипс на экране которому принадлежит данная точка.
     * @param point
     * @return null - если эллипс на экране не найден. MyEll - если эллипс на экране найден.*/
    public boolean findPaintEllB (Point point){
        for(int i=0;i<paintEllArray.size();i++){
            MyEll ell= paintEllArray.get(i);
            if(ell.contains(point.getX(),point.getY())){
                return true;
            }
        }
        return false;
    }
    
    
    
    /**Занесем имеющиеся отношения Проекта в массив рисования элементов Проекта.
     Если эллипс с текстом уже присутствует в массиве рисования, 
     * то двойника в массив рисования не добавляем.*/
    public void importToPaintEll(ArrayList <OntoCompos> myAr){
        //Ниже рисуем элипсы из массива отношений
        for (int ti=0; ti< myAr.size();ti++){
            //достаем Класс объекта Таксономическое отношение
            MyEll klassEll=myAr.get(ti).getEllKlass();
            //Добавим собственно эллипс Класса, эсли элипса с таким текстом нет.
            if(!this.findPaintEllB(klassEll.getText())){
                paintEllArray.add(klassEll);
            }
            //currentRectText=null;
            //Добавляем элементы Класса Таксономического отношения
            //for (int tik=0; tik< ontoComposArray.get(ti).getElemEllArray().getEllArray().size();tik++){
                //достаем элемент Класса объекта Таксономическое отношение
                MyEll elemEll=myAr.get(ti).getEllElem();
                //Добавим собственно эллипс Эемента отношения,
                //если эллипса с таким текстом в массиве рисования нет.
                if(!this.findPaintEllB(elemEll.getText())){
                    paintEllArray.add(elemEll);
                }
            //Добавим линии , о бозначив отношение.
            MyLineCompos myLineCompos= new MyLineCompos( klassEll,  elemEll);
            myLineArray.getLineArray().add(myLineCompos);
                //currentRectText=null;
            //}бозначив отношение

        }
    
    }
    /**Занесем имеющиеся отношения Проекта в массив рисования элементов Проекта.
     Если эллипс с текстом уже присутствует в массиве рисования, 
     * то двойника в массив рисования не добавляем.*/
    public void importToPaintArrayToIn(){
        //Ниже рисуем элипсы из массива таксономий
        for (int ti=0; ti< this.myArrayToIn.size();ti++){
            //достаем Класс объекта Таксономическое отношение
            MyEll ell=myArrayToIn.get(ti);
            //Добавим собственно эллипс Класса, эсли элипса с таким текстом нет.
                paintEllArray.add(ell);
        }
    
    }
    /**Найдем эллипс среди классов отношений
     * @param text.
     * @return */
    public MyEll findMyArrayKlass (String text){
        for(int i=0;i<myArray.size();i++){
            MyEll ell= myArray.get(i).getEllKlass();
            if(ell.getText().equals(text)){
                return ell;
            }
        }
        return null;
    }
    /**Проверим или есть эллипс среди классов отношений
     * @param text?
     * @return */
    public boolean findMyArrayKlassB (String text){
        for(int i=0;i<myArray.size();i++){
            MyEll ell= myArray.get(i).getEllKlass();
            if(ell.getText().equals(text)){
                return true;
            }
        }
        return false;
    }
    /**Найдем эллипс среди элементов отношений ontoComposArray
     * @param text.
     * @return */
    public MyEll findEllInMyArrayElement (String text){
        for(int i=0;i<myArray.size();i++){
            MyEll ell= myArray.get(i).getEllElem();
            if(ell.getText().equals(text)){
                return ell;
            }
        }
        return null;
    }
    /**Проверим или есть эллипс среди классов отношений
     * @param text?
     * @return */
    public boolean findEllInMyArrayElementB (String text){
        for(int i=0;i<myArray.size();i++){
            //for(int j=0; j<myArray.get(i).getEllElem().getEllArray().size();j++){
                MyEll ell= myArray.get(i).getEllElem();
                if(ell.getText().equals(text)){
                    return true;
                }
            //}
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
    /**Удаляем линию с экрана.
     * @param deleteLine
     */
    public void removePaintLine(MyLine deleteLine){
        this.myLineArray.removeMyLine(deleteLine);
    }
    /**Изменяет цвет эллипсам с текстом начала и конца линии
     * @param line.
     * @param color*/
   public void setColorEllLine(MyLine line, Color color){
       //Ищем эллипс с текстом начала линии. Присвоим ему заданный цвет.
       this.findEll(this.paintEllArray,line.getText1()).setColorEll(color);
       //Ищем эллипс с текстом конца линии. Присвоим ему заданный цвет.
       this.findEll(this.paintEllArray,line.getText2()).setColorEll(color);
    }
   /**Изменяет цвет линиям к и от эллипса
     * @param ell
     * @param color*/
   public void setColorLineEll(MyEll ell, Color color){
       //Ищем линии с текстом эллипса в начале или в конце линии. Присвоим ему заданный цвет.
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
     * @return  вернем массив  , что есть поуровневое дерево от выбранного эллипса.*/
    public ArrayList<MyEll> makeEllTreeExtended(MyEll myEll){
        ArrayList <MyEll> makeAr= new ArrayList<MyEll>();//Итоговый массив Дерева.
        ArrayList <MyEll> makeArLevel= new ArrayList<MyEll>();//Промежуточный массив Уровня дерева.
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
     * @return  вернем массив  , что есть поуровневое дерево от выбранного эллипса.*/
    public ArrayList<MyEll> makeEllTreeStrict(MyEll myEll){
        //Создадим массив строгого дерева.
        ArrayList <MyEll> makeArStr =new ArrayList<MyEll> ();//Итоговый массив.
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
                }
            }
        //Итог. Нашли и сохранили объекты строгого дерева отношений.
        }
        //int n=0;
        //for(MyEll ell:makeArStr){
        //    System.out.println("makeArStr "+n+++" "+ell.getAmount()+" "+ell.getText());
        //}
        //int n1=0;
        //for(MyEll ell:makeArExt){
        //    System.out.println("makeArExt "+n1+++" "+ell.getAmount()+" "+ell.getText());
        //}
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
        ArrayList <MyLine> inLineAr = new ArrayList<MyLine>();
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
        ArrayList <MyEll> serchEllAr=new ArrayList<MyEll>();
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
     * а заканчиваются на элементах массива дерева эллипса.
     * Поиск проводим в массиве линий по тексту эллипса.
     * @param ellArray массив эллипсов дерева.
     * @param sk
     * @autor 655 29.06.2016
     * @return */
    public ArrayList <MyLine> findLineText1EllText1 (ArrayList <MyEll> ellArray, String sk){
        if (sk.length()<1) return null;
        ArrayList <MyLine> inLineAr = new ArrayList<MyLine> ();
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
    /**Ищем все линии, которые начинаются на эллипсе  MyEll k.
     * а заканчиваются на элементах массива дерева эллипса.
     * Поиск проводим в массиве линий по тексту эллипса.
     * @param ellArray массив эллипсов дерева.
     * @param sk
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
                    if(findEllB(ellArray,this.myLineArray.getLineArray().get(i).getText2())){//проверим, что ее конец в дереве отношений.
                        //добавим линию к выходному массиву.
                        return true;
                    }
                    
                }
            }
        }
        
        return false;
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
        for(int i=0;i<myEllArr.size();i++){
            if(myEllArr.get(i).getText().equals(myEll.getText())){
                adding=false;
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
    /**Удаляем эллипс и все его дерево (включая линии) с экрана.
     * @param myEll
     @autpr 655 29.06.2016
     */
   public void removePaintEllipseTree (MyEll myEll){
       //выделим строгое дерево от полученного эллипса на экране.
       ArrayList <MyEll> arEll= this.makeEllTreeStrict(myEll);
       for(MyEll myEll5: arEll){
           this.removePaintEll(myEll5);
           
       }
       
   }
   
   
    
    /**Сформируем общий массив входящих и выходящих линий каждого эллипса расширенного дерева.
     * @param myEllArr массив эллипсов расширенного дерева.
     * @return массив линий расширенного дерева
     @autor 655 29.06.2016
     */
    public ArrayList <MyLine> makeLineTreeExtended(ArrayList <MyEll> myEllArr){
        ArrayList <MyLine> makeLineAr= new ArrayList<MyLine> ();//Выходной массив.
        //добавим в выходной массив все линии первого эллипса - вершину дерева.
        //соберем в массив выходящие линии.
        ArrayList <MyLine> lineArrOut=this.getMyLineArray().findLineText1EllText(myEllArr.get(0));
        //соберем в массив входящие линии
        ArrayList <MyLine> lineArrIn=this.getMyLineArray().findLineText2EllText(myEllArr.get(0));
        //добавим входящие линии в итоговый массив с контролем уникальности.
        for(MyLine line:lineArrOut){
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
            for(MyLine line:lineArrOut){
                makeLineAr=this.uniqueMyLine(makeLineAr, line);
            }
            //добавим выходящие линии в итоговый массив с контролем уникальности.
            for(MyLine line:lineArrOut){
                makeLineAr=this.uniqueMyLine(makeLineAr, line);
            }
        }
        return makeLineAr;
        
    }
   
    /**Сформируем массив отношений из линий и эллипсов, что есть на экране.
     * @param pOblast.*/
    public void renewMyArrayFromPaint(String pOblast){
        //удаляем старые данные из массива отношений проекта.
        this.myArray.clear();
        //берем название предметной области с шапки проекта.
        //ищем эллипс текст которого совпадает с название предметной области с шапки проекта.
        MyEll el=this.findPaintEll(pOblast);
        if(el==null||this.myLineArray.findLineText1EllTextB(el)||!this.myLineArray.findLineText2EllTextB(el)){
            //если такого эллипса нет, или у этого эллипса есть хоть одна исходящая линия, 
            //или нет входящих линий, пусть Юзер разрулит ситуацию.
            System.out.println("915 [MyComposArray] если такого эллипса нет, "
                    + "или у этого эллипса есть хоть одна исходящая линия, "
                    + "или нет входящих линий, пусть Юзер разрулит ситуацию.");
        }
        //если такой эллипс есть и у него только входящие линии,
        //берем его как первую вершину и формируем расширенное дерево.
        ArrayList <MyEll> paintArEll = new ArrayList<MyEll>();
        paintArEll.add(el);
        //Добавляем к массиву дерева оставшиеся эллипсы.
        for(MyEll myEll: this.paintEllArray){
            paintArEll=this.unique(paintArEll, myEll);
        }
        //Итог.Имеем массив эллипсов и массив линий. 
        //Надо получить из них массив отношений таксономии. Но сначала надо сформировать массив пар отношений.
        //Добавим пары в  массив отношений.
        OntoCompos ontoCompos;
        ArrayList <OntoCompos> ontoComposAr= new ArrayList<OntoCompos>();//массив пар отношений.
        //если есть линии
        if(myLineArray.getLineArray().size()>0){
            //смотрим линии
            for(MyLine myLine:myLineArray.getLineArray()){
                //берем линию и смотрим на каждый ее признак отношения
                if(myLine.getPriznakUrlAr().size()>0){
                    for(PriznakUrl priznakUrl: myLine.getPriznakUrlAr()){
                        //строим отношение.
                        ontoCompos=new OntoCompos(
                                    this.findPaintEll(myLine.getText2()), 
                                    this.findPaintEll(myLine.getText1()), 
                                    priznakUrl.get_priznak(), 
                                    priznakUrl.get_url(),"Композиции");
                            //добавим его в массив отношений.
                            ontoComposAr=uniqueMyCompos(ontoComposAr,ontoCompos);
                        }
                    }else{
                        //строим отношение.
                            ontoCompos=new OntoCompos(
                                    this.findPaintEll(myLine.getText2()),
                                    this.findPaintEll(myLine.getText1()), 
                                    "", 
                                    "","Композиции");
                            //добавим его в массив отношений.
                            ontoComposAr=uniqueMyCompos(ontoComposAr,ontoCompos);
                    }
                }
            }
       
        //преобразуем массив ontoAr в массив проекта MyArray/
        this.myArray= ontoComposAr;
    }
    
    /**Добавим отношение в массив отношений, если отношений такими значениями полей в массиве нет.
     * @param ontoComposAr
     * @param ontoCompos
     * @return 
     @autor 655 29.06.2016
     */
    public ArrayList <OntoCompos> uniqueMyCompos(ArrayList <OntoCompos> ontoComposAr, OntoCompos ontoCompos){
        int count=0;
        for(OntoCompos ontoCompos1:ontoComposAr){
            if(ontoCompos1.getKlass().trim().equals(ontoCompos.getKlass().trim())
                    &&ontoCompos1.getElem().trim().equals(ontoCompos.getElem().trim())
                    &&ontoCompos1.getPriznak().trim().equals(ontoCompos.getPriznak().trim())
                    &&ontoCompos1.getUrl().trim().equals(ontoCompos.getUrl().trim())){
                count++;
            }
        }
        if(count==0)ontoComposAr.add(ontoCompos);
        return ontoComposAr;
    }
    
    /**
     * Подсчитаем, сколько отношений с такими парами класс-элемент записано в массиве.*/
    private int countAmountOntoCompos(ArrayList <OntoCompos> ontoComposArKlassAr,OntoCompos ontoCompos ){
        int count=0;
        for(OntoCompos ontoCompos1: ontoComposArKlassAr){
            if(ontoCompos1.getKlass().equals(ontoCompos.getKlass())
                    &&ontoCompos1.getElem().equals(ontoCompos.getElem())){
                    count++;
                    
                }
            }
        return count;
    }
    /**Проверим, или есть в массиве отношение с данным текстом класса
     * @param ontoComposArr.
     * @param klass
     * @return */
    public boolean findKlassB(ArrayList <OntoCompos> ontoComposArr, String klass){
        if(ontoComposArr.size()==0)return false;
        for(OntoCompos ontoCompos: ontoComposArr){
            if(ontoCompos.getKlass().equals(klass)){
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
        //- собираем расширенное дерево линий с вершиной в выбранном эллипсе.
        ArrayList <MyEll> treeEll = makeEllTreeExtended(treeEllFirst);
        // соберем в массив линии расширенного дерева.
        ArrayList <MyLine> treeLine = this.makeLineTreeExtended(treeEll);
        //расположим объекты на экране справа и вниз от выбранной вершины.
        //при расположении, выполняем сдвиги эллипсов, что перекрываются вниз. 
        //Проверим, что массив линий не пуст.
        if(!treeEll.isEmpty()){
            //универсальный метод для всех уровней;
            int level=0;//флаг уровня
            double maxWidth= findMaxWidth(treeEll);//максимальная длинни эллипса на уровне.
            //Создадим временный массив входящих линий 1-го уровня, что попадают в вершину.
            ArrayList <MyLine> lineLevelAr = this.findLineText2EllText(treeEll, treeEllFirst);
            while(lineLevelAr.size()>0){
                //первый эллипс будет расположен на след.уроне, вровень с вершинным эллипсом, следующий - ниже и т.д.
                double x= treeEllFirst.getX()+treeEllFirst.getWidth()+50;
                double y= treeEllFirst.getY();
                //x=x+findMaxWidthLevel(treeEll,lineLevelAr)+level*150;
                x=x+level*200;
                //System.out.println("x= "+ x);
                //Для каждой линии временного массива вход.Линий 1-го уровня
                for(MyLine line:lineLevelAr){
                    //найдем в массиве дерева эллипс с текстом начала линии
                    MyEll workEll=this.findEll(treeEll, line.getText1());
                    if(maxWidth<workEll.getWidth())maxWidth=workEll.getWidth();//проверим/установим максимальную длинну эллипса.
                    if(workEll==null){System.out.println("1368 [MyArray] Гм... А эллипс-то с текстом "+line.getText1()+" не нашли. =((");}
                    if(!line.equals(lineLevelAr.get(0))){//если это не первый эллипс уровня, т.е. не первая линия массива
                        //x= treeEllFirst.getX()+treeEllFirst.getWidth()+50;
                        y= y+workEll.getHeight()+10;
                    }
                    //переместим эллипс в удобное место.
                    workEll.setFrame(x,y, workEll.getWidth(), workEll.getHeight());
                    //переместим и его линии
                    //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
                    for (int i=0;i<this.myLineArray.getLineArray().size();i++){
                        //перебираем все линии массива линий
                        MyLineCompos ml=(MyLineCompos) myLineArray.getLineArray().get(i);
                       //переназначаем исходящие линии для перемещаемого эллипса
                        if (ml.getText1().equals(workEll.getText())) ml.setLine(workEll,  findPaintEll(ml.getText2()));
                        //переназначаем входящие линии для пнрнмещаемого эллипса
                        if (ml.getText2().equals(workEll.getText()))  ml.setLine( findPaintEll(ml.getText1()), workEll);
                    }
                    
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
                        MyLineCompos ml=(MyLineCompos) myLineArray.getLineArray().get(i);
                        //переназначаем исходящие линии для перемещаемого эллипса
                        if (ml.getText1().equals(testEll.getText())) ml.setLine(testEll,  findEll(this.paintEllArray,ml.getText2()));
                        //переназначаем входящие линии для пнрнмещаемого эллипса
                        if (ml.getText2().equals(testEll.getText()))  ml.setLine( findEll(this.paintEllArray,ml.getText1()), testEll);

                    }
                compare = true;//флаг наличия перекрытия элипса эллипсом на экране.
            }else{
                compare = false;//флаг наличия перекрытия элипса эллипсом на экране.
            }

        }
    }
    
    /**метод, проверяет, что поставленный нами эллипс не пересекается с любым другим эллипсом.
     * Надо проверит, как работает!!!
     * @param workEll эллипс, который мы переместили.
     * @return если эллипс с экрана имеет общие точки с workEll, вернем другой эллитпс, иначе - вернем null.
    */
    public MyEll bildPaintEllCompare(MyEll workEll){
        //смотрим каждый эллипс на экране.
        for(MyEll ell: this.paintEllArray){
            if(ell.getFrame().contains(workEll.getFrame())&&!ell.getText().equals(workEll.getText())){
                return ell;
            }
        }
        //если эллипс с экрана имеет общие точки с workEll, вернем ИСТИНА.  
        return null;
    }
    /***/
    public void addPaintOnto(OntoCompos ontoCompos){
        
        this.paintEllArray=unique(this.paintEllArray,ontoCompos.getEllKlass());
        this.paintEllArray=unique(this.paintEllArray,ontoCompos.getEllElem());
        this.myLineArray.setLineArray(uniqueMyLine(this.myLineArray.getLineArray(), new MyLineCompos(ontoCompos.getEllElem(),ontoCompos.getEllKlass()) ));
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
    /**На вход подаются массив линий уровня и массив эллипсов дерева.
     На выходе - длинна самого длинного эллипса уровня.*/
    private double findMaxWidthLevel(ArrayList <MyEll> ellArray, ArrayList <MyLine> lineArray){
        double maxWidth=0;
        for(MyLine myLine: lineArray){
            for(MyEll myEll: ellArray){
                if(myLine.getText1().equals(myEll.getText())){
                    if(myEll.getWidth()>maxWidth){
                        maxWidth=myEll.getWidth();
                    }
                }
            }
        }
        
        return maxWidth;
    }
    
    /**расположить объекты в виде деревьев*/
    public void ellPositionedTree() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //Найдем все эллипсы, что являются вершинами деревьев.
        ArrayList <MyEll> ellTemp= new  ArrayList<MyEll>();
        for(MyEll myEll: this.paintEllArray){
            //Если у эллипса нет Выходящих линий,берем его в массив.
            if(!findLineText1EllText1B(this.paintEllArray, myEll.getText())){
                ellTemp.add(myEll);
            }
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
    /**Добавим линию myLine в массив myLineArr, если линии с таким текстом начала и конца в массиве нет.
     * Если такая линия есть, увеличим параметр количества линий.
     * @param myLineArr
     * @param myLine
     * @return 
     @autor 655 29.06.2016
     */
    public ArrayList <MyLine> uniqueMyLinePlus(ArrayList<MyLine> myLineArr, MyLine myLine){
        boolean adding=true;//информирует, о необходимости добавить новую линию.
        for(MyLine MyLineCompos:myLineArr){
            //если тексты начала и конца линии совпадают
            if(MyLineCompos.getText1().trim().equals(myLine.getText1().trim())&&MyLineCompos.getText2().trim().equals(myLine.getText2().trim())){
                adding=!MyLineCompos.addPriznakUrlAr(myLine.getPriznakUrlAr());//т.к. увеличен счетчик отношений линии, новую линию добавлять нет необходимости.
            }
        }
        //if(adding)myLineArr.add(myLine);
        if(adding){//если среди линий проекта такой линии нет
            //присвоим объектам начала и конца линии эллипсы Проекта с текстами начала и конца линии
            myLine.setLine(this.findPaintEll(myLine.getText1()),this.findPaintEll(myLine.getText2()));
            //Добавим созданную линию в проект
            myLineArr.add(myLine);
        }
        return myLineArr;
    }
}
