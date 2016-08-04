/*
 * C�digo-fonte do livro "Programa��o Java para a Web"
 * Autores: D�cio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - todos os direitos reservados
 *
 * LICEN�A: Este arquivo-fonte est� sujeito a Atribui��o 2.5 Brasil, da licen�a Creative Commons,
 * que encontra-se dispon�vel no seguinte endere�o URI: http://creativecommons.org/licenses/by/2.5/br/
 * Se voc� n�o recebeu uma c�pia desta licen�a, e n�o conseguiu obt�-la pela internet, por favor,
 * envie uma notifica��o aos seus autores para que eles possam envi�-la para voc� imediatamente.
 *
 *
 * Source-code of "Programa��o Java para a Web" book
 * Authors: D�cio Heinzelmann Luckow <decioluckow@gmail.com>
 *          Alexandre Altair de Melo <alexandremelo.br@gmail.com>
 *
 * ISBN: 978-85-7522-238-6
 * http://www.javaparaweb.com.br
 * http://www.novatec.com.br/livros/javaparaweb
 * Editora Novatec, 2010 - all rights reserved
 *
 * LICENSE: This source file is subject to Attribution version 2.5 Brazil of the Creative Commons
 * license that is available through the following URI:  http://creativecommons.org/licenses/by/2.5/br/
 * If you did not receive a copy of this license and are unable to obtain it through the web, please
 * send a note to the authors so they can mail you a copy immediately.
 *
 */
package financeiro.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import financeiro.model.Categoria;
import financeiro.repository.CategoriaRepository;
import financeiro.repository.hibernate.CategoriaRepositoryImpl;
import financeiro.rn.CategoriaRN;
import financeiro.util.EntityManagerFactorySingleton;

/**
 * 
 * @author arthur
 * TODO problema com jpa em
 *
 */
@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {
	
	private EntityManager manager;	
	
	private CategoriaRN categoriaRN;
	
	public CategoriaConverter(){
		/*EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		manager = emf.createEntityManager();
		CategoriaRepository categoriaRepository = new CategoriaRepositoryImpl(manager);
		categoriaRN = new CategoriaRN(categoriaRepository);*/
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Object categoria = null;
		
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			Categoria cat = new Categoria();
			try {				
				cat.setCodigo(codigo);
				
				//manager.getTransaction().begin();				
				//categoria = categoriaRN.carregar(codigo);
				//manager.getTransaction().commit();
			} catch (Exception e) {
				try {
					if ( this.manager.getTransaction().isActive() ){
						this.manager.getTransaction().rollback();
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
				throw new ConverterException("N�o foi poss�vel encontrar a categoria de c�digo " + value + "." + e.getMessage());
			} finally {
				//this.manager.close();
			}
			return cat;
		}
		return categoria;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Categoria categoria = (Categoria) value;
			return categoria.getCodigo().toString();
		}
		return "";
	}
}
