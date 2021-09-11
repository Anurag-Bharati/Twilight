package Manager;

public class IconManager {

    public static String getImage(String icon) {
        return switch (icon) {
            case "01d" -> "/main/resources/icons/01d.png";
            case "01n" -> "/main/resources/icons/01n.png";
            case "02d" -> "/main/resources/icons/02d.png";
            case "02n" -> "/main/resources/icons/02n.png";
            case "03d", "03n" -> "/main/resources/icons/03.png";
            case "04d", "04n" -> "/main/resources/icons/04.png";
            case "09d", "09n" -> "/main/resources/icons/09.png";
            case "10d" -> "/main/resources/icons/10d.png";
            case "10n" -> "/main/resources/icons/10n.png";
            case "11n", "11d" -> "/main/resources/icons/11.png";
            case "13d", "13n" -> "/main/resources/icons/13.png";
            case "50d" -> "/main/resources/icons/50d.png";
            case "50n" -> "/main/resources/icons/50n.png";
            default -> "main/resources/icons/03.png";
        };
    }

}