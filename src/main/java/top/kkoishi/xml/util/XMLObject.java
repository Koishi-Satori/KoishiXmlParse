package top.kkoishi.xml.util;

import java.io.Serializable;

/**
 * @author KKoishi_
 */
public interface XMLObject extends Serializable {

    int STRING = 0;

    int ANNOTATION = 1;

    int UNDEFINED = 2;

    int ENTRY = 3;

    /**
     * Get the id of the XML Object.
     *
     * @return id.
     */
    String getId ();

    /**
     * Get the type of the XMLObject.
     *
     * @return type.
     */
    int getType ();


    String getContent ();
}
