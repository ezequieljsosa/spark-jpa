package dds;

import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


public class Server {

    static EntityManagerFactory entityManagerFactory;


    public static void main(String[] args) {
        enableDebugScreen();
        port(4567);

        initRoutes();
        initPersistance();

    }

    private static void initPersistance() {
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
    }

    public static void initRoutes() {

        boolean localhost = true;
        if (localhost) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/static/";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFiles.location("/public");
        }

        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

        get("/auto", AutoController::listaAutos, engine);
        post("/auto", RouteWithTransaction(AutoController::guardarAuto));
        delete("/auto/:patente", RouteWithTransaction(AutoController::borrarAuto));
        get("/auto/:patente", TemplWithTransaction(AutoController::obtenerAuto), engine);
        post("/auto/:patente", RouteWithTransaction(AutoController::guardarAuto));
    }

    private static TemplateViewRoute  TemplWithTransaction(WithTransaction<ModelAndView> fn) {
        TemplateViewRoute r = (req, res) -> {
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            try {
                ModelAndView result = fn.method(req, res, em);
                em.getTransaction().commit();
                return result;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                throw ex;
            } finally {
                em.close();
            }
        };
        return r;
    }
    private static Route RouteWithTransaction(WithTransaction<Object> fn) {
        Route r = (req, res) -> {
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            try {
                Object result = fn.method(req, res, em);
                em.getTransaction().commit();
                return result;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                throw ex;
            } finally {
                em.close();
            }
        };
        return r;
    }




}
