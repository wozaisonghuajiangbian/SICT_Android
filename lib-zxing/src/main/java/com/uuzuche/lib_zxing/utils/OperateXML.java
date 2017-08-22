

package com.uuzuche.lib_zxing.utils;


import com.uuzuche.lib_zxing.bean.Equipment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by ace on 2017/7/21.
 */
public class OperateXML {
    Document document;

    public static boolean addEquipment(String filePath, Equipment equipment)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try
        {
            //读取xml文件
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("file://" + filePath);
            //新建equipment节点
            Element newEquipment = doc.createElement("equipment");
            newEquipment.setAttribute("index", String.valueOf(doc.getElementsByTagName("equipment").getLength() + 1));
            Element id = doc.createElement("id");
            Element assetNum = doc.createElement("assetnum");
            Element roomNum = doc.createElement("roomnum");
            Element pcType = doc.createElement("pctype");
            Element pcBrand = doc.createElement("pcbrand");
            Element department = doc.createElement("department");
            Element userName = doc.createElement("username");
            Element system = doc.createElement("system");
            Element officeSoft = doc.createElement("officesoft");
            Element antiSoft = doc.createElement("antisoft");

            id.appendChild(doc.createTextNode(equipment.getId()));
            assetNum.appendChild(doc.createTextNode(equipment.getAssetNum()));
            roomNum.appendChild(doc.createTextNode(equipment.getRoomNum()));
            pcType.appendChild(doc.createTextNode(equipment.getPcType()));
            pcBrand.appendChild(doc.createTextNode(equipment.getPcBrand()));
            department.appendChild(doc.createTextNode(equipment.getDepartment()));
            userName.appendChild(doc.createTextNode(equipment.getUserName()));
            system.appendChild(doc.createTextNode(equipment.getSystem()));
            officeSoft.appendChild(doc.createTextNode(equipment.getOfficeSoft()));
            antiSoft.appendChild(doc.createTextNode(equipment.getAntiSoft()));

            newEquipment.appendChild(id);
            newEquipment.appendChild(assetNum);
            newEquipment.appendChild(roomNum);
            newEquipment.appendChild(pcType);
            newEquipment.appendChild(pcBrand);
            newEquipment.appendChild(department);
            newEquipment.appendChild(userName);
            newEquipment.appendChild(system);
            newEquipment.appendChild(officeSoft);
            newEquipment.appendChild(antiSoft);

            Element root = (Element) doc.getElementsByTagName("company").item(0);
            root.appendChild(newEquipment);

            writeXml(doc, filePath);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

    public static void writeXml(Document doc, String filePath)
    {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;

        try
        {
            transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");// 设置自动换行
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "4");   //设置自动换行
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, new StreamResult(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Equipment> viewAllEquip(String xmlPath)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();


        //读取xml文件
        Element tmp;
        ArrayList dataList = null;
        DocumentBuilder db = null;
        Equipment tmpEquipment = null;

        try
        {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse("file://" + xmlPath);
            dataList = new ArrayList();

            NodeList allEquipment = doc.getElementsByTagName("equipment");

            for (int i = 0; i < allEquipment.getLength(); i++)
            {
                tmp = (Element) allEquipment.item(i);

                tmpEquipment = new Equipment(
                    tmp.getElementsByTagName("assetnum").item(0).getTextContent(),
                    tmp.getElementsByTagName("username").item(0).getTextContent(),
                    tmp.getElementsByTagName("roomnum").item(0).getTextContent(),
                    tmp.getElementsByTagName("department").item(0).getTextContent(),
                    tmp.getElementsByTagName("pctype").item(0).getTextContent());

                dataList.add(tmpEquipment);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }


    public static void createXml(String xmlPath)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try
        {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        File file = new File(xmlPath);
        if(!file.exists())
        {
            Document document = builder.newDocument();
            Element root = document.createElement("company");
            document.appendChild(root);
            writeXml(document, xmlPath);
        }
    }

    public static String[] isDuplicate(String assetNum, String id, String filePath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        String[] reference = new String[2];
        Element element;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            NodeList allEquipment = doc.getElementsByTagName("equipment");

            for (int i = 0; i < allEquipment.getLength(); i++) {
                element = (Element) allEquipment.item(i);
                if (assetNum.equals(element.getElementsByTagName("assetnum").item(0).getTextContent())) {
                    if(id.equals(element.getElementsByTagName("id").item(0).getTextContent()))
                    {

                        reference[0] = "资产编号与MAC地址均重复，是否覆盖？";
                    }
                    else
                    {
                        reference[0] = "资产编号重复，是否覆盖？";
                    }
                    reference[1] = String.valueOf(i);
                    return reference;
                }
                if(id.equals(element.getElementsByTagName("id").item(0).getTextContent()))
                {
                    reference[0] = "MAC地址重复，是否覆盖？";
                    reference[1] = String.valueOf(i);
                    return reference;
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static boolean coverEquipmentInfo(String filePath, int coverIndex, Equipment newEquipment)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            NodeList allEquipment = doc.getElementsByTagName("equipment");

            Element element = (Element) allEquipment.item(coverIndex);
            element.getElementsByTagName("id").item(0).getFirstChild().setNodeValue(newEquipment.getId());
            element.getElementsByTagName("username").item(0).getFirstChild().setNodeValue(newEquipment.getUserName());
            element.getElementsByTagName("roomnum").item(0).getFirstChild().setNodeValue(newEquipment.getRoomNum());
            element.getElementsByTagName("department").item(0).getFirstChild().setNodeValue(newEquipment.getDepartment());
            element.getElementsByTagName("system").item(0).getFirstChild().setNodeValue(newEquipment.getSystem());
            element.getElementsByTagName("antisoft").item(0).getFirstChild().setNodeValue(newEquipment.getAntiSoft());
            element.getElementsByTagName("officesoft").item(0).getFirstChild().setNodeValue(newEquipment.getOfficeSoft());
            element.getElementsByTagName("pctype").item(0).getFirstChild().setNodeValue(newEquipment.getPcType());
            element.getElementsByTagName("pcbrand").item(0).getFirstChild().setNodeValue(newEquipment.getPcBrand());

            writeXml(doc, filePath);


            return true;

        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
