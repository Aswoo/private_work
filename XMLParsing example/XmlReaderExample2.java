import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by aswoo on 2017-03-31.
 */
public class XmlReaderExample2 {
    public static void main(String argv[]) {

        try {
            File file = new File("C:/Users/yhnu1/IdeaProjects/XMLparsingEX2/src/comboBox.xml");
            DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
            Document doc = docBuild.parse(file);
            doc.getDocumentElement().normalize();

            System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
            System.out.println();

            // comboBox엘리먼트 리스트
            NodeList comboBoxlist = doc.getElementsByTagName("comboBox");

            for (int i = 0; i < comboBoxlist.getLength(); i++) {

                System.out.println("---------- comboBoxNode "+ i + "번째 ------------------");

                Node comboBoxNode = comboBoxlist.item(i);

                if (comboBoxNode.getNodeType() == Node.ELEMENT_NODE) {
                    // comboBox엘리먼트
                    Element comboBoxElmnt = (Element) comboBoxNode;

                    // 콤보박스 명
                    System.out.println("comboBox name : " + comboBoxElmnt.getAttribute("name"));

                    // option 태그
                    NodeList optList= comboBoxElmnt.getElementsByTagName("option");
                    for (int j = 0; j < optList.getLength(); j++) {
                        Element optElmnt = (Element) optList.item(j);
                        Node name = optElmnt.getFirstChild();
                        System.out.println("option : " + name.getNodeValue() + "   value : " +optElmnt.getAttribute("value"));
                    }

                }

                System.out.println("---------------------------------------------");
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
