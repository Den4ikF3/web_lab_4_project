package edu.lab4.web;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.sql.SQLException;

final class Messages {

    private Messages() {
    }

    static void error(String text) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, text, null));
    }

    static void info(String text) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, text, null));
    }

    static void dbError(SQLException e) {
        String state = e.getSQLState();
        if ("23505".equals(state)) {
            error("Запис із такими даними вже існує (порушення унікальності)");
        } else if ("23503".equals(state)) {
            error("Порушення зв’язку між таблицями");
        } else if ("23514".equals(state)) {
            error("Значення не відповідає обмеженням таблиці");
        } else {
            error("Помилка бази даних: " + e.getMessage());
        }
    }
}
