/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
Класс для представуления вида проекта вкладки Состояния.
 */

package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 *
 * @author 655
 */
class CustomJPanelState extends JPanel {
     private static final long serialVersionUID = 11122L;
    /**для постоянного перерисовывания текста элипса */
    private MyEll currentEll;
    /**для выделения активного элипса */
    //private MyEll currentMakeEll;
    /**Получаем  Текущий Проект для отрисовки*/
    private final MyProject myProject;
    /**Получаем название таблицы отношения для отрисовки соответствующих данных проекта*/
    private String pTable;
    //Указание на пересчет видимой области экрана
    public static boolean changed;
    private JPopupMenu popup; 
    private int popupX;
    private int popupY;
    //Меню по правой кнопке мыши
    JMenuItem cop;//Меню по правой кнопке мыши
    JMenuItem paste;//Меню по правой кнопке мыши
    JMenuItem delete;//Меню по правой кнопке мыши
    JMenuItem deleteTree;//Меню по правой кнопке мыши
    JMenu positioned;//Меню по правой кнопке мыши
    JMenuItem positionedTree;//Меню по правой кнопке мыши
    JMenuItem positionedStar;//Меню по правой кнопке мыши
    JMenuItem timeAdd;
    
    
    /**Указывает площадь, занимаемую графикой*/
    private Dimension area;
    /**Заготовка для выделения линии*/
    private MyLineState makeLine;
    /**Фигура для перемещения*/
    private MyEll makeEll;
    /**Первый эллипс для линии*/
    private MyEll makeFirstEll;
    /**Фиксируем текст перемещаемого эллипса*/
    private String makeEllText;
    /**Управляем доступностью попап меню добавить время*/
    //JMenuItem timeAdd;
    /**Флаг рисования хвоста для новой линии*/
    private boolean lineDrag;
    /**нарисованный хвост линии*/
    private MyLine2 myLineDrag;
    
    /**В конструкторе получаем текущий проект и название вида отношений для отрисовки соответствующих данных проекта*/
    CustomJPanelState( final MyProject myProject, final String pTable){
        lineDrag = false;//флаг рисования хвоста линии отключен
        myLineDrag = new MyLine2();//сначала хвоста линии нет
        //Сначала ничего выделенного нет.
        this.makeLine = null;
        this.makeEllText="";
        this.myProject=myProject;
        this.pTable=pTable;
        this.popup = new JPopupMenu();
        //Меню по правой кнопке мыши
        cop = new JMenuItem("Копировать");
        cop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/copy.png")));
        paste = new JMenuItem("Вставить");
        paste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/paste.png")));
        delete = new JMenuItem("Удалить");
        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/delete.png")));
        deleteTree = new JMenuItem("Удалить все дерево");
        deleteTree.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/clean.png")));
        positioned = new JMenu("Расположить элементы.");
        positionedTree = new JMenuItem("В виде дереваа.");
        positionedTree.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/graf.png")));
        positioned.add(positionedTree);
        positionedStar = new JMenuItem("В виде звезды.");
        positionedStar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/zvezd.png")));
        positioned.add(positionedStar);
        timeAdd = new JMenuItem("Время");
        timeAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/time.png")));
        
        this.addAncestorListener(new MyAncestorListener());//добавим слушателя для хождения по вкладкам
        addMouseListener(new MyMouse());
        addMouseMotionListener(new MyMove());
        area = myProject.getMyStateArray().getWindowSize();//указавает площадь, занимаемую графикой.
        
        initComponentMenuPopup();//добавим всплывающее меню
        initComponentMenuToolBar();
        
        
        //System.out.println("Узнаем размеры экрана "+this.getSize().height + "   " +getSize().width);
    }
    
