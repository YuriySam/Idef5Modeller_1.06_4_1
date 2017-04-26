/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 655
 * 
 * Класс нового Проекта
 */
public class MyProject {
    //Поля класса
    /**id проекта*/
    private int pid;
    /**Имя БД проекта*/
    private String namebd;
    /**Название проекта*/
    private String pName;
    /**Полное Название проекта. Состоит из= Названия проекта+ Предметная область+ автор*/
    private String pNameFull;
    /**Название области проекта*/
    private String pOblast;
    /**Автор проекта*/
    private String pAvtor;
    /**Путь к папке проекта*/
    private String pPath;
    /**полное имя файла проекта*/
    private String pFileName;
    /**Класс объектов Таксономии. Отображаются в окне Таксономии.*/
    private MyTaksArray myTaksArray;
    /**Класс объектов Композиции. Отображаются в окне Композиции.*/
    private MyComposArray myComposArray;
    /**Класс объектов Состояния. Отображаются в окне Состояния.*/
    private MyStateArray myStateArray;
    
    /**Активный класс, элементы которого отображаются на экране*/
    private String myEnabledArray;
    /**Индикатор необходимости сохранения Проекта. Если проект изменен, индикатор true, Если Проект еще не изменялся от последнего сохранения или открытия, индикатор = false.*/
    private boolean saving;
    //для окна выбора файла
    private javax.swing.JFileChooser fileChooser;
    
    /***/
    /***/
    
    //Конструктор по-умолчанию
    MyProject(){
        initComponent();
    }
    private void initComponent(){
        myTaksArray=new  MyTaksArray();//создадим класс для массива таксономий
        myComposArray=new MyComposArray();//создадим класс для массива Композиций.
        myStateArray=new MyStateArray();
        myEnabledArray="";
        saving=true;//при создании Проекта, изменений нет, поэтому сохранение не требуется.
        fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Открыть проект IDEF5");
        fileChooser.setFileFilter(new MyCustomFilter());

        //figura="";
    }
    //Мeтоды класса
    
    /**Получим id проекта.*/
    int getPid(){
    return  pid;
    }
    /**Получим имя БД проекта*/
    String getNamebd(){
        return  namebd;
    }
    /**Получим имя Проекта*/
    String getPName(){
        return  pName;
    }

    public String getPNameFull() {
        return pNameFull;
    }

    public void setPNameFull() {
        this.pNameFull = this.pName+"_"+this.pOblast+"_"+this.pAvtor;
    }
    
    /**Получим область проекта*/
    String getPOblast(){
        return  pOblast;
    }
    /**Получим автора проекта*/
    String getPAvtor(){
        return  pAvtor;
    }
    /**Получим путь к папке проекта*/
    String getPPath(){
        return  pPath;
    }
    /**Получим массив отношений таксономии проекта*/
    MyTaksArray getMyTaksArray(){
        return myTaksArray;
    }
    /**Получим массив отношений Композиций проекта
     * @return */
    protected MyComposArray getMyComposArray(){
        return this.myComposArray;
    }
    /**Получим массив отношений Топологии проекта*/
    MyStateArray getMyStateArray(){
        return myStateArray;
    }
    
