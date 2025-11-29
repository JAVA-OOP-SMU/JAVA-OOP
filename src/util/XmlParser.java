package util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {

    // XML 문서에서 특정 태그의 값을 추출하는 메서드
    public static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag); // 주어진 태그명으로 노드 찾기

        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null && node.getChildNodes().item(0) != null) {
                return node.getChildNodes().item(0).getNodeValue();
            }
        }
        return "정보 없음";
    }
}