    /**
     * Находим максимальные значения координат эллипсов на экране. Сохраняем их
     * как размер экрана. Раздвигаем экран до их размеров.
     */
    public void makeArea() {
        double ax = 0;
        double ay = 0;
        for (MyEll ell : myProject.getMyStateArray().getPaintEll()) {

            if (ell.getX() > ax) {
                ax = ell.getX();
            }
            if (ell.getY() > ay) {
                ay = ell.getY();
            }

        }
        this.area.setSize(ax+200, ay + 50);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       //g.drawLine(10, 10, 100, 100);
        Graphics2D g2=(Graphics2D) g;
        
        //Рисуем эллипсы из хранилища для рисования отношений .
        for (int j=0; j< myProject.getMyStateArray().getPaintEll().size();j++){
            //достаем текст объекта circleRect.get(j)
            currentEll=myProject.getMyStateArray().getPaintEll().get(j);
            currentEll.drawEllWithOutDetail(g2);//нарисуем фигуру
        }
        //рисуем линии Классов Stateition отношений.
        //if(!myProject.getMyStateArray().getMyLineArray().getLineArray().isEmpty()){
        for (int lik=0; lik< myProject.getMyStateArray().getMyLineArray().getLineArray().size();lik++){
            //достаем линию объекта Stateition отношение
            MyLineState myLine=(MyLineState)myProject.getMyStateArray().getMyLineArray().getLineArray().get(lik);
            myLine.drawLine(g2,myLine);
        } 
       if(lineDrag&&myLineDrag!=null){
            //установим цвет для линии
            g2.setColor(Color.BLUE);
            g2.draw((Line2D)myLineDrag);
        }
       if(!this.myProject.isSaving()){//экранная версия Проекта отличается от сохраненной
            //Активизируем кнопку Сохранить.(Save)
            MyJFrame.saveProjectJButton.setEnabled(true);
            //Активизируем пункт меню Сохранить.(Save)
            MyJFrame.saveProjectJMenuItem.setEnabled(true);
        }else{
            //Активизируем кнопку Сохранить.(Save)
            MyJFrame.saveProjectJButton.setEnabled(false);
            //Активизируем пункт меню Сохранить.(Save)
            MyJFrame.saveProjectJMenuItem.setEnabled(false);
        }
        //Запишем количество линий(эллипсов) на экране
        MyJFrame.dispLinejLabel.setText(" "+myProject.getMyStateArray().getMyLineArray().getLineArray().size()+"("
                                            +myProject.getMyStateArray().getPaintEll().size()+") ");
        //Если есть необходимость пересчета окна , пересчитаем его размер
        if(myProject.getMyStateArray().isResizeWindow()){
            makeArea();
            changed=true;
            myProject.getMyStateArray().setResizeWindow(false);
        }
        if (changed) {
            //Update client's preferred size because
            //the area taken up by the graphics has
            //gotten larger or smaller (if cleared).
            setPreferredSize(area);

            //Let the scroll pane know to update itself
            //and its scrollbars.
            revalidate();
            repaint();
            
        }
    }

    
    private class MyMouse extends MouseAdapter {
        @Override
        public void mousePressed (MouseEvent event){
            boolean nothing=true;//индикатор пресед на пустом поле
            //Указание на пересчет видимой области экрана
            boolean changed = false;
            //при прессед на правой кнопке мыши
            if (SwingUtilities.isRightMouseButton(event)){
                 //Показать всплывающее меню.
                popup.show(event.getComponent (), event.getX(), event.getY()); 
                popupX=event.getX();
                popupY=event.getY();
                popup.getComponent(5).setEnabled(false);//перед попуп меню проверяем, или есть линии, если да, добавим пункт Время
                
                
                //Подсветить объект, на котором кликнем.
                //если мышь пресед на линии
                //проверки на принадлежность преседной точки линии
                if(myProject.getMyStateArray().getMyLineArray().findMyLineB(event.getPoint())){
                    makeLine = (MyLineState) myProject.getMyStateArray().getMyLineArray().findMyLine(event.getPoint());
                    //если мышка кликает на линии, она становится голубым.
                    makeLine.setColorLine(Color.BLUE);
                    //Изменим цвет фигур начала и конца линии.
                    myProject.getMyStateArray().setColorEllLine(makeLine, Color.RED);
                    popup.getComponent(5).setEnabled(true);//перед попуп меню проверяем, или есть линии, если да, добавим пункт Время
                    changed=true;
                    nothing=false;
                }
                            
                //Если мышь пресед на эллипсе.
                //Проверки на принадлежность преседной точки эллипсу.
                if(myProject.getMyStateArray().findPaintEllB(event.getPoint())){
                    makeEll = myProject.getMyStateArray().findPaintEll(event.getPoint());
                    //если мышка кликает на эллипсе, он становится голубым.
                    makeEll.setColorEll(Color.BLUE);
                    //Изменим цвет линий к и от эллипса.
                    myProject.getMyStateArray().setColorLineEll(makeEll, Color.RED);
                    changed=true;
                    delete.setEnabled(true);
                    deleteTree.setEnabled(true);
                    nothing=false;
                }

            }//при прессед на правой кнопке мыши
            
            //при клике на левой кнопке мышки
            if (event.getModifiers()==MouseEvent.BUTTON1_MASK){
                //проверки на принадлежность первой точки линии
                if(myProject.getMyStateArray().getMyLineArray().findMyLineB(event.getPoint())){
                    makeLine = (MyLineState) myProject.getMyStateArray().getMyLineArray().findMyLine(event.getPoint());
                    //если мышка кликает на линии, она становится голубым.
                    makeLine.setColorLine(Color.BLUE);
                    //Изменим цвет фигур начала и конца линии.
                    myProject.getMyStateArray().setColorEllLine(makeLine, Color.RED);
                    changed=true;
                    if("time".equals(MyJFrame.getWork())){
                        if(makeLine!=null){
                            //По-умолчанию, Название проекта - пробник, Предметная область - все материаллы, Автор - герой.
                            JDialog_AddTime dialogAddTime = new JDialog_AddTime(new javax.swing.JFrame(), true,  makeLine);
                            dialogAddTime.setVisible(true);

                        } 
                    }
                    
                }
                //если мышь пресед на овале
                //Проверки на принадлежность преседной точки эллипсу.
                if(myProject.getMyStateArray().findPaintEllB(event.getPoint())){
                        makeEll = myProject.getMyStateArray().findPaintEll(event.getPoint());
                        //если мышка кликает на эллипсе, он становится голубым.
                        makeEll.setColorEll(Color.BLUE);
                        //Изменим цвет линий к и от эллипса.
                        myProject.getMyStateArray().setColorLineEll(makeEll, Color.RED);
                            changed=true;
                }
                if("Стрелка-указатель".equals(MyJFrame.getWork())&&makeEll!=null){
                    //Фиксируем текст перемещаемого эллипса
                    makeEllText=makeEll.getText();
                    changed=true;
                }
                if("Линия".equals(MyJFrame.getWork())&&makeEll!=null){
                    //Фиксируем текст перемещаемого эллипса
                    makeFirstEll=makeEll;
                    changed=true;
                    //Готовимся вести тень будущей линии
                    myLineDrag=new MyLine2();
                    lineDrag = true;//установим разрешение для отрисовки хвоста линии
                }
                    
                    
                //}//Вкладка Таксономии.

            }
            if (changed) {
                //Update client's preferred size because
                //the area taken up by the graphics has
                //gotten larger or smaller (if cleared).
                setPreferredSize(area);

                //Let the scroll pane know to update itself
                //and its scrollbars.
                revalidate();
                repaint();
               
            }    
        }   // -public void mousePressed (MouseEvent mouseevent)
           
