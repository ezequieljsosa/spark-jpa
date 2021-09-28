package dds;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.notFound;
import static spark.Spark.redirect;

public class AutoController {


    public static ModelAndView listaAutos(Request request, Response response,EntityManager entityManager) {

        //INIT
        RepoAuto repo = new RepoAuto(entityManager);

        //INPUT
        String marca = (request.queryParams("marca") != null) ? request.queryParams("marca") : "";

        //Domain
        List<Auto> autos = null;
        if (marca.isEmpty()) {
            autos = repo.all();
        } else {
            autos = repo.byMarca(marca);
        }

        //OUTPUT
        Map<String, Object> map = new HashMap<>();
        map.put("autos", autos);
        return new ModelAndView(map, "autos.html");
    }

    public static ModelAndView obtenerAuto(Request request, Response response,
                                           EntityManager entityManager) {

        //INIT
        RepoAuto repo = new RepoAuto(entityManager);

        //INPUT
        String patente = request.params("patente");

        //Domain
        Map<String, Object> map = new HashMap<>();
        try {
            Auto auto = repo.byPatente(patente);
            //OUTPUT

            map.put("auto", auto);

        } catch (AutoNotFoundException ex) {
            response.redirect("/auto");
        }


        return new ModelAndView(map, "auto.html");
    }

    public static Object guardarAuto(Request request, Response response,
                                     EntityManager entityManager) {
        //INIT
        RepoAuto repo = new RepoAuto(entityManager);

        //INPUT
        String patente = request.queryParams("patente");
        int anio = Integer.parseInt(request.queryParams("anio"));
        String modelo = request.queryParams("modelo");
        String marca = request.queryParams("marca");

        assert patente != null;

        //Dominio

        Auto auto = null;

        if (repo.existsPatente(patente)) {
            try {
                auto = repo.byPatente(patente);
                auto.setAnio(anio);
                auto.setModelo(modelo);
                auto.setMarca(marca);
                response.status(200);
            } catch (AutoNotFoundException e) {
                response.status(404);
            }

        } else {
            auto = new Auto(patente, modelo, marca, anio);
            repo.persist(auto);
            response.status(201);
        }

        //OUTPUT
        response.redirect("/auto");

        return null;
    }

    public static Object borrarAuto(Request request, Response response, EntityManager em) {

        //INIT
        RepoAuto repo = new RepoAuto(em);

        //INPUT
        String patente = request.params("patente");

        //Domain

        try {
            repo.borrarAuto(patente);
        } catch (AutoNotFoundException e) {
            response.status(404);
            return "el auto no existe";
        }
        response.status(200);
        return "Auto borrado correctamente";


    }

}
