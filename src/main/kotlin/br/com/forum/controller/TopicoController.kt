package br.com.forum.controller

import br.com.forum.dto.AtualizacaoTopicoForm
import br.com.forum.dto.NovoTopicoForm
import br.com.forum.dto.TopicoView
import br.com.forum.service.TopicoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/topicos")
class TopicoController(private val service: TopicoService) { //spring vai injetar o service
    @GetMapping
    fun listar(): List<TopicoView> {
        return service.listar();
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        return service.buscarPorId(id)
    }

    @PostMapping
    // @Valid significa que é para validar as infos passadas na requisição com as validações adicionadas na classe NovoTopicoForm
    fun cadastrar(@RequestBody @Valid form: NovoTopicoForm, uriBuilder: UriComponentsBuilder): ResponseEntity<TopicoView> {
        val topicoView = service.cadastrar(form)
        var uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        // response entity é responsáve por customizer o http respose
        // status 201 / preenche o cabeçalho com um campo de localização do recurso novo criado (uri) / passando no body o objeto
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping("/{id}")
    fun atualizar(@RequestBody @Valid form: AtualizacaoTopicoForm, @PathVariable id: Long, ): ResponseEntity<TopicoView>{
        val topicoView =  service.atualizar(form, id)
        // 200
        return ResponseEntity.ok(topicoView)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // define que quando deletar responde com 204
    fun deletar(@PathVariable id: Long){
        service.deletar(id)
    }
}