package servicios;

import com.appslandia.common.gson.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.*;
import dto.ExercisesDTO;
import entidades.*;
import spark.Spark;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ExercisesAPIREST {

    private ExercisesDAOInterface daoExercises;
    private UsersDAOInterface daoUsers;
    private ApiKeyDAOInterface daoKey;
    private AssociationsDAOInterface daoAssociations;
    private DietDAOInterface daoDiet;
    private TrainingDAOInterface daoTrainingRecords;
    // Adaptar gson para puder utilizar LocalDate
    private Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).excludeFieldsWithoutExposeAnnotation().create();
//.disableHtmlEscaping()
    public ExercisesAPIREST(ExercisesDAOInterface implementacion, UsersDAOInterface implementUsers, AssociationsDAOInterface implementAssociation, DietDAOInterface implementDiet,TrainingDAOInterface implementTR, ApiKeyDAOInterface implementKey){
        daoExercises = implementacion;
        daoUsers = implementUsers;
        daoKey = implementKey;
        daoAssociations = implementAssociation;
        daoDiet = implementDiet;
        daoTrainingRecords = implementTR;

        Spark.port(8080);
        Spark.before((request, response) -> {
            response.type("aplication/json");
        });

        // variables path
        String show = "/show/*";
        String create = "/create/*";
        String delete = "/delete/*";
        String modify = "/modify/*";
        // Endpoint protegido con token
        Spark.before(show,(request, response) -> {
            String apiKey = request.headers("APIKEY");
            if (apiKey == null){
                Spark.halt(401,"Unauthorized access");
            }
            if (!validarAPIKEY(apiKey,show)){
                Spark.halt(401,"Unauthorized access");
            }
        });

        // Endpoint protegido con token, para crear
        Spark.before(create,(request, response) -> {
            String apiKey = request.headers("APIKEY");

            if (apiKey == null){
                Spark.halt(401,"Unauthorized access");
            }
            if (!validarAPIKEY(apiKey,create)){
                Spark.halt(401,"Unauthorized access");
            }
        });

        // Endpoint protegido con token, para borrar
        Spark.before("/delete/*",(request, response) -> {
            String apiKey = request.headers("APIKEY");

            if (apiKey == null){
                Spark.halt(401,"Unauthorized access");
            }
            if (!validarAPIKEY(apiKey,delete)){
                Spark.halt(401,"Unauthorized access");
            }
        });

        // Endpoint protegido con token, para borrar
        Spark.before("/modify/*",(request, response) -> {
            String apiKey = request.headers("APIKEY");

            if (apiKey == null){
                Spark.halt(401,"Unauthorized access");
            }
            if (!validarAPIKEY(apiKey,modify)){
                Spark.halt(401,"Unauthorized access");
            }
        });

//==================================  Endpoints GET  ============================================

        // Endpoint para obtener todos los ejercicios
        Spark.get("/show/allexercises",(request, response) ->{
            List<Exercises> exercises = daoExercises.showAll();
            return gson.toJson(exercises);
        });

        // Endpoint para obtener todas las dietas
        Spark.get("show/alldiet",(request, response) -> {
            List<Diet> diets = daoDiet.showAll();
            return gson.toJson(diets);
        });

        // Endpoint para obtener todos los entrenamientos registrados
        Spark.get("show/alltrainingrecords",(request, response) -> {
            List<TrainingRecords> trainingRecords = daoTrainingRecords.showAll();
            return  gson.toJson(trainingRecords);
        });

        // Endpoint para obtener todas las keys
        Spark.get("/show/allkeys",(request, response) ->{
            List<ApiKeys> keys = daoKey.showAll();
            return gson.toJson(keys);
        });

        //Endpoint para obtener todos los ejercicios paginado
        Spark.get("/show/pageexercises/:page/:size",(request, response) -> {
            Integer page = Integer.parseInt(request.params(":page"));
            Integer size = Integer.parseInt(request.params(":size"));
            List<Exercises> exercises = daoExercises.showAll(page,size);
            Long totalItems = daoExercises.totalExercises();
            PaginationResponse<Exercises> result = new PaginationResponse<>(exercises,totalItems,page,size);
            return  gson.toJson(result);
        });

        //Endpoint para obtener todas las dietas paginado
        Spark.get("/show/pagediets/:page/:size",(request, response) -> {
            Integer page = Integer.parseInt(request.params(":page"));
            Integer size = Integer.parseInt(request.params(":size"));
            List<Diet> diets = daoDiet.showAll(page,size);
            Long allItems = daoDiet.totalDiets();
            PaginationResponse<Diet> result = new PaginationResponse<>(diets,allItems,page,size);
            return  gson.toJson(result);
        });

        //Endpoint para devolver ejercicios ordenados por dificultad
        Spark.get("/show/difficulty",(request, response) -> {
            List<Exercises> exercises = daoExercises.showSortedDifficulty();
            return gson.toJson(exercises);
        });

        //Endpoint para devolver todas las imagenes
        Spark.get("/show/images",(request, response) -> {
            List<String> images = daoExercises.showAllImages();
            return gson.toJson(images);
        });

        //Endpoint para devolver todas las imagenes con nombre
        Spark.get("/show/exercises/imagesName",(request, response) -> {
            List<ExercisesDTO> nameImage = daoExercises.showNamesImages();
            return gson.toJson(nameImage);
        });

        //Endpoint para devolver todas las imagenes, con nombre y nivel de dificultad pasado
        Spark.get("/show/imagesNameDifficulty/:level",(request, response) -> {
                Integer level = Integer.parseInt(request.params(":level"));
                List<ExercisesDTO> exercises = daoExercises.showNamesImagesDifficulty(level);
                return gson.toJson(exercises);
        });

        //Endpoint para devolver el numero de ejercicios registrados
        Spark.get("/show/exercises/total",(request, response) -> {
            Long total = daoExercises.totalExercises();
            return total.toString();
        });

        //Endpoint para buscar ejercicios por nombre
        Spark.get("/show/exercisesname/:name",(request, response) -> {
            String name = request.params(":name");
            List<Exercises> exercises = daoExercises.searchNameLike(name);
            return gson.toJson(exercises);
        });

        //Endpoint para filtrar por grupo muscular
        Spark.get("/show/musclegroup/:musclegroup",(request, response) -> {
            String muscleGroup = request.params(":musclegroup");
            List<Exercises> usuarios = daoExercises.filterMuscleGroup(muscleGroup);
            return gson.toJson(usuarios);
        });

        //Endpoint para filtrar por grupos musculares
        Spark.get("/show/musclegroups/:listmusclegroup", (request, response) -> {
            String muscleGroupsParam = request.params(":listmusclegroup");
            List<String> muscleGroups = Arrays.asList(muscleGroupsParam.split(","));
            List<Exercises> exercises = daoExercises.filterMuscleGroups(muscleGroups);
            return gson.toJson(exercises);
        });

        // Endpoint para buscar un ejercicio por su ID
        Spark.get("/show/exerciseid/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Exercises exercise = daoExercises.searchByID(id);
            if (exercise!=null){
                return gson.toJson(exercise);
            }else {
                return "Exercise not found";
            }
        });

        // Endpoint para obtener todos los usuarios
        Spark.get("/show/users",(request, response) ->{
            List<Users> users = daoUsers.showAll();
            return gson.toJson(users);
        });

