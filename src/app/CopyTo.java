/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author 5521
 * Для создания копии экземпляра класса, а не ссылки на класс.
 */
public interface CopyTo {
    /**Метод для создания копии экземпляра класса, а не ссылки на класс
     * @return */
    public Object copyTo();
}
