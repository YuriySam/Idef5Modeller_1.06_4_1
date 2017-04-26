/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.text.SimpleDateFormat;
import org.freixas.jcalendar.JCalendarCombo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * Для сохранения времени начала, конца и интервала Процесса/действия в отношениях
 * Состояния. Используется для рисования времени и даты объекта действие/процесс, который
 * входит в состав стрелки, которая создает отношение на экране. Возможные
 * варианты заполнения: 
 * - дата начала и дата конца; 
 * - дата начала равная дате конца, т.н. точечная дата события(выбранный день); 
 * - интервал (в секундах, но для удобства ввода выполняем в формате: деней, часов, минут, секунд. 
 * Незаполненные поля считаем равными нулю.).
 * Поля 
 *
 * @author 5521
 */
public class TimeDate {

    /**
     * Calendar timeBegin хранит дату начала действия в GregorianCalendar
     * формате
     */
    //private JCalendarCombo timeStart;
    /**
     * Calendar timeEnd хранит дату окончания действия в GregorianCalendar
     * формате;
     */
    //private JCalendarCombo timeEnd;
    /**
     * long timeInterval хранит время Интервала в милисекундах;
     */
    //private long timeInterval;
    private String[][] myData = {{"год", "yyyy"},
    {"месяц", "mm"},
    {"день", "dd"},
    {"час", "hh"},
    {"минуты", "mm"},
    {"секунды", "ss"},
    {"Начало", "mms"},
    {"Конец", "mms"},
    {"Интервал", "mms"}};
    
    /**
     * Взяты работающие части из обоих предыдущих вариантов. Проблемы: -
     * выполняется учет "летнего" времени - во аремя действия "летнего" времени
     * идет потеря 1 часа, после окончания, этот час находится/появляется.
     */
    TimeDate(JCalendarCombo timeStart, JCalendarCombo timeEnd, boolean ws) {
        this.setTimeDate(timeStart, timeEnd);
    }
    
    TimeDate(String start, String end, String interval){
        this.myData[6][1]=start;
        this.myData[7][1]=end;
        this.myData[8][1]=interval;
        //расчитаем оставшиеся поля date[][]
        setDatePartFrom_IntervalMillisec();
    }
    public String[][] toDstaStert(String dt){

        // Получить текущее время и дату 
        LocalDateTime tnow = LocalDateTime.now(); 
        int hour = tnow.get(ChronoField.HOUR_OF_DAY); 
        int minute = tnow.get(ChronoField.MINUTE_OF_HOUR); 
        int month = tnow.get(ChronoField.MONTH_OF_YEAR); 
        int day = tnow.get(ChronoField.DAY_OF_MONTH); 
        int year = tnow.get(ChronoField.YEAR); 
        return        null;
    }
    /**
     * основной метод наполнения класса данными.
     */
    public void setTimeDate(JCalendarCombo timeStart, JCalendarCombo timeEnd) {
        //this.timeStart = timeStart;
        //this.timeEnd = timeEnd;
        //System.out.println("54 ------- timeStart" + timeStart.getCalendar().getTime() + "   " + timeStart.getDate() + "---  " + timeStart.getCalendar().getTimeInMillis());
        //System.out.println("55 ------- timeEnd" + timeEnd.getCalendar().getTime() + "   " + timeEnd.getDate() + "---  " + timeEnd.getCalendar().getTimeInMillis());
        //System.out.println("");
        
        if (timeStart != null && timeEnd != null) {
            LocalDate start = LocalDate.of(
                    timeStart.getCalendar().get(Calendar.YEAR),
                    timeStart.getCalendar().get(Calendar.MONTH),
                    timeStart.getCalendar().get(Calendar.DATE));
            LocalDate end = LocalDate.of(
                    timeEnd.getCalendar().get(Calendar.YEAR),
                    timeEnd.getCalendar().get(Calendar.MONTH),
                    timeEnd.getCalendar().get(Calendar.DATE));
            Period p = Period.between(start, end);
            long p2 = ChronoUnit.DAYS.between(start, end);
            //System.out.println(" period p= "+p.toString());
            //System.out.println(" period p2= "+p2);
            //TimeDate tempTimeDate= new TimeDate(timeStart, timeEnd,true);
            //tempTimeDate.setMyData([][]);
            //System.out.println(" period3="+ this.setDatePartFrom_IntervalMillisec((timeEnd.getCalendar().getTimeInMillis()-timeStart.getCalendar().getTimeInMillis());
            //Просчитаем интервал для часов, минут и секунд.
            //long intervalTime =c.getTimeInMillis()-timeBegin.getTimeInMillis();
            long interval = ( timeEnd.getDate().getTime() -  timeStart.getDate().getTime());
            int ey2 = (int) (interval / 1000 / 60 / 60 / 24 / 30 /12 );//year
            long ey2o = (interval - ey2 * 12* 30 * 24 * 60 * 60 * 1000L);//yearOst
            int eM2 = (int) (ey2o / 1000 / 60 / 60 / 24 / 30);//Month
            long eM2o = ey2o - (eM2 *30 * 24 * 60 * 60 * 1000L);//MonthOst
            int ed2 = (int) eM2o / 1000 / 60 / 60 / 24;//day
            long ed2o = eM2o - (ed2 * 24 * 60 * 60 * 1000L);//dayOst
            int eh2 = (int) ed2o / (60 * 60 * 1000);//hour ::
            long eh2o = ed2o - (eh2 * 60 * 60 * 1000L);//hourOst
            int em2 = (int) eh2o / (60 * 1000);//min ::
            long em2o = eh2o - (em2 * 60 * 1000L);//minOst
            int es2 = (int) em2o / (1000);//sec ::
            long es2o = em2o - (es2 * 1000);//minOst

            //Присвоим полученные значения полям строки Интервал.
            myData[0][1] = "" + p.getYears();
            myData[1][1] = "" + p.getMonths();
            myData[2][1] = "" + p.getDays();
            myData[3][1] = "" + eh2;
            myData[4][1] = "" + em2;
            myData[5][1] = "" + es2;
            myData[6][1] = "" + timeStart.getDate().getTime();
            myData[7][1] = "" + timeEnd.getDate().getTime();
            myData[8][1] = "" + interval;
            //printDate(" Введен Интервал");
            

            

        }
    }
    
    
    //public JCalendarCombo getTimeStart() {
    //    return timeStart;
    //}

