package translations;

public class PolishLanguage implements TranslationBase {

    @Override
    public String toString(){
        return "Polish";
    }

    @Override
    public void refreshLanguage(TranslateController controller) {
        controller.appName.setValue("Mrówka Langtona");
        controller.boardHeightSetting.setValue("Wysokość planszy:");
        controller.boardWidthSetting.setValue("Szerokość planszy:");
        controller.languageSetting.setValue("Język:");
        controller.speedSetting.setValue("Prędkość animacji:");
        controller.cellSizeSetting.setValue("Rozmiar komórek:");
        controller.colorSetting.setValue("Kolor nowej mrówki:");
        controller.rules.setValue("Mrówka Langtona – prosty automat komórkowy wymyślony i opisany przez Chrisa Langtona w 1986 roku.\n" +
                  "W każdym kroku wyróżniona jest jedna komórka nazywana \"mrówką\", która oprócz koloru ma określony także kierunek, w którym się porusza. Mrówka zachowuje się według następujących zasad:\n" +
                  "\n" +
                  "\t * \t\tjeśli znajduje się na polu białym to obraca się w lewo (o kąt prosty), zmienia kolor pola na czarny i przechodzi na następną komórkę;\n\n" +
                  "\t * \t\tjeśli znajduje się na polu czarnym to obraca się w prawo (o kąt prosty), zmienia kolor pola na biały i przechodzi na następną komórkę;\n\n" +
                  "\t * \t\tporusza się na nieskończonej planszy podzielonej na kwadratowe komórki (pola) w dwóch możliwych kolorach: czarnym i białym.\n\n");

    }
}
