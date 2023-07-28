import controller.ArticleController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.dao.sql.ArticleDaoSql;
import model.dto.Article;
import model.dto.Category;
import service.ArticleService;
import view.WardrobeView;

import java.util.List;



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


        }*/

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        WardrobeView view = new WardrobeView();
        ArticleService model = new ArticleService();
        ArticleDaoSql articleDao = new ArticleDaoSql();
        ArticleController voteViewController = new ArticleController(view, model);

        primaryStage.setTitle("My wardrobe");
        Scene scene = new Scene(view, 630, 670);
        scene.getStylesheets().add("stylesheet.css");
       // view.setBackground(new Background(new BackgroundFill(Color.web("#f5f5f5"), CornerRadii.EMPTY, Insets.EMPTY)));
        // Color.LIGHTGRAY

       /* List<Article> articleList = model.findAll();
        for (Article a : articleList)
            System.out.println(a.toString());*/

/*
       List<Article> articleList = model.findBy("TOP");
        for (Article a : articleList)
            System.out.println(a.toString());

*/
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}