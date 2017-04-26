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
 * @author 655 Вкладка панели для отображения отношения Композиции
 */
class CustomJPanelComposition extends JPanel {
     private static final long serialVersionUID = 112223345678L; // Change number as appropriate
    
    /**для постоянного перерисовывания текста элипса */
    private MyEll currentEll;
    /**для выделения активного элипса */
    //private MyEll currentMakeEll;
    /**Получаем  Текущий Проект для отрисовки*/
    private final MyProject myProject;
    /**Получаем название таблицы отношения для отрисовки соответствующих данных проекта*/
    private final String pTable;
    //Указание на пересчет видимой области экрана
    public static boolean changed;
    private JPopupMenu popup; 
    private int popupX;
    private int popupY;
    //Меню по правой кнопке мыши
    JMenuItem cop;
    JMenuItem paste;
    JMenuItem delete;
    JMenuItem deleteTree;
    JMenu positioned;
    JMenuItem positionedTree;
    JMenuItem positionedStar;
    /**Указывает площадь, занимаемую графикой*/
    private Dimension area;
    /**Заготовка для выделения линии*/
    private MyLine makeLine;
    /**Фигура для перемещения*/
    private MyEll makeEll;
    /**Первый эллипс для линии*/
    private MyEll makeFirstEll;
    /**Фиксируем текст перемещаемого эллипса*/
    private String makeEllText;
    /** Флаг рисования хвоста для новой линии     */
    private boolean lineDrag;
    /** нарисованный хвост линии     */
    private MyLine2 myLineDrag;
    /**выделенная область левоу мышкой на экране*/
    private Rectangle2D rect;
    //private static Image img = null;
    private Point2D press = new Point2D.Double(0, 0);
    
    private boolean pressedBtn = false;
    /**Цвет выделения рамки, линий и Эллипсов*/
    private Color makeGroupColor;
    
    /**В конструкторе получаем текущий проект и название вида отношений для отрисовки соответствующих данных проекта*/
    CustomJPanelComposition( final MyProject myProject, final String pTable){
        this.rect = new Rectangle2D.Double();
        changed = false;
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
        
        this.addAncestorListener(new MyAncestorListener());//добавим слушателя для хождения по вкладкам
        addMouseListener(new MyMouse());
        addMouseMotionListener(new MyMove());
        
        area = myProject.getMyComposArray().getWindowSize();//указавает площадь, занимаемую графикой.
        
        initComponentMenuPopup();//добавим всплывающее меню
        initComponentMenuToolBar();
        makeGroupColor=Color.BLUE;//назначим цветом выделения  синий цвет.
        
        //System.out.println("Узнаем размеры экрана "+this.getSize().height + "   " +getSize().width);
    }
    
