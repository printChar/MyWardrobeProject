package view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.dto.*;

import javax.imageio.ImageIO;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WardrobeView extends BorderPane {
    private final TextField imgSrc = new TextField();
    private TextField modelInput = new TextField();
    private TextField colourInput = new TextField();
    private TextField styleInput = new TextField();
    private TextField brandInput = new TextField();
    int num = -1;
    private final String TOP_IMG = "defaultImg/tshirt-vector.png";
    private final String SKIRT_IMG = "defaultImg/skirt.jpeg";
    private final String BOTTOM_IMG = "defaultImg/pants-vector.png";
    private final String SHOES_IMG = "defaultImg/shoe-vector.png";
    private final String HANG_IMG = "defaultImg/h3.png";
    private final String LEFT_ARROW_IMG = "icons/icons8-back-50.png";
    private final String RIGHT_ARROW_IMG = "icons/icons8-forward-50.png";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border;";
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    private Button browseImgBtn = new Button("Add article");
    private Button swipeLeftOutfitBtn = getIconBtn(LEFT_ARROW_IMG, 20);
    private Button swipeRightOutfitBtn = getIconBtn(RIGHT_ARROW_IMG, 20);
    private Button createBtn = new Button("Create");
    private Button saveBtn = new Button("Save");
    private Button searchBtn = new Button("Search");
    private Button removeBtn = new Button("Delete");
    private Button createOutfitBtn = new Button("Skapa outfit");
    private Button leftTopBtn = getIconBtn(LEFT_ARROW_IMG, 50);
    private Button rightTopBtn = getIconBtn(RIGHT_ARROW_IMG, 50);
    private Button leftBottomBtn = getIconBtn(LEFT_ARROW_IMG, 50);
    private Button rightBottomBtn = getIconBtn(RIGHT_ARROW_IMG, 50);
    private Button leftShoesBtn = getIconBtn(LEFT_ARROW_IMG, 50);
    private Button rightShoesBtn = getIconBtn(RIGHT_ARROW_IMG, 50);
    private ObservableList<Category> categories = FXCollections.observableArrayList(Category.values());
    private ObservableList<Category> categories2 = FXCollections.observableArrayList(Category.values());
    private ObservableList<Size> clothSize = FXCollections.observableArrayList(Size.XS, Size.S, Size.M, Size.L, Size.XL);
    private ObservableList<Size> shoeSize = FXCollections.observableArrayList(Size.S, Size.SM, Size.M, Size.ML, Size.L);
    private ComboBox<Category>  categoryCB = new ComboBox();
    private ComboBox<Category>  categoryCB2 = new ComboBox();
    private ComboBox<Style> themeCB = new ComboBox<>();
    private ComboBox<Gender> genderCB = new ComboBox<>();
    private ComboBox colourCB = new ComboBox();
    private ComboBox<Model> modelCB = new ComboBox<>();
    private ComboBox brandCB = new ComboBox();
    private ComboBox styleCB = new ComboBox();
    private ImageView topImgView = addDefaultImgToImageView(TOP_IMG);
    private Button topImgViewBtn = new Button("", topImgView);
    private ImageView bottomImgView = addDefaultImgToImageView(BOTTOM_IMG);
    private Button bottomImgViewBtn = new Button("", bottomImgView);
    private ImageView shoesImgView = addDefaultImgToImageView(SHOES_IMG);
    private Button shoesImgViewBtn = new Button("", shoesImgView);
    private ToggleGroup sizeToggleGroup = new ToggleGroup();
    private ToggleGroup statusToggleGroup = new ToggleGroup();
    private RadioButton[] sizeChoice = new RadioButton[5];
    private String[] status = {"Clean", "Dirty"};
    private RadioButton[] statusChoice = new RadioButton[status.length];
    private Label positionNum = new Label(" 1 / 2 ");
    private HBox sizeHolder = new HBox(15);

    public WardrobeView() {
        setLeft(inputView());
        setRight(rightTop());
    }

    private GridPane inputView() {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(145);
            gridPane.getColumnConstraints().add(column);
        }
        gridPane.setHgap(0);
        gridPane.setVgap(8);
        gridPane.setMinWidth(320);
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        // gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(10, 10, 10, 15));
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox addBtnHolder = new HBox();
        addBtnHolder.setPrefSize(300, 100);
        // addBtnHolder.fillHeightProperty();
        addBtnHolder.setAlignment(Pos.BASELINE_CENTER);
        addBtnHolder.setPadding(new Insets(0, 1, 5, 10));
        ImageView img = addImgToImageView(HANG_IMG, 150);
        browseImgBtn.setStyle(IDLE_BUTTON_STYLE);
        browseImgBtn.setOnMouseEntered(e -> browseImgBtn.setStyle(HOVERED_BUTTON_STYLE));
        browseImgBtn.setOnMouseExited(e -> browseImgBtn.setStyle(IDLE_BUTTON_STYLE));
        browseImgBtn.setGraphic(img);
        browseImgBtn.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        browseImgBtn.setContentDisplay(ContentDisplay.TOP);
        browseImgBtn.setDisable(true);

        addBtnHolder.getChildren().addAll(browseImgBtn);
        addBtnHolder.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        categoryCB.setPrefWidth(140);
        genderCB.setPrefWidth(140);
        modelInput.setMaxWidth(140);
        colourInput.setMaxWidth(140);
        modelCB.setPrefWidth(140);
        colourCB.setPrefWidth(140);
        styleInput.setMaxWidth(140);
        styleCB.setPrefWidth(140);
        brandInput.setMaxWidth(140);
        brandCB.setPrefWidth(140);
        genderCB.getItems().setAll(Gender.values());

        imgSrc.setDisable(true);
        gridPane.add(imgSrc, 0, 0, 2, 1);
        gridPane.add(addBtnHolder, 0, 1, 2, 1);
        gridPane.add(new Label("Category "), 0, 2);
        gridPane.add(categoryCB, 0, 3);
        gridPane.add(new Label("Sex "), 1, 2);
        gridPane.add(genderCB, 1, 3);
        gridPane.add(new Label("Model "), 0, 4);
        gridPane.add(modelInput, 0, 5);
        gridPane.add(modelCB, 1, 5);
        gridPane.add(new Label("Colour "), 0, 6);
        gridPane.add(colourInput, 0, 7);
        gridPane.add(colourCB, 1, 7);
        gridPane.add(new Label("Theme "), 0, 8);
        gridPane.add(styleInput, 0, 9);
        gridPane.add(styleCB, 1, 9);
        gridPane.add(new Label("Brand "), 0, 10);
        gridPane.add(brandInput, 0, 11);
        gridPane.add(brandCB, 1, 11);
        gridPane.add(new Label("Size "), 0, 12);

        sizeHolder.setAlignment(Pos.CENTER);
        sizeHolder.setPadding(new Insets(5, 0, 2, 0));

        gridPane.add(sizeHolder, 0, 13, 2, 1);
        HBox statusHolder = new HBox(15);
        statusHolder.setPadding(new Insets(5, 0, 0, 0));

        statusHolder.getChildren().add(new Label("Status "));
        for (int i = 0; i < status.length; i++) {
            statusChoice[i] = new RadioButton(status[i]);
            statusHolder.getChildren().addAll(statusChoice[i]);
            statusChoice[i].setToggleGroup(statusToggleGroup);
        }
        gridPane.add(statusHolder, 0, 14, 2, 1);

        HBox crudHolder = new HBox(10);
        crudHolder.setAlignment(Pos.CENTER);
        crudHolder.setPadding(new Insets(39, 0, 0, 0));
        crudHolder.getChildren().addAll(createBtn, saveBtn, searchBtn, removeBtn);
        gridPane.add(crudHolder, 0, 15, 2, 1);

        return gridPane;
    }

    private GridPane rightTop() {
        GridPane gp = new GridPane();
        gp.setHgap(5);
        gp.setVgap(0);
        gp.setStyle("-fx-border--style: solid inside;" + "-fx-border-color: lightgray;");
        gp.setAlignment(Pos.CENTER);
        // gp.setGridLinesVisible(true);
        // gp.setMinWidth(200);
        gp.setPrefWidth(320);
        gp.setPadding(new Insets(5, 5, 5, 5));
        gp.setBackground(new Background(new BackgroundFill(Color.web("#e6eaef"), CornerRadii.EMPTY, Insets.EMPTY)));
        categoryCB2.setMinWidth(140);
        themeCB.setMinWidth(140);

        HBox top3 = new HBox(5);
        top3.setPrefSize(200, 200);
        //  top3.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        top3.setPadding(new Insets(7, 0, 0, 0));
        top3.setAlignment(Pos.CENTER);

        topImgViewBtn.setId("topImgViewBtn");
        topImgViewBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
       // topImgViewBtn.hov
        leftTopBtn.setId("leftTopBtn");
        rightTopBtn.setId("rightTopBtn");
        top3.getChildren().addAll(leftTopBtn, topImgViewBtn, rightTopBtn);

        HBox center = new HBox(5);
        center.setPrefSize(200, 200);
        center.setPadding(new Insets(0, 0, 0, 0));
        center.setAlignment(Pos.CENTER);
       // center.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        leftBottomBtn.setId("leftBottomBtn");
        rightBottomBtn.setId("rightBottomBtn");
        bottomImgViewBtn.setId("bottomImgViewBtn");
        bottomImgViewBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        center.getChildren().addAll(leftBottomBtn, bottomImgViewBtn, rightBottomBtn);

        HBox bottom = new HBox(5);
        bottom.setPrefSize(200, 200);
        bottom.setPadding(new Insets(0, 0, 0, 0));
        //bottom.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        bottom.setAlignment(Pos.CENTER);

        leftShoesBtn.setId("leftShoesBtn");
        rightShoesBtn.setId("rightShoesBtn");
        shoesImgViewBtn.setId("shoesImgViewBtn");
        shoesImgViewBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        bottom.getChildren().addAll(leftShoesBtn, shoesImgViewBtn, rightShoesBtn);

        HBox bottom2 = new HBox(5);
        swipeLeftOutfitBtn.setId("scroll_left_btn");
        swipeRightOutfitBtn.setId("scroll_right_btn");
        //bottom2.setPrefSize(100);
        bottom2.setPadding(new Insets(10, 0, 0, 0));
       //  bottom2.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        bottom2.setAlignment(Pos.CENTER);
        bottom2.getChildren().addAll(swipeLeftOutfitBtn, positionNum, swipeRightOutfitBtn);
        gp.add(new Label("Category"), 0, 0);
        gp.add(new Label("Theme"), 1, 0);
        gp.add(categoryCB2, 0, 1);
        gp.add(themeCB, 1, 1);

        gp.add(top3, 0, 2, 2, 1);
        gp.add(center, 0, 3, 2, 1);
        gp.add(bottom, 0, 4, 2, 1);
        gp.add(bottom2, 0, 5, 2, 1);

        HBox addOutfitHolder = new HBox();
        addOutfitHolder.getChildren().add(createOutfitBtn);
        addOutfitHolder.setAlignment(Pos.CENTER);
        addOutfitHolder.setPadding(new Insets(5, 0, 0, 0));
        gp.add(addOutfitHolder, 0, 6, 2, 1);

        return gp;
    }

    public void setSizeHolder() {

        if (sizeHolder != null) {
            sizeHolder.getChildren().clear();
        }

            if (((Category.SHOES == getCategoryCB().getSelectionModel().getSelectedItem())
                && (Category.SHOES == getCategoryCB2().getSelectionModel().getSelectedItem()))
                || ((Category.SHOES == getCategoryCB().getSelectionModel().getSelectedItem())
                || (Category.SHOES == getCategoryCB2().getSelectionModel().getSelectedItem()))) {

            for (int i = 0; i < sizeChoice.length; i++) {
                Size s = Size.valueOf(shoeSize.get(i).toString());
                sizeChoice[i] = new RadioButton(String.valueOf(s.toIntValue()));
                sizeChoice[i].setToggleGroup(sizeToggleGroup);
                sizeHolder.getChildren().addAll(sizeChoice[i]);
            }

        } else {
            for (int i = 0; i < sizeChoice.length; i++) {
                Size s = clothSize.get(i);
                sizeChoice[i] = new RadioButton(s.name());
                sizeChoice[i].setToggleGroup(sizeToggleGroup);
                sizeHolder.getChildren().addAll(sizeChoice[i]);
            }
        }
    }
    public void leftSizeHolder() {
        num++;
        if (sizeHolder != null) {
            sizeHolder.getChildren().clear();
        }
          getCategoryCB2().getSelectionModel().clearSelection();

        if (Category.SHOES == getCategoryCB().getSelectionModel().getSelectedItem()) {
            for (int i = 0; i < sizeChoice.length; i++) {
                Size s = Size.valueOf(shoeSize.get(i).toString());
                sizeChoice[i] = new RadioButton(String.valueOf(s.toIntValue()));
                sizeChoice[i].setToggleGroup(sizeToggleGroup);
                sizeHolder.getChildren().addAll(sizeChoice[i]);
            }

        } else {
            for (int i = 0; i < sizeChoice.length; i++) {
                Size s = clothSize.get(i);
                sizeChoice[i] = new RadioButton(s.name());
                sizeChoice[i].setToggleGroup(sizeToggleGroup);
                sizeHolder.getChildren().addAll(sizeChoice[i]);
            }
        }
    }
    public void rightSizeHolder() {
        num++;
        if (sizeHolder != null) {
            sizeHolder.getChildren().clear();
        }

        if (Category.BOTTOM == getCategoryCB2().getSelectionModel().getSelectedItem() || Category.TOP == getCategoryCB2().getSelectionModel().getSelectedItem()) {
            for (int i = 0; i < sizeChoice.length; i++) {
                Size s = clothSize.get(i);
                sizeChoice[i] = new RadioButton(s.name());
                sizeChoice[i].setToggleGroup(sizeToggleGroup);
                sizeHolder.getChildren().addAll(sizeChoice[i]);
            }

        } else if (Category.SHOES == getCategoryCB2().getSelectionModel().getSelectedItem()) {
            for (int i = 0; i < sizeChoice.length; i++) {
                Size s = Size.valueOf(shoeSize.get(i).toString());
                sizeChoice[i] = new RadioButton(String.valueOf(s.toIntValue()));
                sizeChoice[i].setToggleGroup(sizeToggleGroup);
                sizeHolder.getChildren().addAll(sizeChoice[i]);
            }
        }
    }
    public void sizeHolder(Category category) {
        if (sizeHolder != null) {
            sizeHolder.getChildren().clear();
        }

        switch (category){
            case TOP:
            case BOTTOM:
                for (int i = 0; i < sizeChoice.length; i++) {
                    Size s = clothSize.get(i);
                    sizeChoice[i] = new RadioButton(s.name());
                    sizeChoice[i].setToggleGroup(sizeToggleGroup);
                    sizeHolder.getChildren().addAll(sizeChoice[i]);
                }
                break;
            case SHOES:
                for (int i = 0; i < sizeChoice.length; i++) {
                    Size s = Size.valueOf(shoeSize.get(i).toString());
                    sizeChoice[i] = new RadioButton(String.valueOf(s.toIntValue()));
                    sizeChoice[i].setToggleGroup(sizeToggleGroup);
                    sizeHolder.getChildren().addAll(sizeChoice[i]);
                }
                break;
        }
    }

    public ImageView addImgToImageView(String stringPath, int width) {
        ImageView imageView = null;

        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(width);
            imageView.setSmooth(true);

        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e);
        }
        return imageView;
    }
    public ImageView addDefaultImgToImageView(String stringPath) {

        ImageView imageView = null;

        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            //
            imageView = new ImageView(image);
            if (image.getWidth() > 200) {
                imageView.setFitWidth(200);
                imageView.fitHeightProperty();
                imageView.setEffect(new DropShadow(1, Color.GRAY));
            } else
                imageView.fitHeightProperty();

            imageView.setPreserveRatio(true);

        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e);
        }
        return imageView;
    }
    public Image resetImageView(String stringPath) {
        Image img = null;
        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            img = SwingFXUtils.toFXImage(imagePath, null);
        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e);
        }
        return img;
    }
    private Button getIconBtn(String stringPath, int size) {
        Button button = null;
        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(size);
            imageView.setPreserveRatio(true);
            button = new Button(null, imageView);
            button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            button.setPadding(Insets.EMPTY);

        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e);
        }
        return button;
    }
    public Button getBrowseImgBtn() {
        return browseImgBtn;
    }
    public Button getLeftTopBtn() {
        return leftTopBtn;
    }
    public Button getRightTopBtn() {
        return rightTopBtn;
    }
    public Button getLeftBottomBtn() {
        return leftBottomBtn;
    }
    public Button getRightBottomBtn() {
        return rightBottomBtn;
    }
    public Button getLeftShoesBtn() {
        return leftShoesBtn;
    }
    public Button getRightShoesBtn() {
        return rightShoesBtn;
    }
    public ImageView getTopImgView() {
        return topImgView;
    }
    public ImageView getBottomImgView() {
        return bottomImgView;
    }
    public ImageView getShoesImgView() {
        return shoesImgView;
    }
    public TextField getImgSrcTxf() {
        return imgSrc;
    }
    public TextField getModelInput() {
        return modelInput;
    }
    public TextField getColourInput() {
        return colourInput;
    }
    public TextField getStyleInput() {
        return styleInput;
    }
    public TextField getBrandInput() {
        return brandInput;
    }
    public ComboBox getColourCB() {
        return colourCB;
    }
    public ComboBox getModelCB() {
        return modelCB;
    }
    public ComboBox getBrandCB() {
        return brandCB;
    }
    public ComboBox getStyleCB() {
        return styleCB;
    }
    public ComboBox<Category> getCategoryCB() {
        return categoryCB;
    }
    public ComboBox<Gender> getGenderCB() {
        return genderCB;
    }
    public ComboBox<Style> getThemeCB() {
        return themeCB;
    }
    public ComboBox<Category> getCategoryCB2() {
        return categoryCB2;
    }
    public Button getCreateBtn() {
        return createBtn;
    }
    public void addImageviewListener(EventHandler eventHandler){
        topImgViewBtn.setOnAction(eventHandler);
        bottomImgViewBtn.setOnAction(eventHandler);
        shoesImgViewBtn.setOnAction(eventHandler);
    }

    public void addSwipeBtnListener(EventHandler eventHandler){
        leftTopBtn.setOnAction(eventHandler);
        rightTopBtn.setOnAction(eventHandler);
        leftBottomBtn.setOnAction(eventHandler);
        rightBottomBtn.setOnAction(eventHandler);
        leftShoesBtn.setOnAction(eventHandler);
        rightShoesBtn.setOnAction(eventHandler);
        swipeLeftOutfitBtn.setOnAction(eventHandler);
        swipeRightOutfitBtn.setOnAction(eventHandler);
    }

    public void addArticleIMGListener(EventHandler eventHandler) {
        browseImgBtn.setOnAction(eventHandler);
    }
    public void addRightComboboxListener(EventHandler eventHandler) {
        categoryCB2.setOnAction(eventHandler);
        categoryCB.setOnAction(eventHandler);
        themeCB.setOnAction(eventHandler);
    }
    public void addLeftComboboxListener(EventHandler eventHandler) {

    }
    public void addCrudBtnListener(EventHandler eventHandler) {
        createBtn.setOnAction(eventHandler);
        createOutfitBtn.setOnAction(eventHandler);
    }

    public ToggleGroup getSizeToggleGroup() {
        return sizeToggleGroup;
    }

    public ToggleGroup getStatusToggleGroup() {
        return statusToggleGroup;
    }

    public Button getTopImgViewBtn() {
        return topImgViewBtn;
    }

    public Button getBottomImgViewBtn() {
        return bottomImgViewBtn;
    }

    public Button getShoesImgViewBtn() {
        return shoesImgViewBtn;
    }

    public Button getCreateOutfitBtn() {
        return createOutfitBtn;
    }

    public Button getSwipeLeftOutfitBtn() {
        return swipeLeftOutfitBtn;
    }

    public Button getSwipeRightOutfitBtn() {
        return swipeRightOutfitBtn;
    }
}