    //public void setTimeStart(JCalendarCombo timeStartNew) {
    //    this.setTimeDate(timeStartNew, this.timeEnd);
    //}

    //public JCalendarCombo getTimeEnd() {
//
    //    return timeEnd;
    //}

    /**
     * Сохраним измененную конечную дату. Пересчитаем все величины с ней
     * связанные: интервал
     *
     * @param timeEndNew
     */
    //public void setTimeEnd(JCalendarCombo timeEndNew) {
    //    this.setTimeDate(this.timeStart, timeEndNew);
    //}

    //public JCalendarCombo getTimeDate() {
    //    return timeDate;
    //}
    //public void setTimeDate(JCalendarCombo timeDate) {
    //    this.timeDate = timeDate;
    //}
    //public long getTimeInterval() {
    //    return timeInterval;
    //}
    //public void setTimeInterval(long timeInterval) {
    //    this.timeInterval = timeInterval;
    //}
    public String[][] getMyData() {
        return myData;
    }

    public void setMyData(String[][] myData) {
        this.myData = myData;
    }

    /**
     * Берем наибольший значимый параметр (название и значение) из год, день,
     * час, минут, секунд в найденном интервале. Или берем 1 день(название и
     * значение), если это точечный интервал. Или 0,0
     */
    public String[] getIntervalActual() {
        String[] intervalActual = {"0", "0"};
        for (String[] name : myData) {
            if (!name[1].equals("0")) {
                intervalActual[0] = name[0];
                intervalActual[1] = name[1];
                return intervalActual;
            }
        }
        return intervalActual;
    }

