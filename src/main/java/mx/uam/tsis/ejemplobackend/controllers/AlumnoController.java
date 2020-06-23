package mx.uam.tsis.ejemplobackend.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.EjemplobackendApplication;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.services.AlumnosServiceImp;

/**
 * Controlador para el API rest
 * 
 * @author humbertocervantes
 *
 */
@RestController
@Slf4j
public class AlumnoController {
	
	@Autowired
	AlumnosServiceImp serviceAlumno;
	
	private static final Logger log = LoggerFactory.getLogger(EjemplobackendApplication.class);
		
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody Alumno nuevoAlumno) {
		// No se deben agregar dos alumnos con la misma matricula
		log.info("Recibí llamada a create con "+nuevoAlumno);
		
		serviceAlumno.save(nuevoAlumno);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		return ResponseEntity.status(HttpStatus.OK).body(serviceAlumno.findAll());
		
	}

	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula);
		
		Alumno alumno = serviceAlumno.findByMatricula(matricula);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
	
	@PutMapping(path = "/alumnos/{matricula}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("matricula") Integer matricula, 
			@RequestBody Alumno nuevoAlumno) {
		Alumno alumno = serviceAlumno.findByMatricula(matricula);
		if(alumno != null) {//se encontro el alumno
			serviceAlumno.save(nuevoAlumno);
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
	}
	
	@DeleteMapping(path = "/alumnos/{matricula}")
	public ResponseEntity <?> delete(@PathVariable("matricula") Integer matricula) {
		if(serviceAlumno.findByMatricula(matricula) != null) { //encontró el usuario a eliminar
			serviceAlumno.delete(matricula);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
 
}//fin de la clase
