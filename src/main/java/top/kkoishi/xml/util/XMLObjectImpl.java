package top.kkoishi.xml.util;

import java.io.Serializable;

final class XMLObjectImpl implements XMLObject, Serializable {

    final String id;

    final int type;

    String content;

    public XMLObjectImpl (String id, int type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    /**
     * Get the id of the XML Object.
     *
     * @return id.
     */
    @Override
    public String getId () {
        return id;
    }

    /**
     * Get the type of the XMLObject.
     *
     * @return type.
     */
    @Override
    public int getType () {
        return type;
    }

    @Override
    public String getContent () {
        return content;
    }
}
