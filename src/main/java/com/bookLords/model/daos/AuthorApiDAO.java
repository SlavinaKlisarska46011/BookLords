package com.bookLords.model.daos;

import com.bookLords.model.DBConnection;
import com.bookLords.model.interfaces.IAuthorApiDAO;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

@Component
public class AuthorApiDAO implements IAuthorApiDAO{
	private static String getAuthorId(String name) throws ParserConfigurationException, SAXException, IOException {
		if (name != null && !name.isEmpty()) {
			name = name.replace(" ", "%20");
			name = name.trim();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db
					.parse(new URL("https://www.goodreads.com/api/author_url/" + name + "?key=NDdBXahEmTqBXY9fa7sRw")
							.openStream());

			String id = "";

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("author");

			Node nNode = nList.item(0);
			if (nNode instanceof Element) {
				id = ((Element) nNode).getAttribute("id");
			}

			return id;
		}
		return name;
	}

	public synchronized int insertAuthor(String name) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL("https://www.goodreads.com/author/show/" + getAuthorId(name)
					+ "?format=xml&key=NDdBXahEmTqBXY9fa7sRw").openStream());

			NodeList nList = doc.getElementsByTagName("author");

			Node nNode = nList.item(0);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) nNode;

				String authorName = element.getElementsByTagName("name").item(0).getTextContent();
				String imgURL = element.getElementsByTagName("image_url").item(0).getTextContent();
				String biography = element.getElementsByTagName("about").item(0).getTextContent();
				String born = "in " + element.getElementsByTagName("hometown").item(0).getTextContent();
				born += "\n" + element.getElementsByTagName("born_at").item(0).getTextContent();
				String died = element.getElementsByTagName("died_at").item(0).getTextContent();

				PreparedStatement ps = connection.prepareStatement("INSERT INTO authors VALUES(null, ?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, authorName);
				ps.setString(2, imgURL);
				ps.setString(3, born);
				ps.setString(4, died);
				ps.setString(5, biography);

				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				return rs.getInt(1);
			}

		} catch (ParserConfigurationException | SAXException | IOException | SQLException e) {

		}
		return 0;

	}
}
