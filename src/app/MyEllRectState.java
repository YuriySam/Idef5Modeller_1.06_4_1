/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.swing.JOptionPane;

/**
 *
 * @author 655
 */
public class MyEllRectState extends MyEll {

    private TimeDate timeDate;
    /**
     * int timeInterval хранит время в милисекундах;
     */
    //private int timeInterval;
    /**
     * Calendar timeBegin хранит дату начала действия в GregorianCalendar
     * формате
     */
    //private Calendar timeBegin;
    /**
     * Calendar timeEnd хранит дату окончания действия в GregorianCalendar
     * формате;
     */
    //private Calendar timeEnd;
    /**
     * Calendar timeDate хранит единственную дату действия в GregorianCalendar
     * формате;
     */
    //private Calendar timeDate;
    /**
     * Точка соединения с перпендикуляром к середине стрелки, которая
     * соединяется с серединой нижней стороны квадрата.
     */
    MyPoint pPriznak;
    //MyEllRectState(){}
    //рисуем квадрат от точки соединения по высоте и ширине текста. 
    MyEllRectState(MyPoint pPriznak, Color color, String text,String start,String end, String interval) {
        //создадим объект с текущим датой и временем.
        //JCalendarCombo dt = new JCalendarCombo(JCalendarCombo.DISPLAY_DATE | JCalendarCombo.DISPLAY_TIME,true);
        //JCalendarCombo dt = new JCalendarCombo();
        //String startS="0";
        //String endS="0";
        //String intervalS ="0";
        this.timeDate = new TimeDate(start, end,interval);
        //this.timeDate = new TimeDate(dt,dt,true);
        
        if (text == null) {
            if (this.getText().isEmpty() || this.getText().equals("Класс/Элемент")) {
                //вызовем окно для ввода текста процесса/действия.
                //изменить текст в квадрате признака/действия.
                String textPriznak = JOptionPane.showInputDialog("Назовем действие/процесс", "действие или процесс");
                if(textPriznak==null){textPriznak = "неизвестный процесс";}
                if (textPriznak.isEmpty()) {
                    textPriznak = "неизвестный процесс";
                }
                //засунем текст признака в овал чтоб расчитать длину и высоту квадрата
                this.setText(textPriznak);
            }
        } else {
            //засунем текст признака в овал чтоб расчитать длину и высоту квадрата
            this.setText(text);
        }

        //расчитаем координаты квадрата, чтоб точка, полученная в параметрах была серединой нижней стороны.    
        super.setFrame(pPriznak.x - super.getWidth() / 2, pPriznak.y - super.getHeight(), super.getWidth(), super.getHeight());
        setColorEll(color);
        this.pPriznak = pPriznak;
    }

