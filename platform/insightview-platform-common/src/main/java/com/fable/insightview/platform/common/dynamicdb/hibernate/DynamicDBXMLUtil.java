package com.fable.insightview.platform.common.dynamicdb.hibernate;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * 动态映射文件解析实现
 * 主要实现加载映射文件和对映射文件的保存
 * @author 郑自辉
 *
 */
public class DynamicDBXMLUtil {

	/**
	 * 删除节点的孩子节点
	 * @param node
	 */
	public static void removeChildren(Node node) {
        NodeList childNodes = node.getChildNodes();
        int length = childNodes.getLength();
        for (int i = length - 1; i > -1; i--)
            node.removeChild(childNodes.item(i));
        }

	/**
	 * 加载映射文件
	 * @param file
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
    public static Document loadDocument(String file)
        throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file);
    }

    /**
     * 映射文件修改之后的保存
     * @param dom
     * @param file
     * @throws TransformerException
     * @throws IOException
     */
    public static void saveDocument(Document dom, String file)
        throws TransformerException, IOException {

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, dom.getDoctype().getPublicId());
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dom.getDoctype().getSystemId());

        DOMSource source = new DOMSource(dom);
        StreamResult result = new StreamResult();

        FileOutputStream outputStream = new FileOutputStream(file);
        result.setOutputStream(outputStream);
        transformer.transform(source, result);

        outputStream.flush();
        outputStream.close();
    }
}