// Relacionados

//        Endpoint obtener usuarios con dieta
        Spark.get("/show/diet/users/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Diet d = daoDiet.searchById(id);
            List<Users> u = daoAssociations.usersWithDiet(d);
            if (u != null ){
                return gson.toJson(u);
            }else {
                return "Diet not found";
            }
        });

//         Endpoint obtener dietas de un usuario
        Spark.get("/show/users/diet/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Users u = daoUsers.searchByID(id);
            List<Diet> d = daoAssociations.dietOfUser(u);
            if (d != null ){
                return gson.toJson(d);
            }else {
                return "User not found";
            }

        });

//          Endpoint para obtener los entrenamientos registrados de un usuario
        Spark.get("/show/user/trainingrecord/:id",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Users u = daoUsers.searchByID(id);
            List<TrainingRecords> tr = daoAssociations.trainingsUser(u);
            if (tr!=null){
                return gson.toJson(tr);
            }else {
                return "User not found";
            }
        });

//          Endpoint para obtener los entrenamientos registrados que use un ejercicio
        Spark.get("/show/exercise/trainingrecord/:id",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Exercises e = daoExercises.searchByID(id);
            List<TrainingRecords> tr = daoAssociations.traiginsExercise(e);
            if (tr!=null){
                return gson.toJson(tr);
            }else {
                return "Exercise not found";
            }

        });
//          Endpoint para obtener usuario que ha realizado el entrenamiento
        Spark.get("/show/trainingrecord/user/:id",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            TrainingRecords tr =daoTrainingRecords.searchById(id);
            Users u = daoAssociations.showUserPerformedTraining(tr);
            if (u!=null){
                return gson.toJson(u);
            }else {
                return "Training record not found";
            }

        });
