package com.batook.orcl;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Test {
    public static void main(String[] args) throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:batook/gfhjdjp@192.168.1.20:1521/xe", "batook", "gfhjdjp");
        Statement st = con.createStatement();
        st.execute("create or replace type TYPE_1 is table of varchar2(400)");
        ArrayDescriptor arrayDesc = ArrayDescriptor.createDescriptor("TYPE_1", con);
        System.out.println(arrayDesc.descType());
    }
}

class CallableStructArray {

    private static final String username = "batook";
    private static final String password = "gfhjdjp";
    private static final String URL = "jdbc:oracle:thin:batook/gfhjdjp@192.168.1.20:1521/xe";

    public static void main(String[] args) throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        // Коннект к Oracle.
        Connection con = DriverManager.getConnection(URL, username, password);
        // Определяем два дескриптора, для ARRAY TYPE и для OBJECT TYPE.
        StructDescriptor structDesc = StructDescriptor.createDescriptor("RECTYPE", con);
        ArrayDescriptor arrayDesc = ArrayDescriptor.createDescriptor("RECTAB", con);

        // Инициализация коллекции для объектов STRUCT.
        ArrayList arrayList = new ArrayList();

        // Объявление массива для значений.
        // Размерность его равна количеству атрибутов в объектном типе RECTYPE,
        // присвоение значений будет идти в порядке объявления в объектном типе.
        Object[] record = new Object[3];
        // Определение значений и создание объекта STRUCT.
        record[0] = 1;
        record[1] = "Первый";
        record[2] = new Timestamp(Calendar.getInstance().getTimeInMillis());
        arrayList.add(new STRUCT(structDesc, con, record));

        record[0] = 2;
        record[1] = "Второй";
        record[2] = new Timestamp(Calendar.getInstance().getTimeInMillis());
        arrayList.add(new STRUCT(structDesc, con, record));

        // Создание объекта ARRAY.
        ARRAY array = new ARRAY(arrayDesc, con, arrayList.toArray());
        // Объявление callable statement.
        // Он должен быть объявлен как OracleCallableStatement.
        OracleCallableStatement cst = (OracleCallableStatement) con
                .prepareCall("{call ioStructArray.testproc(iorec=>?, orec=>?)}");
        // Определение первого параметра процедуры
        cst.setARRAY(1, array);
        // Первый параметр имеет тип inout, поэтому дополнительно он регистрируется как out.
        cst.registerOutParameter(1, OracleTypes.ARRAY, "RECTAB");
        // Регистрация второго out-параметра процедуры
        cst.registerOutParameter(2, OracleTypes.ARRAY, "RECTAB");
        // Выполнение процедуры
        cst.execute();

        // Ассоциация возвращаемого массива с объектом ARRAY.
        array = cst.getARRAY(1);

        // Получение данных первого параметра.
        Object[] objects = (Object[]) array.getArray();
        System.out.println("\nПервый объект:");
        for (Object object : objects) {
            Object[] rec = ((STRUCT) object).getAttributes();
            System.out.println(rec[0] + " | " + rec[1] + " | " + rec[2]);
        }

        // Получение данных второго параметра.
        array = cst.getARRAY(2);
        objects = (Object[]) array.getArray();
        System.out.println("\nВторой объект:");
        for (Object obj : objects) {
            record = ((STRUCT) obj).getAttributes();
            System.out.println(record[0] + " | " + record[1] + " | " + record[2]);
        }

        // Альтернативный метод получения параметра в случае возни с кодировками
        System.out.println("\nВторой объект через Datum:");
        Datum[] datum = array.getOracleArray();
        for (Datum aDatum : datum) {
            Datum[] rec = ((STRUCT) aDatum).getOracleAttributes();
            System.out.println(rec[0].intValue() + " | " + rec[1].stringValue() + " | " + rec[2].timestampValue());
        }
    }
}
