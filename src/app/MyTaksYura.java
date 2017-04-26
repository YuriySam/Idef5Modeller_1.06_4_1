/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Dimension;

/**Класс для работы с отношениями таксономии из Юрика БД.
 *
 * @author 655
 */
public class MyTaksYura {
    int entity_id_1;
    String entity_Klass;
    int entity_id_2;
    String entity_Elem;
    int sign_id;
    String sign_name;
    
    MyTaksYura(){
        
        initComponents();
    }
    /**Начальная инициация переменных*/
    private void initComponents(){
        //new MyTaks();
       entity_Klass="";
       entity_Elem="";
       sign_name="";
    }
    /**
     * @return код класса.*/ 
    public int getEntity_id_1(){
        return this.entity_id_1;
    }
    /**
     * @return текст Класса*/
    public String getEntity_Klass(){
        return this.entity_Klass;
    }
    /**
     * @return код Элемента*/
    public int getEntity_id_2(){
        return this.entity_id_2;
    }
    /**
     * @return текст Элемента*/
    public String getEntity_Elem(){
        return this.entity_Elem;
    }
    /**
     * @return код Признака.*/
    public int getSign_id(){
        return this.sign_id;
    }
    /**
     * @return текст Признака.*/
    public String getSign_name(){
        return this.sign_name;
    }
    
    
    /**
     * @param getEntity_id_1 код Класса.*/
    public void sedtEntity_id_1(int getEntity_id_1){
        this.entity_id_1=getEntity_id_1;
    }
    /**
     * @param entity_Klass текст Класса.*/
    public void setEntity_Klass(String entity_Klass){
        this.entity_Klass=entity_Klass;
    }
    /**
     * @param getEntity_id_2 код Элемента.*/
    public void setEntity_id_2(int getEntity_id_2){
        this.entity_id_2= getEntity_id_2;
    }
    /**
     * @param getEntity_Elem текст Элемента.*/
    public void setEntity_Elem(String getEntity_Elem){
        this.entity_Elem=getEntity_Elem;
    }
    /**
     * @param sign_id код Признака.*/
    public void setSign_id(int sign_id){
        this.sign_id=sign_id;
    }
    /**
     * @param sign_name текст Признака.*/
    public void setSign_name(String sign_name){
        this.sign_name=sign_name;
    }
}
