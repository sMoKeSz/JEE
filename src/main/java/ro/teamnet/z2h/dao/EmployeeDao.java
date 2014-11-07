package ro.teamnet.z2h.dao;

import ro.teamnet.z2h.domain.Employee;
import ro.teamnet.z2h.utils.ResultSetToPojoConverter;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by liviu.spiroiu on 11/3/14.
 */
public class EmployeeDao {

    public List<Employee> getAllEmployees(){
        List<Employee> ret=new ArrayList<Employee>();
        for(int i=0;i<50;i++){
            Employee employee=new Employee();
            employee.setId((long)i+1);
            employee.setFirstName("First Name"+i);
            employee.setLastName("Last Name"+i);
            employee.setSalary(2000d);
            employee.setPhoneNumber("02122334455");
            employee.setHireDate(new Date());
            employee.setEmail("email_"+i+"@z2h.ro");
            employee.setCommissionPoints(2004d);
            ret.add(employee);
        }

        return ret;
    }

    public Employee getById(Long id){
            Employee employee=new Employee();
            employee.setId((long)id+1);
            employee.setFirstName("First Name"+id);
            employee.setLastName("Last Name"+id);
            employee.setSalary(2000d);
            employee.setPhoneNumber("02122334455");
            employee.setHireDate(new Date());
            employee.setEmail("email_"+id+"@z2h.ro");
            employee.setCommissionPoints(2004d);

        return employee;
    }

