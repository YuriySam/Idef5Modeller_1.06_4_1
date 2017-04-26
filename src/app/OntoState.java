/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *Онтологическое отношение Состояния
 * @author 655
 * 24/07/2016
 */
public class OntoState extends MyOnto{
    /**Группа полей, фиксирующая временные показатели отношения состояния для данного отношения. 
     * Полагаю, что: 
     * String timeStart хранит время в милисекундах начала действия;
     * String timeEnd хранит время в милисекундах окончания действия;
     * String timeInterval хранит время в милисекундах интервала;
     * 15-05-2016
     */
    
    
    /**хранит время в милисекундах начала действия     */
    private String timeStart;
    /**хранит время в милисекундах окончания действия */
    private String timeEnd;
    /**String timeInterval хранит время в милисекундах интервала  */
    private String timeInterval;
    /**Эллипс, который отображает текст Класса отношения*/
    MyEll ellKlass;
    /**Эллипс, который отображает текст Элемента отношения*/
    MyEll ellElem;
    /**для датыВремени начала процесса-действия*/
    String priznakSart; //для датыВремени начала процесса-действия
    /**для датыВремени окончания процесса-действия*/
    String priznakEnd;//для датыВремени окончания процесса-действия
    /**для датыВремени интервала/продолжительности процесса-действия*/
    String priznakInterval;//для интервала/продолжительности процесса-действия
    public OntoState(MyEll ellKlass, MyEll ellElem, String priznak, String url, String shape, String priznakSart, String priznakEnd,String priznakInterval){
        super.klass=ellKlass.getText();
        super.klassX=ellKlass.getX();
        super.klassY=ellKlass.getY();
        super.elem=ellElem.getText();
        super.elemX=ellElem.getX();
        super.elemY=ellElem.getY();
        super.priznak=priznak;//сохраним-ка тутка действие/процесс
        super.url=url;
        super.shape=shape;
        this.ellKlass=ellKlass;
        this.ellElem=ellElem;
        this.priznakSart=priznakSart;
        this.priznakEnd=priznakEnd;
        this.priznakInterval=priznakInterval;
    }

    public String getPriznakSart() {
        return priznakSart;
    }

    public void setPriznakSart(String priznakSart) {
        this.priznakSart = priznakSart;
    }

    public String getPriznakEnd() {
        return priznakEnd;
    }

    public void setPriznakEnd(String priznakEnd) {
        this.priznakEnd = priznakEnd;
    }

    public String getPriznakInterval() {
        return priznakInterval;
    }

    public void setPriznakInterval(String priznakInterval) {
        this.priznakInterval = priznakInterval;
    }
    
     /**
     * @return Само слово класса. т.н. Результат.
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
     * @return Само слово элемента. т.н. Источник, начальный объект.
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
     * @return Само слово признака. т.н.Процесс или действие.
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

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    
}
