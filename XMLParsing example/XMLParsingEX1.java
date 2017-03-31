import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParsingEX1 {

    public static void main(String argv[]) {

        try {
            File file = new File("C:/Users/yhnu1/ideaProjects/what/src/addressbook.xml");
            DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
            Document doc = docBuild.parse(file);
            doc.getDocumentElement().normalize();

            System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
            System.out.println();

            // person엘리먼트 리스트
            NodeList personlist = doc.getElementsByTagName("person");

            for (int i = 0; i < personlist.getLength(); i++) {

                System.out.println("---------- personNode "+ i + "번째 ------------------");

                Node personNode = personlist.item(i);

                if (personNode.getNodeType() == Node.ELEMENT_NODE) {
                    // person엘리먼트
                    Element personElmnt = (Element) personNode;

                    // name 태그
                    NodeList nameList= personElmnt.getElementsByTagName("name");
                    Element nameElmnt = (Element) nameList.item(0);
                    Node name = nameElmnt.getFirstChild();
                    System.out.println("name    : " + name.getNodeValue());

                    // tel 태그
                    NodeList telList= personElmnt.getElementsByTagName("tel");
                    Element telElmnt = (Element) telList.item(0);
                    Node tel = telElmnt.getFirstChild();
                    System.out.println("tel     : " + tel.getNodeValue());

                    // address 태그
                    NodeList addressList= personElmnt.getElementsByTagName("address");
                    Element addressElmnt = (Element) addressList.item(0);
                    Node address = addressElmnt.getFirstChild();
                    System.out.println("address : " + address.getNodeValue());
                }

                System.out.println("---------------------------------------------");
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}