package translations;

public class EnglishLanguage implements TranslationBase {

    @Override
    public String toString(){
        return "English";
    }

    @Override
    public void refreshLanguage(TranslateController controller) {
        controller.appName.setValue("Langton's ant");
        controller.boardHeightSetting.setValue("Height of board:");
        controller.boardWidthSetting.setValue("Width of board:");
        controller.languageSetting.setValue("Language:");
        controller.speedSetting.setValue("Animation speed:");
        controller.cellSizeSetting.setValue("Cells size:");
        controller.colorSetting.setValue("Color of new ant:");
        controller.rules.setValue("Langton's ant is a two-dimensional universal Turing machine with a very simple set of rules but complex emergent behavior. " +
                            "It was invented by Chris Langton in 1986" +
                  "Rules: \n Squares on a plane are colored variously either black or white. " +
                  "We arbitrarily identify one square as the \"ant\". " +
                  "The ant can travel in any of the four cardinal directions at each step it takes. " +
                  "The \"ant\" moves according to the rules below:\n" +
                  "\n\n" +
                  "\t * \tAt a white square, turn 90° right, flip the color of the square, move forward one unit\n\n" +
                  "\t * \tAt a black square, turn 90° left, flip the color of the square, move forward one unit\n\n" +
                  "Langton's ant can also be described as a cellular automaton, where the grid is colored black or white and " +
                  "the \"ant\" square has one of eight different colors assigned to encode the combination " +
                  "of black/white state and the current direction of motion of the ant.");
    }
}
