package com.batook.orcl;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SpringStoreProcTest {
    public static void main(String[] args) {
        EmployeeObj emp = new EmployeeObj("TEST_EMP_OBJ", 234, "hello");
        EmployeeObj emp1 = new EmployeeObj("TEST_EMP_OBJ", 235, "bye");
        Object[] employees = new Object[]{emp, emp1};
        EmpProc proc = new EmpProc(dataSource());
        proc.executeProc(employees);
    }

    public static DataSource dataSource() {
        Locale.setDefault(Locale.ENGLISH);
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:batook/gfhjdjp@192.168.1.20:1521/xe");
        ds.setUsername("batook");
        ds.setPassword("gfhjdjp");
        return ds;
    }
}

class EmpProc extends StoredProcedure {
    private static final String PROC = "TEST_EMP_OBJ_ARRAY_PROC";

    public EmpProc(DataSource dataSource) {
        super(dataSource, PROC);
        declareParameter(new SqlParameter("THE_ARRAY", Types.ARRAY, "TEST_EMP_OBJ_ARRAY"));
        compile();
    }

    public void executeProc(final Object[] employees) {

        Map inParams = new HashMap();
        inParams.put("THE_ARRAY", new SqlTypeValue() {
            public void setTypeValue(PreparedStatement cs, int index, int sqlType, String typeName) throws SQLException {
                Connection con = cs.getConnection();
                ArrayDescriptor des = ArrayDescriptor.createDescriptor("TEST_EMP_OBJ_ARRAY", con);
                ARRAY a = new ARRAY(des, con, employees);
                cs.setObject(1, (Object) a);
            }
        });
        Map out = execute(inParams);
    }
}

class EmployeeObj implements SQLData {
    public int empNo;
    public String empName;
    private String sql_type;

    public EmployeeObj(String sql_type, int empNo, String empName) {
        this.sql_type = sql_type;
        this.empNo = empNo;
        this.empName = empName;
    }

    ////// implements SQLData //////
    // define a get method to return the SQL type of the object
    public String getSQLTypeName() throws SQLException {
        return sql_type;
    }

    // define the required readSQL() method
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sql_type = typeName;
        empName = stream.readString();
        empNo = stream.readInt();
    }

    // define the required writeSQL() method
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(empNo);
        stream.writeString(empName);
    }
}