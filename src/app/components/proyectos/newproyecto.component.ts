import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Proyecto } from 'src/app/model/proyecto';
import { ImageProyectoService } from 'src/app/service/image-proyecto.service';
import { ProyectoService } from 'src/app/service/proyecto.service';

@Component({
  selector: 'app-newproyecto',
  templateUrl: './newproyecto.component.html',
  styleUrls: ['./newproyecto.component.css']
})
export class NewProyectosComponent implements OnInit {
  nombre: string;
  descripcion: string;
  img: string;
  constructor(private proyectoService: ProyectoService,
     private router: Router,
     private activatedRouter: ActivatedRoute,
     public imagePService: ImageProyectoService,
    ) { }


  ngOnInit(): void {
    this.imagePService.url = "";
  }

  onCreate(): void {
    const proyecto = new Proyecto(this.nombre, this.descripcion, this.img = this.imagePService.url);
    this.proyectoService.save(proyecto).subscribe(
      data => {
        alert("Proyecto añadido");
        this.router.navigate(['']);
      }, err => {
        alert("Falló");
        this.router.navigate(['']);
      }
    )
  }

  uploadImg($event:any){
    const id = this.activatedRouter.snapshot.params['id'];
    const name = "proyecto_" + this.nombre;
    this.imagePService.uploadImage($event, name);
  }
}