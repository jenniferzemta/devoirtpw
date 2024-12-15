import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class CookieServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Lire les cookies existants
        Cookie[] cookies = request.getCookies();
        String username = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                }
            }
        }

        // Afficher un formulaire si l'utilisateur n'est pas connecté
        if (username == null) {
            out.println("<form action='CookieServlet' method='POST'>");
            out.println("Username: <input type='text' name='username'/>");
            out.println("<input type='submit' value='Login'/>");
            out.println("</form>");
        } else {
            out.println("<h1>Welcome back, " + username + "!</h1>");
            out.println("<form action='CookieServlet' method='POST'>");
            out.println("<input type='submit' value='Logout' name='logout'/>");
            out.println("</form>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        if (username != null) {
            // Créer un cookie pour le nom d'utilisateur
            Cookie userCookie = new Cookie("username", username);
            userCookie.setMaxAge(60 * 60 * 24); // 1 jour
            response.addCookie(userCookie);
        } else {
            // Supprimer le cookie si l'utilisateur se déconnecte
            Cookie cookie = new Cookie("username", null);
            cookie.setMaxAge(0); // Supprimer le cookie
            response.addCookie(cookie);
        }

        // Rediriger vers le GET pour afficher le message de bienvenue
        response.sendRedirect("CookieServlet");
    }
}