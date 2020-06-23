package mx.uam.tsis.ejemplobackend.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@Service
public class AlumnosServiceImp implements IAlumnosService{

	// La "base de datos"
	private Map <Integer, Alumno> alumnoRepository = new HashMap <>();
	
	@Override
	public void save(Alumno alumno) {
		alumnoRepository.put(alumno.getMatricula(), alumno);
	}

	@Override
	public Collection<Alumno> findAll() {
		return alumnoRepository.values();
	}

	@Override
	public Alumno findByMatricula(Integer matricula) {
		return alumnoRepository.get(matricula);
	}

	@Override
	public void delete(Integer matricula) {
		alumnoRepository.remove(matricula);
	}



}
