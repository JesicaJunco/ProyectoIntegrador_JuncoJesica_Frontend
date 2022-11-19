export class Proyecto {
  id?: number;
  nombre?: string;
  descripcion?: string;
  img?: string;

  constructor(nombre: string, descripcion: string, img: string){
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.img = img;
  }
}

