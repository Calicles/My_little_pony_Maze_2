package com.antoine.services;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
	private Map<String, String> injections;
	private Map<String, String> methods;

	/**
	 *
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Assembler() throws SAXException, IOException, ParserConfigurationException {
		injections= new HashMap<>();
		id_class= new HashMap<>();
		methods= new HashMap<>();
		parse();
	}


	/**
	 *
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void parse() throws SAXException, IOException, ParserConfigurationException{
		SAXParserFactory factory= SAXParserFactory.newInstance();
		SAXParser parser= factory.newSAXParser();
		parser.parse("ressources/config/conf.xml", new XMLHandler());
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
	public Object newInstance(String id) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
	InstantiationException, IllegalAccessException, IllegalArgumentException,
	InvocationTargetException {
		if(!injections.containsKey(id)){
			throw new RuntimeException("id non présente dans fichier");
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
	private Object getBean(String id) throws ClassNotFoundException, NoSuchMethodException,
	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
	InvocationTargetException {
		
		Class<?> appClass= Class.forName(id_class.get(id));
		Constructor<?> appCons= appClass.getConstructor();
		Object app= appCons.newInstance();

		String[] parameters= injections.get(id).split(";");

		for(int i=0;i<parameters.length;i++) {
			
			if(id_class.containsKey(parameters[i])) {
				Object o= getBean(parameters[i]);
				Method m= appClass.getDeclaredMethod(methods.get(parameters[i]), o.getClass().getInterfaces());
				m.invoke(app,  o); System.out.println("in assembler   meth"+m);
			}else {
				String param= injections.get(parameters[i]);
				Method m= appClass.getDeclaredMethod(methods.get(parameters[i]), parameters[i].getClass());
				m.invoke(app, param);                          System.out.println("in assembler   meth"+m);
			}
		}
		
		return app;
	
	}

	/**
	 * <b>Classe interne pour gérer la lecture du fichier .xml</b>
	 * <p>lecture en mode event</p>
	 * @author antoine
	 */
	class XMLHandler extends DefaultHandler {


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
				Attributes attr) throws SAXException {
			
			if(qName.equals("bean")) {
				registerBean(attr);
			}else if(qName.equals("injection")) {
				registerInjection(attr);
			}
		
		}


		/**
		 *
		 * @param attr
		 */
		private void registerInjection(Attributes attr) {
			String id= null, value= null, method= null;
			
			for(int i=0;i<attr.getLength();i++) {
				if(attr.getLocalName(i).equals("ref")) {
					id= attr.getValue(i);
				}else if(attr.getLocalName(i).equals("value")) {
					value= attr.getValue(i);
				}else if(attr.getLocalName(i).equals("method")) {
					method= attr.getValue(i);
				}
			}
			if(value != null)
				injections.put(id, value);
			methods.put(id, method);
			
		}


		/**
		 *
		 * @param attr
		 */
		private void registerBean(Attributes attr) {
			String id= null, className= null, injection= null;
			
			for(int i=0;i<attr.getLength();i++) {
				if(attr.getLocalName(i).equals("id")) {
					id= attr.getValue(i);
				}else if(attr.getLocalName(i).equals("class")) {
					className= attr.getValue(i);
				}else if(attr.getLocalName(i).equals("injection")) {
					injection= attr.getValue(i);
				}
			}
			id_class.put(id, className);
			if(injection != null)
				injections.put(id, injection);

		}
	
	}

}
				