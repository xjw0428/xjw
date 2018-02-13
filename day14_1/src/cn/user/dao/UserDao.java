package cn.user.dao;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.user.domain.User;

/*
 * 数据类
 */
public class UserDao {
    private String path="D:/users.xml";//依赖数据文件
    
    public User findByUsername(String username){
    	try {
    	    SAXReader reader =new SAXReader();
			Document doc = reader.read(path);
			//xpath查询
			Element ele=(Element)doc.selectSingleNode("//user[@username='"+username+"']");
		    if(ele==null){
		    	return null;
		    }
    	    User user =new User();
    	    user.setUsername(ele.attributeValue("username"));
    	    user.setPassword(ele.attributeValue("password"));
    	    return user;
    	} catch (Exception e) {
	      throw new RuntimeException(e);
	    }
    }
    
    public void add(User user){
    	  SAXReader reader =new SAXReader();
			try {
				Document doc = reader.read(path);
				Element root=doc.getRootElement();
				Element userEle=root.addElement("user");
				userEle.addAttribute("username", user.getUsername());
				userEle.addAttribute("password", user.getPassword());
				
				OutputFormat format = new OutputFormat("\t",true);
				format.setTrimText(true);
			
				XMLWriter writer =new XMLWriter(
							new OutputStreamWriter(new FileOutputStream(path),"UTF-8"),format);
				writer.write(doc);
				writer.close();
				
			} catch (Exception e) {
				 throw new RuntimeException(e);
			}
    
    }
}
