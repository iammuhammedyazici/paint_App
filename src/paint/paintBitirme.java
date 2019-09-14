package paint;

import javafx.application.Application;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class paintBitirme extends Application {

    @Override
    public void start(Stage yeniPencerem) {

//_-------------------/
        ToggleButton kalemButon  =
                new ToggleButton("Kalem"); // Kalem butonumuzu oluşturduk...
        ToggleButton DortgenButon =
                new ToggleButton("Dörtgen");// Dörtgen çizme butonumuzu oluşturduk.
        ToggleButton daireButon =
                new ToggleButton("Daire");// Daire çizme butonumuzu oluşturduk.
        ToggleButton silgiButon =
                new ToggleButton("Silgi"); // Silgi butonumuzu oluşturduk.
///____________//
        Slider slider = new Slider(1, 80, 5);

        //-----YAZI KISIMLARI---
        Label isimKenar = new Label("Kenarlık Rengi"); //Kenarlık rengi butonumun açıklaması için bir label oluşturdum.
        Label isimDolgu = new Label("Dolgu Rengi");//Kenarlık rengi butonumun açıklaması için bir label oluşturdum.
        Label cizimBoyut = new Label("3.0 ");//Kalınlık butonunun kalınlık değerini gösteren bir label oluşturdum.
        Label a=new Label("by Yazici");
///---------///////

        ToggleButton araclar[]  = {
                kalemButon,
                DortgenButon,
                daireButon,
                silgiButon
        }; //araclar adlı dizimizi oluşturduk.
        ToggleGroup butonlar =
        new ToggleGroup(); // butonlar adlı bir nesne oluşturduk...

        for (ToggleButton arac : araclar) { //for döngüsü oluşturduk. Burada ki amaç butonları düzeltmek ve düzgün çalışmasını sağlamak.
            arac.setMinWidth(100); //butonların genişliklerini 100 px yaptık
            arac.setToggleGroup(butonlar);
        }

        ColorPicker renkKenar = new ColorPicker(Color.BLACK); //Kenar renginin başlangıç rengini siyah olarak tanımladım.
        ColorPicker renkDolgu = new ColorPicker(Color.TRANSPARENT); //Dolgu renginin başlangıç rengi transparan. çünkü siz öyle istemiştiniz. Transparent yerine Black yazarsak renk siyah olacaktır.

        //BUTONLAR İÇİN PENCERE------------
        VBox butonum = new VBox(15); //Bir pencere oluşturdum. Butonların içinde gözükeceği pencere
        butonum .getChildren().addAll(
                kalemButon,
                DortgenButon,
                daireButon,
                silgiButon,
                isimKenar,
                renkKenar,
                isimDolgu,
                renkDolgu,
                cizimBoyut,
                slider,
                a); //Butonların ekledim.

        butonum.setStyle("-fx-background-color: #1bab00"); //Pencerenin rengini tanımladım.
//---------------------------------------------------------------------////
        Canvas cizimEkran = new Canvas(1080, 790);  //Cizim yapacağım ekranı tanımladım.
        GraphicsContext grafik;
        grafik = cizimEkran.getGraphicsContext2D(); //grafik adlı nesneme getGraphicsContext2D fonksiyonunun özelliklerini atadım.

        Rectangle dortgen = new Rectangle(); // Dortgen çizdirmek için dorgen adında dortgen nesnesi oluşturdum.
        Circle daire = new Circle(); //Daire çizdirmek için daire adında daire nesnesi oluşturdum.

        //-------MOUSE PRESSED BAŞI-----
        cizimEkran.setOnMousePressed(e->{ //mouse basıldığı zaman ki işlemler
            if(kalemButon.isSelected()) { //eğer kalem butonu seçilmişse
                grafik.beginPath(); //ekranda olan noktayı al bu fonksiyon olmazsa ilk tıkladığım yerden itibaren çizeecektir.
                grafik.lineTo(e.getX(), e.getY()); // x ve ye koordinatlarını al.
            }
            else if(silgiButon.isSelected()) { //Eğer silgi butonum seçilmişsse
                double genişlik = grafik.getLineWidth();
                grafik.clearRect(e.getX() - genişlik/ 2, e.getY() - genişlik/ 2, genişlik, genişlik); //tanımlanan x, y ,x1 ve y1 koordinatlarında bir hareketi silme işlemi yapıyor
            }
            else if(DortgenButon.isSelected()) { //eğer dortgen butonum seçilmisse
                grafik.setFill(renkDolgu.getValue());//setFilline renkDolgu da seçili rengi ata
                grafik.setStroke(renkKenar.getValue()); //setStrokena renkKenar da seçili rengi ata
                dortgen.setX(e.getX()); //X koordinatını al
                dortgen.setY(e.getY());//Y koordinatını al
            }
            else if(daireButon.isSelected()) {//eğer daire butonumuz seçilmişşse
                grafik.setFill(renkDolgu.getValue());//setFilline renkDolgu da seçili rengi ata
                grafik.setStroke(renkKenar.getValue());  //setStrokena renkKenar da seçili rengi ata
                daire.setCenterX(e.getX()); // //X koordinatını al
                daire.setCenterY(e.getY());// Y koordinatını al
            }
        });//MOUSE PRESSED SONU

//------MOUSE DRAGGED BAŞI
        cizimEkran.setOnMouseDragged(e->{ //Mouse sürüklenmesi işlemi
            if(kalemButon.isSelected()) { //kalem butonum seçili ise
                grafik.lineTo(e.getX(), e.getY()); //X koordinatını ve Y koordinatını al
                grafik.stroke(); //belirtilen yerleri kenarlık renginde ve sürüklenen yerleri çiz
            }
            else if(silgiButon.isSelected()){//silgi butonum seçili ise
                double genislik= grafik.getLineWidth();
                grafik.clearRect(e.getX() - genislik/ 2, e.getY() - genislik/ 2, genislik, genislik); //tanımlanan noktaları sil
            }
        });//---MOUSE DRAGGED SONU

        //Mouse bırakılınca ki işlem
        cizimEkran.setOnMouseReleased(e->{
            if(DortgenButon.isSelected()) { //dortgen butonu secili ise
                dortgen.setHeight(Math.abs((e.getY() - dortgen.getY()))); //Yükseklik koordinatını al Y noktaları pencerenin ve dortgenin
                dortgen.setWidth(Math.abs((e.getX() - dortgen.getX()))); //Genişlik koordinatını al X noktalarının pencerenşn ve dortgenin
                grafik.fillRect(dortgen.getX(), dortgen.getY(), dortgen.getWidth(), dortgen.getHeight()); //tanımlanan noktaları rectangle(dörtgen) olarak çiz içinide dolgu renginde boya
                grafik.strokeRect(dortgen.getX(), dortgen.getY(), dortgen.getWidth(), dortgen.getHeight());//tanımlanan noktaları rectange(dörtgen) olarak çiz kenarları da kenarlık rengine boya
            }
            else if(daireButon.isSelected()) {  //
                daire.setRadius((Math.abs(e.getX() - daire.getCenterX()) + Math.abs(e.getY() - daire.getCenterY())) / 2); //dairenin koordinatlarını aldım kenarları oval olacak sekil de o yğzden radius kullandım
                grafik.fillOval(daire.getCenterX(), daire.getCenterY(), daire.getRadius(), daire.getRadius());//tanımlanan noktaları oval(daire) olarak çiz içinide dolgu renginde boya
                grafik.strokeOval(daire.getCenterX(), daire.getCenterY(), daire.getRadius(), daire.getRadius());////tanımlanan noktaları oval(daire) olarak çiz kenarını, kenar renginde boya
            }
        });
        // color picker
        renkKenar.setOnAction(e->{
            grafik.setStroke(renkKenar.getValue());  //Seçilen kenar rengi bizim çizeceğimiz şekillerin kenar rengi olacak
        });
        renkDolgu.setOnAction(e->{
            grafik.setFill(renkDolgu.getValue()); //Seçilen dolgu rengi bizim çizeceğimiz şekillerin dolgu rengi olacak
        });

        // slider
        slider.valueProperty().addListener(e->{
            double genislik= slider.getValue();
            if(kalemButon.isSelected()){  //kalem buton seçili ise
                grafik.setLineWidth(1);   //1.0 da kalem genişliği 1 px, 10 yaparsam 10 px olur.
                return;
            }
            grafik.setLineWidth(genislik);
        });

        BorderPane kutum = new BorderPane(); //kutum nesnesi oluşturdum
        kutum.setLeft(butonum ); //oluşturduğum kutum nesnesini sola yasladım. Butonlarımın oldugu pencere solda gözükecek.
        kutum.setCenter(cizimEkran); // canvas pencerem ise ordada olacak. Çizim yapılacak ekran
        Scene pencere = new Scene(kutum, 1000, 800); //pencere boyutunu ayarladım.

        yeniPencerem.setTitle("GRAFİK BİTİRME PAİNT UYGULAMASI");
        yeniPencerem.setScene(pencere); //oluşturdugum içerde ki pencerenin gözükmesi
        yeniPencerem.show(); //PENCERENİN GÖZÜKMESİ İÇİN
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}