    /**Присвоим значение имени проекта*/
    void setPName(String pName){
    this.pName = pName;
    }
    /**Присвоим значение ID записи*/
    void setPid(int pid){
    this.pid = pid;
    }
    /**Присвоим имя БД проекта.*/
    void setNamebd(String namebd){
    this.namebd = namebd;
    }
    /**Присвоим область проекта.*/
    void setPOblast(String pOblast){
    this.pOblast = pOblast;
    }
    /**Присвоим авт ора проекта.*/
    void setPAvtor(String pAvtor){
    this.pAvtor = pAvtor;
    }
    /**Присвоим путь к папке проекта.*/
    void setPPath(String pPath){
        this.pPath = pPath;
    }
    /**Присвоим массив отношений таксономии проекта.*/
    void setMyTaksArray (MyTaksArray myTaksArray){
    this.myTaksArray=myTaksArray;
    }
    /**Присвоим массив отношений композиций проекта.*/
    void setMyComposArray (MyComposArray myComposArray){
    this.myComposArray=myComposArray;
    }
    /**Присвоим массив отношений Топологии проекта.*/
    void setMyStateArray (MyStateArray myStsteArray){
    this.myStateArray=myStateArray;
    }
    
    
    /**Развернуть все свернутые элементы с каждой диаграммы.
     * Собрать элементы с экрана каждой диаграммы,
     * выбрать файл для сохраненияи и сохранить.
     
     * Соберем эллипсы с экрана в массив отношений OntoTaks.
     * @param savePatch
     * @return 
     */
    public boolean saveProjectToFile(String savePatch){
        
        //если есть свернутые деревья, развернем их
        
        //if(getMyTaksArray().findPaintEllDetail(event.getPoint()).getDetail().setOpen(false);//изменим рисунок индикатора Скрыть/Отобразить на Плюсик.){}
        boolean nonStop=true;
        while(nonStop){
            nonStop=false;
            for(MyEll myEll:this.myTaksArray.getPaintTaksEll()){//Смотрим каждый эллипс
                if(!myEll.getDetail().isOpen()){//Если флаг развернутости списка false
                    myEll.getDetail().setOpen(true);//установим флаг развернутости
                    this.myTaksArray.makeHideTreeOff(myEll);//Развернем скрытый список выделенного эллипса
                    nonStop=true;//Разрешим смотреть отображенные на экране эллипсы сначала еще раз.
                    break;//вернемся к началу просмотра.
                }
            }
        }
        
        //если индикатор
        
        
        //String text = "This new text \nThis new text2\nThis new text3\nThis new text4\n";
        StringBuilder sb = new StringBuilder();//для сохраняемых в файл отношений.
        //соберем в текст отношения диаграммы Таксономии.
        for(MyLine line: this.myTaksArray.getMyLineArray().getLineArray()){
            for(int i=line.getPriznakUrlAr().size(); i>0;i--){//для подсчета количества линий выбранного отношения
                sb.append("Таксономии");
                sb.append("|");
                sb.append(line.getText2());
                sb.append("|");
                sb.append(line.getTx2());
                sb.append("|");
                sb.append(line.getTy2());
                sb.append("|");
                sb.append(line.getText1());
                sb.append("|");
                sb.append(line.getTx1());
                sb.append("|");
                sb.append(line.getTy1());
                sb.append("\n");
            }
        }
        //соберем в текст отношения диаграммы Композиции.
        for(MyLine line: this.myComposArray.getMyLineArray().getLineArray()){
            for(int i=line.getPriznakUrlAr().size(); i>0;i--){//для подсчета количества линий выбранного отношения
                sb.append("Композиции");
                sb.append("|");
                sb.append(line.getText2());
                sb.append("|");
                sb.append(line.getTx2());
                sb.append("|");
                sb.append(line.getTy2());
                sb.append("|");
                sb.append(line.getText1());
                sb.append("|");
                sb.append(line.getTx1());
                sb.append("|");
                sb.append(line.getTy1());
                sb.append("\n");
            }
        }
        //соберем в текст отношения диаграммы Состояния.
        for(MyLine line: this.myStateArray.getMyLineArray().getLineArray()){
            //for(int i=line.getPriznakUrlAr().size(); i>=0;i--){//здесь учет по признакам не ведется, поэтому считаем просто линии
                sb.append("Состояния");
                sb.append("|");
                sb.append(line.getText2());
                sb.append("|");
                sb.append(line.getTx2());
                sb.append("|");
                sb.append(line.getTy2());
                sb.append("|");
                sb.append(line.getText1());
                sb.append("|");
                sb.append(line.getTx1());
                sb.append("|");
                sb.append(line.getTy1());
                sb.append("|");
                sb.append(line.getMyEllRectStateText());//текст процесса.действия класса линия
                sb.append("|");
                sb.append(line.getTimeDate_MyEllRectState().getMyData()[6][1]);//текст начальной точки времени в миллисекундах.
                sb.append("|");
                sb.append(line.getTimeDate_MyEllRectState().getMyData()[7][1]);//текст конечной точки времени в миллисекундах.
                sb.append("|");
                sb.append(line.getTimeDate_MyEllRectState().getMyData()[8][1]);//текст длительности/интервала времени в миллисекундах.
                sb.append("\n");
            //}
        }
        String fileName = savePatch+"_"+this.getPName()+"_"+this.getPOblast()+"_"+this.getPAvtor()+".i5";
        //Запись в файл
        write(fileName, sb.toString());
        
        return true;
        
    }
    