        public void mouseReleased(MouseEvent event) {
            //Указание на пересчет видимой области экрана
            boolean changed = false;
            //при релизд на правой кнопке мыши
            if (SwingUtilities.isRightMouseButton(event)){
                //проверки существование выделенной линии
                if(makeLine!=null){
                    //Возвращаем линии черный цвет.
                    makeLine.setColorLine(Color.BLACK);
                    //Возвращаем цвет фигурам начала и конца линии.
                    myProject.getMyStateArray().setColorEllLine(makeLine, Color.BLACK);
                    //Снимаем выделение.
                    makeLine =null;
                    //Обновим рисунок на экране.
                    changed=true;
                }
                //Проверка существования выделенного эллипса.
                if(makeEll!=null){
                    //Возвращаем эллипсу черный цвет.
                    makeEll.setColorEll(Color.BLACK);
                    //Возвращаем цвет линиям к и от эллипса
                    myProject.getMyStateArray().setColorLineEll(makeEll, Color.BLACK);
                    //Снимаем выделение.
                    makeEll =null;
                    //Обновим рисунок на экране.
                    changed=true;
                }
            }
            //Релизд на левой кнопке мыши при выбранном "Круг" рисует круг
            if (event.getModifiers()==MouseEvent.BUTTON1_MASK){
                //проверки существование выделенной линии
                if(makeLine!=null){
                    //Возвращаем линии черный цвет.
                    makeLine.setColorLine(Color.BLACK);
                    //Возвращаем цвет фигурам начала и конца линии.
                    myProject.getMyStateArray().setColorEllLine(makeLine, Color.BLACK);
                    //Снимаем выделение.
                    makeLine =null;
                    //Обновим рисунок на экране.
                    changed=true;
                }
                //Проверка существования выделенного эллипса.
                if(makeEll!=null){
                    //Возвращаем эллипсу черный цвет.
                    makeEll.setColorEll(Color.BLACK);
                    //Возвращаем цвет линиям к и от эллипса
                    myProject.getMyStateArray().setColorLineEll(makeEll, Color.BLACK);
                    //Снимаем выделение.
                    makeEll =null;
                    //Обновим рисунок на экране.
                    changed=true;
                }
                if ("Круг".equals(MyJFrame.getWork())){ 
                    //добавить фигуру круг 
                    currentEll =  myProject.getMyStateArray().findPaintEll(event.getPoint());
                    //Убедимся, что создаем эллипс на пустом месте.
                    if (currentEll == null&&!myProject.getMyStateArray().getMyLineArray().findMyLineB(event.getPoint()))  {
                        //создадим заготовку для нового эллипса.
                        currentEll=new MyEll(0,0);
                        //Просчитаем его (нового эллипса) расположение. Подправим при необходимости.
                        //если мышь выходит за левую границу формы, рисуем у края.
                        double creatX=event.getPoint().x-currentEll.getWidth()/2;
                        if (creatX<0){ creatX=0; }
                        //если мышь выходит за верхнюю границу формы, рисуем у края.
                        double creatY=event.getY()-currentEll.getHeight()/2;
                        if (creatY<0){ creatY=0; }

                        //установим новую границу области рисунка отношения
                        double this_width = (creatX + currentEll.getWidth() + 12);
                        if (this_width > area.width) {
                            area.width = (int)this_width; changed=true;
                        }
                        double this_height = (creatY + currentEll.getHeight() +12);
                        if (this_height > area.height) {
                            area.height = (int)this_height; changed=true;
                        }

                        //Подправим новый эллипс при необходимости.
                        currentEll=new MyEll(creatX,creatY,"класс:состояние"+creatX);
                        //Добавим новый эллипс на экран
                        myProject.getMyStateArray().addPaintEll(currentEll);
                    }

                    repaint();
                }

                
                //При выборе меню фигур - линия получим вторую точку.
                if  ("Линия".equals(MyJFrame.getWork())){
                    //Если мышь уже была пресед на овале и сейчас релизед на овале.
                    if(myProject.getMyStateArray().findPaintEllB(event.getPoint())){
                        MyEll lineSecondEll =  myProject.getMyStateArray().findPaintEll(event.getPoint());

                        //Проверки на принадлежность второй точки линии эллипсу или одному и тому же эллипсу. 
                        if (makeFirstEll!= null&&makeFirstEll!=lineSecondEll){
                            //Создадим линию связи и сохраним ее в массиве линий отношений таксономии.
                            MyLineState myLineState = new MyLineState (makeFirstEll,lineSecondEll);
                            //если создание линии не отменено
                            
                            myProject.getMyStateArray().getMyLineArray().getLineArray().add(myLineState);
                            //Данные Проекта могут быть изменены, Проект требует сохранения.
                            myProject.setSaving(false);  
                        }
                        
                        //При релизед первому выделенному эллипсу вернем черный цвет.
                        makeFirstEll.setColorEll(Color.BLACK);
                        //откроем пункт попап меню добавить время
                        //timeAdd.setEnabled(true);

                        changed=true;
                        //repaint();
                    }
                    //выключим рисование хвоста за мышей
                            lineDrag = false;
                            makeEll=null;
                            makeFirstEll=null;
                }
                    
                    //При выборе меню фигур - Стрелка-указатель снимем цветовое выделение.
                    //if  ("Стрелка-указатель".equals(MyJFrame.getWork())){
                        //Если мышь уже переместила овал и сейчас релизед на овале.
                        if (makeEll!= null){
                            //При релизед перемещенному выделенному эллипсу вернем черный цвет.
                            makeEll.setColorEll(Color.BLACK);
                            changed=true;
                            makeEllText="";
                            makeEll=null;
                            repaint();
                        }
                    //}
                } 
                //проверки на принадлежность первой точки линии эллипсу 
                //currentEll = (MyEll) findEll(event.getPoint());
            //}
            //Перерисовка видимой части экрана
            if (changed) {
                //Update client's preferred size because
                //the area taken up by the graphics has
                //gotten larger or smaller (if cleared).
                setPreferredSize(area);
                //Let the scroll pane know to update itself
                //and its scrollbars.
                revalidate();
                repaint();
                
            }    
        }
        @Override
        public void mouseClicked(MouseEvent e){
            //Указание на пересчет видимой области экрана
            boolean changed = false;
            //при клике на правой кнопке мыши
            /*
            if (e.getModifiers()==MouseEvent.BUTTON3_MASK){
            //Point2D clickedPoint=new MyPoint();
            //clickedPoint= e.getPoint();
            if (SwingUtilities.isRightMouseButton(e))
            popup.show(e.getComponent (), e.getX(), e.getY()); 
            popupX=e.getX();
            popupY=e.getY();
            }
            */
            //при клике на левой кнопке мышки
            if (e.getModifiers()==MouseEvent.BUTTON1_MASK){
                //изменяем текст на двойном клике
                if ((e.getClickCount()>= 2)){
                    //изменить слово в эллипсе:
                    //находим выбранный мышкой эллипс
                    MyEll changeTextEll =  myProject.getMyStateArray().findPaintEll(e.getPoint());
                    if (changeTextEll !=null){
                        //получим старый текст
                        String textEllOld=changeTextEll.getText();
                        //изменить слово в эллипсе
                        String oldText=changeTextEll.getText();
                        String textEll=JOptionPane.showInputDialog("Изменяем слово в эллипсе", changeTextEll.getText());
                        if(textEll==null){
                            textEll=oldText;
                        }
                        //Присвоить тексту в эллипсе новое значение. Подогнать размер эллипса под размер текста.
                        changeTextEll.setText(textEll);
                        //перепривяжем все линии к/от эллипса
                        myProject.getMyStateArray().renewEllTextAndLine(changeTextEll,textEllOld);
                        
                        //Отобразим изменения.
                        changed=true;
                        //просчитаем видимую область экрана после изменения текста
                        //если мышь выходит за левую границу формы, рисуем у края.
                        double dX=e.getX()-changeTextEll.getWidth()/2;
                        if (dX<0){ dX=0; }
                        //если мышь выходит за верхнюю границу формы, рисуем у края.
                        double dY=e.getY()-changeTextEll.getHeight()/2;
                        if (dY<0){ dY=0; }
                        //установим новую границу области рисунка отношения
                        double this_width = (dX + changeTextEll.getWidth() + 12);
                        if (this_width > area.width) {
                            area.width = (int)this_width; changed=true;
                        }
                        double this_height = (dY + changeTextEll.getHeight() +12);
                        if (this_height > area.height) {
                            area.height = (int)this_height; changed=true;
                        }
                        changeTextEll=null;
                    }
                    //если мышка дабл кликает на линии
                    if(myProject.getMyStateArray().getMyLineArray().findMyLineB(e.getPoint())){
                            makeLine = (MyLineState) myProject.getMyStateArray().getMyLineArray().findMyLine(e.getPoint());
                            //если мышка дабл кликает на линии, она становится голубым и меняем текст в квадрате.
                                makeLine.setColorLine(Color.BLUE);
                                //Изменим цвет фигур начала и конца линии.
                                myProject.getMyStateArray().setColorEllLine(makeLine, Color.RED);
                                //изменим текст квадрата линии
                                makeLine.editMyEllRectStateText();
                                changed=true;
                    }
                }
            }
            if (changed) {
                //Update client's preferred size because
                //the area taken up by the graphics has
                //gotten larger or smaller (if cleared).
                setPreferredSize(area);

                //Let the scroll pane know to update itself
                //and its scrollbars.
                revalidate();
                repaint();
                
            }
            
        }    
                // -public void mouseClicked(MouseEvent mouseevent)    
    } 
        