    /**
     * Находим максимальные значения координат эллипсов на экране. Сохраняем их
     * как размер экрана. Раздвигаем экран до их размеров.
     */
    public void makeArea() {
        double ax = 0;
        double ay = 0;
        for (MyEll ell : myProject.getMyComposArray().getPaintEll()) {
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
        Graphics2D g2=(Graphics2D) g;
        if (pressedBtn) {//нарисуем область выделения
            makeGroupColorOn(rect);//покажем собираемые объекты для копирования
            float dash1[] = {10.0f};//рисуем пунктирную линию
            g2.setStroke(new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,dash1, 0.0f));//рисуем пунктирную линию
            g2.setColor(this.makeGroupColor);//установим цвет для рисования объекта
            g2.draw(rect);
            g2.setStroke(new BasicStroke() );//устанавливаем стандартную линию
            g2.setColor(Color.black);//вернем цвет для рисования
        }
        if(MyJFrame.myComposArrayCopyPassed){//если кнопка Копировать нажата
            makeGroupCopyOn();//соберем выделенные объекты в класс для копирования
            press = null;//уберем выделение
            pressedBtn = false;//уберем выделение
            makeGroupColorOff(rect);//снимем выделение с выбранной группы
            rect=new Rectangle2D.Double();  //уберем выделение
            MyJFrame.myComposArrayCopyPassed=false;//восстановим индикатор нажима кнопки
            MyJFrame.copyJButton.setEnabled(false);
            MyJFrame.deletejButton.setEnabled(false);
        }
        if(MyJFrame.myComposArrayDeletePassed){//если кнопка удалить нажата
            makeGroupDeleteOn();//соберем выделенные объекты в класс для копирования
            makeDeleteRun();//произведем удаление
            
            press = null;//уберем выделение
            pressedBtn = false;//уберем выделение
            makeGroupColorOff(rect);//снимем выделение с выбранной группы
            rect=new Rectangle2D.Double();  //уберем выделение
            //восстановим индикатор нажима кнопки
            MyJFrame.myComposArrayDeletePassed=false;//восстановим кнопку
        }
        if(MyJFrame.pasteMakeGroupPassed){//если кнопка Вставить нажата
            pasteMakeGroup();
            MyJFrame.pasteMakeGroupPassed=false;
        }
        if(!this.myProject.isSaving()){//экранная версия Проекта отличается от сохраненной
            //Активизируем кнопку Сохранить.(Save)
            MyJFrame.saveProjectJButton.setEnabled(true);
            //Активизируем пункт меню Сохранить.(Save)
            MyJFrame.saveProjectJMenuItem.setEnabled(true);
        } else{
            //Активизируем кнопку Сохранить.(Save)
            MyJFrame.saveProjectJButton.setEnabled(false);
            //Активизируем пункт меню Сохранить.(Save)
            MyJFrame.saveProjectJMenuItem.setEnabled(false);
        }  
        //Рисуем эллипсы из хранилища для рисования отношений .
        for (int j=0; j< myProject.getMyComposArray().getPaintEll().size();j++){
            //достаем текст объекта circleRect.get(j)
            currentEll=myProject.getMyComposArray().getPaintEll().get(j);

            currentEll.drawEllWithOutDetail(g2);//нарисуем фигуру

        }
            
            
            //рисуем линии Классов Composition отношений.
            //if(!myProject.getMyComposArray().getMyLineArray().getLineArray().isEmpty()){
            for (int lik=0; lik< myProject.getMyComposArray().getMyLineArray().getLineArray().size();lik++){
                MyLineCompos myLine=(MyLineCompos)myProject.getMyComposArray().getMyLineArray().getLineArray().get(lik);//достаем линию объекта Composition отношение
                myLine.drawLine(g2,myLine);//рисуем линию со всеми прибамбасами
                
            } 
            
            //если ведем линию, нарисуем ее след на экране.
        if (lineDrag && myLineDrag != null) {
            //установим цвет для линии
            g2.setColor(Color.BLUE);
            g2.draw((Line2D) myLineDrag);
        }
            //Запишем количество линий(эллипсов) на экране
            MyJFrame.dispLinejLabel.setText(" "+myProject.getMyComposArray().getMyLineArray().getLineArray().size()+"("
                    +myProject.getMyComposArray().getPaintEll().size()+") ");
        //Если есть необходимость пересчета окна , пересчитаем его размер
        if(myProject.getMyComposArray().isResizeWindow()){
            makeArea();
            changed=true;
            myProject.getMyComposArray().setResizeWindow(false);
            //System.out.println("MyComposArray= "+myProject.getResizeWindow());
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
            //при прессед на правой кнопке мыши
            if (SwingUtilities.isRightMouseButton(event)){
                boolean nothing=true;
                if(MyJFrame.pastejButton.isEnabled()){
                    paste.setEnabled(true);
                }
                //Показать всплывающее меню.
                popup.show(event.getComponent (), event.getX(), event.getY()); 
                popupX=event.getX();
                popupY=event.getY();
                
                    //Подсветить объект, на котором пресед.
                if(pressedBtn){//если выделенная область есть на экране
                    if(rect.contains(event.getPoint())){//если мышь пресед внутри выделенной области
                        cop.setEnabled(true);//пункт попап меню копировать активен
                        delete.setEnabled(true);//пункт попап меню удалить активен
                        nothing=false;
                    }
                }
                    //если мышь пресед на линии
                    //проверки на принадлежность преседной точки линии
                    if(myProject.getMyComposArray().getMyLineArray().findMyLineB(event.getPoint())){
                        makeLine = myProject.getMyComposArray().getMyLineArray().findMyLine(event.getPoint());
                        //если мышка кликает на линии, она становится голубым.
                            makeLine.setColorLine(Color.BLUE);
                            //Изменим цвет фигур начала и конца линии.
                            myProject.getMyComposArray().setColorEllLine(makeLine, Color.RED);
                            changed=true;
                            nothing=false;
                    }
                            
                    //Если мышь пресед на эллипсе.
                    //Проверки на принадлежность преседной точки эллипсу.
                    if(myProject.getMyComposArray().findPaintEllB(event.getPoint())){
                            makeEll = myProject.getMyComposArray().findPaintEll(event.getPoint());
                            //если мышка кликает на эллипсе, он становится голубым.
                            makeEll.setColorEll(Color.BLUE);
                            //Изменим цвет линий к и от эллипса.
                            myProject.getMyComposArray().setColorLineEll(makeEll, Color.RED);
                            changed=true;
                            delete.setEnabled(true);
                            deleteTree.setEnabled(true);
                            
                            nothing=false;
                            
                    }
                    if(nothing){
                    cop.setEnabled(false);//пункт попап меню копировать активен
                    delete.setEnabled(false);//пункт попап меню удалить активен
                    
                }
            }//при прессед на правой кнопке мыши
            if (event.getModifiers()==MouseEvent.BUTTON1_MASK){//при клике на левой кнопке мышки
                //если мышь пресед на овале
                //Проверки на принадлежность преседной точки эллипсу.
                if(myProject.getMyComposArray().findPaintEllB(event.getPoint())){
                    makeEll = myProject.getMyComposArray().findPaintEll(event.getPoint());
                    //закончим выделение и отменим его
                        press = null;
                        pressedBtn = false;
                        makeGroupColorOff(rect);//снимем выделение с выбранной группы
                        rect=new Rectangle2D.Double();  
                        if(MyJFrame.copyJButton.isEnabled()){//Деактивируем кнопки Копировать
                            MyJFrame.copyJButton.setEnabled(false);
                            cop.setEnabled(false);
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
                }else{//если пресс был на пустом месте
                    if(!pressedBtn){//если это первый пресс выделения
                        press = event.getPoint();//выполним выделение
                        pressedBtn = true;
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
        }   // -public void mousePressed (MouseEvent mouseevent)
           
        public void mouseReleased(MouseEvent event) {
            lineDrag = false;//указание перестать вести тень линии
            boolean changed = false;//Указание на пересчет видимой области экрана
            if (SwingUtilities.isRightMouseButton(event)) {//при релизд на правой кнопке мыши
                //вернем выделенным объектам первоначальный цвет
                if(makeLine!=null){//проверки существование выделенной линии
                    makeLine.setColorLine(Color.BLACK);//Возвращаем линии черный цвет.
                    myProject.getMyComposArray().setColorEllLine(makeLine, Color.BLACK);//Возвращаем цвет фигурам начала и конца линии.
                    makeLine =null;//Снимаем выделение
                    changed=true;//Обновим рисунок на экране.
                }
                if(makeEll!=null){ //Проверка существования выделенного эллипса.
                    makeEll.setColorEll(Color.BLACK);//Возвращаем эллипсу черный цвет.
                    myProject.getMyComposArray().setColorLineEll(makeEll, Color.BLACK);//Возвращаем цвет линиям к и от эллипса
                    makeEll =null;//Снимаем выделение.
                    changed=true;//Обновим рисунок на экране.
                }
            }
            if (event.getModifiers()==MouseEvent.BUTTON1_MASK){//Релизд на левой кнопке мыши 
                if(makeLine!=null){//проверки существование выделенной линии
                    makeLine.setColorLine(Color.BLACK);//Возвращаем линии черный цвет.
                    myProject.getMyComposArray().setColorEllLine(makeLine, Color.BLACK);//Возвращаем цвет фигурам начала и конца линии.
                    makeLine =null;//Снимаем выделение.
                    changed=true;//Обновим рисунок на экране.
                }
                if(makeEll!=null){//Проверка существования выделенного эллипса.
                    makeEll.setColorEll(Color.BLACK);//Возвращаем эллипсу черный цвет.
                    myProject.getMyComposArray().setColorLineEll(makeEll, Color.BLACK);//Возвращаем цвет линиям к и от эллипса
                    makeEll =null;//Снимаем выделение.
                    //makeEllText="";
                    //makeFirstEll=null;
                    changed=true;//Обновим рисунок на экране.
                }
               
                if ("Круг".equals(MyJFrame.getWork())){ 
                    currentEll =  myProject.getMyComposArray().findPaintEll(event.getPoint());//добавить фигуру круг
                    if (currentEll == null&&!myProject.getMyComposArray().getMyLineArray().findMyLineB(event.getPoint()))  {//Убедимся, что создаем эллипс на пустом месте.
                        currentEll=new MyEll(0,0);//создадим заготовку для нового эллипса.
                        double creatX=event.getPoint().x-currentEll.getWidth()/2;//Просчитаем его (нового эллипса) расположение. Подправим при необходимости.
                        if (creatX<0){ creatX=0; }//если мышь выходит за левую границу формы, рисуем у края.
                        double creatY=event.getY()-currentEll.getHeight()/2;//если мышь выходит за верхнюю границу формы, рисуем у края.
                        if (creatY<0){ creatY=0; }
                        double this_width = (creatX + currentEll.getWidth() + 12);//установим новую границу области рисунка отношения
                        if (this_width > area.width) {
                            area.width = (int)this_width; changed=true;
                        }
                        double this_height = (creatY + currentEll.getHeight() +12);
                        if (this_height > area.height) {
                            area.height = (int)this_height; changed=true;
                        }
                        currentEll = new MyEll(creatX, creatY);//Подправим новый эллипс при необходимости.
                        myProject.getMyComposArray().addPaintEll(currentEll);//Добавим новый эллипс на экран
                    }
                    repaint();
                }
                //При выборе меню фигур - линия получим вторую точку.
                if  ("Линия".equals(MyJFrame.getWork())){
                    if(myProject.getMyComposArray().findPaintEllB(event.getPoint())){//Если мышь уже была пресед на овале и сейчас релизед на овале.
                        MyEll lineSecondEll =  myProject.getMyComposArray().findPaintEll(event.getPoint());
                        if (makeFirstEll!= null&&makeFirstEll!=lineSecondEll){//Проверки на принадлежность второй точки линии эллипсу или одному и тому же эллипсу. 
                            if(!myProject.getMyComposArray().findLineText1EllText1B(lineSecondEll.getText(), makeFirstEll.getText())){//надо проверить что обратной линии нет. Иначе что-то сказать.
                                //Создадим линию связи и сохраним ее в массиве линий отношений таксономии.
                                myProject.getMyComposArray().getMyLineArray().getLineArray().add(new MyLineCompos(makeFirstEll, lineSecondEll));
                                myProject.setSaving(false);//Данные Проекта могут быть изменены, Проект требует сохранения.
                            }else{
                                JOptionPane.showMessageDialog(null," Существует отношение обратное создаваемому.");
                            }
                        }
                        //При релизед первому выделенному эллипсу вернем черный цвет.
                        if (makeFirstEll!= null) {
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
                if  ("Стрелка-указатель".equals(MyJFrame.getWork())){
                //Если мышь уже переместила овал и сейчас релизед на овале.
                    changed=true;
                    makeEllText="";
                    makeEll=null;
                    repaint();
              
                }
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
            //при клике на левой кнопке мышки
            if (e.getModifiers()==MouseEvent.BUTTON1_MASK){
                //изменяем текст на двойном клике
                if ((e.getClickCount()>= 2)){
                    //изменить слово в эллипсе:
                    //находим выбранный мышкой эллипс
                    MyEll changeTextEll =  myProject.getMyComposArray().findPaintEll(e.getPoint());
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
                        myProject.getMyComposArray().renewEllTextAndLine(changeTextEll,textEllOld);
                        //Данные Проекта могут быть изменены, 
                        myProject.setSaving(false);
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
                if (myProject.getMyComposArray().findPaintEllB(e.getPoint())
                        ||myProject.getMyComposArray().getMyLineArray().findMyLineB(e.getPoint())){
                //if (myProject.getMyComposArray().findEll(e.getPoint().getX(), e.getPoint().getY())!=null){    
                    //при прохождении мышки над элипсом или линией меняется курсор на руку
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                    //jLabel1.setText(" Курсор проходит над эллипсом");

                }
                else{
                    setCursor(Cursor.getDefaultCursor());
                    //jLabel1.setText(text);
                }
                //}
                //if ("Линия2".equals(work)) {
                /*
                //при прохождении мышки над линией
               if (myProject.getMyComposArray().getMyLineArray().findMyLineB(e.getPoint())){
                   setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                //при прохождении мышки над линией меняется курсор на руку.
                else{
                    setCursor(Cursor.getDefaultCursor());
                }
                //}

                if ("Стрелка-указатель".equals(work)) {
                   //if (myProject.getMyComposArray().findEll(e.getPoint().getX(), e.getPoint().getY())!=null){
                    if (myProject.getMyComposArray().foundPaintTaksEllB(e.getPoint())){
                       setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }

                   //при прохождении мышки над элипсом меняется курсор на крестообразный и элипс становится жирны
                   else{
                       setCursor(Cursor.getDefaultCursor());
                   }
                }
                */
            //}//if (pTable=="Таксономии"){
        }  // +public void mouseMoved(MouseEvent mouseevent
        /** перемещение выбранной фигуры*/
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
            if (e.getModifiers()==MouseEvent.BUTTON1_MASK){
                //рисуем хвост линии при флаге lineDrad == true
                if ("Линия".equals(MyJFrame.getWork()) && makeFirstEll != null && lineDrag) {
                    myLineDrag.setLine(makeFirstEll, e.getPoint());
                }
                //отметим в Таксономии выбранный объект
                //if (pTable=="Таксономии"){
                    //перемещение по нажатию кнопки перемещение 
                    if ("Стрелка-указатель".equals(MyJFrame.getWork())&&makeEllText.length()>0) {
                        //берем выбраный мышей эллипс 
                        if( myProject.getMyComposArray().findPaintEll(e.getPoint())!=null){
                            makeEll=myProject.getMyComposArray().findPaintEll(makeEllText);
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
                            if(!myProject.getMyComposArray().getMyLineArray().getLineArray().isEmpty()){
                                //смотрим массив линий, пересчитываем исходящие и входящие линии (с привязкой к тексту эллипса)
                                for (int i=0;i<myProject.getMyComposArray().getMyLineArray().getLineArray().size();i++){
                                    //перебираем все линии массива линий
                                    MyLineCompos ml=(MyLineCompos) myProject.getMyComposArray().getMyLineArray().getLineArray().get(i);
                                    //переназначаем исходящие линии для перемещаемого эллипса .contains - стреляет неточно.
                                    //if (ml.getText1().contains(makeEll.getText())) ml.setLine(makeEll, (MyEll) myProject.getMyComposArray().findEll(ml.getText2()));
                                    //переназначаем исходящие линии для перемещаемого эллипса
                                    if (ml.getText1().equals(makeEll.getText())) ml.setLine(makeEll,  myProject.getMyComposArray().findPaintEll(ml.getText2()));
                                    //переназначаем входящие линии для пнрнмещаемого эллипса
                                    if (ml.getText2().equals(makeEll.getText()))  ml.setLine( myProject.getMyComposArray().findPaintEll(ml.getText1()), makeEll);
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
        JMenuItem positionedTree = new JMenuItem("В виде дереваа.");
        positionedTree.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/graf.png")));
        positioned.add(positionedTree);
        positionedStar = new JMenuItem("В виде звезды.");
        positionedStar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/icon/zvezd.png")));
        positioned.add(positionedStar);
        
        //delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,java.awt.Event.CTRL_MASK));
        //delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,java.awt.Event.DELETE));
        delete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,0));
        popup.add(cop); 
        popup.add(paste); 
        popup.add(delete);
        popup.add(deleteTree);
        popup.add(positioned);
        cop.setEnabled(false);//Временно. Пока не начата обработка команды.
        delete.setEnabled(false);//Временно. Пока не начата обработка команды.
        deleteTree.setEnabled(false);//Временно. Пока не начата обработка команды.
        paste.setEnabled(false);//Временно. Пока не назначена обработка команды.
        positionedStar.setEnabled(false);//Временно. Пока не назначена обработка команды.
        cop.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s=evt.getActionCommand();
                //меню Копировать 
                MyJFrame.myComposArrayCopyPassed=true;//это сигнал сформировать массив копируемых элементов и убрать выделение области копирования для Таксономии.
                //Активируем кнопку Вставить
                MyJFrame.pastejButton.setEnabled(true);
                //закончим выделение и отменим его
        //        press = null;
        //        pressedBtn = false;
       //         makeGroupColorOff(rect);//снимем выделение с выбранной группы
        //        rect=new Rectangle2D.Double();  
                ///if(MyJFrame.copyJButton.isEnabled()){//Деактивируем кнопки Копировать
                    MyJFrame.copyJButton.setEnabled(false);
                    cop.setEnabled(false);
                    delete.setEnabled(false);
                    MyJFrame.deletejButton.setEnabled(false);
                //}
            }
        });
        paste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s=evt.getActionCommand();
                //меню Вставить 
                 MyJFrame.pasteMakeGroupPassed=true;
                
            }
        });
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s=evt.getActionCommand();
                //меню Удалить
                //makeDeleteRun();
                MyJFrame.myComposArrayDeletePassed=true;//это сигнал сформировать массив копируемых элементов и убрать выделение области копирования для Таксономии.
                //makeDeleteRun();//меню Удалить
                cop.setEnabled(false);//пункт попап меню копировать Неактивен
                delete.setEnabled(false);//пункт попап меню удалить Неактивен
                MyJFrame.copyJButton.setEnabled(false);
                MyJFrame.deletejButton.setEnabled(false);
            }
        });
        deleteTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String s=evt.getActionCommand();
               //1 взяли эллипс для удаления.
                MyEll    deleteEll =  myProject.getMyComposArray().findPaintEll(new Point(popupX,popupY));
                if (deleteEll != null){
                    myProject.getMyComposArray().removePaintEllipseTree(deleteEll);
                    //Данные Проекта могут быть изменены, Проект требует сохранения.
                    myProject.setSaving(false);
                    //меню Удалить

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
                MyEll   ferstTreeEll =  myProject.getMyComposArray().findPaintEll(new Point(popupX,popupY));
                if (ferstTreeEll != null){
                    //располагаем объекты каждого нижнего уровня дерева от линии с вершиной вниз.
                    myProject.getMyComposArray().bildPaintTree(ferstTreeEll);
                    
                }  
                myProject.getMyComposArray().setResizeWindow(true);
                
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
        MyJFrame.liniyaState.setEnabled(false);
        //Деактивизируем кнопку Линия1.
        //MyJFrame.liniya1.setEnabled(false);
        //Деактивизируем кнопку Линия2.
        MyJFrame.liniyaTaks.setEnabled(false);
        //Активизируем кнопку Линия2.
        MyJFrame.liniyaCompos.setEnabled(true);
        //Деактивизируем кнопку time
        MyJFrame.time.setEnabled(false);
        //Подправим предварительный выбор кнопки time, liniyaState, liniyaTaks
        if(MyJFrame.time.isSelected())MyJFrame.strelkaUkazatel.setSelected(true);//значение по-умолчанию
        if(MyJFrame.liniyaState.isSelected())MyJFrame.liniyaCompos.setSelected(true);//значение по-умолчанию
        if(MyJFrame.liniyaTaks.isSelected())MyJFrame.liniyaCompos.setSelected(true);//значение по-умолчанию


        
        //Активизируем кнопку Копировать.(Copy) если есть выделенный для копирования объект
        if(rect.getWidth()+rect.getHeight()>0){
            MyJFrame.copyJButton.setEnabled(true);
            MyJFrame.deletejButton.setEnabled(true);
        }else{
            MyJFrame.copyJButton.setEnabled(false);
            MyJFrame.deletejButton.setEnabled(false);
        }
        //Активизируем кнопку Вставить.(Paste) если есть скопированный объект
        if(MyJFrame.getMyComposArrayCopy().getPaintEll().size()>0){
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
        //Деактивизируем кнопку Стрелка-указатель.
        MyJFrame.strelkaUkazatel.setEnabled(false);
        //Деактивизируем кнопку Круг.
        MyJFrame.krug.setEnabled(false);
        //Деактивизируем кнопку Линия.
        MyJFrame.liniyaState.setEnabled(false);
        //Деактивизируем кнопку Линия1.
        //MyJFrame.liniya1.setEnabled(false);
        //Деактивизируем кнопку Линия2.
        MyJFrame.liniyaTaks.setEnabled(false);
        //Деактивизируем кнопку Линия2.
        MyJFrame.liniyaCompos.setEnabled(false);
        //Деактивизируем кнопку time
        MyJFrame.time.setEnabled(false);
        //Подправим предварительный выбор кнопки time, liniyaState, liniyaTaks
        if(MyJFrame.time.isSelected())MyJFrame.strelkaUkazatel.setSelected(false);//значение по-умолчанию
        if(MyJFrame.liniyaState.isSelected())MyJFrame.liniyaCompos.setSelected(false);//значение по-умолчанию
        if(MyJFrame.liniyaTaks.isSelected())MyJFrame.liniyaCompos.setSelected(false);//значение по-умолчанию

        //ДеАктивизируем кнопку Копировать.(Copy) 
        MyJFrame.copyJButton.setEnabled(false);
        MyJFrame.deletejButton.setEnabled(false);
        
        //ДеАктивизируем кнопку Вставить.(Paste)
        MyJFrame.pastejButton.setEnabled(false);
       
        

        //ДеАктивизируем кнопку Выстроить Дерево
        MyJFrame.positionedTree.setEnabled(false);

        
        //Деактивизируем пункт меню СохранитьКак.(Save As...)
        MyJFrame.saveAsProject.setEnabled(false);
        //ДеАктивизируем кнопку Сохранить.(Save .)
        MyJFrame.saveProjectJButton.setEnabled(false);
        //ДеАктивизируем пункт меню Сохранить.(Save)
        MyJFrame.saveProjectJMenuItem.setEnabled(false);
        
        //Деактивизируем пункт меню закрыть Проект.
        MyJFrame.closeProjectJMenuItem18.setEnabled(false);

        //ДеАктивизируем пункт меню Настройки
        MyJFrame.property.setEnabled(false);
        //ДеАктивизируем пункт меню Импорт.
        MyJFrame.inport.setEnabled(false);
}
    
    //повесим слушателя на переключение вкладки
    private class MyAncestorListener implements AncestorListener{
        public void actionPerformed(AncestorEvent e) {}
        @Override
        public void ancestorAdded(AncestorEvent ae) {
            //выполняется при переключении на вкладку Таксономии.
            initComponentMenuToolBar();
            myProject.getMyComposArray().setResizeWindow(true);//подгоним размер экрана под расположенные данные
            myProject.setMyEnabledArray(pTable);//сообщим Проекту, какая вкладка у него открыта
        }
        @Override
        public void ancestorRemoved(AncestorEvent ae) {
            //выполняется при уходе из вкладки Таксономии
            finalComponentMenuToolBar();
        }
        @Override
        public void ancestorMoved(AncestorEvent ae) {}
        }
    /**Метод, который выделяет цветом все объекты, что попадают в выделенную мышкой область.
     Однако:
     Линия выделяется только в том случае, эсли выделен Начальный и Конечный объект.
     Эллипс выделяется только в том случае, если выделена одна из его 4-х середин сторон.*/
    private void makeGroupColorOn(Rectangle2D rect){
        //проверим, что выделенная область имеет ненулевой размер
        if(this.rect.isEmpty()){
            if(MyJFrame.copyJButton.isEnabled()){
                    MyJFrame.copyJButton.setEnabled(false);
                    MyJFrame.deletejButton.setEnabled(false);
                }
            return;
        }
        //просмотрим все эллипсы .  если точка эллипса присутствует в выделенной области, отмечаем этот эллипс зеленым цветом.
        boolean isMakeEll=false;//если при проходе ни один эллипс не вошел в выделенную область, кнопку копировать не активировать.
        for(MyEll myEll: myProject.getMyComposArray().getPaintEll()){
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
                }
        }
        //просмотрим все линии .  если точка начала и точка конца линии присутствуют  в выделенной области, отмечаем эту линию оранжевым цветом.
        for(MyLine myLine: myProject.getMyComposArray().getMyLineArray().getLineArray()){
            if(this.rect.contains(myLine.getP1())&&this.rect.contains(myLine.getP2())){
                myLine.setColorLine(makeGroupColor);
            }else{
                 myLine.setColorLine(Color.black);
            }
        }
    }
    
    /**Метод, который выделяет цветом все объекты, что попадают в выделенную мышкой область*/
    private void makeGroupColorOff(Rectangle2D rect){
        //просмотрим все эллипсы .  если точка эллипса присутствует в выделенной области, отмечаем этот эллипс зеленым цветом.
        for(MyEll myEll: myProject.getMyComposArray().getPaintEll()){
            for(MyPoint1 myPoint1:myEll.getMyEllPoints()){
                if(this.rect.contains(myPoint1)){
                    myEll.setColorEll(Color.black);
                }
            }
        }
        //просмотрим все линии .  если точка начала и точка конца линии присутствуют  в выделенной области, отмечаем эту линию оранжевым цветом.
        for(MyLine myLine: myProject.getMyComposArray().getMyLineArray().getLineArray()){
            if(this.rect.contains(myLine.getP1())&&this.rect.contains(myLine.getP2())){
                myLine.setColorLine(Color.BLACK);
            }
        }
        
    //this.rect.contains(press);//истина, если точка принадлежит многоугольнику
    
    }
    public void setRect(Rectangle2D rect) {
        this.rect = rect;
        repaint();
    }
    /**Метод собирает в группу выделенные для копирования объекты (линии и эллипсы) Композиции.
     Сбор выполним по каждому полю каждого класса, чтоб исключить */
    private void makeGroupCopyOn(){
        MyJFrame.setMyComposArrayCopy(new MyComposArray()); //очистим группу для копирования.
        for(MyEll myEll: myProject.getMyComposArray().getPaintEll()){//просмотрим массив эллипсов и массив линий.
            if(myEll.getColorEll().equals(makeGroupColor)){//если эллипс выделен цветом выделения
                MyEll myEllnew=new MyEll(myEll.getX(),myEll.getY(),myEll.getText());//создадим новый объект и скопируем в него поля выделенного объекта
                MyJFrame.getMyComposArrayCopy().getPaintEll().add(myEllnew);//добавим созданный объект к массиву объектов для копирования
            }
        }
        for(MyLine myLine: myProject.getMyComposArray().getMyLineArray().getLineArray()){
            if(myLine.getColorLine().equals(makeGroupColor)){
                //создадим новый объект
                MyLineCompos myLineNew =new MyLineCompos(MyJFrame.getMyComposArrayCopy().findPaintEll(myLine.getText1()),MyJFrame.getMyComposArrayCopy().findPaintEll(myLine.getText2()));
                //скопируем в него поля выделенного объекта
                myLineNew.setPriznakUrlAr(myLine.getPriznakUrlAr());
                //добавим созданный объект к массиву объектов для копирования
                MyJFrame.getMyComposArrayCopy().getMyLineArray().getLineArray().add(myLineNew);
            }
        }
    //Если найдем объект, цвет которого равен makeGroupColorLine для линии или makeGroupColorEll для эллипса, берем такой объект в группу для копирования.
    }
    /**Метод собирает в группу выделенные для копирования объекты (линии и эллипсы) Композиции.
     Сбор выполним по каждому полю каждого класса, чтоб исключить */
    private void makeGroupDeleteOn(){
        //очистим группу для копирования.
        MyJFrame.setMyComposArrayCopy(new MyComposArray());
        //просмотрим массив эллипсов
        for(MyEll myEll: myProject.getMyComposArray().getPaintEll()){
            //если эллипс выднлнн цветом выделения
            if(myEll.getColorEll().equals(makeGroupColor)){
                //создадим новый объект
                //скопируем в него поля выделенного объекта
                MyEll myEllnew=new MyEll(myEll.getX(),myEll.getY(),myEll.getText());
                //добавим созданный объект к массиву объектов для копирования
                MyJFrame.getMyComposArrayCopy().getPaintEll().add(myEllnew);
            }
        }
        
    //Если найдем объект, цвет которого равен makeGroupColorLine для линии или makeGroupColorEll для эллипса, берем такой объект в группу для копирования.
    }
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
            MyJFrame.myComposArrayCopyPassed=false;//восстановим индикатор нажима кнопки Копировать
            MyJFrame.copyJButton.setEnabled(false);
            MyJFrame.deletejButton.setEnabled(false);
            for(MyEll deleteEll:MyJFrame.getMyComposArrayCopy().getPaintEll()){
                //Удаляем эллипс и все его линии, если они есть.
                myProject.getMyComposArray().removePaintEll(deleteEll);
            }
            //очистим группу для копирования.
            MyJFrame.setMyComposArrayCopy(new MyComposArray());
        
            return;
        }
                //Если выбран Круг.
                //проверки на принадлежность точки линии эллипсу 
                if(popupX!=0&&popupY!=0){
                MyEll deleteEll =  myProject.getMyComposArray().findPaintEll(new Point(popupX, popupY));
                if (deleteEll != null) {
                    //Удаляем эллипс и все его линии, если они есть.
                    myProject.getMyComposArray().removePaintEll(deleteEll);
                }
                //Если выбран Линия.
                if (myProject.getMyComposArray().getMyLineArray().findMyLineB(new Point(popupX, popupY))) {
                    MyLine deleteLine = myProject.getMyComposArray().getMyLineArray().findMyLine(new Point(popupX, popupY));
                    //Удалить ее.
                    myProject.getMyComposArray().removePaintLine(deleteLine);
                    //Данные Проекта могут быть изменены, Проект требует сохранения.
                    myProject.setSaving(false);
                }

                repaint();
                }
    }
    private void pasteMakeGroup(){
        //вставим скопированные объекты из вкладки Композиции, Если 
        if(MyJFrame.getMyComposArrayCopy().getPaintEll().size()>0){//если есть скопированные эллипсы
            //MyJTabbedPane myJTabbedPanePaste = (MyJTabbedPane) jTabbedPane1.getSelectedComponent();//получим выбранную вкладку с проектом
            //добавим эллипсы
            for(MyEll myEll:MyJFrame.getMyComposArrayCopy().getPaintEll()){
                myProject.getMyComposArray().unique(
                        myProject.getMyComposArray().getPaintEll(), myEll);
            }
            //добавим линии
            for(MyLine myLine:MyJFrame.getMyComposArrayCopy().getMyLineArray().getLineArray()){
                myProject.getMyComposArray().uniqueMyLinePlus(
                        myProject.getMyComposArray().getMyLineArray().getLineArray(), myLine);
            }
            
        }
    }
}//CustomJPanel