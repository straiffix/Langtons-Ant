package translations;

public interface TranslationBase {
    String toString(); // nazwa języka
    void refreshLanguage(TranslateController controller);
}
