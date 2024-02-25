import dao.*;
import servicios.ExercisesAPIREST;

public class    Servidor {
    public static void main(String[] args) {

        ExercisesAPIREST api = new ExercisesAPIREST(new ExercisesDAO(),new UsersDAO(), new AssociationsDAO(), new DietDAO(), new TrainingDAO(), new CountryDAO(), new ApiKeyDAO());
    }

}
