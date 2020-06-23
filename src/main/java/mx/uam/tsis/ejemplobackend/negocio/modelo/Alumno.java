package mx.uam.tsis.ejemplobackend.negocio.modelo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Alumno {
	private Integer matricula;
	
	private String nombre;
	
	private String carrera;

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	@Override
	public String toString() {
		return "Alumno [matricula=" + matricula + ", nombre=" + nombre + ", carrera=" + carrera + "]";
	}
	
	
}