        //обработка команд мышки  
    private class MyMove implements MouseMotionListener {  
        // контроль перемещения мыши
        @Override
        /**Перемещение мыши*/
        public void mouseMoved(MouseEvent e){
            //отметим в Таксономии выбранный объект
            //if (pTable=="Таксономии"){
                //if ("Круг".equals(work)) {
                //setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

                //при прохождении мышки над элипсом или линией
                if (myProject.getMyStateArray().findPaintEllB(e.getPoint())
                         ||myProject.getMyStateArray().getMyLineArray().findMyLineB(e.getPoint())){
                //if (myProject.getMyStateArray().findEll(e.getPoint().getX(), e.getPoint().getY())!=null){    
                    //при прохождении мышки над элипсом или линией меняется курсор на руку
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                    //jLabel1.setText(" Курсор проходит над эллипсом");

                }
                else{
                    setCursor(Cursor.getDefaultCursor());
                    //jLabel1.setText(text);
                }
                
            //}//if (pTable=="Таксономии"){
        }  // +public void mouseMoved(MouseEvent mouseevent
        /** перемещение выбранной фигуры*/
        @Override
        public void mouseDragged(MouseEvent e) {
            //перемещение по нажатию правой кнопки мыши
            //if (e.getModifiers()==MouseEvent.BUTTON3_MASK)
            //перемещение при клике на левой кнопке мышки
            //Указание на пересчет видимой области экрана
            boolean changed = false;
            if (e.getModifiers()==MouseEvent.BUTTON1_MASK){
                //рисуем хвост линии при флаге lineDrad == true
                if("Линия".equals(MyJFrame.getWork())&&makeEll!=null&&lineDrag){
                    myLineDrag.setLine(makeFirstEll, e.getPoint());
                }
                //отметим в Таксономии выбранный объект
                //if (pTable=="Таксономии"){
                    //перемещение по нажатию кнопки перемещение 
                    if ("Стрелка-указатель".equals(MyJFrame.getWork())&&makeEllText.length()>0) {
                        //берем выбраный мышей эллипс 
                        if( myProject.getMyStateArray().findPaintEll(e.getPoint())!=null){
                            makeEll=myProject.getMyStateArray().findPaintEll(makeEllText);
                            //}
                             //если мышь выходит за левую границу формы, рисуем у края.
                            double draggX=e.getX()-makeEll.getWidth()/2;
                            if (draggX<0){ draggX=0; }
                            //если мышь выходит за верхнюю границу формы, рисуем у края.
                            double draggY=e.getY()-makeEll.getHeight()/2;
                            if (draggY<0){ draggY=0; }

                            //установим новую границу области рисунка отношения
                            double this_width = (draggX + makeEll.getWidth() + 12);
                            if (this_width > area.width) {
                               area.width = (int)this_width; 
                                changed=true;
                            }
                            double this_height = (draggY + makeEll.getHeight() +12);
                            if (this_height > area.height) {
                                area.height = (int)this_height; 
                                changed=true;
                            }
                           //собственно перемещение эллипса
                            //if()
                                makeEll.setFrame(draggX,draggY, makeEll.getWidth(), makeEll.getHeight());
                                //Данные Проекта могут быть изменены, Проект требует сохранения.
                                myProject.setSaving(false);  
                            //если массив линий имеет элементы
                            if(!myProject.getMyStateArray().getMyLineArray().getLineArray().isEmpty()){
                                //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
                                for (int i=0;i<myProject.getMyStateArray().getMyLineArray().getLineArray().size();i++){
                                    //перебираем все линии массива линий
                                    MyLineState ml=(MyLineState) myProject.getMyStateArray().getMyLineArray().getLineArray().get(i);
                                    //переназначаем исходящие линии для перемещаемого эллипса .contains - стреляет неточно.
                                    //if (ml.getText1().contains(makeEll.getText())) ml.setLine(makeEll, (MyEll) myProject.getMyStateArray().findEll(ml.getText2()));
                                    //переназначаем исходящие линии для перемещаемого эллипса
                                    if (ml.getText1().equals(makeEll.getText())) ml.setLine(makeEll,  myProject.getMyStateArray().findPaintEll(ml.getText2()));
                                    //переназначаем входящие линии для пнрнмещаемого эллипса
                                    if (ml.getText2().equals(makeEll.getText()))  ml.setLine( myProject.getMyStateArray().findPaintEll(ml.getText1()), makeEll);
                                }
                                //добавить описание действия 
                                //jLabel1.setText("mouse Drag="+e.getPoint().getX()+","+e.getPoint().getY()+"размер окна: "+MyJFrame.windowTaks.height+" "+MyJFrame.windowTaks.width);
                            }

                        }
                        
                        
                    }
                    if (changed) {
                        //Update client's preferred size because
                        //the area taken up by the graphics has
                        //gotten larger or smaller (if cleared).
                        setPreferredSize(area);
                        //Let the scroll pane know to update itself
                        //and its scrollbars.
                        revalidate();
                        
                    }
                    
                    repaint();
                //}//if (pTable=="Таксономии"){
            }//if(e.Modifires==MouseEvent.BUTTON1_MASK)
            
        }//MyMove.mouseDragged
        
    }  //-private class MyMove implements MouseMotionListener 
    public MyProject getMyProject(){
        return this.myProject;
    }
    /**Включим пункты всплывающего меню*/
    private void initComponentMenuPopup(){
        
        
        //добавим картинок к пунктам меню
        //cop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/copy.png"))); // NOI18N
        //inser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/paste.png"))); // NOI18N
        //delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/delete.png"))); // NOI18N
        //deleteTree.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/delete.png"))); // NOI18N
        //positioned.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/peredvig.png"))); // NOI18N
        //timeAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/time.png"))); // NOI18N
        //delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,java.awt.Event.CTRL_MASK));
        //delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,java.awt.Event.DELETE));
        //delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,0));
        popup.add(cop); 
        popup.add(paste); 
        popup.add(delete);
        popup.add(deleteTree);
        popup.add(positioned);
        popup.add(timeAdd);
        cop.setEnabled(false);//Временно. Пока не назначена обработка команды.
        paste.setEnabled(false);//Временно. Пока не назначена обработка команды.
        deleteTree.setEnabled(false);//Временно. Пока не назначена обработка команды.
        positionedStar.setEnabled(false);//Временно. Пока не назначена обработка команды.
        //timeAdd.setEnabled(false);//пока не нарисовано ни одной линии
        if(!myProject.getMyStateArray().getMyLineArray().getLineArray().isEmpty()){
            timeAdd.setEnabled(true);
        }
        //if(!MyJFrame.getWork().equals("time")){timeAdd.setEnabled(false);}
        //if(MyJFrame.getWork().equals("time")){timeAdd.setEnabled(true);}
        cop.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s=evt.getActionCommand();
                //меню Копировать 
                
            }
        });
        paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s=evt.getActionCommand();
                //меню Вставить 
                
                
            }
        });
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s=evt.getActionCommand();
                //меню Удалить
                
               //Если выбран Круг.
                //проверки на принадлежность точки линии эллипсу 
                MyEll    deleteEll = myProject.getMyStateArray().findPaintEll(new Point(popupX,popupY));
                if (deleteEll != null){
                    //Удаляем эллипс и все его линии, если они есть.
                    myProject.getMyStateArray().removePaintEll(deleteEll);
                }
                //Если выбран Линия
                if (myProject.getMyStateArray().getMyLineArray().findMyLineB(new MyPoint1(popupX,popupY) {})){
                   MyLine deleteLine= myProject.getMyStateArray().getMyLineArray().findMyLine(new Point(popupX,popupY));
                    //Подсветить ее.
                   deleteLine.setColorLine(Color.orange);
                    //Удалить ее.
                    myProject.getMyStateArray().removePaintLine(deleteLine);
                    //Данные Проекта могут быть изменены, Проект требует сохранения.
                    myProject.setSaving(false);  
                }
                    
            repaint();    
            }
        });
        deleteTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s=evt.getActionCommand();
               //1 взяли эллипс для удаления.
                MyEll    deleteEll =  myProject.getMyStateArray().findPaintEll(new Point(popupX,popupY));
                if (deleteEll != null){
                    myProject.getMyStateArray().removePaintEllipseTree(deleteEll);
                    //Данные Проекта могут быть изменены, Проект требует сохранения.
                    myProject.setSaving(false);  
                //Если выбран Круг.
                //проверки на принадлежность первой точки линии эллипсу 
                
                repaint();
                }   
            }
        });
        positionedTree.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s=evt.getActionCommand();
                //если выделенный объект это эллипс
                MyEll   ferstTreeEll =  myProject.getMyStateArray().findPaintEll(new Point(popupX,popupY));
                if (ferstTreeEll != null){
                    //располагаем объекты каждого нижнего уровня дерева от линии с вершиной вниз.
                    myProject.getMyStateArray().bildPaintTree(ferstTreeEll);
                    myProject.getMyStateArray().setResizeWindow(true);
                }  
            }
        });
        timeAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s=evt.getActionCommand();
               //Если выбран Линия
                if (myProject.getMyStateArray().getMyLineArray().findMyLineB(new MyPoint1(popupX,popupY) {})){
                   MyLineState timeLine= (MyLineState)myProject.getMyStateArray().getMyLineArray().findMyLine(new Point(popupX,popupY));
                   //if("time".equals(MyJFrame.getWork())&&timeLine!=null){//активность при включении кнопки времени Главного меню
                   if(timeLine!=null){
                        
                        
                        System.out.println("timeLine.getMyEllRectState() "+timeLine.getMyEllRectState());
                        //По-умолчанию, Название проекта - пробник, Предметная область - все материаллы, Автор - герой.
                        JDialog_AddTime dialogAddTime = new JDialog_AddTime(new javax.swing.JFrame(), true,  timeLine);
                        dialogAddTime.setVisible(true);
                        
                    } 
                }
            //меню 
            
            repaint();   
            }
        });
        
    }
    /**Включим пункты меню*/
    public void initComponentMenuToolBar(){
        //Активизируем кнопку Стрелка-указатель.
            MyJFrame.strelkaUkazatel.setEnabled(true);
            //Активизируем кнопку Круг.
            MyJFrame.krug.setEnabled(true);
            //Деактивизируем кнопку Линия.
            MyJFrame.liniyaCompos.setEnabled(false);
            //Деактивизируем кнопку Линия1.
            //MyJFrame.liniya1.setEnabled(false);
            //Деактивизируем кнопку Линия2.
            MyJFrame.liniyaTaks.setEnabled(false);
            //Активизируем кнопку Линия2.
            MyJFrame.liniyaState.setEnabled(true);
            //Активизируем кнопку time
            //MyJFrame.time.setEnabled(true);
            //Подправим выбор Линия work
            //if(MyJFrame.liniya1.isSelected())MyJFrame.liniya.setSelected(true);//значение по-умолчанию
            if(MyJFrame.liniyaCompos.isSelected())MyJFrame.liniyaState.setSelected(true);//значение по-умолчанию
            if(MyJFrame.liniyaTaks.isSelected())MyJFrame.liniyaState.setSelected(true);//значение по-умолчанию
            
            
            
            //Активизируем кнопку Выстроить Дерево
            MyJFrame.positionedTree.setEnabled(true);
            
            //Активизируем пункт меню СохранитьКак.(Save As...)
            MyJFrame.saveAsProject.setEnabled(true);
            
            //Активизируем пункт меню закрыть Проект.
            MyJFrame.closeProjectJMenuItem18.setEnabled(true);
            //Активизируем пункт меню Настройки
            MyJFrame.property.setEnabled(true);
            //Активизируем пункт меню Импорт.
            MyJFrame.inport.setEnabled(true);
            
    }
    /**Выключим пункты меню*/
    public void finalComponentMenuToolBar(){
        //ДеАктивизируем кнопку Стрелка-указатель.
            MyJFrame.strelkaUkazatel.setEnabled(false);
            //ДеАктивизируем кнопку Круг.
            MyJFrame.krug.setEnabled(false);
            //Деактивизируем кнопку Линия.
            MyJFrame.liniyaCompos.setEnabled(false);
            //Деактивизируем кнопку Линия1.
            //MyJFrame.liniya1.setEnabled(false);
            //Деактивизируем кнопку Линия2.
            MyJFrame.liniyaTaks.setEnabled(false);
            //ДеАктивизируем кнопку Линия2.
            MyJFrame.liniyaState.setEnabled(false);
            //Активизируем кнопку time
            //MyJFrame.time.setEnabled(true);
            //Подправим выбор Линия work
            //if(MyJFrame.liniya1.isSelected())MyJFrame.liniya.setSelected(true);//значение по-умолчанию
            if(MyJFrame.liniyaCompos.isSelected())MyJFrame.liniyaState.setSelected(false);//значение по-умолчанию
            if(MyJFrame.liniyaTaks.isSelected())MyJFrame.liniyaState.setSelected(false);//значение по-умолчанию
            
            
             //ДеАктивизируем пункт меню СохранитьКак.(Save As...)
            MyJFrame.saveAsProject.setEnabled(false);
            //ДеАктивизируем кнопку Сохранить.(Save .)
            MyJFrame.saveProjectJButton.setEnabled(false);
            //ДеАктивизируем пункт меню Сохранить.(Save)
            MyJFrame.saveProjectJMenuItem.setEnabled(false);
        
            //ДеАктивизируем пункт меню закрыть Проект.
            MyJFrame.closeProjectJMenuItem18.setEnabled(false);
            
            //ДеАктивизируем пункт меню Настройки
            MyJFrame.property.setEnabled(false);
            //Активизируем пункт меню Импорт.
            MyJFrame.inport.setEnabled(false);
    }
    //повесим слушателя на переключение вкладки
    private class MyAncestorListener implements AncestorListener {
        public void actionPerformed(AncestorEvent e) {}
        @Override
        public void ancestorAdded(AncestorEvent ae) {
            //выполняется при переключении на вкладку Таксономии.
            initComponentMenuToolBar();
            getMyProject().getMyStateArray().setResizeWindow(true);//подгоним размер экрана под расположенные данные
            getMyProject().setMyEnabledArray(pTable);//сообщим Проекту, какая вкладка у него открыта
        }
        @Override
        public void ancestorRemoved(AncestorEvent ae) {
            //выполняется при уходе из вкладки Таксономии
            finalComponentMenuToolBar();
        }
        @Override
        public void ancestorMoved(AncestorEvent ae) {}
        }
    
}//CustomJPanel