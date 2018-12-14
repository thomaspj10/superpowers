package ttdev.superpowers;

public class PowerUtil {

    public static String powersAsList(String prefix, String suffix, String delimiter) {
        StringBuilder builder = new StringBuilder(prefix);
        for (EnumPower power : EnumPower.values()) {
            builder.append(power.getId());
            builder.append(delimiter);
        }
        builder.append(suffix);
        return builder.toString();
    }

}
