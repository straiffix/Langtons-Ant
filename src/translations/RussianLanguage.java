package translations;

public class RussianLanguage implements TranslationBase {

          @Override
          public String toString(){
                    return "Russian";
          }

          @Override
          public void refreshLanguage(TranslateController controller) {
                    controller.appName.setValue("Муравей Лэнгтона");
                    controller.boardHeightSetting.setValue("Высота плоскости:");
                    controller.boardWidthSetting.setValue("Ширина плоскости:");
                    controller.languageSetting.setValue("Язык:");
                    controller.speedSetting.setValue("Скорость анимации:");
                    controller.cellSizeSetting.setValue("Размер клеток:");
                    controller.colorSetting.setValue("Цвет нового муравья:");
                    controller.rules.setValue("Муравей Лэнгтона — это двумерный клеточный автомат с очень простыми правилами, изобретенный Крисом Лэнгтоном.\n" +
                              "Рассмотрим бесконечную плоскость, разбитую на клетки, покрашенные некоторым образом в чёрный и белый цвет. " +
                              "Пусть в одной из клеток находится «муравей», который на каждом шаге может двигаться в одном из четырёх направлений в клетку, соседнюю по стороне. " +
                              "Муравей движется согласно следующим правилам:\n" +
                              "\n" +
                              "\t*\t\tНа чёрном квадрате — повернуть на 90° влево, изменить цвет квадрата на белый, сделать шаг вперед на следующую клетку.\n\n" +
                              "\t*\t\tНа белом квадрате — повернуть на 90° вправо, изменить цвет квадрата на чёрный, сделать шаг вперед на следующую клетку.\n");
          }
}
