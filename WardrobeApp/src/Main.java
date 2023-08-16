import controller.ArticleController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.dao.sql.ArticleDaoSql;
import model.dto.Article;
import model.dto.Category;
import model.dto.Gender;
import model.dto.Size;
import service.ArticleService;
import view.WardrobeView;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        WardrobeView view = new WardrobeView();
        ArticleService model = new ArticleService();
        ArticleDaoSql articleDao = new ArticleDaoSql();
        ArticleController voteViewController = new ArticleController(view, model);

        primaryStage.setTitle("My wardrobe");
        Scene scene = new Scene(view, 680, 790);
        scene.getStylesheets().add("stylesheet.css");
       // view.setBackground(new Background(new BackgroundFill(Color.web("#f5f5f5"), CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}