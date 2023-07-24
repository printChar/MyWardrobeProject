package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.dto.Article;
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
      BtnHandlerCRUD crudOperator = new BtnHandlerCRUD();
      updateView();
   }

   private void updateView(){
      List<Article> allArticles = model.findAll();
   }

   private class BtnHandlerCRUD implements EventHandler {
      public BtnHandlerCRUD() {
         view.addArticleBtnListener(this);
      }

      @Override
      public void handle(Event event) {
         final FileChooser f = new FileChooser();
         Button btn = (Button) event.getSource();

         if(btn == view.getBrowseImgBtn()){
            File file = f.showOpenDialog(new Stage());
            if (file != null) {

            }
         }

      }
      }
   }
