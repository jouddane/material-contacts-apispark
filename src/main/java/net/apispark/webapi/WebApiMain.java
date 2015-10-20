package net.apispark.webapi;

import net.apispark.webapi.core.exception.BadParameterException;
import net.apispark.webapi.db.ContactPersistence;
import net.apispark.webapi.representation.Contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.restlet.Component;
import org.restlet.data.LocalReference;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.resource.Directory;
import org.restlet.routing.Redirector;
import org.restlet.routing.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class WebApiMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebApiMain.class);

    public static void main(String[] args) throws Exception {
        configureLog();
        
        preloadData();

        // Attach application to http://localhost:8000/
        Component c = new Component();
        c.getServers().add(Protocol.HTTP, 8000);
        c.getClients().add(Protocol.CLAP);

        // attach REST API to /api
        c.getDefaultHost().attach("/api", new WebApiApplication());

        // redirect / to /index.html
        c.getDefaultHost().attach(
                "/",
                new Redirector(c.getContext().createChildContext(), "/index.html", Redirector.MODE_CLIENT_PERMANENT),
                Template.MODE_EQUALS);
        // other routes are redirected to static resources
        c.getDefaultHost().attachDefault(
                new Directory(c.getContext().createChildContext(), LocalReference.createClapReference("/static")));

        // start server
        c.start();

        LOGGER.info("Server started on http://localhost:8000/");

    }

    private static void configureLog() {
        //Configure logging
        Engine.configureLog();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    private static void preloadData() throws BadParameterException, ParseException {
        ContactPersistence.INSTANCE.addContact(new Contact("41ee2e80-75bf-11e5-b476-cbcba715b961", "John", "Smith", "svg-1",new SimpleDateFormat("yyyy-MM-dd").parse("1991-06-18")));
        ContactPersistence.INSTANCE.addContact(new Contact("41ee5590-75bf-11e5-b476-cbcba715b961", "Brenda", "Jones", "svg-6",new SimpleDateFormat("yyyy-MM-dd").parse("1994-11-13") ));
    }
}