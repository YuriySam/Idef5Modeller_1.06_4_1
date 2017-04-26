package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс, описывающий слово русского языка на основе морфологического словаря
 * Хагена.
 *
 * @author yak49
 */
public class RusWord extends Word {

    /**
     * Падеж
     */
    private final String type;

    /**
     * Стандартный конструктор.
     *
     * @param c подключение к базе данных с таблицей word определенного формата
     * @param inputWord слово русского языка
     * @throws SQLException
     */
    public RusWord(Connection c, String inputWord) throws SQLException {
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from words where word='" + YakNLP2.delNoDigOrLet(inputWord) + "';");
        if (rs.next()) {
            super.word = rs.getString(1);
            super.part = rs.getString(2);
            super.number = rs.getString(3);
            super.male = rs.getString(4);
            this.type = rs.getString(5);
            super.time = rs.getString(6);
            super.life = rs.getString(9);
            super.normWord = rs.getString(7);
        } else {
            super.word = inputWord;
            super.part = null;
            super.number = null;
            super.male = null;
            this.type = null;
            super.time = null;
            super.life = null;
            super.normWord = null;
            PreparedStatement stmt2 = c.prepareStatement("select word_id from unknown_words where word = ?;");
            stmt2.setString(1, YakNLP2.delNoDigOrLet(inputWord));
            rs = stmt2.executeQuery();
            if(!rs.next()) {
                PreparedStatement stmt3 = c.prepareStatement("insert into unknown_words values(?, ?);");
                stmt3.setString(1, null);
                stmt3.setString(2, YakNLP2.delNoDigOrLet(inputWord));
                stmt3.executeUpdate();
            }
        }
    }

    /**
     * @return Само слово
     */
    public String getWord() {
        return super.word;
    }

    /**
     * @return Часть речи
     */
    public String getPart() {
        return super.part;
    }

    /**
     * @return Число слова
     */
    public String getNumber() {
        return super.number;
    }

    /**
     * @return Род слова
     */
    public String getMale() {
        return super.male;
    }

    /**
     * @return Время слова
     */
    public String getTime() {
        return super.time;
    }

    /**
     * @return Оживлённость/неоживлённость
     */
    public String getLife() {
        return super.life;
    }

    /**
     * @return Нормальная форма слова
     */
    public String getNormWord() {
        return super.normWord;
    }

    /**
     * @return Падеж слова
     */
    public String getType() {
        return this.type;
    }
}
