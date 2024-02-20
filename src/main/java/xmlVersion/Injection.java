package xmlVersion;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Injection {
    @XmlAttribute
    String id;

    @XmlAttribute(name = "className")
    String className;

    @XmlElement(name = "property")
    List<Property> properties;
}

