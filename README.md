<h1>Mini Projet (Framework Injection des dépendance)</h1>

<h2>Enoncé :</h2>

<p>
Développer un mini Framework qui permet de faire l'injection des dépendances avec ses deux version XML et Annotations
Concevoir et créer un mini Framework d'injection des dépendances similaire à Spring IOC
Le Framework doit permettre à un programmeur de faire l'injection des dépendances entre les différents composant de son application respectant les possibilités suivantes : 
1- A travers un fichier XML de configuration en utilisant Jax Binding (OXM : Mapping Objet XML)
2- En utilisant les annotations
3- Possibilité d'injection via :
    a- Le constructeur
    b- Le Setter
    c- Attribut (accès direct à l'attribut : Field)
</p>

[//]: # (<h2>version XML</h2>)

[//]: # ()
[//]: # (<h3>Le fichier dependencies.xml : </h3>)

[//]: # (<p>)

[//]: # ()
[//]: # (  <?xml version="1.0" encoding="UTF-8"?>)

[//]: # (  <injections>)

[//]: # (      <injection id="dao" className="dao.IDaoImpl" />)

[//]: # (      <injection id="metier" className="metier.IMetierImpl">)

[//]: # (          <property name="dao" ref="dao" />)

[//]: # (      </injection>)

[//]: # (  </injections>)

[//]: # ()
[//]: # (</p>)

[//]: # ()
[//]: # (<h3>Injections class : </h3>)

[//]: # (<p>)

[//]: # ()
[//]: # (  package xmlVersion;)

[//]: # (  import javax.xml.bind.annotation.XmlElement;)

[//]: # (  import javax.xml.bind.annotation.XmlRootElement;)

[//]: # (  import java.util.List;)

[//]: # (  )
[//]: # (  @XmlRootElement&#40;name = "injections"&#41;)

[//]: # (  public class Injections {)

[//]: # (      @XmlElement&#40;name = "injection"&#41;)

[//]: # (      List<Injection> injections;)

[//]: # (  })

[//]: # (  )
[//]: # (</p>)

[//]: # ()
[//]: # (<h3>Injection class : </h3>)

[//]: # (<p>)

[//]: # ()
[//]: # (  package xmlVersion;)

[//]: # (  )
[//]: # (  import javax.xml.bind.annotation.XmlAttribute;)

[//]: # (  import javax.xml.bind.annotation.XmlElement;)

[//]: # (  import java.util.List;)

[//]: # (  )
[//]: # (  public class Injection {)

[//]: # (      @XmlAttribute)

[//]: # (      String id;)

[//]: # (  )
[//]: # (      @XmlAttribute&#40;name = "className"&#41;)

[//]: # (      String className;)

[//]: # (  )
[//]: # (      @XmlElement&#40;name = "property"&#41;)

[//]: # (      List<Property> properties;)

[//]: # (  })

[//]: # ()
[//]: # (</p>)

[//]: # ()
[//]: # (<h3>Property class : </h3>)

[//]: # (<p>)

[//]: # ()
[//]: # (  package xmlVersion;)

[//]: # (  )
[//]: # (  import javax.xml.bind.annotation.XmlAttribute;)

[//]: # (  )
[//]: # (  public class Property {)

[//]: # (      @XmlAttribute)

[//]: # (      String name;)

[//]: # (  )
[//]: # (      @XmlAttribute)

[//]: # (      String ref;)

[//]: # (  })

[//]: # (  )
[//]: # (</p>)

[//]: # ()
[//]: # (<h3>DependencyInjector class : </h3>)

[//]: # (<p>)

[//]: # ()
[//]: # (  package xmlVersion;)

[//]: # (  )
[//]: # (  import javax.xml.bind.JAXBContext;)

[//]: # (  import javax.xml.bind.Unmarshaller;)

[//]: # (  import java.io.InputStream;)

[//]: # (  import java.lang.reflect.Method;)

[//]: # (  import java.util.HashMap;)

[//]: # (  import java.util.Map;)

[//]: # (  )
[//]: # (  public class DependencyInjector {)

[//]: # (      private Map<String, Object> instances = new HashMap<>&#40;&#41;;)

[//]: # (  )
[//]: # (      public DependencyInjector&#40;String xmlFile&#41; throws Exception {)

[//]: # (          // Lire le fichier XML)

[//]: # (          JAXBContext jaxbContext = JAXBContext.newInstance&#40;Injections.class&#41;;)

[//]: # (          Unmarshaller unmarshaller = jaxbContext.createUnmarshaller&#40;&#41;;)

[//]: # (          InputStream xmlStream = this.getClass&#40;&#41;.getClassLoader&#40;&#41;.getResourceAsStream&#40;xmlFile&#41;;)

[//]: # (          Injections injections = &#40;Injections&#41; unmarshaller.unmarshal&#40;xmlStream&#41;;)

[//]: # (  )
[//]: # (          // Créer des instances et les stocker dans le map)

[//]: # (          for &#40;Injection injection : injections.injections&#41; {)

[//]: # (              Class<?> clazz = Class.forName&#40;injection.className&#41;;)

[//]: # (              Object instance = clazz.getDeclaredConstructor&#40;&#41;.newInstance&#40;&#41;;)

[//]: # (              instances.put&#40;injection.id, instance&#41;;)

[//]: # (          })

[//]: # (  )
[//]: # (          // Injecter les dépendances)

[//]: # (          for &#40;Injection injection : injections.injections&#41; {)

[//]: # (              if &#40;injection.properties != null&#41; {)

[//]: # (                  for &#40;Property property : injection.properties&#41; {)

[//]: # (                      Object instance = instances.get&#40;injection.id&#41;;)

[//]: # (                      Object dependency = instances.get&#40;property.ref&#41;;)

[//]: # (                      Method setter = instance.getClass&#40;&#41;.getMethod&#40;"set" + property.name.substring&#40;0, 1&#41;.toUpperCase&#40;&#41; + property.name.substring&#40;1&#41;, dependency.getClass&#40;&#41;.getInterfaces&#40;[0]&#41;;)

[//]: # (                      setter.invoke&#40;instance, dependency&#41;;)

[//]: # (                  })

[//]: # (              })

[//]: # (          })

[//]: # (      })

[//]: # (  )
[//]: # (      public Object getBean&#40;String id&#41; {)

[//]: # (          return instances.get&#40;id&#41;;)

[//]: # (      })

[//]: # (  })

[//]: # (  )
[//]: # (</p>)

[//]: # ()
[//]: # (<h3> Main : </h3>)

[//]: # (<p>)

[//]: # ()
[//]: # (  package presentation;)

[//]: # (  )
[//]: # (  import metier.IMetier;)

[//]: # (  import xmlVersion.DependencyInjector;)

[//]: # (  )
[//]: # (  public class PresentationXMLVersion {)

[//]: # (      public static void main&#40;String[] args&#41; throws Exception {)

[//]: # (          DependencyInjector context = new DependencyInjector&#40;"dependencies.xml"&#41;;)

[//]: # (          IMetier metier = &#40;IMetier&#41; context.getBean&#40;"metier"&#41;;)

[//]: # (          System.out.println&#40;metier.calcul&#40;&#41;&#41;;)

[//]: # (      })

[//]: # (  })

[//]: # ()
[//]: # (</p>)


  