//          Endpoint para obtener el ejercicio que ha sido utilizado en el entrenamiento
        Spark.get("/show/trainingrecord/exercise/:id",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            TrainingRecords tr = daoTrainingRecords.searchById(id);
            Exercises e = daoAssociations.showExerciseUsedTraining(tr);
            if (e!=null){
                return gson.toJson(e);
            }else {
                return "Training record not found";
            }

        });

//==================================  Endpoints POST  ============================================

        // Endpoint para agregar un nuevo ejercicio
        Spark.post("/create/exercise",(request, response) -> {
            String body = request.body();
            Exercises newExercise = gson.fromJson(body, Exercises.class);
            Exercises created = daoExercises.create(newExercise);
            System.out.println(created);
            if (created.getId() != null){
                return gson.toJson(created);
            }else{
                return "El ejercicio con ese nombre ya existe.";
            }
        });
        // Endpoint para agregar un nuevo usuario
        Spark.post("/create/user",(request, response) -> {
            String body = request.body();
            Users newUser = gson.fromJson(body, Users.class);
            Users created = daoUsers.createUser(newUser);
            System.out.println(created);
            if (created.getId() != null){
                return gson.toJson(created);
            }else{
                return "El usuario ya existe.";
            }
        });

        // Endpoint para crear una nueva dieta

        Spark.post("/create/diet",(request, response) -> {
            String body = request.body();
            Diet newDiet = gson.fromJson(body, Diet.class);
            Diet created = daoDiet.createDiet(newDiet);
            System.out.println(created);
            if (created.getId() != null){
                return gson.toJson(created);
            }else{
                return "La dieta ya existe.";
            }
        });



        // Endpoint para agregar un nuevo apikey
        Spark.post("/create/key",(request, response) -> {
            String body = request.body();
            ApiKeys newKey = gson.fromJson(body, ApiKeys.class);
            ApiKeys created = daoKey.createApiKey(newKey);
            System.out.println(created);
            if (created.getId() != null){
                return gson.toJson(created);
            }else{
                return "El apikey ya existe.";
            }
        });

//Relacionados

//        Endpoint para asignar dieta a un usuario
        Spark.post("/create/users/diet/:userid/:dietid",(request, response) -> {
            Long idUser = Long.parseLong(request.params(":userid"));
            Long idDiet = Long.parseLong(request.params(":dietid"));
            Users u = daoUsers.searchByID(idUser);
            Diet d = daoDiet.searchById(idDiet);
            return gson.toJson(daoAssociations.assignDietToUser(d,u));
        });

        // Endpoint para crear un nuevo entrenamiento
        Spark.post("/create/trainingrecords/:userid/:exerciseid",(request, response) -> {
            String body = request.body();
            Long idUser = Long.parseLong(request.params(":userid"));
            Long idExercise = Long.parseLong(request.params(":exerciseid"));
            Users u = daoUsers.searchByID(idUser);
            Exercises e = daoExercises.searchByID(idExercise);
            TrainingRecords newTraining = gson.fromJson(body, TrainingRecords.class);
            TrainingRecords created = daoAssociations.createTraining(newTraining,u,e);
            System.out.println(created);
            if (created.getExercise() != null && created.getUser() != null){
                return gson.toJson(created);
            }else{
                return "Error inserting training.";
            }
        });

