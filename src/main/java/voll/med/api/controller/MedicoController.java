package voll.med.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import voll.med.api.domain.medico.*;

    @RestController
    @RequestMapping("/medicos")
    @SecurityRequirement(name = "bearer-key")
    public class MedicoController {

    @Autowired
    private MedicoService service;

    @Autowired
    private MedicoRepository repository;

        @Autowired
        public MedicoController(MedicoService service) {
            this.service = service;
        }

        @PostMapping
        public ResponseEntity<DadosListagemMedico> cadastrar(
                @RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
            DadosListagemMedico dadosListagem = service.cadastrarMedico(dados);
            var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(dadosListagem.id()).toUri();
            return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(dadosListagem);
        }
        @GetMapping
        public ResponseEntity<Page<DadosListagemMedico>> listar(
                @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
            var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
            return ResponseEntity.ok(page);
        }
        @PutMapping
        @Transactional
        public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
            var medico = repository.getReferenceById(dados.id());
            medico.atualizarInformacoes(dados);
            return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
        }
        @DeleteMapping("/{id}")
        @Transactional
        public ResponseEntity excluir(@PathVariable Long id) {
            var medico = repository.getReferenceById(id);
            medico.excluir();
            return ResponseEntity.noContent().build();
        }
        @GetMapping("/{id}")
        public ResponseEntity detalhar(@PathVariable Long id) {
            var medico = repository.getReferenceById(id);
            return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
        }
    }
