package xmlVersion;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "injections")
public class Injections {
    @XmlElement(name = "injection")
    List<Injection> injections;
}

