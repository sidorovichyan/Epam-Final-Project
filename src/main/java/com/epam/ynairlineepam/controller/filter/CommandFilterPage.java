package com.epam.ynairlineepam.controller.filter;



import com.epam.ynairlineepam.command.Command;
import com.epam.ynairlineepam.command.CommandProvider;
import com.epam.ynairlineepam.command.exception.CommandInitializationException;
import com.epam.ynairlineepam.command.exception.CommandNotFoundException;
import com.epam.ynairlineepam.command.impl.user.AddNewUserCommand;
import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandFilterPage implements Filter {

    private final static Logger logger = Logger.getLogger(CommandFilterPage.class);

    private static final String COMMAND = "command";

    private static final Map<String, String> commands = new HashMap<>();

    private static final String XML_PATH = "/command/command.xml";

    private static final String WELCOME_PAGE = "WelcomePage";

    private static final int PAGE_NOT_FOUND_ERROR = 404;

    private static final String NAME = "name";
    private static final String METHOD = "method";
    private static final String CLASS = "class";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DOMParser parser = new DOMParser();
        try {
            parser.parse(new InputSource(this.getClass().getResourceAsStream(XML_PATH)));
            Document document = parser.getDocument();
            this.parseHandler(document.getDocumentElement());
        } catch(SAXException | IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e){
             logger.error(e);
            throw new CommandInitializationException("Can't initialize commands", e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String command = request.getParameter(COMMAND);

        if (command != null && request.getMethod().equals(commands.get(command))) {
            filterChain.doFilter(request, response);
        } else {
            try {
                CommandProvider.getInstance().getCommand(WELCOME_PAGE).execute(request, response);
            } catch (CommandNotFoundException e) {
                logger.error(e);
                response.sendError(PAGE_NOT_FOUND_ERROR);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private void parseHandler(Element root) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        CommandProvider manager = CommandProvider.getInstance();
        NodeList cmd = root.getElementsByTagName(COMMAND);
        for(int i = 0; i < cmd.getLength(); i++){
            Element element = (Element) cmd.item(i);
            String name = this.getSingleChildContent(element, NAME);
            String method = this.getSingleChildContent(element, METHOD);
            String className = this.getSingleChildContent(element, CLASS);
            commands.put(name, method);
            manager.addCommand(name, (Command) Class.forName(className).newInstance());
        }
    }

    private String getSingleChildContent(Element element, String childName){
        return element.getElementsByTagName(childName).item(0).getTextContent();
    }
}