    /**
     * Расчитаем поля интервала из милисекунд Возимем самое старшее значимое поле
     */
    public String getFirstDatePart() {
        if(this.myData[8][1].length()>0){
        //преобразуем String милисекунды в long
        long interval = Long.parseLong(this.myData[8][1]);
        //из интервала в милисекундах расчитаем интервал в годах, месяцах, днях, часах, минутах и секундах
            //Просчитаем интервал для часов, минут и секунд.
            //long intervalTime =c.getTimeInMillis()-timeBegin.getTimeInMillis();
            //long interval = ((long) timeEnd.getDate().getTime() - (long) timeStart.getDate().getTime());
            int ey2 = (int) (interval / 1000 / 60 / 60 / 24 / 30 / 12);//year
            long ey2o = interval - (ey2 *12 * 30 * 24 * 60 * 60 * 1000);//yearOst
            int eM2 = (int) (ey2o / 1000 / 60 / 60 / 24 / 30);//Month
            long eM2o = ey2o - (eM2 *30 * 24 * 60 * 60 * 1000);//MonthOst
            int ed2 = (int) eM2o / 1000 / 60 / 60 / 24;//day
            long ed2o = eM2o - (ed2 * 24 * 60 * 60 * 1000);//dayOst
            int eh2 = (int) ed2o / (60 * 60 * 1000);//hour ::
            long eh2o = ed2o - (eh2 * 60 * 60 * 1000);//hourOst
            int em2 = (int) eh2o / (60 * 1000);//min ::
            long em2o = eh2o - (em2 * 60 * 1000);//minOst
            int es2 = (int) em2o / (1000);//sec ::
            long es2o = em2o - (es2 * 1000);//minOst
        
        /*
        //преобразуем милисекунды в часы, минуты, секунды
        //String hms = String.format("%02d  %02d:%02d:%02d", TimeUnit.MILLISECONDS.toDays(hml),
        //        TimeUnit.MILLISECONDS.toDays(hml) % TimeUnit.DAYS.toDays(1),
        //        TimeUnit.MILLISECONDS.toMinutes(hml) % TimeUnit.HOURS.toMinutes(1),
        //        TimeUnit.MILLISECONDS.toSeconds(hml) % TimeUnit.MINUTES.toSeconds(1));

        long dm = TimeUnit.MILLISECONDS.toDays(hml);
        long hm = TimeUnit.MILLISECONDS.toDays(hml) % TimeUnit.DAYS.toDays(1);
        long mm = TimeUnit.MILLISECONDS.toMinutes(hml) % TimeUnit.HOURS.toMinutes(1);
        long sm = TimeUnit.MILLISECONDS.toSeconds(hml) % TimeUnit.MINUTES.toSeconds(1);
        System.out.println("dm= " + dm + " hm= " + hm + " mm= " + mm + " sm+ " + sm);

        //приведем к арифметическому виду
        long ey2, eM2, ed2, eh2, em2, es2;
        ey2 = Long.parseLong(myData[0][1]) * 1000 * 60 * 60 * 24 * 30 * 12;//year}
        eM2 = Long.parseLong(myData[1][1]) * 1000 * 60 * 60 * 24 * 30; //month
        ed2 = Long.parseLong(myData[2][1]) * 1000 * 60 * 60 * 24; //day
        eh2 = Long.parseLong(myData[3][1]) * 1000 * 60 * 60;//hour ::
        em2 = Long.parseLong(myData[4][1]) * 1000 * 60;//min
        es2 = Long.parseLong(myData[5][1]) * 1000;//sec
        */
        //this.printDate("математич.вид");

        //String hms1 = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(hml),
        //        TimeUnit.MILLISECONDS.toMinutes(hml) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(hml)),
        //        TimeUnit.MILLISECONDS.toSeconds(hml) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(hml)));
        for (String[] perDo : this.getMyData()) {
            //Если значение не равно "0", возвращаем запись.
            //т.к. элементы расположены по-убыванию, мы получим самый старший разряд.
            if (!perDo[1].equals("0")) {
                //преобразуем String милисекунды в long
                //System.out.println("244   getBTtext(1)= " + perDo[1] + " " + perDo[0] + "  interval= " + interval);
                String perDoOut = perDo[0] + "= " + perDo[1];//соберем в одну переменную
                return perDoOut;
            }
        }
    }
        return "";
    }

