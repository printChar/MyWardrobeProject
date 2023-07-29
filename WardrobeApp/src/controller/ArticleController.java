package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.dto.*;
import service.*;
import view.WardrobeView;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArticleController {
   private WardrobeView view;
   private ArticleService model;

   private ModelService modelService = new ModelService();
   private ColourService colourService = new ColourService();
   private StyleService styleService = new StyleService();
   private BrandService brandService = new BrandService();
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
      retrieveModelsFromDatabase();
      retrieveColoursFromDatabase();
      retrieveStylesFromDatabase();
      retrieveBrandsFromDatabase();

      //disableInputs(true);
   }
   private void disableInputs(boolean value){
      view.getGenderCB().setDisable(value);
      view.getModelCB().setDisable(value);
      view.getColourCB().setDisable(value);
      view.getStyleCB().setDisable(value);
      view.getBrandCB().setDisable(value);
   }

   private void retrieveModelsFromDatabase(){
      ObservableList<Model> modelsObservableList = FXCollections.observableArrayList(modelService.findAll());
      ComboBox cb = view.getModelCB();
      cb.setItems(modelsObservableList);
      cb.setConverter(new StringConverter<Model>() {
         @Override
         public String toString(Model model) {
            return model.getValue();
         }
         @Override
         public Model fromString(String string) {
            return null;
         }
      });
   }
   private void retrieveColoursFromDatabase() {
      ObservableList<Colour> coloursObservableList = FXCollections.observableArrayList(colourService.findAll());
      ComboBox cb = view.getColourCB();
      cb.setItems(coloursObservableList);
      cb.setConverter(new StringConverter<Colour>() {
         @Override
         public String toString(Colour colour) {
            return colour.getValue();
         }

         @Override
         public Colour fromString(String string) {
            return null;
         }
      });
   }

   private void retrieveStylesFromDatabase(){
      ObservableList<Style> brandsObservableList = FXCollections.observableArrayList(styleService.findAll());
      ComboBox cb = view.getStyleCB();
      cb.setItems(brandsObservableList);
      cb.setConverter(new StringConverter<Style>() {
         @Override
         public String toString(Style style) {
            return style.getName();
         }
         @Override
         public Style fromString(String string) {
            return null;
         }
      });
   }

   private void retrieveBrandsFromDatabase(){
      ObservableList<Brand> brandsObservableList = FXCollections.observableArrayList(brandService.findAll());
      ComboBox cb = view.getBrandCB();
      cb.setItems(brandsObservableList);
      cb.setConverter(new StringConverter<Brand>() {
         @Override
         public String toString(Brand brand) {
            return brand.getName();
         }
         @Override
         public Brand fromString(String string) {
            return null;
         }
      });
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
         ComboBox cb3 = view.getModelCB();

        if (event.getSource() == cb2) {
           view.getBrowseImgBtn().setDisable(true);
           Category cat = (Category) cb2.getSelectionModel().getSelectedItem();
           switch (cat) {
              case TOP:
                 articleList = model.findBy(cat.getCategory());
                 ObservableList<Article> clothesObservableList = FXCollections.observableArrayList(model.findBy(cat.getCategory()));

                 for (Article a : clothesObservableList) {
                    Image img = new Image(clothesObservableList.get(0).getPicSrc());
                    view.getTopImgView().setImage(img);
                    System.out.println(articleList.get(0).toString());
                    disableInputs(false);
                    view.getGenderCB().getSelectionModel().select(clothesObservableList.get(0).getGender());
                    view.getModelCB().getSelectionModel().select(clothesObservableList.get(0).getModel());
                    view.getColourCB().getSelectionModel().select(clothesObservableList.get(0).getColour());
                    view.getStyleCB().getSelectionModel().select(clothesObservableList.get(0).getStyle());
                    view.getBrandCB().getSelectionModel().select(clothesObservableList.get(0).getBrand());
                   // view.getBrandCB().setDisable(value);
                 }
                 break;
              case BOTTOM:
                 articleList = model.findBy(cat.getCategory());
                 for (Article a : articleList)
                    System.out.println(a.toString());
                 break;
              case SHOES:
                 articleList = model.findBy(cat.getCategory());
                 for (Article a : articleList)
                    System.out.println(a.toString());
                 break;
           }
        }else if(event.getSource() == cb) {
              view.getBrowseImgBtn().setDisable(false);
         }
        else if(event.getSource() == cb3) {
           Model modelvalue = (Model) cb3.getSelectionModel().getSelectedItem();
           System.out.println(modelvalue.getValue());
        }
      }
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

         if (event.getSource() == btn) {
            File file = f.showOpenDialog(new Stage());
            if (file != null) {
                  Image img = new Image(file.toURI().toString());

                  if (img.isError()) {
                     JOptionPane.showMessageDialog(null, "<html>Error<br>Missing images</html>",
                             "Error", JOptionPane.ERROR_MESSAGE);
                  } else {
                     view.getImgSrcTxf().setText(file.toURI().toString());
                     System.out.println(file.toURI());
                     disableInputs(false);
                     Category cat = (Category) cb.getSelectionModel().getSelectedItem();

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

