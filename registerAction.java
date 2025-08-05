package com.example;

import javax.servlet.http.*;
import java.sql.*;
import org.apache.struts.action.*;

public class RegisterAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        RegisterForm rf = (RegisterForm) form;

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO users (username, password, email, city) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rf.getUsername());
            ps.setString(2, rf.getPassword());
            ps.setString(3, rf.getEmail());
            ps.setString(4, rf.getCity());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                return mapping.findForward("success");
            }
        } catch (Exception e) {
            e.printStackTrace();  // shows error in Tomcat console
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            return mapping.findForward("failure");  // return failure to stay on register.jsp
        }

        return mapping.findForward("failure");
    }
}
