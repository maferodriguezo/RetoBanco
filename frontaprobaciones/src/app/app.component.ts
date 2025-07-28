import { Component, OnInit } from '@angular/core';
import { CommonModule, JsonPipe } from '@angular/common';
import { SolicitudService } from './services/solicitud.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, JsonPipe],
  template: `
    <h1>Estado de conexión:</h1>
    <p>{{ connectionStatus }}</p>
    <pre>{{ apiResponse | json }}</pre>
  `,
})
export class AppComponent implements OnInit {
  connectionStatus = "Probando conexión...";
  apiResponse: any;

  constructor(private solicitudService: SolicitudService) {}

  ngOnInit() {
    this.solicitudService.obtenerSolicitudes().subscribe({ 
      next: (response) => {
        this.connectionStatus = "Conexión exitosa con el backend";
        this.apiResponse = response;
      },
      error: (error) => {
        this.connectionStatus = "Error de conexión con el backend";
        this.apiResponse = error;
      }
    });
  }
}