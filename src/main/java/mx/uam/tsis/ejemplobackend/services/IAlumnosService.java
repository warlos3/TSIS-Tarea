package mx.uam.tsis.ejemplobackend.services;

import java.util.Collection;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

public interface IAlumnosService {
	void save(Alumno alumno);
	Collection<Alumno> findAll();
	Alumno findByMatricula(Integer matricula);
	void delete(Integer matricula);
	
	
}
