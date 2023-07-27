package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.dto.Category;
import model.dto.Gender;
import model.dto.Size;

import javax.imageio.ImageIO;
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
    private TextField brandInput= new TextField();
    private final String TOP_IMG = "defaultImg/tshirt-vector.png";
    private final String SKIRT_IMG = "defaultImg/skirt.jpeg";
    private final String BOTTOM_IMG = "defaultImg/pants-vector.png";
    private final String SHOES_IMG = "defaultImg/shoe-vector.png";
    private final String HANG_IMG = "defaultImg/h3.png";
    private final String LEFT_ARROW_IMG = "icons/icons8-back-50.png";
    private final String RIGHT_ARROW_IMG = "icons/icons8-forward-50.png";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border;";
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    private Size[] sizes = {Size.XS, Size.S, Size.M, Size.L, Size.XL};
    private String[] status = {"Ren", "Tvätten"};
    private Button browseImgBtn = new Button("Lägg till artikel");
    private Button scroll_left_btn = getIconBtn(LEFT_ARROW_IMG);
    private Button scroll_right_btn = getIconBtn(RIGHT_ARROW_IMG);
    private Button  createBtn = new Button("Skapa");
    private Button saveBtn = new Button("Spara");
    private Button searchBtn = new Button("Sök");
    private Button removeBtn = new Button("Radera");
    private Button createOutfitBtn = new Button("Skapa outfit");
    private Button rightBtn = getIconBtn(RIGHT_ARROW_IMG);
    private Button leftBtn1 = getIconBtn(LEFT_ARROW_IMG);
    private Button rightBtn1 = getIconBtn(RIGHT_ARROW_IMG);
    private Button leftBtn2 = getIconBtn(LEFT_ARROW_IMG);
    private Button rightBtn2 = getIconBtn(RIGHT_ARROW_IMG);
    private Button leftBtn = getIconBtn(LEFT_ARROW_IMG);
    private ComboBox<Category> typeCB = new ComboBox<>(FXCollections.observableArrayList(Category.TOP, Category.BOTTOM, Category.SHOES));
    private ComboBox<Category> themeCB= new ComboBox<>(FXCollections.observableArrayList(Category.TOP, Category.BOTTOM, Category.SHOES));
    private ComboBox<Gender> genderCB = new ComboBox<>(FXCollections.observableArrayList(Gender.UNISEX, Gender.WOMEN, Gender.MEN));
    private ComboBox colourCB = new ComboBox();
    private ComboBox modelCB = new ComboBox();
    private ComboBox brandCB = new ComboBox();
    private ComboBox styleCB = new ComboBox();
    private ImageView topImgView = addDefaultImgToImageView(TOP_IMG);
    private ImageView bottomImgView = addDefaultImgToImageView(BOTTOM_IMG);
    private ImageView shoesImgView = addDefaultImgToImageView(SHOES_IMG);
    private RadioButton[] sizeChoice = new RadioButton[sizes.length];
    private RadioButton[] statusChoice = new RadioButton[status.length];
    private Label positionNum = new Label(" 1 / 2 ");

    public WardrobeView() {
        setLeft(inputView());
        setRight(rightTop());
    }
    private GridPane inputView(){
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(145);
            gridPane.getColumnConstraints().add(column);
        }

        typeCB.getItems().setAll(Category.values());
        genderCB.getItems().setAll(Gender.values());
        genderCB.setDisable(true);
        brandCB.setDisable(true);
        styleCB.setDisable(true);
        modelCB.setDisable(true);
        colourCB.setDisable(true);


        gridPane.setHgap(0);
        gridPane.setVgap(8);
        gridPane.setMinWidth(320);
        gridPane.setAlignment(Pos.BASELINE_CENTER);
       // gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(35,10,10,15));
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox addBtnHolder = new HBox();
        addBtnHolder.setPrefSize(300, 100);
       // addBtnHolder.fillHeightProperty();
        addBtnHolder.setAlignment(Pos.BASELINE_CENTER);
        addBtnHolder.setPadding(new Insets(0,1,5,10));
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

        typeCB.setPrefWidth(140);
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
        gridPane.add(imgSrc,0,0, 2,1);
        gridPane.add(addBtnHolder, 0,1, 2,1);
        gridPane.add(new Label("Klädesplagg "), 0,2);
        gridPane.add(typeCB, 0,3);
        gridPane.add(new Label("Sex "), 1,2);
        gridPane.add(genderCB, 1,3);
        gridPane.add(new Label("Modell "), 0, 4);
        gridPane.add(modelInput, 0, 5);
        gridPane.add(modelCB, 1, 5);
        gridPane.add(new Label("Färg "), 0, 6);
        gridPane.add(colourInput, 0, 7);
        gridPane.add(colourCB, 1, 7);
        gridPane.add(new Label("Tema "), 0, 8);
        gridPane.add(styleInput, 0, 9);
        gridPane.add(styleCB, 1, 9);
        gridPane.add(new Label("Märke "), 0, 10);
        gridPane.add(brandInput, 0, 11);
        gridPane.add(brandCB, 1, 11);
        gridPane.add(new Label("Storlek "), 0, 12);

        HBox sizeHolder = new HBox(15);
        sizeHolder.setAlignment(Pos.CENTER);
        ToggleGroup sizeToggleGroup = new ToggleGroup();
     //   GridPane.setColumnSpan(sizeHolder, 1);
        sizeHolder.setPadding(new Insets(0, 0, 2, 0));

        for (int i = 0; i < sizes.length; i++) {
            sizeChoice[i] = new RadioButton(sizes[i].name());
            sizeChoice[i].setToggleGroup(sizeToggleGroup);
            sizeHolder.getChildren().addAll(sizeChoice[i]);
        }

        gridPane.add(sizeHolder, 0, 13, 2, 1);
        HBox statusHolder = new HBox(15);
        statusHolder.setPadding(new Insets(5, 0, 0 ,0));
     //   GridPane.setColumnSpan(statusHolder, 1);
        ToggleGroup statusToggleGroup = new ToggleGroup();
        statusHolder.getChildren().add(new Label("Status "));
        for (int i = 0; i < status.length; i++) {
            statusChoice[i] = new RadioButton(status[i]);
            statusHolder.getChildren().addAll(statusChoice[i]);
            statusChoice[i].setToggleGroup(statusToggleGroup);
           /* if(i>= status.length){
                statusChoice[0].setSelected(true);
            }*/
        }
        gridPane.add(statusHolder, 0, 14, 2, 1);

        HBox crudHolder = new HBox(10);
        crudHolder.setAlignment(Pos.CENTER);
        crudHolder.setPadding(new Insets(39, 0, 0, 0));
        crudHolder.getChildren().addAll(createBtn, saveBtn, searchBtn, removeBtn);
        gridPane.add(crudHolder,0, 15, 2, 1);

        return gridPane;
    }

    private GridPane rightTop(){
        GridPane gp = new GridPane();
        gp.setHgap(5);
        gp.setVgap(0);
        gp.setStyle("-fx-border--style: solid inside;" + "-fx-border-color: lightgray;");
        gp.setAlignment(Pos.CENTER);
        // gp.setGridLinesVisible(true);
       // gp.setMinWidth(200);
        gp.setPrefWidth(310);
        gp.setPadding(new Insets(0,0,5,0));
        gp.setBackground(new Background(new BackgroundFill(Color.web("#e6eaef"), CornerRadii.EMPTY, Insets.EMPTY)));
        themeCB.setMinWidth(140);
        typeCB.setMinWidth(140);
        //types.setValue(clothType);

        HBox top3 = new HBox(5);
        top3.setPrefSize(100, 100);
      //  top3.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        top3.setPadding(new Insets(7, 0, 0, 0));
        top3.setAlignment(Pos.CENTER);

        top3.getChildren().addAll(leftBtn, topImgView, rightBtn);

        HBox center = new HBox(5);
        center.setPrefSize(100, 100);
        center.setPadding(new Insets(0, 0, 0, 0));
        center.setAlignment(Pos.CENTER);

        center.getChildren().addAll(leftBtn1, bottomImgView, rightBtn1);

        HBox bottom = new HBox(5);
        bottom.setPrefSize(100, 100);
        bottom.setPadding(new Insets(0, 0, 10, 0));
        bottom.setAlignment(Pos.CENTER);

        bottom.getChildren().addAll(leftBtn2, shoesImgView, rightBtn2);

        HBox bottom2 = new HBox(5);
        //bottom2.setPrefSize(100);
        bottom2.setPadding(new Insets(0, 0, 20, 0));
       // bottom2.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        bottom2.setAlignment(Pos.CENTER);
        bottom2.getChildren().addAll(scroll_left_btn, positionNum, scroll_right_btn);
        gp.add(new Label("Tema"), 0,0);
        gp.add(new Label("Plagg"), 1,0);
        gp.add(themeCB, 0,1);
        gp.add(new Label(""), 1,1);
        gp.add(top3, 0,2,2, 1);
        gp.add(center, 0,3,2, 1);
        gp.add(bottom, 0,4, 2, 1);
        gp.add(bottom2, 0,5, 2, 1);

        HBox addOutfitHolder = new HBox();
        addOutfitHolder.getChildren().add(createOutfitBtn);
        addOutfitHolder.setAlignment(Pos.CENTER);
        addOutfitHolder.setPadding(new Insets(1, 0, 0, 0));
        gp.add(addOutfitHolder,0, 6, 2, 1);

        return gp;
    }

    public ImageView addImgToImageView(String stringPath, int width){
        ImageView imageView = null;

        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(width);
            imageView.setSmooth(true);


        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e + " heeej");
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
                imageView.setEffect(new DropShadow(1, Color.GRAY));
            } else
                imageView.fitHeightProperty();

            imageView.setPreserveRatio(true);

        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e + " heeej");
        }
        return imageView;
    }
    private Button getIconBtn(String stringPath) {

        Button button = null;

        try {
            BufferedImage imagePath = ImageIO.read(new File(stringPath));
            Image image = SwingFXUtils.toFXImage(imagePath, null);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(20);
            imageView.setPreserveRatio(true);
            button = new Button(null, imageView);
            button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            button.setPadding(Insets.EMPTY);

        } catch (IOException e) {
            Logger.getLogger(WardrobeView.class.getName()).log(Level.SEVERE, null, e);
        }
        return button;
    }

    public void addArticleIMGListener(EventHandler eventHandler) {
        browseImgBtn.setOnAction(eventHandler);
    }

    public void addComboboxListener(EventHandler eventHandler) {
        typeCB.setOnAction(eventHandler);
    }


    public Button getBrowseImgBtn() {
        return browseImgBtn;
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

    public void setTopImgView(ImageView topImgView) {
        this.topImgView = topImgView;
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

    public void setColourCB(ComboBox colourCB) {
        this.colourCB = colourCB;
    }

    public ComboBox getModelCB() {
        return modelCB;
    }

    public void setModelCB(ComboBox modelCB) {
        this.modelCB = modelCB;
    }

    public ComboBox getBrandCB() {
        return brandCB;
    }

    public void setBrandCB(ComboBox brandCB) {
        this.brandCB = brandCB;
    }

    public ComboBox getStyleCB() {
        return styleCB;
    }

    public void setStyleCB(ComboBox styleCB) {
        this.styleCB = styleCB;
    }

    public ComboBox<Category> getTypeCB() {
        return typeCB;
    }

    public void setTypeCB(ComboBox<Category> typeCB) {
        this.typeCB = typeCB;
    }

    public ComboBox<Gender> getGenderCB() {
        return genderCB;
    }

    public void setGenderCB(ComboBox<Gender> genderCB) {
        this.genderCB = genderCB;
    }


}


