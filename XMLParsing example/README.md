1.1 XML 불러오기 

Case 1 > File로 부터 읽는 경우

Document xml = DocumentBuilderFactory.newInstance().newDoucumentBuilder().parse(new File(filePath));

Case 2 > String으로 읽는 경우
Document xml = 
DoucumentBuilderFactory.newInstance().newDocumentBuilder().
				Parse(new ByteArrayInputStream(xmlString.getBytes()));
Case 3> URL에서 파싱할 경우
Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().
				Parse("http://xxx.com");
				
1.2 정규화

Xml.getDocumentElement().normalize();


유니코드란
유니코드란 전 세계적으로 사용하는 모든 문자 집합을 하나로 모은 것이다. 유니코드 1.0.0은 1991년 8월 제정되었으며, 그 후 약 5년이 지나서야 유니코드 2.0.0에 한글 11,172자가 모두 포함되었다. 현재 버전은 2010년 10월 11일 제정된 6.0이다.

Java는 유니코드 정규화 기능을 지원하고 있다. 아래 코드는 그 예제이다.
import java.text.Normalizer;
public class NormalizerTest {  
    private void printIt(String string) {
        System.out.println(string);
        for (int i = 0; i < string.length(); i++) {
            System.out.print(String.format("U+%04X ", string.codePointAt(i)));
        }
        System.out.println();
    }
@Test
    public void test() {
        String han = "한";
        printIt(han);
String nfd = Normalizer.normalize(han, Normalizer.Form.NFD);
        printIt(nfd);
String nfc = Normalizer.normalize(nfd, Normalizer.Form.NFC);
        printIt(nfc);
    }
}
아래는 위의 코드를 실행한 결과이다.
	한
	U+D55C
	ㅎㅏㄴ
	U+1112 U+1161 U+11AB
	한
	U+D55C
	Ref http://d2.naver.com/helloworld/76650
	
2. XPath 생성
XPath는 XML노드들을 CSS다루듯이 쉽게 다루도록 도와주는 API입니다.
XPath xpath = XPathFactory.newInstance().newXPath();


3.노드 선택
	• Case1>단일 노드 선택
  단 한개의 노드를 선택할 경우입니다.
  만약 복수의 노드를 써서 선택할 경우 최 노드만 선택됩니다.

  //root 선택
  Node node = xml.getDoucumentElement();

 //rss 아래 channel 아래 위치한 generator 노드 선택
  Node node = (Node)xpath.evaluate("//rss/chhnel/generator",xml,XPathConstants.NODE);

 //Attirbute에 따른 선택 : id가 3인 product 노드 선택
  Node node = (Node) xpath.evaluate("//product[@id='3'],xml,XpathConstants.NODE);

 //전체 city 노드 중 첫번째 city 노드만 선택,첫번째 항목 선택이지만 0이 아니라 1임에 유의
  
  Node node = (Node) xpath.evalutate("//city[1]", xml, XPathConstants.NODE);

	• Case2>복수 노드 선택 
	복수의 노드를 NodeList로 반환합니다.
	//모든 city 노드 선택
	NodeList node = (NodeList) xpath.evaluate("//city",xml,XPathConstants.NODESET);
	
	//Attribute에 따른 선택:wl_ver가 3이면 어떤 노드 든지 다 선택
	NodeList node = (NodeList) xpath
		.evalutate("//*[@wl_ver='3']",xml,XpathConstants.NODESET);
		
	
	• Case3>자식 노드 선택
	Node의 getChildNodes() 메소드는 자식 노드들을 NodeList타입으로 반환합니다.
	
		//예:현재 선택된 노드가 Node타입이고 변수명이 node일 경우
		NodeList childs = node.getChildNodes();
		
4.선택한 노드 값 얻기
