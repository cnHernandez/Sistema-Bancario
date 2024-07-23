package entidad;

public class localidad {

	 private int idLocalidad;
	    private String nombre;
	    private provincia provincia;  // Usando composición

	    public localidad() {
	    }

		public int getIdLocalidad() {
			return idLocalidad;
		}

		public void setIdLocalidad(int idLocalidad) {
			this.idLocalidad = idLocalidad;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public provincia getProvincia() {
			return provincia;
		}

		public void setProvincia(provincia provincia) {
			this.provincia = provincia;
		}

		public localidad(int idLocalidad, String nombre, entidad.provincia provincia) {
			super();
			this.idLocalidad = idLocalidad;
			this.nombre = nombre;
			this.provincia = provincia;
		}

		
}
