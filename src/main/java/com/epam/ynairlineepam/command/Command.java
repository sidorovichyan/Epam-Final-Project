package com.epam.ynairlineepam.command;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
