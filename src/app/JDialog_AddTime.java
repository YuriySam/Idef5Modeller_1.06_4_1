/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Font;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.freixas.jcalendar.JCalendarCombo;

/**
 *
 * @author 5521
 */
public class JDialog_AddTime extends javax.swing.JDialog {
    private static final long serialVersionUID = 11222345678L; // Change number as appropriate
    
    //Инициируем поля для сохранения проверенных введенных данных
        //если оба индикатора timeStartEndIs и timeDuration имеют значение  false, значит пользователь не изменил данные по-умолчанию в полях формы и нажал ОК.
        /**индикатор выбора режима ввода времени. Через Начало и Конец - true; */
        boolean timeStartEndIs=false;
        /**индикатор выбора режима ввода времени. Через Длительность  true */
        boolean timeDuration=false;
        
        /**Установить индикатор выбора режима ввода времени по-умолчанию. Начало и Конец - true; через Длительность  false*/
        boolean timeStartEndIs_Default=true;
        /** timeDurationDefault хранит время в милисекундах. Подставляем как значение по-умолчанию в поле формы Длительность */
        private long timeDuration_Default;
        /**хранит время в милисекундах. Подставляем как значение по-умолчанию в поле формы  Начало.  */
        private long timeStart_Default;
        /**хранит время в милисекундах. Подставляем как значение по-умолчанию в поле формы Конец.*/
        private long timeEnd_Default;
        
        /**введенные даты и Длительности.*/
        private final TimeDate timeDate;
        