    /***/
    public static void write(String fileName, String text) {
    //Определяем файл
    File file = new File(fileName);
 
    try {
        //проверяем, что если файл не существует то создаем его
        if(!file.exists()){
            file.createNewFile();
        }
 
        // Записываем строку в текстовый файл в кодировке Cp866
        PrintWriter out = new PrintWriter   // класс с методами записи строк
            (new OutputStreamWriter          // класс-преобразователь
            (new FileOutputStream         // класс записи байтов в файл
            (file), "Utf8"));

            //pw.println(string);  // записываем строку в файл

            //pw.close();
        
        //PrintWriter обеспечит возможности записи в файл
        //PrintWriter out = new PrintWriter(file.getAbsoluteFile());
 
        try {
            //Записываем текст у файл
            out.print(text);
            
        } finally {
            //После чего мы должны закрыть файл
            //Иначе файл не запишется
            out.close();
        }
    } catch(IOException e) {
        throw new RuntimeException(e);
    }
}
    /**Прочитаем в переменную содержимое файла
     * @param fileName
     * @throws java.io.FileNotFoundException*/
    public static String read(String fileName) throws FileNotFoundException {
    //Этот спец. объект для построения строки
    StringBuilder sb = new StringBuilder();
 
    exists(fileName);
 
    try {
        //Объект для чтения файла в буфер
        BufferedReader in = new BufferedReader(new FileReader( new File(fileName).getAbsoluteFile()));
        try {
            //В цикле построчно считываем файл
            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
        } finally {
            //Также не забываем закрыть файл
            in.close();
        }
    } catch(IOException e) {
        throw new RuntimeException(e);
    }
 
    //Возвращаем полученный текст с файла
    return sb.toString();
}

