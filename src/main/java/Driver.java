import com.horoscope.controllers.UserController;
import com.horoscope.services.UserService;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

public class Driver {
    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(8080);

        UserService service = new UserService();
        UserController controller = new UserController(service);

        app.post("/users/login", controller.login);
        app.post("/users/logout", controller.logout);
        app.post("/users/signup", controller.signup);
        app.put("/mood/update", controller.updateMood);
    }
}