        private final MyLineState timeLine;
    /**
     * Creates new form JDialog_AddTime
     * @param parent
     * @param modal
     * @param makeLine
     */
    public JDialog_AddTime(java.awt.Frame parent, boolean modal, MyLineState makeLine) {
        super(parent, modal);
        System.out.println("System.currentTimeMillis()= "+System.currentTimeMillis());
        
        this.timeLine=makeLine;
    
        timeDate= makeLine.getTimeDate_MyEllRectState();
        
        //Итог. На линии кликнута правая мышка и выбран пункт Добавить время.
        //1. Надо оценить ситуацию и вывести значения времени по-умолчанию:
        
        String timeStart_FromLine=timeDate.getMyData()[6][1];
        String timeEnd_FromLine=timeDate.getMyData()[7][1];
        String timeDuration_FromLine=timeDate.getMyData()[8][1];
        //1.1. харакреристика времени добавляется впервые к процессу/действию.
        if(timeStart_FromLine.equals(timeEnd_FromLine)
                &&timeEnd_FromLine.equals(timeDuration_FromLine)
                &&timeDuration_FromLine.equals("")){//проверено
            //1.1.1. Переключим Форму на режим Задать временную точку либо выбрать период
            timeStartEndIs_Default=true;
            //1.1.2. Выводим текущее время. 
            //timeDuration_Default=Long.parseLong(timeDuration_FromLine);
            timeDuration_Default=0L;
            timeStart_Default=System.currentTimeMillis();
            timeEnd_Default=timeStart_Default;    
            initComponents(); //проинициализируем поля формы.
            this.sName.setText(timeLine.getMyEllRectStateText());//покажем текст действия в форме на экране
        }
        //1.2. Изменяем ранее введенную характеристику времени.
        //1.2.1. Была введена временная точка.
        //1.2.1.1. Выводим временную точку. Длительность пуста и неактивна.
        if(timeStart_FromLine.equals(timeEnd_FromLine)
                &&!timeEnd_FromLine.equals("")){ //на проверке
            //1.2.1.1. Переключим Форму на режим Задать временную точку либо выбрать период
            timeStartEndIs_Default=true;
            //1.2.1.2. Выводим временную точку. Длительность пуста и неактивна.
            //timeDuration_Default=Long.parseLong(timeDuration_FromLine);
            timeDuration_Default=0L;
            timeStart_Default=Long.parseLong(timeStart_FromLine);
            timeEnd_Default=Long.parseLong(timeEnd_FromLine);  
            initComponents(); //проинициализируем поля формы.
            this.sName.setText(timeLine.getMyEllRectStateText());//покажем текст действия в форме на экране
            //Значения интервале не подлежат ручному редактированию.
            // timeDate.getData()=timeDate.getData();
            yearl.setText(timeDate.getMyData()[0][1]);
            monthl.setText(timeDate.getMyData()[1][1]);
            dayl.setText(timeDate.getMyData()[2][1]);
            hourl.setText(timeDate.getMyData()[3][1]);
            minutel.setText(timeDate.getMyData()[4][1]);
            secondl.setText(timeDate.getMyData()[5][1]);
            yearl.setEnabled(false);
            monthl.setEnabled(false);
            dayl.setEnabled(false);
            hourl.setEnabled(false);
            minutel.setEnabled(false);
            secondl.setEnabled(false); 
        }
        //1.2.2. Была введена длительность.
        if(!timeStart_FromLine.equals(timeEnd_FromLine)
                &&timeEnd_FromLine.equals("")
                &&!timeDuration_FromLine.equals("")){// проверено
            //1.2.2.1. Переключим Форму на режим Задать временную точку либо выбрать период
            timeStartEndIs_Default=true;
            //1.2.3.2. Выводим Интервал. Выводим Длительность.
            //timeDuration_Default=Long.parseLong(timeDuration_FromLine);
            timeDuration_Default=0L;
            timeStart_Default=Long.parseLong(timeStart_FromLine);
            timeEnd_Default=Long.parseLong(timeEnd_FromLine);    
            initComponents(); //проинициализируем поля формы.
            this.sName.setText(timeLine.getMyEllRectStateText());//покажем текст действия в форме на экране
            //Значения интервале не подлежат ручному редактированию.
           // timeDate.getData()=timeDate.getData();
            yearl.setText(timeDate.getMyData()[0][1]);
            monthl.setText(timeDate.getMyData()[1][1]);
            dayl.setText(timeDate.getMyData()[2][1]);
            hourl.setText(timeDate.getMyData()[3][1]);
            minutel.setText(timeDate.getMyData()[4][1]);
            secondl.setText(timeDate.getMyData()[5][1]);
            yearl.setEnabled(false);
            monthl.setEnabled(false);
            dayl.setEnabled(false);
            hourl.setEnabled(false);
            minutel.setEnabled(false);
            secondl.setEnabled(false); 
        }
                        
        //1.2.3. Был введен интервал.
        if(!timeStart_FromLine.equals(timeEnd_FromLine)
                &&!timeDuration_FromLine.equals("")){// проверено
            //1.2.2.1. Переключим Форму на режим Задать временную точку либо выбрать период
            timeStartEndIs_Default=true;
            //1.2.3.2. Выводим Интервал. Выводим Длительность.
            //timeDuration_Default=Long.parseLong(timeDuration_FromLine);
            timeDuration_Default=0L;
            timeStart_Default=Long.parseLong(timeStart_FromLine);
            timeEnd_Default=Long.parseLong(timeEnd_FromLine);    
            initComponents(); //проинициализируем поля формы.
            this.sName.setText(timeLine.getMyEllRectStateText());//покажем текст действия в форме на экране
            //расчитаем длительность события:
            //timeDate.setTimeDate(timeStart_FromLine,timeEnd_FromLine);
            //Значения Длительности не подлежат ручному редактированию.
            yearl.setText(timeDate.getMyData()[0][1]);
            monthl.setText(timeDate.getMyData()[1][1]);
            dayl.setText(timeDate.getMyData()[2][1]);
            hourl.setText(timeDate.getMyData()[3][1]);
            minutel.setText(timeDate.getMyData()[4][1]);
            secondl.setText(timeDate.getMyData()[5][1]);
            yearl.setEnabled(false);
            monthl.setEnabled(false);
            dayl.setEnabled(false);
            hourl.setEnabled(false);
            minutel.setEnabled(false);
            secondl.setEnabled(false); 
        }
        //timeDate.printDate(" загрузка формы ввода времени и даты ");
        
        //создадим объект с текущим датой и временем.
        //JCalendarCombo dt = new JCalendarCombo(JCalendarCombo.DISPLAY_DATE | JCalendarCombo.DISPLAY_TIME,true);
        //this.timeDate = new TimeDate(dt,dt,true);
        
        // Получаем текущее время
        //LocalDateTime today = LocalDateTime.now();
        
        //System.out.println("");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timeType = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        timeDateParam = new javax.swing.ButtonGroup();
        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        timeStartInput = new JCalendarCombo(
            JCalendarCombo.DISPLAY_DATE |
            JCalendarCombo.DISPLAY_TIME,
            true);
        timeStartInput.setEditable(true);
        jPanel6 = new javax.swing.JPanel();
        yearl = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        monthl = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        dayl = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        hourl = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        minutel = new javax.swing.JTextField();
        secondl = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        timeEndInput = new JCalendarCombo(
            JCalendarCombo.DISPLAY_DATE |
            JCalendarCombo.DISPLAY_TIME,
            true);
        timeEndInput.setEditable(true);
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        sName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        dataPoint = new javax.swing.JRadioButton();
        dataInterval = new javax.swing.JRadioButton();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Добавление времени");

        ok.setText("Ok");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Длительность:");

        jLabel2.setText("Конец:");

        jLabel1.setText("Начало:");

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        timeStartInput.setDate(new java.util.Date(timeStart_Default));
        timeStartInput.setTimeFont(new Font("DialogInput", Font.PLAIN, 25));
        timeStartInput.addDateListener(new org.freixas.jcalendar.DateListener() {
            public void dateChanged(org.freixas.jcalendar.DateEvent evt) {
                timeStartInputDateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(timeStartInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(timeStartInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        yearl.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        yearl.setEnabled(false);
        yearl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearlActionPerformed(evt);
            }
        });

        jLabel4.setText("лет*");

        jLabel6.setText("месяцев**");

        monthl.setEnabled(false);
        monthl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthlActionPerformed(evt);
            }
        });

