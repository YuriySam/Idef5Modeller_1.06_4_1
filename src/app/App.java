/*
 * 
 */
package app;

import javax.swing.ImageIcon;

/**
 *
 * @author Admin
 */
public class App {

    /**
     */
      //создадим экземпляр массива проектов, которые будут показаны на экране
    //protected static  ArrayList <MyProject>  myProjectArray;
    /**Название программы*/
    protected static String name;
    /**Версия программы.*/
    protected static String release;
    /**Продукт лицензирован на...*/
    protected static String licensedTo;
    /**Код продукта*/
    protected static String siteID;
    protected static MyJFrame myJFrame;
    private MyProject myProject;
     
    public static void main(String[] args) {
        name="Idef5Modeller";
        release = "1.06_3 beta";
        licensedTo="free licensed";
        siteID=licensedTo;
        //myProjectArray  =  new <MyProject> ArrayList();
        myJFrame=new MyJFrame();
        ImageIcon image = new ImageIcon("logo_v1.png");//заменим чашку кофев углу окна
        //myJFrame.setIconImage(image.getImage()); 
        //Image im=Toolkit.getDefaultToolkit().getImage("/src/app/icon/logo_v1.png");
        myJFrame.setIconImage(image.getImage());
        myJFrame.setLocationRelativeTo(null);//открываем myJFrame по центру экрана
        myJFrame.setVisible(true);
        //new MyJFrame().setVisible(true);
      
        //new MyFrame().setVisible(true); 
        //Kvadrat m1 = new Kvadrat ("Kvadrat"); 
    }
}