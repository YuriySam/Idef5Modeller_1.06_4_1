/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *Идея такая.
 * Найденные элементы объединяются в класс по какому-то признаку.
 * Поэтому Признак мы фиксируем для каждого сайта, где находим отношение таксономии.
 * @author 655 2.06.2016   16/09/2016
 */
public class PriznakUrl implements CopyTo{
    /** 2.06.2016  адрес, где встретилась пара с таким признаком     */
    private String url;
    /** 2.06.2016 текст признака отношения     */
    private String priznak;
    /**для датыВремени начала процесса-действия 16/09/2016*/
    private String priznakSart; //для датыВремени начала процесса-действия
    /**для датыВремени окончания процесса-действия 16/09/2016*/
    private String priznakEnd;//для датыВремени окончания процесса-действия
    /**для датыВремени интервала/продолжительности процесса-действия 16/09/2016*/
    private String priznakInterval;//для интервала/продолжительности процесса-действия
    
    /**
     * 2.06.2016
     */
    
    PriznakUrl(){
        initComponent();
    }
    PriznakUrl(String url, String priznak){
        this.url=url;
        this.priznak=priznak;
    }
    /**
     * 2.06.2016
     */
    private void initComponent(){
        url="";
        priznak="";
        this.priznakSart="0";
        this.priznakEnd="0";
        this.priznakInterval="0";
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
     * 2.06.2016
     * @param url
     */
    public void set_url(String url){
        this.url=url;
    }
    /**
     * 2.06.2016
     * @param priznak
     */
     public void set_priznak(String priznak){
         this.priznak = priznak;
     }
            /**
     * 2.06.2016
     * @return 
     */
     public String get_url(){
         return this.url;
     }
            /**
     * 2.06.2016
     */
     public String get_priznak(){
         return this.priznak;
     }

     /**Для копирования экземпляра Класса в новый объект.*/
    @Override
    public PriznakUrl copyTo() {
        PriznakUrl priznakUrlNew = new PriznakUrl();
        priznakUrlNew.url=  url;
        priznakUrlNew.priznak=  priznak;
        priznakUrlNew.priznakSart= priznakSart; //для датыВремени начала процесса-действия
        priznakUrlNew.priznakEnd= priznakEnd;//для датыВремени окончания процесса-действия
        priznakUrlNew.priznakInterval= priznakInterval;//для интервала/продолжительности процесса-действия
        return priznakUrlNew;
    }
}