    public void draw(Graphics2D g2, MyEllRectState myRectState) {
        //рисуем овал признака
        //засунем текст признака в овал чтоб расчитать длину и высоту квадрата
        //MyEll pEll=new MyEll();
        //pEll.setText(this.pText);
        //pEll.setFrame(pPriznak.x-pEll.getWidth()/2, pPriznak.y-pEll.getHeight(), pEll.getWidth(), pEll.getHeight());
        //нарисуем фигуру процесса
        //g2.drawRect((int)(this.pPriznak.x-pEll.getWidth()/2), (int)(pPriznak.y-pEll.getHeight()),(int)pEll.getWidth(),(int)pEll.getHeight());
        //параметры для Большого квадрата
        //int xA=(int)(pPriznak.x-pEll.getWidth()/2);
        //int yA=(int)(pPriznak.y-pEll.getHeight());
        //int wA=(int)pEll.getWidth();
        //int hA=(int)pEll.getHeight();
        g2.drawRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        
        //параметры для первого маленького квадрата
        int xB = (int) (pPriznak.x - getWidth() / 2);//попадаем в левый маленький квадрат
        int yB = (int) (pPriznak.y - getHeight() / 2.5);//попадаем в левый маленький квадрат
        int wB = (int) getWidth() / 3;
        int hB = (int) (getHeight() / 2.5);
        Color oldColor = g2.getColor();
        g2.setColor(Color.WHITE);//чтоб две линии квадратов не делали утолщение, закроем одну линию цветом
        g2.fillRect(xB, yB, wB, hB + 1);//чтоб две линии квадратов не делали утолщение
        g2.setColor(oldColor);
        //g2.setColor(getColorEll());//чиб две линии квадратов не делали утолщение, восстановим цвет.
        g2.drawRect(xB, yB, wB, hB + 1);//чтоб две линии квадратов не делали утолщение

        Font newfont = new Font("Tahoma", Font.PLAIN, 10);
        Font oldfont = g2.getFont();//сохраним текущий шрифт
        //применим новый шрифт к g2
        g2.setFont(newfont);
        //g2.drawString(this.getBText(), xB + 7, yB + 10);//попадаем в левый маленький квадрат
        g2.drawString(this.timeDate.getBText(), xB + 1, yB + 10- (int)getHeight());//попадаем над большим квадратом слева квадрат
        //вернем старый шрифт g2
        g2.setFont(oldfont);

        //параметры для рисования второго маленького квадрата
        int xC = (int) (pPriznak.x - getWidth() / 6);
        int yC = (int) (pPriznak.y - getHeight() / 2.5);
        int wC = (int) getWidth() * 2 / 3;
        int hC = (int) (getHeight() / 2.5);
        //oldColor=g2.getColor();
        g2.setColor(Color.WHITE);//чиб две линии квадратов не делали утолщение, закроем одну линию цветом
        g2.fillRect(xC, yC, wC, hC + 1);//чиб две линии квадратов не делали утолщение
        g2.setColor(oldColor);
        //g2.setColor(getColorEll());//чиб две линии квадратов не делали утолщение, восстановим цвет.
        g2.drawRect(xC, yC, wC, hC + 1);//чиб две линии квадратов не делали утолщение
        //добавим текст во второй маленький квадрат
        //применим новый шрифт к g2
        g2.setFont(newfont);
        //g2.drawString(this.getCText(), xC + 7, yC + 10);//попадаем в правый маленький квадрат
        g2.drawString(this.timeDate.getCText(), xC + 7, yC + 10- (int)getHeight());//попадаем над большим квадратом справа квадрат
        //вернем старый шрифт g2
        g2.setFont(oldfont);

        //рисуем текст, отступая от начала квадрата
        g2.drawString(getText(), (int) getX() + 10, (int) getY() + 15);
    }

    //перерисуем квадрат от новой точки соединения по высоте и ширине текста. 
    public void setMyEllRectState(MyPoint pPriznak, Color color) {
        /*
        if(this.getText().isEmpty()||this.getText().equals("это эллипс")){
            //вызовем окно для ввода текста процесса/действия.
            //изменить текст в квадрате признака/действия.
            String textPriznak=JOptionPane.showInputDialog("Назовем действие/процесс", "действие или процесс");
            if(textPriznak.isEmpty()){
                textPriznak="неизвестный процесс";
            }
        //засунем текст признака в овал чтоб расчитать длину и высоту квадрата
        this.setText(textPriznak); 
        }
         */

        //расчитаем координаты квадрата, чтоб точка, полученная в параметрах была серединой нижней стороны.    
        super.setFrame(pPriznak.x - super.getWidth() / 2, pPriznak.y - super.getHeight(), super.getWidth(), super.getHeight());
        setColorEll(color);
        this.pPriznak = pPriznak;
    }

    /**
     * меняем текст квадрата линии и подгоняем его радмеры
     */
    public void editText() {
        //Сохраним старый текст
        String textOld=this.getText();
        //вызовем окно для ввода текста процесса/действия.
        
        //изменить текст в квадрате признака/действия.
        String textPriznak = JOptionPane.showInputDialog("Назовем действие/процесс", this.getText());
        if (textPriznak == null) {
            textPriznak = textOld;
           // if (this.getText().isEmpty()) {
           //     textPriznak = "неизвестный процесс";
           // } else {
           //     textPriznak = this.getText();
           // }
        }

        //засунем текст признака в овал чтоб расчитать длину и высоту квадрата
        this.setText(textPriznak);
        //пересчитаем все квадраты
        this.setMyEllRectState(pPriznak, this.getColorEll());
    }

    //public int getTimeInterval() {
    //    return timeInterval;
    //}

    //public void setTimeInterval(int timeInterval) {
    //    this.timeInterval = timeInterval;
    //}

    //public Calendar getTimeBegin() {
    //    return timeBegin;
    //}

    //public void setTimeBegin(Calendar timeBegin) {
    //    this.timeBegin = timeBegin;
    //}

    //public Calendar getTimeEnd() {
    //    return timeEnd;
    //}

    //public void setTimeEnd(Calendar timeEnd) {
    //    this.timeEnd = timeEnd;
    //}

    

    
    public TimeDate getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(TimeDate timeDate) {
        this.timeDate = timeDate;
    }
    
}
