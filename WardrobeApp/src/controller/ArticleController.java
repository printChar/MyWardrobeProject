package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.dto.Article;
import model.dto.Category;
import model.dto.Gender;
import service.ArticleService;
import view.WardrobeView;

import java.io.File;
import java.util.List;

public class ArticleController {
   private WardrobeView view;
   private ArticleService model;
   private Article dtoArticle;

   public ArticleController(WardrobeView view, ArticleService model) {
      this.view = view;
      this.model = model;
      ArticleIMGHandler articleIMGHandler = new ArticleIMGHandler();
      ComboBoxListener comboBoxListener = new ComboBoxListener();
      updateView();
   }

   private void updateView(){
      List<Article> allArticles = model.findAll();
   }

   private class ComboBoxListener implements EventHandler {

      public ComboBoxListener() {
         view.addComboboxListener(this);
      }

      @Override
      public void handle(Event event) {

         view.getBrowseImgBtn().setDisable(false);

      }
   }

   private class ArticleIMGHandler implements EventHandler {
      public ArticleIMGHandler() {
         view.addArticleIMGListener(this);
      }

      @Override
      public void handle(Event event) {
         final FileChooser f = new FileChooser();
         Button btn = (Button) event.getSource();

         if(btn == view.getBrowseImgBtn()){
            File file = f.showOpenDialog(new Stage());
            if (file != null) {
               Image img = new Image(file.toURI().toString());
               view.getImgSrcTxf().setText(file.toURI().toString());

               Category cat = view.getTypeCB().getValue();

               switch (cat){
                  case TOP:
                     view.getTopImgView().setImage(img);
                     break;
                  case BOTTOM:
                     view.getBottomImgView().setImage(img);
                     break;
                  case SHOES:
                     view.getShoesImgView().setImage(img);
                     break;
               }
            }
         }
      }
   }
}
