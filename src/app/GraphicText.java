/*
 * class GraphicText 
 * имеет функцию getTextPxSize  
 * для вычисления длины и высоты текста  в пикселях
 * 
 * запускать так:
 * Dimension dm = GraphicText.getTextPxSize(new Font(Font.SANS_SERIF,Font.PLAIN,14), text);
   // dm.width <- тут ширина
   // dm.height <- тут высота
 */
package app;

/**
 *
 * @author Admin
 * 
 * Метод нужен для расчета ширины текста в Эллипсе.
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GraphicText {
    
    private static BufferedImage hiddenImg; //будущий объект изображения, который любезно предоставит объект Graphics
    
    //статичный метод, ну чтобы не создавать каждый раз объект...
    public static Dimension getTextPxSize(Font font, String text){
        if(hiddenImg == null){
                //создаем изображение
            hiddenImg = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
        }
        Graphics g = hiddenImg.getGraphics();  //выдергиваем из него объект graphics
        FontMetrics fm = g.getFontMetrics(font);
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        return new Dimension(width,height);
    }//end method
    
}//end class
