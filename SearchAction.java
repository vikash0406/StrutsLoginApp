package com.example;

import java.sql.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

public class SearchAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {

        SearchForm searchForm = (SearchForm) form;
        String city = searchForm.getCity();

        List<Employee> list = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();
            cs = con.prepareCall("{CALL selectByCity(?)}");
            cs.setString(1, city);
            rs = cs.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("employee_id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));
                emp.setCity(rs.getString("city"));
                list.add(emp);
            }

            request.setAttribute("empList", list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (cs != null) cs.close();
            if (con != null) con.close();
        }

        return mapping.findForward("result");
    }
}
