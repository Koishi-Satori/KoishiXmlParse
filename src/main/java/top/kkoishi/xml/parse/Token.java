package top.kkoishi.xml.parse;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Kkoishi_
 */
public final class Token implements Serializable {

    public static final int ELEMENT_START = 0;

    public static final int ELEMENT_END = 1;

    public static final int XML_OBJECT = 2;

    public static final int XML_ANNOTATION = 3;

    public static final int ELEMENT = 4;

    private static final HashMap<Integer, String> TOKEN_TYPE_STRING_DICT = new HashMap<>();

    static {
        TOKEN_TYPE_STRING_DICT.put(0, "START");
        TOKEN_TYPE_STRING_DICT.put(1, "END");
        TOKEN_TYPE_STRING_DICT.put(2, "OBJ");
        TOKEN_TYPE_STRING_DICT.put(3, "ANNOTATION");
        TOKEN_TYPE_STRING_DICT.put(4, "ELE");
    }

    int type;

    StringBuilder content;

    public Token (int type, String content) {
        this.type = type;
        this.content = new StringBuilder(content);
    }

    @Override
    public String toString () {
        return "Token[%s]{content='%s'}".formatted(TOKEN_TYPE_STRING_DICT.get(type), content);
    }
}
