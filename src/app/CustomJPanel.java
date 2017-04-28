/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;


import java.awt.BasicStroke;
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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 *
 * @author 655 Вкладка панели для отображения отношения Таксономии
 */
class CustomJPanel extends JPanel {
private static final long serialVersionUID = 1122233445678L; // Change number as appropriate
    
    /** для постоянного перерисовывания текста элипса     */
    private MyEll currentEll;
    /**     * для выделения активного элипса     */
    private MyEll currentMakeEll;
    /** Получаем Текущий Проект для отрисовки     */
    private final MyProject myProject;
    /** Получаем название таблицы отношения для отрисовки соответствующих данных проекта     */
    private final String pTable;
    /**Указание на пересчет видимой области экрана*/
    public static boolean changed;

    private JPopupMenu popup;//Меню по правой кнопке мыши
    private int popupX;//Меню по правой кнопке мыши
    private int popupY;//Меню по правой кнопке мыши
    //Меню по правой кнопке мыши
    JMenuItem cop;//Меню по правой кнопке мыши
    JMenuItem paste;//Меню по правой кнопке мыши
    JMenuItem delete;//Меню по правой кнопке мыши
    JMenuItem deleteTree;//Меню по правой кнопке мыши
    JMenu positioned;//Меню по правой кнопке мыши
    JMenuItem positionedTree;//Меню по правой кнопке мыши
    JMenuItem positionedStar;//Меню по правой кнопке мыши
    /** Указывает площадь, занимаемую графикой     */
    private Dimension area;
    /** Заготовка для выделения линии     */
    private MyLine makeLine;
    /** Фигура для перемещения     */
    private MyEll makeEll;
    /** Первый эллипс для линии     */
    private MyEll makeFirstEll;
    /** Фиксируем текст перемещаемого эллипса     */
    private String makeEllText;
    /**Флаг рисования хвоста для новой линии     */
    private boolean lineDrag;
    /** нарисованный хвост линии     */
    private MyLine2 myLineDrag;
    /**выделенная область левоу мышкой на экране*/
    private Rectangle2D rect;
    //private static Image img = null;
    private Point2D press = new Point2D.Double(0, 0);
    /**индикатор наличия выделения на экране*/
    private boolean pressedBtn = false;
    /**Цвет выделения рамки, линий и Эллипсов*/
    private Color makeGroupColor;
    /**Цвет выделения выбранного эллипса и линий его и эллипсов 1-й очереди его*/
    private Color makeEllColor;
    /**индикатор, что на экране есть выделенный эллипс*/
    private MyEll makeEllColorE;
    private MyEll makeSelectEll;//фигура для работы Контекстного меню по правой мышке 
    /**если пасте по правой мышке, то вот координаты, */
    double pasteX;
    /**если пасте по правой мышке, то вот координаты, */
    double pasteY;
    
    
    /** В конструкторе получаем текущий проект и название вида отношений для отрисовки соответствующих данных проекта     */
    CustomJPanel(final MyProject myProject, final String pTable) {
        this.rect = new Rectangle2D.Double();
        changed = false;
        lineDrag = false;//флаг рисования хвоста линии отключен
        myLineDrag = new MyLine2();//сначала хвоста линии нет
        //Сначала ничего выделенного нет.
        this.makeLine = null;
        this.makeEllText = "";
        this.myProject = myProject;
        this.pTable = pTable;
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
        
        this.addAncestorListener(new MyAncestorListener());//добавим слушателя для хождения по вкладкам
        addMouseListener(new MyMouse());//добавим слушателя кликов мыши 
        addMouseMotionListener(new MyMove());//добавим слушателя перемещения мыши
        area = myProject.getMyTaksArray().getWindowSize();//указавает площадь, занимаемую графикой.
        //Получает значение выбора панели пиктограмм фигур workJToolBar
        //MyJFrame.getWork();
        initComponentMenuPopup();//добавим всплывающее меню
        initComponentMenuToolBar();
        makeGroupColor=Color.BLUE;
        makeEllColor= Color.MAGENTA;
        makeEllColorE= null;//по-умолчанию выделенных эллипсов нет
        makeSelectEll=null;//фигура для работы с эллипсом по Поппап - меню
        press = new Point2D.Double(0, 0);
        currentMakeEll=null; //при загрузке формы, выделенного эллипса нет
        pasteX=0;
        pasteY=0;
    }

