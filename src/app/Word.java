package app;

/**
 * Класс для выражения слова на произвольном языке.
 *
 * @author yak49
 */
public abstract class Word {

    /**
     * Слово
     */
    protected String word;

    /**
     * Часть речи
     */
    protected String part;

    /**
     * Число
     */
    protected String number;

    /**
     * Род
     */
    protected String male;
    /**
     * Время
     */
    protected String time;

    /**
     * Одушевлённость
     */
    protected String life;

    /**
     * Нормальная форма слова
     */
    protected String normWord;
}
