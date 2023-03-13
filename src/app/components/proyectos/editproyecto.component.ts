import { Component, OnInit } from '@angular/core';
import { Proyecto } from 'src/app/model/proyecto';
import { ActivatedRoute, Router } from '@angular/router';
import { ImageProyectoService } from 'src/app/service/image-proyecto.service';
import { ProyectoService } from 'src/app/service/proyecto.service';
import { identifierName } from '@angular/compiler';

@Component({
  selector: 'app-editproyecto',
  templateUrl: './editproyecto.component.html',
  styleUrls: ['./editproyecto.component.css']
})
export class EditProyectoComponent implements OnInit {
  proyecto: Proyecto = null;
  imagenCargada: boolean = false;
  
  constructor(private proyectoService: ProyectoService, private activatedRouter: ActivatedRoute,
    private router: Router, public imagePService: ImageProyectoService) { }

  ngOnInit(): void {
    this.imagePService.url = "";
    const id = this.activatedRouter.snapshot.params['id'];
    this.proyectoService.detail(id).subscribe(
      data =>{
        this.proyecto = data;
      }, err =>{
        alert("Error al modificar experiencia");
        this.router.navigate(['']);
      }
    )
  }

  onUpdate(): void{
    const id = this.activatedRouter.snapshot.params['id'];
    this.proyecto.img = this.imagePService.url
    this.proyectoService.update(id, this.proyecto).subscribe(
      data => {
        this.router.navigate(['']);
      }, err =>{
         alert("Error al modificar");
         this.router.navigate(['']);
      }
    )
  }

  uploadImage($event:any){
      const id = this.activatedRouter.snapshot.params['id'];
      const name = "proyecto_" + id;
      this.imagePService.uploadImage($event, name);
    }
}