    public void printDate(String lable) {
        System.out.println("timeDate date[][] " + lable + "  0"
                + myData[0][0] + "= " + myData[0][1] + ",  1"
                + myData[1][0] + "= " + myData[1][1] + ",  2"
                + myData[2][0] + "= " + myData[2][1] + ",  3"
                + myData[3][0] + "= " + myData[3][1] + ",  4"
                + myData[4][0] + "= " + myData[4][1] + ",  5"
                + myData[5][0] + "= " + myData[5][1] + ",  6"
                + myData[6][0] + "= " + myData[6][1] + ",  7"
                + myData[7][0] + "= " + myData[7][1] + ",  8"
                + myData[8][0] + "= " + myData[8][1] + ".  ");
        
    }
    /**создадим поля Date введенного интервала их миллисекунд. Чтоб если минуты, = то 60, а дальше - часы и т.д.*/
    public void setDatePartFrom_IntervalMillisec(){
        if(this.myData[8][1].length()>0){
            //преобразуем String милисекунды в long
            long interval = Long.parseLong(this.myData[8][1]);
            //из интервала в милисекундах расчитаем интервал в годах, месяцах, днях, часах, минутах и секундах
            //Просчитаем интервал для часов, минут и секунд.
            int ey2 = (int) (interval / 1000 / 60 / 60 / 24 / 30 /12);//year
            long ey2o = interval - (ey2 *12 * 30  * 24 * 60 * 60 * 1000L);//yearOst
            int eM2 = (int) (ey2o / 1000 / 60 / 60 / 24 /30);//Month
            long eM2o = ey2o - (eM2 *30 * 24 * 60 * 60 * 1000L);//MonthOst
            int ed2 = (int) eM2o / (1000 * 60 * 60 * 24);//day
            long ed2o = eM2o - (ed2 * 24 * 60 * 60 * 1000L);//dayOst
            int eh2 = (int) ed2o / (60 * 60 * 1000);//hour ::
            long eh2o = ed2o - (eh2 * 60 * 60 * 1000);//hourOst
            int em2 = (int) eh2o / (60 * 1000);//min ::
            long em2o = eh2o - (em2 * 60 * 1000L);//minOst
            int es2 = (int) em2o / (1000);//sec ::
            //long es2o = em2o - (es2 * 1000);//minOst
            this.myData[0][1] =""+ey2;
            this.myData[1][1] =""+eM2;
            this.myData[2][1] =""+ed2;
            this.myData[3][1] =""+eh2;
            this.myData[4][1] =""+em2;
            this.myData[5][1] =""+es2;
        }else{
            //присвоим найденные значения частям Даты
            this.myData[0][1] ="";
            this.myData[1][1] ="";
            this.myData[2][1] ="";
            this.myData[3][1] ="";
            this.myData[4][1] ="";
            this.myData[5][1] ="";
        }
    
    }
    /**создадим из полей Date введенного интервала их миллисекундный эквивалент. Чтоб если минуты, = то 60, а дальше - часы и т.д.*/
    public void setIntervalMillisecFrom_DatePart(){
        //преобразуем String каждого из введенных полей в long милисекунд.
        long py = Long.parseLong(this.myData[0][1]);//year
        long pM = Long.parseLong(this.myData[1][1]);//month
        long pd = Long.parseLong(this.myData[2][1]);//day
        long ph = Long.parseLong(this.myData[3][1]);//hour
        long pm = Long.parseLong(this.myData[4][1]);//minutes
        long ps = Long.parseLong(this.myData[5][1]);//second
        
        //из интервала  в годах, месяцах, днях, часах, минутах и секундах расчитаем интервал в милисекундах 
        //Просчитаем значения для часов, минут и секунд.
        long pyo = py * 12 * 30  * 24 * 60 * 60 * 1000;//yearOst
        long pMo = pM * 30 * 24 * 60 * 60 * 1000;//MonthOst
        long pdo = pd * 24 * 60 * 60 * 1000;//dayOst
        long pho = ph * 60 * 60 * 1000;//hourOst
        long pmo = pm * 60 * 1000;//minOst
        long pso = ps * 1000;//minOst
        //присвоим найденные значения частям Даты
        long interval=pyo+pMo+pdo+pho+pmo+pso;
        this.myData[8][1] =""+interval;
     
    
    }
    /**
     * Формируем текст для первого маленького квадратика. Этим текстом является
     * приблизительный период процесса/действия. Например, если задан и секунды
     * и дни, выводим дни. Вывод состоит из величины значения, и названия описания.
     * @return 
     */
    public String getBText() {
        if (getMyData() != null) {
            //разберем, вид временного контента:
            //это интервал.
            //это интервал между датами.
            //это временная точка.
            //Если значения Начала и конца ==0, а значение Интервала введено, то это интервал.
            if (this.getMyData()[6][1].equals("0") && this.getMyData()[7][1].equals("0")
                    && !this.getMyData()[8][1].equals("0")) {
                //В первом малом квадрате выводим приблизительный период процесса/действия.
                //Расчитаем поля интервала из милисекунд
                //Возимем самое старшее значимое поле
                
                return this.getFirstDatePart();
            }
            //Если значения Начала и Конца введены и не совпадают, и введено значение Интервала, то это интервал между датами.
            if (!this.getMyData()[6][1].equals("0") && !this.getMyData()[7][1].equals("0") && !this.getMyData()[7][1].equals(this.getMyData()[6][1])
                    &&!this.getMyData()[8][1].equals("0")) {
                //В первом малом квадрате выводим приблизительный период процесса/действия.
                for (String[] perDo : this.getMyData()) {
                    //Если значение не равно "0", возвращаем запись.
                    //т.к. элементы расположены по-убыванию, мы получим самый старший разряд.
                    if (!perDo[1].equals("0")) {
                        //System.out.println("getBTtext(2)= "+perDo[1]+" "+perDo[0]); 
                        String perDoOut = perDo[0]+"= "+perDo[1];//соберем в одну переменную
                        return perDoOut;
                    }
                }
            }
            
        }
        //выводим пустую запись.
        String[] perDo = {"", ""};
        //System.out.println("getBTtext(4)= "+perDo[1]+" "+perDo[0]); 
        String perDoOut = perDo[0]+perDo[1];//соберем в одну переменную
        return perDoOut;
    }
    
