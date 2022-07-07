package br.com.forum.service

import br.com.forum.dto.AtualizacaoTopicoForm
import br.com.forum.dto.NovoTopicoForm
import br.com.forum.dto.TopicoView
import br.com.forum.exception.NotFoundException
import br.com.forum.mapper.TopicoFormMapper
import br.com.forum.mapper.TopicoViewMapper
import br.com.forum.model.Topico
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class TopicoService(
    private var topicos: List<Topico> = ArrayList(),
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Tópico não encontrado"
) {
    /*
    init {
        val topico1 = Topico(
            id = 1,
            titulo = "Dúvida Kotlin",
            mensagem = "Variáveis no kotlin",
            curso = Curso(
                id = 1,
                nome = "Kotlin",
                categoria = "Programação"
            ),
            autor = Usuario(
                id = 1,
                nome = "Vitor Almeida",
                email = "vitor@email.com"
            )
        )

        val topico2 = Topico(
            id = 2,
            titulo = "Dúvida Kotlin 2",
            mensagem = "Variáveis no kotlin",
            curso = Curso(
                id = 1,
                nome = "Kotlin",
                categoria = "Programação"
            ),
            autor = Usuario(
                id = 1,
                nome = "Vitor Almeida",
                email = "vitor@email.com"
            )
        )

        val topico3 = Topico(
            id = 3,
            titulo = "Dúvida Kotlin 3",
            mensagem = "Variáveis no kotlin",
            curso = Curso(
                id = 1,
                nome = "Kotlin",
                categoria = "Programação"
            ),
            autor = Usuario(
                id = 1,
                nome = "Vitor Almeida",
                email = "vitor@email.com"
            )
        )
        topicos = Arrays.asList(topico1, topico2, topico3)

    }
    */

    fun listar(): List<TopicoView> {
        return topicos.stream().map{t -> topicoViewMapper.map(t) }.collect(Collectors.toList())
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = topicos.stream().filter{ t -> t.id == id }.findFirst().orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(form: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(form)
        topico.id = topicos.size.toLong() + 1
        topicos = topicos.plus(topico)
        return topicoViewMapper.map(topico)
    }

    fun atualizar(form: AtualizacaoTopicoForm, id: Long): TopicoView {
        val topico = topicos.stream().filter{ t -> t.id == id }.findFirst().orElseThrow{NotFoundException(notFoundMessage)}
        val novoTopico = Topico(
            id = topico.id,
            titulo = form.titulo,
            mensagem = form.mensagem,
            autor = topico.autor,
            curso = topico.curso,
            respostas = topico.respostas,
            status = topico.status,
            dataCriacao = topico.dataCriacao
        )
        topicos = topicos.minus(topico).plus(novoTopico)
        return topicoViewMapper.map(novoTopico)
    }

    fun deletar(id: Long) {
        val topico = topicos.stream().filter{ t -> t.id == id }.findFirst().orElseThrow{NotFoundException(notFoundMessage)}
        topicos = topicos.minus(topico)
    }
}