    public ArrayList<Employee> getAllEmployees(Connection con) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Employee> employees = new ArrayList<Employee>();
        String selectAllFromTableString = "SELECT employee_id,first_name,last_name,salary,email,hire_date,phone_number,commission_pct FROM Employees";
        try {

            stmt = con.prepareStatement(selectAllFromTableString);

            rs = stmt.executeQuery(selectAllFromTableString);

            while (rs.next()) {
                Employee employee = new Employee();


                employee.setId(rs.getLong("employee_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("phone_number"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setCommissionPoints(rs.getDouble("commission_pct"));

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(stmt != null)
                    stmt.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return employees;
    }

    public Employee getEmployeeById(Connection con, Long id){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Employee> employees = new ArrayList<Employee>();

        String selectAllFromTableString = "SELECT employee_id,first_name,last_name,salary,email,hire_date,phone_number,commission_pct FROM Employees" +
                                          " WHERE employee_id = " + id;
        try {

            stmt = con.prepareStatement(selectAllFromTableString);

            rs = stmt.executeQuery(selectAllFromTableString);

            while (rs.next()) {
                Employee employee = new Employee();


                employee.setId(rs.getLong("employee_id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("phone_number"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setCommissionPoints(rs.getDouble("commission_pct"));

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(stmt != null)
                    stmt.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return employees.size() > 0 ? employees.get(0) : null;
    }
	
	public Connection getConnection(String username, String password) {
        try {
            Driver myDriver = new oracle.jdbc.driver.OracleDriver();
            DriverManager.registerDriver( myDriver );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Connection con = null;
        try {
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@demo.teamnet.ro:1521:orcl",
                    username,
                    password);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
    public void saveEmployee(Employee employee, Connection con) {
        LinkedHashMap<String, String> insertIntoTableEmployees = new LinkedHashMap<String, String>();
        String tableName = "employees";
        insertIntoTableEmployees.put("employee_id", employee.getId().toString());
        insertIntoTableEmployees.put("first_name", employee.getFirstName());
        insertIntoTableEmployees.put("last_name", employee.getLastName());
        insertIntoTableEmployees.put("email", employee.getEmail());
        insertIntoTableEmployees.put("phone_number", employee.getPhoneNumber());
        //insertIntoTableEmployees.put("hire_date", "TO_DATE('" + employee.getHireDate().toString() + "','yyyy-mm-dd')");
        insertIntoTableEmployees.put("hire_date", "TO_DATE('" + "1999-12-10" + "','yyyy-mm-dd')");
        insertIntoTableEmployees.put("job_id", employee.getJob().getId().toString());
        insertIntoTableEmployees.put("salary", employee.getSalary().toString());
        insertIntoTableEmployees.put("commission_pct", employee.getCommissionPoints().toString());
        insertIntoTableEmployees.put("manager_id", employee.getManager().getId().toString());
        insertIntoTableEmployees.put("department_id", employee.getDepartment().getId().toString());
        PreparedStatement stmt=null;
        try {
            //
            String createTableString = "INSERT INTO " + tableName + " ( ";
            StringBuilder sqlStatement = new StringBuilder();
            sqlStatement.append(createTableString);
            Integer valuesCount = insertIntoTableEmployees.keySet().size();
            for (String valueName : insertIntoTableEmployees.keySet()) {
                valuesCount--;
                String columnString = valueName + (valuesCount != 0 ? " , " : ")");
                sqlStatement.append(columnString);
            }
            valuesCount = insertIntoTableEmployees.keySet().size();
            sqlStatement.append(" VALUES ( '");
            for (String valueName : insertIntoTableEmployees.keySet()) {
                valuesCount--;

                String columnString;
                if (valueName.equals("hire_date")) {
                    columnString = insertIntoTableEmployees.get(valueName) + (valuesCount != 0 ? " , '" : "')");
                }else if (valueName.equals("phone_number")) {
                    columnString = insertIntoTableEmployees.get(valueName) + (valuesCount != 0 ? "' , " : "')");
                }else
                {
                    columnString = insertIntoTableEmployees.get(valueName) + (valuesCount != 0 ? "' , '" : "')");
                }
                sqlStatement.append(columnString);
            }
            System.out.println(sqlStatement);
            stmt = con.prepareStatement(sqlStatement.toString());
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Inserted into table " + tableName + "...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateEmployee(Employee employee, Connection con) {
        HashMap<String, String> insertIntoTableEmployees = new HashMap<String, String>();
        insertIntoTableEmployees.put("employee_id", employee.getId().toString());
        insertIntoTableEmployees.put("first_name", employee.getFirstName());
        insertIntoTableEmployees.put("last_name", employee.getLastName());
        insertIntoTableEmployees.put("email", employee.getEmail());
        insertIntoTableEmployees.put("phone_number", employee.getPhoneNumber());
        //insertIntoTableEmployees.put("hire_date", "TO_DATE('" + employee.getHireDate().toString() + "','yyyy-mm-dd')");
        insertIntoTableEmployees.put("hire_date", "TO_DATE('" + "1999-12-10" + "','yyyy-mm-dd')");
        insertIntoTableEmployees.put("job_id", employee.getJob().getId().toString());
        insertIntoTableEmployees.put("salary", employee.getSalary().toString());
        insertIntoTableEmployees.put("commission_pct", employee.getCommissionPoints().toString());
        insertIntoTableEmployees.put("manager_id", employee.getManager().getId().toString());
        insertIntoTableEmployees.put("department_id", employee.getDepartment().getId().toString());
        String tableName = "employees";
        PreparedStatement stmt;
        try {

            String createTableString = "UPDATE " + tableName + " SET ";
            StringBuilder sqlStatement = new StringBuilder();
            sqlStatement.append(createTableString);
            Integer columnsCount = insertIntoTableEmployees.keySet().size();
            for (String columnName : insertIntoTableEmployees.keySet()) {
                columnsCount--;
                String columnString;
                if (columnName.equals("hire_date")) {
                    columnString = columnName + " = " + insertIntoTableEmployees.get(columnName) + (columnsCount != 0 ? " , " : "' ");
                } else {
                    columnString = columnName + " = '" + insertIntoTableEmployees.get(columnName) + (columnsCount != 0 ? "' , " : "' ");
                }
                sqlStatement.append(columnString);
            }
            sqlStatement.append("WHERE employee_id = " + employee.getId());

            stmt =con.prepareStatement(sqlStatement.toString());

            stmt.executeUpdate();
            stmt.close();

            System.out.println("Created table " + tableName + " in database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}
