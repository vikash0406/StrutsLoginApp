package com.example;

import java.sql.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

public class LoginAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {

        LoginForm loginForm = (LoginForm) form;
        String user = loginForm.getUsername();
        String pass = loginForm.getPassword();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT * FROM users WHERE username = ? AND password = ?")) {

            ps.setString(1, user);
            ps.setString(2, pass);

            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            	    return mapping.findForward("success");
            	} else {
            	    request.setAttribute("error", "Invalid username or password!");
            	    return mapping.findForward("failure");  // shows error on same page
            	}

            }
        }
    }
}
