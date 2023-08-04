package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.dto.*;
import service.*;
import view.WardrobeView;

import javax.swing.*;
import java.io.File;
import java.util.List;


public class ArticleController {
    private final String TOP_IMG = "defaultImg/tshirt-vector.png";
    private final String BOTTOM_IMG = "defaultImg/pants-vector.png";
    private final String SHOES_IMG = "defaultImg/shoe-vector.png";
    private int topPos = -1;
    private int currTopID;
    private int bottomPos = -1;
    private int currBottomID;
    private int shoesPos = -1;
    private int currShoesID;
    private int outfitPos = -1;


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
        //ComboBoxListener comboBoxListener = new ComboBoxListener();
        LeftComboBoxListener leftComboBoxListener = new LeftComboBoxListener();
        RightComboBoxListener rightComboBoxListener = new RightComboBoxListener();
        ImageViewListener imageViewListener = new ImageViewListener();
        SwipeButtonListener swipeButtonListener = new SwipeButtonListener();
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

        restartInputsAfterCRUD();
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
        view.getTopImgView().setImage(view.resetImageView(TOP_IMG));
        view.getBottomImgView().setImage(view.resetImageView(BOTTOM_IMG));
        view.getShoesImgView().setImage(view.resetImageView(SHOES_IMG));
        view.getBrowseImgBtn().setDisable(true);
        disableInputs(true);
       // Platform.runLater(() -> view.getCategoryCB2().getSelectionModel().clearSelection());
    }
    private void restartInputsAfterCRUD() {
        view.getCategoryCB().getSelectionModel().clearSelection();
        view.getImgSrcTxf().setText("");
        view.getCategoryCB2().getSelectionModel().clearSelection();
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
        view.getTopImgView().setImage(view.resetImageView(TOP_IMG));
        view.getBottomImgView().setImage(view.resetImageView(BOTTOM_IMG));
        view.getShoesImgView().setImage(view.resetImageView(SHOES_IMG));
        view.getBrowseImgBtn().setDisable(true);
        disableInputs(true);
    }
    private void disableInputs(boolean value) {
        view.getGenderCB().setDisable(value);
        view.getModelCB().setDisable(value);
        view.getColourCB().setDisable(value);
        view.getStyleCB().setDisable(value);
        view.getBrandCB().setDisable(value);

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

            view.getTopImgView().setImage(new Image(top.getPicSrc()));
            view.getBottomImgView().setImage(new Image(bottom.getPicSrc()));
            view.getShoesImgView().setImage(new Image(shoes.getPicSrc()));
            view.getStyleCB().getSelectionModel().select(outfit.getStyle() - 1);

    }
    private void showThisArticle(Article article) {
        Image img = new Image(article.getPicSrc());
        if (Category.TOP == article.getCategory()) {
            currTopID = article.getId();
            view.getTopImgView().setImage(img);
        } else if (Category.BOTTOM == article.getCategory()) {
            currBottomID = article.getId();
            view.getBottomImgView().setImage(img);
        } else if (Category.SHOES == article.getCategory()) {
            currShoesID = article.getId();
            view.getShoesImgView().setImage(img);
        }

        view.getGenderCB().getSelectionModel().select(article.getGender());
        view.getModelCB().getSelectionModel().select(article.getModel() - 1);
        view.getColourCB().getSelectionModel().select(article.getColour() - 1);
        view.getStyleCB().getSelectionModel().select(article.getStyle() - 1);
        view.getBrandCB().getSelectionModel().select(article.getBrand() - 1);
        setSelectedToggleOn(article);
        setSelectedToggleOn(article.isClean());

    }
    private class LeftComboBoxListener implements EventHandler {
        public LeftComboBoxListener() {
            view.addLeftComboboxListener(this);
        }
        @Override
        public void handle(Event event) {
            view.getBrowseImgBtn().setDisable(false);

        }
    }

    private class RightComboBoxListener implements EventHandler {
        public RightComboBoxListener() {
            view.addRightComboboxListener(this);
        }
      /*  @Override
        public void handle(Event event) {
            ComboBox<Category> cb = view.getCategoryCB();
            ComboBox<Category> cb2 = view.getCategoryCB2();


            if (cb == event.getSource()) {
                restartInputs();
                view.getSizeToggleGroup().getToggles().clear();
                String stringCat = cb.getSelectionModel().getSelectedItem().getCategory().toUpperCase();
                Category cat = Category.valueOf(stringCat);
                view.sizeHolder(cat);
                view.getBrowseImgBtn().setDisable(false);

            }else if (cb2 == event.getSource()) {
                view.getBrowseImgBtn().setDisable(true);
                disableInputs(false);
                view.getSizeToggleGroup().getToggles().clear();
                String stringCat = cb2.getSelectionModel().getSelectedItem().getCategory().toUpperCase();
                Category cat = Category.valueOf(stringCat);
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(stringCat));
                view.sizeHolder(cat);
                showThisArticle(articlesByCategoryList.get(0));
            }
        }
*/
        @Override
        public void handle(Event e) {
            ComboBox<Category> cb = view.getCategoryCB();
            ComboBox<Category> cb2 = view.getCategoryCB2();
            ComboBox<Style> cb3 = view.getThemeCB();

            if (cb == e.getSource()) {
                restartInputs();
                view.getSizeToggleGroup().getToggles().clear();
                String stringCat = cb.getSelectionModel().getSelectedItem().getCategory().toUpperCase();
                Category cat = Category.valueOf(stringCat);
                view.sizeHolder(cat);
                view.getBrowseImgBtn().setDisable(false);

            }else if (cb2 == e.getSource()) {
                view.getBrowseImgBtn().setDisable(true);
                disableInputs(false);
                view.getSizeToggleGroup().getToggles().clear();
                String stringCat = cb2.getSelectionModel().getSelectedItem().getCategory().toUpperCase();
                Category cat = Category.valueOf(stringCat);
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(stringCat));
                view.sizeHolder(cat);
                showThisArticle(articlesByCategoryList.get(0));
            }else if (cb3 == e.getSource()){
               // Style style = view.getThemeCB().getSelectionModel().getSelectedItem();
              //  ObservableList<Outfit> outfitList = FXCollections.observableArrayList(outfitService.findAll());
                System.out.println(outfitService.findAll().size());
                showOutfit(outfitService.findAll().get(0));
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

            if (articleBtn == view.getRightTopBtn()) {
              //  view.sizeHolder(Category.TOP);
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.TOP.getCategory()));
                topPos++;
                if (topPos > articlesByCategoryList.size() - 1) {
                    topPos = articlesByCategoryList.size() - 1;
                }
                showThisArticle(articlesByCategoryList.get(getTopPos()));

            } else if (articleBtn == view.getLeftTopBtn()) {
              //  view.sizeHolder(Category.TOP);
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.TOP.getCategory()));
                topPos--;
                if (getTopPos() <= 0) {
                    topPos = 0;
                }
                showThisArticle(articlesByCategoryList.get(getTopPos()));

            }else if (articleBtn == view.getLeftBottomBtn()) {
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.BOTTOM.getCategory()));
                bottomPos--;
                if (getBottomPos() <= 0) {
                    bottomPos = 0;
                }
               showThisArticle(articlesByCategoryList.get(getBottomPos()));

            } else if (articleBtn == view.getRightBottomBtn()) {
                ObservableList<Article> articlesByCategoryList  = FXCollections.observableArrayList(model.findBy(Category.BOTTOM.getCategory()));
                bottomPos++;
                if (bottomPos > articlesByCategoryList.size() - 1) {
                    bottomPos = articlesByCategoryList.size() - 1;
                }
                showThisArticle(articlesByCategoryList.get(getBottomPos()));

            } else if (articleBtn == view.getLeftShoesBtn()) {
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.SHOES.getCategory()));
                shoesPos--;
                if (getShoesPos() <= 0) {
                    shoesPos = 0;
                }
                showThisArticle(articlesByCategoryList.get(getShoesPos()));

            } else if (articleBtn == view.getRightShoesBtn()) {
                ObservableList<Article> articlesByCategoryList  = FXCollections.observableArrayList(model.findBy(Category.SHOES.getCategory()));
                System.out.println(articlesByCategoryList.size());
                shoesPos++;
                if (shoesPos > articlesByCategoryList.size() - 1) {
                    shoesPos = articlesByCategoryList.size() - 1;
                }
                showThisArticle(articlesByCategoryList.get(getShoesPos()));
            }else if(articleBtn == view.getSwipeLeftOutfitBtn()){
                List<Outfit> outfitsList  = outfitService.findAll();
                System.out.println(outfitsList.size());
                outfitPos--;
                if (getOutfitPos() <= 0) {
                    outfitPos = 0;
                }
                showOutfit(outfitsList.get(getOutfitPos()));

            }else if(articleBtn == view.getSwipeRightOutfitBtn()){
                List<Outfit> outfitsList = outfitService.findAll();
                System.out.println(outfitsList.size());
                outfitPos++;
                if (outfitPos > outfitsList.size() - 1) {
                    outfitPos = outfitsList.size() - 1;
                }
                showOutfit(outfitsList.get(getOutfitPos()));
            }

        }
    }
    private class ImageViewListener implements EventHandler {
        public ImageViewListener() {
            view.addImageviewListener(this);
        }
        @Override
        public void handle(Event event) {
            Button articleBtn = (Button) event.getSource();

            if (articleBtn == view.getTopImgViewBtn()) {
                view.getCategoryCB2().getSelectionModel().select(Category.TOP);
                List<Article> articlesByCategoryList = model.findBy(Category.TOP.getCategory());
                System.out.println("Position: " + getTopPos());
                showThisArticle(articlesByCategoryList.get(getTopPos()));

            } else if (articleBtn == view.getBottomImgViewBtn()) {
                view.getCategoryCB2().getSelectionModel().select(Category.BOTTOM);
                List<Article> articlesByCategoryList = model.findBy(Category.BOTTOM.getCategory());
                System.out.println("Position? " + getBottomPos());
                showThisArticle(articlesByCategoryList.get(getBottomPos()));

            } else if (articleBtn == view.getShoesImgViewBtn()) {
                view.getCategoryCB2().getSelectionModel().select(Category.SHOES);
                List<Article> articlesByCategoryList = model.findBy(Category.SHOES.getCategory());
                System.out.println("Position? " + getShoesPos());
                showThisArticle(articlesByCategoryList.get(getShoesPos()));
            }
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

                if (btn == view.getCreateBtn()) {
                    if (Category.SHOES == view.getCategoryCB().getSelectionModel().getSelectedItem()) {
                        Colour colour = (Colour) view.getColourCB().getSelectionModel().getSelectedItem();
                        Model shoeModel = (Model) view.getModelCB().getSelectionModel().getSelectedItem();
                        Style style = (Style) view.getStyleCB().getSelectionModel().getSelectedItem();
                        Category category = view.getCategoryCB().getSelectionModel().getSelectedItem();
                        RadioButton chk = (RadioButton) view.getSizeToggleGroup().getSelectedToggle(); //size
                        RadioButton chk2 = (RadioButton) view.getStatusToggleGroup().getSelectedToggle();
                        Gender gender = view.getGenderCB().getSelectionModel().getSelectedItem();
                        Brand brand = (Brand) view.getBrandCB().getSelectionModel().getSelectedItem();
                        String img = view.getImgSrcTxf().getText();
                        Size size = Size.toTextValue(Integer.parseInt(chk.getText()));
                        if (chk2.getText().equals("Clean")) {
                            isClean = true;
                        }
                        model.create(new Article(colour.getId(), style.getId(), category, size, gender, shoeModel.getId(), brand.getId(), img, isClean));
                        restartInputsAfterCRUD();
                    } else {
                        Colour colour = (Colour) view.getColourCB().getSelectionModel().getSelectedItem();
                        Model clothModel = (Model) view.getModelCB().getSelectionModel().getSelectedItem();
                        Style style = (Style) view.getStyleCB().getSelectionModel().getSelectedItem();
                        Category category = view.getCategoryCB().getSelectionModel().getSelectedItem();
                        RadioButton chk = (RadioButton) view.getSizeToggleGroup().getSelectedToggle(); //size
                        RadioButton chk2 = (RadioButton) view.getStatusToggleGroup().getSelectedToggle();
                        Gender gender = view.getGenderCB().getSelectionModel().getSelectedItem();
                        Brand brand = (Brand) view.getBrandCB().getSelectionModel().getSelectedItem();
                        String img = view.getImgSrcTxf().getText();
                        Size size = Size.valueOf(chk.getText());
                        if (chk2.getText().equals("Clean")) {
                            isClean = true;
                        }
                        String msg = model.create(new Article(colour.getId(), style.getId(), category, size, gender, clothModel.getId(), brand.getId(), img, isClean));
                        System.out.println(msg);
                        restartInputsAfterCRUD();
                    }
                }else if (btn == view.getCreateOutfitBtn()) {
                    Article top = model.getBy(getCurrTopID());
                    Article bottom = model.getBy(getCurrBottomID());
                    Article shoes = model.getBy(getCurrShoesID());
                  /*  if (view.getThemeCB().getSelectionModel().isEmpty()) {
                        Alert alertType = new Alert(Alert.AlertType.INFORMATION);

                    } else {*/
                        Style style = view.getThemeCB().getSelectionModel().getSelectedItem();
                        outfitService.create(new Outfit(top.getId(), bottom.getId(), shoes.getId(), style.getId()));
                        restartInputsAfterCRUD();
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
}


