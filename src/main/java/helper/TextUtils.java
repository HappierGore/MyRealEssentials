package helper;

/**
 *
 * @author HappierGore
 */
public final class TextUtils {

    public static String parseColor(String text) {
        return text.replace("&", "§");
    }

    public static String errorMsg(String msg, boolean includeLoggerHeader) {
        StringBuilder str = new StringBuilder();
        if (includeLoggerHeader) {
            str.append("\n&3------------------ §bMyRealEssentials - Logger §3------------------");
        }
        str.append("\n&4ERROR:\n");
        str.append(msg);
        if (includeLoggerHeader) {
            str.append("\n&9------------------------------------------------------------------");
        }
        return str.toString();
    }
}
