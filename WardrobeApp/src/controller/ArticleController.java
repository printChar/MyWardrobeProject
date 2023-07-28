package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.dto.Article;
import model.dto.Category;
import service.ArticleService;
import view.WardrobeView;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
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
      disableInputs(true);
   }

   private class ComboBoxListener implements EventHandler {

      public ComboBoxListener() {
         view.addComboboxListener(this);
      }
      @Override
      public void handle(Event event) {
         view.getBrowseImgBtn().setDisable(false);
         List<Article> articleList;
         ComboBox cb = view.getCategoryCB();
         ComboBox cb2 = view.getCategoryCB2();

       //  Category cat = (Category) cb.getSelectionModel().getSelectedItem();

        if (event.getSource() == cb2) {
           view.getBrowseImgBtn().setDisable(true);
           Category cat = (Category) cb2.getSelectionModel().getSelectedItem();
           switch (cat) {
              case TOP:
                 System.out.println(cat);
                 articleList = model.findBy(cat.getCategory());
                 for (Article a : articleList)
                    System.out.println(a.toString());
                 break;
              case BOTTOM:
                 System.out.println(cat.getCategory());
                 break;
              case SHOES:
                 System.out.println(cat.getCategory());
                 break;
           }
        }else if(event.getSource() == cb) {
              view.getBrowseImgBtn().setDisable(false);

         }
      }
   }
   private void disableInputs(boolean value){
      view.getGenderCB().setDisable(value);
      view.getModelCB().setDisable(value);
      view.getColourCB().setDisable(value);
      view.getStyleCB().setDisable(value);
      view.getBrandCB().setDisable(value);
   }

   private class ArticleIMGHandler implements EventHandler {
      public ArticleIMGHandler() {
         view.addArticleIMGListener(this);
      }

      @Override
      public void handle(Event event) {
         final FileChooser f = new FileChooser();
         Button btn = view.getBrowseImgBtn();
         ComboBox cb = view.getCategoryCB();

         //System.out.println(view.getCategoryCB().getSelectionModel().getSelectedItem());

         if (event.getSource() == btn) {
            File file = f.showOpenDialog(new Stage());
            if (file != null) {
                  Image img = new Image(file.toURI().toString());
                  if (img.isError()) {
                     JOptionPane.showMessageDialog(null, "<html>Error<br>Missing images</html>",
                             "Error", JOptionPane.ERROR_MESSAGE);
                  } else {
                     view.getImgSrcTxf().setText(file.toURI().toString());
                     disableInputs(false);
                     Category cat = (Category) cb.getSelectionModel().getSelectedItem();

                   //  Category cat = view.getCategoryCB().getSelectionModel().getSelectedItem();

                     switch (cat) {
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
   }

