package view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.dto.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WardrobeView extends BorderPane {
    private final TextField imgSrc = new TextField();
    private TextField modelInput = new TextField();
    private TextField colourInput = new TextField();
    private TextField styleInput = new TextField();
    private TextField brandInput = new TextField();
    private final String HANG_IMG = "defaultImg/h3.png";
    private final String LEFT_ARROW_IMG = "icons/swipeleft.png";
    private final String RIGHT_ARROW_IMG = "icons/swiperight.png";
    private final String TOP_ICON = "icons/top-icon.png";
    private final String BOTTOM_ICON = "icons/bottom-icon.png";
    private final String SHOE_ICON = "icons/shoe-icon.png";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border;";
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    private Button browseImgBtn = new Button();
    private Button swipeLeftOutfitBtn = getSwipeIconBtn(LEFT_ARROW_IMG, 15);
    private Button swipeRightOutfitBtn = getSwipeIconBtn(RIGHT_ARROW_IMG, 15);
    private Button createBtn = getCreateIconBtn("icons/create.png");
    private Button saveBtn = getCrudIconBtn("icons/download.png");
    private Button searchBtn = getCrudIconBtn("icons/search.png");
    private Button removeBtn = getCrudIconBtn("icons/delete.png");
    private Button createOutfitBtn = getSwipeIconBtn("icons/create-outfit.png",35);
    private Button randomizeOutfitBtn = getSwipeIconBtn("icons/dice.png",35);
    private Button deleteOutfitBtn = getSwipeIconBtn("icons/delete-outfit.png", 35);
    private Button refreshOutfitBtn = getSwipeIconBtn("icons/reload.png", 35);
    private Button leftTopBtn = getSwipeIconBtn(LEFT_ARROW_IMG);
    private Button rightTopBtn = getSwipeIconBtn(RIGHT_ARROW_IMG);
    private Button leftBottomBtn = getSwipeIconBtn(LEFT_ARROW_IMG);
    private Button rightBottomBtn = getSwipeIconBtn(RIGHT_ARROW_IMG);
    private Button leftShoesBtn = getSwipeIconBtn(LEFT_ARROW_IMG);
    private Button rightShoesBtn = getSwipeIconBtn(RIGHT_ARROW_IMG);
    private final Button topCategoryIconBtn = getCatIconBtn(TOP_ICON);
    private final Button bottomCategoryIconBtn = getCatIconBtn(BOTTOM_ICON);
    private final Button shoesCategoryIconBtn = getCatIconBtn(SHOE_ICON);
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
    private ImageView topImgView = addDefImgToImageView();
    private Button topImgViewBtn = new Button("", topImgView);
    private ImageView bottomImgView = addDefImgToImageView();
    private Button bottomImgViewBtn = new Button("", bottomImgView);
    private ImageView shoesImgView = addDefImgToImageView();
    private Button shoesImgViewBtn = new Button("", shoesImgView);
    private ToggleGroup sizeToggleGroup = new ToggleGroup();
    private ToggleGroup statusToggleGroup = new ToggleGroup();
    private RadioButton[] sizeChoice = new RadioButton[5];
    private String[] status = {"Clean", "Dirty"};
    private RadioButton[] statusChoice = new RadioButton[status.length];
    private Label currPos = new Label();
    private Label amount = new Label();
    private HBox sizeHolder = new HBox(15);
    private HBox crudHolder = new HBox(15);
    private HBox articleAmountBox = new HBox();
    private HBox createBtnHolder = new HBox();
    private HBox outfitHolder = new HBox(10);
    //private Label amountLabel = new Label();

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
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox addBtnHolder = new HBox();
        addBtnHolder.setPrefSize(300, 100);
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
        HBox categoryHolder = new HBox(20);
        categoryHolder.setPrefSize(310, 80);
        categoryHolder.setAlignment(Pos.CENTER);
       // categoryHolder.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
       // categoryHolder.setPadding(new Insets(5));

        addBtnHolder.getChildren().addAll(browseImgBtn);
        addBtnHolder.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        topCategoryIconBtn.setId("topIconBtn");
        bottomCategoryIconBtn.setId("bottomIconBtn");
        shoesCategoryIconBtn.setId("shoesIconBtn");
        categoryHolder.getChildren().addAll(topCategoryIconBtn, bottomCategoryIconBtn, shoesCategoryIconBtn);

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
        Label l = new Label("What are we adding today?");
        l.setId("introTitle");
        imgSrc.setDisable(true);
        HBox colourBox = new HBox();

        gridPane.add(imgSrc, 0, 0, 2, 1);
        gridPane.add(addBtnHolder, 0, 1, 2, 1);
        gridPane.add(l, 0, 2,2,1);
        gridPane.add(categoryHolder, 0, 3, 2,1);
        gridPane.add(new Label("Gender "), 0, 5);
        gridPane.add(new Label("Model "), 1, 5);
        gridPane.add(genderCB, 0, 6);
        gridPane.add(modelCB, 1, 6);
        gridPane.add(new Label("Colour "), 0, 7);
        gridPane.add(colourCB, 0, 8);
        gridPane.add(new Label("Theme "), 1, 9);
        gridPane.add(styleCB, 1, 10);
        gridPane.add(new Label("Brand "), 0, 9);
        gridPane.add(brandCB, 0, 10);
        gridPane.add(new Label("Size "), 0, 12);

        sizeHolder.setAlignment(Pos.CENTER);
        sizeHolder.setPadding(new Insets(5, 0, 2, 0));

        gridPane.add(sizeHolder, 0, 13, 2, 1);
        HBox statusHolder = new HBox(15);
        statusHolder.setPadding(new Insets(7, 0, 0, 0));

        statusHolder.getChildren().add(new Label("Status "));
        for (int i = 0; i < status.length; i++) {
            statusChoice[i] = new RadioButton(status[i]);
            statusHolder.getChildren().addAll(statusChoice[i]);
            statusChoice[i].setToggleGroup(statusToggleGroup);
        }

        HBox createBtnHolder = new HBox();
        createBtn.setId("createBtn");
        createBtn.setContentDisplay(ContentDisplay.TOP);
        createBtn.setText("Add article");
       // createBtnHolder.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        createBtnHolder.setPadding(new Insets(40, 0, 0, 0));
        createBtnHolder.setAlignment(Pos.CENTER);
        createBtnHolder.setMinHeight(100);
        createBtnHolder.getChildren().add(createBtn);
      //  createBtn.setDisable(true);

        gridPane.add(statusHolder, 0, 14, 2, 1);

        crudHolder.setMinWidth(280);
        crudHolder.setAlignment(Pos.BOTTOM_CENTER);
       // crudHolder.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

        crudHolder.setPadding(new Insets(0, 0, 0, 0));
        saveBtn.setId("saveBtn");
        searchBtn.setId("searchBtn");
        removeBtn.setId("removeBtn");

        crudHolder.getChildren().addAll(saveBtn, removeBtn);
        gridPane.add(createBtnHolder, 0, 15, 2, 1);

        gridPane.add(crudHolder, 0, 16, 2, 1);

        return gridPane;
    }

    private GridPane rightTop() {
        GridPane gp = new GridPane();
        gp.setHgap(0);
        gp.setVgap(0);
       // gp.setStyle("-fx-border--style: solid inside;" + "-fx-border-color: lightgray;");
        gp.setAlignment(Pos.CENTER);
        // gp.setGridLinesVisible(true);
        gp.setPrefWidth(360);
        gp.setPadding(new Insets(40, 0, 1, 0));
       // gp.setBackground(new Background(new BackgroundFill(Color.web("#e6eaef"), CornerRadii.EMPTY, Insets.EMPTY)));
        String path = "defaultImg/wardrobe.jpg";
        BufferedImage imagePath = null;
        try {
            imagePath = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image image = SwingFXUtils.toFXImage(imagePath, null);
        BackgroundImage bgImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        gp.setBackground(new Background(bgImage));
        HBox topMenuBox = new HBox(8);
       // topMenuBox.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        topMenuBox.setMinHeight(20);
        topMenuBox.getChildren().addAll(categoryCB2, themeCB);
        topMenuBox.setAlignment(Pos.CENTER);
        categoryCB2.setMinWidth(140);
        categoryCB2.setPromptText("Category");
        themeCB.setMinWidth(140);
        themeCB.setPromptText("Theme");

        HBox top3 = new HBox(5);
        top3.setPrefSize(200, 200);
       // top3.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        top3.setPadding(new Insets(10, 0, 1, 0));
        top3.setAlignment(Pos.CENTER);

        topImgView.setId("topImgView");
        bottomImgView.setId("bottomImgView");
        shoesImgView.setId("shoesImgView");
        topImgViewBtn.setId("topImgViewBtn");
       // topImgViewBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, new Insets(1))));
       // topImgViewBtn.hov
        leftTopBtn.setId("leftTopBtn");
        rightTopBtn.setId("rightTopBtn");
        topImgViewBtn.setPrefSize(200,200);
        topImgViewBtn.setMinWidth(200);
        topImgViewBtn.setMinHeight(200);
        top3.getChildren().addAll(leftTopBtn, topImgViewBtn, rightTopBtn);

        HBox center = new HBox(5);
        center.setPrefSize(200, 200);
        center.setPadding(new Insets(1, 0, 1, 0));
        center.setAlignment(Pos.CENTER);

        leftBottomBtn.setId("leftBottomBtn");
        rightBottomBtn.setId("rightBottomBtn");
        bottomImgViewBtn.setId("bottomImgViewBtn");
        bottomImgViewBtn.setMinWidth(200);
        bottomImgViewBtn.setMinHeight(200);
       // bottomImgViewBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        center.getChildren().addAll(leftBottomBtn, bottomImgViewBtn, rightBottomBtn);

        HBox bottom = new HBox(5);
        bottom.setPrefSize(200, 200);
        bottom.setPadding(new Insets(1, 0, 1, 0));
        //bottom.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        bottom.setAlignment(Pos.CENTER);

        leftShoesBtn.setId("leftShoesBtn");
        rightShoesBtn.setId("rightShoesBtn");
        shoesImgViewBtn.setId("shoesImgViewBtn");
        shoesImgViewBtn.setPrefSize(200, 200);
        shoesImgViewBtn.setMinWidth(200);
        shoesImgViewBtn.setMinHeight(200);
        // shoesImgViewBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        bottom.getChildren().addAll(leftShoesBtn, shoesImgViewBtn, rightShoesBtn);

        HBox bottom2 = new HBox(5);
        swipeLeftOutfitBtn.setId("scroll_left_btn");
        swipeRightOutfitBtn.setId("scroll_right_btn");
        currPos.setId("currPos");
        bottom2.setPadding(new Insets(0, 0, 20, 0));
       // bottom2.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
        bottom2.setAlignment(Pos.CENTER);
        bottom2.getChildren().addAll(swipeLeftOutfitBtn, currPos, amount, swipeRightOutfitBtn);
       // gp.add(labelBox, 0, 0, 2, 1);
        gp.add(topMenuBox, 0, 0, 2, 1);

        gp.add(top3, 0, 1, 2, 1);
        gp.add(center, 0, 2, 2, 1);
        gp.add(bottom, 0, 3, 2, 1);
        gp.add(bottom2, 0, 4, 2, 1);

        createOutfitBtn.setId("createOutfitBtn");
        deleteOutfitBtn.setId("deleteOutfitBtn");
        refreshOutfitBtn.setId("refreshOutfitBtn");
        randomizeOutfitBtn.setId("randomizeOutfitBtn");

        outfitHolder.getChildren().addAll(createOutfitBtn, deleteOutfitBtn, refreshOutfitBtn, randomizeOutfitBtn);
        outfitHolder.setAlignment(Pos.CENTER);
        outfitHolder.setPadding(new Insets(0, 0, 0, 0));
        gp.add(outfitHolder, 0, 6, 2, 1);
        outfitHolder.setVisible(false);

        return gp;
    }
    public void leftSizeHolder() {

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
    public ImageView addImgToImageView(String stringPath) {

        ImageView imageView = null;

            try {
                BufferedImage imagePath = ImageIO.read(new File(stringPath));
                Image image = SwingFXUtils.toFXImage(imagePath, null);
                imageView = new ImageView(image);
                imageView.setFitWidth(200);
                imageView.fitHeightProperty();
                imageView.setEffect(new DropShadow(1, Color.GRAY));


            } catch (IOException e) {
                Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e);
            }
        return imageView;
    }
    public ImageView addTestingImageView(Image img) {
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.fitHeightProperty();
        imageView.setEffect(new DropShadow(1, Color.GRAY));

        return imageView;
    }
    public ImageView addDefImgToImageView() {

        ImageView imageView = null;

        try {
            BufferedImage imagePath = ImageIO.read(new File("icons/click.png"));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            imageView.setPreserveRatio(true);
            imageView.fitHeightProperty();
            imageView.setEffect(new DropShadow(1, Color.GRAY));

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
    private Button getCatIconBtn(String stringPath){
        Button button = null;
        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(55);
            imageView.setSmooth(true);
            imageView.setPreserveRatio(true);
            button = new Button(null, imageView);

        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e);
        }
        return button;
    }

    private Button getCrudIconBtn(String stringPath) {
        Button button = null;
        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(40);
            imageView.setPreserveRatio(true);
            button = new Button(null, imageView);
            button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e);
        }
        return button;
    }

    private Button getCreateIconBtn(String stringPath) {
        Button button = null;
        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setPreserveRatio(true);
            button = new Button(null, imageView);
            button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e);
        }
        return button;
    }

    private Button getSwipeIconBtn(String stringPath) {
        Button button = null;
        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setPreserveRatio(true);
            button = new Button(null, imageView);
            button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e);
        }
        return button;
    }
    private Button getSwipeIconBtn(String stringPath, int size) {
        Button button = null;
        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(size);
            imageView.setPreserveRatio(true);
            button = new Button(null, imageView);
            button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

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

    public void setTopImgView(ImageView topImgView) {
        this.topImgView = topImgView;
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
    public HBox getCrudHolder() {
        return crudHolder;
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
    public Button getRemoveBtn() {
        return removeBtn;
    }

    public void addCrudBtnListener(EventHandler eventHandler) {
        createBtn.setOnAction(eventHandler);
        createOutfitBtn.setOnAction(eventHandler);
        removeBtn.setOnAction(eventHandler);
        refreshOutfitBtn.setOnAction(eventHandler);
        deleteOutfitBtn.setOnAction(eventHandler);
        saveBtn.setOnAction(eventHandler);
    }
    public void randomizeOutfit(EventHandler eventHandler) {
        randomizeOutfitBtn.setOnAction(eventHandler);
    }

    public Button getRandomizeOutfitBtn() {
        return randomizeOutfitBtn;
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

    public Label getPosLabel() {
        return currPos;
    }

    public Label getAmountLabel() {
        return amount;
    }

    public Button getTopCategoryIconBtn() {
        return topCategoryIconBtn;
    }

    public Button getBottomCategoryIconBtn() {
        return bottomCategoryIconBtn;
    }

    public Button getShoesCategoryIconBtn() {
        return shoesCategoryIconBtn;
    }

    public void categoryHandler(EventHandler eventHandler){
        topCategoryIconBtn.setOnAction(eventHandler);
        bottomCategoryIconBtn.setOnAction(eventHandler);
        shoesCategoryIconBtn.setOnAction(eventHandler);
    }

    public HBox getArticleAmountBox() {
        return articleAmountBox;
    }


    public HBox getCreateBtnHolder() {
        return createBtnHolder;
    }

    public HBox getOutfitHolder() {
        return outfitHolder;
    }

    public Button getRefreshOutfitBtn() {
        return refreshOutfitBtn;
    }

    public Button getDeleteOutfitBtn() {
        return deleteOutfitBtn;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }
}