    /**
     * Формируем текст для второго маленького квадратика. Этим текстом является
     * приблизительный интервал процесса/действия. Например, если задан
     * начальная и конечная дата и они разные, выводим их через тире. если
     * заданы начальная и конечная даты и оно одинаковы, выводим эту дату. Вывод
     * состоит из величины значения, и названия описания. Преобразуем выводимые
     * данные в формат dd/mm/yyyy
     * @return 
     */
    public String getCText() {
        if (getMyData() != null) {
            //разберем, вид временного контента:
            //это интервал.(не выводим)
            //это интервал между датами.
            //это временная точка.
            //Если значения Начала и Конца введены и не совпадают, и введено значение Интервала, то это интервал между датами.
            if (!this.getMyData()[6][1].equals("0") && !this.getMyData()[7][1].equals("0") 
                    && !this.getMyData()[7][1].equals(this.getMyData()[6][1])
                    && !this.getMyData()[8][1].equals("0")) {
                //Во втором малом квадрате выводим сокращенные даты начала и конца периода процесса/действия.
                //Преобразуем выводимые данные в формат dd/mm/yyyy*/
                long l2 = 0;
                try {
                    l2 = Long.valueOf(this.getMyData()[6][1]);
                    ////System.out.println("l2= " + l2);
                } catch (NumberFormatException e) {
                    System.err.println("Неверный формат строки!");
                }
                Date sData = new Date(l2);
                //System.out.println("sDate = " + sData.toString());
                SimpleDateFormat myDateFormat = new SimpleDateFormat("dd.MM.yyyy"); //Задали шаблон строки
                String cText6 = myDateFormat.format(sData);     //получили дату в нужном формате
                l2 = 0;
                try {
                    l2 = Long.valueOf(this.getMyData()[7][1]);
                    //System.out.println("l2= " + l2);
                } catch (NumberFormatException e) {
                    System.err.println("Неверный формат строки!");
                }
                sData = new Date(l2);
                //System.out.println("sDate = " + sData.toString());
                myDateFormat = new SimpleDateFormat("dd.MM.yyyy"); //Задали шаблон строки
                String cText7 = myDateFormat.format(sData);     //получили дату в нужном формате
                
                //String cText = sData.toString();
                String cText=cText6+"-"+cText7;
                //System.out.println("getCTtext(1)= "+cText);   
                return cText;
            }
            //Если значение Начала и Конца введены и совпадают, то это временная точка.
            if (!this.getMyData()[6][1].equals("0") && this.getMyData()[7][1].equals(this.getMyData()[6][1])
                    && this.getMyData()[8][1].equals("0")) {
                //Во втором малом квадрате выводим сокращенные дату временной точки процесса/действия.
                long l2 = 0;
                try {
                    l2 = Long.valueOf(this.getMyData()[6][1]);
                    //System.out.println("l2= " + l2);
                } catch (NumberFormatException e) {
                    System.err.println("Неверный формат строки!");
                }
                Date sData = new Date(l2);
                //System.out.println("sDate = " + sData.toString());
                SimpleDateFormat myDateFormat = new SimpleDateFormat("dd.MM.yyyy"); //Задали шаблон строки
                String cText6 = myDateFormat.format(sData);     //получили дату в нужном формате
                
                
                String cText = cText6;
                //System.out.println("временная точка getCTtext(2)= "+cText);    
                return cText;
            }
            String cText = "";
            //System.out.println("пустой вывод getCTtext(3)= "+cText);   
            return cText;
        }
        String cText = "";
        //System.out.println("пустой вывод getCTtext(4)= "+cText);   
            return cText;
    }

}
