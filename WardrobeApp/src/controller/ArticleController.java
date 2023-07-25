package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
      CrudBtnHandler btnHandler = new CrudBtnHandler();
      updateView();
   }

   private void updateView(){
      List<Article> allArticles = model.findAll();
      boolean isMyComboBoxEmpty = view.getTypeInput().getSelectionModel().isEmpty();

      if(isMyComboBoxEmpty){
         view.getSexInput().setDisable(false);
      }
      view.getSexInput().setDisable(true);
   }

   private class classComboBoxListener implements EventHandler {

      public classComboBoxListener() {
         view.addComboboxListener(this);
      }

      @Override
      public void handle(Event event) {

      }
   }

   private class CrudBtnHandler implements EventHandler {
      public CrudBtnHandler() {
         view.addArticleBtnListener(this);
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
               view.getTopImgView().setImage(img);
            }
         }

      }
      }
   }
