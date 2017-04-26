/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JTabbedPane;

/**
 *
 * @author 5521
 * Панель для отображения видов/типов отношений открытого Проекта.
 * Отображение Проекта выполняется в нескольких вкладках по видам отношений Проекта: Таксономии, Композиции и Состояния.
 * Панель открывается для каждого открываемого Проекта.
 * Инкапсулирует методы для отображения и закрытия рабочих пунктов меню и кнопок панелей управления.
 */
public class MyJTabbedPane extends JTabbedPane{
    private static final long serialVersionUID = 1122345678L; // Change number as appropriate
    
    /**Класс с данными открытого проекта*/
    private MyProject myProjectJTabbedPane;

    MyJTabbedPane(){   
    }
   
    public MyProject getMyProjectJTabbedPane() {
        return myProjectJTabbedPane;
    }

    public void setMyProjectJTabbedPane(MyProject myProjectJTabbedPane) {
        this.myProjectJTabbedPane = myProjectJTabbedPane;
    }
    
    
    
}