        jLabel7.setText("дней");

        dayl.setEnabled(false);
        dayl.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                daylCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        dayl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                daylActionPerformed(evt);
            }
        });

        jLabel8.setText("часов");

        hourl.setEnabled(false);
        hourl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourlActionPerformed(evt);
            }
        });

        jLabel9.setText("минут");

        minutel.setEnabled(false);
        minutel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minutelActionPerformed(evt);
            }
        });

        secondl.setEnabled(false);
        secondl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secondlActionPerformed(evt);
            }
        });

        jLabel10.setText("секунд");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(yearl, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addComponent(monthl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(dayl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hourl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(minutel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(secondl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel10)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(secondl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel10))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(monthl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel6)
                .addComponent(dayl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel7)
                .addComponent(hourl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel8)
                .addComponent(minutel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel9)
                .addComponent(yearl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel4))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        //timeEndInput.setDate(new java.util.Date(1472746370000L));
        timeEndInput.setDate(new java.util.Date(timeEnd_Default));
        timeEndInput.setTimeFont(new Font("DialogInput", Font.PLAIN, 25));
        timeEndInput.addDateListener(new org.freixas.jcalendar.DateListener() {
            public void dateChanged(org.freixas.jcalendar.DateEvent evt) {
                timeEndInputDateChanged(evt);
            }
        });

        jButton1.setText("=  Началу.");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(timeEndInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jButton1))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeEndInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jLabel5.setText("Для действия или процесса:");

        sName.setText("получим текст из timeLine");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("*  - за 1 год принимается 12 месяцев.\n** - за 1 месяц принимается 30 дней.\nЕсли такой точности недостаточно, рекомендуем вводить Длительность через Начало и Конец.");
        jTextArea1.setEnabled(false);
        jTextArea1.setFocusable(false);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        timeDateParam.add(dataPoint);
        if(timeStartEndIs_Default){
            dataPoint.setSelected(true);
        }
        dataPoint.setText("Задать временную точку либо выбрать период.");
        dataPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataPointActionPerformed(evt);
            }
        });

        timeDateParam.add(dataInterval);
        if(!timeStartEndIs_Default){
            dataInterval.setSelected(true);
        }
        dataInterval.setText("Задать длительность без привязки к календарю.");
        dataInterval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataIntervalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataInterval)
                    .addComponent(dataPoint))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dataPoint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dataInterval)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sName)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(sName, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ok)
                    .addComponent(cancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        // Кнопка "Ok"
        //если пользователь сразу после открытия формы нажмет ОК
        if(!timeStartEndIs&&!timeDuration){
            //расчитаем длительность события для первой строки:
        timeDate.setTimeDate(timeStartInput,timeEndInput);
        //Сохраним полученные данные в виде экземпляра класса TimeDate
        yearl.setText(timeDate.getMyData()[0][1]);
        monthl.setText(timeDate.getMyData()[1][1]);
        dayl.setText(timeDate.getMyData()[2][1]);
        hourl.setText(timeDate.getMyData()[3][1]);
        minutel.setText(timeDate.getMyData()[4][1]);
        secondl.setText(timeDate.getMyData()[5][1]);
        //Значения интервале не подлежат ручному редактированию.
        yearl.setEnabled(false);
        monthl.setEnabled(false);
        dayl.setEnabled(false);
        hourl.setEnabled(false);
        minutel.setEnabled(false);
        secondl.setEnabled(false); 
        //сохраним в объекте Линия данные про длительнсть процесса.
            timeLine.getMyEllRectState().getTimeDate().setMyData(this.timeDate.getMyData());
        }else{
            if( this.timeStartEndIs){
                //сохраним в объекте Линия данные про длительнсть процесса.
                timeLine.getMyEllRectState().getTimeDate().setMyData(this.timeDate.getMyData());
            }else{
                //Проверим,  введен ли интервал , и правильно ли.
                timeDate.getMyData()[0][1]=yearl.getText();
                timeDate.getMyData()[1][1]=monthl.getText();
                timeDate.getMyData()[2][1]=dayl.getText();
                timeDate.getMyData()[3][1]=hourl.getText();
                timeDate.getMyData()[4][1]=minutel.getText();
                timeDate.getMyData()[5][1]=secondl.getText();
                timeDate.getMyData()[6][1]="0";
                timeDate.getMyData()[7][1]="0";
                //проверим данный полей интервала на корректность.
                if(compareData(timeDate.getMyData())){
                    //timeDate.printDate("интервал до миллисекунд");

                    timeDate.setIntervalMillisecFrom_DatePart();
                    //timeDate.printDate("интервал с миллисекундами");
                    //создадим поля даты интервала из миллисекунд
                    timeDate.setDatePartFrom_IntervalMillisec();
                    //timeDate.printDate("поля Даты интервал из миллисекунд");
                    //сохраняем экземпляр класса TimeDate в экземпляре класса MyEllRectState, входящего в класс стрелки отношения. 
                    this.timeLine.getMyEllRectState().setTimeDate(timeDate);
                }
            }
        
        }
        
         //System.out.println("Выбрано действие"+timeLine.getMyEllRectStateText());
        //timeDate.printDate("конец обработки кнопки ок!  ");
        //System.out.println("");
        setVisible(false);
            dispose();
    }//GEN-LAST:event_okActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // Кнопка "Отмена"
        
        setVisible(false);
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void yearlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearlActionPerformed
        // TODO add your handling code here:
        timeDuration=true; // Индикатор того, что Длительность введена.
    }//GEN-LAST:event_yearlActionPerformed

    private void monthlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlActionPerformed
        // TODO add your handling code here:
        timeDuration=true; // Индикатор того, что Длительность введена.
    }//GEN-LAST:event_monthlActionPerformed

    private void daylActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daylActionPerformed
        // TODO add your handling code here:
        timeDuration=true; // Индикатор того, что Длительность введена.
    }//GEN-LAST:event_daylActionPerformed

    private void hourlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourlActionPerformed
        // TODO add your handling code here:
        timeDuration=true; // Индикатор того, что Длительность введена.
    }//GEN-LAST:event_hourlActionPerformed

    private void minutelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minutelActionPerformed
        // TODO add your handling code here:
        timeDuration=true; // Индикатор того, что Длительность введена.
    }//GEN-LAST:event_minutelActionPerformed

    private void secondlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secondlActionPerformed
        // TODO add your handling code here:
        timeDuration=true; // Индикатор того, что Длительность введена.
    }//GEN-LAST:event_secondlActionPerformed

    private void timeEndInputDateChanged(org.freixas.jcalendar.DateEvent evt) {//GEN-FIRST:event_timeEndInputDateChanged
        // Выбор Конечной даты. Подсчет интервала. Покажем значение интервала на экране.:
        Calendar c = evt.getSelectedDate();
        //if(timeStartIs){
        if (c != null) { 
            //проверим, что Начальная дата моложе Конечной.
        if(timeStartInput.getDate().getTime()>timeEndInput.getDate().getTime()){
            //Если это случилось, выводим сообщение про некорректность ввода данных и возвращаемся в окно ввода дат.
            JOptionPane.showMessageDialog(null," Ошибка ввода Начальной даты: "+timeStartInput.getDate()+" > "+timeStartInput.getDate());
            timeEndInput.setDate(this.timeStartInput.getDate());
        }
            //расчитаем длительность события:
           // timeDate.setTimeEnd(timeEndInput);
            timeDate.setTimeDate(timeStartInput,timeEndInput);
            //=new TimeDate(timeStartInput,timeEndInput,true);
            //Сохраним полученные данные в виде экземпляра класса TimeDate и добавим его к объекту Стрелка отношения Систояние.
            //Значения интервале не подлежат ручному редактированию.
           // timeDate.getData()=timeDate.getData();
            yearl.setText(timeDate.getMyData()[0][1]);
            monthl.setText(timeDate.getMyData()[1][1]);
            dayl.setText(timeDate.getMyData()[2][1]);
            hourl.setText(timeDate.getMyData()[3][1]);
            minutel.setText(timeDate.getMyData()[4][1]);
            secondl.setText(timeDate.getMyData()[5][1]);
            yearl.setEnabled(false);
            monthl.setEnabled(false);
            dayl.setEnabled(false);
            hourl.setEnabled(false);
            minutel.setEnabled(false);
            secondl.setEnabled(false); 
            
            timeStartEndIs=true;//отметим, что Конец вводилось.
            
            //TimeDate timeDate1 = new TimeDate(timeStartInput.getCalendar(),timeEndInput.getCalendar());
            //String[][] data1= timeDate1.getData();
            //timeDate.printDate("Выбор конечной даты");
                
        
    }
    else {
	System.out.println("timeEndInputDateChanged --------- No time selected.");
    }
        
    }//GEN-LAST:event_timeEndInputDateChanged

    private void timeStartInputDateChanged(org.freixas.jcalendar.DateEvent evt) {//GEN-FIRST:event_timeStartInputDateChanged
        // Изменено поле начальной датыВремени timeStartInput:
        //проверим, что Начальная дата моложе Конечной.
        if(timeStartInput.getDate().getTime()>timeEndInput.getDate().getTime()){
            //Если это случилось, выводим сообщение про некорректность ввода данных и возвращаемся в окно ввода дат.
            //JOptionPane.showMessageDialog(null," Ошибка ввода Начальной даты: "+timeStartInput.getDate()+" > "+timeStartInput.getDate());
            timeEndInput.setDate(this.timeStartInput.getDate());
        }
        //расчитаем длительность события для первой строки:
        timeDate.setTimeDate(timeStartInput,timeEndInput);
        //Сохраним полученные данные в виде экземпляра класса TimeDate
        yearl.setText(timeDate.getMyData()[0][1]);
        monthl.setText(timeDate.getMyData()[1][1]);
        dayl.setText(timeDate.getMyData()[2][1]);
        hourl.setText(timeDate.getMyData()[3][1]);
        minutel.setText(timeDate.getMyData()[4][1]);
        secondl.setText(timeDate.getMyData()[5][1]);
        //Значения интервале не подлежат ручному редактированию.
        yearl.setEnabled(false);
        monthl.setEnabled(false);
        dayl.setEnabled(false);
        hourl.setEnabled(false);
        minutel.setEnabled(false);
        secondl.setEnabled(false); 

        timeStartEndIs=true;//отметим, что Начало вводилось.
        //this.endAsStart.setSelected(false);
    }//GEN-LAST:event_timeStartInputDateChanged

    private void dataPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataPointActionPerformed
        // Выбор Задать временную точку либо выбрать период чезез Начало и Конец.:
        if(dataPoint.isSelected()){
            //Значения выбора периода доступны для ручного редактирования
            timeStartInput.setEnabled(true);
            timeStartInput.setEditable(true);
            timeEndInput.setEnabled(true);
            timeEndInput.setEditable(true);
            //Значения интервале не подлежат ручному редактированию.
            yearl.setEnabled(false);
            monthl.setEnabled(false);
            dayl.setEnabled(false);
            hourl.setEnabled(false);
            minutel.setEnabled(false);
            secondl.setEnabled(false); 
            timeStartEndIs = true;
            
        }else{
            //Значения выбора периода не доступны  редактированию
            timeStartInput.setEnabled(false);
            timeStartInput.setEditable(false);
            timeEndInput.setEnabled(false);
            timeEndInput.setEditable(false);
            //Значения интервале доступны  редактированию.
            yearl.setEnabled(true);
            monthl.setEnabled(true);
            dayl.setEnabled(true);
            hourl.setEnabled(true);
            minutel.setEnabled(true);
            secondl.setEnabled(true); 
            timeStartEndIs = false;
        }
    }//GEN-LAST:event_dataPointActionPerformed

    private void dataIntervalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataIntervalActionPerformed
        // Выбор Задать длительность без привязки к календарю.:
        if(!dataInterval.isSelected()){
            //Значения выбора периода доступны для ручного редактирования
        //    timeStartInput.setEnabled(true);
        //    timeStartInput.setEditable(true);
        //    timeEndInput.setEnabled(true);
        //    timeEndInput.setEditable(true);
            //Значения интервале не подлежат ручному редактированию.
        //    yearl.setEnabled(false);
        //    monthl.setEnabled(false);
        //    dayl.setEnabled(false);
        //    hourl.setEnabled(false);
        //    minutel.setEnabled(false);
        //    secondl.setEnabled(false); 
        //    timeStartEndIs = true;
        timeDuration=false; // Индикатор того, что Длительность введена.
            
        }else{
            //Значения выбора периода не доступны  редактированию
            timeStartInput.setEnabled(false);
            timeStartInput.setEditable(false);
            timeEndInput.setEnabled(false);
            timeEndInput.setEditable(false);
            //Значения интервале доступны  редактированию.
            yearl.setEnabled(true);
            monthl.setEnabled(true);
            dayl.setEnabled(true);
            hourl.setEnabled(true);
            minutel.setEnabled(true);
            secondl.setEnabled(true); 
            timeStartEndIs = false;
            timeDuration=true; // Индикатор того, что Длительность введена.
            
        }
    }//GEN-LAST:event_dataIntervalActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //Если выбран, Дата периода Конечная становится равной Дате Начальной.
        
            timeEndInput.setDate(timeStartInput.getDate());
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void daylCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_daylCaretPositionChanged
        // TODO add your handling code here:
        System.out.println("");
    }//GEN-LAST:event_daylCaretPositionChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_AddTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // JDialog_AddTime dialog = new JDialog_AddTime(new javax.swing.JFrame(), true);
                //dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                //    @Override
                 //   public void windowClosing(java.awt.event.WindowEvent e) {
                 //       System.exit(0);
                 //   }
                //});
               // dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JRadioButton dataInterval;
    private javax.swing.JRadioButton dataPoint;
    private javax.swing.JTextField dayl;
    private javax.swing.JTextField hourl;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField minutel;
    private javax.swing.JTextField monthl;
    private javax.swing.JButton ok;
    private javax.swing.JLabel sName;
    private javax.swing.JTextField secondl;
    private javax.swing.ButtonGroup timeDateParam;
    private org.freixas.jcalendar.JCalendarCombo timeEndInput;
    private org.freixas.jcalendar.JCalendarCombo timeStartInput;
    private javax.swing.ButtonGroup timeType;
    private javax.swing.JTextField yearl;
    // End of variables declaration//GEN-END:variables
    /**Проверим, что в строке все поля остались со значениями по-умолчанию.*/
    private boolean compareDataDefault(String year, String month, String day, String hour, String minute, String second){
        //Проверим поле year.
        if(!year.equals(timeDate.getMyData()[0][1])){return false;}
        //Проверим поле month.
        if(!month.equals(timeDate.getMyData()[1][1])){return false;}
        //Проверим поле day.
        if(!day.equals(timeDate.getMyData()[2][1])){return false;}
        //Проверим поле hour.
        if(!hour.equals(timeDate.getMyData()[3][1])){return false;}
        //Проверим поле minute.
        if(!minute.equals(timeDate.getMyData()[4][1])){return false;}
        //Проверим поле second.
        if(!second.equals(timeDate.getMyData()[5][1])){return false;}
        
        return true;
    }
    /**Проверим заполнение полей корректно.
     Проверим, что поле содержит или значение по-умолчанию d 
     * или корректный (по качеству 0-9 и количеству символов n(d)) данные.
     Если поле пустое. запишем в него 0.*/
    private boolean compareData(String[][] data){
        
        //Проверим поле year.
        if(data[0][1].length()==0){data[0][1]="0";}//боремся с пустыми строками.
        if(!compareSymbol("yyyy",data[0][0],data[0][1])){return false;}
        //поле год введено правильно или не изменялось.
        //Проверим month
        if(data[1][1].length()==0){data[1][1]="0";}//боремся с пустыми строками.
        if(!compareSymbol("mm",data[1][0],data[1][1])){return false;}
        //month введено правильно или нетронуто
        //Проверим day
        if(data[2][1].length()==0){data[2][1]="0";}//боремся с пустыми строками.
        if(!compareSymbol("dd",data[2][0],data[2][1])){return false;}
        //day введено правильно или нетронуто
        //Проверим hour
        if(data[3][1].length()==0){data[3][1]="0";}//боремся с пустыми строками.
        if(!compareSymbol("hh",data[3][0],data[3][1])){return false;}
        //hour введено правильно или нетронуто
        //Проверим minute
        if(data[4][1].length()==0){data[4][1]="0";}//боремся с пустыми строками.
        if(!compareSymbol("mm",data[4][0],data[4][1])){return false;}
        //minute введено правильно или нетронуто
        //Проверим second
        if(data[5][1].length()==0){data[5][1]="0";}//боремся с пустыми строками.
        if(!compareSymbol("ss",data[5][0],data[5][1])){return false;}
        //second введено правильно или нетронуто
        
    return true;
}
    /**Проверим, что поле содержит или значение по-умолчанию d 
     * или корректный (по качеству 0-9 и количеству символов n(d)) данные.
     */
    private boolean compareSymbol (String d, String name, String def){
        
        if(d.equals(def)){ return true; }
        //В значении d поля name максимально допустимое количество символов n
        if(d.length()<def.length()||def.length()<0){//Количество не соответствуем правильному.
            JOptionPane.showMessageDialog(null, "В поле "+name+" введено количетво символов "+def.length()+"("+def+"), что больше допустимого ("+d.length()+")  или меньше 1.")  ;
            return false;
        }
        
        //В значении d поля name допустимо использовать только арабские цифры.
        //Pattern p = Pattern.compile("^[0-9]{"+def.length()+"}");
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(def);
        if(!m.matches()){//Название не соответствуем правильному.
            JOptionPane.showMessageDialog(null, "В поле "+name+"("+def+") введены не только цифры.")  ;
            return false;
        }
        return true;
    }
    
   /* JXDatePicker.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    if ((e.getPropertyName().equals("date"))) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                _DateString =df.format(JXDatePicker.getDate());        
                        System.out.println(    _DateString );
                        
                    }
                }
            });
      
    
    JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
timeSpinner.setEditor(timeEditor);
timeSpinner.setValue(new Date()); // will only show the current time

public static JPanel buildDatePanel(String label, Date value) {
JPanel datePanel = new JPanel();

JDateChooser dateChooser = new JDateChooser();
if (value != null) {
    dateChooser.setDate(value);
}
for (Component comp : dateChooser.getComponents()) {
    if (comp instanceof JTextField) {
	((JTextField) comp).setColumns(50);
	((JTextField) comp).setEditable(false);
    }
}

datePanel.add(dateChooser);

SpinnerModel model = new SpinnerDateModel();
JSpinner timeSpinner = new JSpinner(model);
JComponent editor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
timeSpinner.setEditor(editor);
if(value != null) {
    timeSpinner.setValue(value);
}

datePanel.add(timeSpinner);

return datePanel;
}*/

    

    
    
}
