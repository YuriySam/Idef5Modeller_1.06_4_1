/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 * Онтологическое отношение Таксономии.
 * @author 655
 * 24/07/2016
 */
public class OntoTaks extends MyOnto{
    MyEll ellKlass;
    MyEll ellElem;
    public OntoTaks(MyEll ellKlass, MyEll ellElem, String priznak, String url, String shape){
        super.klass=ellKlass.getText();
        super.klassX=ellKlass.getX();
        super.klassY=ellKlass.getY();
        super.elem=ellElem.getText();
        super.elemX=ellElem.getX();
        super.elemY=ellElem.getY();
        super.priznak=priznak;
        super.url=url;
        super.shape=shape;//тип отношения, что рассматривается
        this.ellKlass=ellKlass;
        this.ellElem=ellElem;        
    }
     /**
     * @return Само слово класса.
     */
    public String getKlass() {
        return super.klass;
    }
 /**
     * @return Координата X слова класса.
     */
    public double getKlassX() {
        return super.klassX;
    }
 /**
     * @return Координата Y слова класса.
     */
    public double getKlassY() {
        return super.klassY;
    }

    /**
     * @return Само слово элемента.
     */
    public String getElem() {
        return super.elem;
    }

    /**
     * @return Координата X слова элемента.
     */
    public double getElemX() {
        return super.elemX;
    }

    /**
     * @return Координата Y слова элемента.
     */
    public double getElemY() {
        return super.elemY;
    }

    /**
     * @return Само слово признака. 
     */
    public String getPriznak() {
        return super.priznak;
    }
    /**
     * @return Сам адрес сайта, где нашлось отношение.
     */
    public String getUrl() {
        return super.url;
    }
    public MyEll getEllKlass(){
        return this.ellKlass;
    }
    public MyEll getEllElem(){
        return this.ellElem;
    }
}
