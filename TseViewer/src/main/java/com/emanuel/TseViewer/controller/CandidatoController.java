package com.emanuel.TseViewer.controller;

import com.emanuel.TseViewer.model.Candidato;
import com.emanuel.TseViewer.service.CandidatoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CandidatoController {

    private final CandidatoService candidatoService;

    public CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService;
    }

    @GetMapping
    public String getCandidatos(
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) String partido,
            @RequestParam(required = false) String texto,
            Model model
    ){

        List<Candidato> candidatosEncontrados = candidatoService.filtrar(cargo, partido, texto);

        model.addAttribute("candidatos", candidatosEncontrados);
        model.addAttribute("cargosLista", candidatoService.listarCargos());
        model.addAttribute("partidosLista", candidatoService.listarPartidos());
        model.addAttribute("cargo", cargo);
        model.addAttribute("partido", partido);
        model.addAttribute("texto", texto);

        if (candidatosEncontrados.size() > 1){
            model.addAttribute("mensagem", candidatosEncontrados.size() + " candidato(s) encontrado(s).");
        }else{
            model.addAttribute("mensagem", "Nenhum candidato encontrado.");
        }

        return "index";

    }
}
