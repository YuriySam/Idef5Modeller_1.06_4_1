/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author 655
 */

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

/**
 * Component to be used as tabComponent;
 * Contains a JLabel to show the text and 
 * a JButton to close the tab it belongs to 
 * Компонент для использования в качестве вкладки компонента; 
 * Содержит JLabel, чтобы показать текст и 
 * а JButton, чтобы закрыть вкладку, которому он принадлежит
 */ 
public class ButtonTabComponent extends JPanel {
    private static final long serialVersionUID = 112223344445678L; // Change number as appropriate
    
    private final JTabbedPane pane;

    public ButtonTabComponent(final JTabbedPane pane) {
        //unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        setOpaque(false);//компонент непрозрачный
        
        //make JLabel read titles from JTabbedPane
        // Сделать JLabel чтения названия из JTabbedPane
        JLabel label;
        label = new JLabel() {
            private static final long serialVersionUID = 112223344445677778L; // Change number as appropriate
            @Override
            public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };
        
        add(label);
        //add more space between the label and the button
        //добавить больше пространства между меткой и кнопкой
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //tab button
        JButton button = new TabButton();
        add(button);
        //add more space to the top of the component
        //добавить больше места в верхней части компонента
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    private class TabButton extends JButton implements ActionListener {
        private static final long serialVersionUID = 11222334444455678L; // Change number as appropriate
    
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            //Сделать кнопку выглядит одинаково для всех Жизнь
            setUI(new BasicButtonUI());
            //Make it transparent
            //Сделать его прозрачным(незакрашенной)
            setContentAreaFilled(false);
            //No need to be focusable
            //Нет необходимости быть фокусируемым
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            // Создание хороший эффект при опрокидывании
             // Мы используем один и тот же приемник для всех кнопок
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            // Закрыть вкладку правильной, нажав на кнопку
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            int i = pane.indexOfTabComponent(ButtonTabComponent.this);
            if (i != -1) {
                //При выборе закрыть вкладку кликнув на ее крестике, выполним метод сохранения Проекта, 
                //который проверит необходимость сохранения проекта (если были изменения) и сохранит при необходимости.
                MyJTabbedPane myJTabbedPaneSave = (MyJTabbedPane) pane.getSelectedComponent();//получим выбранную вкладку с проектом
                //if(myJTabbedPaneSave.getMyProjectJTabbedPane().isSave()){
                if(MyJFrame.saveProjectJButton.isEnabled()){     
                    App.myJFrame.projectSave(myJTabbedPaneSave.getMyProjectJTabbedPane());//сохраним проект из выбранной вкладки
                    
                }
                pane.remove(i);
            }
        }

        //we don't want to update UI for this button
        //мы не хотим, чтобы обновить пользовательский интерфейс для этой кнопки
        public void updateUI() {
        }

        //paint the cross
        //покрасить крест
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            //сдвигать изображение для нажатых кнопок
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {//если мышь находится над кнопкой
                g2.setColor(Color.MAGENTA);
            }
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}

