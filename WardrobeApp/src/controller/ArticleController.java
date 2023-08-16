package controller;
import com.sun.org.apache.bcel.internal.generic.FADD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.dto.*;
import service.*;
import view.WardrobeView;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.Random;


public class ArticleController {
    private final String TOP_IMG = "defaultImg/tshirt-vector.png";
    private final String BOTTOM_IMG = "defaultImg/pants-vector.png";
   // private final String SHOES_IMG = "defaultImg/shoe-vector.png";
    private int currTopID;
    private int currShoesID;
    private int currBottomID;
    private int topPos = -1;
    private int bottomPos = -1;
    private int shoesPos = -1;
    private int outfitPos = -1;
    private int outfitPosLabel = 0;
    private int topPosLabel = 0;
    private int bottomPosLabel = 0;
    private int shoesPosLabel = 6;
    private int currArticleID;
    private Category categoryBtnID;
    private WardrobeView view;
    private ArticleService model;
    private ModelService modelService = new ModelService();
    private OutfitService outfitService = new OutfitService();
    private ColourService colourService = new ColourService();
    private StyleService styleService = new StyleService();
    private BrandService brandService = new BrandService();
    public ArticleController(WardrobeView view, ArticleService model) {
        this.view = view;
        this.model = model;
        ArticleIMGHandler articleIMGHandler = new ArticleIMGHandler();
       // RightComboBoxListener rightComboBoxListener = new RightComboBoxListener();
        ImageViewListener imageViewListener = new ImageViewListener();
        SwipeButtonListener swipeButtonListener = new SwipeButtonListener();
        CategoryHandler categoryHandler = new CategoryHandler();
        CrudBtnHandler crud = new CrudBtnHandler();
        updateView();
    }
    private void updateView() {
        List<Article> allArticles = model.findAll();
        List<Outfit> allOutfits = outfitService.findAll();
        view.leftSizeHolder();
        retrieveModelsFromDatabase();
        retrieveColoursFromDatabase();
        retrieveStylesFromDatabase();
        retrieveBrandsFromDatabase();
        setCategories();
        setCategories2();
        new RandomiseListener();
        view.getCrudHolder().setVisible(false);
        restartInputsAfterCRUD();
        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(-0.7);
        view.getCreateBtn().setEffect(desaturate);
        ColorAdjust desaturate2 = new ColorAdjust();
        desaturate2.setSaturation(-1);
        view.getTopCategoryIconBtn().setEffect(desaturate2);
        view.getBottomCategoryIconBtn().setEffect(desaturate2);
        view.getShoesCategoryIconBtn().setEffect(desaturate2);
    }
    private void restartInputs() {
        view.getImgSrcTxf().setText("");

        view.getGenderCB().getSelectionModel().clearSelection();
        view.getModelCB().getSelectionModel().clearSelection();
        view.getColourCB().getSelectionModel().clearSelection();
        view.getStyleCB().getSelectionModel().clearSelection();
        view.getBrandCB().getSelectionModel().clearSelection();
        for (int i = 0; i < view.getSizeToggleGroup().getToggles().size(); i++) {
            view.getSizeToggleGroup().getToggles().get(i).setSelected(false);
        }
        for (int i = 0; i < view.getStatusToggleGroup().getToggles().size(); i++) {
            view.getStatusToggleGroup().getToggles().get(i).setSelected(false);
        }

       // view.getBrowseImgBtn().setDisable(true);
        disableInputs(true);

    }
    private void refreshViews(){
        view.getTopImgViewBtn().setGraphic(view.addDefImgToImageView());
        view.getBottomImgViewBtn().setGraphic(view.addDefImgToImageView());
        view.getShoesImgViewBtn().setGraphic(view.addDefImgToImageView());
    }
    private void restartInputsAfterCRUD() {
        view.getCategoryCB().getSelectionModel().clearSelection();
        view.getImgSrcTxf().setText("");
      //  view.getCategoryCB2().getSelectionModel().clearSelection();
        view.getGenderCB().getSelectionModel().clearSelection();
        view.getModelCB().getSelectionModel().clearSelection();
        view.getColourCB().getSelectionModel().clearSelection();
        view.getStyleCB().getSelectionModel().clearSelection();
        view.getThemeCB().getSelectionModel().clearSelection();
        view.getBrandCB().getSelectionModel().clearSelection();

        for (int i = 0; i < view.getSizeToggleGroup().getToggles().size(); i++) {
            view.getSizeToggleGroup().getToggles().get(i).setSelected(false);
        }

        for (int i = 0; i < view.getStatusToggleGroup().getToggles().size(); i++) {
            view.getStatusToggleGroup().getToggles().get(i).setSelected(false);
        }
        refreshViews();
        view.getBrowseImgBtn().setDisable(true);
        outfitPos = -1;
        topPos = -1;
        bottomPos = -1;
        shoesPos = -1;
        outfitPosLabel = 0;
        topPosLabel = 0;
        bottomPosLabel = 0;
        shoesPosLabel = 0;
        view.getPosLabel().setText("");
        view.getAmountLabel().setText("");
        disableInputs(true);
    }
    private void disableInputs(boolean value) {
        view.getGenderCB().setDisable(value);
        view.getModelCB().setDisable(value);
        view.getColourCB().setDisable(value);
        view.getStyleCB().setDisable(value);
        view.getBrandCB().setDisable(value);
    }

