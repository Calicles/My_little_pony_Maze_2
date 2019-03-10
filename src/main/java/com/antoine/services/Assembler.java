package com.antoine.services;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * <b>Classe de container et d'injection de dépendance.</b>
 * @author antoine
 */
public class Assembler {
	
	private Map<String, String> id_class;	//gestion d'un cache
	private Map<String, String[]> idBean_idParameters;
	private Map<String, String> idParam_value;
	private Map<String, String> idParam_methods;

	/**
	 *
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Assembler(String filePath) {
		idBean_idParameters= new HashMap<>();
		id_class= new HashMap<>();
		idParam_value= new HashMap<>();
		idParam_methods= new HashMap<>();
		parse(filePath);
	}


	/**
	 *
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private void parse(String filePath) {
		SAXParserFactory factory= SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();

			parser.parse(filePath, new XMLHandler());
		} catch (SAXException  | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException("erreur de lecture du fichier de configuration");
		}
	}


	/**
	 *
	 * @param id
	 * @return
	 */
	public Object newInstance(String id) {
		if(!id_class.containsKey(id)){
			throw new RuntimeException("id non présente dans le fichier de configuration");
		}
		return getBean(id);
	}


	/**
	 *
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private Object getBean(String id) {

		Object bean= null;

		try {
			Method m;
			Class<?> beanClass = Class.forName(id_class.get(id));
			Constructor<?> beanConstructor = beanClass.getConstructor();
			bean = beanConstructor.newInstance();

			String[] parameters = idBean_idParameters.get(id);

			for (int i = 0; i < parameters.length; i++) {

				if (id_class.containsKey(parameters[i])) {
					Object o = getBean(parameters[i]);
					if(o.getClass().getInterfaces().length != 0) {
						m = beanClass.getMethod(idParam_methods.get(parameters[i]), o.getClass().getInterfaces());
					}else
						m= beanClass.getMethod(idParam_methods.get(parameters[i]), o.getClass());
					m.invoke(bean, o);

				} else {

					String param = idParam_value.get(parameters[i]);
					m = beanClass.getMethod(idParam_methods.get(parameters[i]), parameters[i].getClass());
					m.invoke(bean, param);
				}
			}

		}catch (InstantiationException | InvocationTargetException | NoSuchMethodException |
		IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("erreur lors de la lecture du fichier de configuration");
		}

		return bean;
	
	}

	/**
	 * <b>Classe interne pour gérer la lecture du fichier .xml</b>
	 * <p>lecture en mode event</p>
	 * @author antoine
	 */
	class XMLHandler extends DefaultHandler {


		private Stack<String> currentBean= new Stack<>();
		private Stack<Integer> indexCurrentBean= new Stack<>();

		/**
		 *
		 * @param nameSpaceURI
		 * @param lName
		 * @param qName
		 * @param attr
		 * @throws SAXException
		 */
		@Override
		public void startElement(String nameSpaceURI, String lName, String qName,
				Attributes attr) {

			int length= 0;

			if(qName.equals("bean")) {

				registerBean(attr);

			}else if(qName.equals("injection")){

				registerInjection(attr);

			}else if(qName.equals("parameters")){

				length= Integer.parseInt(attr.getValue("nbr"));
				idBean_idParameters.put(currentBean.peek(), new String[length]);

			}else if(qName.equals("paramInjection") || qName.equals("parameter")) {

				registerParameter(attr);
			}

		}

		private void registerInjection(Attributes attr) {
			int index= indexCurrentBean.pop();

			idBean_idParameters.get(currentBean.peek())[index]= attr.getValue("id");

			indexCurrentBean.push(++index);

			registerBean(attr);

		}

		private void registerParameter(Attributes attr) {

			String idParam= "";

			for(int i= 0; i< attr.getLength(); i++) {

				if(attr.getLocalName(i).equals("id")) {

					int index= indexCurrentBean.pop();
					idParam= attr.getValue(i);
					idBean_idParameters.get(currentBean.peek())[index]= idParam;
					indexCurrentBean.push(++index);

				}else if(attr.getLocalName(i).equals("value")) {

					idParam_value.put(idParam, attr.getValue(i));

				}else if (attr.getLocalName(i).equals("method")) {

					idParam_methods.put(idParam, attr.getValue(i));
				}
			}
		}


		@Override
		public void endElement(String uri, String localName, String qName) {

			if(qName.equals("bean") || qName.equals("injection")){
				currentBean.pop();
				indexCurrentBean.pop();
			}
		}


		/**
		 *
		 * @param attr
		 */
		private void registerBean(Attributes attr) {

			String id= null, className= null;
			indexCurrentBean.push(0);

			id = attr.getValue("id");
			currentBean.push(id);

			className = attr.getValue("class");

			if (attr.getValue("method") != null)
				idParam_methods.put(id, attr.getValue("method"));

			id_class.put(id, className);
		}
	
	}

}
				