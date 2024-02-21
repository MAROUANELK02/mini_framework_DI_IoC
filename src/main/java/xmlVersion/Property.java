package xmlVersion;

import javax.xml.bind.annotation.XmlAttribute;

public class Property {
    @XmlAttribute
    String name;

    @XmlAttribute
    String ref;
}