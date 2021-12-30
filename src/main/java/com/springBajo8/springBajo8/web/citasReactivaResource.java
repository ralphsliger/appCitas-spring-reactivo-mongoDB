package com.springBajo8.springBajo8.web;


import com.springBajo8.springBajo8.domain.citasDTOReactiva;
import com.springBajo8.springBajo8.service.IcitasReactivaService;
import com.springBajo8.springBajo8.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class citasReactivaResource {

    @Autowired
    private IcitasReactivaService icitasReactivaService;

    @PostMapping("/citasReactivas")
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<citasDTOReactiva> save(@RequestBody citasDTOReactiva citasDTOReactiva) {
        return this.icitasReactivaService.save(citasDTOReactiva);
    }

    @DeleteMapping("/citasReactivas/{id}")
    private Mono<ResponseEntity<citasDTOReactiva>> delete(@PathVariable("id") String id) {
        return this.icitasReactivaService.delete(id)
                .flatMap(citasDTOReactiva -> Mono.just(ResponseEntity.ok(citasDTOReactiva)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @PutMapping("/citasReactivas/{id}")
    private Mono<ResponseEntity<citasDTOReactiva>> update(@PathVariable("id") String id, @RequestBody citasDTOReactiva citasDTOReactiva) {
        return this.icitasReactivaService.update(id, citasDTOReactiva)
                .flatMap(citasDTOReactiva1 -> Mono.just(ResponseEntity.ok(citasDTOReactiva1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @GetMapping("/citasReactivas/{idPaciente}/byidPaciente")
    private Flux<citasDTOReactiva> findAllByidPaciente(@PathVariable("idPaciente") String idPaciente) {
        return this.icitasReactivaService.findByIdPaciente(idPaciente);
    }

    @GetMapping(value = "/citasReactivas")
    private Flux<citasDTOReactiva> findAll() {
        return this.icitasReactivaService.findAll();
    }

    @GetMapping(value = "/citasReactivas/{fecha}/{hora}")
    private Flux<citasDTOReactiva> findAllByHourAndDate(@PathVariable(value = "fecha") String fecha, @PathVariable(value = "hora") String hora) {
        return this.icitasReactivaService.findAllByFechaReservaCitaAndHoraReservaCita(UtilsMethods.StringToLocalDate(fecha), hora);
    }

    @PutMapping("/citasReactivas/cancelar/{id}")
    private Mono<ResponseEntity<citasDTOReactiva>> cancel(@PathVariable("id") String id) {
        return this.icitasReactivaService.findById(id).flatMap(citasDTOReactiva -> {
            citasDTOReactiva.setEstadoCita(false);
            citasDTOReactiva.setEstadoReservaCita("Cancelada");
            return this.icitasReactivaService.save(citasDTOReactiva);
        }).flatMap(citasDTOReactiva -> Mono.just(ResponseEntity.ok(citasDTOReactiva))).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @GetMapping("/citasReactivas/nombreMedico/{id}")
    private Mono<citasDTOReactiva> getMedicoNames(@PathVariable("id") String id) {
        Mono<citasDTOReactiva> infoMedico = this.icitasReactivaService.findById(id).flatMap(citasDTOReactiva -> {
            citasDTOReactiva medico = new citasDTOReactiva();
            medico.setNombreMedico(citasDTOReactiva.getNombreMedico());
            medico.setApellidosMedico(citasDTOReactiva.getApellidosMedico());
            return Mono.just(medico);
        });
        return infoMedico;
    }

    @GetMapping("/citasReactivas/padecimientos/{id}/paciente")
    private Mono<citasDTOReactiva> getPadecimientosYtratamiento(@PathVariable("id") String id) {
        Mono<citasDTOReactiva> infoPaciente = this.icitasReactivaService.findById(id).flatMap(citasDTOReactiva -> {
            citasDTOReactiva paciente = new citasDTOReactiva();
            paciente.setPadecimientos(citasDTOReactiva.getPadecimientos());
            paciente.setTratamientos(citasDTOReactiva.getTratamientos());
            return Mono.just(paciente);
        });
        return infoPaciente;
    }






}
