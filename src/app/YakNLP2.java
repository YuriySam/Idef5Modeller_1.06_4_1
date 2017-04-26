package app;

/**
 * Класс для обработки текстов на русском языке, версия 2.
 *
 * @author yak49
 */
public class YakNLP2 {

    /**
     * Очистка слова от небуквенных символов(кроме дефисов, тире и пробелов) и
     * приведение его к нижнему регистру.
     *
     * @param s строчный параметр, содержащий исходное слово русского языка.
     * @return Результирующее слово.
     */
    public static String delNoDigOrLet(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            //Только кириллица
            if ((Character.isLetterOrDigit(s.charAt(i))/* && s.charAt(i) >=  0x0400 && s.charAt(i) <= 0x04FF*/)
                    || s.charAt(i) == '-'
                    || s.charAt(i) == '–'
                    || s.charAt(i) == ' ') {
                sb.append(s.charAt(i));
            }
        }

        return sb.toString().toLowerCase();
    }
}
