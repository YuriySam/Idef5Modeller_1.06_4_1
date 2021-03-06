/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app;

import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author 655
 */

public class JDialog_CreateProject extends javax.swing.JDialog {
    private static final long serialVersionUID = 112345678L; // Change number as appropriate
    /**
     * Creates new form JDialog_CreateProject
     */
    //создадим переменную для проекта который создал пользователь
    private MyProject myProject;
    /**Индикатор создания пользователем нового проекта. По-умолчанию - false.
     При  создании нового проекта - true.*/
    private boolean newProject;
    //private String def_proektP;
    //private String def_oblastP;
    //private String def_avtorP;
    JTabbedPane jTabbedPane1;
    
    /**берем значения полей по-умолчанию и панель загруженых проектов, чтоб не открыть один проект дважд
     * @param parent
     * @param modal
     * @param def_proektP
     * @param def_oblastP
     * @param def_avtorP
     * @param jTabbedPane1*/
    public JDialog_CreateProject(java.awt.Frame parent, boolean modal,String def_proektP,String def_oblastP,String def_avtorP,JTabbedPane jTabbedPane1) {
        
        super(parent, modal);
        initComponents();
        this.myProject= new MyProject();
        newProject=false;
        //Заполним поля формы значениями по-умолчанию.
        this.proektP.setText(def_proektP);
        this.oblastP.setText(def_oblastP);
        this.avtorP.setText(def_avtorP);
        //установим кнопку по-умолчанию для этого окна
        //JRootPane rootPane1; 
        //rootPane1 = SwingUtilities.getRootPane(create);
        //rootPane1.setDefaultButton(create);
        this.jTabbedPane1=jTabbedPane1;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel8 = new javax.swing.JLabel();
        proektP = new javax.swing.JTextField();
        oblastP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        avtorP = new javax.swing.JTextField();
        create = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Создать новый проект");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel8.setText("Название проекта:* ");

        proektP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                proektPKeyPressed(evt);
            }
        });

        oblastP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                oblastPKeyPressed(evt);
            }
        });

        jLabel9.setText("Предметная область:");

        jLabel10.setText("Автор проекта:");

        avtorP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                avtorPKeyPressed(evt);
            }
        });

        create.setText("Ok");
        create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createActionPerformed(evt);
            }
        });
        create.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                createKeyPressed(evt);
            }
        });

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("  (Длина от 3 до 15.  Разрешены цифры, русск., англ. буквы. )");

        jLabel2.setText("*   выделенное поле обязательно для заполнения");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(create)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancel))
                            .addComponent(avtorP, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(proektP, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(oblastP))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proektP, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(oblastP, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(avtorP, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel)
                    .addComponent(create))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // Кнопка "Отмена"
        newProject=false;
        setVisible(false); 
        dispose();
    }//GEN-LAST:event_cancelActionPerformed
    /**Для получения онформации про результат работы класса.
     Возвращаем метку о создании проекта.
     * true - если проект создан.
     false - если создание проекта отменено
     * @return .*/
    public boolean getNewProject(){
        return newProject;
    }
    
    private void createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createActionPerformed
        // Кнопка "Ok"
        createProjectOk();   
        
    }//GEN-LAST:event_createActionPerformed

    private void createKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_createKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            //обработка нажатия ENTER
            createProjectOk();
        }
    }//GEN-LAST:event_createKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // если на форме ввода пресс Энтер:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            //обработка нажатия ENTER
            createProjectOk();
        }
    }//GEN-LAST:event_formKeyPressed

    private void proektPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proektPKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            //обработка нажатия ENTER
            createProjectOk();
        }
    }//GEN-LAST:event_proektPKeyPressed

    private void oblastPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oblastPKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            //обработка нажатия ENTER
            createProjectOk();
        }
    }//GEN-LAST:event_oblastPKeyPressed

    private void avtorPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_avtorPKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            //обработка нажатия ENTER
            createProjectOk();
        }
    }//GEN-LAST:event_avtorPKeyPressed

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
            java.util.logging.Logger.getLogger(JDialog_CreateProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_CreateProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_CreateProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_CreateProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                /*
                JDialog_CreateProject dialog = new JDialog_CreateProject(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
           */
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JTextField avtorP;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancel;
    private javax.swing.JButton create;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    protected javax.swing.JTextField oblastP;
    protected javax.swing.JTextField proektP;
    // End of variables declaration//GEN-END:variables


    /**Возвращаем созданный проект
     * @return .*/
    public MyProject getmyProject(){
        return myProject;
    }
    private void createProjectOk(){
        
        
        //Создадим имя файла Проекта.
        //Проверим, что имя проекта есть и оно еще не использовалось.
        if(proektP.getText().length()>0){//проверим, что имя проекта есть и оно корректно
            //В имени проекта использованы только русские буквы и арабские цифры.
            //public static boolean checkWithRegExp(String userNameString){  
            Pattern p = Pattern.compile("^[-А-яA-z0-9]{3,15}$");  
            Matcher m = p.matcher(proektP.getText());  
            //return m.matches();  
            //}
            if(!m.matches()){//Название не соответствуем правильному.
                JOptionPane.showMessageDialog(null,"Выбранное Название проекта: "+proektP.getText()+"   содержит меньше 3 или больше 15 символов. Или небукво-цифровые символы(в т.ч. _).");
                return;
            }
        }else{//если ничего не введено
            JOptionPane.showMessageDialog(null,"Выбранное Название проекта: "+proektP.getText()+" содержит меньше 1 символа.");
            return;
            }
        
        
        
        MyProject mProject=new MyProject();
            //создадим проект, заполним его шапку, откроем его данные.
            //заносим данные о выбранном проекте в поля класса MyProject
            mProject.setPName(proektP.getText());
            mProject.setPOblast(oblastP.getText());
            mProject.setPAvtor(avtorP.getText());
            mProject.setPNameFull();
        //проверим, что имя проекта нет среди открытых проектов
        for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
                if(mProject.getPNameFull().equals(jTabbedPane1.getTitleAt(i))){
                    JOptionPane.showMessageDialog(null," Проект с  именем "+mProject.getPNameFull()+" уже открыт");
                    return;
                }
            }    
            
            //mProject.importFromFile(file);//получим данные проекта.
            //добавим открываемый проект в массив открытых проектов
            //app.App.myProjectArray.add(mProject);
            //добавим выбранный проект как актуальный проект на экран
            myProject=mProject;
            //add_JTabbed_Project(jTabbedPane1,myProject);
            CustomJPanel.changed=true;//подберем размер окон проекта под размер данных
       this.newProject=true;
        setVisible(false); 
        dispose();
        
    
    }
}