//==================================  Endpoints PUT  ============================================

        // Endpoint para actualizar ejercicio por ID
        Spark.put("/modify/exercise/:id",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            String body = request.body();
            Exercises updatedExercise = gson.fromJson(body,Exercises.class);
            updatedExercise.setId(id);
            Exercises updated = daoExercises.updateByID(updatedExercise);
            if (updated != null) {
                return gson.toJson(updated);
            } else {
                response.status(404);
                return "Exercise not found";
            }
        });

        // Endpoint para actualizar usuario por ID
        Spark.put("/modify/user/:id",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            String body = request.body();
            Users updatedUser = gson.fromJson(body,Users.class);
            updatedUser.setId(id);
            Users updated = daoUsers.updateUserID(updatedUser);
            if (updated != null) {
                return gson.toJson(updated);
            } else {
                response.status(404);
                return "User not found";
            }
        });

        //Endpoint para actualizar dieta
        Spark.put("/modify/diet/:id",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            String body = request.body();
            Diet updateDiet = gson.fromJson(body, Diet.class);
            updateDiet.setId(id);
            Diet updated = daoDiet.updateDietByID(updateDiet);
            if (updated != null) {
                return gson.toJson(updated);
            } else {
                response.status(404);
                return "Diet not found";
            }
        });

        //Endpoint para actualizar entrenamiento
        Spark.put("/modify/trainingrecord/:id/:userid/:exerciseid",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            String body = request.body();
            Long idUser = Long.parseLong(request.params(":userid"));
            Long idExercise = Long.parseLong(request.params(":exerciseid"));
            Users u = daoUsers.searchByID(idUser);
            Exercises e = daoExercises.searchByID(idExercise);
            TrainingRecords updateTraining = gson.fromJson(body, TrainingRecords.class);
            updateTraining.setId(id);
            TrainingRecords updated =daoAssociations.updateTrainingtByID(updateTraining,u,e);
            if (updated != null) {
                return gson.toJson(updated);
            } else {
                response.status(404);
                return "Training record not found";
            }
        });

        // Endpoint para actualizar key por ID
        Spark.put("/modify/key/:id",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            String body = request.body();
            ApiKeys updatedKey = gson.fromJson(body,ApiKeys.class);
            updatedKey.setId(id);
            ApiKeys updated = daoKey.modifyByID(updatedKey);
            if (updated != null) {
                return gson.toJson(updated);
            } else {
                response.status(404);
                return "Key not found";
            }
        });


//==================================  Endpoints DELETE  ==========================================

        // Endpoint para eliminar un ejercicio por su ID
        Spark.delete("/delete/exercise/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            boolean deleted = daoExercises.deleteById(id);
            if (deleted){
                return "Exercise removed correctly";
            }else {
                response.status(404);
                return "Exercise not found";
            }
        });

        // Endpoint para eliminar un ejercicio por su nombre
        Spark.delete("/delete/name/:name", (request, response) -> {
                String name = request.params(":name");
                boolean deleted = daoExercises.deleteName(name);
                if (deleted){
                    return "Exercise removed correctly";
                }else {
                    response.status(404);
                    return "Exercise not found";
                }
        });

        // Endpoint para eliminar dieta por su ID
        Spark.delete("/delete/diet/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            boolean deleted = daoDiet.deleteDietByID(id);
            if (deleted){
                return "Diet removed correctly";
            }else {
                response.status(404);
                return "Diet not found";
            }
        });

        // Endpoint para eliminar usuario por su ID
        Spark.delete("/delete/user/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            boolean deleted = daoUsers.deleteUserByID(id);
            if (deleted){
                return "User removed correctly";
            }else {
                response.status(404);
                return "User not found";
            }
        });

        // Endpoint para eliminar entrenamiento por su ID
        Spark.delete("/delete/trainingrecord/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            boolean deleted = daoTrainingRecords.deleteTrainingByID(id);
            if (deleted){
                return "Training record removed correctly";
            }else {
                response.status(404);
                return "Training record not found";
            }
        });

        // Endpoint para eliminar una key por su ID
        Spark.delete("/delete/key/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            boolean deleted = daoKey.deleteById(id);
            if (deleted){
                return "Key removed correctly";
            }else {
                response.status(404);
                return "Key not found";
            }
        });


//==================================  Endpoints NO ENCONTRADO  ===================================

        // Endpoint en caso de que se introduzca una ruta mal
        Spark.notFound((request, response) -> {
            response.type("application/json");
            return "{\"error\": \"Ruta no encontrada\",\"hint1\": \"/exercises\"," +
                    "\"hint2\": \"/exercises/paginated/:page/:tam_page\",\"hint3\": \"/exerice/id/:id\"}";
        });

//==================================   Endpoints EXCEPCIONES   ===================================
        //Para ver las excepciones
        Spark.exception(Exception.class, (e, req, res) -> {
            e.printStackTrace(); // Imprime la excepción en la consola
            res.status(500); // Establece el código de estado HTTP 500
            res.body("Excepcion en tu codigo"); // Mensaje de error para el cliente
        });

    }


    private boolean validarAPIKEY(String apiKey , String path) {
        ApiKeys existApikey = daoKey.getApiKey(apiKey);
        if (path.equals("/show/*")){ return existApikey != null && existApikey.isReading(); }
        else if (path.equals("/create/*")) { return existApikey != null && existApikey.isCreate(); }
        else if (path.equals("/delete/*")) { return existApikey != null && existApikey.isDelete(); }
        else{ return existApikey != null && existApikey.isModify(); }
    }
}