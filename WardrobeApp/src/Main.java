import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.ArticleView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

      /*    ModelDaoImpl modelDaoImpl = new ModelDaoImpl();
        List<Model> modelList = modelDaoImpl.getAll();

        for (Model m : modelList) {
            System.out.println(m.getName());
        }

/*       ArticleDaoImpl articleDao = new ArticleDaoImpl();

        Article article = new Article(10, 2, 1, Size.S,
                Gender.WOMEN, 3, 1, "testing.jpg", Boolean.FALSE);

        articleDao.create(article);

        List<Article> articleList = articleDao.getAll();
        for (Article a : articleList) {
            System.out.println(a.toString());
        }*/

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ArticleView view = new ArticleView();

        primaryStage.setTitle("My wardrobe");
        Scene scene = new Scene(view, 630, 670);
        scene.getStylesheets().add("stylesheet.css");

       // view.setBackground(new Background(new BackgroundFill(Color.web("#f5f5f5"), CornerRadii.EMPTY, Insets.EMPTY)));
        // Color.LIGHTGRAY

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}