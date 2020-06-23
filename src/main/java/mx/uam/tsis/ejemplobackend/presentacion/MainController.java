package mx.uam.tsis.ejemplobackend.presentacion;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.services.AlumnosServiceImp;

/**
 * Controlador web
 * 
 * @author humbertocervantes
 *
 */
@Controller
@Slf4j
public class MainController {
	
	@Autowired
	AlumnosServiceImp serviceAlumno;
	
	/**
	 * index: Muestra un listado de los alumnos registrados
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("alumnos", serviceAlumno.findAll());
		return "index";
	}

	/**
	 * create: manda formulario para registrar un nuevo alumno
	 * @param alumno
	 * @param model
	 * @return
	 */
	@GetMapping("/create")
	public String create(Alumno alumno, Model model) {
		model.addAttribute("alumno", alumno);
		return "formAlumno";
	}
	
	/**
	 * save: guarda un alumno nuevo en la BD.
	 * @param alumno
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/save")
	public String save(Alumno alumno, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) { //si hubo errores en el registro, se manda un aviso
			attributes.addAttribute("error", "Ocurrió un problema la registrar el alumno, por favor, verifíquelo.");
			model.addAttribute("alumno", alumno);
			return "formAlumno";
		}
		/*Verificamos que el alumno sea nuevo para generar su matricula*/
		if(alumno.getMatricula() == null) {
			alumno.setMatricula(getRandomInteger(0,2000000000));
			attributes.addFlashAttribute("msg", "Alumno Registrado");
		} //si no, se está editando un alumno y no se cambia su matricula
		else {
			attributes.addFlashAttribute("msg", "Alumno editado con éxito");
		}
		serviceAlumno.save(alumno);
		return "redirect:/";
	}
	
	/**
	 * edit: Se busca el alumno respecto a su matricula, se encuentra el alumno y se le pasa
	 * a la vista para ser modificado
	 * @param matricula
	 * @param model
	 * @return
	 */
	@GetMapping("/editar/{matricula}")
	public String edit(@PathVariable("matricula") Integer matricula, Model model) {
		Alumno alumno = serviceAlumno.findByMatricula(matricula);
		if(alumno == null) { //si no se encuentra el alumno a editar, se crea uno nuevo
			model.addAttribute("alumno", new Alumno());
		}
		else {
			model.addAttribute("alumno", alumno);
		}
		return "formAlumno";
	}
	
	/**
	 * delete: se busca el alumno con la matricula correspondiente, si es encontrado se elimina
	 * en otro caso, manda mensaje de que el usuario no existe.
	 * @param matricula
	 * @param model
	 * @return
	 */
	@GetMapping("/delete/{matricula}")
	public String delete(@PathVariable("matricula") Integer matricula, Model model, RedirectAttributes attributes) {
		serviceAlumno.delete(matricula);
		attributes.addFlashAttribute("delete", "Alumno eliminado");
		return "redirect:/";
	}
	
	/*Genera número aleatorio para la matricula del alumno*/
	public static int getRandomInteger(double min, double max){
		int x = (int) ((Math.random()*((max-min)+1))+min);
		return x;
	}

}
