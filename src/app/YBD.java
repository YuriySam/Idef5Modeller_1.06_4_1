/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;

/**
 *Этот класс создан для работы с отношением таксономии из Юрика БД онтологий.
 * @author 655
 */
public class YBD {
    /**Код Класса отношения.*/
    private int entity_id_1;
    /**Текст Класса отношения.*/
    private String entity_Klass;
    /**Код Элемента отношения.*/
    private int entity_id_2;
    /**Текст Элемента отношения.*/
    private String entity_Elem;
    /**Код Признака отношения.*/
    private int sign_id;
    /**Текст Признака отношения.*/
    private String sign_name;
    /**Код Суперкласса, т.е. 0-го уровня Предметной области*/
    private int word_id;
    /**Текст Суперкласса, т.е. 0-го уровня Предметной области*/
    private String word_name;
    /**Уровень для отображения отношения.*/
    private int level;
    /**Коридор расположения элементов. Первый забирает от 0 до wy, и т.д.*/
    double wx,wy;
    /**Индекс(ряд) порядковый в массиве 0-го уровня Предметной области*/
    int ryad;
    int x,y;//for  public MyEll setPointToDisplay(String text){
    /**Количество найденных отношений, но на разных сайтах. */
    private int amount;
    /**Список сайтов, где встретилась пара. */
    private ArrayList <String> amountAr;
    
    YBD(){
        initComponent();
    }
    private void initComponent(){
        entity_Klass="";
        entity_Elem="";
        sign_name="";
        word_name="";
        x=0;
        y=0;
        amountAr=new ArrayList<String>();
        
    }
    
    public int get_entity_id_1(){
        return this.entity_id_1;
    }
    public String get_entity_Klass(){
        return this.entity_Klass;
    }
    public int get_entity_id_2(){
        return this.entity_id_2;
    }
    public String get_entity_Elem(){
        return this.entity_Elem;
    }
    public int get_sign_id(){
        return this.sign_id;
    }
    public String get_sign_name(){
        return this.sign_name;
    }
    public int get_word_id(){
        return this.word_id;
    }
    public String get_word_name(){
        return this.word_name;
    }
    public int get_level(){
        return this.level;
    }
    
    public double get_wy(){
        return this.wy;
    }
    public int get_ryad(){
        return this.ryad;
    }
    public ArrayList<String> get_amountAr(){
        return this.amountAr;
    }
    public void  set_entity_id_1(int entity_id_1){
        this.entity_id_1=entity_id_1;
    }
    public void set_entity_Klass(String entity_Klass){
         this.entity_Klass=entity_Klass;
    }
    public void  set_entity_id_2(int entity_id_2){
        this.entity_id_2=entity_id_2;
    }
    public void set_entity_Elem(String entity_Elem){
         this.entity_Elem=entity_Elem;
    }
    public void set_sign_id(int sign_id){
         this.sign_id=sign_id;
    }
    public void set_sign_name(String sign_name){
         this.sign_name=sign_name;
    }
    public void set_word_id(int word_id){
         this.word_id=word_id;
    }
    public void set_word_name(String word_name){
         this.word_name=word_name;
    }
    public void set_level(int level){
         this.level=level;
    }
    public void set_wy(double wy){
         this.wy=wy;
    }
    public void set_ryad(int ryad){
         this.ryad=ryad;
    }
    public void set_amountAr(ArrayList <String> amountAr){
         this.amountAr=amountAr;
    }
    
    /**
     * @return */
    public int getAmount(){
        return this.amount;
    }
    /**
     * @param amount*/
    public void setAmount(int amount){
        this.amount=amount;
    }
    
    
}