    /***/
    private static void exists(String fileName) throws FileNotFoundException {
    File file = new File(fileName);
    if (!file.exists()){
        throw new FileNotFoundException(file.getName());
    }
}
    /**
     * выделим слова из строки файла.
     * @param line строка из файла.
     * @return массив значений, выделенных из строки файла.
     */
    private static String[] getSplitText(String line) {
        String out[]=new String[7];
        out[0] = line.substring(0, line.indexOf("|"));
        String strProperties = line.substring(line.indexOf("|")+1);
        out[1] = strProperties.substring(0, strProperties.indexOf("|"));
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        out[2]= strProperties.substring(0, strProperties.indexOf("|"));
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        out[3]= strProperties.substring(0, strProperties.indexOf("|"));
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        out[4]= strProperties.substring(0, strProperties.indexOf("|"));
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        out[5]= strProperties.substring(0, strProperties.indexOf("|"));
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        out[6]= strProperties;
        
        return out;
    }
    /**
     * разбивает строку на поля Класса и заполняет их.
     * @param line исходное строка
     * @return отношение.
     */
    private OntoTaks getOntoTaksCSV(String line, double t2x, double t2y, double t1x, double t1y) {
        String shape;
        String text2;
        String text1;
        shape = "Таксономии";
        String strProperties= "";
        if(line.substring(0,1).equals(";")){//чтоб пропустить первый сымвол - знак ;. Чтоб юзер не заморачивался при импорте из чужих программ.
            strProperties = line.substring(line.indexOf(";")+1);
        }else{
            strProperties = line;
        }
        text2 = strProperties.substring(0, strProperties.indexOf(";"));
        strProperties = strProperties.substring(strProperties.indexOf(";")+1);
        text1 = strProperties;
        //System.out.println(" text2.length()= "+text2.length());
        String text2new=text2;
        //если первый символ строки не является буквой или цифрой, пропустить его.
        if(!this.compareText(text2.substring(0,1))){
            //System.out.println(" text2.substring(0)= "+text2.substring(0));
            text2new= text2.substring(1, text2.length());
            //System.out.println("text2= "+text2);
            //System.out.println("text2new= "+text2new);
        }
        //Создадим отношение
        OntoTaks ontoTaks= new OntoTaks(new MyEll(t2x,t2y,text2new),new MyEll(t1x,t1y,text1),null,null,shape);
        
        return ontoTaks;
    }
    /**
     * разбиваес строку на поля Класса и заполняет их.
     * @param line исходное строка
     * @return отношение.
     */
    private static OntoTaks getOntoTaks(String line) {
        String shape;
        String text2;
        String t2xs;
        String t2ys;
        String text1;
        String t1xs;
        String t1ys;
        shape = line.substring(0, line.indexOf("|"));
        String strProperties = line.substring(line.indexOf("|")+1);
        text2 = strProperties.substring(0, strProperties.indexOf("|"));
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        t2xs= strProperties.substring(0, strProperties.indexOf("|"));
        Double t2x = Double.valueOf(t2xs);
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        t2ys= strProperties.substring(0, strProperties.indexOf("|"));
        Double t2y = Double.valueOf(t2ys);
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        text1= strProperties.substring(0, strProperties.indexOf("|"));
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        t1xs= strProperties.substring(0, strProperties.indexOf("|"));
        Double t1x = Double.valueOf(t1xs);
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        t1ys= strProperties;
        Double t1y = Double.valueOf(t1ys);
        
        OntoTaks ontoTaks= new OntoTaks(new MyEll(t2x,t2y,text2),new MyEll(t1x,t1y,text1),null,null,shape);
        
        return ontoTaks;
    }
    /**
     * разбиваес строку на поля Класса и заполняет их.
     * @param line исходное строка
     * @return отношение.
     */
    private static OntoCompos getOntoCompos(String line) {
        String shape;
        String text2;
        String t2xs;
        String t2ys;
        String text1;
        String t1xs;
        String t1ys;
        shape = line.substring(0, line.indexOf("|"));
        String strProperties = line.substring(line.indexOf("|")+1);
        text2 = strProperties.substring(0, strProperties.indexOf("|"));
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        t2xs= strProperties.substring(0, strProperties.indexOf("|"));
        Double t2x = Double.valueOf(t2xs);
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        t2ys= strProperties.substring(0, strProperties.indexOf("|"));
        Double t2y = Double.valueOf(t2ys);
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        text1= strProperties.substring(0, strProperties.indexOf("|"));
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        t1xs= strProperties.substring(0, strProperties.indexOf("|"));
        Double t1x = Double.valueOf(t1xs);
        strProperties = strProperties.substring(strProperties.indexOf("|")+1);
        t1ys= strProperties;
        Double t1y = Double.valueOf(t1ys);
        
        OntoCompos ontoCompos= new OntoCompos(new MyEll(t2x,t2y,text2),new MyEll(t1x,t1y,text1),null,null,shape);
        
        return ontoCompos;
    }
    /**
     * разбиваес строку на поля Класса и заполняет их.
     * @param line исходное строка
     * @return отношение.
     */
    private static OntoState getOntoState(String line) {
        String shape;//название типа отношения
        String text2;
        String t2xs;
        String t2ys;
        String text1;
        String t1xs;
        String t1ys;
        String priznak;
        String start; //для датыВремени начала процесса-действия
        String end;//для датыВремени окончания процесса-действия
        String interval;//для интервала/продолжительности процесса-действия
        shape = line.substring(0, line.indexOf("|"));//берем первое поле из строки
            String strProperties = line.substring(line.indexOf("|")+1);//убираем взятое из строки
        text2 = strProperties.substring(0, strProperties.indexOf("|"));//берем текст состояния объекта 107
            strProperties = strProperties.substring(strProperties.indexOf("|")+1);//убираем взятое из строки
        t2xs= strProperties.substring(0, strProperties.indexOf("|"));//берем координату х объекта состояния 107
            Double t2x = Double.valueOf(t2xs);//конвертируем х-координату в нужный тип.
            strProperties = strProperties.substring(strProperties.indexOf("|")+1);//убираем взятое из строки
        t2ys= strProperties.substring(0, strProperties.indexOf("|"));//берем координату у объекта
            Double t2y = Double.valueOf(t2ys);
            strProperties = strProperties.substring(strProperties.indexOf("|")+1);//убираем взятое из строки
        text1= strProperties.substring(0, strProperties.indexOf("|"));//берем текст состояния объекта
            strProperties = strProperties.substring(strProperties.indexOf("|")+1);//убираем взятое из строки
        t1xs= strProperties.substring(0, strProperties.indexOf("|"));
            Double t1x = Double.valueOf(t1xs);
            strProperties = strProperties.substring(strProperties.indexOf("|")+1);//убираем взятое из строки
        t1ys= strProperties.substring(0, strProperties.indexOf("|"));
            Double t1y = Double.valueOf(t1ys);
            strProperties = strProperties.substring(strProperties.indexOf("|")+1);//убираем взятое из строки
        priznak= strProperties.substring(0, strProperties.indexOf("|"));//берем текст Действия или Процесса
            strProperties = strProperties.substring(strProperties.indexOf("|")+1);//убираем взятое из строки
        start= strProperties.substring(0, strProperties.indexOf("|"));//берем датуВремя начала Действия или Процесса
            strProperties = strProperties.substring(strProperties.indexOf("|")+1);//убираем взятое из строки
        end= strProperties.substring(0, strProperties.indexOf("|"));//берем датуВремя окончания Действия или Процесса
            strProperties = strProperties.substring(strProperties.indexOf("|")+1);//убираем взятое из строки
        interval= strProperties;//берем датуВремя интервала Действия или Процесса
            
        
        
        
        OntoState ontoState= new OntoState(new MyEll(t2x,t2y,text2),new MyEll(t1x,t1y,text1),priznak,null,shape,start,end,interval);
        
        return ontoState;
    }
    /**Загрузим сохраненные в файле данные проекта и нарисуем их на экране
     * @param file.*/
    public void importFromFile(File file){
        //Выделим из имени файла информацию про название Проекта, предметную область, автора
        String temp = file.toString().substring(0, file.toString().lastIndexOf("."));//отбросим расширение
        //выделим автора, как часть имени в его конце от "." до первого "_"
        String temp1 = temp.substring( temp.lastIndexOf("_")+1,temp.length());//возьмем имя автора
        setPAvtor(temp1);
        //уменьшим длину имени на длину автора
        temp = temp.substring(0, temp.lastIndexOf("_"));//отбросим автора
        //выделим название предметной области
        String temp2 = temp.substring( temp.lastIndexOf("_")+1,temp.length());
        setPOblast(temp2);
        //уменьшим длину имени на длину предметной области
        temp = temp.substring(0, temp.lastIndexOf("_"));//отбросим предметную область
        //выделим название проекта
        String temp3 = temp.substring( temp.lastIndexOf("_")+1,temp.length());
        setPName(temp3);
        setPNameFull();
        //уменьшим длину имени на длину названия проекта
        temp = temp.substring(0, temp.lastIndexOf("_"));//отбросим предметную область
        //выделим путь проекта
        setPPath(temp);
        List<String> DICTIONARY = null;
        try {
            //DICTIONARY = Files.readAllLines(Paths.get("d://a.txt"), Charset.forName("utf-8"));
            DICTIONARY = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.forName("utf-8"));
            
        } catch (IOException ex) {
            Logger.getLogger(MyJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (ListIterator<String> it = DICTIONARY.listIterator(); it.hasNext();) {
                String line = it.next();
                if (!line.equals(" ")) {
                    //String lineParts[] = getOnto(line), word = lineParts[0], part = lineParts[1];
                    String splitLine[];
                    splitLine = getSplitText(line);
                    if(splitLine[0].equals("Таксономии")){
                        this.myTaksArray.addPaintOntoTaks(getOntoTaks(line));
                    }
                    if(splitLine[0].equals("Композиции")){
                        this.myComposArray.addPaintOnto(getOntoCompos(line));
                    }
                    if(splitLine[0].equals("Состояния")){
                        this.myStateArray.addPaintOnto(getOntoState(line));
                    }
                    
                    //System.out.println();
                }
        }
        //..Files.
    }
    /**Импорт списка из файла csv для формата win1251.
     Становится доступным только только для загруженного проекта. Импорт начинается в самой нижней левой точке окна с расширением вправо и вниз.
     Возвращает количество импортированных строк
     Импорт выполняется только для вкладки Таксономии Проекта
     * @param fileCSV
     * @return .*/
    public int importFromCSV (File fileCSV){
        //Список строк для добавления
        List<String> DICTIONARY = null;
        //Список добавленных отношений
        ArrayList<OntoTaks> ontoTaksAddings= new ArrayList<OntoTaks>();
        try {
            //DICTIONARY = Files.readAllLines(Paths.get("d://a.txt"), Charset.forName("utf-8"));
            DICTIONARY = Files.readAllLines(Paths.get(fileCSV.getAbsolutePath()), Charset.forName("cp1251"));
            
        } catch (IOException ex) {
            System.out.println("Ошибка чтения файла "+ex);
            Logger.getLogger(MyJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        int count=0;
        //расчитаем координаты для первого отношения
        //Найдем самый нижний объект из присутствующих в окне таксономии.
        Double t2y=0D;
        for(MyEll myEll: this.myTaksArray.getPaintTaksEll()){
            if(myEll.getY()>t2y){t2y=myEll.getY();}
        }
        //Зададим координаты объекта класса из нового отношения ниже и как можно левее.
        Double t2x=5D;
        //t2y=t2y;
        Double t1x=t2x+200;
        Double t1y=t2y;
        for (ListIterator<String> it = DICTIONARY.listIterator(); it.hasNext();) {
                String line = it.next();
                if (!line.equals(" ")) {
                    if(line.length()>1){
                    //String lineParts[] = getOnto(line), word = lineParts[0], part = lineParts[1];
                    //String splitLine[];
                    //splitLine = getSplitText(line);
                    //Расчитаем место экрана для отображения отношения.
                    //t2x=t2x;
                    t2y=t2y+30;
                    //t1x=t1x;
                    t1y=t2y+30;
                    OntoTaks ontoTaks= getOntoTaksCSV(line,t2x,t2y,t1x,t1y);
                    this.myTaksArray.addPaintOntoTaks(ontoTaks);
                    ontoTaksAddings.add(ontoTaks);
                    //System.out.println();
                    count++;
                    }
                }
                //System.out.println("1  "+this.compareText(line));
                
        }
        //надо пересоздать линии всех добавленных объектов используя сохраненные тексты начала и конца линии
        //for(OntoTaks ontoTaks1: ontoTaksAddings){
            //пересоздадим линии добавленных на экран эллипсов
        //    getMyTaksArray().renewEllLine(ontoTaks1.getEllKlass());
        //    getMyTaksArray().renewEllLine(ontoTaks1.getEllElem());
        //}
        this.getMyTaksArray().setResizeWindow(true);
        return count;
    }
    public void setMyProject(MyProject myProjectOld){
        
        this.myComposArray=myProjectOld.getMyComposArray();
        this.myStateArray=myProjectOld.getMyStateArray();
        this.myTaksArray=myProjectOld.getMyTaksArray();
        this.namebd=myProjectOld.getNamebd();
        this.pAvtor=myProjectOld.pAvtor;
        this.pName=myProjectOld.pName;
        this.pNameFull=myProjectOld.pNameFull;
        this.pOblast=myProjectOld.pOblast;
        this.pPath=myProjectOld.pPath;
        this.pFileName=myProjectOld.pFileName;
    
    }

    public String getpFileName() {
        return pFileName;
    }

    public void setpFileName(String pFileName) {
        this.pFileName = pFileName;
    }
    
    /***/
    private boolean compareText(String text){
        //В имени проекта использованы только русские буквы и арабские цифры.
            //public static boolean checkWithRegExp(String userNameString){  
            Pattern p = Pattern.compile("^[А-Яа-яA-z0-9]*$");  
            Matcher m = p.matcher(text); 
            //String text1 = text.replace("[^А-яa-zA-Z0-9]", "");
            //System.out.println(" "+text1);
            //return m.matches();  
            //}
            if(!m.matches()){
                return false;
            }
            return true;
    }

    

    public String getMyEnabledArray() {
        return myEnabledArray;
    }

    public void setMyEnabledArray(String myEnabledArray) {
        this.myEnabledArray = myEnabledArray;
    }

    public boolean isSaving() {
        return saving;
    }

    public void setSaving(boolean isSaving) {
        this.saving = isSaving;
    }
    
    /**Развернуть все свернутые элементы Таксономии.
     * Соберем эллипсы с экрана в массив отношений OntoTaks.
     * Выполним экспорт в файл.
     * @param saveFile
     * @return 
     * @throws java.io.FileNotFoundException 
     */
    public boolean exportProjectToCSV(File saveFile) throws FileNotFoundException, IOException{
        boolean nonStop=true;
        while(nonStop){
            nonStop=false;
            for(MyEll myEll:this.myTaksArray.getPaintTaksEll()){//Смотрим каждый эллипс
                if(!myEll.getDetail().isOpen()){//Если флаг развернутости списка false
                    myEll.getDetail().setOpen(true);//установим флаг развернутости
                    this.myTaksArray.makeHideTreeOff(myEll);//Развернем скрытый список выделенного эллипса
                    nonStop=true;//Разрешим смотреть отображенные на экране эллипсы сначала еще раз.
                    break;//вернемся к началу просмотра.
                }
            }
        }
        //String fileName = saveFile.getAbsolutePath()+"_"+this.getPName()+"_"+this.getPOblast()+"_"+this.getPAvtor()+".csv";
        
       
        
        String sb="";//для сохраняемых в файл отношений.
        //соберем в текст отношения диаграммы Таксономии.
        for(MyLine line: this.myTaksArray.getMyLineArray().getLineArray()){
            for(int i=line.getPriznakUrlAr().size(); i>0;i--){
                sb= sb+";"+line.getText2()+";"+line.getText1()+"\n";
                
            }
        }
        //Запись в файл
        //write(fileName, sb.toString());
        
         try (OutputStream m = new FileOutputStream(saveFile.getAbsolutePath())) {
             
             
            m.write(sb.getBytes("Cp1251"));
            //m.close();
        }
        return true;
        
    }
     
}