    /** Находим максимальные значения координат эллипсов на экране. Сохраняем их
     * как размер экрана. Раздвигаем экран до их размеров.     */
    public void makeArea() {
        double ax = 0;
        double ay = 0;
        for (MyEll ell : myProject.getMyTaksArray().getPaintTaksEll()) {
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
        Graphics2D g2 = (Graphics2D) g;
        if ((rect.getHeight()+rect.getWidth())>1&&currentMakeEll==null) {//нарисуем область выделения
            makeGroupColorOn(rect);//покажем собираемые объекты для копирования
            //рисуем пунктирную линию
            float dash1[] = {10.0f};
            g2.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,dash1, 0.0f));
            //установим цвет для объекта
            g2.setColor(this.makeGroupColor);
            g2.draw(rect);
            g2.setStroke(new BasicStroke() );//устанавливаем стандартную линию
            g2.setColor(Color.black);
        }
        if(MyJFrame.myTaksArrayCopyPassed){//если кнопка Копировать нажата
            if((rect.getWidth()+rect.getHeight())>1&&currentMakeEll==null){//Если есть выделенная область на экране и мы хотим Копировать именно ее.
                makeGroupCopyOn();//соберем выделенные объекты в класс для копирования
                //уберем выделение
                press = null;//точка начала выделения
                pressedBtn = false;//индикатор наличия выделения на экране
                makeGroupColorOff(rect);//снимем выделение с выбранной группы
                rect=new Rectangle2D.Double();  //очистим выбранную область
            }else{//Если нет выделенной области на экране и хотим Копировать эллипс, на котором правый прес мышей.
                if(currentMakeEll!=null){//если мышка правый клик на эллипсе и выбрано пункт меню копировать
                    makeGroupCopyOn();//соберем выделенные объекты в класс для копирования
                }
               
            }
            paste.setEnabled(true);
            MyJFrame.pastejButton.setEnabled(true);
            cop.setEnabled(false);
            delete.setEnabled(false);
            MyJFrame.copyJButton.setEnabled(false);
            MyJFrame.deletejButton.setEnabled(false);
            MyJFrame.myTaksArrayCopyPassed=false;//восстановим индикатор нажима кнопки
        }
        if(MyJFrame.myTaksArrayDeletePassed){//если кнопка Удалить нажата
            if(pressedBtn){//Если есть выделенная область на экране и мы хотим удалить именно ее.
                makeGroupCopyOn();//соберем выделенные объекты в класс для копирования
                makeDeleteRun();//удалим выделенные элементы
                //уберем выделение
                press = null;
                pressedBtn = false;
                makeGroupColorOff(rect);//снимем выделение с выбранной группы
                rect=new Rectangle2D.Double();  
                this.myProject.setSaving(false);//проект изменен
            }else{//Если нет выделенной области на экране и хотим удалить эллипс, на котором правый прес мышей.
                makeDeleteRun();//удалим выделенные элементы
                this.myProject.setSaving(false);//проект изменен
            }
            cop.setEnabled(false);
            delete.setEnabled(false);
            MyJFrame.copyJButton.setEnabled(false);
            MyJFrame.deletejButton.setEnabled(false);
            MyJFrame.myTaksArrayDeletePassed=false;//восстановим индикатор нажима кнопки
        }
        if(MyJFrame.pasteMakeGroupPassed){//если кнопка Вставить нажата
            pasteMakeGroup();
            this.myProject.setSaving(false);//проект изменен
            MyJFrame.pasteMakeGroupPassed=false;
            pasteX=0;pasteY=0; //очистим координаты места вставки
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
        //MyJFrame.copyJButton.setEnabled(false);
       // cop.setEnabled(false);
        
        
        //рисуем линии Классов Таксономических отношений.
        //Color g2TempColor= g2.getColor();
        //g2.setColor(Color.GRAY);
        for (int lik = 0; lik < myProject.getMyTaksArray().getMyLineArray().getLineArray().size(); lik++) {
            MyLine2 myLine = (MyLine2) myProject.getMyTaksArray().getMyLineArray().getLineArray().get(lik);//достаем линию объекта Таксономическое отношение
            myLine.setColorLine(Color.LIGHT_GRAY);
            myLine.drawLine(g2,myLine);//рисуем линию со всеми прибамбасами
        }
        //g2.setColor(g2TempColor);
        //Рисуем эллипсы из хранилища для рисования отношений таксономии.
        for (int j = 0; j < myProject.getMyTaksArray().getPaintTaksEll().size(); j++) {
            //достаем текст объекта circleRect.get(j)
            currentEll =  myProject.getMyTaksArray().getPaintTaksEll().get(j);
            
            currentEll.drawEll(g2);//нарисуем фигуру
            
        }
        
        if (lineDrag && myLineDrag != null) {//если ведем линию, нарисуем ее след на экране.
            //установим цвет для следа линии
            g2.setColor(Color.BLUE);
            g2.draw((Line2D) myLineDrag);
        }
        
        //Запишем количество линий(эллипсов) на экране
        MyJFrame.dispLinejLabel.setText(" " + myProject.getMyTaksArray().getMyLineArray().getLineArray().size() + "("
                + myProject.getMyTaksArray().getPaintTaksEll().size() + ") ");
        
        if(myProject.getMyTaksArray().isResizeWindow()){//Если есть необходимость пересчета окна , пересчитаем его размер
            makeArea();
            changed=true;
            myProject.getMyTaksArray().setResizeWindow(false);
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
        public void mousePressed(MouseEvent event) {
           
            if (SwingUtilities.isRightMouseButton(event)) {//при прессед на правой кнопке мыши
                boolean nothing=true;//индикатор пресед на пустом поле
                if(MyJFrame.pastejButton.isEnabled()){
                    paste.setEnabled(true);
                }
                //Показать всплывающее меню.
                popup.show(event.getComponent(), event.getX(), event.getY());
                popupX = event.getX();
                popupY = event.getY();
                
                if((rect.getHeight()+rect.getWidth())>0){//если выделенная область есть на экране
                    if(rect.contains(event.getPoint())){//если мышь пресед внутри выделенной области
                        cop.setEnabled(true);//пункт попап меню копировать активен
                        delete.setEnabled(true);//пункт попап меню удалить активен
                        nothing=false;
                    }
                }
                
                //если правая мышь пресед на линии
                //проверки на принадлежность преседной точки линии
                if (myProject.getMyTaksArray().getMyLineArray().findMyLineB(event.getPoint())) {
                    makeLine = myProject.getMyTaksArray().getMyLineArray().findMyLine(event.getPoint());
                    //если мышка пресед на линии, она становится голубым.
                    makeLine.setColorLine(Color.BLUE);
                    //Изменим цвет фигур начала и конца линии.
                    myProject.getMyTaksArray().setColorPaintEllLine(makeLine, Color.RED);
                    delete.setEnabled(true);
                    changed = true;
                    nothing=false;
                }
                //Если правая мышь пресед на эллипсе.
                if (myProject.getMyTaksArray().findPaintEllB(event.getPoint())) {
                    currentMakeEll = myProject.getMyTaksArray().findPaintEll(event.getPoint());
                    makeEllColorE= myProject.getMyTaksArray().findPaintEll(event.getPoint());
                    myProject.getMyTaksArray().setColorPaintEll_Line_1Ell(makeEllColorE, makeEllColor);//изменим цвет эллипса и линий его и связанныч с ним эллипсов 1-й очереди
                    changed = true;//Указание на пересчет видимой области экрана
                    cop.setEnabled(true);
                    delete.setEnabled(true);
                    deleteTree.setEnabled(true);
                    nothing=false;
                    //pressedBtn = true;//отметим, что выделение есть
                }
                if(nothing){//если правая мышь пресед на пустом месте
                    cop.setEnabled(false);//пункт попап меню копировать активен
                    delete.setEnabled(false);//пункт попап меню удалить активен
                    deleteTree.setEnabled(false);//пункт попап меню удалить активен
                }
            }//при прессед на правой кнопке мыши
            
            if (event.getModifiers() == MouseEvent.BUTTON1_MASK) {//при пресед на левой кнопке мышки
                //если мышь пресед на овале
                //Проверки на принадлежность преседной точки эллипсу.
                if (myProject.getMyTaksArray().findPaintEllB(event.getPoint())) {
                    makeEll = myProject.getMyTaksArray().findPaintEll(event.getPoint());
                    makeEllColorE= myProject.getMyTaksArray().findPaintEll(event.getPoint());
                    myProject.getMyTaksArray().setColorPaintEll_Line_1Ell(makeEllColorE, makeEllColor);//изменим цвет эллипса и линий его и связанныч с ним эллипсов 1-й очереди
                    
                    //закончим выделение группы и отменим его
                        press = null;
                        pressedBtn = false;
                        makeGroupColorOff(rect);//снимем выделение с выбранной группы
                        rect=new Rectangle2D.Double();  
                        if(MyJFrame.copyJButton.isEnabled()){//Деактивируем кнопки Копировать
                            MyJFrame.copyJButton.setEnabled(false);
                            cop.setEnabled(false);
                            MyJFrame.deletejButton.setEnabled(false);
                        }

                    if ("Стрелка-указатель".equals(MyJFrame.getWork()) && makeEll != null) {
                        //Фиксируем текст перемещаемого эллипса
                        //myProject.getMyTaksArray().setColorPaintEll_Line_1Ell(makeEll, Color.RED);//изменим цвет эллипса и линий его и связанныч с ним эллипсов 1-й очереди
                    
                        makeEllText = makeEll.getText();
                        changed = true;
                    }
                    if ("Линия".equals(MyJFrame.getWork()) && makeEll != null) {
                        //Фиксируем  пресседный эллипс
                        makeFirstEll = makeEll;
                        changed = true;//обновление экрана
                        //Готовимся вести тень будущей линии
                        myLineDrag=new MyLine2();
                        lineDrag = true;//установим разрешение для отрисовки хвоста линии
                       
                    }
                }else{//если пресс был не на эллипсе
                    if(myProject.getMyTaksArray().findPaintEllDetailB(event.getPoint())){//если пресед был на крестике Свернуть/Развернуть эллипса
                        if(myProject.getMyTaksArray().findPaintEllDetail(event.getPoint()).getDetail().isOpen()){//Если пресед был на минусике, то Дерево открыто и надо его скрыть
                            //System.out.println("Дерево развернуто. Его скроем.");
                            myProject.getMyTaksArray().findPaintEllDetail(event.getPoint()).getDetail().setOpen(false);//изменим рисунок индикатора Скрыть/Отобразить на Плюсик.
                            myProject.getMyTaksArray().makeHideTreeOn(myProject.getMyTaksArray().findPaintEllDetail(event.getPoint()));//Скроем дерево
                        }else{//Если пресс был на плюсике, Дерево сейчас скрыто и надо его открыть
                            myProject.getMyTaksArray().findPaintEllDetail(event.getPoint()).getDetail().setOpen(true);//изменяем рисунок индикатора Скрыть/Отобразить на Минусик 
                            myProject.getMyTaksArray().makeHideTreeOff(myProject.getMyTaksArray().findPaintEllDetail(event.getPoint()));//Отобразим дерево
                        }
                    }//1
                    if(!pressedBtn){//если это первый пресс выделения
                        press = event.getPoint();//выполним выделение
                        pressedBtn = true;//отметим, что выделение есть
                    }else{//если это пресс при наличии выделенной области на экране
                        //закончим выделение и отменим его
                        press = null;
                        pressedBtn = false;
                        makeGroupColorOff(rect);//снимем выделение с выбранной группы
                        rect=new Rectangle2D.Double();  
                        if(MyJFrame.copyJButton.isEnabled()){//Деактивируем кнопки Копировать
                            MyJFrame.copyJButton.setEnabled(false);
                            cop.setEnabled(false);
                            delete.setEnabled(false);
                            MyJFrame.deletejButton.setEnabled(false);
                        }
                    }
                   
                }//2
                
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

        @Override
        public void mouseReleased(MouseEvent event) {
            lineDrag = false;//указание перестать вести тень линии
            boolean changed = false;//Указание на пересчет видимой области экрана
            if(makeEllColorE!=null){
                myProject.getMyTaksArray().setColorPaintEll_Line_1Ell(makeEllColorE, Color.BLACK);//изменим цвет эллипса и линий его и связанныч с ним эллипсов 1-й очереди
                makeEllColorE= null;    
            }
            if (SwingUtilities.isRightMouseButton(event)) {//при релизд на правой кнопке мыши
                if (makeLine != null) {//проверки существование выделенной линии
                    makeLine.setColorLine(Color.BLACK);//вернем выделенным объектам первоначальный цвет
                    myProject.getMyTaksArray().setColorPaintEllLine(makeLine, Color.BLACK);//Возвращаем цвет фигурам начала и конца линии.
                    makeLine = null;//Снимаем выделение.
                    changed = true;//Обновим рисунок на экране.
                }
                if (makeEll != null) {//Проверка существования выделенного эллипса.
                    
                    makeEll.setColorEll(Color.BLACK);//вернем выделенным объектам первоначальный черный цвет
                    myProject.getMyTaksArray().setColorLineEll(makeEll, Color.BLACK);//Возвращаем цвет линиям к и от эллипса
                    makeEll = null;//Снимаем выделение.
                    changed = true;//Обновим рисунок на экране.
                }
            }//при релизд на правой кнопке мыши
            if (event.getModifiers() == MouseEvent.BUTTON1_MASK) {//при релизд на левой кнопке мыши
                if (makeLine != null) {//проверки существование выделенной линии
                    makeLine.setColorLine(Color.BLACK);//вернем выделенным объектам первоначальный черный цвет
                    myProject.getMyTaksArray().setColorPaintEllLine(makeLine, Color.BLACK);//Возвращаем цвет фигурам начала и конца линии.
                    makeLine = null;//Снимаем выделение.
                    changed = true;//Обновим рисунок на экране.
                }
                if (makeEll != null) {//Проверка существования выделенного эллипса.
                    
                    makeEll.setColorEll(Color.BLACK);//вернем выделенным объектам первоначальный черный цвет
                    myProject.getMyTaksArray().setColorLineEll(makeEll, Color.BLACK);//Возвращаем цвет линиям к и от эллипса
                    makeEll = null;//Снимаем выделение.
                    changed = true;//Обновим рисунок на экране.
                }
                
                
                if ("Круг".equals(MyJFrame.getWork())) {//если выбрано добавить круг
                    currentEll =  myProject.getMyTaksArray().findPaintEll(event.getPoint());
                    //Убедимся, что создаем эллипс на пустом месте.
                    if (currentEll == null && !myProject.getMyTaksArray().getMyLineArray().findMyLineB(event.getPoint())) {
                        
                        currentEll = new MyEll(0, 0);//создадим заготовку для нового эллипса.
                        //Просчитаем его (нового эллипса) расположение. Подправим при необходимости.
                        //если мышь выходит за левую границу формы, рисуем у края.
                        double creatX = event.getPoint().x - currentEll.getWidth() / 2;
                        if (creatX < 0) {
                            creatX = 0;
                        }
                        //если мышь выходит за верхнюю границу формы, рисуем у края.
                        double creatY = event.getY() - currentEll.getHeight() / 2;
                        if (creatY < 0) {
                            creatY = 0;
                        }
                        //установим новую границу области рисунка отношения
                        double this_width = (creatX + currentEll.getWidth() + 12);
                        if (this_width > area.width) {
                            area.width = (int) this_width;
                            changed = true;
                        }
                        double this_height = (creatY + currentEll.getHeight() + 12);
                        if (this_height > area.height) {
                            area.height = (int) this_height;
                            changed = true;
                        }
                        //Подправим новый эллипс при необходимости.
                        currentEll = new MyEll(creatX, creatY);
                        //Добавим новый эллипс на экран
                        myProject.getMyTaksArray().addPaintTaksEll(currentEll);
                    }
                    repaint();
                }
                //Релизд на левой кнопке мыши при выбранном "Таксономия" "Линия" рисует линию
                //При выборе меню фигур - линия получим вторую точку.
                if ("Линия".equals(MyJFrame.getWork())) {
                    if (myProject.getMyTaksArray().findPaintEllB(event.getPoint())) {//Если мышь уже была пресед на овале и сейчас релизед на овале.
                        MyEll lineSecondEll =  myProject.getMyTaksArray().findPaintEll(event.getPoint());
                        if (makeFirstEll != null && makeFirstEll != lineSecondEll) {//Проверки на непринадлежность второй точки линии одному и тому же эллипсу. 
                            if(!myProject.getMyTaksArray().findLineText1EllText1B(lineSecondEll.getText(), makeFirstEll.getText())){//надо проверить что обратной линии нет. Иначе что-то сказать.
                                
                                //Создадим линию связи и сохраним ее в массиве линий отношений таксономии.
                                //myProject.getMyTaksArray().getMyLineArray().getLineArray().add(new MyLine2(makeFirstEll, lineSecondEll));
                                
                                myProject.getMyTaksArray().uniqueMyLinePlus(myProject.getMyTaksArray().getMyLineArray().getLineArray(), new MyLine2(makeFirstEll, lineSecondEll));
                                myProject.setSaving(false);//Данные Проекта могут быть изменены, Проект требует сохранения.
                            }else{
                                JOptionPane.showMessageDialog(null," Существует отношение обратное создаваемому.");
                            }
                        }
                        //При релизед первому выделенному эллипсу вернем черный цвет.
                        if (makeFirstEll!=null) {
                            makeFirstEll.setColorEll(Color.BLACK);
                        }
                        changed = true;
                        //repaint();
                    }
                     //выключим рисование хвоста за мышей
                            lineDrag = false;
                            makeEll=null;
                            makeFirstEll=null;
                            
                            
                    
                }

                //При выборе меню фигур - Стрелка-указатель снимем цветовое выделение.
                //Если мышь уже переместила овал и сейчас релизед на овале.
                if ("Стрелка-Указатель".equals(MyJFrame.getWork())&&makeEll != null) {
                    //При релизед перемещенному выделенному эллипсу вернем черный цвет.
                    makeEll.setColorEll(Color.BLACK);
                    changed = true;
                    makeEllText = "";
                    makeEll = null;
                    repaint();
                }
                
            }//при релизд на левой кнопке мыши

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
        public void mouseClicked(MouseEvent e) {
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
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                //изменяем текст на двойном клике
                if ((e.getClickCount() >= 2)) {
                    //изменить слово в эллипсе:
                    //находим выбранный мышкой эллипс
                    MyEll changeTextEll =  myProject.getMyTaksArray().findPaintEll(e.getPoint());
                    if (changeTextEll != null) {
                        //получим старый текст
                        String textEllOld = changeTextEll.getText();
                        //изменить слово в эллипсе
                        String oldText = changeTextEll.getText();
                        int countNumbet=0;
                        String textEll = JOptionPane.showInputDialog("Изменяем слово в эллипсе", changeTextEll.getText());
                        if (textEll == null||textEll.equals(oldText)) {
                            countNumbet=1;
                            textEll = oldText;
                        }
                        //найти на экране все эллипсы с новым текстом//собрать их в один и нарисовать его с текущими координатами
                        ArrayList <MyEll> countMyEll= new ArrayList <MyEll>();
                        for(MyEll myEll: myProject.getMyTaksArray().getPaintTaksEll() ){
                            if(myEll.getText().equals(textEll)){
                                countMyEll.add(myEll);
                            }
                        }
                        if(countMyEll.size()>countNumbet){//если на экране еще были найдены эллипсы (кроме измененного)
                            for(MyEll myEll: countMyEll){
                                //добавим в Изменяемый эллипс скрытые объекты найденного(тезки) дубликата
                                changeTextEll.getMyTaksArrayHide().getPaintTaksEll().addAll(myEll.getMyTaksArrayHide().getPaintTaksEll());
                                //удалим дубликат
                                myProject.getMyTaksArray().getPaintTaksEll().remove(myEll);
                                //Данные Проекта могут быть изменены, Проект требует сохранения.
                                myProject.setSaving(false);
                            }
                        }
                        //Присвоить тексту в эллипсе новое значение. Подогнать размер эллипса под размер текста.
                        changeTextEll.setText(textEll);
                        myProject.getMyTaksArray().renewEllTextAndLine(changeTextEll, textEllOld);//перепривяжем все линии к/от эллипса
                        myProject.getMyTaksArray().renewEllTextAndLine(changeTextEll, textEll);//перепривяжем все линии к/от эллипса
                        myProject.getMyTaksArray().removeErrorLine();
                        myProject.setSaving(false);//Данные Проекта могут быть изменены, 
                        //Отобразим изменения.
                        changed = true;
                        //просчитаем видимую область экрана после изменения текста
                        //если мышь выходит за левую границу формы, рисуем у края.
                        double dX = e.getX() - changeTextEll.getWidth() / 2;
                        if (dX < 0) {
                            dX = 0;
                        }
                        //если мышь выходит за верхнюю границу формы, рисуем у края.
                        double dY = e.getY() - changeTextEll.getHeight() / 2;
                        if (dY < 0) {
                            dY = 0;
                        }
                        //установим новую границу области рисунка отношения
                        double this_width = (dX + changeTextEll.getWidth() + 12);
                        if (this_width > area.width) {
                            area.width = (int) this_width;
                            changed = true;
                        }
                        double this_height = (dY + changeTextEll.getHeight() + 12);
                        if (this_height > area.height) {
                            area.height = (int) this_height;
                            changed = true;
                        }
                        changeTextEll = null;
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
        /**
         * Перемещение мыши
         */
        public void mouseMoved(MouseEvent e) {
            //отметим в Таксономии выбранный объект
            //при прохождении мышки над элипсом или линией
            
            if (myProject.getMyTaksArray().findPaintEllB(e.getPoint())
                    || myProject.getMyTaksArray().getMyLineArray().findMyLineB(e.getPoint())
                    ||myProject.getMyTaksArray().findPaintEllDetailB(e.getPoint())) {
                //if (myProject.getMyTaksArray().findEll(e.getPoint().getX(), e.getPoint().getY())!=null){    
                //при прохождении мышки над элипсом или линией меняется курсор на руку
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
               
                //jLabel1.setText(" Курсор проходит над эллипсом");
            } else {
                setCursor(Cursor.getDefaultCursor());
                
                
                //jLabel1.setText(text);
            }
            
           
        }  // +public void mouseMoved(MouseEvent mouseevent

        /**         * перемещение выбранной фигуры         */
        @Override
        public void mouseDragged(MouseEvent e) {
            //если выбрано выделение области
            if(pressedBtn){
                double x, y, w, h;
                if (e.getX() > (int) press.getX()) {
                    x = press.getX();
                    w = (double) e.getX() - press.getX();
                } else {
                    x = (double) e.getX();
                    w = press.getX() - (double) e.getX();
                }
 
                if (e.getY() > (int) press.getY()) {
                    y = press.getY();
                    h = (double) e.getY() - press.getY();
                } else {
                    y = (double) e.getY();
                    h = press.getY() - (double) e.getY();
                }
                
                setRect(new Rectangle2D.Double(x, y, w, h));
                //maikeGroup(new Rectangle2D.Double(x, y, w, h));//соберем объекты для копирования
                
            }    
                
            //перемещение по нажатию правой кнопки мыши
            //if (e.getModifiers()==MouseEvent.BUTTON3_MASK)
            //перемещение при клике на левой кнопке мышки
            //Указание на пересчет видимой области экрана
            boolean changed = false;
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
                //рисуем хвост линии при флаге lineDrad == true
                if ("Линия".equals(MyJFrame.getWork()) && makeFirstEll != null && lineDrag) {
                    myLineDrag.setLine(makeFirstEll, e.getPoint());
                    
                }
                //перемещение по нажатию кнопки перемещение 
                if ("Стрелка-указатель".equals(MyJFrame.getWork()) && makeEll!=null) {
                    //берем выбраный мышей эллипс 
                    if (myProject.getMyTaksArray().findPaintEllB(e.getPoint())) {
                        //makeEll = myProject.getMyTaksArray().findPaintEll(makeEllText);
                        //}
                        //если мышь выходит за левую границу формы, рисуем у края.
                        double draggX = e.getX() - makeEll.getWidth() / 2;
                        if (draggX < 0) {
                            draggX = 0;
                        }
                        //если мышь выходит за верхнюю границу формы, рисуем у края.
                        double draggY = e.getY() - makeEll.getHeight() / 2;
                        if (draggY < 0) {
                            draggY = 0;
                        }

                        //установим новую границу области рисунка отношения
                        double this_width = (draggX + makeEll.getWidth() + 12);
                        if (this_width > area.width) {
                            area.width = (int) this_width;
                            changed = true;
                        }
                        double this_height = (draggY + makeEll.getHeight() + 12);
                        if (this_height > area.height) {
                            area.height = (int) this_height;
                            changed = true;
                        }
                        //собственно перемещение эллипса
                        //if()
                        makeEll.setFrame(draggX, draggY, makeEll.getWidth(), makeEll.getHeight());
                        //Данные Проекта могут быть изменены, Проект требует сохранения.
                        myProject.setSaving(false);
                        if (!myProject.getMyTaksArray().getMyLineArray().getLineArray().isEmpty()) {
                            //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
                            for (int i = 0; i < myProject.getMyTaksArray().getMyLineArray().getLineArray().size(); i++) {
                                //перебираем все линии массива линий
                                MyLine2 ml = (MyLine2) myProject.getMyTaksArray().getMyLineArray().getLineArray().get(i);
                                //переназначаем исходящие линии для перемещаемого эллипса .contains - стреляет неточно.
                                //if (ml.getText1().contains(makeEll.getText())) ml.setLine(makeEll, (MyEll) myProject.getMyTaksArray().findEll(ml.getText2()));
                                //переназначаем исходящие линии для перемещаемого эллипса
                                if (ml.getText1().equals(makeEll.getText())) {
                                    ml.setLine(makeEll,  myProject.getMyTaksArray().findPaintEll(ml.getText2()));
                                }
                                //переназначаем входящие линии для пнрнмещаемого эллипса
                                if (ml.getText2().equals(makeEll.getText())) {
                                    ml.setLine( myProject.getMyTaksArray().findPaintEll(ml.getText1()), makeEll);
                                }
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
            }//if(e.Modifires==MouseEvent.BUTTON1_MASK)
        }//MyMove.mouseDragged
    }  //-private class MyMove implements MouseMotionListener 

    public MyProject getMyProject() {
        return this.myProject;
    }

    /**Включим пункты всплывающего меню*/
    private void initComponentMenuPopup(){
        //delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,java.awt.Event.CTRL_MASK));
        //delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,java.awt.Event.DELETE));
        delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        popup.add(cop);
        popup.add(paste);
        popup.add(delete);
        popup.add(deleteTree);
        popup.add(positioned);
        cop.setEnabled(false);//Временно. Пока не начата обработка команды.
        delete.setEnabled(false);//Временно. Пока не начата обработка команды.
        deleteTree.setEnabled(false);//Временно. Пока не начата обработка команды.
        paste.setEnabled(false);//Временно. Пока не начата обработка команды.
        positionedStar.setEnabled(false);//Временно. Пока не назначена обработка команды.
        cop.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s = evt.getActionCommand();
                MyJFrame.myTaksArrayCopyPassed=true;//это сигнал сформировать массив копируемых элементов, убрать выделение области копирования и отобразить/скрыть нужные кнопки.
                }
        });
        paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s = evt.getActionCommand();
                pasteX=popupX;
                pasteY=popupY;
                //меню Вставить 
                MyJFrame.pasteMakeGroupPassed=true;
            }
        });
        delete.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s = evt.getActionCommand();
                MyJFrame.myTaksArrayDeletePassed=true;//это сигнал сформировать массив копируемых элементов и убрать выделение области копирования для Таксономии.
                
            }
            
        });
        deleteTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s = evt.getActionCommand();
                //1 взяли эллипс для удаления.
                MyEll deleteEll =  myProject.getMyTaksArray().findPaintEll(new Point(popupX, popupY));
                if (deleteEll != null) {
                    myProject.getMyTaksArray().removePaintEllipseTree(deleteEll);
                    //Данные Проекта могут быть изменены, Проект требует сохранения.
                    myProject.setSaving(false);
                    //Если выбран Круг.
                    //проверки на принадлежность первой точки линии эллипсу 

                    repaint();
                }
            }
        });
        positionedTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s = evt.getActionCommand();
                //если выделенный объект это эллипс
                MyEll ferstTreeEll =  myProject.getMyTaksArray().findPaintEll(new Point(popupX, popupY));
                if (ferstTreeEll != null) {
                    //- собираем расширенное дерево с вершиной в выбранном объекте.
                    //ArrayList <MyEll> treeEll = myProject.getMyTaksArray().makeEllTreeExtended(ferstTreeEll);
                    //- располагаем первый объект нижнего уровня дерева на линии с вершиной.
                    myProject.getMyTaksArray().bildPaintTree(ferstTreeEll);
                    //Данные Проекта могут быть изменены, Проект требует сохранения.
                    myProject.setSaving(false);
                }
                myProject.getMyTaksArray().setResizeWindow(true);//подгоним размер экрана под расположенные данные

            }
        });
        //System.out.println("Узнаем размеры экрана "+this.getSize().height + "   " +getSize().width);

    
    }
    /**Включим пункты меню*/
    public void initComponentMenuToolBar(){
        MyJFrame.strelkaUkazatel.setEnabled(true);//Активизируем кнопку Стрелка-указатель.
        MyJFrame.krug.setEnabled(true);//Активизируем кнопку Круг.
        MyJFrame.liniyaState.setEnabled(false);//Деактивизируем кнопку Линия.
        MyJFrame.liniyaTaks.setEnabled(true);//Активизируем кнопку Линия.
        MyJFrame.liniyaCompos.setEnabled(false);//Деактивизируем кнопку liniyaCompos.
        MyJFrame.time.setEnabled(false);//Деактивизируем кнопку time
        if (MyJFrame.time.isSelected()) {//Подправим выбор
            MyJFrame.strelkaUkazatel.setSelected(true);//значение по-умолчанию
        }
        if (MyJFrame.liniyaState.isSelected()) {//Подправим выбор
            MyJFrame.liniyaTaks.setSelected(true);//значение по-умолчанию
        }
        if (MyJFrame.liniyaCompos.isSelected()) {//Подправим выбор
            MyJFrame.liniyaTaks.setSelected(true);//значение по-умолчанию
        }
        
        //Активизируем кнопку Копировать.(Copy) если есть выделенный для копирования объект
        if(rect.getWidth()+rect.getHeight()>0){
            MyJFrame.copyJButton.setEnabled(true);
            MyJFrame.deletejButton.setEnabled(true);
        }else{
            MyJFrame.copyJButton.setEnabled(false);
            MyJFrame.deletejButton.setEnabled(false);
        }
        //Активизируем кнопку Вставить.(Paste) если есть скопированный объект
        if(MyJFrame.getMyTaksArrayCopy().getPaintTaksEll().size()>0){
            MyJFrame.pastejButton.setEnabled(true);
        }else{
            MyJFrame.pastejButton.setEnabled(false);
        }
        
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
        MyJFrame.liniyaState.setEnabled(false);
        //Деактивизируем кнопку Линия1.
        //MyJFrame.liniya1.setEnabled(false);
        //ДеАктивизируем кнопку Линия.
        MyJFrame.liniyaTaks.setEnabled(false);
        //Деактивизируем кнопку liniyaCompos.
        MyJFrame.liniyaCompos.setEnabled(false);
        //Деактивизируем кнопку time
        MyJFrame.time.setEnabled(false);
        //Подправим выбор
        if (MyJFrame.time.isSelected()) {
            MyJFrame.strelkaUkazatel.setSelected(false);//значение по-умолчанию
        }
        if (MyJFrame.liniyaState.isSelected()) {
            MyJFrame.liniyaTaks.setSelected(false);//значение по-умолчанию
        }
        if (MyJFrame.liniyaCompos.isSelected()) {
            MyJFrame.liniyaTaks.setSelected(false);//значение по-умолчанию
        }
        
        //ДеАктивизируем кнопку Копировать.(Copy)
        MyJFrame.copyJButton.setEnabled(false);
        MyJFrame.deletejButton.setEnabled(false);
        //ДеАктивизируем кнопку Вставить.(Paste)
        MyJFrame.pastejButton.setEnabled(false);
        
        
        //ДеАктивизируем кнопку Выстроить Дерево
        MyJFrame.positionedTree.setEnabled(false);
        
       
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
        //ДеАктивизируем пункт меню Импорт.
        MyJFrame.inport.setEnabled(false);
    }
    
    //повесим слушателя на переключение вкладки
    private class MyAncestorListener implements AncestorListener
     {
        public void actionPerformed(AncestorEvent e) {}
        @Override
        public void ancestorAdded(AncestorEvent ae) {
            //выполняется при переключении на вкладку Таксономии.
            initComponentMenuToolBar();
            myProject.getMyTaksArray().setResizeWindow(true);//подгоним размер экрана под расположенные данные
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
    public void setRect(Rectangle2D rect) {
        this.rect = rect;
        repaint();
    }
    
    /**Метод, который выделяет цветом все объекты, что попадают в выделенную мышкой область.
     Если в выделенной области нет ни одного эллипса, кнопка Копировать не активируется. 
     Однако:
     Линия выделяется только в том случае, эсли выделен Начальный и Конечный объект.
     Эллипс выделяется только в том случае, если выделена одна из его 4-х середин сторон.*/
    private void makeGroupColorOn(Rectangle2D rect){
        //проверим, что выделенная область имеет ненулевой размер
        if(this.rect.isEmpty()){
            if(MyJFrame.copyJButton.isEnabled()){
                    MyJFrame.copyJButton.setEnabled(false);
                    MyJFrame.deletejButton.setEnabled(false);
                    //cop.setEnabled(false);//пункт попап меню копировать Неактивен
                    //delete.setEnabled(false);//пункт попап меню удалить Неактивен
                    
                }
            return;
        }
        //просмотрим все эллипсы .  если точка эллипса присутствует в выделенной области, отмечаем этот эллипс зеленым цветом.
        
        boolean isMakeEll=false;//если при проходе ни один эллипс не вошел в выделенную область, кнопку копировать не активировать.
        for(MyEll myEll: myProject.getMyTaksArray().getPaintTaksEll()){
            boolean makeEll=false;
            
            for(MyPoint1 myPoint1:myEll.getMyEllPoints()){
                if(this.rect.contains(myPoint1)){
                    makeEll=true;
                }
            }
            myEll.setColorEll(Color.black);
            if(makeEll){
                myEll.setColorEll(makeGroupColor);
                isMakeEll=true;
            }
            
        }
        if(isMakeEll){
            if(!MyJFrame.copyJButton.isEnabled()){
                    MyJFrame.copyJButton.setEnabled(true);
                    MyJFrame.deletejButton.setEnabled(true);
                    //cop.setEnabled(true);//пункт попап меню копировать Неактивен
                    //delete.setEnabled(true);//пункт попап меню удалить Неактивен
                }
        }
        //просмотрим все линии .  если точка начала и точка конца линии присутствуют  в выделенной области, отмечаем эту линию оранжевым цветом.
        for(MyLine myLine: myProject.getMyTaksArray().getMyLineArray().getLineArray()){
            if(this.rect.contains(myLine.getP1())&&this.rect.contains(myLine.getP2())){
                myLine.setColorLine(makeGroupColor);
            }else{
                 myLine.setColorLine(Color.black);
            }
        }
    }
    /**Метод, который возвращает цвет по-умолчанию всем объектам, что попадают в выделенную мышкой область*/
    private void makeGroupColorOff(Rectangle2D rect){
        //просмотрим все эллипсы .  если точка эллипса присутствует в выделенной области, отмечаем этот эллипс зеленым цветом.
        for(MyEll myEll: myProject.getMyTaksArray().getPaintTaksEll()){
            for(MyPoint1 myPoint1:myEll.getMyEllPoints()){
                if(this.rect.contains(myPoint1)){
                    myEll.setColorEll(Color.black);
                }
            }
        }
        //просмотрим все линии .  если точка начала и точка конца линии присутствуют  в выделенной области, отмечаем эту линию оранжевым цветом.
        for(MyLine myLine: myProject.getMyTaksArray().getMyLineArray().getLineArray()){
            if(this.rect.contains(myLine.getP1())&&this.rect.contains(myLine.getP2())){
                myLine.setColorLine(Color.BLACK);
            }
        }
        
    //this.rect.contains(press);//истина, если точка принадлежит многоугольнику
    
    }
    /**Метод собирает в группу MyJFrame.myTaksArrayCopy выделенные для копирования объекты (линии и эллипсы) таксономии.
     Сбор выполним по каждому полю каждого класса, чтоб исключить */
    private void makeGroupCopyOn(){
        if((rect.getHeight()+rect.getWidth())>1&&currentMakeEll==null){//если нарисуем область выделения
            MyJFrame.setMyTaksArrayCopy(new MyTaksArray());//очистим группу для копирования.
            for(MyEll myEll: myProject.getMyTaksArray().getPaintTaksEll()){//просмотрим массив эллипсов и массив линий.
                if(myEll.getColorEll().equals(makeGroupColor)){//если эллипс выднлен цветом выделения
                    MyEll myEllNew=myEll.copyTo();//создадим новый объект//скопируем в него поля выделенного объекта
                    MyJFrame.getMyTaksArrayCopy().getPaintTaksEll().add(myEllNew);//добавим созданный объект к массиву объектов для копирования
                }
            }
            for(MyLine myLine: myProject.getMyTaksArray().getMyLineArray().getLineArray()){
                if(myLine.getColorLine().equals(makeGroupColor)){
                    MyLine2 myLineNew= (MyLine2) myLine.copyTo();
                    MyJFrame.getMyTaksArrayCopy().getMyLineArray().getLineArray().add(myLineNew);//добавим созданный объект к массиву объектов для копирования
                }
            }
            return;
        }else{//Если выбран Круг.
            if(currentMakeEll!=null){
                MyEll copEll =  myProject.getMyTaksArray().findPaintEll(new Point(popupX, popupY));
                if (copEll != null) {//проверки на принадлежность точки  эллипсу 
                    MyJFrame.setMyTaksArrayCopy(new MyTaksArray());//очистим группу для копирования.
                    MyEll myEllNew=copEll.copyTo();//скопируем в него поля выделенного объекта
                    MyJFrame.getMyTaksArrayCopy().getPaintTaksEll().add(myEllNew);//добавим созданный объект к массиву объектов для копирования
                    //return;
                }
                currentMakeEll=null;
            }
        }
    //Если найдем объект, цвет которого равен makeGroupColorLine для линии или makeGroupColorEll для эллипса, берем такой объект в группу для копирования.
    }
    /**Метод собирает в группу выделенные для копирования объекты (линии и эллипсы) таксономии.
     Сбор выполним по каждому полю каждого класса, чтоб исключить
    private void makeGroupDeleteOn(){
        //очистим группу для копирования.
        MyJFrame.setMyTaksArrayCopy(new MyTaksArray());
        //просмотрим массив эллипсов и массив линий.
        for(MyEll myEll: myProject.getMyTaksArray().getPaintTaksEll()){
            //если эллипс выднлнн цветом выделения
            if(myEll.getColorEll().equals(makeGroupColor)){
                //создадим новый объект
                //скопируем в него поля выделенного объекта
                MyEll myEllnew=new MyEll(myEll.getX(),myEll.getY(),myEll.getText());
                //добавим созданный объект к массиву объектов для копирования
                MyJFrame.getMyTaksArrayCopy().getPaintTaksEll().add(myEllnew);
            }
        }
        
    //Если найдем объект, цвет которого равен makeGroupColorLine для линии или makeGroupColorEll для эллипса, берем такой объект в группу для копирования.
    }
     */
    /**выбираем пункт всплывающего меню Удалить, либо нажимаем кнопку удалить при выделенной областью группы объектов на экране*/
    private void makeDeleteRun(){
        //если есть выделенные элементы, то их можно удалить
        if(pressedBtn){//если нарисуем область выделения
            //makeGroupCopyOn();//соберем выделенные объекты в класс для копирования
            //уберем выделение
            press = null;
            pressedBtn = false;
            makeGroupColorOff(rect);//снимем выделение с выбранной группы
            rect=new Rectangle2D.Double();  //восстановим пустую область выделения
            //MyJFrame.myTaksArrayCopyPassed=false;//восстановим индикатор нажима кнопки Копировать
            //MyJFrame.copyJButton.setEnabled(false);
            //MyJFrame.deletejButton.setEnabled(false);
            for(MyEll deleteEll:MyJFrame.getMyTaksArrayCopy().getPaintTaksEll()){
                //Удаляем эллипс и все его линии, если они есть.
                myProject.getMyTaksArray().removePaintEllipse(deleteEll);
            }
            //очистим группу для копирования.
            MyJFrame.setMyTaksArrayCopy(new MyTaksArray());
        
            return;
        }
        //Если выбран Круг.
        //проверки на принадлежность точки линии эллипсу 
        if(popupX!=0&&popupY!=0){
        MyEll deleteEll =  myProject.getMyTaksArray().findPaintEll(new Point(popupX, popupY));
        if (deleteEll != null) {
            //Удаляем эллипс и все его линии, если они есть.
            myProject.getMyTaksArray().removePaintEllipse(deleteEll);
            //Данные Проекта могут быть изменены, Проект требует сохранения.
            myProject.setSaving(false);
        }
        //Если выбран Линия.
        if (myProject.getMyTaksArray().getMyLineArray().findMyLineB(new Point(popupX, popupY))) {
            MyLine deleteLine = myProject.getMyTaksArray().getMyLineArray().findMyLine(new Point(popupX, popupY));
            //Удалить ее.
            myProject.getMyTaksArray().removeLine(deleteLine);
            //Данные Проекта могут быть изменены, Проект требует сохранения.
            myProject.setSaving(false);
        }
        //MyJFrame.deletejButton.setEnabled(false);
        //repaint();
        //return;
        }
    }
    private void pasteMakeGroup(){
        //вставим скопированные объекты из вкладки Таксономии, Если они там есть.
        if(MyJFrame.getMyTaksArrayCopy().getPaintTaksEll().size()>0){//если есть скопированные эллипсы
            if(pasteX!=0&&pasteY!=0){//если выбрана точка вставки, изменим координаты вершины вставки на нее
                MyJFrame.getMyTaksArrayCopy().getPaintTaksEll().get(0).setFrame(pasteX, pasteY, MyJFrame.getMyTaksArrayCopy().getPaintTaksEll().get(0).getWidth(),MyJFrame.getMyTaksArrayCopy().getPaintTaksEll().get(0).getHeight());
            }
            //добавим эллипсы
            for(MyEll myEll:MyJFrame.getMyTaksArrayCopy().getPaintTaksEll()){
                
                myProject.getMyTaksArray().addPaintTaksEll(myEll.copyTo());
            }
            
            //добавим линии
            for(MyLine myLine:MyJFrame.getMyTaksArrayCopy().getMyLineArray().getLineArray()){ //Смотрим каждую скопированную линию
                //Если между эллипсами Проекта с текстом начала и конца линии есть линия, 
                //увеличим счетчик линий на величину счетчика вставляемой линии.
                //Иначе, рисуем новую линию между ними.
                myProject.getMyTaksArray().uniqueMyLinePlus(myProject.getMyTaksArray().getMyLineArray().getLineArray(), myLine.copyTo());
            }
        }
    }
    
    
    
}//CustomJPanel