    private boolean checkArticleInputs(){
        boolean allChecked;
        if(view.getGenderCB().getSelectionModel().isEmpty() || view.getModelCB().getSelectionModel().isEmpty()
        || view.getColourCB().getSelectionModel().isEmpty() || view.getStyleCB().getSelectionModel().isEmpty()
        || view.getBrandCB().getSelectionModel().isEmpty() || view.getImgSrcTxf().getText().isEmpty()) {
            return allChecked = false;
        }else{
            return allChecked = true;
        }
    }

    private void retrieveModelsFromDatabase() {
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
               /*Colour newColour = null;
               newColour.setValue(string);
               return newColour.getValue();*/
               return null;

            }
        });
    }
    private void retrieveStylesFromDatabase() {
        ObservableList<Style> brandsObservableList = FXCollections.observableArrayList(styleService.findAll());
        ComboBox cb = view.getStyleCB();
        ComboBox cb2 = view.getThemeCB();

        cb.setItems(brandsObservableList);
        cb2.setItems(brandsObservableList);
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
        cb2.setConverter(new StringConverter<Style>() {
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
    private void setCategories() {
        ObservableList<Category> categories = FXCollections.observableArrayList(Category.values());
        ComboBox cb = view.getCategoryCB();
        cb.setItems(categories);
        cb.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category.getCategory();
            }
            @Override
            public Category fromString(String string) {
                return null;
            }
        });
    }
    private void setCategories2() {
        ObservableList<Category> categories = FXCollections.observableArrayList(Category.values());
        ComboBox cb2 = view.getCategoryCB2();
        cb2.setItems(categories);
        cb2.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category.getCategory();
            }
            @Override
            public Category fromString(String string) {
                return null;
            }
        });
    }
    private void retrieveBrandsFromDatabase() {
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
    private void setSelectedToggleOn(Article article) {
        Size size = article.getSize();
        view.sizeHolder(article.getCategory());
        ObservableList<Toggle> sizesObservableList = view.getSizeToggleGroup().getToggles();
        for (int i = 0; i < sizesObservableList.size(); i++) {
            RadioButton rb = (RadioButton) sizesObservableList.get(i);

            if (rb.getText().equals(size.name())) {
                sizesObservableList.get(i).setSelected(true);
            } else if (rb.getText().equals(String.valueOf(size.toIntValue()))) {
                sizesObservableList.get(i).setSelected(true);
            }
        }
    }
    private void setSelectedToggleOn(Boolean isClean) {
        ObservableList<Toggle> statusObservableList = view.getStatusToggleGroup().getToggles();
        for (int i = 0; i < statusObservableList.size(); i++) {
            if (isClean) {
                statusObservableList.get(0).setSelected(true);
            } else {
                statusObservableList.get(1).setSelected(true);
            }
        }
    }
    private void showOutfit(Outfit outfit) {
        Article top = model.getBy(outfit.getTop());
        Article bottom = model.getBy(outfit.getBottom());
        Article shoes = model.getBy(outfit.getShoes());

        view.getTopImgViewBtn().setGraphic(view.addTestingImageView(new Image(top.getPicSrc())));
        view.getBottomImgViewBtn().setGraphic(view.addTestingImageView(new Image(bottom.getPicSrc())));
        view.getShoesImgViewBtn().setGraphic(view.addTestingImageView(new Image(shoes.getPicSrc())));
        view.getThemeCB().getSelectionModel().select(outfit.getStyle() - 1);
    }
    private void resetCatBtns(){
        view.getTopCategoryIconBtn().getStyleClass().remove("selectedCat");
        view.getTopCategoryIconBtn().setEffect(null);
        view.getBottomCategoryIconBtn().getStyleClass().remove("selectedCat");
        view.getBottomCategoryIconBtn().setEffect(null);
        view.getShoesCategoryIconBtn().getStyleClass().remove("selectedCat");
        view.getShoesCategoryIconBtn().setEffect(null);
    }
    private void resetBorder(){
        view.getTopImgViewBtn().getStyleClass().remove("selectedView");
        view.getBottomImgViewBtn().getStyleClass().remove("selectedView");
        view.getShoesImgViewBtn().getStyleClass().remove("selectedView");
    }
    private void showThisArticle(Article article) {
        view.getCreateBtnHolder().setVisible(false);
        categoryStyle();

        currArticleID = article.getId();
        if (Category.TOP == article.getCategory()) {
            currTopID = article.getId();
            view.getTopImgViewBtn().setGraphic(view.addTestingImageView(new Image(article.getPicSrc())));

        } else if (Category.BOTTOM == article.getCategory()) {
            currBottomID = article.getId();
            view.getBottomImgViewBtn().setGraphic(view.addTestingImageView(new Image(article.getPicSrc())));

        } else if (Category.SHOES == article.getCategory()) {
            currShoesID = article.getId();
            view.getShoesImgViewBtn().setGraphic(view.addTestingImageView(new Image(article.getPicSrc())));
        }
        view.getGenderCB().getSelectionModel().select(article.getGender());
        view.getModelCB().getSelectionModel().select(article.getModel() - 1);
        view.getColourCB().getSelectionModel().select(article.getColour() - 1);
        view.getStyleCB().getSelectionModel().select(article.getStyle() - 1);
        view.getBrandCB().getSelectionModel().select(article.getBrand() - 1);
        setSelectedToggleOn(article);
        setSelectedToggleOn(article.isClean());
      //  view.getCreateOutfitBtn().setDisable(true);
       // view.getDeleteOutfitBtn().setDisable(true);
        //view.getRefreshOutfitBtn().

    }
    private class RightComboBoxListener implements EventHandler {
        public RightComboBoxListener() {
            view.addRightComboboxListener(this);
        }
        @Override
        public void handle(Event e) {
            ComboBox<Category> cb2 = view.getCategoryCB2();
            view.getBrowseImgBtn().setDisable(true);
            view.getCreateBtn().setVisible(false);
            resetCatBtns();

            if (cb2 == e.getSource()) {
                disableInputs(false);
                view.getSizeToggleGroup().getToggles().clear();
                String stringCat = cb2.getSelectionModel().getSelectedItem().getCategory().toUpperCase();
                Category cat = Category.valueOf(stringCat);
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(stringCat));
                if(!articlesByCategoryList.isEmpty()){
                    switch (cat){
                        case TOP:
                            topPos = 0;
                            showThisArticle(articlesByCategoryList.get(getTopPos()));
                        case BOTTOM:
                            bottomPos = 0;
                            showThisArticle(articlesByCategoryList.get(getBottomPos()));
                        case SHOES:
                            shoesPos = 0;
                            showThisArticle(articlesByCategoryList.get(getShoesPos()));
                    }
                    view.getArticleAmountBox().setVisible(true);
                    view.sizeHolder(cat);
                    resetCatBtns();
                    view.getCrudHolder().setVisible(true);
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The list of articles whitin the " + cat + " category is empty. ", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        }
    }
   private class SwipeButtonListener implements EventHandler{
        public SwipeButtonListener() {
            view.addSwipeBtnListener(this);
        }
        @Override
        public void handle(Event event) {
            Button articleBtn = (Button) event.getSource();
            view.getCreateBtn().setVisible(false);
            view.getArticleAmountBox().setVisible(true);
            view.getOutfitHolder().setVisible(true);
            view.getCreateOutfitBtn().setDisable(false);
            view.getDeleteOutfitBtn().setDisable(false);
            view.getCrudHolder().setVisible(false);
            categoryStyle();
            disableInputs(true);
            //view.getCrudHolder().setVisible(true);
            resetBorder();

            if (articleBtn == view.getRightTopBtn()) {
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.TOP.getCategory()));
               if(!articlesByCategoryList.isEmpty()) {
                   if (getTopPos() >= articlesByCategoryList.size() - 1) {
                       topPos = articlesByCategoryList.size() - 1;
                   } else {
                       if (getTopPos() == -1) {
                           topPos = 0;
                       } else {
                           topPos++;
                       }
                       topPosLabel++;
                   }
                   view.getPosLabel().setText(String.valueOf(getTopPosLabel()));
                   view.getAmountLabel().setText("/" + articlesByCategoryList.size());
                   showThisArticle(articlesByCategoryList.get(getTopPos()));
               }else{
                   Alert alert = new Alert(Alert.AlertType.WARNING, "There is no stored tops. ", ButtonType.OK);
                   alert.showAndWait();
               }
            } else if (articleBtn == view.getLeftTopBtn()) {
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.TOP.getCategory()));
               if(!articlesByCategoryList.isEmpty()) {
                   if (getTopPos() <= 0) {
                       topPos = 0;
                       topPosLabel = 1;
                   } else {
                       topPos--;
                       topPosLabel--;
                   }
                   view.getPosLabel().setText(String.valueOf(getTopPosLabel()));
                   view.getAmountLabel().setText("/" + articlesByCategoryList.size());
                   showThisArticle(articlesByCategoryList.get(getTopPos()));
               }else{
                   Alert alert = new Alert(Alert.AlertType.WARNING, "There is no stored tops. ", ButtonType.OK);
                   alert.showAndWait();
               }

            }else if (articleBtn == view.getLeftBottomBtn()) {
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.BOTTOM.getCategory()));
                if(!articlesByCategoryList.isEmpty()) {
                    if (getBottomPos() <= 0) {
                        bottomPos = 0;
                        bottomPosLabel = 1;
                    } else {
                        bottomPos--;
                        bottomPosLabel--;
                    }
                    view.getPosLabel().setText(String.valueOf(getBottomPosLabel()));
                    view.getAmountLabel().setText("/" + articlesByCategoryList.size());
                    showThisArticle(articlesByCategoryList.get(getBottomPos()));
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, "There is no stored bottoms. ", ButtonType.OK);
                    alert.showAndWait();
                }

            } else if (articleBtn == view.getRightBottomBtn()) {
                ObservableList<Article> articlesByCategoryList  = FXCollections.observableArrayList(model.findBy(Category.BOTTOM.getCategory()));
                if(!articlesByCategoryList.isEmpty()) {
                    if (getBottomPos() >= articlesByCategoryList.size() - 1) {
                        bottomPos = articlesByCategoryList.size() - 1;
                    } else {
                        if (getBottomPos() == -1) {
                            bottomPos = 0;
                        } else {
                            bottomPos++;
                        }
                        bottomPosLabel++;
                    }
                    view.getPosLabel().setText(String.valueOf(getBottomPosLabel()));
                    view.getAmountLabel().setText("/" + articlesByCategoryList.size());
                    showThisArticle(articlesByCategoryList.get(getBottomPos()));
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, "There is no stored bottoms. ", ButtonType.OK);
                    alert.showAndWait();
                }

            } else if (articleBtn == view.getRightShoesBtn()) {
                ObservableList<Article> articlesByCategoryList  = FXCollections.observableArrayList(model.findBy(Category.SHOES.getCategory()));
                if(!articlesByCategoryList.isEmpty()) {
                    if (getShoesPos() >= articlesByCategoryList.size() - 1) {
                        shoesPos = articlesByCategoryList.size() - 1;
                    } else {
                        if (getShoesPos() == -1) {
                            shoesPos = 0;
                        } else {
                            shoesPos++;
                        }
                        shoesPosLabel++;
                    }
                    view.getPosLabel().setText(String.valueOf(getShoesPosLabel()));
                    view.getAmountLabel().setText("/" + articlesByCategoryList.size());
                    showThisArticle(articlesByCategoryList.get(getShoesPos()));
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, "There is no stored shoes. ", ButtonType.OK);
                    alert.showAndWait();
                }

            } else if (articleBtn == view.getLeftShoesBtn()) {
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.SHOES.getCategory()));
                if (!articlesByCategoryList.isEmpty()){
                    if (getShoesPos() <= 0) {
                        shoesPos = 0;
                        shoesPosLabel = 1;
                    } else {
                        shoesPos--;
                        shoesPosLabel--;
                    }
                view.getPosLabel().setText(String.valueOf(getShoesPosLabel()));
                view.getAmountLabel().setText("/" + articlesByCategoryList.size());
                showThisArticle(articlesByCategoryList.get(getShoesPos()));
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "There is no stored shoes. ", ButtonType.OK);
                alert.showAndWait();
            }

            } else if(articleBtn == view.getSwipeLeftOutfitBtn()){
                List<Outfit> outfitsList  = outfitService.findAll();
                if(!outfitsList.isEmpty()) {
                    if (getOutfitPos() <= 0) {
                        outfitPos = 0;
                        outfitPosLabel = 1;
                    } else {
                        outfitPos--;
                        outfitPosLabel--;
                    }
                    view.getPosLabel().setText(String.valueOf(getOutfitPosLabel()));
                    view.getAmountLabel().setText("/" + outfitsList.size());
                    showOutfit(outfitsList.get(getOutfitPos()));
                    int currOutfitID = outfitsList.get(getOutfitPos()).getId();
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, "There is no stored outfits. ", ButtonType.OK);
                    alert.showAndWait();
                }
            }else if(articleBtn == view.getSwipeRightOutfitBtn()){

                    List<Outfit> outfitsList = outfitService.findAll();
                if(!outfitsList.isEmpty()) {
                    if (getOutfitPos() >= outfitsList.size() - 1) {
                        outfitPos = outfitsList.size() - 1;
                        outfitPosLabel = outfitsList.size();
                    } else {
                        if (getOutfitPos() == -1) {
                            outfitPos = 0;
                        } else {
                            outfitPos++;
                        }
                        outfitPosLabel++;
                    }
                    view.getPosLabel().setText(String.valueOf(getOutfitPosLabel()));
                    view.getAmountLabel().setText("/" + outfitsList.size());
                    showOutfit(outfitsList.get(getOutfitPos()));
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING, "There is no stored outfits. ", ButtonType.OK);
                    alert.showAndWait();
                    view.getOutfitHolder().setVisible(false);
                }
            }
        }
    }

    private class RandomiseListener implements EventHandler{

        public RandomiseListener() {
            view.randomizeOutfit(this);
        }
        @Override
        public void handle(Event event) {
            view.getCreateOutfitBtn().setDisable(false);
            view.getDeleteOutfitBtn().setDisable(false);

            if(event.getSource() == view.getRandomizeOutfitBtn()) {
                    Random random = new Random();
                    List<Article> topList = model.findBy(Category.TOP.getCategory());
                    List<Article> bottomList = model.findBy(Category.BOTTOM.getCategory());
                    List<Article> shoesList = model.findBy(Category.SHOES.getCategory());
                    if(!topList.isEmpty() && !bottomList.isEmpty() && !shoesList.isEmpty()){
                    int maxTop = topList.size();
                    int maxBottom = bottomList.size();
                    int maxShoes = shoesList.size();
                    int min = 0;

                    int randomTop = random.nextInt(maxTop - min) + min;
                    int randomBottom = random.nextInt(maxBottom - min) + min;
                    int randomShoes = random.nextInt(maxShoes - min) + min;
                    System.out.println(randomTop + " " + randomBottom + " " + randomShoes);
                    showThisArticle(topList.get(randomTop));
                    showThisArticle(bottomList.get(randomBottom));
                    showThisArticle(shoesList.get(randomShoes));
                }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The app miss articles to randomise an outfit. ", ButtonType.OK);
                        alert.showAndWait();
                    }
            }
        }
    }
    private class ImageViewListener implements EventHandler {
        public ImageViewListener() {
            view.addImageviewListener(this);
        }
        @Override
        public void handle(Event event) {

            view.getCrudHolder().setVisible(true);
            Button articleBtn = (Button) event.getSource();
            disableInputs(false);

            view.getTopImgViewBtn().getStyleClass().remove("selectedView");
            view.getBottomImgViewBtn().getStyleClass().remove("selectedView");
            view.getShoesImgViewBtn().getStyleClass().remove("selectedView");

            if (articleBtn == view.getTopImgViewBtn()) {
                view.getCategoryCB2().getSelectionModel().select(Category.TOP);
                List<Article> articlesByCategoryList = model.findBy(Category.TOP.getCategory());
                view.getTopImgViewBtn().getStyleClass().add("selectedView");
                if (getTopPos() <= 0) {
                    topPos = 0;
                    topPosLabel = 1;
                }
                showThisArticle(articlesByCategoryList.get(getTopPos()));
                view.getPosLabel().setText(String.valueOf(getTopPosLabel()));
                view.getAmountLabel().setText("/" + articlesByCategoryList.size());

            } else if (articleBtn == view.getBottomImgViewBtn()) {
                view.getCategoryCB2().getSelectionModel().select(Category.BOTTOM);
                List<Article> articlesByCategoryList = model.findBy(Category.BOTTOM.getCategory());
                view.getBottomImgViewBtn().getStyleClass().add("selectedView");
                if (getBottomPos() <= 0) {
                    bottomPos = 0;
                    bottomPosLabel = 1;
                }
                showThisArticle(articlesByCategoryList.get(getBottomPos()));
                view.getPosLabel().setText(String.valueOf(getBottomPosLabel()));
                view.getAmountLabel().setText("/" + articlesByCategoryList.size());

            } else if (articleBtn == view.getShoesImgViewBtn()) {
                view.getCategoryCB2().getSelectionModel().select(Category.SHOES);
                List<Article> articlesByCategoryList = model.findBy(Category.SHOES.getCategory());
                view.getShoesImgViewBtn().getStyleClass().add("selectedView");
                if (getShoesPos() <= 0) {
                    shoesPos = 0;
                    shoesPosLabel = 1;
                }
                showThisArticle(articlesByCategoryList.get(getShoesPos()));
                view.getPosLabel().setText(String.valueOf(getShoesPosLabel()));
                view.getAmountLabel().setText("/" + articlesByCategoryList.size());
            }
            view.getBrowseImgBtn().setDisable(true);
        }
    }
    private class CrudBtnHandler implements EventHandler {
            public CrudBtnHandler() {
                view.addCrudBtnListener(this);
            }
            @Override
            public void handle(Event event) {
                Button btn = (Button) event.getSource();
                boolean isClean = false;
               // Model articleModel;
               // view.getCreateBtn().getStyleClass().add("selectedView");
                resetBorder();

                if (btn == view.getCreateBtn()) {
                    if(checkArticleInputs()) {
                        if (Category.SHOES == getCategoryBtnID()) {
                            Colour colour = (Colour) view.getColourCB().getSelectionModel().getSelectedItem();
                            Model shoeModel = (Model) view.getModelCB().getSelectionModel().getSelectedItem();
                            Style style = (Style) view.getStyleCB().getSelectionModel().getSelectedItem();
                            Category category = getCategoryBtnID();
                            RadioButton chk = (RadioButton) view.getSizeToggleGroup().getSelectedToggle(); //size
                            RadioButton chk2 = (RadioButton) view.getStatusToggleGroup().getSelectedToggle();
                            Gender gender = view.getGenderCB().getSelectionModel().getSelectedItem();
                            Brand brand = (Brand) view.getBrandCB().getSelectionModel().getSelectedItem();
                            String img = view.getImgSrcTxf().getText();
                            Size size = Size.toTextValue(Integer.parseInt(chk.getText()));
                            if (chk2.getText().equals("Clean")) {
                                isClean = true;
                            }
                            System.out.println(model.create(new Article(colour.getId(), style.getId(), category, size, gender, shoeModel.getId(), brand.getId(), img, isClean)));

                        } else {
                            Colour colour = (Colour) view.getColourCB().getSelectionModel().getSelectedItem();
                            Model clothModel = (Model) view.getModelCB().getSelectionModel().getSelectedItem();
                            Style style = (Style) view.getStyleCB().getSelectionModel().getSelectedItem();
                            Category category = getCategoryBtnID();
                            RadioButton chk = (RadioButton) view.getSizeToggleGroup().getSelectedToggle(); //size
                            RadioButton chk2 = (RadioButton) view.getStatusToggleGroup().getSelectedToggle();
                            Gender gender = view.getGenderCB().getSelectionModel().getSelectedItem();
                            Brand brand = (Brand) view.getBrandCB().getSelectionModel().getSelectedItem();
                            String img = view.getImgSrcTxf().getText();
                            Size size = Size.valueOf(chk.getText());
                            if (chk2.getText().equals("Clean")) {
                                isClean = true;
                            }
                            System.out.println(model.create(new Article(colour.getId(), style.getId(), category, size, gender, clothModel.getId(), brand.getId(), img, isClean)));
                        }
                        restartInputsAfterCRUD();
                        resetCatBtns();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING, "You forgot to add some traits, please go back and fill in.", ButtonType.OK);
                        alert.showAndWait();
                    }

                }else if (btn == view.getCreateOutfitBtn()) {
                    if (view.getThemeCB().getSelectionModel().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "You have to add a theme to the outfit to be able store it.", ButtonType.OK);
                        alert.showAndWait();
                    } else {
                        Article top = model.getBy(getCurrTopID());
                        Article bottom = model.getBy(getCurrBottomID());
                        Article shoes = model.getBy(getCurrShoesID());

                        Style style = view.getThemeCB().getSelectionModel().getSelectedItem();
                        System.out.println(outfitService.create(new Outfit(top.getId(), bottom.getId(), shoes.getId(), style.getId())));
                        restartInputsAfterCRUD();
                    }
                    } else if (btn == view.getRemoveBtn()) {
                        System.out.println(model.delete(getCurrArticleID()));
                        restartInputsAfterCRUD();

                    } else if (btn == view.getRefreshOutfitBtn()) {
                        restartInputsAfterCRUD();

                    } else if (btn == view.getDeleteOutfitBtn()) {
                    List<Outfit> outfitsList  = outfitService.findAll();
                    System.out.println(outfitService.delete(outfitsList.get(getOutfitPos()).getId()));
                    restartInputsAfterCRUD();

                }else if (btn == view.getSaveBtn()) {
                    Article article = model.getBy(getCurrArticleID());
                    Size size;
                    RadioButton chk = (RadioButton) view.getSizeToggleGroup().getSelectedToggle(); //size
                    RadioButton chk2 = (RadioButton) view.getStatusToggleGroup().getSelectedToggle();
                    if(article.getCategory() == Category.SHOES) {
                        size = Size.toTextValue(Integer.parseInt(chk.getText()));
                    }else {
                        size = Size.valueOf(chk.getText());
                    }
                //    colourID=?, styleID=?, size=?, gender=?, modelID=?, brandID=?, isClean=?  WHERE ID=?"
                    Colour colour = (Colour) view.getColourCB().getSelectionModel().getSelectedItem();
                    article.setColour(colour.getId());
                    Style style = (Style) view.getStyleCB().getSelectionModel().getSelectedItem();
                    article.setStyle(style.getId());
                    Gender gender = view.getGenderCB().getSelectionModel().getSelectedItem();
                    article.setGender(gender);
                    Model articleModel = (Model) view.getModelCB().getSelectionModel().getSelectedItem();
                    article.setModel(articleModel.getId());
                    Brand brand = (Brand) view.getBrandCB().getSelectionModel().getSelectedItem();
                    article.setBrand(brand.getId());
                    article.setSize(size);
                    if (chk2.getText().equals("Clean")) {
                        isClean = true;
                    }
                    article.setClean(isClean);
                    System.out.println(model.update(article));
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
                Category cat = getCategoryBtnID();

                if (event.getSource() == btn) {
                    File file = f.showOpenDialog(new Stage());
                    if (file != null) {
                        Image img = new Image(file.toURI().toString());

                        if (img.isError()) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Could not add that image, try again.", ButtonType.OK);
                            alert.showAndWait();
                        } else {
                            view.getImgSrcTxf().setText(file.toURI().toString());
                            disableInputs(false);

                            switch (cat) {
                                case TOP:
                                    view.getTopImgViewBtn().setGraphic(view.addTestingImageView(img));
                                    break;
                                case BOTTOM:
                                    view.getBottomImgViewBtn().setGraphic(view.addTestingImageView(img));
                                    break;
                                case SHOES:
                                    view.getShoesImgViewBtn().setGraphic(view.addTestingImageView(img));
                                    break;
                            }
                        }
                    }
                }
            }
        }

        private void categoryStyle(){
            ColorAdjust desaturate = new ColorAdjust();
            desaturate.setSaturation(-1);
            view.getTopCategoryIconBtn().getStyleClass().remove("selectedCat");
            view.getTopCategoryIconBtn().setEffect(desaturate);
            view.getBottomCategoryIconBtn().getStyleClass().remove("selectedCat");
            view.getBottomCategoryIconBtn().setEffect(desaturate);
            view.getShoesCategoryIconBtn().getStyleClass().remove("selectedCat");
            view.getShoesCategoryIconBtn().setEffect(desaturate);
    }
    private class CategoryHandler implements EventHandler{
        public CategoryHandler() {
            view.categoryHandler(this);
        }
        @Override
        public void handle(Event event) {
           Button btn = (Button) event.getSource();
           view.getBrowseImgBtn().setDisable(false);
           view.getCreateBtn().setVisible(true);
           view.getCrudHolder().setVisible(false);
           view.getArticleAmountBox().setVisible(false);
           restartInputs();
           refreshViews();
           resetBorder();

           //restartInputsAfterCRUD();

        /*   ColorAdjust desaturate = new ColorAdjust();
           desaturate.setSaturation(-1);
           view.getTopCategoryIconBtn().getStyleClass().remove("selectedCat");
           view.getTopCategoryIconBtn().setEffect(desaturate);
           view.getBottomCategoryIconBtn().getStyleClass().remove("selectedCat");
           view.getBottomCategoryIconBtn().setEffect(desaturate);
           view.getShoesCategoryIconBtn().getStyleClass().remove("selectedCat");
           view.getShoesCategoryIconBtn().setEffect(desaturate);*/
            categoryStyle();
            btn.getStyleClass().add("selectedCat");

            if(btn == view.getTopCategoryIconBtn()){
                view.sizeHolder(Category.TOP);
                categoryBtnID = Category.TOP;

            }else if(btn == view.getBottomCategoryIconBtn()){
                view.sizeHolder(Category.BOTTOM);
                categoryBtnID = Category.BOTTOM;

            }else if(btn == view.getShoesCategoryIconBtn()){
                view.sizeHolder(Category.SHOES);
                categoryBtnID = Category.SHOES;
            }
        }
    }
    public int getTopPos() {
        return topPos;
    }
    public int getBottomPos() {
        return bottomPos;
    }
    public int getShoesPos() {
        return shoesPos;
    }
    public int getCurrTopID() {
        return currTopID;
    }
    public int getCurrBottomID() {
        return currBottomID;
    }
    public int getCurrShoesID() {
        return currShoesID;
    }
    public int getOutfitPos() {
        return outfitPos;
    }
    public Category getCategoryBtnID() {
        return categoryBtnID;
    }
    private Article getCurrArticle(int id){
      //  if()
        return model.getBy(id);
    }
    public int getCurrArticleID() {
        return currArticleID;
    }
    public int getOutfitPosLabel() {
        if (outfitPosLabel <= 0) {
            outfitPosLabel = 0;
        }
        return outfitPosLabel;
    }
    public int getTopPosLabel() {
        if (topPosLabel <= 0) {
            topPosLabel = 0;
        }
        return topPosLabel;
    }
    public int getBottomPosLabel() {
        if (bottomPosLabel <= 0) {
            bottomPosLabel = 0;
        }
        return bottomPosLabel;
    }
    public int getShoesPosLabel() {
        if (shoesPosLabel <= 0) {
            shoesPosLabel = 0;
        }
        return shoesPosLabel;
    }
}


