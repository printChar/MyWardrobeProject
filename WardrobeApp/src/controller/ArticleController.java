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
    private WardrobeView view;
    private ArticleService model;
    private ModelService modelService = new ModelService();
    private ColourService colourService = new ColourService();
    private StyleService styleService = new StyleService();
    private BrandService brandService = new BrandService();

    public ArticleController(WardrobeView view, ArticleService model) {
        this.view = view;
        this.model = model;
        ArticleIMGHandler articleIMGHandler = new ArticleIMGHandler();
        ComboBoxListener comboBoxListener = new ComboBoxListener();
        ImageViewListener imageViewListener = new ImageViewListener();
        CrudBtnHandler crud = new CrudBtnHandler();
        updateView();
    }

    private void updateView() {
        List<Article> allArticles = model.findAll();
        view.setSizeHolder();
        retrieveModelsFromDatabase();
        retrieveColoursFromDatabase();
        retrieveStylesFromDatabase();
        retrieveBrandsFromDatabase();
       // restartInputs();
        restartInputsAfterCRUD();
    }

    private void restartInputs() {
        view.getImgSrcTxf().setText("");
        view.getGenderCB().getSelectionModel().clearSelection();
        view.getModelCB().getSelectionModel().clearSelection();
        view.getColourCB().getSelectionModel().clearSelection();
        view.getStyleCB().getSelectionModel().clearSelection();
        //view.getThemeCB().getSelectionModel().clearSelection();
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
    private void restartInputsAfterCRUD() {
        view.getCategoryCB().getSelectionModel().clearSelection();
        view.getImgSrcTxf().setText("");
        view.getCategoryCB2().getSelectionModel().clearSelection();
        view.getGenderCB().getSelectionModel().clearSelection();
        view.getModelCB().getSelectionModel().clearSelection();
        view.getColourCB().getSelectionModel().clearSelection();
        view.getStyleCB().getSelectionModel().clearSelection();
        //view.getThemeCB().getSelectionModel().clearSelection();
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

    private void setSelectedToggleOn(Size size) {

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

    private void showArticles(ObservableList<Article> articles) {
        // for (Article a : articles) {
        Image img = new Image(articles.get(0).getPicSrc());
        if (Category.TOP == articles.get(0).getCategory()) {
            view.getTopImgView().setImage(img);
        } else if (Category.BOTTOM == articles.get(0).getCategory()) {
            view.getBottomImgView().setImage(img);
        } else if (Category.SHOES == articles.get(0).getCategory()) {
            view.getShoesImgView().setImage(img);
        }
          /*  view.getGenderCB().getSelectionModel().select(articles.get(0).getGender());
            view.getModelCB().getSelectionModel().select(articles.get(0).getModel() - 1);
            view.getColourCB().getSelectionModel().select(articles.get(0).getColour() - 1);
            view.getStyleCB().getSelectionModel().select(articles.get(0).getStyle() - 1);
            view.getBrandCB().getSelectionModel().select(articles.get(0).getBrand() - 1);
            setSelectedToggleOn(articles.get(0).getSize());
            setSelectedToggleOn(articles.get(0).isClean());*/
        // }
        view.getGenderCB().getSelectionModel().select(articles.get(0).getGender());
        view.getModelCB().getSelectionModel().select(articles.get(0).getModel() - 1);
        view.getColourCB().getSelectionModel().select(articles.get(0).getColour() - 1);
        view.getStyleCB().getSelectionModel().select(articles.get(0).getStyle() - 1);
        view.getBrandCB().getSelectionModel().select(articles.get(0).getBrand() - 1);
        setSelectedToggleOn(articles.get(0).getSize());
        setSelectedToggleOn(articles.get(0).isClean());

    }

    private void showArticle(ObservableList<Article> articles) {


        for (Article a : articles) {
      /*  Image img = new Image(articles.get(0).getPicSrc());
        if (Category.TOP == articles.get(0).getCategory()) {
            view.getTopImgView().setImage(img);
        } else if (Category.BOTTOM == articles.get(0).getCategory()) {
            view.getBottomImgView().setImage(img);
        } else if (Category.SHOES == articles.get(0).getCategory()) {
            view.getShoesImgView().setImage(img);
        }
       */
            System.out.println(a.toString());
        }
          /*  view.getGenderCB().getSelectionModel().select(articles.get(0).getGender());
            view.getModelCB().getSelectionModel().select(articles.get(0).getModel() - 1);
            view.getColourCB().getSelectionModel().select(articles.get(0).getColour() - 1);
            view.getStyleCB().getSelectionModel().select(articles.get(0).getStyle() - 1);
            view.getBrandCB().getSelectionModel().select(articles.get(0).getBrand() - 1);
            setSelectedToggleOn(articles.get(0).getSize());
            setSelectedToggleOn(articles.get(0).isClean());*/
        // }
       /* view.getGenderCB().getSelectionModel().select(articles.get(0).getGender());
        view.getModelCB().getSelectionModel().select(articles.get(0).getModel() - 1);
        view.getColourCB().getSelectionModel().select(articles.get(0).getColour() - 1);
        view.getStyleCB().getSelectionModel().select(articles.get(0).getStyle() - 1);
        view.getBrandCB().getSelectionModel().select(articles.get(0).getBrand() - 1);
        setSelectedToggleOn(articles.get(0).getSize());
        setSelectedToggleOn(articles.get(0).isClean());*/

    }
    private class ImageViewListener implements EventHandler{

        public ImageViewListener() {
            view.addImageviewListener(this);
        }

        @Override
        public void handle(Event event) {
            Button articleBtn = (Button) event.getSource();

            if(articleBtn == view.getTopImgViewBtn()){
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.TOP.getCategory()));
                showArticle(articlesByCategoryList);

            } else if (articleBtn == view.getBottomImgViewBtn()) {
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.BOTTOM.getCategory()));
                showArticle(articlesByCategoryList);

            } else if (articleBtn == view.getShoesImgViewBtn()) {
                ObservableList<Article> articlesByCategoryList = FXCollections.observableArrayList(model.findBy(Category.SHOES.getCategory()));
                showArticle(articlesByCategoryList);
            }
        }
    }


    private class ComboBoxListener implements EventHandler {
        public ComboBoxListener() {
            view.addComboboxListener(this);
        }

        @Override
        public void handle(Event event) {
            ComboBox cb2 = view.getCategoryCB2();
            ComboBox cb = view.getCategoryCB();
            ObservableList<Article> articlesByCategoryList;

            if (cb == event.getSource()) {
                System.out.println("IF: CB2 is empty?: " + cb2.getSelectionModel().isEmpty());
                System.out.println(cb.getSelectionModel().selectedItemProperty().getValue());
                restartInputs();
                view.getSizeToggleGroup().getToggles().clear();
                view.setSizeHolder();
                view.getBrowseImgBtn().setDisable(false);

            } else if (cb2 == event.getSource()) {
                System.out.println("LAST ELSE IF: CB is empty?: " + cb.getSelectionModel().isEmpty());
                cb.getSelectionModel().clearSelection();
                view.getBrowseImgBtn().setDisable(true);
                disableInputs(false);
                view.getSizeToggleGroup().getToggles().clear();
                view.setSizeHolder();
                Category cat = (Category) cb2.getSelectionModel().getSelectedItem();
                articlesByCategoryList = FXCollections.observableArrayList(model.findBy(cat.getCategory()));
                showArticles(articlesByCategoryList);
       /*    } else if (cb2 == event.getSource() && Category.SHOES == cb2.getSelectionModel().getSelectedItem()){

         }*/
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
                    model.create(new Article(colour.getId(), style.getId(), category, size, gender, clothModel.getId(), brand.getId(), img, isClean));
                    restartInputsAfterCRUD();
                }

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
}

